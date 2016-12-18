package me.sciion.gdx.netcode;

import com.esotericsoftware.kryonet.Listener;

public class ServerKryo extends Listener {
/*
    private Server server;

    private ServerLevel level;
    private Hashtable<Integer, Integer> internalExternal;
    private Hashtable<Integer, Integer> externalIneternal;
    private Hashtable<Integer,Integer> registeredEntities;
    
    private int externalCounter = 2000;
    private int connectionID = -1;
    
    private Channels channels;
    
    private int getNextExternal() {
	return externalCounter++;
    }

    public ServerKryo() {
	internalExternal = new Hashtable<Integer, Integer>();
	externalIneternal = new Hashtable<Integer, Integer>();
	registeredEntities = new Hashtable<Integer,Integer>();
    }

    public int getInternal(int external) {
	return externalIneternal.get(external);
    }
    
    public int getExternal(int internal){
	if(internalExternal.containsKey(internal)){
	    return internalExternal.get(internal); 
	}
	return -1;
    }
    
    public boolean isRegistered(int external){
	return externalIneternal.containsKey(external);
    }
    
    public void processInbound(){
	channels.processInbound();
    }
    
    public void processOutbound(){
	channels.processOutbound();
    }
    
    public void setup(ServerLevel level) {
	this.level = level;
	
	channels = new Channels() {
	    
	    @Override
	    public void processOutbound() {
		//System.out.println("Outbound size: " + outbound.size);

		NetworkMessage message = dequeueOutbound();
		while (message != null) {
		    //System.out.println("Sending message with owner: "+ message.owner + " from server");
		    if(message.tcp)
			server.sendToAllTCP(message);
		    else
			server.sendToAllUDP(message);
		    message = channels.dequeueOutbound();
		}
	    }
	    
	    @Override
	    public void processInbound() {
		//System.out.println("Inbound size: " + inbound.size);

		NetworkMessage message = dequeueInbound();
		while (message != null) {
		    if(message instanceof EntityMessage){
			level.enqueInbound((EntityMessage) message);
		    }
		    else if(message instanceof EntitySync){
			for(int i : registeredEntities.keySet()){
			    System.out.println("Create sync message with id: " + i);
			    EntityCreated m = new EntityCreated();
			    m.id = i;
			    m.owner = registeredEntities.get(i);
			    m.poistion = level.getComponent(SpatialComponent.class, externalIneternal.get(i)).position;
			    m.dimensions = new Vector3(0.8f, 1.0f, 0.8f);

			    m.type = EntityType.NETWORKED;
			    enqueOutbound(m);
			}
		    }
		    message = dequeueInbound();
		}
	    }
	};
	
	server = new Server();
	KryoUtils.register(server.getKryo());
	server.start();
	try {
	    server.bind(KryoUtils.TCP, KryoUtils.UDP);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	server.addListener(this);
	
	EntityCreated message = new EntityCreated();
	message.id = -1;
	message.poistion = level.getComponent(SpatialComponent.class, level.getMarker("player_spawn")).position;
	message.dimensions = new Vector3(0.8f, 1.0f, 0.8f);
	message.owner = connectionID;
	message.type = EntityType.PLAYER;
	level.enqueInbound(message);
    }

    @Override
    public void connected(Connection connection) {
	Log.info("Server received connection from client: " + connection.getID());

	// Make sure entity created on server
	EntityCreated message = new EntityCreated();
	message.owner = connection.getID();
	message.poistion = level.getComponent(SpatialComponent.class, level.getMarker("player_spawn")).position;
	message.dimensions = new Vector3(0.8f, 1.0f, 0.8f);
	message.type = EntityType.NETWORKED;
	level.enqueInbound(message);
	// Create client player entity ´
	// Send update to all clients

    }

    @Override
    public void disconnected(Connection connection) {
	Log.info("Server received disconnect from client: " + connection.getID());
	for(int i : registeredEntities.keySet()){
	    if(registeredEntities.get(i) == connection.getID()){
		EntityDelete delete = new EntityDelete();
		delete.owner = registeredEntities.get(i);
		delete.id = i;
	
		enqueInbound(delete);
	    }
	}
    }

    @Override
    public void received(Connection connection, Object object) {
	//Log.info("Server received message from client:" + connection.getID());
	if (object instanceof EntityMessage) {
	    EntityMessage message = (EntityMessage) object;
	    level.enqueInbound(message);
	} else if (object instanceof NetworkMessage) {
	    NetworkMessage message = (NetworkMessage) object;
	    enqueInbound(message);
	}
    }

    public int registerEntity(int internal, Vector3 position, int owner) {
	int external = getNextExternal();
	internalExternal.put(internal, external);
	externalIneternal.put(external, internal);
	registeredEntities.put(external,owner);
	EntityCreated message = new EntityCreated();
	message.owner = owner;
	message.id = external;
	message.poistion = position;
	message.dimensions = new Vector3(0.8f, 1.0f, 0.8f);

	message.type = EntityType.NETWORKED;
	enqueOutbound(message);
	return external;
    }
    
    public void unregisterEntity(int internal) {
	int external = internalExternal.get(internal);
	int owner = registeredEntities.get(external);
	internalExternal.remove(internal);
	externalIneternal.remove(external);
	registeredEntities.remove(external);
	EntityDelete message = new EntityDelete();
	message.id = external;
	message.owner = owner;
	enqueOutbound(message);
    }

    public void enqueInbound(NetworkMessage message) {
	channels.enqueInbound(message);
    }

    public void enqueOutbound(NetworkMessage message) {
	channels.enqueOutbound(message);
    }

    public NetworkMessage dequeueInbound() {
	return channels.dequeueInbound();
    }

    public NetworkMessage dequeueOutbound() {
	return channels.dequeueOutbound();
    }

*/
}
