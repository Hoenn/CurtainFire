package com.neet.cf.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.neet.cf.CurtainFire;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable=false;
		config.vSyncEnabled=true;
		config.width=960; //480, 720, 960
		config.height=768;//384, 576, 768
		new LwjglApplication(new CurtainFire(config.width, config.height), config);
	}
}
