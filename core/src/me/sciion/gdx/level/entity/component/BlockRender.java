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


    protected Vector3 position;
    protected Vector2 dimensions;
    protected Color color;
    
    private ModelInstance instance;
    private Model model;

    
    public BlockRender(float x, float y, float z, float w, float h, Color color) {
	this.color = color;
	ModelBuilder b = new ModelBuilder();
	Material m =  new Material(ColorAttribute.createDiffuse(color));
	
	model = b.createBox(w, 2.0f, h, m,  Usage.Position | Usage.Normal);
  	position = new Vector3(x,y,z);
  	dimensions = new Vector2(w,h);
  	instance = new ModelInstance(model);
    }
    
    public BlockRender(float x, float y, float z, float w, float h) {
	position = new Vector3(x,y,z);
	dimensions = new Vector2(w,h);
	color = Color.WHITE;
    }
    
    @Override
    public void setup(){

    }
    
    @Override
    public void tick() {
	
    }

    @Override
    public void render(Renderer render) {
	instance.transform.setToTranslation(position.cpy().add(dimensions.x/2.0f, 0, dimensions.y/2.0f)); 
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

    public Vector3 getPosition() {
        return position;
    }

    public Vector2 getDimensions() {
        return dimensions;
    }

    
}
