package com.neet.cf.script.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.neet.cf.CurtainFire;
import com.neet.cf.screens.BattleScreen;

public class BattlePanel
{
	private IDrawTable menuTable;
	private EntityLabel enemyLabel;
	private EntityLabel playerLabel;
	private final int nameWidth = 75;
	private final int hpWidth = 60;
	public BattlePanel(Stage stage)
	{
		menuTable = new IDrawTable();
		
		Skin skin = CurtainFire.manager.get("uiskin.json", Skin.class);
		
		enemyLabel = new EntityLabel(skin);
		playerLabel = new EntityLabel(skin);
		
		menuTable.setSize(BattleScreen.MENU_WIDTH, BattleScreen.FIELD_HEIGHT);
		menuTable.setPosition(BattleScreen.FIELD_WIDTH, 0);
		stage.addActor(menuTable);
		
		//menuTable.add(enemyLabel.getNameLabel()).expand().fillX().top();
		//TODO fix this mess
		menuTable.add(enemyLabel.getNameLabel()).expand().fillX().width(nameWidth).top();
		menuTable.add(enemyLabel.getHPLabel()).expand().fillX().width(hpWidth).top();
		menuTable.row();
		menuTable.add(playerLabel.getNameLabel()).expandX().fillX().width(nameWidth).top();
		menuTable.add(playerLabel.getHPLabel()).expandX().fillX().width(hpWidth).top();
	}
	
	public void setDebug(boolean debug)
	{
		menuTable.setDebug(debug);
	}
	
	public IDrawTable getMenuTable()
	{
		return menuTable;
	}
	
	public EntityLabel getEnemyLabel()
	{
		return enemyLabel;
	}
	
	public EntityLabel getPlayerLabel()
	{
		return playerLabel;
	}
}
