package com.udacity.desromj.breakout.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.udacity.desromj.breakout.BreakoutGame;
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
    BreakoutGame game;

    ShapeRenderer renderer;
    Viewport viewport;

    // Gameplay Objects which need to be updated and rendered
    Platform platform;
    Ball ball;
    Difficulty difficulty;
    Blocks blocks;

    // Other game-specific variables
    int numLives;
    Long timeStarted;

    SpriteBatch spriteBatch;
    BitmapFont font;

    /**
     * Keep a reference to the parent game so we can switch screens
     * @param game
     */
    public BreakoutScreen(BreakoutGame game, Difficulty difficulty)
    {
        this.game = game;
        this.difficulty = difficulty;
        this.numLives = difficulty.numLives;
        this.timeStarted = TimeUtils.nanoTime();
    }

    @Override
    public void show()
    {
        renderer = new ShapeRenderer();
        viewport = new ExtendViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        platform = new Platform(difficulty);
        ball = new Ball(platform, viewport, difficulty);
        blocks = new Blocks(difficulty);

        Gdx.input.setInputProcessor(ball);

        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
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

        // Check if we win
        if (blocks.blocks.size <= 0)
            endGame(true);

        // Otherwise, continue with updates
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

        // Render evey other game object that requires it
        platform.render(renderer);
        ball.render(renderer);
        blocks.render(renderer);

        renderer.end();

        // Start text and sprite rendering
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        font.getData().setScale(Constants.INGAME_FONT_SCALE);
        font.setColor(Constants.TEXT_COLOR);

        // Check the time remaining in the game due to difficulty
        float elapsedTime = MathUtils.nanoToSec * (TimeUtils.nanoTime() - timeStarted);
        float timeLeft = difficulty.timeLimitInMinutes * 60 - elapsedTime;

        // End the game if our time is too long. Otherwise, proceed to display it
        if (timeLeft < 0.0f)
            endGame(false);

        int minutesLeft = (int) (timeLeft / 60);
        int secondsLeft = (int) (60 - elapsedTime % 60);

        String secondsPrintable = (String.valueOf(secondsLeft).length() == 1) ? "0" + secondsLeft : String.valueOf(secondsLeft);

        font.draw(
                spriteBatch,
                "Time Remaining: " + minutesLeft + ":" + secondsPrintable,
                Constants.WORLD_WIDTH / 2,
                Constants.WORLD_HEIGHT - Constants.TEXT_MARGIN,
                0,
                Align.center,
                false
        );

        // Draw the rest of the GUI
        font.draw(
                spriteBatch,
                "Score: " + game.score.score + "\n" +
                        "Top Score: " + game.score.topScore,
                Constants.TEXT_MARGIN,
                Constants.WORLD_HEIGHT - Constants.TEXT_MARGIN,
                0,
                Align.topLeft,
                false
        );

        font.draw(
                spriteBatch,
                "Lives: " + numLives + "\n" +
                        "Current Combo: " + game.score.currentCombo + "\n" +
                        "Combo Color: " + game.score.getLastComboLabel(),
                Constants.WORLD_WIDTH - Constants.TEXT_MARGIN,
                Constants.WORLD_HEIGHT - Constants.TEXT_MARGIN,
                0,
                Align.topRight,
                false
        );

        spriteBatch.end();
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

            if (ball.isColliding(block))
            {
                ball.bounceOffBlock(block);
                game.score.addScore(block);

                blocks.blocks.removeIndex(i);
            }
        }
    }

    private void checkBallIsOnScreen()
    {
        if (ball.isOffScreen)
        {
            if (--numLives <= 0) {
                endGame(false);
            } else {
                ball.init();
                game.score.currentCombo = 0;
            }
        }
    }

    private void endGame(boolean win)
    {
        if (win) {
            game.score.addScore(difficulty.clearBonus);
            game.showWinScreen();
        } else {
            game.showGameOverScreen();
        }
    }

    @Override
    public void hide()
    {
        // TODO: Used to dispose renderers here, which was causing errors due to an error:
        // Cannot use offsets when Array Buffer Object is disabled
    }

    @Override
    public void dispose()
    {
        renderer.dispose();
        spriteBatch.dispose();
        font.dispose();
    }
}
