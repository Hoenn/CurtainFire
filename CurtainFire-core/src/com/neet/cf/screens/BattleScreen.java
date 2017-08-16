package com.neet.cf.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.neet.cf.CurtainFire;
import com.neet.cf.handlers.GameScreenManager;
import com.neet.cf.handlers.Transition;
import com.neet.cf.handlers.Transition.TransitionType;
import com.neet.cf.overworld.entities.NPC;
import com.neet.cf.overworld.util.CFVars;
import com.neet.cf.script.debug.DebugController;
import com.neet.cf.script.entities.CircleBullet;
import com.neet.cf.script.entities.Player;
import com.neet.cf.script.entities.RectBullet;
import com.neet.cf.script.facilties.PlayerInput;
import com.neet.cf.script.script.ScriptController;
import com.neet.cf.script.ui.BattlePanel;

public class BattleScreen extends GameScreen
{	
	public static Stage stage;
	public static final int MENU_WIDTH = 170;
	public static float FIELD_WIDTH;
	public static float FIELD_HEIGHT;
	public static boolean INVOKE_GC; //only runs garbage collection when one or more bullet states that it is not alive
	
	public static NPC overworldNPCSprite;

	public static String scriptFile;
	
	private final float DELTA_LIMIT = 0.030f;
	public static ScriptController scriptController; //plan on using array, every instance of scriptController should control an indiviual instance of Enemy
	
	protected DebugController debugController;
	
	public static Array<CircleBullet> enemyBullets;
	public static Array<RectBullet> playerBullets;
	
	public static BattlePanel battlePanel;
	
	public static Player player;
	
	private float battleEndTimer;
	private float battleEndTime;
	private boolean enemyDefeatedSoundPlayed;
	
	private Music music;
	
	public BattleScreen(GameScreenManager gsm)
	{	
		super(gsm);
		stage = new Stage(new ScreenViewport());
		
		//FIELD_WIDTH = stage.getWidth() - MENU_WIDTH;
		FIELD_WIDTH = stage.getWidth();
		FIELD_HEIGHT = stage.getHeight();
		
		INVOKE_GC = false;
		
		scriptController = new ScriptController(scriptFile);
		
		//Menu Widget
		
		//battlePanel = new BattlePanel(stage);
		
		//Menu Widget
		
		Gdx.input.setInputProcessor(stage);
		
		enemyBullets = new Array<CircleBullet>();
		playerBullets = new Array<RectBullet>();
		
		if (CurtainFire.DEBUG)
		{
			//battlePanel.setDebug(true);
			debugController = new DebugController(scriptController);
		}
		//temporary
		player = new Player("Beedrill", "Beedrill_1.png", "Beedrill_2.png", 
				0.6f, CFVars.SCREEN_WIDTH / 2, CFVars.SCREEN_HEIGHT / 2 - CFVars.SCREEN_HEIGHT / 4, 3);
		
		stage.addActor(player);
		
		Gdx.input.setInputProcessor(new PlayerInput(player));
		
		battleEndTimer = 0;
		battleEndTime = 2.5f; //time gap between screen transition
		
		enemyDefeatedSoundPlayed = false;
		
		music = CurtainFire.manager.get("Boss_Battle.ogg", Music.class);
		music.setLooping(true);
		
		music.setVolume(CFVars.VOLUME);
		music.play();
	}

	@Override
	public void show()
	{
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * Game Logic goes here
	 */
	public void tick(float delta)
	{
		scriptController.tick();
		if (CurtainFire.DEBUG)
			debugController.tick();
		if (enemyBullets.size > 0)
		{
			
			for (CircleBullet e : enemyBullets)
				if (!scriptController.isPause())
					e.tick(delta);
			
			for (RectBullet e : playerBullets)
				e.tick(delta);
			
		}		
		if (player.getHP() <= 0)
		{
			battleEndTimer += delta;
			player.setInvincible(true);
			player.setShootable(false);
			if (battleEndTimer >= battleEndTime)
			{
				player.remove();
				Gdx.input.setInputProcessor(CurtainFire.inputProc);
				gsm.setScreen(gsm.OVERWORLD, new Transition(TransitionType.SplitOut), false);
			}
		}
		
		if (scriptController.getEnemy().getHP() <= 0)
		{
			battleEndTimer += delta;
			if (!enemyDefeatedSoundPlayed)
			{
				CurtainFire.manager.get("enemy_defeated.ogg", Sound.class).play(CFVars.VOLUME / 2);
				enemyDefeatedSoundPlayed = true;
			}
			scriptController.getEnemy().remove();
			player.setInvincible(true); //so stray bullets don't accidently defeat you
			if (battleEndTimer >= battleEndTime)
			{
				Gdx.input.setInputProcessor(CurtainFire.inputProc);
				gsm.setScreen(gsm.OVERWORLD, new Transition(TransitionType.SplitOut), false);
				//Proof of concept
				overworldNPCSprite.setDefeated();
			}
		}
		
		if (INVOKE_GC)
		{
			garbageCollection();
		}
	}
	
	protected void garbageCollection()
	{
		INVOKE_GC = false;
		for (int i = 0, len = enemyBullets.size; i < len; i++)
		{
			if (!enemyBullets.get(i).isAlive())
			{
				enemyBullets.removeIndex(i);
				i--;
				len--;
			}
		}
		for (int i = 0, len = playerBullets.size; i < len; i++)
		{
			if (!playerBullets.get(i).isAlive())
			{
				playerBullets.removeIndex(i);
				i--;
				len--;
			}
		}
	}

	@Override
	public void update(float delta)
	{
		if (delta < DELTA_LIMIT)
		{	
			stage.act(delta);
			tick(delta);
			handleInput();
		}
	}

	@Override
	public void resize(int width, int height)
	{
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose()
	{
		stage.dispose();
		music.stop();
		music.dispose();
	}

	public String getScriptFile()
	{
		return scriptFile;
	}

	public void setScriptFile(String scriptFile)
	{
		this.scriptFile = scriptFile;
	}

	@Override
	public void handleInput()
	{
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE))
		{
			gsm.prevScreen=gsm.BATTLE;
			gsm.transitionScreens(this, gsm.OPTIONS, new Transition(TransitionType.RectDown));
		}		
		if (Gdx.input.isKeyJustPressed(Keys.BACKSPACE) && CurtainFire.DEBUG)
		{
			Gdx.input.setInputProcessor(CurtainFire.inputProc);
			gsm.setScreen(gsm.OVERWORLD, new Transition(TransitionType.SplitOut), false);
			//Proof of concept
			overworldNPCSprite.setDefeated();
		}
	}

	@Override
	public void render()
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		CurtainFire.shapeRenderer.begin(ShapeType.Line);
		stage.draw();
		for (CircleBullet e : enemyBullets)
			e.draw();
		for (RectBullet e : playerBullets) 
			e.draw();
		CurtainFire.shapeRenderer.end();
		CurtainFire.sBatch.begin();
		if (CurtainFire.DEBUG)
			debugController.render();
		


		//battlePanel.getMenuTable().iDraw(CurtainFire.sBatch);
		CurtainFire.sBatch.end();
	}

}
