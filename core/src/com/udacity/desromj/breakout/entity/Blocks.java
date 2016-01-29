package com.udacity.desromj.breakout.entity;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.udacity.desromj.breakout.BreakoutGame;
import com.udacity.desromj.breakout.screen.BreakoutScreen;
import com.udacity.desromj.breakout.util.Constants;
import com.udacity.desromj.breakout.util.Difficulty;

import java.util.Random;

/**
 * Created by Quiv on 2015-12-27.
 */
public class Blocks
{
    DelayedRemovalArray<Block> blocks;
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
        for (int i = 0; i < difficulty.getRowsOfBlocks(); i++)
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

    /**
     * Loops through all blocks onScreen and checks if the ball is colliding with them.
     * If it is, determines whether or not to bounce the ball along the X or Y axis
     */
    public void checkCollision(Ball ball, BreakoutScreen screen)
    {
        for (int i = 0; i < blocks.size; i++) {
            Block block = blocks.get(i);

            if (ball.collided(block, screen))
            {
                BreakoutGame.score.addScore(block, difficulty);

                if (new Random().nextFloat() <= Constants.POWERUP_SPAWN_PERCENT / 100.0f)
                    screen.spawnRandomPowerup(block.position);

                blocks.removeIndex(i);
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
