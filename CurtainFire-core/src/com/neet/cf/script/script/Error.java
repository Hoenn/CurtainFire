package com.neet.cf.script.script;

public enum Error
{
	INDEX_DOES_NOT_EXIST("You are trying to access an index that does not exist at line "),
	INCORRECT_KEYWORD("Incorrect Keyword at line "),
	INCORRECT_ARG_NUM("Incorrect number of arguments at line "),
	INCORRECT_ARG("An argument was incorrect at line "),
	MULTI_CONSTRUCTION("You can only use the \"construct\" keyword once in a script! at line "),
	INCORRECT_INTERPOLATION("The interpolation specified does not exist! The interpolation was not added to the action at line ");
	
	private final String text;
	
	Error(String text)
	{
		this.text = text;
	}
	
	public String getText()
	{
		return text;
	}
}
