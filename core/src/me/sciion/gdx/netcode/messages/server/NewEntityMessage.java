package me.sciion.gdx.netcode.messages.server;

public class NewEntityMessage implements ServerMessage{

    public String id;
    
    public NewEntityMessage(){
	
    }
    
    public NewEntityMessage(String id){
	this.id = id;
    }
    
    @Override
    public String getId() {
	return id;
    }

}
