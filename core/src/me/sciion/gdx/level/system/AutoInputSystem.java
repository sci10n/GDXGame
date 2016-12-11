package me.sciion.gdx.level.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;

import me.sciion.gdx.level.components.AutoInputComponent;
import me.sciion.gdx.level.components.CollisionComponent;
import me.sciion.gdx.level.components.SpatialComponent;
import me.sciion.gdx.netcode.Channels;
import me.sciion.gdx.utils.KryoMessage.EntityInput;

public class AutoInputSystem extends IteratingSystem {

    ComponentMapper<AutoInputComponent> im;
    ComponentMapper<SpatialComponent> sm;
    ComponentMapper<CollisionComponent> cm;
    private Channels channels;

    public AutoInputSystem(Channels channels) {
	super(Aspect.all(AutoInputComponent.class, SpatialComponent.class, CollisionComponent.class));
	this.channels = channels;
    }

    @Override
    protected void process(int e) {
	AutoInputComponent ic = im.get(e);
	if (ic.idling) {
	    if (ic.currentTime >= ic.idleTime) {
		ic.idling = false;
		ic.currentTime = 0;
		Body b = cm.get(e).body;
		b.setLinearVelocity(Vector2.Zero.cpy().setToRandomDirection().scl(ic.MAX_DELTA));
		EntityInput message = new EntityInput();
		message.id = e;
		message.position = new Vector3(cm.get(e).body.getPosition().x, 0, cm.get(e).body.getPosition().y);
		message.tcp = false;
		message.time = System.nanoTime() / 100000;
		channels.enqueOutbound(message);

	    } else {
		ic.currentTime += Gdx.graphics.getDeltaTime();
		Body b = cm.get(e).body;
		b.setLinearVelocity(Vector2.Zero.cpy());

	    }
	} else {
	    if (ic.targetEntity == -1) {
		if (ic.currentTime >= ic.targetTime) {
		    ic.currentTime = 0;
		    ic.idling = true;
		    Body b = cm.get(e).body;
		    b.setLinearVelocity(Vector2.Zero.cpy());

		} else {
		    ic.currentTime += Gdx.graphics.getDeltaTime();
		    EntityInput message = new EntityInput();
		    message.id = e;
		    message.position = new Vector3(cm.get(e).body.getPosition().x, 0, cm.get(e).body.getPosition().y);
		    message.tcp = false;
		    message.time = System.nanoTime() / 100000;
		    channels.enqueOutbound(message);
		}

	    } else {
		if (sm.getSafe(ic.targetEntity) == null) {
		    System.err.println("Target entity dissapeared");
		    ic.targetEntity = -1;
		} else {
		    SpatialComponent target = sm.get(ic.targetEntity);
		    SpatialComponent source = sm.get(e);
		    Vector3 distance = target.position.cpy().sub(source.position).nor().scl(ic.MAX_DELTA);
		    Body b = cm.get(e).body;
		    b.setLinearVelocity(distance.x, distance.z);
		}
	    }
	}
    }

}
