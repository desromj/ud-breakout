package com.udacity.desromj.breakout.util;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Quiv on 2015-12-27.
 *
 * Holds most global variables required for the Breakout project. Private, cannot instantiate
 */
public class Constants
{
    public static final float WORLD_WIDTH = 800.0f;
    public static final float WORLD_HEIGHT = 600.0f;

    public static final String GAME_TITLE = "Quiv's Breakout!!!";
    public static final float TITLE_BORDER = 40.0f;

    // Get a dark background for our white to stand out against
    public static final float START_BUTTON_WIDTH = 160.0f;
    public static final float START_BUTTON_HEIGHT = 120.0f;
    public static final Color START_BUTTON_COLOR = Color.BLUE;

    public static final float PLATFORM_WIDTH = 75.0f;
    public static final float PLATFORM_HEIGHT = 20.0f;
    public static final float PLATFORM_MAX_SPEED = 300.0f;
    public static final Color PLATFORM_COLOR = Color.PURPLE;

    public static final float BALL_RADIUS = 8.0f;
    public static final float BALL_SPEED = 400.0f;
    public static final Color BALL_COLOR = Color.RED;

    public static final int BLOCKS_PER_ROW = 12;

    public static final float BLOCK_WIDTH = WORLD_WIDTH / BLOCKS_PER_ROW;
    public static final float BLOCK_HEIGHT = 30.0f;
    public static final float BLOCK_BORDER_WIDTH = 2.5f;
    public static final float BLOCK_MARGIN_FROM_TOP = 60.0f;

    // Area within/above the platform within which the ball can be hit (including its redius)
    public static final float BALL_HIT_ALLOWANCE = 0.02f;

    /** Platform's height from the bottom of the screen */
    public static final float PLATFORM_BOTTOM_MARGIN = 40.0f;

    public static final Color BACKGROUND_COLOR_BOTTOM = Color.CYAN;
    public static final Color BACKGROUND_COLOR_TOP = Color.NAVY;

    public static final float TEXT_MARGIN = 8.0f;
    public static final Color TEXT_COLOR = Color.WHITE;
    public static final Color WIN_TEXT_COLOR = Color.BLACK;
    public static final Color LOSE_TEXT_COLOR = Color.BLUE;









    private Constants() {}
}
