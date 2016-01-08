package com.udacity.desromj.breakout.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.udacity.desromj.breakout.entity.powerup.PowerupType;
import com.udacity.desromj.breakout.util.Constants;

/**
 * Created by Quiv on 2016-01-07.
 */
public abstract class Powerup
{
    protected PowerupType type;

    Vector2 position;
    Color color;
    String letter;

    BitmapFont font;

    protected Powerup(Block block, Color color, String letter)
    {
        this.position = new Vector2(block.position.x, block.position.y);
        this.color = color;
        this.letter = letter;

        font = new BitmapFont();
        font.getData().setScale(Constants.POWERUP_FONT_SCALE);
        font.setColor(Constants.POWERUP_SECONDARY_COLOR);
    }

    /**
     * Set the protected field 'type' to the appropriate PowerupType enum
     */
    public abstract void setPowerupType();

    public final void update(float delta)
    {
        this.position.y -= delta * Constants.POWERUP_FALL_SPEED;
    }

    public final void render(ShapeRenderer renderer, SpriteBatch batch)
    {
        renderer.setColor(Constants.POWERUP_SECONDARY_COLOR);
        renderer.circle(position.x, position.y, Constants.POWERUP_RADIUS);

        renderer.setColor(this.color);
        renderer.circle(position.x, position.y, Constants.POWERUP_RADIUS - Constants.POWERUP_MARGIN);

        font.draw(batch, letter, position.x, position.y, 0, Align.center, false);
    }

    public final PowerupType getType()
    {
        return this.type;
    }
}
