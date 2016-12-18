package me.sciion.gdx.level.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.DelayedIteratingSystem;

import me.sciion.gdx.level.components.Cooldown;

public class CooldownSystem extends DelayedIteratingSystem{

    private ComponentMapper<Cooldown> cm;
    public CooldownSystem() {
	super(Aspect.all(Cooldown.class));
    }

    @Override
    protected float getRemainingDelay(int entityId) {
	return cm.get(entityId).cooldown;
    }

    @Override
    protected void processDelta(int entityId, float accumulatedDelta) {
	cm.get(entityId).cooldown -= accumulatedDelta;
    }

    @Override
    protected void processExpired(int entityId) {
	//System.out.println(entityId);
	float cooldown = cm.get(entityId).trigger.execute(entityId, world);
	cm.get(entityId).cooldown = cooldown;
	offerDelay(cooldown);
    }

}
