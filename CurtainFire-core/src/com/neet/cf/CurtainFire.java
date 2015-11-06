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

public class CurtainFire extends ApplicationAdapter {
	public static AssetManager manager;
	public static BitmapFont font;
	public static GameScreen currentScreen;
	public static float height=480;
	public static float width = 640;
	public static GameScreenManager gsm;
	private SpriteBatch sb;
	private OrthographicCamera cam;
	private OrthographicCamera hudCam;
	@Override
	public void create()
	{
		manager = new AssetManager();
		manager.load("player.png", Texture.class);
		manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		manager.load("map001.tmx", TiledMap.class);
		manager.finishLoading();
		font = new BitmapFont();
		Gdx.input.setInputProcessor(new GameInputProcessor());
		sb = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, width/2, height/2);
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