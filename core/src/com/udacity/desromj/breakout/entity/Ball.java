package com.udacity.desromj.breakout.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.udacity.desromj.breakout.util.Constants;

/**
 * Created by Quiv on 2015-12-27.
 */
public class Ball extends InputAdapter
{
    public static final String TAG = Ball.class.getName();

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

    public void launch(Vector2 target)
    {
        moveState = MoveState.MOVING;
        velocity.x = target.nor().x * Constants.BALL_SPEED;
        velocity.y = target.nor().y * Constants.BALL_SPEED;
    }

    @Override
    public boolean keyDown(int keycode)
    {
        if (moveState == MoveState.MOVING)
            return true;

        if (keycode == Input.Keys.SPACE) {
            launch(new Vector2(1, 1));
        }
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        if (moveState == MoveState.MOVING)
            return true;

        // TODO: allow launching the ball through touch controls
        return false;
    }

    public enum MoveState
    {
        HELD,
        MOVING;
    }
}
