package com.neet.cf.script.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.neet.cf.overworld.util.CFVars;
import com.neet.cf.screens.BattleScreen;

public abstract class Entity extends Actor
{
	private Texture texture;
	private TextureRegion[] frames;
	private TextureRegion region;
	private Animation animation;
	private float stateTime;
	
	private String name;
	boolean pause;
	protected boolean hit;
	
	Color hitboxColor;
	
	private int hp;
	
	public Entity(String name, String imageFile1, String imageFile2, float aniDur, float x, float y, int hp)
	{
		frames = new TextureRegion[2];
		region = null;
		
		this.name = name;
		this.setPosition(x, y);
		this.setOrigin(0, 0);
		
		texture = new Texture(Gdx.files.absolute(System.getProperty("user.dir") + 
				"/user_assets/" + getClass().getSimpleName().toLowerCase() + "/" + imageFile1));
		frames[0] = new TextureRegion(texture, texture.getWidth(), texture.getHeight());
		
		texture = new Texture(Gdx.files.absolute(System.getProperty("user.dir") + 
				"/user_assets/" + getClass().getSimpleName().toLowerCase() + "/" + imageFile2));
		frames[1] = new TextureRegion(texture, texture.getWidth(), texture.getHeight());
		
		animation = new Animation(aniDur, frames);
		stateTime = 0;
		
		this.setWidth(texture.getWidth());
		this.setHeight(texture.getHeight());
		
		this.hp = hp;
		
		hit = false;
		
		init();
	}
	
	abstract void init();
	abstract void checkCollision(float delta);
	abstract void tickHitbox();
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		stateTime += Gdx.graphics.getDeltaTime();
		region = animation.getKeyFrame(stateTime, true);
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, parentAlpha);
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        
        if (hit)
        {
        	CFVars.font.setColor(Color.RED);
        	CFVars.font.draw(batch, "HP: " + getHP(), getX(), getY());
        }
        else
        {
        	CFVars.font.setColor(Color.WHITE);
        	CFVars.font.draw(batch, "HP: " + getHP(), getX(), getY());
        }
	}
	
	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isPause()
	{
		return pause;
	}

	public void setPause(boolean pause)
	{
		this.pause = pause;
	}
	
	public int getHP()
	{
		return hp;
	}
	
	public void decreaseHP(int i)
	{
		hp -= i;
//		if (this instanceof Enemy)
//			BattleScreen.battlePanel.getEnemyLabel().setHP(hp);
//		else if (this instanceof Player)
//			BattleScreen.battlePanel.getPlayerLabel().setHP(hp);
	}
	
	protected boolean isAlive()
	{
		if (hp > 0)
			return true;
		return false;
	}
	
	/*
	 * Returns first instance of enemy on the stage
	 */
	public static int getEnemyIndex()
	{
		for (int i = BattleScreen.stage.getActors().size - 1; i >= 0; i--)
		{
			if (BattleScreen.stage.getActors().get(i) instanceof Enemy)
				return i;
		}
		return -1;
	}
}
