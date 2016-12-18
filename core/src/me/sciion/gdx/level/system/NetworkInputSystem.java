package me.sciion.gdx.level.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;

import me.sciion.gdx.level.components.Network;
import me.sciion.gdx.level.components.Physics;
import me.sciion.gdx.level.components.Spatial;
import me.sciion.gdx.netcode.Channels;
import me.sciion.gdx.utils.KryoMessage.EntityInput;
import me.sciion.gdx.utils.KryoMessage.EntityMessage;
import me.sciion.gdx.utils.KryoMessage.Input;

public class NetworkInputSystem extends IteratingSystem {

    @Wire
    private Channels channels;
    
    private ComponentMapper<Network> nm;
    private ComponentMapper<Spatial> sm;
    private ComponentMapper<Physics> cm;
    
    public NetworkInputSystem() {
	super(Aspect.all(Network.class, Spatial.class));
    }

    @Override
    protected void process(int e) {
	Network in = nm.get(e);
	Spatial sc = sm.get(e);
	Physics cc = cm.getSafe(e);
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
		    if(cc != null){
			cc.body.setLinearVelocity(input.velocity.x, input.velocity.z);
		    }
		    else{
			sc.position.set(input.position.add(input.velocity.scl(Gdx.graphics.getDeltaTime())));

		    }

		}
	}
    }

}
