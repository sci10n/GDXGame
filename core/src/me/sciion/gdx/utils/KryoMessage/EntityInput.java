package me.sciion.gdx.utils.KryoMessage;

import com.badlogic.gdx.math.Vector3;

public class EntityInput extends EntityMessage{

    
    public Input type;
    public Vector3 position;
    public Vector3 velocity;
    public long time = 0;
    public EntityInput() {
	position = new Vector3();
	velocity = new Vector3();
    }

}
