package me.sciion.gdx.level.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector3;

public class VelocityComponent extends Component{

    public Vector3 velocity;
    
    
    public VelocityComponent(){
	velocity = Vector3.Zero.cpy();
    }
    public void create(float vx, float vy, float vz){
	if(velocity == null)
	    velocity = new Vector3(vx, vy, vz);
	else
	    velocity.set(vx, vy, vz);
    }
}
