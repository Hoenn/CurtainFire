package com.neet.cf.script.entities;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.neet.cf.CurtainFire;
import com.neet.cf.overworld.util.CFVars;
import com.neet.cf.screens.BattleScreen;
import com.neet.cf.script.script.Error;
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
	private Array<LuaValue> scripts;
	private String scriptName;
	
	private Rectangle hitbox;
	
	private Color bulletColor;
	
	public Enemy(String imageFile1, String imageFile2, float aniDur, float x, float y, int hp)
	{
		super(imageFile1, imageFile2, aniDur, x, y, hp);
	}
	
	@Override
	void init()
	{
		globals = JsePlatform.standardGlobals();
		
		scripts = new Array<LuaValue>();
		scriptName = null;
		
		hitbox = new Rectangle();
		hitboxColor = Color.BLUE;
		hitbox.width = getWidth();
		hitbox.height = getHeight();
		
		bulletColor = Color.WHITE; //default
	}
	
	@Override
	public void act(float delta)
	{
		if (!pause)
			super.act(delta);
		//passes Enemy and delta into lua script tick function every frame
		LuaValue script;
		for (int i = 0; i < scripts.size; i++)
		{
			script = scripts.get(i);
			
			if (script != null)
				script.call(CoerceJavaToLua.coerce(this), LuaValue.valueOf(delta));
		}
		
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
				CurtainFire.manager.get("se_damage01.ogg", Sound.class).play(CFVars.VOLUME);
				BattleScreen.playerBullets.get(i).setAlive(false);
			}
		}
	}
	
	public void setBulletColor(int r, int g, int b)
	{
		bulletColor = new Color(r / 255.0f, g / 255.0f, b / 255.0f, 1.0f);
	}

	public void setScript(String s, int index, String sw, int lineNumber)
	{
		if (sw.equals(Keyword.ON.getValue()))
		{
			scriptName = s;
			globals.get("dofile").call(LuaValue.valueOf("user_assets/lua/" + s)); // initialize
			try	
			{
				scripts.set(index, globals.get("tick"));
			}
			catch (IndexOutOfBoundsException e)
			{
				scripts.add(globals.get("tick"));
			}
		}
		else
		{
			try
			{
				scripts.set(index, null);
			}
			catch (IndexOutOfBoundsException e)
			{
				System.out.println(Error.INDEX_DOES_NOT_EXIST.getText() + (lineNumber + 1));
			}
		}
	}
	
	/*
	 * Places a bullet at the center of the enemy
	 */
	public void shoot(int radius, double angle, float speed, int index)
	{
		BattleScreen.enemyBullets.add(new CircleBullet(
				getX() + getWidth() / 2,
				getY() + getHeight() / 2,
				radius, angle, speed, index, bulletColor //colors are shouldn't be necessary if using images
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
				radius, angle, speed, index, bulletColor
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
