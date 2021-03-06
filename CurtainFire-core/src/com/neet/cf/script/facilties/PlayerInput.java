package com.neet.cf.script.facilties;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.neet.cf.script.entities.Player;

public class PlayerInput extends InputAdapter
{
	private Player player;
	public PlayerInput(Player player)
	{
		this.player = player;
	}
	
	 @Override
	    public boolean keyDown(int keycode)
	    {
	        switch (keycode)
	        {
		        case Keys.LEFT:
		            player.setMoveLeft(true);
		            break;
		        case Keys.RIGHT:
		            player.setMoveRight(true);
		            break;
		        case Keys.UP:
		            player.setMoveUp(true);
		            break;
		        case Keys.DOWN:
		            player.setMoveDown(true);
		            break;
		        case Keys.SHIFT_LEFT:
		        	player.halveSpeed(true);
		        	break;
		        case Keys.Z:
		        	player.setShoot(true);
		        	break;
	        }
	        return true;
	    }
	    @Override
	    public boolean keyUp(int keycode)
	    {
	        switch (keycode)
	        {
		        case Keys.LEFT:
		        	player.setMoveLeft(false);
		            break;
		        case Keys.RIGHT:
		        	player.setMoveRight(false);
		            break;
		        case Keys.UP:
		            player.setMoveUp(false);
		            break;
		        case Keys.DOWN:
		            player.setMoveDown(false);
		            break;
		        case Keys.SHIFT_LEFT:
		        	player.halveSpeed(false);
		        	break;
		        case Keys.Z:
		        	player.setShoot(false);
		        	break;
	        }
	        return true;
	    }
}
