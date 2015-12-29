package com.udacity.desromj.breakout.util;

/**
 * Created by Quiv on 2015-12-27.
 */
public enum Difficulty
{
    EASY(0.75f, 4, 0.75f, "Breeze", 10, 5),
    MEDIUM(1.0f, 6, 1.0f, "Gust", 8, 4),
    HARD(1.25f, 7, 1.25f, "Storm", 7, 3),
    INSANE(1.5f, 9, 1.5f, "Hurricane", 6, 2);

    public float scoreMultiplier;
    public int rowsOfBlocks;
    public float ballSpeedMultiplier;
    public String label;
    public int timeLimitInMinutes;
    public int numLives;

    private Difficulty(float scoreMultiplier, int rowsOfBlocks, float ballSpeedMultiplier,
                       String label, int timeLimitInMinutes, int numLives)
    {
        this.scoreMultiplier = scoreMultiplier;
        this.rowsOfBlocks = rowsOfBlocks;
        this.ballSpeedMultiplier = ballSpeedMultiplier;
        this.label = label;
        this.timeLimitInMinutes = timeLimitInMinutes;
        this.numLives = numLives;
    }

    public static Difficulty getDifficulty(String label)
    {
        for (Difficulty diff: Difficulty.values())
            if (diff.label.equalsIgnoreCase(label))
                return diff;

        return Difficulty.MEDIUM;
    }
}
