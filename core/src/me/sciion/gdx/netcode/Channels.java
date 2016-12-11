package me.sciion.gdx.netcode;

import java.util.concurrent.locks.ReentrantLock;

import com.badlogic.gdx.utils.Queue;

import me.sciion.gdx.utils.KryoMessage.NetworkMessage;

public abstract class Channels {

    protected Queue<NetworkMessage> outbound;
    protected Queue<NetworkMessage> inbound;

    protected ReentrantLock inboundLock;
    protected ReentrantLock outboundLock;


    public Channels() {
	outbound = new Queue<NetworkMessage>();
	inbound = new Queue<NetworkMessage>();
	outboundLock = new ReentrantLock();
	inboundLock = new ReentrantLock();
    }

    public abstract void processOutbound();
    public abstract void processInbound();

    public void enqueInbound(NetworkMessage message) {
	inboundLock.lock();
	try {
	    inbound.addLast(message);
	} finally {
	    inboundLock.unlock();
	}
    }

    public void enqueOutbound(NetworkMessage message) {
	outboundLock.lock();
	try {
	    outbound.addLast(message);
	} finally {
	    outboundLock.unlock();
	}
    }

    public NetworkMessage dequeueInbound() {
	NetworkMessage message = null;
	inboundLock.lock();
	try {
	    if (inbound.size != 0) {
		message = inbound.removeFirst();
	    }
	} finally {
	    inboundLock.unlock();
	}
	return message;
    }

    public NetworkMessage dequeueOutbound() {
	NetworkMessage message = null;
	outboundLock.lock();
	try {
	    if (outbound.size != 0) {
		message = outbound.removeFirst();
	    }
	} finally {
	    outboundLock.unlock();
	}
	return message;
    }

}
