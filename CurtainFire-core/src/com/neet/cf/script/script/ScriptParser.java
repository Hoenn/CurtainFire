package com.neet.cf.script.script;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.badlogic.gdx.utils.Array;

/*
 * TODO
 *  - lua scripts
 */
public class ScriptParser
{	
	private ScriptRunner scriptRunner;
	private ScriptController scriptController;
	private String scriptFile;
	
	private Scanner script;
	private Array<String> tokens;
	private Array<Array<String>> scriptLines; //each line in script is held in this array
	private String token;
	private int lineNumber;
	
	private boolean isCommBlock;

	public ScriptParser(String scriptFile, ScriptController scriptController)
	{
		this.scriptController = scriptController;
		scriptRunner = new ScriptRunner(scriptController);
		this.scriptFile = scriptFile;
		
		init();
		
	}
	
	public void init()
	{
		script = null;
		tokens = new Array<String>();
		token = "";
		lineNumber = 0;
		
		scriptLines = new Array<Array<String>>();
		
		isCommBlock = false;

		try
		{
			script = new Scanner(new File(System.getProperty("user.dir") + "/user_assets/scripts/" + scriptFile));
		} 
		catch (IOException e)
		{
			try
			{
				script = new Scanner(new File(System.getProperty("user.dir") + "/user_assets/scripts/default.cfl"));
			}
			catch (Exception ex)
			{
				//TODO crash log
				System.exit(1); 
			}
			//TODO crash log
		}
		if (script != null)
			parse();
	}
	
	public void tick()
	{
		if (!scriptRunner.isBusy() && lineNumber < scriptLines.size)
		{
			scriptRunner.execute(new Array<String>(scriptLines.get(lineNumber)), lineNumber);
		}
		else if (lineNumber >= scriptLines.size)
			lineNumber = 1; //infinite looping of scripts
	}

	private void parse()
	{
		while(script.hasNextLine())
		{
			try
        	{
        		token = script.next();
        	}
        	catch(NoSuchElementException e)
        	{
        		try
        		{
        			script.nextLine();
        		}
        		catch(NoSuchElementException exception)
        		{
        			System.out.println("Script Parsing Complete");
        			return;
        		}
        	}
			
			if (!isCommBlock)
			{
				if (token.equals(Keyword.END.getValue()))
	        	{
	        		scriptLines.add(new Array<String>(tokens));
	        		tokens.clear();
	        	}
	        	else if (token.startsWith(Keyword.COMMENT.getValue()))
	        	{
	        		tokens.clear();
	        		try
	        		{
	        			script.nextLine();
	        		}
	        		catch(NoSuchElementException e)
	        		{
	        			System.out.println("Script Parsing Complete");
	        			return;
	        		}
	        	}
	        	else if (token.startsWith(Keyword.COMMENTSTART.getValue()))
	        	{
	        		tokens.clear();
	        		isCommBlock = true;
	        		try
	        		{
	        			script.nextLine();
	        		}
	        		catch(NoSuchElementException e)
	        		{
	        			System.out.println("Script Parsing Complete");
	        			return;
	        		}
	        	}
	        	else if (token.equals(Keyword.PRINT.getValue()))
	        	{
	        		try
	        		{
	        			tokens.add(token);
	        			tokens.add(script.nextLine().trim());
	        			scriptLines.add(new Array<String>(tokens));
	        			tokens.clear();
	        		}
	        		catch(NoSuchElementException e)
	        		{
	        			System.out.println("Script Parsing Complete");
	        			return;
	        		}
	        	}
	        	else
	        		tokens.add(token);
			}
			else
			{
	        	if (token.startsWith(Keyword.COMMENTEND.getValue()))
	        	{
	        		tokens.clear();
	        		isCommBlock = false;
	        		try
	        		{
	        			script.nextLine();
	        		}
	        		catch(NoSuchElementException e)
	        		{
	        			System.out.println("Script Parsing Complete");
	        			return;
	        		}
	        	}
			}
		}
	}
	

	public int getLineNumber()
	{
		return lineNumber;
	}

	public void setLineNumber(int lineNumber)
	{
		this.lineNumber = lineNumber;
	}
	
	public void incrementLineNumber()
	{
		lineNumber++;
	}
}
