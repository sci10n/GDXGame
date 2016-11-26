package me.sciion.gdx.game.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;

import me.sciion.gdx.level.Level;

public class Renderer {

    
    private final float SCALE = 32;
    //private OrthographicCamera camera;
    private CameraInputController controller;
    
    //public SpriteBatch batch;
    //public ShapeRenderer sr;
    
    public PerspectiveCamera camera;
    public ModelBatch batch;
    public Environment env;
    public Renderer(){
	camera = new PerspectiveCamera(85, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	
	camera.position.set(0, 15f, 0);
	camera.lookAt(0,0,-2);
	camera.near = 1f;
	camera.far = 300f;
	camera.update();
	batch = new ModelBatch();
	env = new Environment();
	env.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
	env.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
    }
    
    public void render(Level level){
	Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
	camera.update();
	batch.begin(camera);
	level.render(this);
	batch.end();
    }
    
    public void move(Vector3 pos){
	camera.position.x = pos.x;
	camera.position.z = pos.z;
	
	camera.lookAt(pos.x,0,pos.z);
	camera.update();
    }
}
