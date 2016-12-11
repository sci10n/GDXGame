package me.sciion.gdx.level.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;

import me.sciion.gdx.level.components.CollisionComponent;
import me.sciion.gdx.level.components.PlayerInputComponent;
import me.sciion.gdx.level.components.SpatialComponent;
import me.sciion.gdx.netcode.Channels;
import me.sciion.gdx.utils.EntityType;
import me.sciion.gdx.utils.InputUtils;
import me.sciion.gdx.utils.KryoMessage.EntityCreated;
import me.sciion.gdx.utils.KryoMessage.EntityInput;

public class PlayerInputSystem extends IteratingSystem {

    private ComponentMapper<PlayerInputComponent> pm;
    private ComponentMapper<SpatialComponent> sm;
    private ComponentMapper<CollisionComponent> cm;

    private Channels channels;

    public PlayerInputSystem(Channels channels) {
	super(Aspect.all(PlayerInputComponent.class, SpatialComponent.class, CollisionComponent.class));
	this.channels = channels;
    }

    @Override
    protected void process(int eid) {

	boolean dirty = false;
	Body body = cm.get(eid).body;
	body.setLinearVelocity(body.getLinearVelocity().cpy().scl(0.7f));
	if (Gdx.input.isKeyPressed(Keys.W)) {
	    body.setLinearVelocity(body.getLinearVelocity().x, 5);
	    dirty = true;
	} else if (Gdx.input.isKeyPressed(Keys.S)) {
	    body.setLinearVelocity(body.getLinearVelocity().x, -5);
	    dirty = true;

	}
	if (Gdx.input.isKeyPressed(Keys.D)) {
	    body.setLinearVelocity(-5, body.getLinearVelocity().y);
	    dirty = true;

	} else if (Gdx.input.isKeyPressed(Keys.A)) {
	    body.setLinearVelocity(5, body.getLinearVelocity().y);
	    dirty = true;
	}
	if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
	    System.out.println("interact!");
	    Vector3 v = InputUtils.playerMouse();
	    v.sub(body.getPosition().x,0, body.getPosition().y);
	    EntityCreated message = new EntityCreated();
	    message.type = EntityType.NPC;
	    message.poistion = v.scl(2);
	    message.id = -1;
	    message.owner = -1;
	    
	    channels.enqueInbound(message);
	}
	
	if (Gdx.input.isKeyJustPressed(Keys.N)) {
	    System.out.println("Create new entity!");
	}

	if (dirty) {
	    EntityInput message = new EntityInput();
	    message.id = eid;
	    message.position = new Vector3(body.getPosition().x,0,body.getPosition().y);
	    message.tcp = false;
	    message.time = System.nanoTime() / 100000;
	    channels.enqueOutbound(message);
	}
    }

}
