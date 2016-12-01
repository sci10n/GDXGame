package me.sciion.gdx.level.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import me.sciion.gdx.level.components.ModelComponent;
import me.sciion.gdx.level.components.PlayerInputComponent;
import me.sciion.gdx.level.components.SpatialComponent;

public class RenderSystem extends EntitySystem {

    ComponentMapper<SpatialComponent> sm;
    ComponentMapper<ModelComponent> mm;
    ComponentMapper<PlayerInputComponent> pm;
    
    public static boolean followPlayer = true;
    private PerspectiveCamera camera;
    private ModelBatch batch;
    private DecalBatch decal_batch;
    private Environment environemnt;
    
   // private PointLight playerLight;
    private int focus;

    private CameraInputController controller;
    public RenderSystem() {
	super(Aspect.all(ModelComponent.class, SpatialComponent.class));
	camera = new PerspectiveCamera(65, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	camera.position.set(0, 4.0f, 0);
	camera.lookAt(0, 0, 1);
	camera.near = 1f;
	camera.far = 300f;
	camera.update();

	batch = new ModelBatch();
	decal_batch = new DecalBatch(new CameraGroupStrategy(camera));
	environemnt = new Environment();
	environemnt.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 0.8f));
	environemnt.add(new DirectionalLight().set(0.5f, 0.5f, 0.5f, -1f, -0.3f, -0.2f));

	//playerLight = new PointLight().set(new Color(0.2f, 0.2f, 0.8f, 1.0f), Vector3.Z, 10.0f);
	//environemnt.add(playerLight);
	controller = new CameraInputController(camera);
	Gdx.input.setInputProcessor(controller);
    }

    @Override
    protected void processSystem() {
	Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
	Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
	
	// Ugly and should be changed!
	
	if (pm.getSafe(focus) != null && followPlayer) {
	    float x = sm.get(focus).position.x;
	    float z = sm.get(focus).position.z;
	    camera.position.x = x;
	    camera.position.z = z;
	    camera.lookAt(x,1, z+1.5f);

	    camera.update();
	}
	
	batch.begin(camera);
	for (Entity e : getEntities()) {
	    if (pm.getSafe(e.getId()) != null) {
		setFocus(e.getId());
	    }
	    if(mm.get(e).decal != null){
		Decal decal = mm.get(e).decal;
		decal.setPosition(sm.get(e).position.cpy().add(0, 0.1f, 0));
		decal.setDimensions(1, 1);
		decal.lookAt(camera.position, camera.up);
		decal_batch.add(mm.get(e).decal);
	    }
	    if(mm.get(e).instance != null){
		SpatialComponent s = sm.get(e);
		ModelComponent m = mm.get(e);
		m.instance.transform.setToTranslation(s.position);
		batch.render(m.instance, environemnt);
	    }
	}
	batch.end();
	decal_batch.flush();
    }

    public void setFocus(int focus) {
	this.focus = focus;
    }
}
