package me.sciion.gdx.netcode;


import com.badlogic.gdx.math.Vector3;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import me.sciion.gdx.netcode.messages.client.ClientMessage;
import me.sciion.gdx.netcode.messages.client.FireMessage;

public class Network {

    public static final int TCP_SOCK = 54555;
    public static final int UDP_SOCK = 54777;
    public static final int TIMEOUT = 5000;
    public static void register(EndPoint ep){
	Kryo kryo = ep.getKryo();
	kryo.register(ClientMessage.class);
	kryo.register(FireMessage.class);
	kryo.register(Vector3.class);
	kryo.register(me.sciion.gdx.netcode.messages.client.MoveMessage.class);
	kryo.register(me.sciion.gdx.netcode.messages.client.MoveMessage.class);
	kryo.register(me.sciion.gdx.netcode.messages.server.NewEntityMessage.class);
	kryo.register(me.sciion.gdx.netcode.messages.server.MoveMessage.class);

    }
}
