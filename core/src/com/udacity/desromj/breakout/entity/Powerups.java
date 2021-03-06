package com.udacity.desromj.breakout.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.udacity.desromj.breakout.entity.powerup.PowerupType;
import com.udacity.desromj.breakout.screen.BreakoutScreen;
import com.udacity.desromj.breakout.util.Constants;

/**
 * Created by Mike on 2016-01-11.
 */
public class Powerups
{
    Array<Powerup> powerups;

    public Powerups()
    {
        this.powerups = new DelayedRemovalArray<Powerup>();
    }

    public void init()
    {
        this.powerups.clear();
    }

    public void addNewRandomPowerup(Vector2 position)
    {
        powerups.add(Powerup.makeRandomPowerup(position));
    }

    public void checkCollision(Platform platform, BreakoutScreen screen)
    {
        for (Powerup pu: powerups) {
            if (platform.getHitRectangle().contains(pu.getPosition())) {
                pu.activate(screen);
            }
        }
    }

    /**
     * Checks if the passed powerup type is currently active and should have its effects in effect
     *
     * @param type
     * @return
     */
    public boolean powerupTypeIsActive(PowerupType type)
    {
        for (Powerup pu: powerups)
            if (pu.getType() == type && pu.isAlive() && pu.isInEffect())
                return true;

        return false;
    }

    public void update(float delta)
    {
        for (int i = 0; i < powerups.size; i++)
        {
            Powerup pu = powerups.get(i);
            pu.update(delta);

            // Remove powerups off the bottom of the screen
            if (pu.getPosition().y <= 0.0f - Constants.POWERUP_RADIUS)
                powerups.removeIndex(i);

            // Remove powerups which were activated but have outlived their lifeTime
            if (pu.isAlive() && !pu.isInEffect())
                powerups.removeIndex(i);
        }
    }

    public void renderShapes(ShapeRenderer renderer)
    {
        for (Powerup pu: powerups)
            pu.renderShapes(renderer);
    }

    public void renderSprites(SpriteBatch batch)
    {
        for (Powerup pu: powerups)
            pu.renderSprites(batch);
    }

}
