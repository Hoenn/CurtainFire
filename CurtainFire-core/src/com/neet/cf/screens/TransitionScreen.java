package com.neet.cf.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.neet.cf.CurtainFire;
import com.neet.cf.handlers.GameInput;
import com.neet.cf.handlers.GameScreenManager;
import com.neet.cf.handlers.Transition;

public class TransitionScreen extends GameScreen
{

	private int nextScreen;
	private GameScreen prevScreen;
	private Transition transition;
	public TransitionScreen(GameScreenManager gsm, GameScreen p, int s, Transition t)
	{
		
		super(gsm);
		nextScreen=s;
		prevScreen=p;
		transition = t;
		
	}

	@Override
	public void update(float delta)
	{
		prevScreen.render();
		transition.update(delta);
		if(transition.isComplete())
		{
			transition.dispose();
			gsm.setScreen(nextScreen);
		}
		
		
	}
	@Override
	public void render()
	{
			
	}
	@Override
	public void show()
	{
		
	}

	@Override
	public void resize(int width, int height)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void handleInput()
	{
		// TODO Auto-generated method stub
		
	}

}
