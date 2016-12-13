package me.sciion.gdx.level.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;

import me.sciion.gdx.level.components.Damage;
import me.sciion.gdx.level.components.Health;
import me.sciion.gdx.level.components.Physics;

public class CollisionResolvingSystem extends IteratingSystem {

    PhysicsSystem ps;
    ComponentMapper<Physics> cm;
    ComponentMapper<Health> hm;
    ComponentMapper<Damage> dm;

    @SuppressWarnings("unchecked")
    public CollisionResolvingSystem() {
	super(Aspect.all(Physics.class).one(Health.class, Damage.class));

    }

    @Override
    protected void process(int id) {
	if (ps.getCollisions().containsKey(id)) {
	    int target = ps.getCollisions().get(id);
	    Damage dc1 = dm.getSafe(id);
	    Damage dc2 = dm.getSafe(target);
	    Health hc1 = hm.getSafe(id);
	    Health hc2 = hm.getSafe(target);
	    
	    if(dc1 != null && hc2 != null){
		hc2.remove(dc1.damage);
		System.out.println(target + " damaged for " + dc1.damage);
	    }else if(dc2 != null && hc1 != null){
		hc1.remove(dc2.damage);
		System.out.println(id + " damaged for " + dc2.damage);

	    }
	    
	    //if (hc != null){
	//	hc.remove(dc.damage);
//	    }
	}
    }
    
}
