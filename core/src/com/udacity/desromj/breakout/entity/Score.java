package com.udacity.desromj.breakout.entity;

import com.badlogic.gdx.graphics.Color;
import com.udacity.desromj.breakout.util.Difficulty;

/**
 * Created by Quiv on 2015-12-27.
 */
public class Score
{
    int score;
    int topScore;

    // Used for tracking the combo scoring
    Color lastColorDestroyed;
    int currentCombo;

    public Score()
    {
        topScore = 0;
        init();
    }

    public void init()
    {
        score = 0;
        lastColorDestroyed = Color.BLACK; // No black blocks, so combo will always start at 1
        currentCombo = 0;
    }

    /**
     * Adds the score of the destroyed block to the current score
     *
     * Raw scores are multiplied by the difficulty the game is being played at
     *
     * @param block
     */
    public void addScore(Block block, Difficulty difficulty)
    {
        if (block.blockType.color == lastColorDestroyed)
        {
            currentCombo++;
        }
        else
        {
            lastColorDestroyed = block.blockType.color;
            currentCombo = 1;
        }

        int addScore = (int) (block.blockType.pointValue * currentCombo * difficulty.getScoreMultiplier());
        addScore(addScore);
    }

    /**
     * Adds the passed score to this score object. Does NOT use difficulty modifiers
     *
     * @param points
     */
    public void addScore(int points)
    {
        score += points;

        if (score >= topScore)
            topScore = score;
    }

    public void resetCombo()
    {
        this.currentCombo = 0;
    }

    public String getLastComboLabel()
    {
        if (lastColorDestroyed == Color.YELLOW) return "Yellow";
        if (lastColorDestroyed == Color.RED) return "Red";
        if (lastColorDestroyed == Color.BLUE) return "Blue";
        if (lastColorDestroyed == Color.GREEN) return "Green";

        return "None";
    }

    public int getScore() {
        return score;
    }

    public int getTopScore() {
        return topScore;
    }

    public Color getLastColorDestroyed() {
        return lastColorDestroyed;
    }

    public int getCurrentCombo() {
        return currentCombo;
    }

}
