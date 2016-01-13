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

    // Area within/above the platform within which the ball can be hit (including its radius)
    public static final float BALL_HIT_ALLOWANCE = 0.02f;

    public static final float BALL_RADIUS = 8.0f;
    public static final float BALL_SPEED = 400.0f;
    public static final Color BALL_COLOR = Color.RED;

    public static final int BLOCKS_PER_ROW = 12;

    public static final float BLOCK_WIDTH = WORLD_WIDTH / BLOCKS_PER_ROW;
    public static final float BLOCK_HEIGHT = WORLD_HEIGHT / 20.0f;
    public static final float BLOCK_BORDER_WIDTH = 2.5f;
    public static final float BLOCK_MARGIN_FROM_TOP = 60.0f;


    /** Platform's height from the bottom of the screen */
    public static final float PLATFORM_BOTTOM_MARGIN = 40.0f;

    public static final Color BACKGROUND_COLOR_BOTTOM = Color.CYAN;
    public static final Color BACKGROUND_COLOR_TOP = Color.NAVY;

    public static final float TEXT_MARGIN = 8.0f;
    public static final Color TEXT_COLOR = Color.WHITE;
    public static final Color WIN_TEXT_COLOR = Color.BLACK;
    public static final Color LOSE_TEXT_COLOR = Color.BLUE;
    public static final Color TOP_SCORE_TEXT_COLOR = Color.CORAL;

    public static final float START_BUTTON_FONT_SCALE = 2.0f;
    public static final float TOP_SCORE_FONT_SCALE = 3.0f;
    public static final float GAME_FINISHED_FONT_SCALE = 2.5f;
    public static final float TITLE_FONT_SCALE = 4.0f;
    public static final float INGAME_FONT_SCALE = 1.0f;
    public static final float POWERUP_FONT_SCALE = 0.8f;

    public static final float BOUNCE_ANGLE_DEGREES = (float) Math.abs((Math.atan2(BLOCK_HEIGHT + BALL_RADIUS, BLOCK_WIDTH + BALL_RADIUS) * 180.0f / Math.PI));
    public static final float MINIMUM_HIT_ANGLE_DEGREES = 10.0f;

    public static final float POWERUP_SPAWN_PERCENT = 12.5f;
    public static final float POWERUP_FALL_SPEED = 120.0f;
    public static final float POWERUP_RADIUS = 8.0f;
    public static final float POWERUP_MARGIN = 1.0f;
    public static final Color POWERUP_SECONDARY_COLOR = Color.WHITE;

    public static final Color POWERUP_MULTIBALL_COLOR = Color.RED;
    public static final String POWERUP_MULTIBALL_LETTER = "M";
    public static final float POWERUP_MULTIBALL_SPLIT_DEGREES = 12.5f;
    public static final float POWERUP_MULTIBALL_LIFETIME = 0.00001f;

    public static final Color POWERUP_WIDER_PADDLE_COLOR = Color.YELLOW;
    public static final String POWERUP_WIDER_PADDLE_LETTER = "W";
    public static final float POWERUP_WIDER_PADDLE_WIDTH_MODIFIER = 2.0f;
    public static final float POWERUP_WIDER_PADDLE_LIFETIME = 10.0f;

    public static final Color POWERUP_UNSTOPPABALL_COLOR = Color.GREEN;
    public static final String POWERUP_UNSTOPPABALL_LETTER = "P";
    public static final float POWERUP_UNSTOPPABALL_LIFETIME = 5.0f;

    public static final Color POWERUP_STICKY_PADDLE_COLOR = Color.BLUE;
    public static final String POWERUP_STICKY_PADDLE_LETTER = "S";
    public static final float POWERUP_STICKY_PADDLE_LIFETIME = 30.0f;

    private Constants() {}
}
