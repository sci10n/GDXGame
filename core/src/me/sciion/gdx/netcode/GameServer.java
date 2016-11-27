package me.sciion.gdx.netcode;

import java.io.IOException;

import com.esotericsoftware.kryonet.Server;

import me.sciion.gdx.level.Level;

public class GameServer {
    
    private Server server;
    
    public GameServer(Level level){
	server = new Server();
	Network.register(server);
	
	//server.addListener(new ServerListener(level,server));
	
	server.start();
	try {
	    server.bind(Network.TCP_SOCK, Network.UDP_SOCK);
	} catch (IOException e) {
	    System.err.println("Server could not bind to ports: " + Network.TCP_SOCK + " " + Network.UDP_SOCK);
	    e.printStackTrace();
	    return;
	}
    }
}
