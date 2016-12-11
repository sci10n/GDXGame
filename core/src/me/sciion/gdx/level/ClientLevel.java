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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import me.sciion.gdx.level.components.CollisionComponent;
import me.sciion.gdx.level.components.ModelComponent;
import me.sciion.gdx.level.components.NetworkedInput;
import me.sciion.gdx.level.components.SpatialComponent;
import me.sciion.gdx.level.system.AutoInputSystem;
import me.sciion.gdx.level.system.NetworkInputSystem;
import me.sciion.gdx.level.system.PhysicsSystem;
import me.sciion.gdx.level.system.PlayerInputSystem;
import me.sciion.gdx.level.system.RenderSystem;
import me.sciion.gdx.netcode.Channels;
import me.sciion.gdx.netcode.ClientKryo;
import me.sciion.gdx.netcode.ServerKryo;
import me.sciion.gdx.utils.Archetypes;
import me.sciion.gdx.utils.EntityType;
import me.sciion.gdx.utils.ModelConstructer;
import me.sciion.gdx.utils.PhysicsUtils;
import me.sciion.gdx.utils.KryoMessage.EntityCreated;
import me.sciion.gdx.utils.KryoMessage.EntityDelete;
import me.sciion.gdx.utils.KryoMessage.EntityInput;
import me.sciion.gdx.utils.KryoMessage.EntityMessage;

public class ClientLevel extends Channels {

    private World world;
    private com.badlogic.gdx.physics.box2d.World physics_world;
    private ClientKryo client;
    private Archetypes archetypes;
    
    private boolean hasTargetEntity = false;
    
    private Hashtable<String,Integer> markers;
    
    public <T extends Component> T getComponent(Class<T> clazz, int internalID) {
	T t = world.getMapper(clazz).getSafe(internalID);
	return t;
    }
    
    public int getMarker(String marker){
	if(markers.contains(marker)){
	    return markers.get(marker);
	}
	System.err.println("Marker with name: " + marker + " not found!");
	return -1;
    }
    
    public ClientLevel() {
	markers = new Hashtable<String,Integer>();
	WorldConfiguration world_config = new WorldConfigurationBuilder().with(new PlayerInputSystem(this),new RenderSystem(), new PhysicsSystem(), new AutoInputSystem(this), new NetworkInputSystem()).build();
	world = new World(world_config);
	
	archetypes = new Archetypes(world);
	physics_world = new com.badlogic.gdx.physics.box2d.World(Vector2.Zero, true);
	
    }
    
    public void setup(ClientKryo client){
	this.client = client;
    }
    
    public void process(){
	processInbound();
	physics_world.step(Gdx.graphics.getDeltaTime(), 6, 2);
	world.setDelta(Gdx.graphics.getDeltaTime());
	world.process();
	processOutbound();
    }
    
    
    @Override
    public void processOutbound() {
	EntityMessage message = (EntityMessage) dequeueOutbound();
	while(message != null){
	    message.id = client.getExternal(message.id);
	    client.enqueOutbound(message);
	    message = (EntityMessage) dequeueOutbound();
	}
    }

    @Override
    public void processInbound() {
	EntityMessage message = (EntityMessage) dequeueInbound();
	while(message != null){
	    // ----------------
	    if(message instanceof EntityCreated){
		if(client.isRegistered(message.id)){
		    System.err.println("Entity " + message.id + " already exist on client!");
		}
		else{
        		System.out.println(message.toString());
        		if(!hasTargetEntity && ((EntityCreated) message).type == EntityType.NETWORKED){
        		    ((EntityCreated) message).type = EntityType.PLAYER;
        		    hasTargetEntity = true;
        		}
        		switch(((EntityCreated) message).type){
                		case NPC:
                			    
                		break;
                		case NETWORKED:
                		{
                		    Vector3 spawnPosition = ((EntityCreated) message).poistion; //getComponent(SpatialComponent.class, markers.get("player_spawn")).position;
                		    Vector3 dimensions = new Vector3(0.8f,1.0f,0.8f);
                		    int internal = world.create(archetypes.networked);
                		    getComponent(SpatialComponent.class, internal).create(spawnPosition.cpy(), dimensions.cpy());
                		    client.registerEntity(internal, message.id);
                		    RandomXS128 r = new RandomXS128(message.id);
                		    getComponent(ModelComponent.class,internal).instance = ModelConstructer.create(dimensions.x,dimensions.y,dimensions.z, new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), 1.0f));
                		}
                		break;
                		case PLAYER:
                		{
                		    Vector3 spawnPosition = ((EntityCreated) message).poistion; //getComponent(SpatialComponent.class, markers.get("player_spawn")).position;
                		    Vector3 dimensions = new Vector3(0.8f,1.0f,0.8f);
                		    int internal = world.create(archetypes.player);
                		    getComponent(SpatialComponent.class, internal).create(spawnPosition.cpy(), dimensions.cpy());
                		    getComponent(CollisionComponent.class, internal).create(PhysicsUtils.createBody(physics_world,spawnPosition.x, spawnPosition.z, dimensions.x, dimensions.z, BodyType.DynamicBody));
                		    client.registerEntity(internal, message.id);
                		    RandomXS128 r = new RandomXS128(message.id);
                		    getComponent(ModelComponent.class,internal).instance = ModelConstructer.create(dimensions.x,dimensions.y,dimensions.z, new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), 1.0f));
                		}
                		break;
                		case STATIC:
                		    
                		break;
        		}
		}
	    } else if(message instanceof EntityDelete){
		if(!client.isRegistered(message.id)){
		    System.err.println("Entity " + message.id + " not on client!");
		}
		else{
		    int internal = client.getInternal(message.id);
		    world.delete(internal);
		    client.unregisterEntity(internal);
		}
	    } else if(message instanceof EntityInput){
		if(!client.isRegistered(message.id)){
		    System.err.println("Entity " + message.id + " not on client!");
		}
		else{
		    int internal = client.getInternal(message.id);
		    getComponent(NetworkedInput.class, internal).inbound.addLast(message);
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
 	   getComponent(CollisionComponent.class, s).create(PhysicsUtils.createBody(physics_world,x, z, w, d, BodyType.StaticBody));
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
 		getComponent(CollisionComponent.class, s).create(PhysicsUtils.createBody(physics_world, x, z, 0.8f, 0.8f, BodyType.DynamicBody));
 	    }
 	};

 	for (MapObject o : marker_level.getObjects()) {
 	    player_spawn.process(o);
 	    npc_spawn.process(o);
 	}
     }
}
