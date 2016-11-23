package me.sciion.gdx.game.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

import me.sciion.gdx.level.Level;

public class Render3d {
    public PerspectiveCamera camera;
    public ModelBatch modelBatch;
    public Environment environment;
    public CameraInputController controller;
    
     
    public Render3d(){
	environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));	
	camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
	camera.position.set(0,10f,0f);
	camera.lookAt(0,0,0);
	camera.near = 1f;
	camera.far = 300f;
	camera.update();
	modelBatch = new ModelBatch();
	controller = new CameraInputController(camera);
	Gdx.input.setInputProcessor(controller);
    }
    
    public void render(Level level){
	controller.update();
	Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
	modelBatch.begin(camera);
	level.render(this);
	modelBatch.end();
    }
    
    public void dispose(){
	modelBatch.dispose();
    }
}
