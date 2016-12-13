package me.sciion.gdx.level.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector3;

import me.sciion.gdx.level.components.PlayerInput;
import me.sciion.gdx.level.components.Spatial;
import me.sciion.gdx.netcode.Channels;
import me.sciion.gdx.utils.KryoMessage.EntityInput;

public class PlayerInputSystem extends IteratingSystem {
    
    private PhysicsSystem ps;
    private ComponentMapper<PlayerInput> pm;
    private ComponentMapper<Spatial> sm;
    private Channels channels;

    public PlayerInputSystem(Channels channels) {
	super(Aspect.all(PlayerInput.class, Spatial.class));
	this.channels = channels;
    }

    @Override
    protected void process(int eid) {

	boolean dirty = false;
	EntityInput message = new EntityInput();
	Vector3 pos = sm.get(eid).position;
	if (Gdx.input.isKeyPressed(Keys.W)) {
	    message.velocity.z = 5;
	    dirty = true;
	} else if (Gdx.input.isKeyPressed(Keys.S)) {
	    message.velocity.z = -5;
	    dirty = true;
	}
	if (Gdx.input.isKeyPressed(Keys.D)) {
	    message.velocity.x = -5;
	    dirty = true;
	} else if (Gdx.input.isKeyPressed(Keys.A)) {
	    message.velocity.x = 5;
	    dirty = true;
	}
	if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
	    System.out.println("interact!");
	    /*
	    int internal = world.create();
	    Spatial sc = world.getMapper(Spatial.class).create(internal);
	    Model mc = world.getMapper(Model.class).create(internal); 
	    Physics cc = world.getMapper(Physics.class).create(internal); 
	    Damage dc = world.getMapper(Damage.class).create(internal); 
	    Vector3 spawnPos = pos.cpy();
	    spawnPos=  spawnPos.add(InputUtils.playerMouse().cpy().sub(spawnPos).nor().scl(1.1f));
	    Vector3 dim = new Vector3(0.2f,0.1f,0.2f);
	    sc.create(spawnPos, dim);
	    mc.instance = ModelConstructer.create(dim.x, dim.y, dim.z, Color.RED);
	    Body b = PhysicsUtils.createBody(ps.getPhysicsWorld(), spawnPos.x, spawnPos.z, dim.x, dim.z, BodyType.DynamicBody, false);
	    b.setUserData(internal);
	    cc.create(b);
	    pc.direction = new Vector3(InputUtils.playerMouse().cpy().sub(spawnPos).nor().scl(-1));
	    pc.velocity = 25;
	    dc.damage = 1;
	    
	    EntityCreated created = new EntityCreated();
	    created.id = internal;
	    created.poistion = spawnPos;
	    created.dimensions = dim;
	    System.out.println(dim);
	    created.tcp = false;
	    created.type = EntityType.NETWORKED;
	    channels.enqueOutbound(created);
	    */
	}
	
	if (Gdx.input.isKeyJustPressed(Keys.N)) {
	}

	if (dirty) {
	    message.id = eid;
	    message.position = pos;
	    message.tcp = false;
	    message.time = System.nanoTime() / 100000;
	    channels.enqueOutbound(message);
	    channels.enqueInbound(message);
	}
    }
    
}
