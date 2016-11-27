package me.sciion.gdx.level;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;

import me.sciion.gdx.level.components.CollisionComponent;
import me.sciion.gdx.level.components.ModelComponent;
import me.sciion.gdx.level.components.PlayerInputComponent;
import me.sciion.gdx.level.components.SpatialComponent;
import me.sciion.gdx.level.components.VelocityComponent;
import me.sciion.gdx.level.system.PhysicsSystem;
import me.sciion.gdx.level.system.PlayerInputSystem;
import me.sciion.gdx.level.system.RenderSystem;

public class Level {

    // --== Worlds ==-- //
    private World world;
    private com.badlogic.gdx.physics.box2d.World physics_world;

    // --== Mappers ==-- //
    private ComponentMapper<SpatialComponent> spatialMapper;
    private ComponentMapper<ModelComponent> modelMapper;
    
    // --== Archetypes ==-- //
    Archetype characterArchetype;
    Archetype playerArchetype;
    
    public Level() {
	// --== Worlds ==-- //
	WorldConfiguration world_config = new WorldConfigurationBuilder()
		.with(new PlayerInputSystem(), new RenderSystem(), new PhysicsSystem())
		.build();
	world = new World(world_config);
	physics_world = new com.badlogic.gdx.physics.box2d.World(Vector2.Zero, true);
	
	// --== Mappers ==-- //
	spatialMapper = world.getMapper(SpatialComponent.class);
	modelMapper = world.getMapper(ModelComponent.class);
	
	// --== Archetypes ==-- //
	characterArchetype = new ArchetypeBuilder()
		.add(SpatialComponent.class)
		.add(ModelComponent.class)
		.add(CollisionComponent.class)
		.add(VelocityComponent.class)
		.build(world);
	playerArchetype = new ArchetypeBuilder(characterArchetype)
		.add(PlayerInputComponent.class)
		.build(world);
    }

    // Load from file
    public void load(TiledMap levelMap) {
	/*
	MapLayer structural_level = levelMap.getLayers().get("structures");
	MapLayer marker_level = levelMap.getLayers().get("markers");
	System.out.println(structural_level.toString());
	System.out.println(marker_level.toString());
	
	System.out.println("--===|Structures|===--");
	for (MapObject o : structural_level.getObjects()) {
	   // Entity e = entities.createEntity();
	    float x = (Float) o.getProperties().get("x");
	    float y = (Float) o.getProperties().get("y");
	    float w = (Float) o.getProperties().get("width");
	    float h = (Float) o.getProperties().get("height");
	   // e.addComponent(new SpatialComponent(x, 0, y, w, h));
	   // e.addComponent(new BlockRender(Color.DARK_GRAY));
	   // e.addComponent(new CollisionComponent());
	   // entities.addEntity(e);
	}
	
	System.out.println("--===|Markers|===--");
	MarkerGenerator spawn = (o) -> {
	    if (o.getName().equals("player_spawn")) {
		System.out.println("player_spawn marker!");
		//Entity e = entities.createEntity("player_spawn");
		float x = (Float) o.getProperties().get("x");
		float y = (Float) o.getProperties().get("y");
		float w = (Float) o.getProperties().get("width");
		float h = (Float) o.getProperties().get("height");
		//e.addComponent(new SpatialComponent(x, 0, y, w, h));
		//e.addComponent(new BlockRender(Color.CHARTREUSE));
		//entities.addEntity(e);
	    }
	    return null;
	};
	
	for (MapObject o : marker_level.getObjects()) {
	    spawn.operation(o);
	}
	*/
    }

    // Stuff that should be run once
    public void setup() {
	System.out.println("Setup level " + toString());
	
	int playerEntity = world.create(playerArchetype);
	spatialMapper.get(playerEntity).create(0, 0, 0, 1, 1, 1);
	modelMapper.get(playerEntity).create(1, 1, 1);
	
	int dummy1 = world.create(characterArchetype);
	spatialMapper.get(dummy1).create(2, 0, 0, 1, 1, 1);
	modelMapper.get(dummy1).create(1, 1, 1);
	
    }
    
    
    // Stuff that is drawn
    public void process() {
	world.setDelta(Gdx.graphics.getDeltaTime());
	world.process();
    }


    public void dispose() {
	//entities.dispose();
    }

}
