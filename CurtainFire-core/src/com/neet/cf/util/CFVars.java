package com.neet.cf.util;

import com.badlogic.gdx.graphics.Color;

public class CFVars
{	
	public static final int BACKGROUND_LAYER=1;
	public static final int SPECIAL_LAYER=2;
	public static final int MIDDLEGROUND_LAYER=3;
	public static final int FOREGROUND_LAYER=4;
	
	public static final float GRASS_ANIMATION_SPEED= 0.1850f;
	public static final float FLOWER_ANIMATION_SPEED = 0.5f;
	
	public static float SCREEN_WIDTH;
	public static float SCREEN_HEIGHT;
	
	public static float V_WIDTH;
	public static float V_HEIGHT;
	
	public static final int TILE_WIDTH = 16;
	
	public static final int SPRITE_WIDTH = 16;
	public static final int SPRITE_HEIGHT= 20;
	
	public static Color DEFAULT_SB_COLOR;
	public static final Color DEFAULT_SHADOW_COLOR = new Color(0,0,0,0.8f);
	
	public enum Direction
	{
		UP(0), LEFT(1), DOWN(2), RIGHT(3);
		private int numRep;
		Direction(int numRep)
		{
			this.numRep = numRep;
		}
		public int num()
		{
			return numRep;
		}
	}
	
}
