package com.neet.cf.util;

public class CharSequencer
{
	private char[] array;
	private StringBuilder current;
	private int currentPos;
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
		current.append(array[currentPos]);
		currentPos++;
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
