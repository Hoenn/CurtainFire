package com.neet.cf.script.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.neet.cf.CurtainFire;
import com.neet.cf.overworld.util.CFVars;
import com.neet.cf.screens.BattleScreen;
import com.neet.cf.script.script.ScriptController;

public class DebugController
{
	ScriptController scriptController;
	
	public DebugController(ScriptController scriptController)
	{
		this.scriptController = scriptController;
	}
	
	public void tick()
	{
		if (Gdx.input.isKeyJustPressed(Keys.R) && !scriptController.isPause())
		{
			//Resets the script
			System.out.println("Script reset.");
			scriptController.reset();
		}
		else if (Gdx.input.isKeyJustPressed(Keys.SPACE))
		{
			scriptController.pause();
		}
		else if (Gdx.input.isKeyJustPressed(Keys.BACKSPACE) && !scriptController.isPause())
		{
			for (Actor actor : BattleScreen.stage.getActors())
			{
				for (Action action : actor.getActions())
				{
					if (action instanceof SequenceAction)
					{
						// Most definitely needs refactoring
						if (((SequenceAction) action).getActions().first() instanceof TemporalAction)
							((TemporalAction)((SequenceAction) action).getActions().first()).finish();
						else if (((SequenceAction) action).getActions().first() instanceof DelayAction)
							((DelayAction)((SequenceAction) action).getActions().first()).finish();
					}
				}
			}
		} 
	}
	
	public void render()
	{
		CFVars.font.draw(CurtainFire.sBatch, 
				"Line Number: " + (scriptController.getScriptParser().getLineNumber() + 1), 
				30, Gdx.graphics.getHeight() - 30);
		if (scriptController.isPause())
		{
			CFVars.font.draw(CurtainFire.sBatch, 
				"Paused", 
				30, Gdx.graphics.getHeight() - 50);
		}
	}
}
