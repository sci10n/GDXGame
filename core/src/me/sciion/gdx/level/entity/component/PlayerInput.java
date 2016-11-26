package me.sciion.gdx.level.entity.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import me.sciion.gdx.game.render.Renderer;

public class PlayerInput extends Component{

    
    private Vector3 velocity;
    private float speed;
    
    public PlayerInput() {
	velocity = new Vector3(0, 0, 0);
    }
    
    @Override
    public void tick() {
	BlockRender br = parent.getComponent(ComponentType.BlockRender);
	if(br == null){
	    return;
	}
	velocity.scl(0.01f);
	if(Gdx.input.isKeyPressed(Keys.W)){
	    
	    float grid_correction = MathUtils.round(br.position.x)-br.position.x;
	    velocity.add(grid_correction,0,-1);
	}
	if(Gdx.input.isKeyPressed(Keys.S)){
	    float grid_correction = MathUtils.round(br.position.x)-br.position.x;
	    velocity.add(grid_correction, 0,1);
	}
	if(Gdx.input.isKeyPressed(Keys.D)){
	    float grid_correction = MathUtils.round(br.position.z)-br.position.z;
	    velocity.add(1,0,grid_correction);
	}
	if(Gdx.input.isKeyPressed(Keys.A)){
	    float grid_correction = MathUtils.round(br.position.z)-br.position.z;
	    velocity.add(-1,0, grid_correction);
	}
	velocity.scl(speed);
	br.position.add(velocity.cpy().scl(Gdx.graphics.getDeltaTime()));
    }

    @Override
    public void render(Renderer render) {
	BlockRender br = parent.getComponent(ComponentType.BlockRender);
	if(br == null){
	    return;
	}
	render.move(br.position);
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
