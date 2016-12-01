package me.sciion.gdx.utils;

import java.io.IOException;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Queue;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import me.sciion.gdx.level.components.NetworkedInput;
import me.sciion.gdx.utils.KryoMessage.InputMessage;

public class KryoStasis {

    public static final int TCP = 5580;
    public static final int UDP = 5640;

    
    public Queue<InputMessage> inbounds;
    public Queue<InputMessage> outbound;
    private Server server;
    private Client client;

    public KryoStasis(){
	inbounds = new Queue<InputMessage>();
	outbound = new Queue<InputMessage>();
    }
    
    public void addOutbound(InputMessage output){
	outbound.addLast(output);
    }
    
    
    public void processOutbound(){
	while(outbound.size >= 0){
	    if(server != null){
		server.sendToAllTCP(outbound.removeFirst());
	    } else if(client != null) {
		client.sendTCP(outbound.removeFirst());
	    }
	}
    }
    
    public void processInbound(ComponentMapper<NetworkedInput> components){
	while(inbounds.size >= 0){
	    InputMessage message = inbounds.removeFirst();
	    if(server != null){
		server.sendToAllExceptTCP(message.originator, message);
	    }else if(client != null){
		NetworkedInput input = components.getSafe(message.entityId);
		if(input != null){
		    input.inbound.addLast(message);
		}else{
		    System.err.println("Remote entity not existing on client!");
		}
	    }
	}
    }
    
    public static void register(Kryo kryo){
	kryo.register(Vector3.class);
	kryo.register(Input.class);
	kryo.register(InputMessage.class);
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
	
	register(server.getKryo());
	server.addListener(new Listener(){
	    @Override
	    public void connected(Connection connection) {
		System.out.println("Server: " +  connection.getID() + " connected to server...");
	    }
	    @Override
	    public void disconnected(Connection connection) {
		System.out.println("Server: " +  connection.getID() + " disconnected to server...");

	    }
	    @Override
	    public void received(Connection connection, Object object) {
		System.out.println("Server: " +  connection.getID() + " sent object " + object.toString() + " to server...");
		if(object instanceof InputMessage){
		      System.out.println("Client: " + connection.getID() + " received object " + object.toString() + " from server...");
		      InputMessage message = (InputMessage)object;
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
	    }
	    
	    @Override
	    public void disconnected(Connection connection) {
	       System.out.println("Client: disconnected to server...");
	    }
	    
	    @Override
	    public void received(Connection connection, Object object) {
		if(object instanceof InputMessage){
		      System.out.println("Client: " + connection.getID() + " received object " + object.toString() + " from server...");
		      InputMessage message = (InputMessage)object;
		      inbounds.addLast(message);
		}
	    }
	});
	return true;
	
    }
}
