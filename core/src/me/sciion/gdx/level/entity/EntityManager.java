package me.sciion.gdx.level.entity;

import com.badlogic.gdx.utils.Array;

import me.sciion.gdx.game.render.Renderer;
import me.sciion.gdx.level.entity.component.ComponentType;

public class EntityManager {

    private Array<Entity> entities;
    private static long count = 0;
    
    public EntityManager(){
	entities = new Array<Entity>();
    }
    
    public void addEntity(Entity entity){
	if(!entities.contains(entity, false)){
	    entity.setup();
	    entities.add(entity);
	} else{
	    System.out.println("Entity already in entity manager");
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
	Entity e = new Entity("Undefiend_Id"+count++,this);
	return e;
    }
    
    public Entity createEntity(String id){
	Entity e = new Entity(id,this);
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
    
    public Array<Entity> getEntitiesWithComponent(ComponentType type){
	Array<Entity> e = new Array<Entity>();
	for(int i = 0; i < entities.size; i++){
	    if(entities.get(i).getComponent(type) != null){
		e.add(entities.get(i));
	    }
	}
	return e;
    }
    

}
