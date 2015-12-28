package com.udacity.desromj.breakout.util;

/**
 * Created by Quiv on 2015-12-27.
 */
public enum Difficulty
{
    EASY(0.75f, 4, 0.75f, "Breeze", 10),
    MEDIUM(1.0f, 6, 1.0f, "Gust", 8),
    HARD(1.25f, 7, 1.25f, "Storm", 7),
    INSANE(1.5f, 9, 1.5f, "Hurricane", 6);

    float scoreMultiplier;
    int rowsOfBlocks;
    float ballSpeedMultiplier;
    String label;
    int timeLimitInMinutes;

    private Difficulty(float scoreMultiplier, int rowsOfBlocks, float ballSpeedMultiplier, String label, int timeLimitInMinutes)
    {
        this.scoreMultiplier = scoreMultiplier;
        this.rowsOfBlocks = rowsOfBlocks;
        this.ballSpeedMultiplier = ballSpeedMultiplier;
        this.label = label;
        this.timeLimitInMinutes = timeLimitInMinutes;
    }
}
