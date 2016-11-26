package me.sciion.gdx.level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;

import me.sciion.gdx.game.render.Renderer;
import me.sciion.gdx.level.entity.Entity;
import me.sciion.gdx.level.entity.EntityManager;
import me.sciion.gdx.level.entity.component.BlockRender;
import me.sciion.gdx.level.entity.component.ComponentType;
import me.sciion.gdx.level.entity.component.PlayerInput;
import me.sciion.gdx.level.event.EventChannel;

public class Level {

    private EntityManager entities;
    private EventChannel levelChannel;

    public Level() {
	entities = new EntityManager();
	levelChannel = new EventChannel();
    }

    private interface MarkerGenerator {
	Entity operation(MapObject object);
    }

    // Load from file
    public void load(TiledMap levelMap) {
	MapLayer structural_level = levelMap.getLayers().get("structures");
	MapLayer marker_level = levelMap.getLayers().get("markers");
	System.out.println(structural_level.toString());
	System.out.println(marker_level.toString());
	
	System.out.println("--===|Structures|===--");
	for (MapObject o : structural_level.getObjects()) {
	    Entity e = entities.createEntity();
	    float x = (Float) o.getProperties().get("x");
	    float y = (Float) o.getProperties().get("y");
	    float w = (Float) o.getProperties().get("width");
	    float h = (Float) o.getProperties().get("height");
	    e.addComponent(new BlockRender(x, 0, y, w, h, Color.DARK_GRAY));
	    entities.addEntity(e);
	}
	
	System.out.println("--===|Markers|===--");
	MarkerGenerator spawn = (o) -> {
	    if (o.getName().equals("player_spawn")) {
		System.out.println("player_spawn marker!");
		Entity e = new Entity("player_spawn");
		float x = (Float) o.getProperties().get("x");
		float y = (Float) o.getProperties().get("y");
		float w = (Float) o.getProperties().get("width");
		float h = (Float) o.getProperties().get("height");
		e.addComponent(new BlockRender(x, 0, y, w, h, Color.CHARTREUSE));
		entities.addEntity(e);
	    }
	    return null;
	};
	
	for (MapObject o : marker_level.getObjects()) {
	    spawn.operation(o);
	}
    }

    // Stuff that should be run once
    public void setup() {
	System.out.println("Setup level " + toString());
	
	Entity ps = entities.getEntityById("player_spawn");
	if(ps != null){
	    BlockRender br = ps.getComponent(ComponentType.BlockRender);
	    Entity playerEntity = new Entity("player");
	    float spawn_x = br.getPosition().x;
	    float spawn_y = br.getPosition().z;
	    playerEntity.addComponent(new BlockRender(spawn_x,0,spawn_y,1.0f,1.0f,Color.LIGHT_GRAY));
	    
	    PlayerInput input = new PlayerInput();
	    input.setup();
	    playerEntity.addComponent(input);
	    
	    entities.addEntity(playerEntity);
	}

	/*
	 * Entity dummy1 = entities.createEntity(); dummy1.addComponent(new
	 * BlockRender(0,0,0)); dummy1.setup();
	 * 
	 * entities.addEntity(dummy1); Entity dummy2 = entities.createEntity();
	 * dummy2.addComponent(new BlockRender(2,0,0)); dummy2.addComponent(new
	 * PlayerInput()); dummy2.setup(); entities.addEntity(dummy2);
	 * 
	 * Entity dummy3 = entities.createEntity(); dummy3.addComponent(new
	 * BlockRender(5,0,2)); dummy3.setup(); entities.addEntity(dummy3);
	 */
    }

    // Stuff that is drawn
    public void render(Renderer render) {
	entities.render(render);
    }

    // Stuff that runs every frame
    public void tick() {
	entities.tick();
    }

    public void dispose() {
	entities.dispose();
    }
}
