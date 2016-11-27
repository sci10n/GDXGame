package me.sciion.gdx.level.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;

import me.sciion.gdx.level.components.ModelComponent;
import me.sciion.gdx.level.components.PlayerInputComponent;
import me.sciion.gdx.level.components.SpatialComponent;

public class RenderSystem extends EntitySystem {

    ComponentMapper<SpatialComponent> sm;
    ComponentMapper<ModelComponent> mm;
    ComponentMapper<PlayerInputComponent> pm;

    private PerspectiveCamera camera;
    private ModelBatch batch;
    private Environment environemnt;

    private int focus;

    private CameraInputController controller;

    public RenderSystem() {
	super(Aspect.all(ModelComponent.class, SpatialComponent.class));
	camera = new PerspectiveCamera(85, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	camera.position.set(0, 5.0f, 0);
	camera.lookAt(0, 0, 4);
	camera.near = 1f;
	camera.far = 300f;
	camera.update();
	batch = new ModelBatch();
	environemnt = new Environment();
	environemnt.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
	environemnt.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
	controller = new CameraInputController(camera);
	Gdx.input.setInputProcessor(controller);
    }

    @Override
    protected void processSystem() {
	Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1.0f);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

	// Ugly and should be changed!
	
	if (pm.getSafe(focus) != null) {
	    float x = sm.get(focus).position.x;
	    float z = sm.get(focus).position.z - 2;
	    camera.position.x = x;
	    camera.position.z = z;
	    camera.lookAt(x, 0, z+2);
	    camera.update();
	}
	

	batch.begin(camera);
	for (Entity e : getEntities()) {
	    if (pm.getSafe(e.getId()) != null) {
		setFocus(e.getId());
	    }
	    SpatialComponent s = sm.get(e);
	    ModelComponent m = mm.get(e);
	    m.instance.transform.setToTranslation(s.position);
	    batch.render(m.instance, environemnt);
	}
	batch.end();
    }

    public void setFocus(int focus) {
	this.focus = focus;
    }
}
