package me.sciion.gdx.level.components;

import com.artemis.Component;
import com.badlogic.gdx.utils.Queue;

import me.sciion.gdx.utils.KryoMessage.InputMessage;

public class NetworkedInput extends Component{

    public Queue<InputMessage> inbound;
    public NetworkedInput(){
	inbound = new Queue<InputMessage>();
    }
    
}
