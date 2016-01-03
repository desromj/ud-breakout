package com.udacity.desromj.breakout;

import com.badlogic.gdx.Game;
import com.udacity.desromj.breakout.entity.Score;
import com.udacity.desromj.breakout.screen.BreakoutScreen;
import com.udacity.desromj.breakout.screen.GameOverScreen;
import com.udacity.desromj.breakout.screen.StartScreen;
import com.udacity.desromj.breakout.screen.WinScreen;
import com.udacity.desromj.breakout.util.Difficulty;

public class BreakoutGame extends Game
{
    /** Keep the same score for the duration of the game - reset on new game start */
    public static final Score score = new Score();

	@Override
	public void create ()
    {
		showStartScreen();
	}

	public void showStartScreen()
    {
        setScreen(new StartScreen(this));
    }

    public void showBreakoutScreen(Difficulty diff)
    {
        setScreen(new BreakoutScreen(this, diff));
    }

    public void showWinScreen()
    {
        setScreen(new WinScreen(this));
    }

    public void showGameOverScreen()
    {
        setScreen(new GameOverScreen(this));
    }
}
