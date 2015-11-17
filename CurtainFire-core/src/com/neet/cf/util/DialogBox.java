package com.neet.cf.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class DialogBox
{
	public static CharSequencer charSeq;
	public static Texture box;
	private final static float width = 592;
	private final static float height = 200;
	private final static float x = 64;
	private final static float y = 32;
	private static GlyphLayout layout = new GlyphLayout();
	public static void draw(SpriteBatch sb, OrthographicCamera hudCam)
	{		
		sb.begin();
		sb.setProjectionMatrix(hudCam.combined);
		sb.draw(box, x, y, width, height);
		CFVars.font.setColor(Color.BLACK);
		layout.setText(CFVars.font, charSeq.getCurrent());
		//If box is "full" there is a carriage return
		if(layout.width>=461*3)
			charSeq.setReturn();
		
		CFVars.font.draw(sb, charSeq.getCurrent(), x+x, 176, 470, 30, true);
		sb.end();

	}
	
}
