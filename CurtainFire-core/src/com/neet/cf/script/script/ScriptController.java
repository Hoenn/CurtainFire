package com.neet.cf.script.script;

import com.neet.cf.screens.BattleScreen;
import com.neet.cf.script.entities.Enemy;

public class ScriptController
{
	private ScriptParser scriptParser;
	private Enemy enemy; //only one enemy per script! 3 enemies on screen = 3 instances of ScriptController
	private String scriptFile;
	private boolean pause;
	
	public ScriptController(String scriptFile)
	{
		setEnemy(null);
		scriptParser = new ScriptParser(scriptFile, this);
		this.scriptFile = scriptFile;
		pause = false;
	}
	
	/*
	 * Resets and clears entire stage
	 */
	public void reset()
	{
		setEnemy(null);
		scriptParser = new ScriptParser(scriptFile, this);
		BattleScreen.stage.getActors().removeIndex((Enemy.getEnemyIndex())); //TODO support multiple enemies
		BattleScreen.enemyBullets.clear();
	}
	
	public void tick()
	{
		scriptParser.tick();
	}

	public Enemy getEnemy()
	{
		return enemy;
	}

	public void setEnemy(Enemy enemy)
	{
		this.enemy = enemy;
	}
	
	public ScriptParser getScriptParser()
	{
		return scriptParser;
	}

	public void setScriptParser(ScriptParser scriptParser)
	{
		this.scriptParser = scriptParser;
	}
	
	public void pause()
	{
		pause = !pause;
		getEnemy().setPause(pause); //TODO support multiple enemies
	}

	public boolean isPause()
	{
		return pause;
	}

	public void setPause(boolean pause)
	{
		this.pause = pause;
	}
}
