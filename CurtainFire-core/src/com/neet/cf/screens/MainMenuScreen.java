package com.neet.cf.screens;

import static com.neet.cf.handlers.GameInput.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.neet.cf.CurtainFire;

public class MainMenuScreen implements Screen
{
	private OrthographicCamera camera;
	private CurtainFire cf;
	private SpriteBatch batch;
    private BitmapFont font;
	public MainMenuScreen(CurtainFire game)
	{
		cf=game;
		batch = new SpriteBatch();    
		font = new BitmapFont();
	}
	@Override
	public void show()
	{
		camera = new OrthographicCamera();
		camera.zoom=0.5f;
	}

	@Override
	public void render(float delta)
	{

		handleInput();
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		font.draw(batch, "Press Z", 300, 300);
		batch.end();
		
	}
	public void handleInput()
	{
		if(isDown(BUTTON_Z))
		{
			cf.setScreen(new OverWorld(cf));
			
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
	public void hide()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose()
	{
		
	}

}
