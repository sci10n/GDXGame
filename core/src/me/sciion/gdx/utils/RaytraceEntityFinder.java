package me.sciion.gdx.utils;

import com.artemis.annotations.EntityId;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;

public class RaytraceEntityFinder implements RayCastCallback {

    @EntityId
    private int target = -1;


    private Vector3 targetPoint;
    private Vector3 targetNormal;
    
    public RaytraceEntityFinder() {
	targetPoint = new Vector3();
	targetNormal = new Vector3();
    }
    public RaytraceEntityFinder(Vector3 defaultTarget) {
	targetPoint = defaultTarget;
	targetNormal = new Vector3();
    }

    public int getTarget() {
	return target;
    }

    @Override
    public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
	Integer i = (Integer) (fixture.getUserData());
	Integer ii = (Integer) fixture.getBody().getUserData();
	
	// Neither fixture nor body is entity
	if (i == null && ii == null) {
	    target = 0;
	    targetPoint.set(point.x, 0, point.y);
	    targetNormal.set(normal.x, 0, normal.y);
	    return fraction;
	}
	// Fixture is not entity but body is entity
	else if (i == null && ii != null) {
	    return 1;
	    // Fixture is entity and body is entity
	} else if (i != null && ii != null) {
	    target = ii;
	    //System.out.println("target: " + target);
	    targetPoint.set(point.x, 0, point.y);
	    targetNormal.set(normal.x, 0, normal.y);

	    return fraction;
	    // ????
	} else {
	    target = 0;
	    return 0;
	}
    }

    public Vector3 getTargetPoint() {
	return targetPoint;
    }
    
    public Vector3 getTargetNormal(){
	return targetNormal;
    }

}
