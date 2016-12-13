package me.sciion.gdx.level.components;

import com.artemis.Component;

public class Health extends Component{

    public int health;
    public int max;
    
    public void create(int max, int start){
	health = start;
	this.max = max;
    }
    
    
    public void add(int v){
	health = Math.min(health + v , max);

    }
    
    public void remove(int v){
	health = Math.max(health - v , 0);
    }
    
    public int getCurrent(){
	return health;
    }
}
