package me.sciion.gdx.utils;

import com.artemis.annotations.EntityId;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import me.sciion.gdx.level.LevelGlobals;
import net.dermetfan.utils.Pair;

//TODO Unfinished
public class RayTraceEntityFinderReflect {

    @Wire
    LevelGlobals globals;


    private Array<Vector3> targetPoints;
    private float range;
    private World world;

    public RayTraceEntityFinderReflect(float range, World world, LevelGlobals globals) {
	targetPoints = new Array<Vector3>();
	this.range = range;
	this.world = world;
	this.globals = globals;
    }

    public void rayTrace(Vector3 source, Vector3 target) {
	float accumulate = 0.0f;
	Vector3 s = source;
	Vector3 t = target;
	for(int i = 0; i < 10; i++){
	    globals.rays.add(new Pair<Vector3, Vector3>(s.cpy(),t.cpy()));
	    RaytraceEntityFinder finder = new RaytraceEntityFinder();
	    PhysicsUtils.rayCast(world, finder, s.x, s.z, t.x,t.z);
	    Vector3 v = finder.getTargetPoint().sub(s);
	    Vector3 n = finder.getTargetNormal();
	    Vector3 ns = PhysicsUtils.reflect(v, n);
	    s = finder.getTargetPoint().cpy();
	    t = s.cpy().add(ns);
	} 
    }
}
