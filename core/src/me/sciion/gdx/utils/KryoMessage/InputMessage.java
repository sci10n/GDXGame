package me.sciion.gdx.utils.KryoMessage;

import com.badlogic.gdx.math.Vector3;

public class InputMessage extends NetworkMessage{

    
    public Input type;
    public Vector3 velocity;
    public int entityId;
    public int originator;
    
    public InputMessage(){
	
    }
    public InputMessage(Input type, Vector3 velocity, int id){
	this.type = type;
	this.velocity = velocity;
	this.entityId = id;
    }
}
