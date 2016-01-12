package com.udacity.desromj.breakout.entity.powerup;

import com.badlogic.gdx.math.Vector2;
import com.udacity.desromj.breakout.entity.Powerup;
import com.udacity.desromj.breakout.screen.BreakoutScreen;
import com.udacity.desromj.breakout.util.Constants;

/**
 * Created by Quiv on 2016-01-11.
 */
public class UnstoppaballPowerup extends Powerup
{
    public UnstoppaballPowerup(Vector2 position)
    {
        super(
                position,
                Constants.POWERUP_UNSTOPPABALL_COLOR,
                Constants.POWERUP_UNSTOPPABALL_LETTER,
                Constants.POWERUP_UNSTOPPABALL_LIFETIME);
    }

    @Override
    protected void setPowerupType() { this.type = PowerupType.UNSTOPPABALL; }

    @Override
    protected void doActivationEffects(BreakoutScreen screen)
    {
        // No effects, just collision in the ball class
    }
}
