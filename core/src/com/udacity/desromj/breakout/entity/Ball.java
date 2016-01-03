package com.udacity.desromj.breakout.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g3d.particles.values.MeshSpawnShapeValue;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.udacity.desromj.breakout.util.Constants;
import com.udacity.desromj.breakout.util.Difficulty;

/**
 * Created by Quiv on 2015-12-27.
 */
public class Ball extends InputAdapter
{
    public static final String TAG = Ball.class.getName();

    Vector2 position, velocity;
    Platform launchPlatform;
    MoveState moveState;
    Difficulty difficulty;

    public boolean isOffScreen;

    Viewport viewport;

    public Ball(Platform launchPlatform, Viewport viewport, Difficulty difficulty)
    {
        this.launchPlatform = launchPlatform;
        this.viewport = viewport;
        this.difficulty = difficulty;
        init();
    }

    public void init()
    {
        moveState = MoveState.HELD;
        position = new Vector2();
        velocity = new Vector2();
        isOffScreen = false;
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

        // Constrain the ball to the play area and make it bounce, except for the bottom
        constrainToPlayArea();
        bounceOffPlatform();
    }

    private void bounceOffPlatform()
    {
        /*
         * If we are inside the hit rectangle of the platform, detect the angle between ball and platform
         * ONLY IF: The ball is moving, the ball is moving DOWNWARD, and it is within the hit retangle
         */
        if (moveState == MoveState.MOVING && velocity.y <= 0 && launchPlatform.hitRect.contains(position))
        {
            Vector2 angle = new Vector2(position.x - launchPlatform.position.x, position.y - launchPlatform.position.y);
            launch(angle);
        }
    }

    private void constrainToPlayArea()
    {
        // Right edge
        if (position.x + Constants.BALL_RADIUS >= Constants.WORLD_WIDTH) {
            position.x = Constants.WORLD_WIDTH - Constants.BALL_RADIUS;
            bounceX();
        }

        // Left edge
        if (position.x - Constants.BALL_RADIUS <= 0.0f) {
            position.x = Constants.BALL_RADIUS;
            bounceX();
        }

        // Top edge
        if (position.y + Constants.BALL_RADIUS >= Constants.WORLD_HEIGHT) {
            position.y = Constants.WORLD_HEIGHT - Constants.BALL_RADIUS;
            bounceY();
        }

        // Check the bottom edge - if the ball is offscreen
        isOffScreen = (position.y <= 0);
    }

    // Bounce off the block in the desired direction - bounces depending on whether it hits from the x or y axis first
    public void bounceOffBlock(Block block)
    {
        // Check the hit angle of the ball against the block
        float hitAngle = (float) (Math.atan2(
                block.position.y - this.position.y,
                block.position.x - this.position.x
        ) * 180.0f / Math.PI);

        hitAngle = Math.abs(hitAngle % 180.0f);

        Gdx.app.debug(TAG, "Hit Angle: " + hitAngle);

        // if Bounce angle is 45 degrees, bounce Y when between 45-135. Bounce x between 0-45 and 135-180
        if (hitAngle < Constants.BOUNCE_ANGLE_DEGREES || hitAngle > 180.0f - Constants.BOUNCE_ANGLE_DEGREES)
            bounceX();
        else
            bounceY();
    }

    /**
     * Checks if the ball is colliding with the passed block
     * @param block
     * @return
     */
    public boolean isColliding(Block block)
    {
        if (Math.abs(this.position.x - block.position.x) < Constants.BALL_RADIUS + Constants.BALL_HIT_ALLOWANCE + Constants.BLOCK_WIDTH / 2
            && Math.abs(this.position.y - block.position.y) < Constants.BALL_RADIUS + Constants.BALL_HIT_ALLOWANCE + Constants.BLOCK_HEIGHT / 2)
        {
            return true;
        }

        return false;
    }

    public void bounceX()
    {
        velocity.x = -velocity.x;
    }

    public void bounceY()
    {
        velocity.y = -velocity.y;
    }

    public void render(ShapeRenderer renderer)
    {
        renderer.setColor(Constants.BALL_COLOR);
        renderer.circle(position.x, position.y, Constants.BALL_RADIUS);
    }

    public void launch(Vector2 target)
    {
        moveState = MoveState.MOVING;
        velocity.x = target.nor().x * Constants.BALL_SPEED * difficulty.getSpeedMultiplier();
        velocity.y = target.nor().y * Constants.BALL_SPEED * difficulty.getSpeedMultiplier();
    }

    @Override
    public boolean keyDown(int keycode)
    {
        if (moveState == MoveState.MOVING)
            return true;

        if (keycode == Input.Keys.SPACE) {
            launch(
                    new Vector2(
                            (launchPlatform.lastDirection == Platform.DirectionMoved.RIGHT) ? 1 : -1,
                            1));
        }

        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        if (moveState == MoveState.MOVING)
            return true;

        // allow launching the ball through touch controls
        Vector2 touchPos = viewport.unproject(new Vector2(screenX, screenY));
        Vector2 diff = touchPos.sub(position);

        // Y value must ve positive to respond to the event
        if (diff.y > 0)
            launch(diff);

        return true;
    }

    public enum MoveState
    {
        HELD,
        MOVING;
    }
}
