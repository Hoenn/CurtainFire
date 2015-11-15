package com.neet.cf.handlers;

public class GameInput
{
	public static boolean[] keys;
	public static boolean[] pkeys;
	
	public static final int NUM_KEYS=16;
	
	//Movement
	public static final int BUTTON_UP=0;
	public static final int BUTTON_LEFT=1;
	public static final int BUTTON_DOWN=2;
	public static final int BUTTON_RIGHT=3;
	//Actions
	public static final int BUTTON_SPACE=4;
	public static final int BUTTON_Z=5;
	public static final int BUTTON_X=6;
	public static final int BUTTON_C=7;
	public static final int BUTTON_R=8;
	public static final int BUTTON_F=9;
	public static final int BUTTON_NUM_1=10;
	public static final int BUTTON_NUM_2=11;
	public static final int BUTTON_NUM_3=12;
	public static final int BUTTON_NUM_4=13;
	public static final int BUTTON_NUM_5=14;
	//Menu related buttons
	public static final int BUTTON_ESCAPE=15;
	
	static{
		keys=new boolean[NUM_KEYS];
		pkeys=new boolean[NUM_KEYS];
	}
	public static void update()
	{
		for(int i=0; i<NUM_KEYS;i++)
		{
			pkeys[i]=keys[i];
		}
	}
	public static void setKey(int i, boolean b)
	{
		keys[i]=b;
	}
	public static boolean isDown(int i)
	{
		return keys[i];
	}
	public static boolean isPressed(int i)
	{
		return keys[i] && !pkeys[i];
	}
}