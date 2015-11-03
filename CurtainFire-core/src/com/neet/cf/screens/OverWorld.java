package com.neet.cf.screens;

import java.util.Iterator;

import static com.neet.cf.util.CFVars.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.neet.cf.CurtainFire;
import com.neet.cf.entities.Player;
import com.neet.cf.handlers.GameInput;
import com.neet.cf.handlers.OverworldGrid;
import com.neet.cf.screens.TransitionScreen.TransitionType;

public class OverWorld implements Screen
{
	private CurtainFire cf;
	
	private static TiledMap currentMap;
	private static int currentMapHeight;
	private static int currentMapWidth;
	private final String ANIMATIONFRAMES="animatedTileset";
	private Array<StaticTiledMapTile> flowerTiles;
	private static Array<TiledMapTile> grassTiles = new Array<TiledMapTile>();
	private static TiledMapTile staticGrass;
	private static Cell currentGrass;
	private static Cell currentGrassFloor;	
	private static float elapsedTimeSinceAnimation = 0.0f;
	private static int currentAnimationFrame =0;
	private static boolean animating = false;
	
	private static OverworldGrid masterMap;
	
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private static Player player;
	public OverWorld(CurtainFire game)
	{
		cf=game;
		player = new Player();
		currentMap = CurtainFire.manager.get("map001.tmx");
		MapProperties props = currentMap.getProperties();
		currentMapHeight = props.get("height", Integer.class);
		currentMapWidth = props.get("width", Integer.class);
		camera = new OrthographicCamera();
		camera.zoom = 0.5f;
		renderer = new OrthogonalTiledMapRenderer(currentMap);
		masterMap = new OverworldGrid(currentMapWidth,currentMapHeight);
		TiledMapTileLayer specialLayer = (TiledMapTileLayer) currentMap.getLayers().get(SPECIAL_LAYER);
		
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
		//Get a copy of the non animated grass tile
		Iterator<TiledMapTile> tiles = currentMap.getTileSets().getTileSet("tileset").iterator();
		boolean found = false;
		while(!found&&tiles.hasNext())
		{
			TiledMapTile tile = tiles.next();
			if(tile.getProperties().containsKey("GRASS"))
			{
				if(staticGrass==null)
				{
					found=true;
					staticGrass=tile;
				}
			}
			
		}
		//Make an animated tile out of the flower tile frames
		//Gather frames for grass animation
		flowerTiles = new Array<StaticTiledMapTile>();
		tiles = currentMap.getTileSets().getTileSet(ANIMATIONFRAMES).iterator();
		while(tiles.hasNext())
		{
			TiledMapTile tile = tiles.next();

			if(tile.getProperties().containsKey("FLOWER"))
			{
				flowerTiles.add((StaticTiledMapTile)tile);
			}
			else if(tile.getProperties().containsKey("GRASS"))
			{
				grassTiles.add(tile);
			}
		}
		AnimatedTiledMapTile animatedTile = new AnimatedTiledMapTile(FLOWER_ANIMATION_SPEED , flowerTiles);
		TiledMapTileLayer layer = (TiledMapTileLayer) currentMap.getLayers().get(BACKGROUND_LAYER);
		for(int x = 0; x<layer.getWidth(); x++)
			for(int y =0; y<layer.getHeight(); y++)
			{
				Cell cell = layer.getCell(x, y);
				if(cell!=null&&cell.getTile().getProperties().containsKey("FLOWER"))
				{
					cell.setTile(animatedTile);
				}
			}
		
		

	}
	@Override
	public void show()
	{		
				
	}
	@Override
	public void render(float delta)
	{

		handleInput();
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);	
		renderer.setView(camera);

		
		AnimatedTiledMapTile.updateAnimationBaseTime();
		
		if(currentGrass!=null )
		{

			if(animating)
			{
				elapsedTimeSinceAnimation += Gdx.graphics.getDeltaTime();
		        	if(elapsedTimeSinceAnimation > GRASS_ANIMATION_SPEED){
		        		updateGrassAnimation();
		        		elapsedTimeSinceAnimation = 0.0f;
		        	}
			}

		}
		
		//Render Order: BG -> Player, Sprites -> FGd
		//Object Layer will be turned into Sprites, render then
		renderer.render(new int[]{BACKGROUND_LAYER, MIDDLEGROUND_LAYER});

		
		Vector3 position = camera.position;
		position.x += (player.getPosition().x - position.x) * OVERWORLDCAMERA_LERP;
		position.y += (player.getPosition().y - position.y) *OVERWORLDCAMERA_LERP;
		camera.position.set(position);
		
		
		camera.update();

		renderer.getBatch().begin();	
			player.draw(renderer.getBatch());
		renderer.getBatch().end();
		
		renderer.render(new int[]{FOREGROUND_LAYER});
		}
	
	private void updateGrassAnimation()
	 {
		if (currentAnimationFrame >= grassTiles.size)
		{
			//End of animation
			animating=false;
			elapsedTimeSinceAnimation=0;
			currentAnimationFrame=0;
			return;
		}
	
		TiledMapTile newTile = grassTiles.get(currentAnimationFrame);
		if(currentAnimationFrame == 0)
			currentGrassFloor.setTile(newTile);
		else
			currentGrass.setTile(newTile);
		
		currentAnimationFrame++;
	 }
	    
	public static void setGrassAnimation(int x, int y)
	{
		TiledMapTileLayer lower = (TiledMapTileLayer) currentMap.getLayers().get(BACKGROUND_LAYER);
		TiledMapTileLayer upper = (TiledMapTileLayer) currentMap.getLayers().get(FOREGROUND_LAYER);
		Cell target = new Cell();
		upper.setCell(x, y, target);
		currentGrass=target;
		
		Cell targetLower = new Cell();
		targetLower.setTile(staticGrass);
		targetLower.getTile().getProperties().put("GRASS", null);
		lower.setCell(x, y, targetLower);
		
		animating = true;
		currentAnimationFrame=0;
		elapsedTimeSinceAnimation=0;
		currentGrassFloor = targetLower;
	}
	public static void replaceGrassTile(int x, int y)
	{
		TiledMapTileLayer lower = (TiledMapTileLayer) currentMap.getLayers().get(BACKGROUND_LAYER);
		TiledMapTileLayer upper = (TiledMapTileLayer) currentMap.getLayers().get(FOREGROUND_LAYER);
		Cell target = new Cell();
		upper.setCell(x, y, target);
		target.setTile(null);
		
		currentGrass=null;
		
		Cell targetLower = new Cell();
		targetLower.setTile(staticGrass);
		targetLower.getTile().getProperties().put("GRASS", null);
		lower.setCell(x, y, targetLower);
	}
	public static boolean isOpen(int x, int y)
	{
		if(x<0||x>=currentMapWidth)
			return false;
		if(y<0||y>=currentMapHeight)
			return false;

		return masterMap.getPos(y, x)==0;
	}
	public static void handleGrass(int x, int y)
	{
		TiledMapTileLayer layer = (TiledMapTileLayer) currentMap.getLayers().get(BACKGROUND_LAYER);
		Cell targetCell = layer.getCell(x, y);
		Vector2 grid = player.getGridPos();
		Cell currentCell = layer.getCell((int)grid.x,(int) grid.y);
		if(currentCell!=null && currentCell.getTile().getProperties().containsKey("GRASS"))
		{		
			replaceGrassTile((int) grid.x,(int)grid.y);
		}
		if(targetCell!=null &&targetCell.getTile().getProperties().containsKey("GRASS"))
		{
			setGrassAnimation(x, y);
		}
		
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

	private void handleInput()
	{
		if(GameInput.isDown(GameInput.BUTTON_R))
		{
			cf.setScreen(new TransitionScreen(cf, this, TransitionType.FadeOut));
		}
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
		//currentMap.dispose();
		//renderer.dispose();

	}

}
