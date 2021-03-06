package com.neet.cf.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.neet.cf.CurtainFire;
import com.neet.cf.handlers.GameScreenManager;
import com.neet.cf.handlers.Transition;
import com.neet.cf.handlers.Transition.TransitionType;
import com.neet.cf.overworld.util.CFVars;

public class MainMenuScreen extends GameScreen
{
    private SpriteBatch batch;
    private ShapeRenderer sr;
    private GlyphLayout layout;

    
    private Rectangle rect;
    private enum Selection {
    	Play(0, "PLAY"), 
    	Options(1, "OPTIONS"), 
    	Test(2, "Test");
    	private final int items=3;
    	private float yVal;
    	private String text;
    	private Selection(int listNum, String t){
    		yVal= (items-listNum)*CFVars.SCREEN_HEIGHT/(items+1);
    		text=t;
    	}
    	private float getY(){
    		return yVal;
    	}
 
    };
    private Selection[] selectList;
    private int listPos;
	public MainMenuScreen(GameScreenManager gsm)
	{
		super(gsm);
		//Set layout to widest test for rectangle init
		layout = new GlyphLayout(CFVars.font, Selection.Options.text);
		batch = new SpriteBatch();
		sr = new ShapeRenderer();
		listPos=0;
		selectList = Selection.values();
		rect= new Rectangle((CFVars.SCREEN_WIDTH-layout.width*2)/2, selectList[listPos].getY()-layout.height*1.5f, layout.width*2, layout.height*2);
	}
	@Override
	public void render()
	{
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//Draw Test
		batch.begin();
		for(int i=0; i<selectList.length; i++)
		{
			layout.setText(CFVars.font, selectList[i].text);
			CFVars.font.draw(batch, layout, (CFVars.SCREEN_WIDTH-layout.width)/2, selectList[i].getY());
			
		}
		
		if (CurtainFire.DEBUG)
			CFVars.font.draw(batch, "Debug", 30, Gdx.graphics.getHeight() - 50);
		
		batch.end();
		
		//Draw Selection Rectangle
		sr.begin(ShapeType.Line);
		sr.setColor(Color.WHITE);
		sr.rect(rect.x, rect.y, rect.width, rect.height);
		sr.end();
	}
	public void handleInput()
	{
		if(Gdx.input.isKeyJustPressed(Keys.Z))
		{
			Selection sel = selectList[listPos];
			switch(sel)
			{
				case Play:
					gsm.setScreen(gsm.OVERWORLD, new Transition(TransitionType.SplitOut), false);
					break;
				case Options:
					gsm.prevScreen=gsm.MENU;
					gsm.setScreen(gsm.OPTIONS);
					break;
				case Test:
					gsm.prevScreen = gsm.MENU;
					TestScreen.scriptFile = CurtainFire.testScript;
					gsm.setScreen(gsm.TEST);
					break;
			}
		}
		if(Gdx.input.isKeyJustPressed(Keys.DOWN))
		{
			listPos= listPos+1;
			//Wrap around
			if(listPos>=selectList.length)
				listPos=0;
			rect.y=selectList[listPos].getY()-layout.height*1.5f;
		}
		if(Gdx.input.isKeyJustPressed(Keys.UP))
		{
			listPos= listPos-1%selectList.length;
			//Wrap around
			if(listPos<0)
				listPos=selectList.length-1;
			rect.y=selectList[listPos].getY()-layout.height*1.5f;
		}
		if (Gdx.input.isKeyJustPressed(Keys.D))
			CurtainFire.DEBUG = !CurtainFire.DEBUG;
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
		sr.dispose();
		batch.dispose();
	}
	@Override
	public void update(float dt)
	{
		handleInput();
		
	}


}
