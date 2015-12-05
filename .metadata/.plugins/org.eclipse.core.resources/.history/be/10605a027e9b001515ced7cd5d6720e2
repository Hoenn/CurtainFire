package com.neet.cf.util;

import com.badlogic.gdx.audio.Sound;
import com.neet.cf.CurtainFire;

public class CharSequencer
{
	private char[] array;
	private StringBuilder current;
	private int currentPos;
	private boolean carriageReturn=false;
	private static final char nextLineKey = '|';
	public CharSequencer(String entireString)
	{
		array = entireString.toCharArray();
		current = new StringBuilder("");
		currentPos=0;

	}
	public void addNextChar()
	{
		if(currentPos>=array.length)
			return;
		if(carriageReturn==false)
		{
			if(array[currentPos]==nextLineKey)
			{
				carriageReturn=true;
			}
			else
			{
				current.append(array[currentPos]);
			}
			currentPos++;
		}
	}
	public void setReturn()
	{
		carriageReturn=true;
	}
	public boolean needsReturn()
	{
		return carriageReturn;
	}
	public void carriageReturn()
	{
		current.setLength(0);
		carriageReturn=false;
	}
	public String getCurrent()
	{
		return current.toString();
	}
	public boolean isDone()
	{
		return currentPos>=array.length;
	}
}
