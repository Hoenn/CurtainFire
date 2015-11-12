package com.neet.cf.entities;

import static com.neet.cf.util.CFVars.SPRITE_HEIGHT;
import static com.neet.cf.util.CFVars.SPRITE_WIDTH;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.neet.cf.CurtainFire;
import com.neet.cf.handlers.Animation;
import com.neet.cf.util.CFVars;

public class NPC
{
	private Vector2 gridPos;
	private Vector2 position;
	private TextureRegion currentFrame;
	public NPC(String imgPath, float x, float y, int direction)
	{
		Texture texture = CurtainFire.manager.get(imgPath);
		TextureRegion[][] temp = TextureRegion.split(texture, SPRITE_WIDTH, SPRITE_HEIGHT);

		TextureRegion idleFrame=null;
		if(direction==CFVars.Direction.RIGHT.num())
		{
			TextureRegion right = new TextureRegion(temp[0][1]);
			right.flip(true,  false);
			idleFrame = right;
		}
		else
			idleFrame = new TextureRegion(temp[0][direction]);
	
		currentFrame = idleFrame;
		
		position = new Vector2(x, y);
		gridPos = new Vector2(x/CFVars.TILE_WIDTH, y/CFVars.TILE_WIDTH);
	}
	public void render(Batch sb)
	{
		
		sb.setColor(CFVars.DEFAULT_SHADOW_COLOR);
		sb.draw(currentFrame, position.x+2, position.y+2, 0, 0, 16, 10, 1.2f, 1.9f,0);
		sb.setColor(CFVars.DEFAULT_SB_COLOR);
		sb.draw(currentFrame, position.x, position.y);
	}
	public Vector2 getGridPos()
	{
		return gridPos;
	}
}
