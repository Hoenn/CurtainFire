package com.neet.cf.script.entities;

import com.badlogic.gdx.graphics.Color;
import com.neet.cf.screens.BattleScreen;

public class Bullet
{
	//protected Image image;
	protected Color hitboxColor;
	protected double angle;
	protected float speed;
	protected int index;
	protected boolean alive;
	protected double accumulatedAngleChange;
	protected float accumulatedSpeedChange;
	
	public Bullet(float x, float y, double angle, float speed, Color color)
	{
		this.setAngle(angle);
		this.setSpeed(speed);
		setAlive(true);
		accumulatedAngleChange = 0;
		accumulatedSpeedChange = 0;
		hitboxColor = color;
	}
	
	public Color getHitboxColor()
	{
		return hitboxColor;
	}
	
	public int getIndex()
	{
		return index;
	}
	
	public void setIndex(int index)
	{
		this.index = index;
	}

	public float getSpeed()
	{
		return speed;
	}

	public void setSpeed(float speed)
	{
		this.speed = speed;
	}

	public double getAngle()
	{
		return angle;
	}

	public void setAngle(double angle)
	{
		this.angle = angle;
	}

	public boolean isAlive()
	{
		return alive;
	}

	public void setAlive(boolean alive)
	{
		this.alive = alive;
		if (!alive)
			BattleScreen.INVOKE_GC = true;
	}

	public double getAccumulatedAngleChange()
	{
		return accumulatedAngleChange;
	}

	public void setAccumulatedAngleChange(double accumulatedAngleChange)
	{
		this.accumulatedAngleChange = accumulatedAngleChange;
	}

	public float getAccumulatedSpeedChange()
	{
		return accumulatedSpeedChange;
	}

	public void setAccumulatedSpeedChange(float accumulatedSpeedChange)
	{
		this.accumulatedSpeedChange = accumulatedSpeedChange;
	}
}
