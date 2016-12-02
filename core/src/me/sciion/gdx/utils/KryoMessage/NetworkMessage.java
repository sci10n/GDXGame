package me.sciion.gdx.utils.KryoMessage;

public class NetworkMessage {

    
    public int originator;
    public NetworkMessage(){
	originator = -1;
    }
    
    public NetworkMessage(int id){
	originator = id;
    }
}
