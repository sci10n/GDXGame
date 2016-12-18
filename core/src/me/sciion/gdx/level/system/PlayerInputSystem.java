package me.sciion.gdx.level.system;

import java.awt.Dialog.ModalExclusionType;

import com.artemis.Archetype;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.EntityId;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import me.sciion.gdx.level.LevelGlobals;
import me.sciion.gdx.level.components.Cooldown;
import me.sciion.gdx.level.components.Physics;
import me.sciion.gdx.level.components.PlayerInput;
import me.sciion.gdx.level.components.Spatial;
import me.sciion.gdx.level.components.Velocity;
import me.sciion.gdx.netcode.Channels;
import me.sciion.gdx.utils.InputUtils;
import me.sciion.gdx.utils.ModelConstructer;
import me.sciion.gdx.utils.PhysicsUtils;
import me.sciion.gdx.utils.RayTraceEntityFinderReflect;
import me.sciion.gdx.utils.RaytraceEntityFinder;
import me.sciion.gdx.utils.KryoMessage.EntityInput;
import net.dermetfan.utils.Pair;

public class PlayerInputSystem extends IteratingSystem {
    
    @Wire
    LevelGlobals globals;
    
    private PhysicsSystem ps;
    private ComponentMapper<PlayerInput> pm;
    private ComponentMapper<Spatial> sm;
    private ComponentMapper<Velocity> vm;
    private Channels channels;

    public PlayerInputSystem() {
	super(Aspect.all(PlayerInput.class, Spatial.class, Velocity.class));
    }

    @Override
    protected void process(int eid) {

	boolean dirty = false;
	EntityInput message = new EntityInput();
	Vector3 position = sm.get(eid).position;
	Vector3 velocity = vm.get(eid).velocity;
	velocity.set(0, 0, 0);
	if (Gdx.input.isKeyPressed(Keys.W)) {
	    velocity.z = 5;
	    dirty = true;
	} else if (Gdx.input.isKeyPressed(Keys.S)) {
	    velocity.z = -5;
	    dirty = true;
	}
	if (Gdx.input.isKeyPressed(Keys.D)) {
	    velocity.x = -5;
	    dirty = true;
	} else if (Gdx.input.isKeyPressed(Keys.A)) {
	    velocity.x = 5;
	    dirty = true;
	}
	if (Gdx.input.isKeyPressed(Keys.SPACE)) {
	    com.badlogic.gdx.physics.box2d.World physics_world = ps.getPhysicsWorld();
	    RaytraceEntityFinder entityFinder = new RaytraceEntityFinder(eid);
	    Vector3 origin = position.cpy();
	    Vector3 target = origin.cpy().add(InputUtils.playerMouse().cpy().sub(origin).nor().scl(8.0f));
	    
	    
	    RayTraceEntityFinderReflect er = new RayTraceEntityFinderReflect(eid, 10, physics_world, globals);
	    er.rayTrace(origin, target);
	    // == This to raycast == //
	    if(PhysicsUtils.rayCast(physics_world, entityFinder, origin.x, origin.z, target.x, target.z)){
		if(entityFinder.getTarget() > 0){
		    
		}
		    //globals.rays.add(new Pair<Vector3, Vector3>(origin, entityFinder.getTargetPoint()));
	    }
	    else{
		return;
	    }

	    //System.out.println(entityFinder.getTarget());
	    
		
		
	    // == Spawn Entity == //
	    /*
	    int entityId = world.create();
	    world.getMapper(me.sciion.gdx.level.components.Model.class).create(entityId).instance = ModelConstructer.create(0.2f, 0.2f, 0.2f, Color.RED);
	    world.getMapper(Spatial.class).create(entityId).create(target,new Vector3(0.2f,0.2f,0.2f));
	    world.getMapper(Physics.class).create(entityId).create(PhysicsUtils.createBody(physics_world, origin.x, origin.z, 0.2f, 0.2f, BodyType.DynamicBody, true));
	    world.getMapper(Velocity.class).create(entityId).create(new Vector3(InputUtils.playerMouse().cpy().sub(origin).nor().scl(20.0f)));
	    world.getMapper(Cooldown.class).create(entityId).cooldown = 1.0f;
	    world.getMapper(Cooldown.class).create(entityId).remove = true;
	    */
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

	if (dirty) {
	    message.id = eid;
	    message.position = position;
	    message.tcp = false;
	    message.time = System.nanoTime() / 100000;
	    //channels.enqueOutbound(message);
	    //channels.enqueInbound(message);
	}
    }
    
}
