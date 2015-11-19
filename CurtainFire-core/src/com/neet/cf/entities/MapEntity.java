package com.neet.cf.entities;

import static com.neet.cf.util.CFVars.DEFAULT_SHADOW_COLOR;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class MapEntity
{
	protected TextureRegion currentFrame;
	protected Vector2 position;
	private static int shadowX=2;
	private static int shadowY=2;
	private static float shadowScaleX=1.1f;
	private static float shadowScaleY=1.9f;
	
	
	public void drawShadow(Batch sb)
	{
		sb.setColor(DEFAULT_SHADOW_COLOR);
		sb.draw(currentFrame, position.x+shadowX, position.y+shadowY, 0, 0, 16, 10, shadowScaleX, shadowScaleY,0);
	}
	public Vector2 getPosition()
	{
		return position;
	}
}
