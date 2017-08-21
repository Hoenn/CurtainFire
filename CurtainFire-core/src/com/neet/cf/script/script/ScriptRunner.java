package com.neet.cf.script.script;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.neet.cf.overworld.util.CFVars;
import com.neet.cf.screens.BattleScreen;
import com.neet.cf.script.entities.Enemy;

public class ScriptRunner
{	
	private boolean busy;
	private boolean constructed;
	private ScriptController scriptController;
	
	private IntArray loopNums;
	private IntArray lineStamps;
	
	private int loopDepth;
	
	public ScriptRunner(ScriptController scriptController)
	{
		this.scriptController = scriptController;
		busy = false;
		constructed = false;
		
		loopNums = new IntArray();
		lineStamps = new IntArray();
		
		loopDepth = -1;
	}
	
	public void execute(Array<String> tokens, int lineNumber)
	{
		String firstToken = tokens.removeIndex(0);
		
		if (firstToken.equals(Keyword.CONSTRUCT.getValue()))
		{
			if (!constructed)
				construct(tokens, lineNumber);
			else
				System.out.println(Error.MULTI_CONSTRUCTION.getText() + (lineNumber + 1));
		}
		else if (firstToken.equals(Keyword.MOVE.getValue()))
			move(tokens, lineNumber);
		else if (firstToken.equals(Keyword.WAIT.getValue()))
			wait(tokens, lineNumber);
		else if (firstToken.equals(Keyword.LOOP.getValue()))
			loop(tokens, lineNumber);
		else if (firstToken.equals(Keyword.ENDLOOP.getValue()))
			endLoop(tokens, lineNumber);
		else if (firstToken.equals(Keyword.PRINT.getValue()))
			print(tokens, lineNumber);
		else if (firstToken.equals(Keyword.SHOOT.getValue()))
		{
			try
			{
				shoot(tokens, lineNumber);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
			System.out.println(Error.INCORRECT_KEYWORD.getText() + (lineNumber + 1)); //TODO print list of usable keywords
	}
	
	private void construct(Array<String> tokens, int lineNumber)
	{
		if (tokens.size == Keyword.CONSTRUCT.getArgLength())
		{
			Enemy enemy = null;
			try
			{
				enemy = new Enemy(
					tokens.get(0),
					tokens.get(1),
					Float.parseFloat(tokens.get(2)),
					CFVars.SCREEN_WIDTH / 2, 
					CFVars.SCREEN_HEIGHT / 2 + CFVars.SCREEN_HEIGHT / 4,
					Integer.parseInt(tokens.get(3))
				);
			}
			catch (NumberFormatException e)
			{
				System.out.println(Error.INCORRECT_ARG.getText() + (lineNumber + 1));
				busy = true; //stops execution of script until user fixes error
			}
			
			if (enemy != null)
			{
				BattleScreen.stage.addActor(enemy);
				scriptController.setEnemy(enemy);
				
				//BattlePanel
//				BattleScreen.battlePanel.getEnemyLabel().setName(enemy.getName());
//				BattleScreen.battlePanel.getEnemyLabel().setHP(enemy.getHP());
			}
		}
		else
		{
			System.out.println(Error.INCORRECT_ARG_NUM.getText() + (lineNumber + 1));
			busy = true; //stops execution of script until user fixes error
		}
		
		scriptController.getScriptParser().incrementLineNumber();
		
		constructed = true;
	}
	
	private void move(Array<String> tokens, int lineNumber)
	{
		if (tokens.size == Keyword.MOVE.getArgLength())
		{
			busy = true;
			Action complete = new Action(){
				@Override
				public boolean act(float delta)
				{
					busy = false;
					scriptController.getScriptParser().incrementLineNumber();
					return true;
				}
			};
					
			MoveToAction action = new MoveToAction();
			try
			{
				String arg0 = tokens.get(0);
				float x;
				if (arg0.startsWith(Keyword.RAND.getValue()))
				{
					arg0 = arg0.substring(Keyword.RAND.getValue().length());
					x = (MathUtils.random((int)Float.parseFloat(arg0)) / 2) * (MathUtils.randomBoolean() ? 1 : -1)
							+ scriptController.getEnemy().getX();
				}
				else
				{
					if (arg0.equals(Keyword.DEF.getValue()))
						x = (CFVars.SCREEN_WIDTH / 2) - (BattleScreen.scriptController.getEnemy().getWidth() / 2);
					else
						x = (Float.parseFloat(arg0) / 100.0f * CFVars.SCREEN_WIDTH) - (BattleScreen.scriptController.getEnemy().getWidth() / 2.0f);
				}
				
				String arg1 = tokens.get(1);
				float y;
				if (arg1.startsWith(Keyword.RAND.getValue()))
				{
					arg1 = arg1.substring(Keyword.RAND.getValue().length());
					y = (MathUtils.random((int)Float.parseFloat(arg1)) / 2) * (MathUtils.randomBoolean() ? 1 : -1)
							+ scriptController.getEnemy().getY();
				}
				else
				{
					if (arg1.equals(Keyword.DEF.getValue()))
						y = CFVars.SCREEN_HEIGHT / 2 + CFVars.SCREEN_HEIGHT / 4;
					else
						y = (Float.parseFloat(arg1) / 100.0f * CFVars.SCREEN_HEIGHT) - (BattleScreen.scriptController.getEnemy().getHeight() / 2.0f);
				}

				action.setPosition(x, y);
				action.setDuration(Float.parseFloat(tokens.get(2)));
				try
				{
					action.setInterpolation((Interpolation)ClassReflection.forName("com.badlogic.gdx.math.Interpolation").getField(tokens.get(3)).get(null));
				} 
				catch (Exception e)
				{
					System.out.println(Error.INCORRECT_INTERPOLATION.getText() + (lineNumber + 1));
				}
		
				scriptController.getEnemy().addAction(Actions.sequence(action, complete));
			}
			catch (NumberFormatException e)
			{
				System.out.println(Error.INCORRECT_ARG.getText() + (lineNumber + 1));
				busy = true; //stops execution of script until user fixes error
			}
		}
		else
		{
			System.out.println(Error.INCORRECT_ARG_NUM.getText() + (lineNumber + 1));
			busy = true; //stops execution of script until user fixes error
		}
	}
	
	private void wait(Array<String> tokens, int lineNumber)
	{
		if (tokens.size == Keyword.WAIT.getArgLength())
		{
			busy = true;
			Action complete = new Action(){
				@Override
				public boolean act(float delta)
				{
					busy = false;
					scriptController.getScriptParser().incrementLineNumber();
					return true;
				}
			};
					
			DelayAction action = null;
			try
			{
				action = new DelayAction(Float.parseFloat(tokens.first()));
				scriptController.getEnemy().addAction(Actions.sequence(action, complete));
			}
			catch (NumberFormatException e)
			{
				System.out.println(Error.INCORRECT_ARG.getText() + (lineNumber + 1));
				busy = true; //stops execution of script until user fixes error
			}
		}
		else
		{
			System.out.println(Error.INCORRECT_ARG_NUM.getText() + (lineNumber + 1));
			busy = true; //stops execution of script until user fixes error
		}
	}
	
	private void loop(Array<String> tokens, int lineNumber)
	{
		if (tokens.size == Keyword.LOOP.getArgLength())
		{
			try
			{
				loopNums.add(Integer.parseInt(tokens.first()));
				loopDepth++;
			}
			catch (NumberFormatException e)
			{
				System.out.println(Error.INCORRECT_ARG.getText() + (lineNumber + 1));
				busy = true; //stops execution of script until user fixes error
			}
			lineStamps.add(lineNumber);
		}
		else
		{
			System.out.println(Error.INCORRECT_ARG_NUM.getText() + (lineNumber + 1));
			busy = true; //stops execution of script until user fixes error
		}
		
		scriptController.getScriptParser().incrementLineNumber();
	}
	
	private void endLoop(Array<String> tokens, int lineNumber)
	{
		if (tokens.size == Keyword.ENDLOOP.getArgLength())
		{
			if (loopNums.get(loopDepth) > 1)
			{
				scriptController.getScriptParser().setLineNumber(lineStamps.get(loopDepth));
				loopNums.set(loopDepth, loopNums.get(loopDepth) - 1);
			}
			else
			{
				loopNums.pop();
				lineStamps.pop();
				loopDepth--;
			}
		}
		else
		{
			System.out.println(Error.INCORRECT_ARG_NUM.getText() + (lineNumber + 1));
			busy = true; //stops execution of script until user fixes error
		}
		
		scriptController.getScriptParser().incrementLineNumber();
		
		busy = false;
	}

	private void print(Array<String> tokens, int lineNumber)
	{
		if (tokens.size == Keyword.PRINT.getArgLength())
		{
			System.out.println(tokens.first());
		}
		
		scriptController.getScriptParser().incrementLineNumber();
	}
	
	private void shoot(Array<String> tokens, int lineNumber)
	{
		if (tokens.size == Keyword.SHOOT.getArgLength())
		{
			if (tokens.get(2).equals(Keyword.ON.getValue()) || tokens.get(2).equals(Keyword.OFF.getValue()))
			{				
				try
				{
					scriptController.getEnemy().setScript(tokens.get(0), Integer.parseInt(tokens.get(1)), tokens.get(2), lineNumber);
				}
				catch (NumberFormatException e)
				{
					System.out.println(Error.INCORRECT_ARG.getText() + (lineNumber + 1));
					busy = true; //stops execution of script until user fixes error
				}
			}
			else
			{
				System.out.println(Error.INCORRECT_ARG.getText() + (lineNumber + 1));
				busy = true; //stops execution of script until user fixes error
			}	
		}
		else
		{
			System.out.println(Error.INCORRECT_ARG_NUM.getText() + (lineNumber + 1));
			busy = true; //stops execution of script until user fixes error
		}
		
		scriptController.getScriptParser().incrementLineNumber();
	}
	
	public void setBusy(boolean busy)
	{
		this.busy = busy;
	}

	public boolean isBusy()
	{
		return busy;
	}
}
