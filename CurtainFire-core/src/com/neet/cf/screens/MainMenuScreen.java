package com.neet.cf.screens;

import static com.neet.cf.handlers.GameInput.BUTTON_Z;
import static com.neet.cf.handlers.GameInput.isDown;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.neet.cf.handlers.GameScreenManager;
import com.neet.cf.handlers.Transition;
import com.neet.cf.handlers.Transition.TransitionType;
import com.neet.cf.util.CFVars;

public class MainMenuScreen extends GameScreen
{
    private SpriteBatch batch;
    private ShapeRenderer sr;
    private GlyphLayout layout;
    private final String MENU1 ="PLAY";
    private final String MENU2 ="OPTIONS";
    private final String MENU3= "Test";
    
    private Rectangle rect;
    private enum Selection {
    	Play((3*CFVars.SCREEN_HEIGHT)/4), 
    	Options((2*CFVars.SCREEN_HEIGHT)/4), 
    	Test((CFVars.SCREEN_HEIGHT)/4);
    	private float yVal;
    	private Selection(float y){
    		yVal=y;
    	}
    	private float getY(){
    		return yVal;
    	}
 
    };
    private Selection[] selectList = {Selection.Play, Selection.Options, Selection.Test};
    private int listPos;
	public MainMenuScreen(GameScreenManager gsm)
	{
		super(gsm);
		//Set layout to widest test for rectangle init
		layout = new GlyphLayout(CFVars.font, MENU2);
		batch = new SpriteBatch();
		sr = new ShapeRenderer();
		listPos=0;
		rect= new Rectangle((CFVars.SCREEN_WIDTH-layout.width*2)/2, selectList[listPos].getY()-layout.height*1.5f, layout.width*2, layout.height*2);
	}
	@Override
	public void render()
	{
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//Draw Test
		batch.begin();
		CFVars.font.setColor(Color.WHITE);
		layout.setText(CFVars.font, MENU1);
		CFVars.font.draw(batch, layout, (CFVars.SCREEN_WIDTH-layout.width)/2, Selection.Play.getY());
		layout.setText(CFVars.font, MENU2);
		CFVars.font.draw(batch, layout, (CFVars.SCREEN_WIDTH-layout.width)/2, Selection.Options.getY());
		layout.setText(CFVars.font, MENU3);
		CFVars.font.draw(batch, layout, (CFVars.SCREEN_WIDTH-layout.width)/2, Selection.Test.getY());
		batch.end();
		
		//Draw Selection Rectangle
		sr.begin(ShapeType.Line);
		sr.setColor(Color.WHITE);
		sr.rect(rect.x, rect.y, rect.width, rect.height);
		sr.end();
	}
	public void handleInput()
	{
		if(isDown(BUTTON_Z))
		{
			Selection sel = selectList[listPos];
			switch(sel)
			{
				case Play:
					gsm.setScreen(gsm.OVERWORLD, new Transition(TransitionType.SplitOut));
					break;
				case Options:
					break;
				case Test:
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
