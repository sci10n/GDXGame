package me.sciion.gdx.level.entity.component;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import me.sciion.gdx.game.render.Renderer;

public class BlockRender extends Component{


    protected Vector3 position;
    protected Vector2 dimensions;
    
    public BlockRender(float x, float y, float z) {
	position = new Vector3(x,y,z);
	dimensions = new Vector2(1.0f,1.0f);

    }
    
    @Override
    public void setup(){

	//model = modelBuilder.createBox(1f,1f,1f, dummyMaterial, Usage.Position|Usage.Normal);
	//instance = new ModelInstance(model);
	//instance.transform.setToTranslation(position);
	
    }
    
    @Override
    public void tick() {
	
    }

    @Override
    public void render(Renderer render) {
	render.sr.rect(position.x, position.y, dimensions.x,dimensions.y);
    }

    @Override
    public ComponentType getType() {
	return ComponentType.BlockRender;
    }

    @Override
    public void dispose() {
    }

    
}
