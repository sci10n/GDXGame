package me.sciion.gdx.level.components;

import com.artemis.Component;
import com.badlogic.gdx.physics.box2d.Body;

public class CollisionComponent extends Component {

    
    public Body body;
    
    public CollisionComponent() {
	
    }
    
    public void create(Body body){
	this.body = body;
    }
}
