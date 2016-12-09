package me.sciion.gdx.netcode;

import java.io.IOException;
import java.util.Hashtable;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import me.sciion.gdx.level.Level;
import me.sciion.gdx.utils.KryoMessage.EntityMessage;
import me.sciion.gdx.utils.KryoMessage.NetworkMessage;

public class ClientKryo extends Listener{
    
    private Client client;
    
    private Hashtable<Integer,Integer> internalExternal;
    private Hashtable<Integer,Integer> externalIneternal;
    
    private KryoChannels channels;
    
    private String serverIP;
    private Level level;
    
    public ClientKryo(){
	internalExternal = new Hashtable<Integer,Integer>();
	externalIneternal = new Hashtable<Integer,Integer>();

    }
    
    public void registerEntity(int internal, int external){ 
	internalExternal.put(internal, external);
	externalIneternal.put(external, internal);
    }
    
    public void processOutbound(){
	NetworkMessage message = channels.dequeueOutbound();
	while(message != null){
	    client.sendTCP(message);
	    message = channels.dequeueOutbound();
	}
    }
    
    public void processInbound(){
	NetworkMessage message = channels.dequeueInbound();
	while(message != null){
	    message = channels.dequeueInbound();
	}
    }
    
    public void setup(String serverIP, Level level){
	this.level = level;
	this.serverIP = serverIP;
	client = new Client();
	client.start();
	try {
	    client.connect(5000, serverIP, KryoUtils.TCP, KryoUtils.UDP);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	client.addListener(this);
    }

    @Override
    public void connected(Connection connection) {
	Log.info("Client connected to server: " + serverIP + " with id: " + connection.getID());
	channels = new KryoChannels(connection.getID());
    }

    @Override
    public void disconnected(Connection connection) {
	Log.info("Client disconnected from server: " + serverIP + " with id: " + connection.getID());
	
    }

    @Override
    public void received(Connection connection, Object object) {
	Log.info("Client recieved message:\t" + object + "\tto server: " + serverIP + " with id: " + connection.getID());
	if(object instanceof EntityMessage){
	    EntityMessage message = (EntityMessage) object;
	    level.enqueInbound(message);
	} else if (object instanceof NetworkMessage) {
	    NetworkMessage message = (NetworkMessage) object;
	    channels.enqueInbound(message);
	}
    }
}
