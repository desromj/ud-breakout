package com.udacity.desromj.breakout.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.udacity.desromj.breakout.entity.Ball;
import com.udacity.desromj.breakout.entity.Block;
import com.udacity.desromj.breakout.entity.Blocks;
import com.udacity.desromj.breakout.entity.Platform;
import com.udacity.desromj.breakout.util.Constants;
import com.udacity.desromj.breakout.util.Difficulty;

/**
 * Created by Quiv on 2015-12-27.
 */
public class BreakoutScreen extends ScreenAdapter
{
    Game game;

    ShapeRenderer renderer;
    Viewport viewport;

    // Gameplay Objects which need to be updated and rendered
    Platform platform;
    Ball ball;
    Difficulty difficulty;
    Blocks blocks;

    // Other game-specific variables
    int numLives;

    /**
     * Keep a reference to the parent game so we can switch screens
     * @param game
     */
    public BreakoutScreen(Game game, Difficulty difficulty)
    {
        this.game = game;
        this.difficulty = difficulty;
        this.numLives = difficulty.numLives;
    }

    @Override
    public void show()
    {
        renderer = new ShapeRenderer();
        viewport = new ExtendViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        platform = new Platform();
        ball = new Ball(platform, viewport, difficulty);

        blocks = new Blocks(difficulty);

        Gdx.input.setInputProcessor(ball);
    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height, true);
    }

    @Override
    public void render(float delta)
    {
        viewport.apply();
        renderer.setProjectionMatrix(viewport.getCamera().combined);

        // Perform entity updates
        platform.update(delta);
        ball.update(delta);

        checkBallIsOnScreen();
        checkBallCollisionWithBlocks();

        // Clear the screen to white - will be drawing a custom rectangle colour blend
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Handle our shape renderer batch
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        // draw the play area with a gradient rectangle
        renderer.rect(
                0,
                0,
                Constants.WORLD_WIDTH,
                Constants.WORLD_HEIGHT,
                Constants.BACKGROUND_COLOR_BOTTOM,
                Constants.BACKGROUND_COLOR_BOTTOM,
                Constants.BACKGROUND_COLOR_TOP,
                Constants.BACKGROUND_COLOR_TOP);

        // TODO: Render evey other game object that requires it
        platform.render(renderer);
        ball.render(renderer);
        blocks.render(renderer);



        renderer.end();
    }

    /**
     * Loops through all blocks onScreen and checks if the ball is colliding with them.
     * If it is, determines whether or not to bounce the ball along the X or Y axis
     *
     * This will probably be moved to the Blocks class when it gets written
      */
    private void checkBallCollisionWithBlocks()
    {
        for (int i = 0; i < blocks.blocks.size; i++) {
            Block block = blocks.blocks.get(i);

            if (ball.isColliding(block)) {
                ball.bounceOffBlock(block);
                blocks.blocks.removeIndex(i);
            }
        }
    }

    private void checkBallIsOnScreen()
    {
        if (ball.isOffScreen)
        {
            if (--numLives <= 0) {
                endGame();
            } else {
                ball.init();
            }
        }
    }

    private void endGame()
    {
        // TODO: Show the gameover screen, save high score
    }

    @Override
    public void hide()
    {
        renderer.dispose();
    }

    @Override
    public void dispose()
    {
        renderer.dispose();
    }
}
