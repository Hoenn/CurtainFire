package com.neet.cf.overworld.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

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
	public static final Color DEFAULT_SHADOW_COLOR = new Color(0,0,0,0.7f);
	
	public static BitmapFont font;
	public static float fontSize;
	
	public static final String PO_CHAR ="{";
	public static final String KE_CHAR ="}";
	public static final String PK_CHAR ="[";
	public static final String MN_CHAR ="]";	
	
	public static float VOLUME = 0.0f; //0.2f;
	public static final float VOLUME_MAX=0.5f;
	
	public static final float textReadRate_NORM = .08f;
	public static final float textReadRate_FAST = .01f;
	public static float currTextReadRate = textReadRate_NORM;
	
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
