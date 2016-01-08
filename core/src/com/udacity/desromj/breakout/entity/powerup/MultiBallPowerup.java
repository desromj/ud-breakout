package com.udacity.desromj.breakout.entity.powerup;

import com.badlogic.gdx.graphics.Color;
import com.udacity.desromj.breakout.entity.Block;
import com.udacity.desromj.breakout.entity.Powerup;
import com.udacity.desromj.breakout.util.Constants;

/**
 * Created by Quiv on 2016-01-07.
 */
public class MultiBallPowerup extends Powerup
{
    public MultiBallPowerup(Block block)
    {
        super(block, Constants.POWERUP_MULTIBALL_COLOR, Constants.POWERUP_MULTIBALL_LETTER);
    }

    @Override
    public void setPowerupType()
    {
        this.type = PowerupType.MULTIBALL;
    }
}
