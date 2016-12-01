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
    public MyGame(boolean server){
	this.server = server;
    }
    
    @Override
    public void create() {
	loader = new LevelLoader();
	level = loader.load("maps/dummy_map.tmx");
	level.setup();
	networking = new KryoStasis();
	if( networking.createServer()){
	   
	}else{
	    networking.createClient("127.0.0.1");
	}

    }
    
    
    @Override
    public void render() {
	//System.out.println(Gdx.graphics.getFramesPerSecond());
	networking.processInbound(level.getNetworkMapper());
	level.process();
	networking.processOutbound();
    }

    @Override
    public void dispose() {
	level.dispose();
    }	
}
