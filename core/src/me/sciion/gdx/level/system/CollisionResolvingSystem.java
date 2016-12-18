package me.sciion.gdx.level.system;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import me.sciion.gdx.level.components.Collision;

public class CollisionResolvingSystem extends BaseEntitySystem implements ContactListener {

    PhysicsSystem ps;
    ComponentMapper<Collision> cm;
    
    public CollisionResolvingSystem() {
	super(Aspect.all(Collision.class));
    }
    
    @Override
    public void beginContact(Contact contact) {
	Integer ida = (Integer) contact.getFixtureA().getBody().getUserData();
	Integer idb = (Integer) contact.getFixtureB().getBody().getUserData();
	if (ida != null && idb != null) {
	    cm.get(ida).currentCollisions.add(idb);
	    cm.get(idb).currentCollisions.add(ida);
	}

    }

    @Override
    public void endContact(Contact contact) {
	Integer ida = (Integer) contact.getFixtureA().getBody().getUserData();
	Integer idb = (Integer) contact.getFixtureB().getBody().getUserData();
	if (ida != null && idb != null) {
	    cm.get(ida).currentCollisions.removeValue(idb,false);
	    cm.get(idb).currentCollisions.removeValue(ida,false);
	}
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
	
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
	
    }

    @Override
    protected void processSystem() {
	ps.getPhysicsWorld().setContactListener(this);
    }

}
