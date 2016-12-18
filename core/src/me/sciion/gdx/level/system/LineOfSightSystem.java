package me.sciion.gdx.level.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector3;

import me.sciion.gdx.level.LevelGlobals;
import me.sciion.gdx.level.components.Collision;
import me.sciion.gdx.level.components.Detector;
import me.sciion.gdx.level.components.Spatial;
import me.sciion.gdx.utils.PhysicsUtils;
import me.sciion.gdx.utils.RaytraceEntityFinder;
import net.dermetfan.utils.Pair;


public class LineOfSightSystem extends IteratingSystem{

    ComponentMapper<Collision> cm;
    ComponentMapper<Detector> dm;
    ComponentMapper<Spatial> sm;
    
    private PhysicsSystem ps;
    @Wire
    private LevelGlobals globals;
    
    public LineOfSightSystem() {
	super(Aspect.all(Spatial.class, Detector.class, Collision.class));
    }

    @Override
    protected void begin() {
	globals.rays.clear();
        super.begin();
    }
    @Override
    protected void process(int entityId) {
	if(!dm.get(entityId).active)
	    return;
	Collision c = cm.get(entityId);
	// System.out.println(c.currentCollisions);
	for (int i : c.currentCollisions) {
	    // System.out.println("HELLO WORLD");
	    com.badlogic.gdx.physics.box2d.World physics_world = ps.getPhysicsWorld();
	    RaytraceEntityFinder entityFinder = new RaytraceEntityFinder(entityId);
	    Vector3 origin = sm.get(entityId).position.cpy();
	    Vector3 target = sm.getSafe(i).position.cpy();
	    //if (origin.cpy().sub(target).len() > dm.get(entityId).range) {
		//continue;
	    //}
	    // == This to raycast == //

	    if (PhysicsUtils.rayCast(physics_world, entityFinder, origin.x, origin.z, target.x, target.z)) {
		if (entityFinder.getTarget() > 0) {
		     globals.rays.add(new Pair<Vector3, Vector3>(origin,entityFinder.getTargetPoint()));
		    //System.out.println("I've got the beast i nmy sight: " + entityFinder.getTarget() + " ");
		} // System.out.println("Target " + i + " in sight from " +
		  // entityId);
		// }
	    }
	    // System.out.println(entityFinder.getTarget());

	}
    }

}
