package me.sciion.gdx.level.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;

import me.sciion.gdx.level.components.ModelComponent;
import me.sciion.gdx.level.components.SpatialComponent;

public class RenderSystem extends EntitySystem{

    ComponentMapper<SpatialComponent> sm;
    ComponentMapper<ModelComponent> mm;
    
    private PerspectiveCamera camera;
    private ModelBatch batch;
    private Environment environemnt;
    
    public RenderSystem() {
	super(Aspect.all(ModelComponent.class, SpatialComponent.class));
	camera = new PerspectiveCamera(85, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	camera.position.set(0, 15f, 0);
	camera.lookAt(0,0,-2);
	camera.near = 1f;
	camera.far = 300f;
	camera.update();
	batch = new ModelBatch();
	environemnt = new Environment();
	environemnt.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
	environemnt.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
    }

    @Override
    protected void processSystem() {
	Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
	camera.update();
	
	batch.begin(camera);
	for(Entity e: getEntities()){
	    SpatialComponent s = sm.get(e);
	    ModelComponent m = mm.get(e);
	    m.instance.transform.setToTranslation(s.position);
	    batch.render(m.instance, environemnt);
	}
	batch.end();
    }
}
