package com.neet.cf.entities;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.neet.cf.CurtainFire;
import com.neet.cf.handlers.Animation;
import com.neet.cf.screens.GameScreen;

import static com.neet.cf.handlers.GameInput.*;

public class Player
{
	private Vector2 position, destVector, moveVector;	
	private final float RUNSPEED = 0.20f;
	private final float WALKSPEED = 0.39f;
	private float MOVE_TIME= WALKSPEED;
	private final int TILE_WIDTH=16;
	public int direction;
	private final int UP=0, LEFT=1, DOWN=2, RIGHT=3;
	private ArrayList<Animation> walks = new ArrayList<Animation>();
	private final float WALK_ANI_SPEED = 0.195f;
	private final float RUN_ANI_SPEED = 0.1f;
	private ArrayList<Animation> runs = new ArrayList<Animation>();
	private TextureRegion[] idleFrames = new TextureRegion[4];
	private TextureRegion currentFrame;
	private boolean moving=false;
	private int gridX;
	private int gridY;
	private float turnTimeCounter;
	private final float turnTime= 1/16f;
		
	public Player()
	{
		Texture texture = CurtainFire.manager.get("player.png");
		TextureRegion[][] temp = TextureRegion.split(texture, 16, 20);

		TextureRegion[] walkFrames = new TextureRegion[temp[0].length];
		int i =0;
		for(int j = 0; j<temp[0].length; j++)
		{
				walkFrames[i] = temp[0][j];		
				i++;
		}
		
		walks.add(UP, new Animation(Arrays.copyOfRange(walkFrames, 0, 3), WALK_ANI_SPEED, true));
		walks.add(LEFT, new Animation(Arrays.copyOfRange(walkFrames, 3, 6), WALK_ANI_SPEED, true));
		walks.add(DOWN, new Animation(Arrays.copyOfRange(walkFrames, 6, 9), WALK_ANI_SPEED,true));
		walks.add(RIGHT, new Animation(mirrorTextureRegionsY(Arrays.copyOfRange(walkFrames, 3, 6)), WALK_ANI_SPEED, true));
		
		idleFrames[UP]=walkFrames[1];
		idleFrames[LEFT]=walkFrames[4];
		TextureRegion right =new TextureRegion(walkFrames[4]);
		right.flip(true,  false);
		idleFrames[RIGHT] = right;
		idleFrames[DOWN]= walkFrames[7];
		

		TextureRegion[] runFrames = new TextureRegion[temp[0].length];
		int a =0;
		for(int b =0; b<temp[0].length; b++)
		{
			runFrames[a] =temp[1][b];
			a++;
		}
		runs.add(UP, new Animation(Arrays.copyOfRange(runFrames, 0, 3), RUN_ANI_SPEED, true));
		runs.add(LEFT, new Animation(Arrays.copyOfRange(runFrames, 3, 6), RUN_ANI_SPEED, true));
		runs.add(DOWN, new Animation(Arrays.copyOfRange(runFrames, 6, 9), RUN_ANI_SPEED,true));
		runs.add(RIGHT, new Animation(mirrorTextureRegionsY(Arrays.copyOfRange(runFrames, 3, 6)), RUN_ANI_SPEED, true));
		
		//Initialize Player facing upwards
		direction=UP;
		currentFrame = idleFrames[direction];

		position = new Vector2(0, 0);
		gridX = (int) position.x;
		gridY = (int) position.y;
		
		destVector = new Vector2(0, 0);
		moveVector = new Vector2(0,0);
	}
	private TextureRegion[] mirrorTextureRegionsY(TextureRegion[] textures)
	{
		TextureRegion[] mirrored = new TextureRegion[textures.length];
		for(int i =0; i<textures.length; i++)
		{
			mirrored[i]= new TextureRegion(textures[i]);
			mirrored[i].flip(true, false);
		}
		return mirrored;
	}
	public void draw(Batch sb)
	{
		update(Gdx.graphics.getDeltaTime());
		
		if(moving)
		{
			if(MOVE_TIME==RUNSPEED)
			{
				currentFrame = runs.get(direction).getFrame();
				runs.get(direction).update(Gdx.graphics.getDeltaTime());
			}
			else
			{
				currentFrame = walks.get(direction).getFrame();
				walks.get(direction).update(Gdx.graphics.getDeltaTime());
			}
		}
		else
			currentFrame = idleFrames[direction];
		
		sb.draw(currentFrame, position.x, position.y);
	}
	public void update(float delta)
	{		
		if(moving)
		{
			if(GameScreen.isOpen(gridX+(int)moveVector.x, gridY+(int)moveVector.y))
			{
				if(moveVector.x!=0)
				{
					
					if(moveVector.x>0)
					{
						position.x += TILE_WIDTH*(delta/MOVE_TIME);
						if(position.x>=destVector.x)
						{
							position.x=destVector.x;
							gridX+=1;
							if(isDown(BUTTON_D))
							{
								startMove(RIGHT);
							}
							else
							{	moving=false;
								moveVector.x=0;
								destVector=null;
							}
						}
					}
					else if(moveVector.x<0)
					{
						position.x -= TILE_WIDTH*(delta/MOVE_TIME);
						if(position.x<=destVector.x)
						{
							position.x=destVector.x;
							gridX-=1;
							if(isDown(BUTTON_A))
							{
								startMove(LEFT);
							}
							else
							{	moving=false;
								moveVector.x=0;
								destVector=null;
							}
						}
					}
				}
				else if (moveVector.y!=0)
				{
					if(moveVector.y>0)
					{
						position.y += TILE_WIDTH*(delta/MOVE_TIME);
						if(position.y>=destVector.y)
						{
							position.y=destVector.y;
							gridY+=1;
							if(isDown(BUTTON_W))
							{
								startMove(UP);
							}
							else
							{	moving=false;
								moveVector.y=0;
								destVector=null;
							}
						}
					}
					else if(moveVector.y<0)
					{
						position.y -=TILE_WIDTH*(delta/MOVE_TIME);
						if(position.y<=destVector.y)
						{
							gridY-=1;
							position.y=destVector.y;
							if(isDown(BUTTON_S))
							{
								startMove(DOWN);
							}
							else
							{	moving=false;
								moveVector.y=0;
								destVector=null;
							}
							
						}
					}
				}
			}
			else
			{
				moving=false;
				moveVector.x=0;
				moveVector.y=0;
			}
		}
	
	}
	
	public void handleMove()
	{
		//If not facing in the direction pressed then face that direction
		//If that direction is held for 1/16s while facing correctly then allow movement
		if(isDown(BUTTON_SPACE))
			MOVE_TIME=RUNSPEED;
		else
			MOVE_TIME=WALKSPEED;
		if(!moving)
		{
			if(Gdx.input.isKeyJustPressed(Keys.W) && direction!=UP)
			{
				
				setDirection(UP);
				turnTimeCounter=0;
			}
			else if(isDown(BUTTON_W))
			{
				
					turnTimeCounter+=Gdx.graphics.getDeltaTime();
					
					if(turnTimeCounter>turnTime)
					{
						turnTimeCounter=0;
						if(moveVector.y==0)
						{
							startMove(UP);
						}
						setDirection(UP);
					}					
			}

			if(Gdx.input.isKeyJustPressed(Keys.A) && direction!=LEFT)
			{
				setDirection(LEFT);
				turnTimeCounter=0;
			}
			else if(isDown(BUTTON_A))
			{
					turnTimeCounter+=Gdx.graphics.getDeltaTime();
					if(turnTimeCounter>turnTime)
					{
						turnTimeCounter=0;
						if(moveVector.x==0)
						{
							startMove(LEFT);

						}
						setDirection(LEFT);
					}					
			}
			if(Gdx.input.isKeyJustPressed(Keys.S) && direction!=DOWN)
			{
				setDirection(DOWN);
				turnTimeCounter=0;
			}
			else if(isDown(BUTTON_S))
			{
				
					turnTimeCounter+=Gdx.graphics.getDeltaTime();
					if(turnTimeCounter>turnTime)
					{
						turnTimeCounter=0;
						if(moveVector.y==0)
						{
							startMove(DOWN);
						}
						setDirection(DOWN);
					}					
			}
			if(Gdx.input.isKeyJustPressed(Keys.D) && direction!=RIGHT)
			{
				setDirection(RIGHT);
				turnTimeCounter=0;
			}
			else if(isDown(BUTTON_D))
			{
				
					turnTimeCounter+=Gdx.graphics.getDeltaTime();
					if(turnTimeCounter>turnTime)
					{
						turnTimeCounter=0;
						if(moveVector.x==0)
						{
							startMove(RIGHT);
						}
						setDirection(RIGHT);
					}					
			}
		}	
	}
	public void setDirection(int dir)
	{
		direction=dir;
		currentFrame=idleFrames[dir];
	}
	private void startMove(int dir)
	{
		switch(dir)
		{
			case UP:
						moveVector.y = 1;
						moving = true;
						destVector = new Vector2(position.x,position.y + (int) moveVector.y * TILE_WIDTH);
						break;
			case LEFT:
						moveVector.x = -1;
						moving = true;
						destVector = new Vector2(position.x + (int) moveVector.x * TILE_WIDTH, position.y);
						break;
			case DOWN:
						moveVector.y = -1;
						moving = true;
						destVector = new Vector2(position.x, position.y + (int) moveVector.y * TILE_WIDTH);
						break;
			case RIGHT:
						moveVector.x = 1;
						moving = true;
						destVector = new Vector2(position.x + (int) moveVector.x * TILE_WIDTH, position.y);
						break;
		}
	}
	public Vector2 getPosition()
	{
		return position;
	}

}
