package me.sciion.gdx.level.system;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;

import me.sciion.gdx.level.components.LightEmitter;
import me.sciion.gdx.level.components.Spatial;

public class LightSystem extends BaseEntitySystem{
     
    public LightSystem() {
	super(Aspect.all(LightEmitter.class, Spatial.class));
    }

    @Override
    protected void inserted(int entityId) {
        super.inserted(entityId);
    }
    
    @Override
    protected void processSystem() {
	
    }

}
