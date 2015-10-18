package com.neet.cf.handlers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation
{
	private TextureRegion[] frames;
	private float time;
	private float delay;
	private int currentFrame;
	private int timesPlayed;
	private boolean cycleFlip;
	private boolean flipper;
	
	public Animation(){}
	
	public Animation(TextureRegion[] frames) 
	{
		this(frames, 1/12f);
	}
	public Animation(TextureRegion[] frames, float delay)
	{
		setFrames(frames, delay);
	}
	public Animation(TextureRegion[] frames, float delay, boolean cycleFlip)
	{
		setFrames(frames, delay);
		this.cycleFlip= cycleFlip;
		flipper=true;
	}
	public void setFrames(TextureRegion[] frames, float delay)
	{
		this.frames=frames;
		this.delay=delay;
		time=0;
		currentFrame=0;
		timesPlayed=0;
	}
	public void update(float dt)
	{
		if(delay<=0)
			return;
		time+=dt;
		while(time>=delay)
			step();
	}
	public void step()
	{
		time-=delay;
		if(cycleFlip)
		{
			if(flipper)
			{
				currentFrame++;
				if(currentFrame==frames.length-1)
					flipper=false;
			}
			else
			{
				currentFrame--;
				if(currentFrame==0)
					flipper=true;
			}			
		}
		else
		{
			currentFrame++;
			if(currentFrame==frames.length)
			{
				currentFrame=0;
			}
		}
		timesPlayed++;
	}
	public TextureRegion getFrame()
	{
		return frames[currentFrame];
	}
	public int getTimesPlayed()
	{
		return timesPlayed;
	}
	
}
