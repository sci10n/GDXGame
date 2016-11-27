package me.sciion.gdx.netcode.messages.server;

import com.badlogic.gdx.math.Vector3;

public class MoveMessage implements ServerMessage {

    public String id;
    public Vector3 position;
    
    public MoveMessage(){
	
    }
    
    public MoveMessage(String id, Vector3 position){
	this.id = id;
	this.position = position;
    }
    
    @Override
    public String getId() {
	return id;
    }

}
