package me.sciion.gdx.level.components;

import com.artemis.Component;

import me.sciion.gdx.utils.Trigger;

public class Cooldown extends Component{

    public float cooldown = 1.0f;
    
    public Trigger trigger;
   
    
    public void create( Trigger trigger, float cooldown){
	this.trigger = trigger;
	this.cooldown = cooldown;
    }
}
