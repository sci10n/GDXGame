package me.sciion.gdx.level.components;

import com.artemis.Component;

public class LightEmitter extends Component{

    public float luminocity;
    
    public void create(float luminocity) {
	this.luminocity = luminocity;
    }
}
