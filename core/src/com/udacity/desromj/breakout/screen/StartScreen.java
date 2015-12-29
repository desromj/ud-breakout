package com.udacity.desromj.breakout.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.udacity.desromj.breakout.BreakoutGame;
import com.udacity.desromj.breakout.util.Constants;
import com.udacity.desromj.breakout.util.Difficulty;

/**
 * Created by Quiv on 2015-12-27.
 */
public class StartScreen extends ScreenAdapter implements InputProcessor
{
    BreakoutGame game;

    ShapeRenderer renderer;
    SpriteBatch batch;
    BitmapFont font;
    Viewport viewport;

    private float[] centreRatios = new float[] {
            2.0f/5.0f, 2.0f/5.0f,
            4.0f/5.0f, 2.0f/5.0f,
            2.0f/5.0f, 4.0f/5.0f,
            4.0f/5.0f, 4.0f/5.0f
    };

    public StartScreen(BreakoutGame game)
    {
        this.game = game;
        viewport = new ExtendViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
    }

    @Override
    public void render(float delta)
    {
        viewport.apply();

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setProjectionMatrix(viewport.getCamera().combined);

        // Shape rendering

        // Title
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        renderer.setColor(Color.PURPLE);
        renderer.rect(
                0,
                0,
                Constants.WORLD_WIDTH,
                Constants.WORLD_HEIGHT
        );

        renderer.setColor(Color.BLACK);
        renderer.rect(
                Constants.TITLE_BORDER,
                Constants.TITLE_BORDER,
                Constants.WORLD_WIDTH - 2 * Constants.TITLE_BORDER,
                Constants.WORLD_HEIGHT - 2 * Constants.TITLE_BORDER
        );

        // Start Buttons
        renderer.setColor(Constants.START_BUTTON_COLOR);

        for (int i = 0; i < centreRatios.length; i += 2)
        {
            renderer.rect(
                    centreRatios[i] * Constants.WORLD_WIDTH - Constants.START_BUTTON_WIDTH / 2,
                    centreRatios[i + 1] * Constants.WORLD_HEIGHT - Constants.START_BUTTON_HEIGHT / 2,
                    Constants.START_BUTTON_WIDTH,
                    Constants.START_BUTTON_HEIGHT
            );
        }

        renderer.end();

        // Text drawing

        // Title
        batch.begin();
        batch.setColor(Constants.TEXT_COLOR);

        font.draw(
                batch,
                Constants.GAME_TITLE,
                Constants.WORLD_WIDTH / 2,
                Constants.WORLD_HEIGHT / 1.2f,
                0,
                Align.center,
                false
        );

        // Button Labels (difficulty labels)
        for (int i = 0; i < centreRatios.length; i += 2)
        {
            font.draw(
                    batch,
                    Difficulty.values()[i / 2].label,
                    centreRatios[i] * Constants.WORLD_WIDTH - Constants.START_BUTTON_WIDTH / 2,
                    centreRatios[i + 1] * Constants.WORLD_HEIGHT - Constants.START_BUTTON_HEIGHT / 2,
                    0,
                    Align.center,
                    false
            );
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void show() {
        renderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(2.0f);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void hide() {
        renderer.dispose();
        batch.dispose();
        font.dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        batch.dispose();
        font.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
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
        Vector2 touchPos = viewport.unproject(new Vector2(screenX, screenY));
        checkForPresses(touchPos);
        return true;
    }

    public void checkForPresses(Vector2 unprojectedPos)
    {
        for (int i = 0; i < centreRatios.length; i += 2)
        {
            Rectangle rect = new Rectangle(
                    centreRatios[i] * Constants.WORLD_WIDTH - Constants.START_BUTTON_WIDTH / 2,
                    centreRatios[i + 1] * Constants.WORLD_HEIGHT - Constants.START_BUTTON_HEIGHT / 2,
                    Constants.START_BUTTON_WIDTH,
                    Constants.START_BUTTON_HEIGHT
            );

            if (rect.contains(unprojectedPos))
            {
                Difficulty difficulty = Difficulty.values()[i / 2];
                game.showBreakoutScreen(difficulty);
                return;
            }
        }
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
