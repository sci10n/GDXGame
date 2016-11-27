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

public class BlockRender extends Component{



    protected Color color;
    
    private ModelInstance instance;
    private Model model;
    
    public BlockRender(Color color) {
	this.color = color;
    }
    
    @Override
    public void setup(){

    }
    
    @Override
    public void tick() {
	
    }

    @Override
    public void render(Renderer render) {
	SpatialComponent s = parent.getComponent(ComponentType.Spatial);
	if(s == null)
	    return;
	if(instance == null){
		ModelBuilder b = new ModelBuilder();
		Material m =  new Material(ColorAttribute.createDiffuse(color));
		
		model = b.createBox(s.dimensions.x, 2.0f, s.dimensions.y, m,  Usage.Position | Usage.Normal);
	  	instance = new ModelInstance(model);
	}

	instance.transform.setToTranslation(s.position.cpy().add(s.dimensions.x/2.0f, 0, s.dimensions.y/2.0f)); 
	render.batch.render(instance,render.env);
    }

    @Override
    public ComponentType getType() {
	return ComponentType.BlockRender;
    }

    @Override
    public void dispose() {
	model.dispose();
    }

    
}
