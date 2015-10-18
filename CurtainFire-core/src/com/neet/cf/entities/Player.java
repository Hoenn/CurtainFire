package com.neet.cf.entities;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.neet.cf.CurtainFire;

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
	private ArrayList<Animation> runs = new ArrayList<Animation>();
	private TextureRegion[] idleFrames = new TextureRegion[4];
	private TextureRegion currentFrame;
	private float currentFrameTime;
	private boolean moving=false;
	private float turnTimeCounter;
	private final float turnTime= 1/16f;
		
	public Player()
	{
		Texture texture = CurtainFire.manager.get("player.png");
		TextureRegion[][] temp = TextureRegion.split(texture, 16, 20);

		TextureRegion[] frames = new TextureRegion[temp[0].length];
		int z =0;
		for(int r = 0; r<temp[0].length; r++)
		{
				frames[z] = temp[0][r];
				z++;
		}
		
		
		TextureRegion[] upWalk = new TextureRegion[4];
		TextureRegion[] downWalk= new TextureRegion[4];
		TextureRegion[] leftWalk= new TextureRegion[4];
		TextureRegion[] rightWalk= new TextureRegion[4];
		int k = 0;
		for(int j =0; j<3; j++)
		{ 
			upWalk[k] = frames[j];
			leftWalk[k] = frames[j+3];
			TextureRegion mirror = new TextureRegion(frames[j+3]);
			mirror.flip(true, false);
			rightWalk[k] = mirror;
			downWalk[k] = frames[j+6];
			k++;
		}
		idleFrames[UP]=frames[1];
		idleFrames[LEFT]=frames[4];
		TextureRegion right =new TextureRegion( frames[4]);
		right.flip(true,  false);
		idleFrames[RIGHT] = right;
		idleFrames[DOWN]= frames[7];
		
		upWalk[3]=idleFrames[UP];
		leftWalk[3]=idleFrames[LEFT];
		downWalk[3]=idleFrames[DOWN];
		rightWalk[3]=idleFrames[RIGHT];
			
		walks.add(UP, new Animation(MOVE_TIME/2, upWalk));
		walks.add(LEFT,new Animation(MOVE_TIME/2, leftWalk));
		walks.add(DOWN, new Animation(MOVE_TIME/2, downWalk));
		walks.add(RIGHT,new Animation(MOVE_TIME/2, rightWalk));
		
		TextureRegion[] runFrames = new TextureRegion[temp[0].length];
		z=0;
		for(int r =0; r<temp[0].length; r++)
		{
			runFrames[z] =temp[1][r];
			z++;
		}
		TextureRegion[] upRun = new TextureRegion[4];
		TextureRegion[] downRun= new TextureRegion[4];
		TextureRegion[] leftRun= new TextureRegion[4];
		TextureRegion[] rightRun= new TextureRegion[4];
		k=0;
		for(int j=0; j<3;j++)
		{
			upRun[k] = runFrames[j];
			leftRun[k] = runFrames[j+3];
			TextureRegion mirror = new TextureRegion(runFrames[j+3]);
			mirror.flip(true, false);
			rightRun[k] = mirror;
			downRun[k] = runFrames[j+6];
			k++;
		}
		upRun[3]=upRun[1];
		leftRun[3]=leftRun[1];
		downRun[3]=downRun[1];
		rightRun[3]=rightRun[1];
		runs.add(UP, new Animation(RUNSPEED/2, upRun));
		runs.add(LEFT, new Animation(RUNSPEED/2, leftRun));
		runs.add(DOWN, new Animation(RUNSPEED/2, downRun));
		runs.add(RIGHT, new Animation(RUNSPEED/2, rightRun));
		
		
		direction=UP;
		currentFrame = idleFrames[direction];

		position = new Vector2(0, 0);
		destVector = new Vector2(0, 0);
		moveVector = new Vector2(0,0);
	}
	public void draw(Batch sb)
	{
		update(Gdx.graphics.getDeltaTime());
		
		if(moving)
		{
			currentFrameTime += Gdx.graphics.getDeltaTime();
			if(MOVE_TIME==RUNSPEED)
			{
				currentFrame = runs.get(direction).getKeyFrame(currentFrameTime, true);

			}
			else
				currentFrame = walks.get(direction).getKeyFrame(currentFrameTime, true);
		}
		else
			currentFrame = idleFrames[direction];
		
		sb.draw(currentFrame, position.x, position.y);
	}
	public void update(float delta)
	{	
		if(moveVector.x!=0||moveVector.y!=0)
			updateMove(delta);
		//Occupy initial position and final position
		//else
			//Occupy final position only
	}
	public void updateMove(float delta)
	{
		float overflowX = 0, overflowY = 0;
		

		if (moving)
		{
			//If moving in x direction
			if (moveVector.x != 0)
			{
				//modify position with moveVector+- direction
				//use TILE_WIDTH * delta/MOVE_TIME to make this 
				//movement gradual
				position.x += moveVector.x * TILE_WIDTH * (delta / MOVE_TIME);
				if ((moveVector.x < 0 && position.x <= destVector.x)
						|| (moveVector.x > 0 && position.x >= destVector.x))
				{
					//If moved too far or too little find difference
					//store as overflowX
					//Major movement is done, only small overflow adjustment now
					moving = false;
					//Cannot be activating if moving, moveVector
					//will only be set in the handleMove method called
					//each frame
					if(!isDown(BUTTON_A)&&moveVector.x==-1)
						moveVector.x=0;
					else if(!isDown(BUTTON_D)&&moveVector.x==1)
						moveVector.x=0;
					overflowX = position.x - destVector.x;
				}
			} else if (moveVector.y != 0)
			{
				position.y += moveVector.y * TILE_WIDTH * (delta / MOVE_TIME);
				if ((moveVector.y < 0 && position.y <= destVector.y)
						|| (moveVector.y > 0 && position.y >= destVector.y))
				{
					moving = false;
					if(!isDown(BUTTON_W)&&moveVector.y==1)
						moveVector.y=0;
					else if(!isDown(BUTTON_S)&&moveVector.y==-1)
						moveVector.y=0;
					overflowY = position.y - destVector.y;
				}
			}
		}
		if (!moving)
		{
			//If there is a moveVector present			
			if (moveVector.x != 0 || moveVector.y != 0)
			{
				//set move to true which will revert to top loop
				moving = true;
				//create a destination using moveVector.x/y +-
				//Use rounding to ensure position's float values don't
				//allow destVect to become an incorrect tile
				//create rough point, round it, divide by 16, multiply 16, 
				//this will guarantee the number as a factor of 16
				destVector.x = Math.round((position.x + moveVector.x * TILE_WIDTH) / TILE_WIDTH) * TILE_WIDTH;
				destVector.y = Math.round((position.y + moveVector.y * TILE_WIDTH) / TILE_WIDTH) * TILE_WIDTH;
				
				//If there is an overFlow
				if (overflowX != 0 && destVector.x != position.x)
				{
					//If that overFlow is somewhat substantial
					if (Math.abs(overflowX) > 0.001f)
					{
						//move using the overflow
						updateMove(delta * (overflowX
								/ (TILE_WIDTH * (delta / MOVE_TIME))));
					}				

				} 
				else if (overflowY != 0 && destVector.y != position.y)
				{
					if (Math.abs(overflowY) > 0.001f)
						updateMove(delta * (overflowY
								/ (TILE_WIDTH * (delta / MOVE_TIME))));

				}
				
				//Moving should be done by now, round off player's position
				position.x = Math.round(position.x*TILE_WIDTH)/TILE_WIDTH;
				position.y = Math.round(position.y*TILE_WIDTH)/TILE_WIDTH;
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
							moveVector.y=1;
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
							moveVector.x=-1;
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
							moveVector.y=-1;
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
							moveVector.x=1;
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
	
	public Vector2 getPosition()
	{
		return position;
	}

}
