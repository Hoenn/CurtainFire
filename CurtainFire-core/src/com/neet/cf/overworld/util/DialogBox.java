package com.neet.cf.overworld.util;

import static com.neet.cf.overworld.util.CFVars.DEFAULT_SB_COLOR;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class DialogBox
{
	public static CharSequencer charSeq;
	public static Texture box;	
	private final static float x = 16*(CFVars.SCREEN_WIDTH/CFVars.V_WIDTH);
	private final static float y = 8;
	private final static float width =CFVars.SCREEN_WIDTH-x*2;	
	private final static float height = 168;
	private static GlyphLayout layout = new GlyphLayout();
	public static void draw(SpriteBatch sb, OrthographicCamera hudCam)
	{		
		sb.begin();
		sb.setColor(DEFAULT_SB_COLOR);
		sb.setProjectionMatrix(hudCam.combined);
		sb.draw(box, x, y, width, height);
		CFVars.font.setColor(Color.BLACK);
		layout.setText(CFVars.font, charSeq.getCurrent());
		//If box is "full" there is a carriage return
		if(layout.width>=(width-x-x-16)*3-36)
			charSeq.setReturn();
		
		CFVars.font.draw(sb, charSeq.getCurrent(), (x*2)+8, 135, width-(x*2)-16, 30, true);
		sb.end();

	}
	
}
