package me.sciion.gdx.level.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;

import me.sciion.gdx.level.components.CollisionComponent;
import me.sciion.gdx.level.components.SpatialComponent;

public class PhysicsSystem extends IteratingSystem{

    ComponentMapper<SpatialComponent> sm;
    ComponentMapper<CollisionComponent> cm;
    
    public PhysicsSystem() {
	super(Aspect.all(SpatialComponent.class, CollisionComponent.class));
    }


    @Override
    protected void process(int e) {
	Body b = cm.get(e).body;
	if(b == null){
	    System.err.println("Physics system works with no body! Entity " + e);
	    return;
	}
	float x = b.getPosition().x;
	float z = b.getPosition().y;
	sm.get(e).position.set(x,0,z);
    }


}
