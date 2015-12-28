package com.udacity.desromj.breakout.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.udacity.desromj.breakout.util.Constants;

/**
 * Created by Quiv on 2015-12-27.
 */
public class Platform
{
    Vector2 position;

    public Platform()
    {
        init();
    }

    public void init()
    {
        position = new Vector2(Constants.WORLD_WIDTH / 2, Constants.PLATFORM_BOTTOM_MARGIN);
    }

    // TODO: Check for input and update the platform here
    public void update(float delta)
    {

    }

    // TODO: Render the platform
    public void render(ShapeRenderer renderer)
    {
        renderer.setColor(Constants.PLATFORM_COLOR);

        renderer.rect(
                position.x - (Constants.PLATFORM_WIDTH / 2),
                position.y - (Constants.PLATFORM_HEIGHT / 2),
                Constants.PLATFORM_WIDTH,
                Constants.PLATFORM_HEIGHT
        );
    }
}
