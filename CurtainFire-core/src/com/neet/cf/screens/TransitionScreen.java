package com.neet.cf.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.neet.cf.CurtainFire;
import com.neet.cf.handlers.GameInput;

public class TransitionScreen implements Screen
{
	public enum TransitionType {
		FadeOut, FadeIn, Flash
	}
	private TransitionType currentTransition;
	private CurtainFire cf;
	private Color transitionColor;
	private OrthographicCamera camera;
	private Screen nextScreen;
	private ShapeRenderer sr;
	public TransitionScreen(CurtainFire game, Screen s, TransitionType ct)
	{
		
		cf = game;
		nextScreen=s;
		currentTransition = ct;
		transitionColor = new Color(0,0,0,0);
		sr = new ShapeRenderer();
		
	}
	@Override
	public void show()
	{
		camera = new OrthographicCamera();
		camera.zoom=0.5f;
		
	}

	@Override
	public void render(float delta)
	{
		if(GameInput.isDown(GameInput.BUTTON_C))
		{
			cf.setScreen(nextScreen);
		}
		switch(currentTransition)
		{
			case FadeOut: 	fadeOut(delta);
							break;
			case FadeIn:
							break;
			case Flash:
							break;
		}
		
	}
	private void fadeOut(float delta)
	{
		if(transitionColor.a>=1)
		{
			//done=true;
		}
		sr.begin(ShapeType.Filled);
		sr.setColor(new Color(0,0,0,0));
		sr.rect(0,0,640,480);
		sr.end();
		
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
}
