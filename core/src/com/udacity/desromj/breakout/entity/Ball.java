package com.udacity.desromj.breakout.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.udacity.desromj.breakout.util.Constants;

/**
 * Created by Quiv on 2015-12-27.
 */
public class Ball
{
    Vector2 position, velocity;
    Platform launchPlatform;
    MoveState moveState;

    public Ball(Platform launchPlatform)
    {
        this.launchPlatform = launchPlatform;
        init();
    }

    public void init()
    {
        moveState = MoveState.HELD;
        position = new Vector2();
        velocity = new Vector2();
    }

    public void update(float delta)
    {
        switch (moveState)
        {
            case HELD:

                this.position.x = launchPlatform.position.x;
                this.position.y =
                        launchPlatform.position.y
                        + Constants.PLATFORM_HEIGHT / 2
                        + Constants.BALL_RADIUS;

                break;

            case MOVING:

                this.position.x += this.velocity.x * delta;
                this.position.y += this.velocity.y * delta;

            default:

                break;
        }
    }

    public void render(ShapeRenderer renderer)
    {
        renderer.setColor(Constants.BALL_COLOR);
        renderer.circle(position.x, position.y, Constants.BALL_RADIUS);
    }

    public enum MoveState
    {
        HELD,
        MOVING;
    }
}
