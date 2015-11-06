package com.neet.cf.handlers;

import static com.neet.cf.util.CFVars.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;


public class Transition
{
	private ShapeRenderer sr;
	private boolean complete;
	private TransitionType type;
	private Rectangle shape;
	private float timer;
	private float tick;
	public enum TransitionType {
		RectUp, RectDown
	}
	public Transition(TransitionType t)
	{
		sr = new ShapeRenderer();
		type=t;
		complete=false;
		timer=0f;
		//Specific initializers
		switch(type)
		{
			case RectUp:
				tick=0.05f;
				shape = new Rectangle(0, 0, SCREEN_WIDTH, TILE_WIDTH);
				break;
	
			case RectDown:
				tick=0.05f;
				shape = new Rectangle(0, SCREEN_HEIGHT - TILE_WIDTH,
							SCREEN_WIDTH, TILE_WIDTH);
				break;
		}
		
	}
	public boolean isComplete()
	{
		return complete;
	}
	public void update(float delta)
	{
		switch(type)
		{
			case RectUp:
				rectUp(delta);
				break;
	
			case RectDown:
				rectDown(delta);
				break;
		}
	}
	private void rectUp(float delta)
	{
		timer+=delta;
		if(timer>=tick)
		{	
			shape.height+=TILE_WIDTH;
			if(shape.height>SCREEN_HEIGHT)
				complete=true;
			timer=0;
		}
		
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.BLACK);
		sr.rect(shape.x, shape.y, shape.width, shape.height);
		sr.end();
	}
	private void rectDown(float delta)
	{
		timer+=delta;
		if(timer>=tick)
		{	
			shape.height+=TILE_WIDTH;
			shape.y-=TILE_WIDTH;
			if(shape.y<=0)
				complete=true;
			timer=0;
		}
		//Gdx.gl.glEnable(GL20.GL_BLEND);
	    //Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		sr.begin(ShapeType.Filled);
		sr.setColor(new Color(0,0,0, 0.8f));
		sr.rect(shape.x, shape.y, shape.width, shape.height);
		sr.end();
		//Gdx.gl.glDisable(GL20.GL_BLEND);
	}
	public void dispose()
	{
		sr.dispose();
	}
	
}
