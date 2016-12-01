package me.sciion.gdx.level.components;

import com.artemis.Component;
import com.badlogic.gdx.math.MathUtils;

public class AutoInputComponent extends Component{

    // If -1, wander
    public int targetEntity;
    
    public final float MAX_DELTA;
    // Counter in seconds
    public float currentTime;
    
    // Target value for counter in seconds
    public float targetTime;
    
    public AutoInputComponent(){
	targetEntity = -1;
	currentTime = 0;
	targetTime = MathUtils.random(1, 5f);
	MAX_DELTA = 2.0f;
    }
    
    public void create(int target){
	this.targetEntity = target;
    }
    
}
