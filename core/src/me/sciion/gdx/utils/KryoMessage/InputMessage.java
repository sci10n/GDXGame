package me.sciion.gdx.utils.KryoMessage;

import com.badlogic.gdx.math.Vector3;

public class InputMessage {

    
    public Input type;
    public Vector3 velocity;
    public int entityId;
    public int originator;
    public InputMessage(Input type, Vector3 velocity, int id, int originator){
	this.type = type;
	this.velocity = velocity;
	this.entityId = id;
	this.originator = originator;
    }
}
