package me.sciion.gdx.level.components;

import com.artemis.Component;

public class Detector extends Component{
    public float range;
    public boolean active = true;
    public float accumulator = 0.5f;
    public Detector(){
	range = 2;
    }
    
    public void create(float range){
	this.range = range;
    }
    public void create(float range, boolean active){
  	this.range = range;
  	this.active = active;
      }
}
