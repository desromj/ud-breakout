package com.udacity.desromj.breakout.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.udacity.desromj.breakout.entity.powerup.PowerupType;
import com.udacity.desromj.breakout.screen.BreakoutScreen;
import com.udacity.desromj.breakout.util.Constants;

/**
 * Created by Quiv on 2015-12-27.
 */
public class Ball
{
    public static final String TAG = Ball.class.getName();

    Vector2 lastFramePosition, position, velocity, nextLaunchAngle;

    float stickyXOffset;
    MoveState moveState;
    BreakoutScreen screen;

    boolean isOffScreen;

    Viewport viewport;

    public Ball(BreakoutScreen screen)
    {
        this.screen = screen;
        init();
    }

    public void init()
    {
        moveState = MoveState.HELD;
        lastFramePosition = new Vector2();
        position = new Vector2();
        velocity = new Vector2();
        nextLaunchAngle = new Vector2();
        isOffScreen = false;
        stickyXOffset = 0.0f;
    }

    public void update(float delta)
    {
        lastFramePosition.x = this.position.x;
        lastFramePosition.y = this.position.y;

        switch (moveState)
        {
            case HELD:

                Platform launchPlatform = screen.getLaunchPlatform();

                this.position.x = launchPlatform.position.x + stickyXOffset;
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
        Platform launchPlatform = screen.getLaunchPlatform();

        /*
         * If we are inside the hit rectangle of the platform, detect the angle between ball and platform
         * ONLY IF: The ball is moving, the ball is moving DOWNWARD, and it is within the hit retangle
         */
        if (moveState == MoveState.MOVING && velocity.y <= 0 && launchPlatform.hitRect.contains(position))
        {
            nextLaunchAngle.set(position.x - launchPlatform.position.x, position.y - launchPlatform.position.y);

            // Only launch if we hit at the minimum allowed angle - to prevent 179.99 degree hits which take 5 minutes just to climb the screen
            float degrees = (float) (Math.atan2(nextLaunchAngle.y, nextLaunchAngle.x) * 180.0f / Math.PI);
            degrees %= 180.0f;

            if (degrees > Constants.MINIMUM_HIT_ANGLE_DEGREES && degrees <= 180.0f - Constants.MINIMUM_HIT_ANGLE_DEGREES)
            {
                if (screen.powerupTypeIsActive(PowerupType.STICKY_PADDLE)) {
                    stickyXOffset = this.position.x - launchPlatform.position.x;
                    moveState = MoveState.HELD;
                } else {
                    launch(nextLaunchAngle);
                }
            }
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

    /**
     * Checks if the ball is colliding with the passed block
     * @param block
     * @return
     */
    public boolean collided(Block block)
    {
        float
            collideWidth = Constants.BALL_RADIUS + Constants.BLOCK_WIDTH / 2,
            collideHeight = Constants.BALL_RADIUS + Constants.BLOCK_HEIGHT / 2;

        // If we are colliding...
        if (Math.abs(this.position.x - block.position.x) <= collideWidth && Math.abs(this.position.y - block.position.y) <= collideHeight)
        {
            /*
                Move the ball to the outside of the block based on if it's above, below, left, or right.
                This prevents infinite collisions and chain reactions between blocks.
                Use the last frame's position to tell if it just entered the block this cycle
             */

            // Above
            if (this.lastFramePosition.y - block.position.y > collideHeight &&
                    this.position.y - block.position.y <= collideHeight && this.position.y - block.position.y >= 0)
            {
                this.position.y = block.position.y + collideHeight;

                if (!screen.powerupTypeIsActive(PowerupType.UNSTOPPABALL)
                        && this.velocity.y <= 0.0f)
                    bounceY();
            }

            // Below
            if (block.position.y - this.lastFramePosition.y > collideHeight &&
                    block.position.y - this.position.y <= collideHeight && block.position.y - this.position.y >= 0)
            {
                this.position.y = block.position.y - collideHeight;

                if (!screen.powerupTypeIsActive(PowerupType.UNSTOPPABALL)
                        && this.velocity.y >= 0.0f)
                    bounceY();
            }

            // Left
            if (block.position.x - this.lastFramePosition.x > collideWidth &&
                    block.position.x - this.position.x <= collideWidth && block.position.x - this.position.x >= 0)
            {
                this.position.x = block.position.x - collideWidth;

                if (!screen.powerupTypeIsActive(PowerupType.UNSTOPPABALL)
                        && this.velocity.x >= 0.0f)
                    bounceX();
            }

            // Right
            if (this.lastFramePosition.x - block.position.x > collideWidth &&
                    this.position.x - block.position.x <= collideWidth && this.position.x - block.position.x >= 0)
            {
                this.position.x = block.position.x + collideWidth;

                if (!screen.powerupTypeIsActive(PowerupType.UNSTOPPABALL)
                        && this.velocity.x <= 0.0f)
                    bounceX();
            }

            // Finally, return true that we're colliding
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

    public void launchAtNextAngle()
    {
        launch(this.nextLaunchAngle);
    }

    public void launch(Vector2 target)
    {
        this.stickyXOffset = 0.0f;
        moveState = MoveState.MOVING;
        velocity.x = target.nor().x * Constants.BALL_SPEED * screen.getDifficulty().getSpeedMultiplier();
        velocity.y = target.nor().y * Constants.BALL_SPEED * screen.getDifficulty().getSpeedMultiplier();
    }

    public Vector2 getPosition() { return this.position; }
    public Vector2 getVelocity() { return this.velocity; }

    public void setVelocity(Vector2 newVel)
    {
        velocity.x = newVel.nor().x * Constants.BALL_SPEED * screen.getDifficulty().getSpeedMultiplier();;
        velocity.y = newVel.nor().y * Constants.BALL_SPEED * screen.getDifficulty().getSpeedMultiplier();;
    }

    public void setPosition(Vector2 newPos)
    {
        this.position.x = newPos.x;
        this.position.y = newPos.y;
    }

    public void setMoveState(MoveState newState)
    {
        this.moveState = newState;
    }

    public MoveState getMoveState()
    {
        return this.moveState;
    }

    public boolean isOffScreen() {
        return isOffScreen;
    }

    public enum MoveState
    {
        HELD,
        MOVING;
    }
}
