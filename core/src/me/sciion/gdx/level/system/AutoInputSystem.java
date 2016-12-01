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

public class AutoInputSystem extends IteratingSystem {
    

    ComponentMapper<AutoInputComponent> im;
    ComponentMapper<SpatialComponent> sm;
    ComponentMapper<CollisionComponent> cm;
    public AutoInputSystem() {
	super(Aspect.all(AutoInputComponent.class, SpatialComponent.class, CollisionComponent.class));
    }

    @Override
    protected void process(int e) {
	AutoInputComponent ic = im.get(e);
	if(ic.targetEntity == -1){
	    if(ic.currentTime >= ic.targetTime){
		Body b = cm.get(e).body;
		b.setLinearVelocity(Vector2.Zero.setToRandomDirection().scl(ic.MAX_DELTA));
		ic.currentTime = 0;
	    }else{
		ic.currentTime += Gdx.graphics.getDeltaTime();
	    }
	}else{
	    if(sm.getSafe(ic.targetEntity) == null){
		System.err.println("Target entity dissapeared");
		ic.targetEntity = -1;
	    }else{
		SpatialComponent target = sm.get(ic.targetEntity);
		SpatialComponent source = sm.get(e);
		Vector3 distance = target.position.cpy().sub(source.position).nor().scl(ic.MAX_DELTA);
		Body b = cm.get(e).body;
		b.setLinearVelocity(distance.x, distance.z);
	    }
	}
    }

}
