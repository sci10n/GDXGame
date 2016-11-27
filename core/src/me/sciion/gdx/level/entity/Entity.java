package me.sciion.gdx.level.entity;

import com.badlogic.gdx.utils.Array;

import me.sciion.gdx.game.render.Renderer;
import me.sciion.gdx.level.entity.component.Component;
import me.sciion.gdx.level.entity.component.ComponentType;

public class Entity {

    private Array<Component> components;
    private String id;
    private EntityManager manager;
    
    public Entity(String id, EntityManager manager){
	this.id = id;
	this.manager = manager;
	components = new Array<Component>();
    }

    public void setup(){
	for(int i = 0; i < components.size; i++){
	    Component c = components.get(i);
	    c.setup();
	}
    }
    
    public void dispose(){
	for(int i = 0; i < components.size; i++){
	    Component c = components.get(i);
	    c.dispose();
	}
    }
    
    public void tick(){
	for(int i = 0; i < components.size; i++){
	    Component c = components.get(i);
	    c.tick();
	}
    }
    
    public void render(Renderer render){
	for(Component c: components){
	    c.render(render);
	}
    }
    
    public void addComponent(Component c){
	c.setParent(this);
	c.setup();
	components.add(c);
    }
    
    public void deleteComponent(Component c){
	components.removeValue(c, false);
    }
    
    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(ComponentType type){
	for(int i = 0; i < components.size; i++){
	    Component c = components.get(i);
	    if(c.getType() == type){
		return (T) c;
	    }
	}
	return null;
    }
   
    public EntityManager getManager(){
	return manager;
    }
    
    public String getID(){
	return id;
    }
    
    @Override
    public boolean equals(Object obj) {
	if(obj instanceof Entity){
	    return ((Entity)obj).getID().equals(id);
	}
        return false;
    }
}
