package me.sciion.gdx.level.components;

import com.artemis.Component;
import com.badlogic.gdx.utils.Queue;

import me.sciion.gdx.utils.KryoMessage.EntityMessage;

public class NetworkedInput extends Component{

    public Queue<EntityMessage> inbound;
    public NetworkedInput(){
	inbound = new Queue<EntityMessage>();
    }
    
}
