package me.sciion.gdx.netcode;

import java.io.IOException;
import java.util.Hashtable;

import com.badlogic.gdx.math.Vector3;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import me.sciion.gdx.level.Level;
import me.sciion.gdx.utils.KryoMessage.EntityCreated;
import me.sciion.gdx.utils.KryoMessage.EntityMessage;
import me.sciion.gdx.utils.KryoMessage.NetworkMessage;

public class ServerKryo extends Listener{

    
    private Server server;
    
    private Level level;
    private Hashtable<Integer,Integer> internalExternal;
    private Hashtable<Integer,Integer> externalIneternal;
    
    public KryoChannels channel;
    
    private int externalCounter = 1000;
    private int getNextExternal(){
	return externalCounter++;
    }
    
    public ServerKryo(){
	internalExternal = new Hashtable<Integer,Integer>();
	externalIneternal = new Hashtable<Integer,Integer>();
	channel = new KryoChannels(-1);
    }
    
    public void setup(Level level){
	this.level = level;
	server = new Server();
	server.start();
	try {
	    server.bind(KryoUtils.TCP, KryoUtils.UDP);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	server.addListener(this);
    }
    
    
    public void processOutbound(){
	NetworkMessage message = channel.dequeueOutbound();
	while(message != null){
	    server.sendToAllTCP(message);
	    message = channel.dequeueOutbound();
	}
    }
    
    public void processInbound(){
	NetworkMessage message = channel.dequeueInbound();
	while(message != null){
	    message = channel.dequeueInbound();
	}
    }
    
    // Used to register entity that should exist on all clients (and server), also creates lookup table.
    public boolean registerEntity(int internal, Vector3 position){
	int external = getNextExternal();
	internalExternal.put(internal, external);
	externalIneternal.put(external, internal);
	
	EntityCreated message = new EntityCreated();
	message.originator = -1;
	message.id = external;
	message.poistion = position;
	
	server.sendToAllTCP(message);
	return true;
    }
    
    @Override
    public void connected(Connection connection) {
	Log.info("Server received connection from client: " + connection.getID());
	
	int entity = level.createEntity(level.archetypes.networkedEntity);

	// Create client player entity ´
	// Send update to all clients
	
    }

    @Override
    public void disconnected(Connection connection) {
	Log.info("Server received disconnect from client: " + connection.getID());
    }

    @Override
    public void received(Connection connection, Object object) {
	Log.info("Server received message from client:" + connection.getID() + "\t" + object);
	if(object instanceof EntityMessage){
	    EntityMessage message = (EntityMessage) object;
	    level.enqueInbound(message);
	} else if (object instanceof NetworkMessage) {
	    NetworkMessage message = (NetworkMessage) object;
	    channel.enqueInbound(message);
	}
    }
}
