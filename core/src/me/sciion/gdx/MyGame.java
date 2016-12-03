package me.sciion.gdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import me.sciion.gdx.level.Level;
import me.sciion.gdx.level.LevelLoader;
import me.sciion.gdx.utils.KryoStasis;

public class MyGame extends ApplicationAdapter {

    Level level;
    LevelLoader loader;
    KryoStasis networking;
    public boolean server;
    private String serverIP;
    public MyGame(boolean server, String serverIP){
	this.server = server;
	this.serverIP = serverIP;
    }
    
    @Override
    public void create() {
	networking = new KryoStasis();
	if( server && networking.createServer()){
	   System.out.println("Server");
	}else if(!server){
	    System.out.println("Client");
	    networking.createClient(serverIP);
	}
	loader = new LevelLoader();
	level = loader.load("maps/dummy_map.tmx",networking);
	level.setup();
	
	

    }
    
    
    @Override
    public void render() {
	//System.out.println(Gdx.graphics.getFramesPerSecond());
	networking.processInbound(level);
	level.process();
	networking.processOutbound();
    }

    @Override
    public void dispose() {
	level.dispose();
    }	
}
