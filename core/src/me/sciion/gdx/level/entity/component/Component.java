package me.sciion.gdx.level.entity.component;

import me.sciion.gdx.game.render.Render3d;

public abstract class Component {

    	public abstract void tick();
    	public abstract void render(Render3d render);
    	
    	public abstract ComponentType getType();
    	
    	public abstract void setup();
    	public abstract void dispose();
}
