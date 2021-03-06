package com.neet.cf.script.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.neet.cf.CurtainFire;
import com.neet.cf.screens.BattleScreen;
/*
 * Circular shaped bullet with image on top
 */
public class CircleBullet extends Bullet
{
	private Circle hitbox;
	
	public CircleBullet(float x, float y, int radius, double angle, float speed, int index, Color color)
	{
		super(x, y, angle, speed, color, index);
		hitbox = new Circle(x, y, radius);
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
		return (hitbox.x + hitbox.radius < 0 || 
				hitbox.x > BattleScreen.FIELD_WIDTH ||
				hitbox.y + hitbox.radius < 0 ||
				hitbox.y > BattleScreen.FIELD_HEIGHT);
	}
	
	public void draw()
	{
		//bullet image code goes above
		
		//show hitboxes when debuggins
		//if (CurtainFire.DEBUG)
		//{
			CurtainFire.shapeRenderer.setColor(hitboxColor);
			CurtainFire.shapeRenderer.circle(hitbox.x, hitbox.y, hitbox.radius);
		//}
	}
	
	public Circle getHitbox()
	{
		return hitbox;
	}
}
