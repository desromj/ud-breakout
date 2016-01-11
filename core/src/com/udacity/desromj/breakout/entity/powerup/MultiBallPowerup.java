package com.udacity.desromj.breakout.entity.powerup;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.udacity.desromj.breakout.entity.Ball;
import com.udacity.desromj.breakout.entity.Powerup;
import com.udacity.desromj.breakout.screen.BreakoutScreen;
import com.udacity.desromj.breakout.util.Constants;

/**
 * Created by Quiv on 2016-01-07.
 */
public class MultiBallPowerup extends Powerup
{
    public MultiBallPowerup(Vector2 position)
    {
        super(
                position,
                Constants.POWERUP_MULTIBALL_COLOR,
                Constants.POWERUP_MULTIBALL_LETTER,
                Constants.POWERUP_MULTIBALL_LIFETIME);
    }

    @Override
    public void setPowerupType()
    {
        this.type = PowerupType.MULTIBALL;
    }

    @Override
    protected void doActivationEffects(BreakoutScreen screen)
    {
        // Split all balls onscreen into two balls and change the angle they are moving by about 10 degrees each
        Array<Ball> balls = screen.getBalls();
        int size = balls.size;

        for (int i = 0; i < size; i++)
        {
            Ball ball = balls.get(i);
            Vector2 velocity = new Vector2(ball.getVelocity().x, ball.getVelocity().y);

            float angle = (float) (Math.atan2(velocity.y, velocity.x) * 180.0f / Math.PI);
            float newAngle1 = angle + Constants.POWERUP_MULTIBALL_SPLIT_DEGREES;
            float newAngle2 = angle - Constants.POWERUP_MULTIBALL_SPLIT_DEGREES;

            Vector2 newVel1 = new Vector2(
                    (float) Math.cos(Math.toRadians(newAngle1)),
                    (float) Math.sin(Math.toRadians(newAngle1)));
            Vector2 newVel2 = new Vector2(
                    (float) Math.cos(Math.toRadians(newAngle2)),
                    (float) Math.sin(Math.toRadians(newAngle2)));

            // Add a ball splitting off the left
            Ball splitBallLeft = new Ball(
                    screen.getLaunchPlatform(),
                    screen.getViewport(),
                    screen.getDifficulty());

            splitBallLeft.setPosition(ball.getPosition());
            splitBallLeft.setVelocity(newVel1);
            splitBallLeft.setMoveState(Ball.MoveState.MOVING);

            balls.add(splitBallLeft);

            // Add a ball splitting off the right
            Ball splitBallRight = new Ball(
                    screen.getLaunchPlatform(),
                    screen.getViewport(),
                    screen.getDifficulty());

            splitBallRight.setPosition(ball.getPosition());
            splitBallRight.setVelocity(newVel2);
            splitBallRight.setMoveState(Ball.MoveState.MOVING);

            balls.add(splitBallRight);
        }
    }
}
