package com.udacity.desromj.breakout.entity;

import com.badlogic.gdx.graphics.Color;

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
     * Adds the score of the destroyed block to the current score, and returns a String
     * which can be displayed in a user-firendly manner (350x2)
     * @param block
     * @return
     */
    public String addScore(Block block)
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

        int addScore = block.blockType.pointValue * currentCombo;
        addScore(addScore);

        return String.valueOf(addScore) + ((currentCombo > 1) ? " x " + currentCombo : "");
    }

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
