package com.udacity.desromj.breakout.entity.powerup;

import com.badlogic.gdx.graphics.Color;
import com.udacity.desromj.breakout.entity.Block;
import com.udacity.desromj.breakout.entity.Powerup;
import com.udacity.desromj.breakout.screen.BreakoutScreen;
import com.udacity.desromj.breakout.util.Constants;

/**
 * Created by Quiv on 2016-01-07.
 */
public class MultiBallPowerup extends Powerup
{
    public MultiBallPowerup(Block block, float lifeTime)
    {
        super(block, Constants.POWERUP_MULTIBALL_COLOR, Constants.POWERUP_MULTIBALL_LETTER, lifeTime);
    }

    @Override
    public void setPowerupType()
    {
        this.type = PowerupType.MULTIBALL;
    }

    @Override
    public void activate(BreakoutScreen screen)
    {
        // TODO: Split all balls onscreen into two balls and change the angle they are moving by about 10 degrees each
    }
}
