package com.udacity.desromj.breakout.util;

/**
 * Created by Quiv on 2015-12-27.
 */
public enum Difficulty
{
    EASY(0.75f, 4, 0.75f, "Breeze", 5, 5, 4000),
    MEDIUM(1.0f, 6, 1.0f, "Gust", 4, 4, 7500),
    HARD(1.25f, 7, 1.25f, "Storm", 3, 3, 10000),
    INSANE(1.5f, 9, 1.5f, "Hurricane", 2, 3, 15000);

    public float scoreMultiplier;
    public int rowsOfBlocks;
    public float speedMultiplier;
    public String label;
    public int timeLimitInMinutes;
    public int numLives;
    public int clearBonus;

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
}
