package com.neet.cf.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.neet.cf.CurtainFire;
import com.neet.cf.util.CFVars;

public class NPC extends Sprite
{
	private Vector2 gridPos;
	public NPC(String imgPath, float x, float y)
	{
		super((Texture) CurtainFire.manager.get(imgPath+".png"));
		this.setX(x);
		this.setY(y);
		gridPos = new Vector2(x/CFVars.TILE_WIDTH, y/CFVars.TILE_WIDTH);
	}
	public void render(Batch batch)
	{
		this.draw(batch);
	}
	public Vector2 getGridPos()
	{
		return gridPos;
	}
}
