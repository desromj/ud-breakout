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

    public static final float PLATFORM_WIDTH = 75.0f;
    public static final float PLATFORM_HEIGHT = 20.0f;
    public static final float PLATFORM_MAX_SPEED = 300.0f;
    public static final Color PLATFORM_COLOR = Color.PURPLE;

    public static final float BALL_RADIUS = 8.0f;
    public static final float BALL_SPEED = 400.0f;
    public static final Color BALL_COLOR = Color.RED;

    /** Platform's height from the bottom of the screen */
    public static final float PLATFORM_BOTTOM_MARGIN = 40.0f;

    public static final Color BACKGROUND_COLOR_BOTTOM = Color.CYAN;
    public static final Color BACKGROUND_COLOR_TOP = Color.NAVY;









    private Constants() {}
}
