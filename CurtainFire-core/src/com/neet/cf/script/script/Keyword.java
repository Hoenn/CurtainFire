package com.neet.cf.script.script;

public enum Keyword
{
	CONSTRUCT("construct", 7),
	MOVE("move", 4),
	WAIT("wait", 1),
	SHOOT("shoot", 1),
	NIL("nil", 0),
	END("end", 0),
	SPACE("_", 0), //TODO add support for space
	LOOP("loop", 1),
	ENDLOOP("endloop", 0),
	COMMENT("--", 0),
	COMMENTSTART("-*", 0),
	COMMENTEND("*-", 0),
	RAND("rand", 0),
	PRINT("print", 1);
	
	private final String value;
	private final int argLength;
	Keyword(String value, int argLength)
	{
		this.value = value;
		this.argLength = argLength;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public int getArgLength()
	{
		return argLength;
	}
	
}