package com.udacity.desromj.breakout.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.udacity.desromj.breakout.util.Constants;

import java.util.Random;

/**
 * Created by Quiv on 2015-12-27.
 */
public class Block
{
    public Vector2 position;
    BlockType blockType;

    public Block(Vector2 position)
    {
        this(position, BlockType.makeRandomBlock());
    }

    public Block(Vector2 position, BlockType blockType)
    {
        this.position = position;
        this.blockType = blockType;
    }

    public void render(ShapeRenderer renderer)
    {
        // Draw the border
        renderer.setColor(Color.BLACK);

        renderer.rect(
                position.x - Constants.BLOCK_WIDTH / 2,
                position.y - Constants.BLOCK_HEIGHT / 2,
                Constants.BLOCK_WIDTH,
                Constants.BLOCK_HEIGHT
        );

        // Then the colour rectangle of the block
        renderer.setColor(blockType.color);

        renderer.rect(
                position.x + Constants.BLOCK_BORDER_WIDTH - Constants.BLOCK_WIDTH / 2,
                position.y + Constants.BLOCK_BORDER_WIDTH - Constants.BLOCK_HEIGHT / 2,
                Constants.BLOCK_WIDTH - 2 * Constants.BLOCK_BORDER_WIDTH,
                Constants.BLOCK_HEIGHT - 2 * Constants.BLOCK_BORDER_WIDTH
        );
    }






    public enum BlockType
    {
        GREEN(Color.GREEN, 100),
        YELLOW(Color.YELLOW, 200),
        BLUE(Color.BLUE, 350),
        RED(Color.RED, 500);

        Color color;
        int pointValue;

        private static final Random random = new Random();

        private BlockType(Color color, int pointValue)
        {
            this.color = color;
            this.pointValue = pointValue;
        }

        public static BlockType makeRandomBlock()
        {
            int nextIdx = (int) (random.nextFloat() * BlockType.values().length);
            return BlockType.values()[nextIdx];
        }
    }
}
