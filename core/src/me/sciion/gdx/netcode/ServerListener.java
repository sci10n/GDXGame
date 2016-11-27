package me.sciion.gdx.netcode;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import me.sciion.gdx.level.Level;
import me.sciion.gdx.level.entity.Entity;
import me.sciion.gdx.level.entity.EntityManager;
import me.sciion.gdx.level.entity.component.BlockRender;
import me.sciion.gdx.level.entity.component.ComponentType;
import me.sciion.gdx.level.entity.component.SpatialComponent;
import me.sciion.gdx.netcode.messages.client.MoveMessage;
import me.sciion.gdx.netcode.messages.server.NewEntityMessage;

public class ServerListener extends Listener {
/*
    private Level level;
    private Server server;
    
    private Array<String> connectedPlayers;
    public ServerListener(Level level, Server server) {
	this.level = level;
	this.server = server;
	connectedPlayers = new Array<String>();
    }

    // Server side logic when client send (client) message
    @Override
    public void received(Connection connection, Object object) {
	if (object instanceof MoveMessage) {
	    MoveMessage move = ((MoveMessage) object);
	    Entity e = level.getEntities().getEntityById(move.getId());
	    if(e == null){
		System.out.println("Entity " + move.getId() + " does not exist on server!");
		return;
	    }
	    SpatialComponent sc = e.getComponent(ComponentType.Spatial);
	    sc.position.set(move.position);
	    server.sendToAllExceptTCP(connection.getID(),move);
	}
    }

    // Server side logic when client connects
    @Override
    public void connected(Connection connection) {
	System.out.println("Connection established from: " + connection.toString());
	EntityManager manager = level.getEntities();

	// == Get spawn location ==
	Entity spawn_marker = manager.getEntityById("player_spawn");
	SpatialComponent sc = spawn_marker.getComponent(ComponentType.Spatial);
	if (sc == null) {
	    System.err.println("No spawn marker on level");
	    return;
	}
	connectedPlayers.add("player_" + connection.getID());
	Entity player_entity = manager.createEntity("player_" + connection.getID());
	player_entity.addComponent(new SpatialComponent(sc.position.x, 0, sc.position.z, 1, 1));
	player_entity.addComponent(new BlockRender(Color.BROWN));
	manager.addEntity(player_entity);
	for(String id: connectedPlayers){
		NewEntityMessage message = new NewEntityMessage(id);
		connection.sendTCP(message);
	}
	
	server.sendToAllExceptTCP(connection.getID(),new NewEntityMessage("player_" + connection.getID()));

    }

    @Override
    public void disconnected(Connection connection) {
	System.out.println("Disconnect " + connection.toString());

    }
*/
}
