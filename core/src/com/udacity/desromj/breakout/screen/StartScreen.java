package com.udacity.desromj.breakout.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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
import com.udacity.desromj.breakout.util.Constants;

/**
 * Created by Quiv on 2015-12-27.
 */
public class StartScreen extends ScreenAdapter
{
    BreakoutGame game;

    ShapeRenderer renderer;
    SpriteBatch batch;
    BitmapFont font;
    Viewport viewport;

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


        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show() {
        renderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(4.0f);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
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
}
