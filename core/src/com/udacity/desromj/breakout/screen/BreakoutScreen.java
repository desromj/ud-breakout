package com.udacity.desromj.breakout.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.udacity.desromj.breakout.entity.Platform;
import com.udacity.desromj.breakout.util.Constants;

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

    /**
     * Keep a reference to the parent game so we can switch screens
     * @param game
     */
    public BreakoutScreen(Game game)
    {
        this.game = game;
        platform = new Platform();
    }

    @Override
    public void show()
    {
        renderer = new ShapeRenderer();
        viewport = new ExtendViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
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


        renderer.end();
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
