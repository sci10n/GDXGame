package me.sciion.gdx.level.components;

import com.artemis.Component;
import com.badlogic.gdx.utils.Queue;

import me.sciion.gdx.utils.KryoMessage.EntityMessage;

public class Network extends Component{

    public Queue<EntityMessage> inbound;
    public Network(){
	inbound = new Queue<EntityMessage>();
    }
    
}
