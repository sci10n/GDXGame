package me.sciion.gdx.level.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.DelayedIteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import me.sciion.gdx.level.components.AutoInput;
import me.sciion.gdx.level.components.Physics;
import me.sciion.gdx.level.components.Spatial;
import me.sciion.gdx.level.components.Velocity;
import me.sciion.gdx.utils.Behaviors;

public class AutoInputSystem extends DelayedIteratingSystem {

    ComponentMapper<AutoInput> aim;
    ComponentMapper<Velocity> vm;
    ComponentMapper<Physics> pm;

    public AutoInputSystem() {
	super(Aspect.all(AutoInput.class, Velocity.class, Physics.class));
    }

    @Override
    protected float getRemainingDelay(int id) {
	return aim.get(id).ellapsedTime;
    }

    @Override
    protected void processDelta(int id, float accumulatedDelta) {
	System.out.println(id + " " + accumulatedDelta);
	aim.get(id).ellapsedTime -= accumulatedDelta;
	if(aim.get(id).behavior == Behaviors.WANDERING){
	    vm.get(id).velocity = aim.get(id).direction;
	}
    }

    @Override
    protected void processExpired(int id) {
	aim.get(id).ellapsedTime = MathUtils.random(1, 4);
	if(aim.get(id).behavior == Behaviors.WANDERING){
	    aim.get(id).direction.setToRandomDirection();
	    aim.get(id).direction.y = 0;
	    Vector2 pos = pm.get(id).body.getPosition();
	    pm.get(id).body.setTransform(pos, MathUtils.atan2(aim.get(id).direction.z, aim.get(id).direction.x));
	}
	offerDelay(aim.get(id).ellapsedTime);
    }
}
