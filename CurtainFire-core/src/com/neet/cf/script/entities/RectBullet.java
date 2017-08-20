package com.neet.cf.script.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.neet.cf.CurtainFire;
import com.neet.cf.screens.BattleScreen;

/*
 * Rectangular shaped bullet with image on top
 */
public class RectBullet extends Bullet
{
	private Rectangle hitbox;
	
	
	public RectBullet(float x, float y, float width, float height, double angle, float speed, Color color)
	{
		super(x, y, angle, speed, color, -1);
		hitbox = new Rectangle(x, y, width, height);
	}
	
	public void tick(float delta)
	{
		hitbox.x += (float)(Math.cos(Math.toRadians(angle)) * (speed * delta));
		hitbox.y += (float)(Math.sin(Math.toRadians(angle)) * (speed * delta));
		
		if (offScreen())
		{
			setAlive(false);
		}
	}
	
	private boolean offScreen()
	{
		return (hitbox.x + hitbox.width < 0 || 
				hitbox.x > BattleScreen.FIELD_WIDTH ||
				hitbox.y + hitbox.height < 0 ||
				hitbox.y > BattleScreen.FIELD_HEIGHT);
	}
	
	public void draw()
	{
		//bullet image code goes above
		
		//show hitboxes when debugging
		//if (CurtainFire.DEBUG)
		//{
			CurtainFire.shapeRenderer.setColor(hitboxColor);
			CurtainFire.shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
		//}
	}
	
	public Rectangle getHitbox()
	{
		return hitbox;
	}
}
