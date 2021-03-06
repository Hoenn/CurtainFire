package com.neet.cf.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.neet.cf.CurtainFire;
import com.neet.cf.handlers.GameScreenManager;

public abstract class GameScreen implements Screen
{
	protected GameScreenManager gsm;
	protected CurtainFire game;
	
	protected SpriteBatch sb;
	protected OrthographicCamera cam;
	protected OrthographicCamera hudCam;
	
	
	
	private final float[] zoomDepth = new float[]
				{.125f, .25f, .5f, .75f, 1f, 1.25f, 1.5f, 1.75f, 2f, 3f, 4f, 5f};
	private final int defaultZoomDepthPosition=4; 
	private int currentZoomDepth=defaultZoomDepthPosition;
		
	protected GameScreen(GameScreenManager gsm)
	{
		this.gsm = gsm;
		game = gsm.getGame();
		sb=game.getSpriteBatch();
		cam=game.getCamera();
		hudCam = game.getHUDCamera();

		zoomDefault();
	}

	protected void zoomIn()
	{
		if(currentZoomDepth>0)
		{
			cam.zoom= zoomDepth[currentZoomDepth-1];
			cam.update();

			currentZoomDepth--;
		}
	}
	
	protected void zoomOut()
	{
		if(currentZoomDepth<zoomDepth.length-1)
		{
			cam.zoom= zoomDepth[currentZoomDepth+1];
			cam.update();
		
			
			currentZoomDepth++;
		}
	}
	
	protected void zoomDefault()
	{
		cam.zoom=zoomDepth[defaultZoomDepthPosition];
		cam.update();
		
		
		currentZoomDepth=defaultZoomDepthPosition;
	}

	public abstract void handleInput();
	public abstract void update(float dt);
	public abstract void render();
	public abstract void dispose();
	public void show(){}//Children will implement resume and pause
	public void hide(){}
	public void render(float dt){} //Children implement update and render separately
	public void resize(int arg0, int arg1){}


}
