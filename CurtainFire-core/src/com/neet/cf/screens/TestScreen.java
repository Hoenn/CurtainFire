package com.neet.cf.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.neet.cf.CurtainFire;
import com.neet.cf.handlers.GameScreenManager;
import com.neet.cf.handlers.Transition;
import com.neet.cf.handlers.Transition.TransitionType;

public class TestScreen extends BattleScreen
{

	public TestScreen(GameScreenManager gsm)
	{
		super(gsm);
	}
	
	@Override
	public void handleInput()
	{
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE))
		{
			gsm.prevScreen=gsm.TEST;
			gsm.transitionScreens(this, gsm.OPTIONS, new Transition(TransitionType.RectDown));
		}		
		if (Gdx.input.isKeyJustPressed(Keys.BACKSPACE))
		{
			Gdx.input.setInputProcessor(CurtainFire.inputProc);
			gsm.disposeScreen(gsm.TEST);
			gsm.setScreen(gsm.MENU);
		}
	}

}
