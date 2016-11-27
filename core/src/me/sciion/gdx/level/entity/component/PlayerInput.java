package me.sciion.gdx.level.entity.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import me.sciion.gdx.game.render.Renderer;
import me.sciion.gdx.netcode.messages.client.MoveMessage;

public class PlayerInput extends Component{

    
    private Vector3 velocity;
    private float speed;
    
    public PlayerInput() {
	velocity = new Vector3(0, 0, 0);
	
    }
    
    @Override
    public void tick() {
	SpatialComponent s = parent.getComponent(ComponentType.Spatial);
	if(s == null){
	    return;
	}
	velocity.scl(0.01f);
	if(Gdx.input.isKeyPressed(Keys.W)){
	    float grid_correction = 0;//MathUtils.round(s.position.x)-s.position.x;
	    velocity.add(grid_correction,0,-1);
	}
	if(Gdx.input.isKeyPressed(Keys.S)){
	    float grid_correction = 0;//MathUtils.round(s.position.x)-s.position.x;
	    velocity.add(grid_correction, 0,1);
	}
	if(Gdx.input.isKeyPressed(Keys.D)){
	    float grid_correction = 0;//MathUtils.round(s.position.z)-s.position.z;
	    velocity.add(1,0,grid_correction);
	}
	if(Gdx.input.isKeyPressed(Keys.A)){
	    float grid_correction = 0;//MathUtils.round(s.position.z)-s.position.z;
	    velocity.add(-1,0, grid_correction);
	}
	velocity.scl(speed);
	s.position.add(velocity.cpy().scl(Gdx.graphics.getDeltaTime()));
	ClientMessenger cm = parent.getComponent(ComponentType.ClientMessenger);
	if(cm == null)
	    return;
	MoveMessage move = new MoveMessage(parent.getID(),s.position);
	cm.send(move);
    }

    @Override
    public void render(Renderer render) {
	SpatialComponent s = parent.getComponent(ComponentType.Spatial);
	if(s == null){
	    return;
	}
	render.move(s.position);
    }

    @Override
    public ComponentType getType() {
	return ComponentType.PlayerInput;
    }

    @Override
    public void setup() {
	speed = 5.5f;
    }

    @Override
    public void dispose() {
	
    }

}
