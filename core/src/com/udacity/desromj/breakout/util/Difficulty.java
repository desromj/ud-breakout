package com.udacity.desromj.breakout.util;

/**
 * Created by Quiv on 2015-12-27.
 */
public enum Difficulty
{
    EASY(0.75f, 4, 0.75f, "Breeze", 5, 5),
    MEDIUM(1.0f, 6, 1.0f, "Gust", 4, 4),
    HARD(1.25f, 7, 1.25f, "Storm", 3, 3),
    INSANE(1.5f, 9, 1.5f, "Hurricane", 2, 3);

    public float scoreMultiplier;
    public int rowsOfBlocks;
    public float speedMultiplier;
    public String label;
    public int timeLimitInMinutes;
    public int numLives;

    private Difficulty(float scoreMultiplier, int rowsOfBlocks, float speedMultiplier,
                       String label, int timeLimitInMinutes, int numLives)
    {
        this.scoreMultiplier = scoreMultiplier;
        this.rowsOfBlocks = rowsOfBlocks;
        this.speedMultiplier = speedMultiplier;
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
