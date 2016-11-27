package me.sciion.gdx.netcode.messages.client;

public class FireMessage implements ClientMessage{

    
    public String id;
    
    public FireMessage() {
	id = "Undefined";
    }
    
    public FireMessage(String id){
	this.id = id;
    }
    
    @Override
    public String getId() {
	return id;
    }


}
