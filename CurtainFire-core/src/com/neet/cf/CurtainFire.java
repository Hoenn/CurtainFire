package com.neet.cf;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.neet.cf.handlers.GameInput;
import com.neet.cf.handlers.GameInputProcessor;
import com.neet.cf.screens.GameScreen;

public class CurtainFire extends Game {
	public static AssetManager manager;
	public static BitmapFont font;
	@Override
	public void create()
	{
		manager = new AssetManager();
		manager.load("player.png", Texture.class);
		manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		manager.load("map001.tmx", TiledMap.class);
		manager.finishLoading();
		Gdx.input.setInputProcessor(new GameInputProcessor());
		
		setScreen(new GameScreen());
	}
	@Override
	public void dispose()
	{
		super.dispose();
	}
	@Override
	public void render()
	{
	
		super.render();
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