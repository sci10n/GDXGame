package me.sciion.gdx.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import me.sciion.gdx.MyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 800;
		config.vSyncEnabled = false;
		config.foregroundFPS = 144;
		config.useGL30 = true;
		config.samples = 8;
		String serverIP = "";
		for(String a : arg){
		    //System.out.println(arg);
		    if(a.contains("--server-ip=")){
			serverIP = a.substring("--server-ip=".length(), a.length());
		    }
		}
		new LwjglApplication(new MyGame(serverIP), config);
	}
}
