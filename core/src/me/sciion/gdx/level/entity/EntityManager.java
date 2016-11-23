package me.sciion.gdx.level.entity;

import com.badlogic.gdx.utils.Array;

import me.sciion.gdx.game.render.Render3d;

public class EntityManager {

    private Array<Entity> entities;
    private static int nextId = 1;
    
    public EntityManager(){
	entities = new Array<Entity>();
    }
    
    public void addEntity(Entity entity){
	if(!entities.contains(entity, false)){
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
    
    public Entity createEntity(){
	Entity e = new Entity(nextId++);
	return e;
    }
    
    public void tick(){
	for(Entity e: entities){
	    e.tick();
	}
    }
    
    public void render(Render3d render){
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
