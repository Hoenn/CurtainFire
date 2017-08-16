package com.neet.cf.script.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.neet.cf.CurtainFire;
import com.neet.cf.screens.BattleScreen;

public class Player extends Entity
{
	private int baseSpeed;
	private int speed;
	
	private Vector2 velocity;
	private double length;
	
	private boolean moveLeft, moveRight, moveUp, moveDown, shoot, shootable;

	private float shootIntervalSpeed;
	
	private float bulletTimer;
	private float hitTimer;
	private float hitTimeLimit;
	
	private Circle hitbox;
	
	private float bulletSpeed;
	
	private boolean invincible;
	
	public Player(String name, String imageFile1, String imageFile2, float aniDur, float x, float y, int hp)
	{
		super(name, imageFile1, imageFile2, aniDur, x, y, hp);
		//BattleScreen.battlePanel.getPlayerLabel().setName(name);
		//BattleScreen.battlePanel.getPlayerLabel().setHP(hp);
	}

	@Override
	void init()
	{	
		baseSpeed = 250; //temporary, we'll determine later how we want to do speeds
		speed = baseSpeed;
		
		velocity = new Vector2();
		
		hitbox = new Circle();
		hitboxColor = Color.WHITE;
		hitbox.radius = 5;
		
		hitTimer = 0;
		hitTimeLimit = 200; //temporary
		bulletSpeed = 800;
		
		shootIntervalSpeed = .10f;
		
		shootable = true;
	}
	
	@Override
	public void act(float delta)
	{
		if (!pause)
			super.act(delta);
		
		if (isMoving())
			move(delta);
		
		if(shoot && shootable)
			shoot();
		
		bulletTimer += delta;
		
		tickHitbox();
		if (!invincible)
			checkCollision(delta);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		super.draw(batch, parentAlpha);
		
		
        CurtainFire.shapeRenderer.setColor(hitboxColor);
		CurtainFire.shapeRenderer.circle(hitbox.x, hitbox.y, hitbox.radius);
	}
	
	@Override
	void tickHitbox()
	{
		hitbox.x = getX() + getWidth() / 2;
		hitbox.y = getY() + getHeight() / 2;
	}
	
	
	private void shoot()
	{
		//TODO Temporary, likely to be implemented on subclass or per instance basis
		if (bulletTimer > shootIntervalSpeed)
		{
			BattleScreen.playerBullets.add(new RectBullet(
				getX() + getWidth() / 2 - 10 / 2, getY() + getHeight() / 2, 
				10, 20, 90, bulletSpeed, Color.CYAN));
			bulletTimer = 0;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.curtainfire.entities.Entity#checkCollision()
	 * TODO current decreaseHP(1) lines are temporary
	 */
	@Override
	void checkCollision(float delta)
	{
		if (!hit)
		{
			try
			{
				if (Intersector.overlaps(hitbox, BattleScreen.scriptController.getEnemy().getHitbox()))
				{
					hit = true;
					decreaseHP(1);
					hitTimer = 0;
				}
			}
			catch (NullPointerException e)
			{
				//TODO enemy is loaded after player, but what if player exists and no enemy is on screen?
			}
			
			for (int i = 0, len = BattleScreen.enemyBullets.size; i < len; i++)
			{
				if (hitbox.overlaps(BattleScreen.enemyBullets.get(i).getHitbox()))
				{
					hit = true;
					decreaseHP(1);
					BattleScreen.enemyBullets.get(i).setAlive(false);
					hitTimer = 0;
					break;
				}
			}
		}
		else
		{
			hitTimer += delta * 100;
			if (hitTimer >= hitTimeLimit)
			{
				hitTimer = 0;
				hit = false;
			}
		}
	}
	
	private boolean isMoving()
	{
		return moveLeft || moveRight || moveUp || moveDown;
	}
	
	/*
	 * //https://gamedev.stackexchange.com/questions/82247/how-to-avoid-movement-speed-stacking-when-multiple-keys-are-pressed
	 */
	private void move(float delta)
	{
		velocity.setZero();
		if (moveLeft && getX() >= 0)
			velocity.x += -1;
		if (moveRight && getX() <= BattleScreen.FIELD_WIDTH - getWidth())
			velocity.x += 1;
		if (moveUp && getY() <= BattleScreen.FIELD_HEIGHT - getHeight())
			velocity.y += 1;
		if (moveDown && getY() >= 0)
			velocity.y += -1;
		length = Math.sqrt(Math.pow(velocity.x, 2) + Math.pow(velocity.y, 2)); //normalize
		
		if (length != 0)
		{
			velocity.x /= length;
			velocity.y /= length;
		}
		setX(getX() + velocity.x * delta * speed);
		setY(getY() + velocity.y * delta * speed);
	}

	public void halveSpeed(boolean halve)
	{
		if (halve)
			speed = baseSpeed / 2;
		else
			speed = baseSpeed;
	}

	public void setMoveLeft(boolean moveLeft)
	{
		this.moveLeft = moveLeft;
	}

	public void setMoveRight(boolean moveRight)
	{
		this.moveRight = moveRight;
	}

	public void setMoveUp(boolean moveUp)
	{
		this.moveUp = moveUp;
	}

	public void setMoveDown(boolean moveDown)
	{
		this.moveDown = moveDown;
	}
	
	public void setShoot(boolean shoot)
	{
		this.shoot = shoot;
	}

	public boolean isInvincible()
	{
		return invincible;
	}

	public void setInvincible(boolean invincible)
	{
		this.invincible = invincible;
	}

	public boolean isShootable()
	{
		return shootable;
	}

	public void setShootable(boolean shootable)
	{
		this.shootable = shootable;
	}
}
