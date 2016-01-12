package com.udacity.desromj.breakout.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.udacity.desromj.breakout.BreakoutGame;
import com.udacity.desromj.breakout.entity.Ball;
import com.udacity.desromj.breakout.entity.Blocks;
import com.udacity.desromj.breakout.entity.Platform;
import com.udacity.desromj.breakout.entity.Powerup;
import com.udacity.desromj.breakout.entity.Powerups;
import com.udacity.desromj.breakout.entity.powerup.PowerupType;
import com.udacity.desromj.breakout.util.Constants;
import com.udacity.desromj.breakout.util.Difficulty;

/**
 * Created by Quiv on 2015-12-27.
 */
public class BreakoutScreen extends ScreenAdapter implements InputProcessor
{
    BreakoutGame game;

    ShapeRenderer renderer;
    Viewport viewport;

    // Gameplay Objects which need to be updated and rendered
    Platform platform;
    Array<Ball> balls;
    Difficulty difficulty;
    Blocks blocks;
    Powerups powerups;

    // Other game-specific variables
    int numLives;
    Long timeStarted;

    SpriteBatch batch;
    BitmapFont font;

    /**
     * Keep a reference to the parent game so we can switch screens
     * @param game
     */
    public BreakoutScreen(BreakoutGame game, Difficulty difficulty)
    {
        this.game = game;
        this.difficulty = difficulty;
        this.numLives = difficulty.getNumLives();
        this.timeStarted = TimeUtils.nanoTime();
        this.balls = new DelayedRemovalArray<Ball>();
        this.powerups = new Powerups();
    }

    @Override
    public void show()
    {
        renderer = new ShapeRenderer();
        viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        platform = new Platform(this);
        initFirstBall();
        blocks = new Blocks(difficulty);

        Gdx.input.setInputProcessor(this);

        batch = new SpriteBatch();
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

        for (Ball ball: balls)
            ball.update(delta);

        powerups.update(delta);
        powerups.checkCollision(platform, this);

        // Check if we win
        if (!blocks.hasBlocksRemaining())
            endGame(true);

        // Otherwise, continue with updates
        checkBallIsOnScreen();

        for (Ball ball: balls)
            blocks.checkCollision(ball, game, this);

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

        for (Ball ball: balls)
            ball.render(renderer);

        blocks.render(renderer);
        powerups.renderShapes(renderer);

        renderer.end();

        // Start text and sprite rendering
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        // Game Element sprites
        font.getData().setScale(Constants.POWERUP_FONT_SCALE);
        font.setColor(Constants.POWERUP_SECONDARY_COLOR);

        powerups.renderSprites(batch);

        // GUI Sprites
        font.getData().setScale(Constants.INGAME_FONT_SCALE);
        font.setColor(Constants.TEXT_COLOR);

        // Check the time remaining in the game due to difficulty
        float elapsedTime = MathUtils.nanoToSec * (TimeUtils.nanoTime() - timeStarted);
        float timeLeft = difficulty.getTimeLimitInMinutes() * 60 - elapsedTime;

        // End the game if our time is too long. Otherwise, proceed to display it
        if (timeLeft < 0.0f)
            endGame(false);

        int minutesLeft = (int) (timeLeft / 60);
        int secondsLeft = (int) (60 - elapsedTime % 60);

        String secondsPrintable = (String.valueOf(secondsLeft).length() == 1) ? "0" + secondsLeft : String.valueOf(secondsLeft);

        font.draw(
                batch,
                "Time Remaining: " + minutesLeft + ":" + secondsPrintable,
                Constants.WORLD_WIDTH / 2,
                Constants.WORLD_HEIGHT - Constants.TEXT_MARGIN,
                0,
                Align.center,
                false
        );

        // Draw the rest of the GUI
        font.draw(
                batch,
                "Score: " + game.score.getScore() + "\n" +
                        "Top Score: " + game.score.getTopScore(),
                Constants.TEXT_MARGIN,
                Constants.WORLD_HEIGHT - Constants.TEXT_MARGIN,
                0,
                Align.topLeft,
                false
        );

        font.draw(
                batch,
                "Lives: " + numLives + "\n" +
                        "Current Combo: " + game.score.getCurrentCombo() + "\n" +
                        "Combo Color: " + game.score.getLastComboLabel(),
                Constants.WORLD_WIDTH - Constants.TEXT_MARGIN,
                Constants.WORLD_HEIGHT - Constants.TEXT_MARGIN,
                0,
                Align.topRight,
                false
        );

        batch.end();
    }

    private void checkBallIsOnScreen()
    {
        for (int i = 0; i < balls.size; i++)
            if (balls.get(i).isOffScreen())
                balls.removeIndex(i);

        if (balls.size <= 0)
        {
            initFirstBall();
            game.score.resetCombo();

            if (--numLives <= 0)
                endGame(false);
        }
    }

    private void initFirstBall()
    {
        balls.clear();
        balls.add(new Ball(this));
        
        powerups.init();
    }

    public void spawnRandomPowerup(Vector2 position)
    {
        powerups.addNewRandomPowerup(position);
    }

    private void endGame(boolean win)
    {
        if (win) {
            game.score.addScore(difficulty.getClearBonus());
            game.showWinScreen();
        } else {
            game.showGameOverScreen();
        }
    }

    public boolean powerupTypeIsActive(PowerupType type)
    {
        return powerups.powerupTypeIsActive(type);
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
        batch.dispose();
        font.dispose();
    }

    public Array<Ball> getBalls()
    {
        return this.balls;
    }

    public Platform getLaunchPlatform()
    {
        return platform;
    }

    public Viewport getViewport()
    {
        return viewport;
    }

    public Difficulty getDifficulty()
    {
        return difficulty;
    }

    /*
        InputProcessor methods
     */

    @Override
    public boolean keyDown(int keycode)
    {
        for (Ball ball: balls) {
            if (ball.getMoveState() == Ball.MoveState.MOVING)
                continue;

            if (keycode == Input.Keys.SPACE) {
                ball.launch(
                        new Vector2(
                                (platform.getLastDirection() == Platform.DirectionMoved.RIGHT) ? 1 : -1,
                                1));
            }
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        for (Ball ball: balls)
        {
            if (ball.getMoveState() == Ball.MoveState.MOVING)
                continue;

            // allow launching the ball through touch controls
            Vector2 touchPos = viewport.unproject(new Vector2(screenX, screenY));
            Vector2 diff = touchPos.sub(ball.getPosition());

            // Y value must ve positive to respond to the event
            if (diff.y > 0)
                ball.launch(diff);
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
