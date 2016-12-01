package me.sciion.gdx.level.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.box2d.Body;

import me.sciion.gdx.level.components.CollisionComponent;
import me.sciion.gdx.level.components.PlayerInputComponent;
import me.sciion.gdx.level.components.SpatialComponent;

public class PlayerInputSystem extends IteratingSystem {

    private ComponentMapper<PlayerInputComponent> pm;
    private ComponentMapper<SpatialComponent> sm;
    private ComponentMapper<CollisionComponent> cm;

    public PlayerInputSystem() {
	super(Aspect.all(PlayerInputComponent.class, SpatialComponent.class, CollisionComponent.class));
    }

    @Override
    protected void process(int eid) {
	
	Body body = cm.get(eid).body;
	body.setLinearVelocity(body.getLinearVelocity().cpy().scl(0.7f));
	if(Gdx.input.isKeyPressed(Keys.W)){
	    body.setLinearVelocity(body.getLinearVelocity().x, 5);
	}
	else if(Gdx.input.isKeyPressed(Keys.S)){
            body.setLinearVelocity(body.getLinearVelocity().x, -5);
        }
        if(Gdx.input.isKeyPressed(Keys.D)){
            body.setLinearVelocity(-5, body.getLinearVelocity().y);
        }
        else if(Gdx.input.isKeyPressed(Keys.A)){
            body.setLinearVelocity(5, body.getLinearVelocity().y);    
        }
        if(Gdx.input.isKeyJustPressed(Keys.SPACE)){
            System.out.println("interact!");
        }
        
    }




}
