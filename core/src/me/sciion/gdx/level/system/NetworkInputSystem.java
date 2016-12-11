package me.sciion.gdx.level.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;

import me.sciion.gdx.level.components.CollisionComponent;
import me.sciion.gdx.level.components.NetworkedInput;
import me.sciion.gdx.level.components.SpatialComponent;
import me.sciion.gdx.utils.KryoMessage.EntityInput;
import me.sciion.gdx.utils.KryoMessage.EntityMessage;
import me.sciion.gdx.utils.KryoMessage.EntitySync;
import me.sciion.gdx.utils.KryoMessage.Input;

public class NetworkInputSystem extends IteratingSystem {

    private ComponentMapper<NetworkedInput> nm;
    private ComponentMapper<SpatialComponent> sm;

    public NetworkInputSystem() {
	super(Aspect.all(NetworkedInput.class, SpatialComponent.class));
    }

    @Override
    protected void process(int e) {
	NetworkedInput in = nm.get(e);
	SpatialComponent sc = sm.get(e);
	if(in.inbound.size != 0){
		EntityMessage message = in.inbound.removeFirst();
		if(message instanceof EntityInput){
		    EntityInput input = (EntityInput) message;
		    if(input.type == Input.ACTIVATE){
		    }else if(input.type == Input.MOVE_DOWN){
		    }else if(input.type == Input.MOVE_LEFT){
		    }else if(input.type == Input.MOVE_UP){
		    }else if(input.type == Input.MOVE_RIGHT){
		    }
		    sc.position.set(input.position);
		}
	}
    }

}
