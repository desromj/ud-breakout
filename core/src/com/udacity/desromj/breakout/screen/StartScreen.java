package com.udacity.desromj.breakout.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
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

    Rectangle
        btnEasy,
        btnMedium,
        btnHard,
        btnInsane;

    public StartScreen(BreakoutGame game)
    {
        this.game = game;
        viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
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

        renderer.rect(btnEasy.x, btnEasy.y, btnEasy.width, btnEasy.height);
        renderer.rect(btnMedium.x, btnMedium.y, btnMedium.width, btnMedium.height);
        renderer.rect(btnHard.x, btnHard.y, btnHard.width, btnHard.height);
        renderer.rect(btnInsane.x, btnInsane.y, btnInsane.width, btnInsane.height);

        renderer.end();

        // Text drawing

        // Title
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        font.setColor(Constants.TEXT_COLOR);

        font.getData().setScale(Constants.TITLE_FONT_SCALE);
        font.draw(
                batch,
                Constants.GAME_TITLE,
                Constants.WORLD_WIDTH / 2,
                Constants.WORLD_HEIGHT / 1.1f,
                0,
                Align.center,
                false
        );

        // Description for buttons
        font.getData().setScale(Constants.INGAME_FONT_SCALE);
        font.draw(
                batch,
                "Click on a button to play that difficulty!",
                Constants.WORLD_WIDTH / 2,
                Constants.WORLD_HEIGHT - (Constants.WORLD_HEIGHT / 1.1f),
                0,
                Align.center,
                false
        );

        // Button Labels (difficulty labels)
        font.getData().setScale(Constants.START_BUTTON_FONT_SCALE);
        font.draw(
                batch,
                Difficulty.EASY.getLabel(),
                btnEasy.x + btnEasy.width / 2,
                btnEasy.y + btnEasy.height / 2,
                0,
                Align.center,
                false
        );
        font.draw(
                batch,
                Difficulty.MEDIUM.getLabel(),
                btnMedium.x + btnMedium.width / 2,
                btnMedium.y + btnMedium.height / 2,
                0,
                Align.center,
                false
        );
        font.draw(
                batch,
                Difficulty.HARD.getLabel(),
                btnHard.x + btnHard.width / 2,
                btnHard.y + btnHard.height / 2,
                0,
                Align.center,
                false
        );
        font.draw(
                batch,
                Difficulty.INSANE.getLabel(),
                btnInsane.x + btnInsane.width / 2,
                btnInsane.y + btnInsane.height / 2,
                0,
                Align.center,
                false
        );

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
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Gdx.input.setInputProcessor(this);

        initRectangles();
    }

    private void initRectangles()
    {
        btnEasy = new Rectangle(
                Constants.WORLD_WIDTH / 5 * 2 - Constants.START_BUTTON_WIDTH,
                Constants.WORLD_HEIGHT / 5 * 4 - Constants.START_BUTTON_HEIGHT,
                Constants.START_BUTTON_WIDTH,
                Constants.START_BUTTON_HEIGHT
        );
        btnMedium = new Rectangle(
                Constants.WORLD_WIDTH / 5 * 4 - Constants.START_BUTTON_WIDTH,
                Constants.WORLD_HEIGHT / 5 * 4 - Constants.START_BUTTON_HEIGHT,
                Constants.START_BUTTON_WIDTH,
                Constants.START_BUTTON_HEIGHT
        );
        btnHard = new Rectangle(
                Constants.WORLD_WIDTH / 5 * 2 - Constants.START_BUTTON_WIDTH,
                Constants.WORLD_HEIGHT / 5 * 2 - Constants.START_BUTTON_HEIGHT,
                Constants.START_BUTTON_WIDTH,
                Constants.START_BUTTON_HEIGHT
        );
        btnInsane = new Rectangle(
                Constants.WORLD_WIDTH / 5 * 4 - Constants.START_BUTTON_WIDTH,
                Constants.WORLD_HEIGHT / 5 * 2 - Constants.START_BUTTON_HEIGHT,
                Constants.START_BUTTON_WIDTH,
                Constants.START_BUTTON_HEIGHT
        );
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
    public boolean keyDown(int keycode){
        if (keycode == Input.Keys.ESCAPE)
            Gdx.app.exit();

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
        Vector2 touchPos = viewport.unproject(new Vector2(screenX, screenY));
        checkForPresses(touchPos);
        return true;
    }

    public void checkForPresses(Vector2 unprojectedPos)
    {
        if (btnEasy.contains(unprojectedPos)) {
            game.showBreakoutScreen(Difficulty.EASY);
        } else if (btnMedium.contains(unprojectedPos)) {
            game.showBreakoutScreen(Difficulty.MEDIUM);
        } else if (btnHard.contains(unprojectedPos)) {
            game.showBreakoutScreen(Difficulty.HARD);
        } else if (btnInsane.contains(unprojectedPos)) {
            game.showBreakoutScreen(Difficulty.INSANE);
        }

        // Reset the score when we begin a game
        this.game.score.init();
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
