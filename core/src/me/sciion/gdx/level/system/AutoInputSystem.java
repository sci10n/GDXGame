package me.sciion.gdx.level.system;

import com.artemis.Aspect;
import com.artemis.systems.DelayedIteratingSystem;

import me.sciion.gdx.level.components.AutoInput;

public class AutoInputSystem extends DelayedIteratingSystem {

    public AutoInputSystem() {
	super(Aspect.all(AutoInput.class));
    }

    @Override
    protected float getRemainingDelay(int id) {
	return 5;
    }

    @Override
    protected void processDelta(int id, float accumulatedDelta) {
	System.out.println(id + " " + accumulatedDelta);
    }

    @Override
    protected void processExpired(int id) {
	System.out.println(id);
    }

}
