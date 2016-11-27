package me.sciion.gdx;

import com.badlogic.gdx.ApplicationAdapter;import com.kotcrab.vis.runtime.spriter.Player;

import me.sciion.gdx.game.render.Renderer;
import me.sciion.gdx.level.Level;
import me.sciion.gdx.level.LevelLoader;
import me.sciion.gdx.level.entity.Entity;
import me.sciion.gdx.level.entity.EntityManager;
import me.sciion.gdx.level.entity.component.PlayerInput;
import me.sciion.gdx.level.entity.component.SpatialComponent;
import me.sciion.gdx.netcode.GameClient;
import me.sciion.gdx.netcode.GameServer;

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
