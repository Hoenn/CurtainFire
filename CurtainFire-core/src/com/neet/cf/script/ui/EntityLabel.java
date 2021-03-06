package com.neet.cf.script.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class EntityLabel
{
	private Label nameLabel;
	private Label hpLabel;
	
	public EntityLabel(Skin skin)
	{
		nameLabel = new Label("", skin);
		hpLabel = new Label("", skin);
	}

	public void setName(String name)
	{
		nameLabel.setText(name);
	}

	public void setHP(int hp)
	{
		hpLabel.setText("HP: " + hp);
	}

	public Label getNameLabel()
	{
		return nameLabel;
	}

	public Label getHPLabel()
	{
		return hpLabel;
	}
}
