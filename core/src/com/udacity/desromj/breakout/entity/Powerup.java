package com.udacity.desromj.breakout.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.udacity.desromj.breakout.entity.powerup.MultiBallPowerup;
import com.udacity.desromj.breakout.entity.powerup.PowerupType;
import com.udacity.desromj.breakout.screen.BreakoutScreen;
import com.udacity.desromj.breakout.util.Constants;

import java.util.Random;

/**
 * Created by Quiv on 2016-01-07.
 */
public abstract class Powerup
{
    protected PowerupType type;

    Vector2 position;
    Color color;
    String letter;
    float lifeTimeInSeconds;
    float startTime;
    boolean alive;

    BitmapFont font;

    protected Powerup(Vector2 position, Color color, String letter, float lifeTimeInSeconds)
    {
        this.position = new Vector2(position.x, position.y);
        this.color = color;
        this.letter = letter;
        this.lifeTimeInSeconds = lifeTimeInSeconds;
        this.alive = false;

        font = new BitmapFont();
        font.getData().setScale(Constants.POWERUP_FONT_SCALE);
        font.setColor(Constants.POWERUP_SECONDARY_COLOR);
    }

    /**
     * Set the protected field 'type' to the appropriate PowerupType enum
     */
    protected abstract void setPowerupType();
    protected abstract void doActivationEffects(BreakoutScreen screen);

    public final void activate(BreakoutScreen screen)
    {
        this.startTime = TimeUtils.nanoTime();
        this.alive = true;
        doActivationEffects(screen);
    }

    public final boolean isAlive()
    {
        return this.alive;
    }

    public final boolean isInEffect()
    {
        return MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime) > this.lifeTimeInSeconds;
    }

    public final void update(float delta)
    {
        this.position.y -= delta * Constants.POWERUP_FALL_SPEED;
    }

    public final void renderShapes(ShapeRenderer renderer)
    {
        if (this.alive) return;

        renderer.setColor(Constants.POWERUP_SECONDARY_COLOR);
        renderer.circle(position.x, position.y, Constants.POWERUP_RADIUS);

        renderer.setColor(this.color);
        renderer.circle(position.x, position.y, Constants.POWERUP_RADIUS - Constants.POWERUP_MARGIN);
    }

    public final void renderSprites(SpriteBatch batch)
    {
        if (this.alive) return;

        font.draw(batch, letter, position.x, position.y, 0, Align.center, false);
    }



    public static final Powerup makeRandomPowerup(Vector2 position)
    {
        int idx = new Random().nextInt(PowerupType.values().length);

        switch (PowerupType.values()[idx])
        {
            case MULTIBALL:
                return new MultiBallPowerup(new Vector2(position.x, position.y), 0.1f);

            default:
                throw new UnsupportedOperationException();
        }
    }

    public final Vector2 getPosition() { return this.position; }
    public final PowerupType getType()
    {
        return this.type;
    }
}
