package com.neet.cf.handlers;

import static com.neet.cf.overworld.util.CFVars.SCREEN_HEIGHT;
import static com.neet.cf.overworld.util.CFVars.SCREEN_WIDTH;
import static com.neet.cf.overworld.util.CFVars.TILE_WIDTH;
import static com.neet.cf.overworld.util.CFVars.V_WIDTH;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;


public class Transition
{
	private static float LRAdjustment = TILE_WIDTH*1.27f;
	private ShapeRenderer sr;
	private boolean complete;
	private TransitionType type;
	private Array<Rectangle> shapes;
	private float timer;
	private float speed;
	private float tick;
	public enum TransitionType {
		RectUp, RectDown, SplitOut, VerticalSquish, HorizontalSquish, 
		FourWaySquish, VerticalSlices
	}
	public Transition(TransitionType t)
	{
		sr = new ShapeRenderer();
		type=t;
		complete=false;
		timer=0f;
		speed = 2.0f;
		shapes = new Array<Rectangle>();
		//Specific initializers
		initialize();
		
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
			case SplitOut:
				splitOut(delta);
				break;
			case VerticalSquish:
				verticalSquish(delta);
				break;
			case HorizontalSquish:
				horizontalSquish(delta);
				break;
			case FourWaySquish:
				fourWaySquish(delta);
				break;
			case VerticalSlices:
				verticalSlices(delta);
				break;
		}
	}
	private void rectUp(float delta)
	{
		timer+=delta * speed;
		Rectangle shape = shapes.get(0);
		if(timer>=tick)
		{	
			shape.y+=TILE_WIDTH;
			if(shape.y>=SCREEN_HEIGHT)
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
		timer+=delta * speed;
		Rectangle shape = shapes.get(0);
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
		sr.setColor(Color.BLACK);
		sr.rect(shape.x, shape.y, shape.width, shape.height);
		sr.end();
		//Gdx.gl.glDisable(GL20.GL_BLEND);
	}
	private void splitOut(float delta)
	{
		Rectangle left = shapes.get(0);
		Rectangle right = shapes.get(1);
		timer+=delta * speed;
		if(timer>=tick)
		{
			left.x-=TILE_WIDTH;
			right.x+=TILE_WIDTH;
			if(left.x<=-SCREEN_WIDTH/2)
				complete=true;
			timer=0;
			
		}
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.BLACK);
		sr.rect(left.x, left.y, left.width, left.height);
		sr.rect(right.x, right.y, right.width, right.height);
		sr.end();
	}
	private void verticalSquish(float delta)
	{
		Rectangle left = shapes.get(0);
		Rectangle right = shapes.get(1);
		timer+=delta * speed;
		if(timer>=tick)
		{
			left.x+=TILE_WIDTH;
			right.x-=TILE_WIDTH;
			if(left.x>=0)
				complete=true;
			timer=0;
			
		}
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.BLACK);
		sr.rect(left.x, left.y, left.width, left.height);
		sr.rect(right.x, right.y, right.width, right.height);
		sr.end();
	}
	private void horizontalSquish(float delta)
	{
		Rectangle bottom = shapes.get(0);
		Rectangle top = shapes.get(1);
		timer+=delta * speed;
		if(timer>=tick)
		{
			bottom.y+=TILE_WIDTH;
			top.y-=TILE_WIDTH;
			if(bottom.y>=0)
				complete=true;
			timer=0;
			
		}
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.BLACK);
		sr.rect(bottom.x, bottom.y, bottom.width, bottom.height);
		sr.rect(top.x, top.y, top.width, top.height);
		sr.end();
	}
	private void fourWaySquish(float delta)
	{
		Rectangle left = shapes.get(0);
		Rectangle right = shapes.get(1);
		Rectangle bottom = shapes.get(2);
		Rectangle top = shapes.get(3);
		timer+=delta * speed;
		if(timer>=tick)
		{
			left.x+=(LRAdjustment);
			right.x-=(LRAdjustment);
			bottom.y+=TILE_WIDTH;
			top.y-=TILE_WIDTH;
			if(bottom.y>=0)
				complete=true;
			timer=0;
			
		}
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.BLACK);
		sr.rect(left.x, left.y, left.width, left.height);
		sr.rect(right.x, right.y, right.width, right.height);
		sr.rect(bottom.x, bottom.y, bottom.width, bottom.height);
		sr.rect(top.x, top.y, top.width, top.height);
		sr.end();
	}
	private void verticalSlices(float delta)
	{
		if(shapes.get(1).y<=0)
			complete=true;
		timer+=delta * speed;
		if(timer>tick)
		{
			if(shapes.get(0).y<0)
			{
				for(int i = 0; i <shapes.size; i+=2)
				{
					shapes.get(i).y+=TILE_WIDTH*2;
				}
			}
			else
			{
				for(int i = 1; i <shapes.size-1; i+=2)
				{
					shapes.get(i).y-=TILE_WIDTH*2;
				}

			}
			timer=0;
		}
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.BLACK);
		for(Rectangle r: shapes)
		{
			sr.rect(r.x, r.y, r.width, r.height);
		}
		sr.end();
		
	}
	private void initialize()
	{
		switch(type)
		{
			case RectUp:
				tick=0.05f;
				shapes.add(new Rectangle(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT));
				break;
	
			case RectDown:
				tick=0.05f;
				shapes.add(new Rectangle(0, SCREEN_HEIGHT - TILE_WIDTH,
							SCREEN_WIDTH, TILE_WIDTH));
				break;
			case SplitOut:
				tick=0.05f;
				//Left
				shapes.add(new Rectangle(0, 0, SCREEN_WIDTH/2, SCREEN_HEIGHT));
				//Right
				shapes.add(new Rectangle(SCREEN_WIDTH/2, 0, SCREEN_WIDTH, SCREEN_HEIGHT));
				break;
			case VerticalSquish:
				tick=0.05f;
				//Left
				shapes.add(new Rectangle(-SCREEN_WIDTH/2, 0, SCREEN_WIDTH/2, SCREEN_HEIGHT));
				//Right
				shapes.add(new Rectangle(SCREEN_WIDTH, 0, SCREEN_WIDTH/2, SCREEN_HEIGHT));
				break;
			case HorizontalSquish:
				tick=0.05f;
				//Bottom
				shapes.add(new Rectangle(0, -SCREEN_HEIGHT/2, SCREEN_WIDTH, SCREEN_HEIGHT/2));
				//Top
				shapes.add(new Rectangle(0, SCREEN_HEIGHT, SCREEN_WIDTH, SCREEN_HEIGHT/2));
			case FourWaySquish:
				tick=0.05f;
				//Left
				shapes.add(new Rectangle(-SCREEN_WIDTH/2, 0, SCREEN_WIDTH/2, SCREEN_HEIGHT));
				//Right
				shapes.add(new Rectangle(SCREEN_WIDTH, 0, SCREEN_WIDTH/2, SCREEN_HEIGHT));
				//Bottom
				shapes.add(new Rectangle(0, -SCREEN_HEIGHT/2, SCREEN_WIDTH, SCREEN_HEIGHT/2));
				//Top
				shapes.add(new Rectangle(0, SCREEN_HEIGHT, SCREEN_WIDTH, SCREEN_HEIGHT/2));
				break;
			case VerticalSlices:
				tick=0.02f;
				//Upscales so independent of actual screen size
				float rectWidth= SCREEN_WIDTH/(V_WIDTH/TILE_WIDTH);
				for(int i = 0; i<=TILE_WIDTH; i++)
				{
					//For even iterations
					if(i%2==0)
					{
						shapes.add(new Rectangle(i*rectWidth, -SCREEN_HEIGHT, rectWidth, SCREEN_HEIGHT));
					}
					else //For odd iterations
					{
						shapes.add(new Rectangle(i*rectWidth, SCREEN_HEIGHT, rectWidth, SCREEN_HEIGHT));
					}
						
				}
				break;
				
		}
	}
	public void dispose()
	{
		sr.dispose();
	}
	
}
