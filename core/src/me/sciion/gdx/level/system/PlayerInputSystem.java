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

import me.sciion.gdx.level.Level;
import me.sciion.gdx.level.components.CollisionComponent;
import me.sciion.gdx.level.components.PlayerInputComponent;
import me.sciion.gdx.level.components.SpatialComponent;
import me.sciion.gdx.utils.KryoStasis;
import me.sciion.gdx.utils.KryoMessage.Input;
import me.sciion.gdx.utils.KryoMessage.InputMessage;

public class PlayerInputSystem extends IteratingSystem {

    private ComponentMapper<PlayerInputComponent> pm;
    private ComponentMapper<SpatialComponent> sm;
    private ComponentMapper<CollisionComponent> cm;

    private KryoStasis connection;
    private Level level;
    public PlayerInputSystem(KryoStasis connection, Level level) {
	super(Aspect.all(PlayerInputComponent.class, SpatialComponent.class, CollisionComponent.class));
	this.connection = connection;
	this.level = level;
    }

    @Override
    protected void process(int eid) {
	
	Body body = cm.get(eid).body;
	body.setLinearVelocity(body.getLinearVelocity().cpy().scl(0.7f));
	if(Gdx.input.isKeyPressed(Keys.W)){
	    body.setLinearVelocity(body.getLinearVelocity().x, 5);
		connection.addOutbound(new InputMessage(Input.MOVE, new Vector3(body.getLinearVelocity().x,0,body.getLinearVelocity().y), level.internalToExternal.get(eid)),level);

	}
	else if(Gdx.input.isKeyPressed(Keys.S)){
            body.setLinearVelocity(body.getLinearVelocity().x, -5);
    	connection.addOutbound(new InputMessage(Input.MOVE, new Vector3(body.getLinearVelocity().x,0,body.getLinearVelocity().y), level.internalToExternal.get(eid)),level);

        }
        if(Gdx.input.isKeyPressed(Keys.D)){
            body.setLinearVelocity(-5, body.getLinearVelocity().y);
    	connection.addOutbound(new InputMessage(Input.MOVE, new Vector3(body.getLinearVelocity().x,0,body.getLinearVelocity().y), level.internalToExternal.get(eid)),level);

        }
        else if(Gdx.input.isKeyPressed(Keys.A)){
            body.setLinearVelocity(5, body.getLinearVelocity().y);    
    	connection.addOutbound(new InputMessage(Input.MOVE, new Vector3(body.getLinearVelocity().x,0,body.getLinearVelocity().y), level.internalToExternal.get(eid)),level);

        }
        if(Gdx.input.isKeyJustPressed(Keys.SPACE)){
            System.out.println("interact!");
        }

        
    }




}
