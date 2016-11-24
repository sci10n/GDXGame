package me.sciion.gdx.level.entity.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import me.sciion.gdx.game.render.Renderer;

public class PlayerInput extends Component{

    
    private Vector3 velocity;
    private float speed;
    @Override
    public void tick() {
	BlockRender br = parent.getComponent(ComponentType.BlockRender);
	if(br == null){
	    return;
	}
	velocity.scl(0.01f);
	if(Gdx.input.isKeyPressed(Keys.W)){
	    velocity.add(0, 1,0);
	}
	if(Gdx.input.isKeyPressed(Keys.S)){
	    velocity.add(0, -1,0);
	}
	if(Gdx.input.isKeyPressed(Keys.D)){
	    velocity.add(1, 0,0);
	}
	if(Gdx.input.isKeyPressed(Keys.A)){
	    velocity.add(-1, 0,0);
	}
	velocity.scl(speed);
	br.position.add(velocity.cpy().scl(Gdx.graphics.getDeltaTime()));
	
    }

    @Override
    public void render(Renderer render) {
	
    }

    @Override
    public ComponentType getType() {
	return ComponentType.PlayerInput;
    }

    @Override
    public void setup() {
	velocity = new Vector3(0, 0,0);
	speed = 3.0f;
    }

    @Override
    public void dispose() {
	
    }

}
