package me.sciion.gdx.level.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector3;

public class Velocity extends Component{

    public Vector3 velocity;
    
    public void create(Vector3 velocity){
	this.velocity = velocity;
    }
}
