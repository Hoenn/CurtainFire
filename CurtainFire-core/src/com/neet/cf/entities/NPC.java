package com.neet.cf.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.neet.cf.CurtainFire;

public class NPC extends Sprite
{

	public NPC(String imgPath, float x, float y)
	{
		super((Texture) CurtainFire.manager.get(imgPath+".png"));
		this.setX(x);
		this.setY(y);
	}
	public void render(Batch batch)
	{
		
		this.draw(batch);
	}
}
