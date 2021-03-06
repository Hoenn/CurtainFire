package com.neet.cf;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.neet.cf.handlers.GameInputProcessor;
import com.neet.cf.handlers.GameScreenManager;
import com.neet.cf.overworld.util.CFVars;
import com.neet.cf.overworld.util.DialogBox;
import com.neet.cf.screens.GameScreen;

public class CurtainFire extends ApplicationAdapter {
	public static AssetManager manager;
	public static GameScreen currentScreen;
	public static final float V_WIDTH = 240;
	public static final float V_HEIGHT =192;
	public static boolean DEBUG = false;
	public static String testScript = "weezing.cfl";
	
	public static final GameInputProcessor inputProc = new GameInputProcessor();

	public static GameScreenManager gsm;
	private SpriteBatch sb;
	private OrthographicCamera cam;
	private OrthographicCamera hudCam;
	
	//Fix this
	public static SpriteBatch sBatch;
	public static ShapeRenderer shapeRenderer;
	public CurtainFire(float width, float height)
	{
		CFVars.SCREEN_WIDTH=width;
		CFVars.SCREEN_HEIGHT=height;
		CFVars.V_WIDTH=V_WIDTH;
		CFVars.V_HEIGHT=V_HEIGHT;
	}
	@Override
	public void create()
	{
		manager = new AssetManager();
		loadAssets();
		manager.finishLoading();
		
		CFVars.font = manager.get("cfFont.fnt", BitmapFont.class);
	
		
		Gdx.input.setInputProcessor(inputProc);
		
		DialogBox.box= manager.get("textBoxPurple.png", Texture.class);
		
		sb = new SpriteBatch();
		CFVars.DEFAULT_SB_COLOR=sb.getColor();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, CFVars.SCREEN_WIDTH,CFVars.SCREEN_HEIGHT);
		
		sBatch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
		
		manager.get("pcboot.ogg", Sound.class).play(CFVars.VOLUME);
		gsm = new GameScreenManager(this);
	}
	public SpriteBatch getSpriteBatch()
	{
		return sb;
	}
	
	public OrthographicCamera getCamera()
	{
		return cam;
	}
	
	public OrthographicCamera getHUDCamera()
	{
		return hudCam;
	}
	private void loadAssets()
	{
		manager.load("player.png", Texture.class);
		manager.load("textBoxPurple.png", Texture.class);
		manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		manager.load("map001.tmx", TiledMap.class);
		manager.load("flowerIsland.tmx", TiledMap.class);
		manager.load("DemoMap.tmx", TiledMap.class);
		manager.load("cfFont.fnt", BitmapFont.class);
		//manager.load("uiskin.json", Skin.class);
		manager.load("pcboot.ogg", Sound.class);
		manager.load("blip.ogg", Sound.class);
		manager.load("pause.ogg", Sound.class);
		manager.load("unpause.ogg", Sound.class);
		manager.load("testMusic.ogg", Music.class);
		manager.load("enemy_defeated.ogg", Sound.class);
		manager.load("playerHit.ogg", Sound.class);
		manager.load("se_damage01.ogg", Sound.class);
		manager.load("Boss_Battle.ogg", Music.class);
	}
	public void render()
	{
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render();

	}
	@Override
	public void dispose()
	{
		super.dispose();
	}

	@Override
	public void resize(int width, int height)
	{
		super.resize(width, height);
	}
	@Override
	public void pause()
	{
		super.pause();
	}
	@Override
	public void resume() 
	{
		super.resume();
	}

	
	
}