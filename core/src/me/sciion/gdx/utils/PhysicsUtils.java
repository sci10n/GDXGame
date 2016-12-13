package me.sciion.gdx.utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class PhysicsUtils {

    public static Body createBody(World world, float x, float z, float w, float d, BodyType type, boolean sensor) {
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
	shape.dispose();
	body.setFixedRotation(true);
	System.out.println(body);
	return body;
    }
}
