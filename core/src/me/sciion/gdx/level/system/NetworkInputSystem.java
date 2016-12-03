package me.sciion.gdx.level.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;

import me.sciion.gdx.level.components.CollisionComponent;
import me.sciion.gdx.level.components.NetworkedInput;
import me.sciion.gdx.utils.KryoMessage.EntityInput;
import me.sciion.gdx.utils.KryoMessage.EntityMessage;
import me.sciion.gdx.utils.KryoMessage.EntitySync;
import me.sciion.gdx.utils.KryoMessage.Input;

public class NetworkInputSystem extends IteratingSystem {

    private ComponentMapper<NetworkedInput> nm;
    private ComponentMapper<CollisionComponent> cm;

    public NetworkInputSystem() {
	super(Aspect.all(NetworkedInput.class, CollisionComponent.class));
    }

    @Override
    protected void process(int e) {
	NetworkedInput in = nm.get(e);
	CollisionComponent cc = cm.get(e);
	cc.body.setLinearVelocity(cc.body.getLinearVelocity().cpy().scl(0.7f));
	if(in.inbound.size != 0){
		EntityMessage message = in.inbound.removeFirst();
		if(message instanceof EntityInput){
		    EntityInput input = (EntityInput) message;
		    if(input.type == Input.INTERACT){
			
		    }else if(input.type == Input.MOVE){
			cc.body.setLinearVelocity(input.velocity.x, input.velocity.z);
		    }
		} else if( message instanceof EntitySync){
		    EntitySync syn = (EntitySync) message;
		    cc.body.setTransform(syn.position.x, syn.position.y, 0);
		}
	}
    }

}
