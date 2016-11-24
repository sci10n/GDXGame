package me.sciion.gdx;

import com.badlogic.gdx.ApplicationAdapter;

import me.sciion.gdx.game.render.Renderer;
import me.sciion.gdx.level.Level;

public class MyGame extends ApplicationAdapter {

    //Render3d render;
    Renderer render;
    Level level;
    @Override
    public void create() {
	level = new Level();
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
