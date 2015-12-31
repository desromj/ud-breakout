package com.udacity.desromj.breakout.entity;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Quiv on 2015-12-27.
 */
public class Score
{
    public int score;
    public int topScore;

    // Used for tracking the combo scoring
    public Color lastColorDestroyed;
    public int currentCombo;

    // Fonts for drawing the score to the screen

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

    public String getLastComboLabel()
    {
        if (lastColorDestroyed == Color.YELLOW) return "Yellow";
        if (lastColorDestroyed == Color.RED) return "Red";
        if (lastColorDestroyed == Color.BLUE) return "Blue";
        if (lastColorDestroyed == Color.GREEN) return "Green";

        return "None";
    }
}
