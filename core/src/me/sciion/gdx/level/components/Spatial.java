package me.sciion.gdx.level.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector3;

public class Spatial extends Component {

    public Vector3 position;
    public Vector3 dimension;
    public Vector3 axis;
    public float rads;
    
    public Spatial(){
	axis = new Vector3();
	rads = 0;
    }
    
    public Spatial(Vector3 position, Vector3 dimension){
	this.position = position;
	this.dimension = dimension;
    }
    public void create(float x, float y, float z, float w, float h, float d) {
	position = new Vector3(x, y, z);
	dimension = new Vector3(w, h, d);
    }
    public void create(Vector3 p, Vector3 d) {
 	position = p;
 	dimension = d;
     }
}
