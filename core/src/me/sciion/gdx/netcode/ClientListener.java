package me.sciion.gdx.netcode;

import com.badlogic.gdx.graphics.Color;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import me.sciion.gdx.level.Level;
import me.sciion.gdx.level.entity.Entity;
import me.sciion.gdx.level.entity.EntityManager;
import me.sciion.gdx.level.entity.component.BlockRender;
import me.sciion.gdx.level.entity.component.ClientMessenger;
import me.sciion.gdx.level.entity.component.CollisionComponent;
import me.sciion.gdx.level.entity.component.ComponentType;
import me.sciion.gdx.level.entity.component.PlayerInput;
import me.sciion.gdx.level.entity.component.SpatialComponent;
import me.sciion.gdx.netcode.messages.client.MoveMessage;
import me.sciion.gdx.netcode.messages.server.NewEntityMessage;

public class ClientListener extends Listener {
/*
    private Level level;
    private GameClient client;

    public ClientListener(Level level, GameClient client) {
	this.level = level;
	this.client = client;
    }

    // Client side logic when server sends (server) message
    @Override
    public void received(Connection connection, Object object) {
	if (object instanceof NewEntityMessage) {
	    NewEntityMessage message = ((NewEntityMessage) object);
	    
	    System.out.println("New Entity from server:" + message.id);
	    
	    EntityManager manager = level.getEntities();

	    // == Get spawn location ==
	    Entity spawn_marker = manager.getEntityById("player_spawn");
	    SpatialComponent sc = spawn_marker.getComponent(ComponentType.Spatial);
	    if (sc == null) {
		System.err.println("No spawn marker on level");
		return;
	    }

	    // == Create local player entity ==
	    System.out.println("--===|Create local version of " + message.id + "|===---");
	    Entity player_entity = manager.createEntity(message.id);
	    player_entity.addComponent(new SpatialComponent(sc.position.x, sc.position.y, sc.position.z, sc.dimensions.x, sc.dimensions.y));
	    player_entity.addComponent(new BlockRender(Color.GREEN));
	    player_entity.addComponent(new CollisionComponent());

	    manager.addEntity(player_entity);
	}
	else if (object instanceof MoveMessage) {
	    MoveMessage move = ((MoveMessage) object);
	    Entity e = level.getEntities().getEntityById(move.getId());
	    if(e == null){
		System.out.println("Entity " + move.getId() + " does not exist on server!");
		return;
	    }
	    SpatialComponent sc = e.getComponent(ComponentType.Spatial);
	    sc.position.set(move.position);
	}
    }

    // Client side logic when server connects
    @Override
    public void connected(Connection connection) {
	System.out.println("Connection established to: " + connection.toString());
	EntityManager manager = level.getEntities();

	// == Get spawn location ==
	Entity spawn_marker = manager.getEntityById("player_spawn");
	SpatialComponent sc = spawn_marker.getComponent(ComponentType.Spatial);
	if (sc == null) {
	    System.err.println("No spawn marker on level");
	    return;
	}

	// == Create local player entity ==
	System.out.println("--===|Create player entity|===---");
	Entity player_entity = manager.createEntity("player_" + connection.getID());
	player_entity.addComponent(new SpatialComponent(sc.position.x, sc.position.y, sc.position.z, sc.dimensions.x, sc.dimensions.y));
	player_entity.addComponent(new BlockRender(Color.GREEN));
	player_entity.addComponent(new PlayerInput());
	player_entity.addComponent(new ClientMessenger(client));
	player_entity.addComponent(new CollisionComponent());

	manager.addEntity(player_entity);
    }

    @Override
    public void disconnected(Connection connection) {
	System.out.println("Disconnect " + connection.toString());

    }
    */
}
