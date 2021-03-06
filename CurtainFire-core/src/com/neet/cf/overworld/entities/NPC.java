package com.neet.cf.overworld.entities;

import static com.neet.cf.overworld.util.CFVars.DEFAULT_SB_COLOR;
import static com.neet.cf.overworld.util.CFVars.SPRITE_HEIGHT;
import static com.neet.cf.overworld.util.CFVars.SPRITE_WIDTH;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.neet.cf.CurtainFire;
import com.neet.cf.overworld.util.CFVars;

public class NPC extends MapEntity
{
	private Vector2 gridPos;
	private Color currentColor;
	private String scriptPath;
	private boolean defeated;
	private TextureRegion[][] frames;
	public NPC(String imgPath, float x, float y, int direction, String sp)
	{
		CurtainFire.manager.load(imgPath, Texture.class);
		CurtainFire.manager.finishLoading();
		Texture texture = CurtainFire.manager.get(imgPath);
		frames = TextureRegion.split(texture, SPRITE_WIDTH, SPRITE_HEIGHT);

		scriptPath = sp;
		
		turn(direction);
		
		currentColor = DEFAULT_SB_COLOR;
		defeated=false;
		
		position = new Vector2(x, y);
		gridPos = new Vector2(x/CFVars.TILE_WIDTH, y/CFVars.TILE_WIDTH);
	}
	public void draw(Batch sb)
	{	
		sb.setColor(currentColor);
		sb.draw(currentFrame, position.x, position.y);
	}

	public boolean isDefeated()
	{
		return defeated;
	}
	public void setDefeated()
	{
		defeated=true;
		currentColor = Color.SLATE;
	}
	public void turn(int dir)
	{
		//only allow 0,1,2,3
		dir = dir%4;
		if(dir==CFVars.Direction.RIGHT.num())
		{
			TextureRegion right = new TextureRegion(frames[0][1]);
			right.flip(true,  false);
			currentFrame= right;
		}
		else
			currentFrame = new TextureRegion(frames[0][dir]);
	}
	public Vector2 getGridPos()
	{
		return gridPos;
	}
	public String getScript()
	{
		return scriptPath;
	}
}
