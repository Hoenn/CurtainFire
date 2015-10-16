package com.neet.cf.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.neet.cf.CurtainFire;
import com.neet.cf.entities.Player;
import com.neet.cf.handlers.GameInput;
import com.neet.cf.handlers.GameInputProcessor;

public class GameScreen implements Screen
{
	private TiledMap currentMap;
	private final int BACKGROUND_LAYER=2;
	private final int FOREGROUND_LAYER=3;
	
	
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private Player player;
	@Override
	public void show()
	{
		currentMap = CurtainFire.manager.get("map001.tmx");
		camera = new OrthographicCamera();
		renderer = new OrthogonalTiledMapRenderer(currentMap);
		player = new Player();

	}

	@Override
	public void render(float delta)
	{
		handleInput();
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.setView(camera);

		//Render Order: BG -> Player, Sprites -> FG
		//Object Layer will be turned into Sprites, render then
		renderer.render(new int[]{BACKGROUND_LAYER});
		
		//camera.position.set(player.getX(), player.getY(), 0);
	    //camera.update();

		renderer.getBatch().begin();
		player.draw(renderer.getBatch());
		renderer.getBatch().end();
		
		renderer.render(new int[]{FOREGROUND_LAYER});
	}
	private void handleInput()
	{
		player.handleMove();
	}
	@Override
	public void resize(int width, int height)
	{
		camera.viewportWidth=width;
		camera.viewportHeight=height;
		camera.update();
	}

	@Override
	public void pause()
	{
		
	}

	@Override
	public void resume()
	{
		
	}

	@Override
	public void hide()
	{
		dispose();
	}

	@Override
	public void dispose()
	{
		currentMap.dispose();
		renderer.dispose();

	}

}
