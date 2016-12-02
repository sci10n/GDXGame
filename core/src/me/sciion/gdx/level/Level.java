package me.sciion.gdx.level;

import java.util.HashMap;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;

import me.sciion.gdx.level.components.AutoInputComponent;
import me.sciion.gdx.level.components.CollisionComponent;
import me.sciion.gdx.level.components.ModelComponent;
import me.sciion.gdx.level.components.NetworkedInput;
import me.sciion.gdx.level.components.PlayerInputComponent;
import me.sciion.gdx.level.components.SpatialComponent;
import me.sciion.gdx.level.system.AutoInputSystem;
import me.sciion.gdx.level.system.NetworkSystem;
import me.sciion.gdx.level.system.PhysicsSystem;
import me.sciion.gdx.level.system.PlayerInputSystem;
import me.sciion.gdx.level.system.RenderSystem;
import me.sciion.gdx.utils.KryoStasis;
import me.sciion.gdx.utils.ModelConstructer;
import me.sciion.gdx.utils.KryoMessage.InputMessage;
import me.sciion.gdx.utils.KryoMessage.NetworkMessage;
import me.sciion.gdx.utils.KryoMessage.NewEntity;

public class Level {

    // --==( Worlds )==-- //
    private World world;
    private com.badlogic.gdx.physics.box2d.World physics_world;

    // --==( Mappers )==-- //
    private ComponentMapper<SpatialComponent> spatialMapper;
    private ComponentMapper<ModelComponent> modelMapper;
    private ComponentMapper<CollisionComponent> physicsMapper;
    private ComponentMapper<AutoInputComponent> inputMapper;
    private ComponentMapper<NetworkedInput> networkMapper;
    // --==( Archetypes )==-- //
    Archetype characterArchetype;
    Archetype playerArchetype;
    Archetype npcArchetype;
    Archetype structureArchetype;
    Archetype markerArchetype;
    Archetype floorArchetype;
    Archetype networkedEntity;
    
    // --==( Map )==-- //
    private int player_spawn_entity;
    private int playerEntity;

    interface MarkerGenerator {
	void process(MapObject o);
    }
    
    // --==( Networking )==-- 
    private int externalEntityID = -1;
    
    public int getNextUID(){
	if(externalEntityID == -1){
	    externalEntityID = (networking.netowrkID + 1000) * 100;
	}
	return externalEntityID++;
    }
    
    public HashMap<Integer,Integer> externalToInternal;
    public HashMap<Integer,Integer> internalToExternal;

    private KryoStasis networking;
    public Queue<NetworkMessage> inbound;
    public Level(KryoStasis networking) {
	this.networking = networking;
	// --==( Worlds )==-- //
	WorldConfiguration world_config = new WorldConfigurationBuilder()
		.with(new PlayerInputSystem(networking,this), new RenderSystem(), new PhysicsSystem(), new AutoInputSystem(), new NetworkSystem()).build();
	world = new World(world_config);
	physics_world = new com.badlogic.gdx.physics.box2d.World(Vector2.Zero, true);

	
	inbound = new Queue<NetworkMessage>();
	// --==( Mappers )==-- //
	spatialMapper = world.getMapper(SpatialComponent.class);
	modelMapper = world.getMapper(ModelComponent.class);
	physicsMapper = world.getMapper(CollisionComponent.class);
	inputMapper = world.getMapper(AutoInputComponent.class);
	networkMapper = world.getMapper(NetworkedInput.class);
	
	// --==( Archetypes )==-- //
	characterArchetype = new ArchetypeBuilder()
		.add(SpatialComponent.class)
		.add(ModelComponent.class)
		.add(CollisionComponent.class)
		.build(world);
	playerArchetype = new ArchetypeBuilder(characterArchetype)
		.add(PlayerInputComponent.class).build(world);
	structureArchetype = new ArchetypeBuilder()
		.add(SpatialComponent.class)
		.add(CollisionComponent.class)
		.add(ModelComponent.class).build(world);
	floorArchetype = new ArchetypeBuilder()
		.add(SpatialComponent.class)
		.add(ModelComponent.class)
		.build(world);
	markerArchetype = new ArchetypeBuilder()
		.add(SpatialComponent.class)
		.build(world);
	npcArchetype= new ArchetypeBuilder()
		.add(SpatialComponent.class)
		.add(ModelComponent.class)
		.add(CollisionComponent.class)
		.add(AutoInputComponent.class)
		.build(world);
	networkedEntity = new ArchetypeBuilder()
		.add(SpatialComponent.class)
		.add(ModelComponent.class)
		.add(CollisionComponent.class)
		.add(NetworkedInput.class)
		.build(world);
	
	externalToInternal = new HashMap<Integer,Integer>();
	internalToExternal = new HashMap<Integer,Integer>();

    }
    
    public void addNetworked(int internalID, int externalID){
	System.out.println("added to netowrked " + internalID + " " + externalID);
	externalToInternal.put(externalID, internalID);
	internalToExternal.put(internalID, externalID);
    }
    
    // Load from file
    public void load(TiledMap levelMap) {
	
	MapLayer structural_level = levelMap.getLayers().get("structures");
	MapLayer marker_level = levelMap.getLayers().get("markers");
	System.out.println(structural_level.toString());
	System.out.println(marker_level.toString());

	System.out.println("--===|Structures|===--");
	for (int z = 0; z < (int) levelMap.getProperties().get("height"); z++) {
	    for (int x = 0; x < (int) levelMap.getProperties().get("width"); x++) {
		float w = 1.0f;
		float d = 1.0f;
		int s = world.create(floorArchetype);
		spatialMapper.get(s).create(x + w / 2.0f, 0, z + d / 2.0f, w, 0, d);
		modelMapper.get(s).instance = ModelConstructer.create(w, 0, d,Color.GRAY);
	    }
	}
	for (MapObject o : structural_level.getObjects()) {
	    float x = (Float) o.getProperties().get("x");
	    float z = (Float) o.getProperties().get("y");
	    float w = (Float) o.getProperties().get("width");
	    float d = (Float) o.getProperties().get("height");
	    x = x + w / 2.0f;
	    z = z + d / 2.0f;
	    int s = world.create(structureArchetype);
	    float h = 1.0f;
	    spatialMapper.get(s).create(x, 0, z, w, h, d);
	    modelMapper.get(s).instance = ModelConstructer.create(w, h, d,Color.DARK_GRAY);
	    physicsMapper.get(s).create(createBody(x, z, w, d, BodyType.StaticBody));
	}

	System.out.println("--===|Markers|===--");
	MarkerGenerator player_spawn = (o) -> {
	    if (o.getName().equals("player_spawn")) {
		System.out.println("player_spawn marker!");
		float x = (Float) o.getProperties().get("x");
		float z = (Float) o.getProperties().get("y");
		float w = (Float) o.getProperties().get("width");
		float d = (Float) o.getProperties().get("height");
		x = x + w / 2.0f;
		z = z + d / 2.0f;
		player_spawn_entity = world.create(markerArchetype);
		spatialMapper.get(player_spawn_entity).create(x, 0, z, w, 0, d);
		
		playerEntity = world.create(playerArchetype);
		addNetworked(playerEntity, getNextUID());

		Vector3 p = spatialMapper.get(player_spawn_entity).position;
		spatialMapper.get(playerEntity).create(p.x, 0, p.z, 0.8f, 1, 0.8f);

		// Decal decal = Decal.newDecal(new TextureRegion(texture),true);
		modelMapper.get(playerEntity).instance = ModelConstructer.create(0.8f, 1, 0.8f, Color.LIME);
		physicsMapper.get(playerEntity).create(createBody(p.x, p.z, 0.8f, 0.8f, BodyType.DynamicBody));
		NewEntity message = new NewEntity();
		message.id = playerEntity;
		message.init = p;
		networking.addOutbound(message, this);
	    }
	};

	MarkerGenerator npc_spawn = (o) -> {
	    if (o.getName().equals("npc_spawn")) {
		System.out.println("npc_spawn marker!");
		float x = (Float) o.getProperties().get("x");
		float z = (Float) o.getProperties().get("y");
		float w = (Float) o.getProperties().get("width");
		float d = (Float) o.getProperties().get("height");
		x = x + w / 2.0f;
		z = z + d / 2.0f;
		int npcEntity = world.create(npcArchetype);
		spatialMapper.get(npcEntity).create(x, 0, z, 0.8f, 1, 0.8f);
		modelMapper.get(npcEntity).instance = ModelConstructer.create(0.8f, 1, 0.8f,Color.NAVY);
		physicsMapper.get(npcEntity).create(createBody(x, z, 0.8f, 0.8f, BodyType.DynamicBody));
		inputMapper.get(npcEntity);
	    }
	};

	for (MapObject o : marker_level.getObjects()) {
	    player_spawn.process(o);
	    npc_spawn.process(o);
	}
    }
    
    public Body createBody(float x, float z, float w, float d, BodyType type) {
	BodyDef def = new BodyDef();
	def.type = type;
	def.position.set(x, z);
	Body body = physics_world.createBody(def);
	PolygonShape shape = new PolygonShape();
	shape.setAsBox(w / 2.0f, d / 2.0f);
	FixtureDef fixDef = new FixtureDef();
	fixDef.shape = shape;
	fixDef.density = 1f;
	// fixDef.friction = 20.0f;
	Fixture f = body.createFixture(fixDef);
	shape.dispose();
	body.setFixedRotation(true);
	return body;
    }

    // Stuff that should be run once
    public void setup() {
	System.out.println("Setup level " + toString());
	
    }

    // Stuff that is drawn
    public void process() {
	    processInbound();

	physics_world.step(Gdx.graphics.getDeltaTime(), 6, 2);
	world.setDelta(Gdx.graphics.getDeltaTime());
	world.process();
    }

    
    private void processInbound() {
	while(inbound.size > 0){
	    NetworkMessage message = inbound.removeFirst();
	    if(message instanceof NewEntity){

		NewEntity ne = (NewEntity) message;
		System.out.println("Level recv new entity message with external id: " + ne.id);

		float x = ne.init.x;
		float z = ne.init.z;
		int networkEntity = world.create(networkedEntity);
		spatialMapper.get(networkEntity).create(x, 0, z, 0.8f, 1, 0.8f);
		modelMapper.get(networkEntity).instance = ModelConstructer.create(0.8f, 1, 0.8f,Color.RED);
		physicsMapper.get(networkEntity).create(createBody(x, z, 0.8f, 0.8f, BodyType.DynamicBody));
		inputMapper.get(networkEntity);
		addNetworked(networkEntity,ne.id);
	    }
	    else if(message instanceof InputMessage){
		InputMessage im = (InputMessage) message;
		System.out.println("Level recv input message with external id: " + im.entityId);
		try{
			int id = externalToInternal.get(im.entityId);
			if(networkMapper.getSafe(id) != null){
				networkMapper.getSafe(id).inbound.addLast(im);
			}
			else{
			    System.err.println("Could not resolv external id");
			}
		}catch(NullPointerException e){
		    e.printStackTrace();
		    return;
		}
		
	    }
	}
    }

    public void dispose() {
	// entities.dispose();
    }

    // Used to access from KryoStasis
    public ComponentMapper<NetworkedInput> getNetworkMapper() {
        return networkMapper;
    }

}
