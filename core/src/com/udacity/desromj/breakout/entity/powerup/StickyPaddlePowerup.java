package com.udacity.desromj.breakout.entity.powerup;

import com.badlogic.gdx.math.Vector2;
import com.udacity.desromj.breakout.entity.Powerup;
import com.udacity.desromj.breakout.screen.BreakoutScreen;
import com.udacity.desromj.breakout.util.Constants;

/**
 * Created by Quiv on 2016-01-11.
 */
public class StickyPaddlePowerup extends Powerup
{
    public StickyPaddlePowerup(Vector2 position)
    {
        super(
                position,
                Constants.POWERUP_STICKY_PADDLE_COLOR,
                Constants.POWERUP_STICKY_PADDLE_LETTER,
                Constants.POWERUP_STICKY_PADDLE_LIFETIME);
    }

    @Override
    protected void setPowerupType() { this.type = PowerupType.STICKY_PADDLE; }

    @Override
    protected void doActivationEffects(BreakoutScreen screen)
    {
        // No activation effects, just affects collision in the Ball class
    }
}
