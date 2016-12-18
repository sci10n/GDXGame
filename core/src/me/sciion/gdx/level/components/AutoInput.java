package me.sciion.gdx.level.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector3;

import me.sciion.gdx.utils.Behaviors;

public class AutoInput extends Component{

    public Behaviors behavior = Behaviors.WANDERING;
    public float ellapsedTime = 3;
    public Vector3 direction;
    
    public AutoInput(){
	direction = new Vector3();
    }
    

    
}
