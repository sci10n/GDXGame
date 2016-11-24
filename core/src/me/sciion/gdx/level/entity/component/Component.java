package me.sciion.gdx.level.entity.component;

import com.badlogic.gdx.utils.Json;

import me.sciion.gdx.game.render.Renderer;
import me.sciion.gdx.level.entity.Entity;

public abstract class Component {

    	protected Entity parent;
    	
    	public void setParent(Entity parent){
    	    this.parent = parent;
    	}
    	
    	public abstract void tick();
    	public abstract void render(Renderer render);
    	
    	public abstract ComponentType getType();
    	
    	public abstract void setup();
    	public abstract void dispose();
    	
}
