package me.sciion.gdx;

import com.badlogic.gdx.ApplicationAdapter;

import me.sciion.gdx.game.render.Renderer;
import me.sciion.gdx.level.Level;
import me.sciion.gdx.level.LevelLoader;

public class MyGame extends ApplicationAdapter {

    //Render3d render;
    Renderer render;
    Level level;
    
    LevelLoader loader;
    @Override
    public void create() {
	loader = new LevelLoader();
	level = loader.load("maps/dummy_map.tmx");
	render = new Renderer();
	level.setup();
    }

    @Override
    public void render() {
	level.tick();
	render.render(level);
    }

    @Override
    public void dispose() {
	level.dispose();
    }	
}
