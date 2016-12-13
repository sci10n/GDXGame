package me.sciion.gdx.level.components;

import com.artemis.Component;

public class Damage extends Component {
    
    public int damage;
    
    public void create(int damage){
	this.damage = damage;
    }
}
