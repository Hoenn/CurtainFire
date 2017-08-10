package com.neet.cf.script.entities;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.neet.cf.CurtainFire;
import com.neet.cf.screens.BattleScreen;
import com.neet.cf.script.script.Keyword;


/*
 * TODO
 * 	- keyword hitbox that comes after construct and is defines the width and height of hitbox.
 * 	- hitbox would be centered on enemy by default.
 *  - Player would inherit hitbox attributes from enemy if an enemy is caught and becomes a player.
 */

public class Enemy extends Entity
{
	private Globals globals;
	private LuaValue script;
	private String scriptName;
	
	private Rectangle hitbox;
	
	public Enemy(String name, String imageFile1, String imageFile2, float aniDur, float x, float y, int hp)
	{
		super(name, imageFile1, imageFile2, aniDur, x, y, hp);
	}
	
	@Override
	void init()
	{
		globals = JsePlatform.standardGlobals();
		
		script = null;
		scriptName = null;
		
		hitbox = new Rectangle();
		hitboxColor = Color.BLUE;
		hitbox.width = getWidth();
		hitbox.height = getHeight();
	}
	
	@Override
	public void act(float delta)
	{
		if (!pause)
			super.act(delta);
		//passes Enemy and delta into lua script tick function every frame
		if (script != null && !pause)
			script.call(CoerceJavaToLua.coerce(this), LuaValue.valueOf(delta));
		
		tickHitbox();
		
		checkCollision(delta);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		super.draw(batch, parentAlpha);
		
		if (CurtainFire.DEBUG)
		{
	        CurtainFire.shapeRenderer.setColor(hitboxColor);
			CurtainFire.shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
		}
	}
	
	@Override
	void tickHitbox()
	{
		hitbox.x = getX();
		hitbox.y = getY();
	}
	
	@Override
	void checkCollision(float delta)
	{
		for (int i = 0, len = BattleScreen.playerBullets.size; i < len; i++)
		{
			if (hitbox.overlaps(BattleScreen.playerBullets.get(i).getHitbox()))
			{
				decreaseHP(1); //will likely be designed to vary
				BattleScreen.playerBullets.get(i).setAlive(false);
				if (!isAlive())
					System.out.println("You won!"); //TODO implement win
			}
		}
	}

	public void setScript(String s)
	{
		if (!s.equals(Keyword.NIL.getValue()) && !s.equals(scriptName))
		{
			scriptName = s;
			globals.get("dofile").call(LuaValue.valueOf("user_assets/lua/" + s)); // initialize
			script = globals.get("tick");
		}
		else if (!s.equals(Keyword.NIL.getValue()) && s.equals(scriptName))
		{
			//prevents needless reloading of lua script
		}
		else
			script = null;
	}
	
	/*
	 * Places a bullet at the center of the enemy
	 */
	public void shoot(int radius, double angle, float speed, int index)
	{
		BattleScreen.enemyBullets.add(new CircleBullet(
				getX() + getWidth() / 2,
				getY() + getHeight() / 2,
				radius, angle, speed, index, Color.RED //colors are shouldn't be necessary if using images
		));
	}
	
	/*
	 * x and y values are numbers relative to distance from enemy
	 */
	public void shoot(float x, float y, int radius, double angle, float speed, int index)
	{
		BattleScreen.enemyBullets.add(new CircleBullet(
				getX() + getWidth() / 2 + x,
				getY() + getHeight() / 2 + y,
				radius, angle, speed, index, Color.RED
		));
	}
	
	public void addBulletAngle(double angle, int index)
	{
		for (CircleBullet e : BattleScreen.enemyBullets)
		{
			if (e.getIndex() == index)
			{
				e.setAngle(e.getAngle() + angle);
			}
		}
	}
	
	public void addBulletAngleLimit(double angle, double limit, int index)
	{
		for (CircleBullet e : BattleScreen.enemyBullets)
		{
			if (e.getIndex() == index && Math.abs(e.getAccumulatedAngleChange()) <= limit)
			{
				e.setAngle(e.getAngle() + angle);
				e.setAccumulatedAngleChange(e.getAccumulatedAngleChange() + angle);
			}
		}
	}
	
	public void changeBulletAngle(double angle, int index)
	{
		for (CircleBullet e : BattleScreen.enemyBullets)
		{
			if (e.getIndex() == index)
			{
				e.setAngle(angle);
			}
		}
	}
	
	public void addBulletSpeed(float speedChange, int index)
	{
		for (CircleBullet e : BattleScreen.enemyBullets)
		{
			if (e.getIndex() == index)
			{
				e.setSpeed(e.getSpeed() + speedChange);
			}
		}
	}
	
	public void addBulletSpeedLimitIncreasing(float speedChange, double limit, int index)
	{
		for (CircleBullet e : BattleScreen.enemyBullets)
		{
			if (e.getIndex() == index && Math.abs(e.getSpeed()) < limit)
			{
				e.setSpeed(e.getSpeed() + speedChange);
			}
		}
	}
	
	public void addBulletSpeedLimitDecreasing(float speedChange, double limit, int index)
	{
		for (CircleBullet e : BattleScreen.enemyBullets)
		{
			if (e.getIndex() == index && Math.abs(e.getSpeed()) > limit)
			{
				e.setSpeed(e.getSpeed() - speedChange);
			}
		}
	}
	
	public void changeBulletSpeed(float speed, int index)
	{
		for (CircleBullet e : BattleScreen.enemyBullets)
		{
			if (e.getIndex() == index)
			{
				e.setSpeed(speed);
			}
		}
	}

	public Rectangle getHitbox()
	{
		return hitbox;
	}
}
