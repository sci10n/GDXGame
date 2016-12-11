package me.sciion.gdx.utils.KryoMessage;

public class NetworkMessage {

    
    public int owner;
    public boolean tcp = true;
    public NetworkMessage(){
	owner = -1;
    }
    
    public NetworkMessage(int id){
	owner = id;
    }
}
