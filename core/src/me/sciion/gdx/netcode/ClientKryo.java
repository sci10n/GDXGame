package me.sciion.gdx.netcode;

import com.esotericsoftware.kryonet.Listener;

public class ClientKryo extends Listener{
/*
    private Client client;

    private Hashtable<Integer, Integer> internalExternal;
    private Hashtable<Integer, Integer> externalIneternal;

    private Channels channels;

    private String serverIP;
    private ClientLevel level;

    private int connectionID = 0;
    public int getInternal(int external) {
	return externalIneternal.get(external);
    }
    
    public int getExternal(int internal){
	if(internalExternal.containsKey(internal)){
	    return internalExternal.get(internal); 
	}
	return -1;
    }
    
    public boolean isRegistered(int external){
	return externalIneternal.containsKey(external);
    }
    
    public ClientKryo() {
	internalExternal = new Hashtable<Integer, Integer>();
	externalIneternal = new Hashtable<Integer, Integer>();
    }

    public void processOutbound() {
	channels.processOutbound();
    }

    public void processInbound() {
	channels.processInbound();
    }

    public void setup(String serverIP, ClientLevel level) {
	this.level = level;
	this.serverIP = serverIP;
	client = new Client();
	KryoUtils.register(client.getKryo());

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
	channels = new Channels() {
	    
	    @Override
	    public void processOutbound() {
		System.out.println("Outbound size: " + outbound.size);

		if(channels == null){
		    return;
		}
		NetworkMessage message = channels.dequeueOutbound();
		while (message != null) {
		    if(message.tcp)
			client.sendTCP(message);
		    else
			client.sendUDP(message);
		    message = channels.dequeueOutbound();
		}
	    }
	    
	    @Override
	    public void processInbound() {
		System.out.println("Inbound size: " + inbound.size);

		if(channels == null){
		    return;
		}
		NetworkMessage message = channels.dequeueInbound();
		while (message != null) {
		    if(message instanceof EntityMessage){
			level.enqueInbound((EntityMessage) message);
		    }
		    message = channels.dequeueInbound();
		}
	    }
	};
	connectionID = connection.getID();
	EntitySync message = new EntitySync();
	enqueOutbound(message);
    }

    @Override
    public void disconnected(Connection connection) {
	Log.info("Client disconnected from server: " + serverIP + " with id: " + connection.getID());

    }

    @Override
    public void received(Connection connection, Object object) {
	if (object instanceof NetworkMessage) {
	  //  Log.info("Client recieved message:\t" + object + "\tto server: " + serverIP + " with id: " + connection.getID());
	    if (object instanceof EntityMessage) {
		EntityMessage message = (EntityMessage) object;
		channels.enqueInbound(message);
	    } else if (object instanceof NetworkMessage) {
		NetworkMessage message = (NetworkMessage) object;
		channels.enqueInbound(message);
	    }
	}
    }
    
    public void registerEntity(int internal, int external) {
	internalExternal.put(internal, external);
	externalIneternal.put(external, internal);
    }

    public void unregisterEntity(int internal) {
	int external = internalExternal.get(internal);
	internalExternal.remove(internal);
	externalIneternal.remove(external);
    }
    public void enqueInbound(NetworkMessage message) {
	if(channels == null){
	    return;
	}
	channels.enqueInbound(message);
    }

    public void enqueOutbound(NetworkMessage message) {
	if(channels == null){
	    return;
	}
	channels.enqueOutbound(message);
    }

    public NetworkMessage dequeueInbound() {
	if(channels == null){
	    return null;
	}
	return channels.dequeueInbound();
    }

    public NetworkMessage dequeueOutbound() {
	if(channels == null){
	    return null;
	}
	return channels.dequeueOutbound();
    }
*/
}
