package me.sciion.gdx.level.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;

import me.sciion.gdx.level.components.CollisionComponent;
import me.sciion.gdx.level.components.ModelComponent;
import me.sciion.gdx.level.components.SpatialComponent;
import me.sciion.gdx.level.components.VelocityComponent;

public class PhysicsSystem extends EntityProcessingSystem {

    ComponentMapper<SpatialComponent> sm;
    ComponentMapper<ModelComponent> mm;
    ComponentMapper<VelocityComponent> vm;
    
    public PhysicsSystem() {
	super(Aspect.all(SpatialComponent.class, CollisionComponent.class));
    }

    @Override
    protected void process(Entity e) {
	System.out.println("Physics System: " + e.getId());
	if(e.getComponent(VelocityComponent.class) != null){
	    sm.get(e).position.add(vm.get(e).velocity.cpy().scl(Gdx.graphics.getDeltaTime()));
	}
    }


}
