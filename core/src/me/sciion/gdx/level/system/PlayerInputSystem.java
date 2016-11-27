package me.sciion.gdx.level.system;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import me.sciion.gdx.level.components.PlayerInputComponent;
import me.sciion.gdx.level.components.SpatialComponent;
import me.sciion.gdx.level.components.VelocityComponent;

public class PlayerInputSystem extends BaseEntitySystem {

    ComponentMapper<VelocityComponent> vm;
    
    public PlayerInputSystem() {
	super(Aspect.all(PlayerInputComponent.class, SpatialComponent.class, VelocityComponent.class));
    }

    @Override
    protected void processSystem() {
	IntBag actives = subscription.getEntities();
	int[] ids = actives.getData();
	for(int i = 0, s = actives.size(); s > i; i++){
	    vm.get(ids[i]).velocity.set(0, 0, 0);
	    if(Gdx.input.isKeyPressed(Keys.W)){
		vm.get(ids[i]).velocity.add(0, 0, -5);
	    }
	    if(Gdx.input.isKeyPressed(Keys.A)){
		vm.get(ids[i]).velocity.add(-5, 0, 0);
	    }
	    if(Gdx.input.isKeyPressed(Keys.S)){
		vm.get(ids[i]).velocity.add(0, 0, 5);
	    }
	    if(Gdx.input.isKeyPressed(Keys.D)){
		vm.get(ids[i]).velocity.add(5, 0, 0);
	    }
	    vm.get(ids[i]).velocity.nor().scl(5);
	}
    }
    

}
