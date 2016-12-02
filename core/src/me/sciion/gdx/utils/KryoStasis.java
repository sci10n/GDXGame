package me.sciion.gdx.utils;

import java.io.IOException;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Queue;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import me.sciion.gdx.level.Level;
import me.sciion.gdx.level.components.NetworkedInput;
import me.sciion.gdx.utils.KryoMessage.Input;
import me.sciion.gdx.utils.KryoMessage.InputMessage;
import me.sciion.gdx.utils.KryoMessage.NetworkMessage;
import me.sciion.gdx.utils.KryoMessage.NewEntity;

public class KryoStasis {
    
    public int netowrkID = 0;
    public static final int TCP = 5580;
    public static final int UDP = 5640;

    public Queue<NetworkMessage> inbounds;
    public Queue<NetworkMessage> outbound;
    private Server server;
    private Client client;

    public KryoStasis(){
	inbounds = new Queue<NetworkMessage>();
	outbound = new Queue<NetworkMessage>();
    }
    
    public void addOutbound(NetworkMessage output, Level level){
	output.originator = netowrkID;
	if(output instanceof NewEntity){
	    NewEntity ne = (NewEntity) output;
	    System.out.println(level.internalToExternal + " " + ne.id);
	    ne.id = level.internalToExternal.get(ne.id);
	} else if(output instanceof InputMessage){
	    InputMessage im = (InputMessage) output;
	    if(im.entityId < 999){
		System.err.println("Tried to send message with internal id! " +im.entityId + "  " + im.toString());
		return;
	    }

	}
	outbound.addLast(output);
    }
    
    public void processOutbound(){
	while(outbound.size > 0){
	    if(server != null){
		server.sendToAllTCP(outbound.removeFirst());
	    } else if(client != null) {
		client.sendTCP(outbound.removeFirst());
	    }
	}
    }
    
    public void processInbound(ComponentMapper<NetworkedInput> component, Level level){
	while(inbounds.size > 0){
	    NetworkMessage message = inbounds.removeFirst();
	    level.inbound.addLast(message);
	    if(message instanceof InputMessage){
		InputMessage im = (InputMessage) message;
		   if(server != null){
			server.sendToAllExceptTCP(im.originator, im);
		    }
	    }
	}
    }
    
    public static void register(Kryo kryo){
	kryo.register(Vector3.class);
	kryo.register(Input.class);
	kryo.register(InputMessage.class);
	kryo.register(NewEntity.class);
    }
    
    public boolean createServer(){
	server = new Server();
	server.start();
	try {
	    server.bind(TCP,UDP);
	} catch (IOException e) {
	    e.printStackTrace();
	    server = null;
	    return false;
	}
	netowrkID = -1;
	register(server.getKryo());
	server.addListener(new Listener(){
	    @Override
	    public void connected(Connection connection) {
		System.out.println("Server recv connection from: " +  connection.getID());
		
	    }
	    @Override
	    public void disconnected(Connection connection) {
		System.out.println("Server: " +  connection.getID() + " disconnected to server...");

	    }
	    @Override
	    public void received(Connection connection, Object object) {
		System.out.println("Server: " +  connection.getID() + " sent object " + object.toString() + " to server...");
		if(object instanceof NetworkMessage){
		    NetworkMessage message = (NetworkMessage)object;
		    inbounds.addLast(message);
		}
	    }
	});
	
	return true;
    }
    
    public boolean createClient(String serverIP){
	client = new Client();
	client.start();
	try {
	    client.connect(5000, serverIP, TCP, UDP);
	} catch (IOException e) {
	    e.printStackTrace();
	    client = null;
	    return false;
	}
	
	register(client.getKryo());
	client.addListener(new Listener(){
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
		if(object instanceof InputMessage){
		    InputMessage message = (InputMessage)object;
		    inbounds.addLast(message);
		}
		else if(object instanceof NetworkMessage){
		    NetworkMessage message = (NetworkMessage)object;
		    inbounds.addLast(message);
		}
	    }
	});
	return true;
	
    }
}
