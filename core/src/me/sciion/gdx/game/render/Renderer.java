package me.sciion.gdx.game.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import me.sciion.gdx.level.Level;

public class Renderer {

    
    private final float SCALE = 32;
    private OrthographicCamera camera;
    private CameraInputController controller;
    public SpriteBatch batch;
    public ShapeRenderer sr;
    
    
    public Renderer(){
	camera = new OrthographicCamera();
	camera.setToOrtho(false, Gdx.graphics.getWidth()/SCALE, Gdx.graphics.getHeight()/SCALE);
	camera.update();
	controller = new CameraInputController(camera);
	//Gdx.input.setInputProcessor(controller);
	batch = new SpriteBatch();
	sr = new ShapeRenderer();
    }
    
    public void render(Level level){
	System.out.println(Gdx.graphics.getFramesPerSecond());
	controller.update();
	camera.update();
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	Gdx.gl.glClearColor(0, 0, 0, 1);
	sr.setProjectionMatrix(camera.combined);
	batch.setProjectionMatrix(camera.combined);
	sr.begin(ShapeType.Filled);
	level.render(this);
	sr.end();
    }
}
