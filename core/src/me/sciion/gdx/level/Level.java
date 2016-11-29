package me.sciion.gdx.level;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalMaterial;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import me.sciion.gdx.level.components.CollisionComponent;
import me.sciion.gdx.level.components.ModelComponent;
import me.sciion.gdx.level.components.PlayerInputComponent;
import me.sciion.gdx.level.components.SpatialComponent;
import me.sciion.gdx.level.components.VelocityComponent;
import me.sciion.gdx.level.system.PhysicsSystem;
import me.sciion.gdx.level.system.PlayerInputSystem;
import me.sciion.gdx.level.system.RenderSystem;
import me.sciion.gdx.utils.ModelConstructer;

public class Level {

    // --==( Worlds )==-- //
    private World world;
    private com.badlogic.gdx.physics.box2d.World physics_world;

    // --==( Mappers )==-- //
    private ComponentMapper<SpatialComponent> spatialMapper;
    private ComponentMapper<ModelComponent> modelMapper;
    private ComponentMapper<CollisionComponent> physicsMapper;
    
    // --==( Archetypes )==-- //
    Archetype characterArchetype;
    Archetype playerArchetype;
    Archetype structureArchetype;
    Archetype markerArchetype;
    Archetype floorArchetype;
    
    // --==( Map )==-- //
    private int player_spawn_entity;
    interface MarkerGenerator{
	void process(MapObject o);
    }
    
    public Level() {
	// --==( Worlds )==-- //
	WorldConfiguration world_config = new WorldConfigurationBuilder()
		.with(new PlayerInputSystem(), new RenderSystem(), new PhysicsSystem())
		.build();
	world = new World(world_config);
	physics_world = new com.badlogic.gdx.physics.box2d.World(Vector2.Zero, true);
	
	// --==( Mappers )==-- //
	spatialMapper = world.getMapper(SpatialComponent.class);
	modelMapper   = world.getMapper(ModelComponent.class);
	physicsMapper = world.getMapper(CollisionComponent.class);
	
	// --==( Archetypes )==-- //
	characterArchetype = new ArchetypeBuilder()
		.add(SpatialComponent.class)
		.add(ModelComponent.class)
		.add(CollisionComponent.class)
		.add(VelocityComponent.class)
		.build(world);
	playerArchetype = new ArchetypeBuilder(characterArchetype)
		.add(PlayerInputComponent.class)
		.build(world);
	structureArchetype = new ArchetypeBuilder()
		.add(SpatialComponent.class)
		.add(CollisionComponent.class)
		.add(ModelComponent.class)
		.build(world);
	
	floorArchetype = new ArchetypeBuilder()
		.add(SpatialComponent.class)
		.add(ModelComponent.class)
		.build(world);
	
	markerArchetype = new ArchetypeBuilder()
		.add(SpatialComponent.class)
		.build(world);
    }

    // Load from file
    public void load(TiledMap levelMap) {
	
	MapLayer structural_level = levelMap.getLayers().get("structures");
	MapLayer marker_level = levelMap.getLayers().get("markers");
	System.out.println(structural_level.toString());
	System.out.println(marker_level.toString());
	
	System.out.println("--===|Structures|===--");
	for(int z = 0; z < (int)levelMap.getProperties().get("height"); z++){
	    for(int x = 0; x <  (int)levelMap.getProperties().get("width");x++){
		    float w = 1.0f;
		    float d = 1.0f;
		    int s = world.create(floorArchetype);
		    spatialMapper.get(s).create(x+w/2.0f, 0, z+d/2.0f, w, 0, d);
		    modelMapper.get(s).instance = ModelConstructer.create(w, 0, d,new Texture(Gdx.files.internal("tile1.png")));


	    }
	}
	for (MapObject o : structural_level.getObjects()) {
	    float x = (Float) o.getProperties().get("x");
	    float z = (Float) o.getProperties().get("y");
	    float w = (Float) o.getProperties().get("width");
	    float d = (Float) o.getProperties().get("height");
	    x = x + w/2.0f;
	    z = z + d/2.0f;
	    int s = world.create(structureArchetype);
	    System.out.println(x + " " + z + " " + w + " " + d);
	    spatialMapper.get(s).create(x, 0, z, w, 1, d);
	    modelMapper.get(s).instance = ModelConstructer.create(w, 1, d,new Texture(Gdx.files.internal("tiled3.png")));
	    physicsMapper.get(s).create(createBody(x, z, w, d, BodyType.StaticBody));
	}
	
	System.out.println("--===|Markers|===--");
	MarkerGenerator spawn = (o) -> {
	    if (o.getName().equals("player_spawn")) {
		System.out.println("player_spawn marker!");
		 float x = (Float) o.getProperties().get("x");
		    float z = (Float) o.getProperties().get("y");
		    float w = (Float) o.getProperties().get("width");
		    float d = (Float) o.getProperties().get("height");
		    x = x + w/2.0f;
		    z = z + d/2.0f;
		    player_spawn_entity = world.create(markerArchetype);
		    spatialMapper.get(player_spawn_entity).create(x, 0, z, w, 0, d);
	    }
	};
	
	for (MapObject o : marker_level.getObjects()) {
	    spawn.process(o);
	}
	
    }
    

    
    public Body createBody(float x,float z, float w, float d, BodyType type){    
	BodyDef def = new BodyDef();
	def.type = type;
	def.position.set(x, z);
	
	Body body = physics_world.createBody(def);
	
	PolygonShape shape = new PolygonShape();
	shape.setAsBox(w/2.0f, d/2.0f);
	
	FixtureDef fixDef = new FixtureDef();
	fixDef.shape = shape;
	fixDef.density = 1f;
	//fixDef.friction = 20.0f;
	Fixture f = body.createFixture(fixDef);
	shape.dispose();
	body.setFixedRotation(true);
	return body;
    }
    
    // Stuff that should be run once
    public void setup() {
	System.out.println("Setup level " + toString());
	
	int playerEntity = world.create(playerArchetype);
	Vector3 p = spatialMapper.get(player_spawn_entity).position;
	spatialMapper.get(playerEntity).create(p.x, 0, p.z, 0.8f, 1, 0.8f);
	Texture texture = new Texture(Gdx.files.internal("arrow1.png"));

	//Decal decal = Decal.newDecal(new TextureRegion(texture),true);
	 modelMapper.get(playerEntity).instance = ModelConstructer.create(0.8f, 1, 0.8f, new Texture(Gdx.files.internal("grid2.png")));
	physicsMapper.get(playerEntity).create(createBody(p.x, p.z, 0.8f, 0.8f, BodyType.DynamicBody));
	
	
    }
    
    
    // Stuff that is drawn
    public void process() {
	physics_world.step(Gdx.graphics.getDeltaTime(), 6, 2);
	world.setDelta(Gdx.graphics.getDeltaTime());
	world.process();
    }


    public void dispose() {
	//entities.dispose();
    }

}
