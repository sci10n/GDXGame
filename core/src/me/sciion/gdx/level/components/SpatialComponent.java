package me.sciion.gdx.level.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

public class SpatialComponent extends Component {

    public Vector3 position;
    public Vector3 dimension;

    public void create(float x, float y, float z, float w, float h, float d) {
	position = new Vector3(x, y, z);
	dimension = new Vector3(w, h, d);
    }
    public void create(Vector3 p, Vector3 d) {
 	position = p;
 	dimension = d;
     }
}
