package com.neet.cf.screens;

import static com.neet.cf.util.CFVars.*;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.neet.cf.CurtainFire;
import com.neet.cf.entities.NPC;
import com.neet.cf.entities.Player;
import com.neet.cf.handlers.GameInput;
import com.neet.cf.handlers.GameScreenManager;
import com.neet.cf.handlers.OverworldGrid;
import com.neet.cf.handlers.Transition;
import com.neet.cf.handlers.Transition.TransitionType;

public class OverWorld extends GameScreen
{
	private CurtainFire cf;
	
	private static TiledMap currentMap;
	private static int currentMapHeight;
	private static int currentMapWidth;
	private final String ANIMATIONFRAMES="animatedTileset";
	private Array<NPC> NPCList;
	private Array<NPC> tempList;
	private Array<StaticTiledMapTile> flowerTiles;
	private static Array<TiledMapTile> grassTiles;
	private static TiledMapTile staticGrass;
	private static Cell currentGrass;
	private static Cell currentGrassFloor;	
	private static float elapsedTimeSinceAnimation = 0.0f;
	private static int currentAnimationFrame =0;
	private static boolean animating;
	
	private static OverworldGrid masterMap;
	
	private OrthogonalTiledMapRenderer renderer;
	private static Player player;
	public OverWorld(GameScreenManager gsm)
	{
		super(gsm);
		player = new Player();
		cam.position.set(new Vector3(player.getPosition(), 0));
		cam.update();
		changeMap("map001.tmx");
	}
	public void changeMap(String mapPath)
	{
		currentMap = CurtainFire.manager.get(mapPath);
		MapProperties props = currentMap.getProperties();
		currentMapHeight = props.get("height", Integer.class);
		currentMapWidth = props.get("width", Integer.class);

		renderer = new OrthogonalTiledMapRenderer(currentMap);

		masterMap = new OverworldGrid(currentMapWidth,currentMapHeight);
		TiledMapTileLayer specialLayer = (TiledMapTileLayer) currentMap.getLayers().get(SPECIAL_LAYER);
		for(int i=0; i<currentMapWidth; i++)
		{
			for(int j=0; j<currentMapHeight; j++)
			{
				//Create collision
				Cell c = specialLayer.getCell(i, j);
				if(c!=null)
				{
					//Add collidable to master map
					masterMap.setPos(j,i, 1);		
				}
				else
				{
					masterMap.setPos(j,i, 0);
				}
			}
		}
		
		NPCList = new Array<NPC>();
		MapLayer objLayer= currentMap.getLayers().get("Objects");
		MapObjects moList = objLayer.getObjects();
		for(MapObject mo: moList)
		{
			MapProperties properties = mo.getProperties();
			if(properties.containsKey("BATTLE"))
			{
				String imgPath = (String) properties.get("SPRITE");
				Rectangle rect = ((RectangleMapObject)mo).getRectangle();
				NPCList.add(new NPC(imgPath,rect.x, rect.y));//send scripts too
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
		grassTiles =   new Array<TiledMapTile>();
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
	public void update(float dt)
	{
		handleInput();
		player.update(Gdx.graphics.getDeltaTime());	
	}
	@Override
	public void render()
	{
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);	
		renderer.setView(cam);
	
		//+8+10 is to center camera on center of player
		Vector3 position = cam.position;
		position.x=player.getPosition().x+SPRITE_WIDTH/2;
		position.y=player.getPosition().y+SPRITE_HEIGHT/2;
		cam.position.set(position);
		cam.update();

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
		
		//Render Order: BG -> NPC/Player/NPC, Sprites -> FGd
		//Object Layer will be turned into Sprites, render then
		renderer.render(new int[]{BACKGROUND_LAYER, MIDDLEGROUND_LAYER});
	
		sb.setProjectionMatrix(cam.combined);
		sb.begin();	
		tempList = new Array<NPC>();
		for(NPC n: NPCList)
		{
			if(n.getGridPos().y>player.getGridPos().y)
				n.render(sb);
			else
				tempList.add(n);
		}
		player.draw(sb);
		//NPC Below player
		for(NPC n: tempList)
		{
			n.render(sb);;
		}
		sb.end();
		
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
			
		}
		else
		{
			TiledMapTile newTile = grassTiles.get(currentAnimationFrame);
			if(currentAnimationFrame == 0)
				currentGrassFloor.setTile(newTile);
			else
				currentGrass.setTile(newTile);
			
			currentAnimationFrame++;
		}
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

	public void handleInput()
	{
		if(GameInput.isDown(GameInput.BUTTON_R))
		{
			gsm.transitionScreens(this, gsm.START, new Transition(TransitionType.FourWaySquish));

		}
		if(GameInput.isDown(GameInput.BUTTON_F))
		{
			changeMap("flowerIsland.tmx");
		}
		if(GameInput.isDown(GameInput.BUTTON_NUM_1))
				changeMap("map001.tmx");
		player.handleMove();
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
	@Override
	public void show()
	{		
				
	}

}
