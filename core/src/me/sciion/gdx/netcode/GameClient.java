package me.sciion.gdx.netcode;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;

import me.sciion.gdx.level.Level;
import me.sciion.gdx.netcode.messages.client.ClientMessage;

public class GameClient {

    private Client client;

    public GameClient(Level level){
	client = new Client();
	Network.register(client);
	
	//client.addListener(new ClientListener(level,this));
	
	client.start();
	try {
	    client.connect(Network.TIMEOUT, "127.0.0.1", Network.TCP_SOCK, Network.UDP_SOCK);
	} catch (IOException e) {
	    System.err.println("Could not connect to: " + "127.0.0.1:" + Network.TCP_SOCK + ", 127.0.0.1:" + Network.UDP_SOCK);
	    e.printStackTrace();
	    return;
	}
    }
    public void send(ClientMessage message) {
	if(message == null){
	    System.err.println("Tired to send null message!");
	    return;
	}
	client.sendTCP(message);
    }

}
