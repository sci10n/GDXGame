package me.sciion.gdx.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import me.sciion.gdx.MyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 600;
		config.vSyncEnabled = false;
		config.foregroundFPS = 0;
		config.useGL30 = true;
		config.samples = 8;
		
		new LwjglApplication(new MyGame(true), config);
	}
}
