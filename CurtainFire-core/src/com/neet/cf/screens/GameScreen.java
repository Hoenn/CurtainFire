package com.neet.cf.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.neet.cf.CurtainFire;
import com.neet.cf.entities.Player;
import com.neet.cf.handlers.OverworldGrid;

public class GameScreen implements Screen
{
	private TiledMap currentMap;
	private static int currentMapHeight;
	private static int currentMapWidth;
	private final int BACKGROUND_LAYER=2;
	private final int MIDDLEGROUND_LAYER=3;
	private final int FOREGROUND_LAYER=4;
	
	private static OverworldGrid masterMap;
	
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private Player player;
	@Override
	public void show()
	{
		currentMap = CurtainFire.manager.get("map001.tmx");
		MapProperties props = currentMap.getProperties();
		currentMapHeight = props.get("height", Integer.class);
		currentMapWidth = props.get("width", Integer.class);
		camera = new OrthographicCamera();
		renderer = new OrthogonalTiledMapRenderer(currentMap);
		masterMap = new OverworldGrid(currentMapWidth,currentMapHeight);
		TiledMapTileLayer specialLayer = (TiledMapTileLayer) currentMap.getLayers().get("Special");
		
		for(int i=0; i<currentMapWidth; i++)
		{
			for(int j=0; j<currentMapHeight; j++)
			{
				Cell c = specialLayer.getCell(i, j);
				if(c!=null)
				{
					masterMap.setPos(j,i, 1);
				}
				else
				{
					masterMap.setPos(j,i, 0);
				}
			}
		}
		player = new Player();

		
				
	}
	public static boolean isOpen(int x, int y)
	{
		return masterMap.getPos(x, y)==0;
	}
	public static void printMap()
	{
		for(int i=0; i<currentMapWidth; i++)
		{
			for(int j=0;j<currentMapHeight; j++)
				System.out.print(masterMap.getPos(i, j));
			
			System.out.println();
		}
	}
	public static void addToMap(int x, int y) 
	{
		try
		{
			if(masterMap.getPos(x, y)==0)
				masterMap.setPos(x, y, 1);
			else
				throw new Exception("Occupied Space");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
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
		renderer.render(new int[]{BACKGROUND_LAYER, MIDDLEGROUND_LAYER});
		
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
