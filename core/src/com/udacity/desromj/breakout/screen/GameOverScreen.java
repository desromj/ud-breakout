package com.udacity.desromj.breakout.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.udacity.desromj.breakout.BreakoutGame;
import com.udacity.desromj.breakout.entity.Score;
import com.udacity.desromj.breakout.util.Constants;

/**
 * Created by Quiv on 2015-12-27.
 */
public class GameOverScreen extends ScreenAdapter implements InputProcessor
{
    BreakoutGame game;

    ShapeRenderer renderer;
    SpriteBatch batch;
    BitmapFont font;
    Viewport viewport;

    public GameOverScreen(BreakoutGame game)
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
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        renderer.rect(
                0,
                0,
                Constants.WORLD_WIDTH,
                Constants.WORLD_HEIGHT,
                Color.FIREBRICK,
                Color.RED,
                Color.FIREBRICK,
                Color.RED
        );

        renderer.end();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        if (game.score.score >= game.score.topScore)
        {
            font.setColor(Constants.TOP_SCORE_TEXT_COLOR);
            font.draw(
                    batch,
                    "New top score!\nCongratulations!!!",
                    Constants.WORLD_WIDTH / 2,
                    Constants.WORLD_HEIGHT / 1.2f,
                    0,
                    Align.center,
                    false
            );
        }

        font.setColor(Constants.LOSE_TEXT_COLOR);
        font.draw(
                batch,
                "You Lose!!!\nYour Score: " + game.score.score + "\nTop Score: " + game.score.topScore +
                    "\n\nTouch the Screen to play again!",
                Constants.WORLD_WIDTH / 2,
                Constants.WORLD_HEIGHT / 2,
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
        font.getData().setScale(2.5f);
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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        game.showStartScreen();
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
