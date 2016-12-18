package me.sciion.gdx.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;

public class PhysicsUtils {

    public static Body createBody(World world, float x, float z, float w, float d, BodyType type, boolean sensor, Object userData) {
	BodyDef def = new BodyDef();
	def.type = type;
	def.position.set(x, z);
	Body body = world.createBody(def);
	PolygonShape shape = new PolygonShape();
	shape.setAsBox(w / 2.0f, d / 2.0f);
	FixtureDef fixDef = new FixtureDef();
	fixDef.shape = shape;
	fixDef.density = 1f;
	fixDef.isSensor = sensor;
	// fixDef.friction = 20.0f;
	Fixture f = body.createFixture(fixDef);
	f.setUserData(userData);
	body.setUserData(userData);
	shape.dispose();
	body.setFixedRotation(true);
	if(type == BodyType.DynamicBody && !sensor)
	    body.setSleepingAllowed(false);
	return body;
    }

    private static int increments = 7;

    public static Fixture createCircularSensor(Body parent,float radius, float angle) {
	PolygonShape shape = new PolygonShape();
	float[] indices = new float[(1+increments ) * 2];
	float angleIncrement = angle / increments;
	
	float angleAccumualted = -angle / 2.0f;
	for (int i = 0; i < increments*2; i+=2) {
	    angleAccumualted += angleIncrement;
	    indices[i] = 0 + MathUtils.cos(angleAccumualted ) * radius;
	    indices[i+1] = 0 + MathUtils.sin(angleAccumualted) * radius;
	}
	indices[indices.length-2] = 0;
	indices[indices.length-1] = 0;
	shape.set(indices);
	//shape.setRadius(radius);
	FixtureDef def = new FixtureDef();
	def.shape = shape;
	def.isSensor = true;
	def.density = 1.0f;
	Fixture f = parent.createFixture(def);
	f.setUserData(null);
	shape.dispose();
	return f;
    }

    public static boolean rayCast(World world, RayCastCallback callback, float sx, float sz, float tx, float tz) {
	//System.out.println(sx + " " + sz + "\t" + tx + " " + tz);
	if(Float.isNaN(sx) || Float.isNaN(sz)|| Float.isNaN(tx)|| Float.isNaN(tz)){
	    System.out.println("Trigger on Nan");
	    return false;
	}
	if(sx == tx && sz == tz){
	    //System.out.println("Trigger on length equal to 0");
	    return false;
	}
	 world.rayCast(callback, sx, sz, tx, tz);
	 return true;
    }
    
    public static Vector3 reflect(Vector3 v, Vector3 n){
        	Vector3 p = n.cpy().scl(v.cpy().dot(n));
        	p.scl(-2);
        	return p.cpy().add(v);
	
    }
}
