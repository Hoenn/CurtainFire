package com.neet.cf.screens;

import static com.neet.cf.handlers.GameInput.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.neet.cf.handlers.GameScreenManager;
import com.neet.cf.handlers.Transition;
import com.neet.cf.handlers.Transition.TransitionType;

public class MainMenuScreen extends GameScreen
{
    private BitmapFont font;
    private SpriteBatch batch;
	public MainMenuScreen(GameScreenManager gsm)
	{
		super(gsm);

		batch = new SpriteBatch();
		font = new BitmapFont();
	}
	@Override
	public void render()
	{
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.setColor(Color.WHITE);
		font.draw(batch,"Press Z", 300, 300);
		batch.end();
	}
	public void handleInput()
	{
		if(isDown(BUTTON_Z))
		{
			gsm.setScreen(gsm.OVERWORLD, new Transition(TransitionType.SplitOut));
		}
	}
	@Override
	public void resize(int width, int height)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume()
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void show()
	{
	}
	@Override
	public void hide()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose()
	{
		
	}
	@Override
	public void update(float dt)
	{
		handleInput();
		
	}


}
