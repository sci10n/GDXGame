package me.sciion.gdx.utils;

import java.io.IOException;
import java.util.HashMap;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import me.sciion.gdx.level.Level;
import me.sciion.gdx.level.components.NetworkedInput;
import me.sciion.gdx.level.components.SpatialComponent;
import me.sciion.gdx.utils.KryoMessage.ClientRequests;
import me.sciion.gdx.utils.KryoMessage.EntityCreated;
import me.sciion.gdx.utils.KryoMessage.EntityDelete;
import me.sciion.gdx.utils.KryoMessage.EntityInput;
import me.sciion.gdx.utils.KryoMessage.EntityMessage;
import me.sciion.gdx.utils.KryoMessage.EntitySync;
import me.sciion.gdx.utils.KryoMessage.ExternalId;
import me.sciion.gdx.utils.KryoMessage.Input;
import me.sciion.gdx.utils.KryoMessage.NetworkMessage;

public class KryoStasis {

    public int netowrkID = 0;
    public static final int TCP = 5580;
    public static final int UDP = 5640;

    public Queue<NetworkMessage> inbounds;
    public Queue<NetworkMessage> outbound;
    
    private Server server;
    private Client client;
    private int external = -1;

    private HashMap<Integer,Integer> externalToInternal;
    private HashMap<Integer,Integer> internalToExternal;
    
    private ExternalId lastResponse;
    
    private Array<Integer> registeredEntities;
    
    // For maximum coupling
    private Level  level;
    
    public KryoStasis() {
	inbounds = new Queue<NetworkMessage>();
	outbound = new Queue<NetworkMessage>();
	externalToInternal = new HashMap<Integer,Integer>();
	internalToExternal = new HashMap<Integer,Integer>();
	registeredEntities = new Array<Integer>();
    }

    
    private ExternalId  waitForResponse(){
	while(lastResponse == null){
	    
	}
	System.out.println("Client: got response from server: " + lastResponse);
	
	ExternalId r = lastResponse;
	lastResponse = null;
	return r;
    }
    
    // Used when registering a new entity. called both on local and new entity propagation
    public void registerNewEntity(int internalID, Vector3 position){
	System.out.println("New networked enitty registered with client: " + netowrkID);
	int externalID = -1;
	if(client != null){
	    client.sendTCP(ClientRequests.NextExternalId);
	    externalID =  waitForResponse().id;
	} else{
	    externalID = getExternalID();
	}

	externalToInternal.put(externalID, internalID);
	internalToExternal.put(internalID, externalID); 
	System.out.println(externalToInternal);
	System.out.println(internalToExternal);
	registeredEntities.add(internalID);
	
	// Send new Entity created message to server
	if(client != null){
	    System.out.println("Send new entity message to server!");
	    EntityCreated m = new EntityCreated();
	    m.id = externalID;
	    m.originator = netowrkID;
	    m.poistion = position;
	    client.sendTCP(m);
	}
	
	// Send new entity created message to clients
	if(server != null){
	    
		   System.out.println("Construct new entity messages with eid:" + internalID);
		   EntityCreated m = new EntityCreated();
		   m.id = internalToExternal.get(internalID);
		   m.originator = netowrkID;
		   m.poistion = level.getComponent(SpatialComponent.class,internalID).position;
	 server.sendToAllTCP(m);
	}
    }
    
    public void forceSynch(){
	System.out.println("Synching all the clients with server coordinates...");
	System.out.println(internalToExternal);
	System.out.println(registeredEntities);
	for(int i : registeredEntities){
	    System.out.println("ID: " + i);
	    EntitySync synch = new EntitySync();
	    synch.id = i;
	    synch.originator = netowrkID;
	    synch.position = level.getComponent(SpatialComponent.class, i).position;
	    outbound.addLast(synch);
	}
    }
    
    private int getExternalID() {
	return external++;
    }

    
    public void processOutbound() {
	while (outbound.size > 0) {
	    NetworkMessage message = outbound.removeFirst();
	    message.originator = netowrkID;
	    if(message instanceof EntityMessage){
		EntityMessage em = (EntityMessage) message;
		em.id = internalToExternal.get(em.id);
		System.out.println("Send outbound with id: " + em.id);
	    }
	    if (server != null) {
		server.sendToAllTCP(message);
	    } else if (client != null) {
		client.sendTCP(message);
	    }
	}
    }

    public void processInbound(Level level) {
	while (inbounds.size > 0) {
	    NetworkMessage message = inbounds.removeFirst();
	    if (server != null) {
		server.sendToAllExceptTCP(message.originator, message);
	    }
	    
	    
	    if(message instanceof EntityMessage){
		EntityMessage em = (EntityMessage) message;
		
		//Special case handled outside level
		if(message instanceof EntityCreated){
		    if(externalToInternal.containsKey(((EntityMessage) message).id)){
			System.out.println("Entity with external id: " + em.id + " already exist in this world. Ignoring proppagation");
			continue;
		    }
		    int internal = level.createNetworkedEntity(((EntityCreated) message).poistion);
		    int external = em.id;
		    System.out.println("Ex: " + external + " Int: " + internal);
		    externalToInternal.put(external, internal);
		    internalToExternal.put(internal, external); 
		    registeredEntities.add(internal);
		    continue;
		}
		
		
		System.out.println("Entity message with external:" + em.id);
		if(externalToInternal.containsKey(em.id)){
			em.id = externalToInternal.get(em.id);
			level.inbound.addLast(em);
		} else{
		    System.err.println("Entity does not exist in level "+ em.id);
		}

	    }

	}
    }

    public static void register(Kryo kryo) {
	kryo.register(Vector3.class);
	kryo.register(Input.class);
	kryo.register(NetworkMessage.class);
	kryo.register(EntityMessage.class);
	kryo.register(EntityInput.class);
	kryo.register(EntityCreated.class);
	kryo.register(ClientRequests.class);
	kryo.register(ExternalId.class);
	kryo.register(EntityDelete.class);
	kryo.register(EntitySync.class);
    }

    public boolean createServer() {
	server = new Server();
	server.start();
	try {
	    server.bind(TCP, UDP);
	} catch (IOException e) {
	    e.printStackTrace();
	    server = null;
	    return false;
	}
	netowrkID = -1;
	external = 1000;
	register(server.getKryo());
	server.addListener(new Listener() {
	    @Override
	    public void connected(Connection connection) {
		System.out.println("Server recv connection from: " + connection.getID());
		// Send all registered entities to new client
		for(int i : registeredEntities){
		    System.out.println("Send bulk with id: " + internalToExternal.get(i));
		  EntityCreated m = new EntityCreated();
		  m.id = internalToExternal.get(i);
		  m.originator = netowrkID;
		  m.poistion = level.getComponent(SpatialComponent.class,i).position;
		 connection.sendTCP(m);
		}
	    }

	    @Override
	    public void disconnected(Connection connection) {
		System.out.println("Server: " + connection.getID() + " disconnected to server...");
		
	    }

	    @Override
	    public void received(Connection connection, Object object) {
		System.out.println("Server: " + connection.getID() + " sent object " + object.toString() + " to server...");
		if (object instanceof NetworkMessage) {
		    NetworkMessage message = (NetworkMessage) object;
		    inbounds.addLast(message);
		} else if(object instanceof ClientRequests){
		    ClientRequests r = (ClientRequests) object;
		    if(r == ClientRequests.NextExternalId){
			ExternalId id = new ExternalId();
			id.id = getExternalID();
			connection.sendTCP(id);
		    }
		}
	    }
	});

	return true;
    }

    public boolean createClient(String serverIP) {
	client = new Client();
	register(client.getKryo());

	client.start();
	try {
	    client.connect(5000, serverIP, TCP, UDP);
	} catch (IOException e) {
	    e.printStackTrace();
	    client = null;
	    return false;
	}

	client.addListener(new Listener() {
	    @Override
	    public void connected(Connection connection) {
		System.out.println("Client: connected to server...");
		netowrkID = connection.getID();
	    }

	    @Override
	    public void disconnected(Connection connection) {
		System.out.println("Client: disconnected to server...");
	    }

	    @Override
	    public void received(Connection connection, Object object) {
		if(object instanceof NetworkMessage){
		    inbounds.addLast((NetworkMessage) object);
		} else if(object instanceof ExternalId){
		    lastResponse = (ExternalId) object;
		}
	    }
	});
	return true;

    }


    public void setLevel(Level level) {
        this.level = level;
    }
}
