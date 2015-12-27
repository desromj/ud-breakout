package com.udacity.desromj.breakout;

import com.badlogic.gdx.Game;
import com.udacity.desromj.breakout.screen.BreakoutScreen;

public class BreakoutGame extends Game
{
	@Override
	public void create ()
    {
		setScreen(new BreakoutScreen(this));
	}
}
