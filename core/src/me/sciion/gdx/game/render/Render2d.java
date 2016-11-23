package me.sciion.gdx.game.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Render2d{

    
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ShapeRenderer sr;
    
    
    public Render2d(){
	camera = new OrthographicCamera();
	camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	camera.update();

	batch = new SpriteBatch();
	sr = new ShapeRenderer();
    }
    
    public void render(){
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	Gdx.gl.glClearColor(0, 0, 0, 1);
	sr.setProjectionMatrix(camera.combined);
	sr.setColor(Color.GREEN);
	sr.begin(ShapeType.Filled);
	sr.circle(0, 0, 16);
	sr.end();
    }
}
