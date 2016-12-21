package me.sciion.gdx.level.system;

import org.python.modules.binascii;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import me.sciion.gdx.level.LevelGlobals;
import me.sciion.gdx.level.components.DecalComponent;
import me.sciion.gdx.level.components.Model;
import me.sciion.gdx.level.components.Spatial;
import me.sciion.gdx.level.components.Velocity;
import me.sciion.gdx.utils.InputUtils;
import me.sciion.gdx.utils.RenderUtils;
import net.dermetfan.utils.Pair;

public class RenderSystem extends EntitySystem {

    @Wire
    private LevelGlobals globals;

    private ComponentMapper<Spatial> sm;
    private ComponentMapper<Model> mm;
    private ComponentMapper<DecalComponent> cm;
    private ComponentMapper<Velocity> vm;
    
    private PerspectiveCamera camera;
    private ModelBatch batch;
    private DecalBatch decal_batch;
    private Environment environemnt;

    // private PointLight playerLight;
    private int focus;

    private CameraInputController controller;
    private ModelInstance dummyModel;
    private ModelInstance dummyModel2;
    
    private PhysicsSystem ps;
    private Box2DDebugRenderer debug;
    private ShapeRenderer sr;
    
    private ModelInstance lightOverlay;
    private ShaderProgram shaderProgram;
    private FrameBuffer visionMap;
    @SuppressWarnings("unchecked")
    public RenderSystem() {
	super(Aspect.all(Spatial.class).one(Model.class,DecalComponent.class));
	camera = new PerspectiveCamera(65, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	camera.position.set(0, 8.0f, 0);
	camera.lookAt(0, 0, 0.5f);
	camera.near = 1f;
	camera.far = 300f;
	camera.update();
	
	Texture t = new Texture(Gdx.files.internal("tile.png"));
	dummyModel = RenderUtils.create(0.1f, 0.1f, 0.1f, Color.CYAN,t);
	dummyModel2 = RenderUtils.create(0.1f, 0.1f, 0.1f, Color.RED,t);
	batch = new ModelBatch();
	decal_batch = new DecalBatch(new CameraGroupStrategy(camera));
	environemnt = new Environment();
	environemnt.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 0.8f));
	environemnt.add(new DirectionalLight().set(0.5f, 0.5f, 0.5f, -1f, -0.3f, -0.2f));
	shaderProgram = new ShaderProgram(Gdx.files.internal("shaders/simple.vx"), Gdx.files.internal("shaders/simple.fg"));
	//shaderProgram = new ShaderProgram(Gdx.files.internal("shaders/default.vx"), Gdx.files.internal("shaders/default.fg"));

	// playerLight = new PointLight().set(new Color(0.2f, 0.2f, 0.8f, 1.0f),
	// Vector3.Z, 10.0f);
	// environemnt.add(playerLight);
	visionMap = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
	controller = new CameraInputController(camera);
	Gdx.input.setInputProcessor(controller);
	debug = new  Box2DDebugRenderer();
	sr = new ShapeRenderer();
    }

    @Override
    protected void processSystem() {
	Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
	Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
	
	// Ugly and should be changed!
	float screenX = Gdx.input.getX();
	float screenY = Gdx.input.getY();
	Vector3 origin = new Vector3();
	if(sm.get(globals.focusEntity) != null){
        	float x = sm.get(globals.focusEntity).position.x;
        	float z = sm.get(globals.focusEntity).position.z;
        	camera.position.x = x;
        	camera.position.z = z;
        	camera.lookAt(x, 1,z);
        	Ray ray = camera.getPickRay(screenX, screenY);
        	Vector3 translatedPos = ray.origin;
        	translatedPos.y = 2;
        	dummyModel.transform.setToTranslation(translatedPos);
        	InputUtils.setPlayerMouse(translatedPos.x, translatedPos.z);
        	camera.update();
        	origin = sm.get(globals.focusEntity).position; 
        	Vector3 target = origin.cpy().sub(InputUtils.playerMouse()).nor().scl(-2.5f).add(origin);
        	target.y = 0.0f;
        	dummyModel2.transform.setToTranslation(target);
        	lightOverlay = RenderUtils.createMesh(ps.getPhysicsWorld(),world, origin.x, 1.0f, origin.z, 10, 200,globals);

	}
//	shaderProgram.begin();
//	shaderProgram.setUniformMatrix("u_projectionViewMatrix", camera.combined);
//	//shaderProgram.setUniformf("u_pos", camera.project(origin).nor());
//	//shaderProgram.setUniformf("u_norm", Vector2.len(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
//	//shaderProgram.setUniformf("screenHeight", Gdx.graphics.getHeight());
//       	lightOverlay.render(shaderProgram,GL30.GL_TRIANGLE_FAN);
//	shaderProgram.end();
	batch.begin(camera);
	for (Entity e : getEntities()) {
	    if (cm.getSafe(e) != null) {
		Spatial s = sm.get(e);
		Decal decal = cm.get(e).decal;
		decal.setPosition(s.position.cpy().add(cm.get(e).offset));
		decal.lookAt(camera.position, camera.up);
		decal_batch.add(decal);
	    }
	    if (mm.getSafe(e) != null) {
		Spatial s = sm.get(e);
		Model m = mm.get(e);
		m.instance.transform.setToTranslation(s.position.cpy().add(mm.get(e).offset));
		m.instance.transform.rotateRad(new Vector3(0,1,0), s.rads);
		batch.render(m.instance, environemnt);

	    }
	}
	batch.render(dummyModel2);
	//batch.render(dummyModel);
	batch.end();
	decal_batch.flush();
	
	visionMap.begin();
	Gdx.gl.glClearColor(0, 0, 0, 1.0f);
	Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
	batch.begin(camera);
	batch.render(lightOverlay);
	batch.end();
	visionMap.end();
	Matrix4 projection = camera.combined.cpy();
	projection.rotate(0, 1, 1f, -180);
	projection.rotate(0, 1, 0, 180);
	debug.render(ps.getPhysicsWorld(), projection);
	sr.setProjectionMatrix(camera.combined);
	
	sr.begin(ShapeType.Line);
	for(Pair<Vector3, Vector3> p : globals.rays){
	   // System.out.println(p.toString());
	    sr.line(p.getKey(), p.getValue());
	}
	sr.end();
	
	OrthographicCamera cam = new OrthographicCamera(Gdx.graphics.getWidth(),  Gdx.graphics.getHeight());
	cam.setToOrtho(true);
	SpriteBatch batch = new SpriteBatch();
	batch.setBlendFunction(GL30.GL_DST_COLOR, GL30.GL_ZERO);
	batch.setProjectionMatrix(cam.combined);	
	batch.begin();
	batch.draw(visionMap.getColorBufferTexture(),0,0);
	batch.end();
	

	
    }

    public void setFocus(int focus) {
	this.focus = focus;
    }
}
