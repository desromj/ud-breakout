package com.udacity.desromj.breakout;

import com.badlogic.gdx.Game;
import com.udacity.desromj.breakout.screen.BreakoutScreen;
import com.udacity.desromj.breakout.util.Difficulty;

public class BreakoutGame extends Game
{
	@Override
	public void create ()
    {
		setScreen(new BreakoutScreen(this, Difficulty.MEDIUM));
	}
}
