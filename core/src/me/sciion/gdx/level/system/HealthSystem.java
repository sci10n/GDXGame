package me.sciion.gdx.level.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;

import me.sciion.gdx.level.components.Health;
import me.sciion.gdx.netcode.Channels;
import me.sciion.gdx.utils.KryoMessage.EntityDelete;

public class HealthSystem extends IteratingSystem{

    @Wire
    private Channels channels;

    private ComponentMapper<Health> hm;
    public HealthSystem() {
	super(Aspect.all(Health.class));
    }

    @Override
    protected void process(int e) {
	if(hm.get(e).getCurrent() == 0){
	    EntityDelete message = new EntityDelete();
	    message.id = e;
	    channels.enqueInbound(message);
	    channels.enqueOutbound(message);
	}
    }
}
