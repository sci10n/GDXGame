package me.sciion.gdx.level.system;

import java.util.HashMap;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;

import me.sciion.gdx.level.components.Physics;
import me.sciion.gdx.level.components.Spatial;
import me.sciion.gdx.level.components.Velocity;

public class PhysicsSystem extends IteratingSystem {

    ComponentMapper<Spatial> sm;
    ComponentMapper<Physics> cm;
    ComponentMapper<Velocity> vm;

    private World world;
    private HashMap<Integer,Integer> contacs;

    public PhysicsSystem() {
	super(Aspect.all(Spatial.class, Physics.class, Velocity.class));
	world = new World(Vector2.Zero, true);
	contacs = new HashMap<Integer,Integer>();
    }

    public HashMap<Integer,Integer> getCollisions() {
	return contacs;
    }

    public World getPhysicsWorld() {
	return world;
    }

    @Override
    protected void begin() {
	contacs.clear();
        super.begin();
    }
    
    @Override
    protected void process(int e) {
	Body b = cm.get(e).body;
	Vector3 position = sm.get(e).position;
	Vector3 velocity = vm.get(e).velocity;
	if (b == null) {
	    System.err.println("Physics system works with no body! Entity " + e);
	    return;
	}
	b.setLinearVelocity(velocity.x, velocity.z);
	float x = b.getPosition().x;
	float z = b.getPosition().y;
	position.set(x, 0, z);
    }

    protected void end() {
	world.step(Gdx.graphics.getDeltaTime(), 6, 2);
	for (Contact c : world.getContactList()) {
	    Integer idA = ((Integer) c.getFixtureA().getBody().getUserData());
	    Integer idB = ((Integer) c.getFixtureB().getBody().getUserData());
	    if (idA != null && idB != null) {
		contacs.put(idA, idB);
	    }
	}
    };
}
