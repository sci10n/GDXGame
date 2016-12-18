package me.sciion.gdx.level.system;

import com.artemis.Aspect;
import com.artemis.Aspect.Builder;
import com.artemis.systems.IteratingSystem;

import me.sciion.gdx.level.components.Script;

public class ScriptingSystem extends IteratingSystem{

    public ScriptingSystem() {
	super(Aspect.all(Script.class));
    }

    @Override
    protected void process(int entityId) {
	
    }

}
