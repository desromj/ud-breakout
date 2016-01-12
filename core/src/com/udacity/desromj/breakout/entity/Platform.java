package com.udacity.desromj.breakout.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.udacity.desromj.breakout.entity.powerup.PowerupType;
import com.udacity.desromj.breakout.screen.BreakoutScreen;
import com.udacity.desromj.breakout.util.Constants;
import com.udacity.desromj.breakout.util.Difficulty;

/**
 * Created by Quiv on 2015-12-27.
 */
public class Platform
{
    public static final String TAG = Platform.class.getSimpleName();

    Vector2 position;
    DirectionMoved lastDirection;
    Rectangle hitRect;
    Difficulty difficulty;
    BreakoutScreen screen;

    public Platform(BreakoutScreen screen)
    {
        init(screen);
    }

    public void init(BreakoutScreen screen)
    {
        this.screen = screen;
        position = new Vector2(Constants.WORLD_WIDTH / 2, Constants.PLATFORM_BOTTOM_MARGIN);
        lastDirection = DirectionMoved.RIGHT;
        hitRect = new Rectangle();
        this.difficulty = screen.getDifficulty();
    }

    public void update(float delta)
    {
        // Keyboard Controls
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            position.x -= Constants.PLATFORM_MAX_SPEED * delta * difficulty.getSpeedMultiplier();
            lastDirection = DirectionMoved.LEFT;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            position.x += Constants.PLATFORM_MAX_SPEED * delta * difficulty.getSpeedMultiplier();
            lastDirection = DirectionMoved.RIGHT;
        }

        // Accelerometer Controls
        boolean hasTiltControl = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);

        if (hasTiltControl)
        {
            float xAxis = Gdx.input.getAccelerometerY();
            position.x += xAxis * Constants.PLATFORM_MAX_SPEED * delta;
        }

        // Constrain the platform to the play area
        float
                xOffset = Constants.PLATFORM_WIDTH / 2,
                xWidth = Constants.PLATFORM_WIDTH;

        if (screen.powerupTypeIsActive(PowerupType.WIDER_PADDLE))
        {
            xOffset *= Constants.POWERUP_WIDER_PADDLE_WIDTH_MODIFIER;
            xWidth *= Constants.POWERUP_WIDER_PADDLE_WIDTH_MODIFIER;
        }

        if (position.x - xOffset <= 0.0f)
            position.x = xOffset;
        if (position.x + xOffset >= Constants.WORLD_WIDTH)
            position.x = Constants.WORLD_WIDTH - xOffset;

        // Set the hit rectangle for the ball

        /*
         * y values are: the top of the platform - allowance, top of the platform + ball radius + allowance
         * x values are: left edge of the platform, right edge of the platform
         */
        hitRect.set(
                this.position.x - xOffset,
                this.position.y + Constants.BALL_HIT_ALLOWANCE,
                xWidth,
                Constants.PLATFORM_HEIGHT / 2 + Constants.BALL_RADIUS + Constants.BALL_HIT_ALLOWANCE
        );
    }

    public void render(ShapeRenderer renderer)
    {
        renderer.setColor(Constants.PLATFORM_COLOR);

        float
                xOffset = Constants.PLATFORM_WIDTH / 2,
                xWidth = Constants.PLATFORM_WIDTH;

        // Gdx.app.log(TAG, "Wider Paddle Active: " + screen.powerupTypeIsActive(PowerupType.WIDER_PADDLE));

        if (screen.powerupTypeIsActive(PowerupType.WIDER_PADDLE))
        {
            xOffset *= Constants.POWERUP_WIDER_PADDLE_WIDTH_MODIFIER;
            xWidth *= Constants.POWERUP_WIDER_PADDLE_WIDTH_MODIFIER;
        }

        renderer.rect(
                position.x - xOffset,
                position.y - (Constants.PLATFORM_HEIGHT / 2),
                xWidth,
                Constants.PLATFORM_HEIGHT
        );
    }

    public Rectangle getHitRectangle() { return this.hitRect; }
    public DirectionMoved getLastDirection()
    {
        return this.lastDirection;
    }

    public enum DirectionMoved
    {
        LEFT,
        RIGHT;
    }
}
