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
    public float idleTime;
    public boolean idling = true;
    public AutoInputComponent(){
	targetEntity = -1;
	currentTime = 0;
	targetTime = MathUtils.random(1, 2f);
	idleTime = MathUtils.random(2,4);
	MAX_DELTA = 2.0f;
    }
    
    public void create(int target){
	this.targetEntity = target;
    }
    
}
