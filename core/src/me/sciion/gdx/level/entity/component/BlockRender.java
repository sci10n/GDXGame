package me.sciion.gdx.level.entity.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

import me.sciion.gdx.game.render.Render3d;

public class BlockRender extends Component{

    private Model model;
    private ModelInstance instance;
    private Material dummyMaterial;
    
    private Vector3 position;
    
    public BlockRender(float x, float y, float z) {
	position = new Vector3(x,y,z);

    }
    
    @Override
    public void setup(){
	model = new Model();
	ModelBuilder modelBuilder = new ModelBuilder();
	dummyMaterial = new Material(ColorAttribute.createDiffuse(Color.FIREBRICK));
	model = modelBuilder.createBox(1f,1f,1f, dummyMaterial, Usage.Position|Usage.Normal);
	instance = new ModelInstance(model);
	instance.transform.setToTranslation(position);
    }
    
    @Override
    public void tick() {
	
    }

    @Override
    public void render(Render3d render) {
	render.modelBatch.render(instance,render.environment);
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
