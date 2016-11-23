package me.sciion.gdx.level;

import me.sciion.gdx.game.render.Render3d;
import me.sciion.gdx.level.entity.Entity;
import me.sciion.gdx.level.entity.EntityManager;
import me.sciion.gdx.level.entity.component.BlockRender;
import me.sciion.gdx.level.event.EventChannel;

public class Level {

    private EntityManager entities;
    private EventChannel levelChannel;
    public Level(){
	entities = new EntityManager();
	levelChannel = new EventChannel();
    }
    
    // Stuff that should be run once
    public void setup(){
	System.out.println("Setup level " + toString());
	Entity dummy1 = entities.createEntity();
	dummy1.addComponent(new BlockRender(0,0,0));
	dummy1.setup();
	entities.addEntity(dummy1);
	
	Entity dummy2 = entities.createEntity();
	dummy2.addComponent(new BlockRender(2,0,0));
	dummy2.setup();
	entities.addEntity(dummy2);
	
	Entity dummy3 = entities.createEntity();
	dummy3.addComponent(new BlockRender(5,0,2));
	dummy3.setup();
	entities.addEntity(dummy3);
    }
    
    // Stuff that is drawn
    public void render(Render3d render){
	entities.render(render);
    }
    
    // Stuff that runs every frame
    public void tick(){
	entities.tick();
    }
    
    public void dispose(){
	entities.dispose();
    }
}
