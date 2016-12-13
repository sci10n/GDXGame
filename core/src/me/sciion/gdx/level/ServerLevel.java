package me.sciion.gdx.level;

import java.util.Hashtable;

import com.artemis.Component;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import me.sciion.gdx.level.components.CollisionComponent;
import me.sciion.gdx.level.components.HealthComponent;
import me.sciion.gdx.level.components.ModelComponent;
import me.sciion.gdx.level.components.NetworkedInput;
import me.sciion.gdx.level.components.PlayerInputComponent;
import me.sciion.gdx.level.components.SpatialComponent;
import me.sciion.gdx.level.system.AutoInputSystem;
import me.sciion.gdx.level.system.CollisionResolvingSystem;
import me.sciion.gdx.level.system.HealthSystem;
import me.sciion.gdx.level.system.NetworkInputSystem;
import me.sciion.gdx.level.system.PhysicsSystem;
import me.sciion.gdx.level.system.PlayerInputSystem;
import me.sciion.gdx.level.system.ProjectileSystem;
import me.sciion.gdx.level.system.RenderSystem;
import me.sciion.gdx.netcode.Channels;
import me.sciion.gdx.netcode.ServerKryo;
import me.sciion.gdx.utils.Archetypes;
import me.sciion.gdx.utils.InputUtils;
import me.sciion.gdx.utils.ModelConstructer;
import me.sciion.gdx.utils.PhysicsUtils;
import me.sciion.gdx.utils.KryoMessage.EntityCreated;
import me.sciion.gdx.utils.KryoMessage.EntityDelete;
import me.sciion.gdx.utils.KryoMessage.EntityInput;
import me.sciion.gdx.utils.KryoMessage.EntityMessage;
import me.sciion.gdx.utils.KryoMessage.Input;

public class ServerLevel extends Channels {

    private PlayerInputSystem pis;
    private RenderSystem rs;
    private PhysicsSystem ps;
    private CollisionResolvingSystem crs;
    private AutoInputSystem ais;
    private NetworkInputSystem nis;
    
    private World world;
    private ServerKryo server;
    private Archetypes archetypes;
    
    private Hashtable<String,Integer> markers;
    
    private int entityFocus = -1;
    public <T extends Component> T getComponent(Class<T> clazz, int internalID) {
	T t = world.getMapper(clazz).getSafe(internalID);
	return t;
    }
    
    public <T extends Component> T addComponent(Class<T> clazz, int internal){
	T t = world.getMapper(clazz).create(internal);
	return t;
    }
    
    public int getMarker(String marker){
	System.out.println(markers.containsKey(marker));
	if(markers.containsKey(marker)){
	    return markers.get(marker);
	}
	System.err.println("Marker with name: " + marker + " not found!");
	return -1;
    }
    
    public ServerLevel() {
	
	pis = new PlayerInputSystem(this);
	rs = new RenderSystem();
	ps = new PhysicsSystem();
	crs = new CollisionResolvingSystem();
	ais = new AutoInputSystem(this);
	nis = new NetworkInputSystem(this);
	
	markers = new Hashtable<String,Integer>();
	WorldConfiguration world_config = new WorldConfigurationBuilder().with(pis,rs,ps,crs,ais,nis, new HealthSystem(this), new ProjectileSystem(this)).build();
	world = new World(world_config);
	
	archetypes = new Archetypes(world);

	
    }
    
    public void setup(ServerKryo server){
	this.server = server;
    }
    
    public void process(){
	processInbound();
	world.setDelta(Gdx.graphics.getDeltaTime());
	world.process();
	processOutbound();
    }
    
    
    @Override
    public void processOutbound() {
	EntityMessage message = (EntityMessage) dequeueOutbound();
	while(message != null){
	    if(server.getExternal(message.id) == -1){
		if(message instanceof EntityCreated){
			server.registerEntity(message.id, ((EntityCreated) message).poistion, message.owner);
		}
	    }
	    message.id = server.getExternal(message.id);
	    server.enqueOutbound(message);
	    message = (EntityMessage) dequeueOutbound();
	}
    }

    @Override
    public void processInbound() {
	EntityMessage message = (EntityMessage) dequeueInbound();
	while(message != null){
	    // ----------------
	    if(message instanceof EntityCreated){
		switch(((EntityCreated) message).type){
		case NPC:
		{
		    Vector3 spawnPosition = ((EntityCreated) message).poistion; //getComponent(SpatialComponent.class, markers.get("player_spawn")).position;
		    Vector3 dimensions = new Vector3(0.8f,1.0f,0.8f);
		    int internal = world.create(archetypes.npc);
		    getComponent(SpatialComponent.class, internal).create(spawnPosition.cpy(), dimensions.cpy());
		    //addComponent(CollisionComponent.class, internal).create(PhysicsUtils.createBody(physics_world,spawnPosition.x, spawnPosition.z, dimensions.x, dimensions.z, BodyType.DynamicBody));
		    int external = server.registerEntity(internal, spawnPosition, message.owner);
		    RandomXS128 r = new RandomXS128(external);
		    getComponent(ModelComponent.class,internal).instance = ModelConstructer.create(dimensions.x,dimensions.y,dimensions.z, new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), 1.0f));

		}
		break;
		case NETWORKED:
		{
		    Vector3 spawnPosition = ((EntityCreated) message).poistion; //getComponent(SpatialComponent.class, markers.get("player_spawn")).position;
		    Vector3 dimensions = ((EntityCreated) message).dimensions;
		    int internal = world.create(archetypes.networked);
		    getComponent(SpatialComponent.class, internal).create(spawnPosition.cpy(), dimensions.cpy());
		    Body b = PhysicsUtils.createBody(ps.getPhysicsWorld(),spawnPosition.x, spawnPosition.z, dimensions.x, dimensions.z, BodyType.KinematicBody, true);
		    b.setUserData(internal);
		    addComponent(CollisionComponent.class, internal).create(b );
		    int external = server.registerEntity(internal, spawnPosition, message.owner);
		    RandomXS128 r = new RandomXS128(external);
		    getComponent(ModelComponent.class,internal).instance = ModelConstructer.create(dimensions.x,dimensions.y,dimensions.z, new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), 1.0f));
		    addComponent(HealthComponent.class, internal).create(100, 100);

		}
		break;
		case PLAYER:
		{
		    Vector3 spawnPosition = ((EntityCreated) message).poistion; //getComponent(SpatialComponent.class, markers.get("player_spawn")).position;
		    Vector3 dimensions = new Vector3(0.8f,1.0f,0.8f);
		    int internal = world.create(archetypes.player);
		    getComponent(SpatialComponent.class, internal).create(spawnPosition.cpy(), dimensions.cpy());

		    Body b = PhysicsUtils.createBody(ps.getPhysicsWorld(),spawnPosition.x, spawnPosition.z, dimensions.x, dimensions.z, BodyType.KinematicBody,false);
		    b.setUserData(internal);
		    addComponent(CollisionComponent.class, internal).create(b);
		    addComponent(HealthComponent.class, internal).create(100, 100);
		    int external = server.registerEntity(internal, spawnPosition, message.owner);
		    RandomXS128 r = new RandomXS128(external);
		    getComponent(ModelComponent.class,internal).instance = ModelConstructer.create(dimensions.x,dimensions.y,dimensions.z, new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), 1.0f));
		    if(entityFocus == -1){
			entityFocus = internal;
		    }
		    
		}
		break;
		case STATIC:
		    
		break;
		}
	    } else if(message instanceof EntityDelete){
		if(!server.isRegistered(message.id)){
		    System.err.println("Entity " + message.id + " not on server!");
		}
		else{
		    int internal = server.getInternal(message.id);
		    world.delete(internal);
		    server.unregisterEntity(internal);
		}
	    } else if(message instanceof EntityInput){
		if(!server.isRegistered(message.id)){
		    System.err.println("Entity " + message.id + " not on server!");
		}
		else{
		    EntityInput input = ((EntityInput)message);
		    int internal = server.getInternal(message.id);
		    getComponent(NetworkedInput.class, internal).inbound.addLast(message);
		    if(input.type == Input.ACTIVATE){
			Vector3 v = InputUtils.playerMouse();
			v.sub(input.position.x,0, input.position.z);
		    }
		}
	    }
	    // ----------------
	    message = (EntityMessage) dequeueInbound();
	}
    }
    
    public void load(TiledMap levelMap) {

 	MapLayer structural_level = levelMap.getLayers().get("structures");
 	MapLayer marker_level = levelMap.getLayers().get("markers");

 	System.out.println("--===|Structures|===--");
 	for (int z = 0; z < (int) levelMap.getProperties().get("height"); z++) {
 	    for (int x = 0; x < (int) levelMap.getProperties().get("width"); x++) {
 		float w = 1.0f;
 		float d = 1.0f;
 		int s = world.create(archetypes.floor);
 		getComponent(SpatialComponent.class, s).create(x + w / 2.0f, 0, z + d / 2.0f, w, 0, d);
 		getComponent(ModelComponent.class, s).instance = ModelConstructer.create(w, 0, d, Color.GRAY);
 	    }
 	}
 	for (MapObject o : structural_level.getObjects()) {
 	    float x = (Float) o.getProperties().get("x");
 	    float z = (Float) o.getProperties().get("y");
 	    float w = (Float) o.getProperties().get("width");
 	    float d = (Float) o.getProperties().get("height");
 	    x = x + w / 2.0f;
 	    z = z + d / 2.0f;
 	    int s = world.create(archetypes.structure);
 	    float h = 1.0f;
 	   getComponent(SpatialComponent.class, s).create(x, 0, z, w, h, d);
 	   getComponent(ModelComponent.class, s).instance = ModelConstructer.create(w, h, d, Color.DARK_GRAY);
 	   getComponent(CollisionComponent.class, s).create(PhysicsUtils.createBody(ps.getPhysicsWorld(),x, z, w, d, BodyType.StaticBody,false));
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
 		int m = world.create(archetypes.marker);
 		getComponent(SpatialComponent.class, m).create(x, 0, z, w, 0, d);
 		markers.put("player_spawn", m);
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
 		int s = world.create(archetypes.npc);
 		getComponent(SpatialComponent.class, s).create(x, 0, z, 0.8f, 1, 0.8f);
 		getComponent(ModelComponent.class, s).instance = ModelConstructer.create(0.8f, 1, 0.8f, Color.NAVY);
 		Body b = PhysicsUtils.createBody(ps.getPhysicsWorld(), x, z, 0.8f, 0.8f, BodyType.DynamicBody, false);
 		b.setUserData(s);
 		getComponent(CollisionComponent.class, s).create(b);
		addComponent(HealthComponent.class, s).create(100, 100);
 	    }
 	};
 	
 	MarkerGenerator dummy_sensor = (o) -> {
 	    if (o.getName().equals("dummy_marker")) {
 		System.out.println("dummy_marker marker!");
 		float x = (Float) o.getProperties().get("x");
 		float z = (Float) o.getProperties().get("y");
 		float w = (Float) o.getProperties().get("width");
 		float d = (Float) o.getProperties().get("height");
 		x = x + w / 2.0f;
 		z = z + d / 2.0f;
 		int s = world.create(archetypes.marker);
 		getComponent(SpatialComponent.class, s).create(x, 0, z, 1.0f, 1,1.0f);
 		addComponent(ModelComponent.class, s).instance = ModelConstructer.create(1.0f, 1, 1.0f, Color.NAVY);
 		Body b = PhysicsUtils.createBody(ps.getPhysicsWorld(), x, z, 1.0f, 1.0f, BodyType.StaticBody, true);
 		b.setUserData(s);
 		addComponent(CollisionComponent.class, s).create(b);
		addComponent(HealthComponent.class, s).create(100, 100);
 	    }
 	};

 	for (MapObject o : marker_level.getObjects()) {
 	    player_spawn.process(o);
 	    npc_spawn.process(o);
 	   dummy_sensor.process(o);
 	}
     }
}
