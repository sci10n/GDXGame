package me.sciion.gdx.level.entity.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import me.sciion.gdx.game.render.Renderer;

public class SpatialComponent extends Component{

    
    public Vector3 position;
    public Vector2 dimensions;    
    

    public SpatialComponent(float x, float y, float z, float w, float h) {
	position = new Vector3(x,y,z);
	dimensions = new Vector2(w,h);
    }


    @Override
    public void tick() {
	
    }


    @Override
    public void render(Renderer render) {
	
    }


    @Override
    public ComponentType getType() {
	return ComponentType.Spatial;
    }


    @Override
    public void setup() {
	
    }


    @Override
    public void dispose() {
	
    }
    
    
}
