package me.sciion.gdx;

import com.badlogic.gdx.ApplicationAdapter;

import me.sciion.gdx.level.Level;
import me.sciion.gdx.level.LevelLoader;

public class MyGame extends ApplicationAdapter {

    Level level;
    
    LevelLoader loader;
    
    private boolean isServer = false;
    
    public MyGame(boolean isServer){
	this.isServer = isServer;
    }
    
    @Override
    public void create() {
	loader = new LevelLoader();
	level = loader.load("maps/dummy_map.tmx");
	level.setup();
    }
    

    @Override
    public void render() {
	level.process();
    }

    @Override
    public void dispose() {
	level.dispose();
    }	
}
