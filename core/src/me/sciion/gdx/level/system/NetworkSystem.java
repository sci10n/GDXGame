package me.sciion.gdx.level.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;

import me.sciion.gdx.level.components.CollisionComponent;
import me.sciion.gdx.level.components.NetworkedInput;
import me.sciion.gdx.level.components.SpatialComponent;
import me.sciion.gdx.utils.KryoMessage.Input;
import me.sciion.gdx.utils.KryoMessage.InputMessage;

public class NetworkSystem extends IteratingSystem {

    private ComponentMapper<NetworkedInput> nm;
    private ComponentMapper<SpatialComponent> sm;
    private ComponentMapper<CollisionComponent> cm;

    public NetworkSystem() {
	super(Aspect.all(NetworkedInput.class, SpatialComponent.class, CollisionComponent.class));
    }

    @Override
    protected void process(int e) {
	NetworkedInput in = nm.get(e);
	SpatialComponent sc = sm.get(e);
	CollisionComponent cc = cm.get(e);
	InputMessage message = in.inbound.removeFirst();
	if (message.type == Input.MOVE) {
	    Body b = cc.body;
	    b.setLinearVelocity(message.velocity.x, message.velocity.z);
	}
    }

}
