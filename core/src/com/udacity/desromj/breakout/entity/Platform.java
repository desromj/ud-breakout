package com.udacity.desromj.breakout.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.udacity.desromj.breakout.util.Constants;

/**
 * Created by Quiv on 2015-12-27.
 */
public class Platform
{
    Vector2 position;
    DirectionMoved lastDirection;
    Rectangle hitRect;

    public Platform()
    {
        init();
    }

    public void init()
    {
        position = new Vector2(Constants.WORLD_WIDTH / 2, Constants.PLATFORM_BOTTOM_MARGIN);
        lastDirection = DirectionMoved.RIGHT;
        hitRect = new Rectangle();
    }

    public void update(float delta)
    {
        // Keyboard Controls
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            position.x -= Constants.PLATFORM_MAX_SPEED * delta;
            lastDirection = DirectionMoved.LEFT;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            position.x += Constants.PLATFORM_MAX_SPEED * delta;
            lastDirection = DirectionMoved.RIGHT;
        }

        // Accelerometer Controls need to be tested
        boolean hasTiltControl = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);

        if (hasTiltControl)
        {
            float xAxis = Gdx.input.getAccelerometerY();
            position.x += xAxis * Constants.PLATFORM_MAX_SPEED * delta;
        }

        // Constrain the platform to the play area
        if (position.x - Constants.PLATFORM_WIDTH / 2 <= 0.0f)
            position.x = Constants.PLATFORM_WIDTH / 2;
        if (position.x + Constants.PLATFORM_WIDTH / 2 >= Constants.WORLD_WIDTH)
            position.x = Constants.WORLD_WIDTH - Constants.PLATFORM_WIDTH / 2;

        // Set the hit rectangle for the ball

        /*
         * y values are: the top of the platform - allowance, top of the platform + ball radius + allowance
         * x values are: left edge of the platform, right edge of the platform
         */
        hitRect.set(
                this.position.x - Constants.PLATFORM_WIDTH / 2,
                this.position.y + Constants.PLATFORM_HEIGHT / 2 - Constants.BALL_HIT_ALLOWANCE,
                Constants.PLATFORM_WIDTH,
                Constants.BALL_RADIUS + Constants.BALL_HIT_ALLOWANCE
        );
    }

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

    public enum DirectionMoved
    {
        LEFT,
        RIGHT;
    }
}
