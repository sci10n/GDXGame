package me.sciion.gdx.level.entity;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import me.sciion.gdx.game.render.Renderer;

public class EntityManager {

    private Array<Entity> entities;
    
    
    public EntityManager(){
	entities = new Array<Entity>();
    }
    
    public void addEntity(Entity entity){
	if(!entities.contains(entity, false)){
	    entity.setup();
	    entities.add(entity);
	}
    }
    
    public void deleteEntity(Entity e){
	if(entities.contains(e, false)){
	  for(Entity ent: entities){
	      if(ent.equals(e)){
		 ent.dispose();
		entities.removeValue(e, false);
		return;
	      }
	  }
	}
    }
    
    public Entity getEntityById(String id){
	for(int i = 0; i < entities.size; i++){
	    if(entities.get(i).getID().equals(id)){
		return entities.get(i);
	    }
	}
	return null;
    }
    
    public Entity createEntity(){
	Entity e = new Entity("Undefiend_Id");
	return e;
    }
    
    public void tick(){
	for(Entity e: entities){
	    e.tick();
	}
    }
    
    public void render(Renderer render){
	for(Entity e: entities){
	    e.render(render);
	}
    }
    
    public void dispose(){
	for(Entity e : entities){
	    e.dispose();
	}
	entities.clear();
    }
    

}
