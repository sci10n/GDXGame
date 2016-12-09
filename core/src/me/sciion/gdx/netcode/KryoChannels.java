package me.sciion.gdx.netcode;

import java.util.concurrent.locks.ReentrantLock;

import com.badlogic.gdx.utils.Queue;

import me.sciion.gdx.utils.KryoMessage.NetworkMessage;

public class KryoChannels {

    private Queue<NetworkMessage> outbound;
    private Queue<NetworkMessage> inbound;

    private ReentrantLock inboundLock;
    private ReentrantLock outboundLock;

    private int originator;

    public KryoChannels(int originator) {
	this.originator = originator;
	outbound = new Queue<NetworkMessage>();
	inbound = new Queue<NetworkMessage>();
    }

    public void enqueInbound(NetworkMessage message) {
	inboundLock.lock();
	try {
	    inbound.addLast(message);
	} finally {
	    inboundLock.unlock();
	}
    }

    public void enqueOutbound(NetworkMessage message) {
	message.originator = originator;
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
