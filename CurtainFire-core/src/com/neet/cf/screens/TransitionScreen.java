package com.neet.cf.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.neet.cf.CurtainFire;
import com.neet.cf.handlers.GameInput;
import com.neet.cf.handlers.GameScreenManager;

public class TransitionScreen extends GameScreen
{
	public enum TransitionType {
		RectUp, FadeIn, Flash
	}
	private TransitionType currentTransition;
	private CurtainFire cf;
	private Color transitionColor;
	private OrthographicCamera camera;
	private int nextScreen;
	private GameScreen prevScreen;
	private ShapeRenderer sr;
	private Rectangle growingRectangle;
	private float rectangleTimer = 0f;
	private boolean done = false;
	private float rectangleTick = .05f;
	public TransitionScreen(GameScreenManager gsm, GameScreen p, int s, TransitionType ct)
	{
		
		super(gsm);
		p.render();
		nextScreen=s;
		prevScreen=p;
		currentTransition = ct;
		transitionColor = new Color(0,0,0,0);
		sr = new ShapeRenderer();
		growingRectangle = new Rectangle(0, 0, CurtainFire.width, 16);
		
	}

	@Override
	public void update(float delta)
	{
		prevScreen.render();

		if(done)
			gsm.setScreen(nextScreen);
		switch(currentTransition)
		{
			case RectUp: 	rectUp(delta);
							break;
			case FadeIn:
							break;
			case Flash:
							break;
		}	
		
	}
	private void rectUp(float delta)
	{
	
		if(growingRectangle.getHeight()>=CurtainFire.height+16)
		{
			done=true;
		}
		
		rectangleTimer+=delta;
		if(rectangleTimer>rectangleTick)
		{
			growingRectangle.height+=16;
			rectangleTimer=0;
		}
		sr.begin(ShapeType.Filled);
		sr.setColor(new Color(0,0,0,0));
		sr.rect(growingRectangle.x, growingRectangle.y, growingRectangle.width, growingRectangle.height);
		sr.end();
		
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
