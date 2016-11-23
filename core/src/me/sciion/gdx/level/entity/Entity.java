package me.sciion.gdx.level.entity;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import me.sciion.gdx.game.render.Render3d;
import me.sciion.gdx.level.entity.component.Component;
import me.sciion.gdx.level.entity.component.ComponentType;

public class Entity {

    private Array<Component> components;
    private int id;
    
    public Entity(int id){
	this.id = id;
	components = new Array<Component>();
    }

    public void setup(){
	for(Component c: components){
	    c.setup();
	}
    }
    
    public void dispose(){
	for(Component c: components){
	    c.dispose();
	}
    }
    
    public void tick(){
	for(Component c: components){
	    c.tick();
	}
    }
    
    public void render(Render3d render){
	for(Component c: components){
	    c.render(render);
	}
    }
    
    public void addComponent(Component c){
	components.add(c);
    }
    
    public void deleteComponent(Component c){
	components.removeValue(c, false);
    }
    
    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(ComponentType type){
	for(Component c: components){
	    if(c.getType() == type){
		return (T) c;
	    }
	}
	return null;
    }
    
    public static Entity fromJSON(String s){
	Json json = new Json();
	return json.fromJson(Entity.class,s);
    }
    
    public String toJSON(){
	Json json = new Json();
	return json.toJson(this);
    }
    
    public int getID(){
	return id;
    }
}
