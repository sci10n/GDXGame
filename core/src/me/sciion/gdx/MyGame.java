package me.sciion.gdx;

import com.badlogic.gdx.ApplicationAdapter;

import me.sciion.gdx.level.Level;
import me.sciion.gdx.level.LevelLoader;
import me.sciion.gdx.netcode.ClientKryo;
import me.sciion.gdx.netcode.ServerKryo;

public class MyGame extends ApplicationAdapter {

    Level level;
    LevelLoader loader;
    ClientKryo client;
    ServerKryo server;
    
    private String serverIP;
    public MyGame(String serverIP){
	this.serverIP = serverIP;
    }
    
    @Override
    public void create() {
	loader = new LevelLoader();
	level = loader.load("maps/dummy_map.tmx");
	level.setup();
	if(serverIP.isEmpty()) {
	    server = new ServerKryo();
	    server.setup(level);
	} 
	else {
	    client = new ClientKryo();
	    client.setup(serverIP,level);
	}
	

    }
    
    
    @Override
    public void render() {
	//System.out.println(Gdx.graphics.getFramesPerSecond());
	level.process();
    }

    @Override
    public void dispose() {
	level.dispose();
    }	
}
