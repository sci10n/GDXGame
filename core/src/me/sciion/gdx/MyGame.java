package me.sciion.gdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import me.sciion.gdx.level.ServerLevel;
import me.sciion.gdx.netcode.ClientKryo;
import me.sciion.gdx.netcode.ServerKryo;

public class MyGame extends ApplicationAdapter {

    ServerLevel serverLevel;
  //  ClientLevel clientLevel;
    ClientKryo client;
    ServerKryo server;
    
    private String serverIP;
    public MyGame(String serverIP){
	this.serverIP = serverIP;
    }
    
    @Override
    public void create() {
	
	TmxMapLoader loader  = new TmxMapLoader(new InternalFileHandleResolver());
	TiledMap levelMap = loader.load("maps/dummy_map.tmx");
	
	if(serverIP.isEmpty()) {
	    server = new ServerKryo();
	    serverLevel = new ServerLevel();
	    serverLevel.load(levelMap);
	    serverLevel.setup(server);
	    //server.setup(serverLevel);
	} 
	/*
	 * else {
	    client = new ClientKryo();
	    clientLevel = new ClientLevel();
	    clientLevel.load(levelMap);
	    clientLevel.setup(client);
	    client.setup(serverIP, clientLevel);
	}
	*/
    }
    
    @Override
    public void render() {
	//System.out.println(Gdx.graphics.getFramesPerSecond());
	/*
	if(server != null){
	    server.processInbound();
	}
	if(client != null){
	    client.processInbound();
	}
	*/
	if(serverLevel != null){
	    serverLevel.process();
	}
	//if(clientLevel != null){
	//    clientLevel.process();
	//}
	/*
	if(server != null){
	    server.processOutbound();
	}
	if(client != null){
	    client.processOutbound();
	}
	*/
    }

    @Override
    public void dispose() {
    }	
}
