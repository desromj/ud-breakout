package com.udacity.desromj.breakout.entity.powerup;

import com.badlogic.gdx.math.Vector2;
import com.udacity.desromj.breakout.entity.Powerup;
import com.udacity.desromj.breakout.screen.BreakoutScreen;
import com.udacity.desromj.breakout.util.Constants;

/**
 * Created by Mike on 2016-01-11.
 */
public class WiderPaddlePowerup extends Powerup
{
    public WiderPaddlePowerup(Vector2 position)
    {
        super(
                position,
                Constants.POWERUP_WIDER_PADDLE_COLOR,
                Constants.POWERUP_WIDER_PADDLE_LETTER,
                Constants.POWERUP_WIDER_PADDLE_LIFETIME);
    }

    @Override
    protected void setPowerupType()
    {
        this.type = PowerupType.WIDER_PADDLE;
    }

    @Override
    protected void doActivationEffects(BreakoutScreen screen)
    {
        // No activation effects - instead this will be polled for if it is active or not
    }
}
