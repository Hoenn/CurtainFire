package com.neet.cf;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.neet.cf.handlers.GameInputProcessor;
import com.neet.cf.handlers.GameScreenManager;
import com.neet.cf.screens.GameScreen;
import com.neet.cf.util.CFVars;

public class CurtainFire extends ApplicationAdapter {
	public static AssetManager manager;
	public static BitmapFont font;
	public static GameScreen currentScreen;
	public static final float V_WIDTH = 240;
	public static final float V_HEIGHT =192;

	public static GameScreenManager gsm;
	private SpriteBatch sb;
	private OrthographicCamera cam;
	private OrthographicCamera hudCam;
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
		manager.load("player.png", Texture.class);
		manager.load("boyscout.png", Texture.class);
		manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		manager.load("map001.tmx", TiledMap.class);
		manager.load("flowerIsland.tmx", TiledMap.class);
		manager.finishLoading();
		font = new BitmapFont();
		Gdx.input.setInputProcessor(new GameInputProcessor());
		
		sb = new SpriteBatch();
		CFVars.DEFAULT_SB_COLOR=sb.getColor();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		hudCam = new OrthographicCamera();
		
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