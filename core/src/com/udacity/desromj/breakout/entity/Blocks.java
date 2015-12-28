package com.udacity.desromj.breakout.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.udacity.desromj.breakout.util.Constants;
import com.udacity.desromj.breakout.util.Difficulty;

/**
 * Created by Quiv on 2015-12-27.
 */
public class Blocks
{
    public DelayedRemovalArray<Block> blocks;
    Difficulty difficulty;

    public Blocks(Difficulty difficulty)
    {
        this.difficulty = difficulty;
        blocks = new DelayedRemovalArray<Block>();
        init();
    }

    public void init()
    {
        blocks.clear();

        // create the 2-D array of blocks to populate the screen
        for (int i = 0; i < difficulty.rowsOfBlocks; i++)
        {
            for (int j = 0; j < Constants.BLOCKS_PER_ROW; j++)
            {
                Vector2 newPos = new Vector2(
                        Constants.BLOCK_WIDTH / 2 + Constants.BLOCK_WIDTH * j,
                        Constants.WORLD_HEIGHT - (Constants.BLOCK_MARGIN_FROM_TOP + Constants.BLOCK_HEIGHT / 2 + Constants.BLOCK_HEIGHT * i)
                );

                blocks.add(new Block(newPos));
            }
        }
    }

    public boolean hasBlocksRemaining()
    {
        return (blocks.size > 0);
    }

    public void render(ShapeRenderer renderer)
    {
        for (Block block: blocks)
            block.render(renderer);
    }
}
