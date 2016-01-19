package com.udacity.desromj.breakout.util;

/**
 * Created by Quiv on 2015-12-27.
 */
public enum Difficulty
{
    EASY(0.8f, 4, 0.8f, "Breeze", 5, 5, 4000),
    MEDIUM(1.0f, 6, 1.0f, "Gust", 4, 4, 7500),
    HARD(1.2f, 7, 1.2f, "Storm", 3, 3, 10000),
    INSANE(1.5f, 9, 1.5f, "Hurricane", 2, 3, 15000);

    private float scoreMultiplier;
    private int rowsOfBlocks;
    private float speedMultiplier;
    private String label;
    private int timeLimitInMinutes;
    private int numLives;
    private int clearBonus;

    private Difficulty(float scoreMultiplier, int rowsOfBlocks, float speedMultiplier,
                       String label, int timeLimitInMinutes, int numLives, int clearBonus)
    {
        this.scoreMultiplier = scoreMultiplier;
        this.rowsOfBlocks = rowsOfBlocks;
        this.speedMultiplier = speedMultiplier;
        this.label = label;
        this.timeLimitInMinutes = timeLimitInMinutes;
        this.numLives = numLives;
        this.clearBonus = clearBonus;
    }

    public static Difficulty getDifficulty(String label)
    {
        for (Difficulty diff: Difficulty.values())
            if (diff.label.equalsIgnoreCase(label))
                return diff;

        return Difficulty.MEDIUM;
    }

    public float getScoreMultiplier() {
        return scoreMultiplier;
    }

    public int getRowsOfBlocks() {
        return rowsOfBlocks;
    }

    public float getSpeedMultiplier() {
        return speedMultiplier;
    }

    public String getLabel() {
        return label;
    }

    public int getTimeLimitInMinutes() {
        return timeLimitInMinutes;
    }

    public int getNumLives() {
        return numLives;
    }

    public int getClearBonus() {
        return clearBonus;
    }
}
