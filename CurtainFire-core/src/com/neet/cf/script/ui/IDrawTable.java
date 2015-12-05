package com.neet.cf.script.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/*
 * Purpose of this class is to be able to draw separately from stage.draw();
 */
public class IDrawTable extends Table
{
	float parentAlpha;
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		if (this.parentAlpha != parentAlpha)
			this.parentAlpha = parentAlpha;
	}
	
	public void iDraw(Batch batch)
	{
		super.draw(batch, parentAlpha);
	}
}
