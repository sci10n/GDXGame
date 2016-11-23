package me.sciion.gdx;

import com.badlogic.gdx.ApplicationAdapter;

import me.sciion.gdx.game.render.Render3d;
import me.sciion.gdx.level.Level;
import me.sciion.gdx.level.entity.Entity;

public class MyGame extends ApplicationAdapter {

    //Render3d render;
    Render3d render;
    Level level;
    @Override
    public void create() {
	level = new Level();
	render = new Render3d();
	level.setup();
    }

    @Override
    public void render() {
	level.tick();
	render.render(level);
    }

    @Override
    public void dispose() {
	render.dispose();
    }	
}
