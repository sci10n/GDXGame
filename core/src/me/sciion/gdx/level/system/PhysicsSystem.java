package me.sciion.gdx.level.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;

import me.sciion.gdx.level.components.CollisionComponent;
import me.sciion.gdx.level.components.ModelComponent;
import me.sciion.gdx.level.components.SpatialComponent;
import me.sciion.gdx.level.components.VelocityComponent;

public class PhysicsSystem extends IteratingSystem{

    ComponentMapper<SpatialComponent> sm;
    ComponentMapper<VelocityComponent> vm;
    ComponentMapper<CollisionComponent> cm;
    
    public PhysicsSystem() {
	super(Aspect.all(SpatialComponent.class, CollisionComponent.class));
    }


    @Override
    protected void process(int eid) {
	float x = cm.get(eid).body.getPosition().x;
	float z = cm.get(eid).body.getPosition().y;
	sm.get(eid).position.set(x,0,z);
    }


}
