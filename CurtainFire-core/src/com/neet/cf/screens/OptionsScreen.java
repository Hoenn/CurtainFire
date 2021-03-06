package com.neet.cf.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
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

public class OptionsScreen extends GameScreen
{

	private SpriteBatch batch;
	private ShapeRenderer sr;
	private GlyphLayout layout;


	private Rectangle rect;
	
    private enum Selection {
    	VolumeUp(0, "VOLUME UP"), 
    	VolumeDown(1, "VOLUME DOWN"), 
    	Mute(2, "Mute"),
    	Done(3, "Return");
    	private final int items=4;
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
    private Selection[] selectList = Selection.values();
	private int listPos;

	public OptionsScreen(GameScreenManager gsm)
	{
		super(gsm);
		// Set layout to widest test for rectangle init
		layout = new GlyphLayout(CFVars.font, Selection.VolumeDown.text);
		batch = new SpriteBatch();
		sr = new ShapeRenderer();
		listPos = 0;
		selectList = Selection.values();
		rect= new Rectangle((CFVars.SCREEN_WIDTH-layout.width*2)/2, selectList[listPos].getY()-layout.height*1.5f, layout.width*2, layout.height*2);
	}

	@Override
	public void render()
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Draw Test
		batch.begin();
		CFVars.font.setColor(Color.WHITE);
		for(int i=0; i<selectList.length; i++)
		{
			layout.setText(CFVars.font, selectList[i].text);
			CFVars.font.draw(batch, layout, (CFVars.SCREEN_WIDTH-layout.width)/2, selectList[i].getY());
			
		}
		batch.end();

		// Draw Selection Rectangle
		sr.begin(ShapeType.Line);
		sr.setColor(Color.WHITE);
		sr.rect(rect.x, rect.y, rect.width, rect.height);
		sr.end();
	}

	public void handleInput()
	{
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE))
			gsm.setScreen(gsm.prevScreen, new Transition(TransitionType.RectUp), false);
		if (Gdx.input.isKeyJustPressed(Keys.Z))
		{
			CurtainFire.manager.get("blip.ogg", Sound.class).play(CFVars.VOLUME);
			Selection sel = selectList[listPos];
			switch (sel)
			{
				case VolumeUp:
							if(CFVars.VOLUME<CFVars.VOLUME_MAX)
								CFVars.VOLUME+=.025f;
							break;
				case VolumeDown:
							if(CFVars.VOLUME>0)
								CFVars.VOLUME -= .025f;
								if(CFVars.VOLUME <= 0)
									CFVars.VOLUME = 0;
							
							break;
				case Mute:	
							CFVars.VOLUME=0;
							break;
				case Done:  gsm.setScreen(gsm.prevScreen, new Transition(TransitionType.RectUp), false);
							break;
			}
		}
		if (Gdx.input.isKeyJustPressed(Keys.DOWN))
		{
			listPos = listPos + 1;
			// Wrap around
			if (listPos >= selectList.length)
				listPos = 0;
			rect.y = selectList[listPos].getY() - layout.height * 1.5f;
		}
		if (Gdx.input.isKeyJustPressed(Keys.UP))
		{
			listPos = listPos - 1 % selectList.length;
			// Wrap around
			if (listPos < 0)
				listPos = selectList.length - 1;
			rect.y = selectList[listPos].getY() - layout.height * 1.5f;
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
