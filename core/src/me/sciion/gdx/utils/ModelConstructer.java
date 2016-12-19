package me.sciion.gdx.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;

public class ModelConstructer {

    public static ModelInstance create(float w, float h, float d, Color color, Texture texture){
        
        if(h != 0){
 	   float x = w/2.0f;
 	       float y = h;
 	       float z = d/2.0f;
 	       ModelBuilder build = new ModelBuilder();
 	       Material material =  new Material(ColorAttribute.createDiffuse(color),TextureAttribute.createDiffuse(texture));
 	       
 	       build.begin();
 	       MeshPartBuilder meshBuilder;
 	       Material m = material;
 	       meshBuilder = build.part("pz", GL30.GL_TRIANGLES,Usage.Position | Usage.Normal | Usage.TextureCoordinates,m);
 	       meshBuilder.rect(-x,0,z,
 		       		 x,0,z,
 		       		 x,y,z,
 		       		-x,y,z,
 		       		 0,0,1.0f);
 	       meshBuilder.rect( x,0,-z,
 		       		-x,0,-z,
 		       		-x,y,-z,
 		       		 x,y,-z,
 		       		 0.0f,0.0f,-1.0f);
 	       meshBuilder.rect( x,0, z,
 		       		 x,0,-z,
 		       		 x,y,-z,
 		       		 x,y, z,
 		       		 1.0f,0.0f, 0.0f);
 	       meshBuilder.rect(-x,0,-z,
 		       		-x,0, z,
 		       		-x,y, z,
 		       		-x,y,-z,
 		       		-1.0f,0.0f, 0.0f);
 	       meshBuilder = build.part("py", GL30.GL_TRIANGLES,Usage.Position | Usage.Normal| Usage.TextureCoordinates,m);
 	       meshBuilder.rect(x,y, z,
 	     		 	x,y,-z,
 	     		       -x,y,-z,
 	     		       -x,y, z,
 	     		 	0.0f, 1.0f, 0.0f);
 	       meshBuilder.rect(-x,0, z,
 		       		-x,0,-z,
 		       		 x,0,-z,
 		       		 x,0, z,
 		       		 0.0f,-1.0f, 0.0f);
 	       
 	       //Model model = build.createBox(w, h*100, d, material, Usage.Position | Usage.Normal| Usage.TextureCoordinates);
 	       Model model = build.end();
 	       return new ModelInstance(model);  
        } else{
 	   float x = w/2.0f;
 	   float y = h;
 	   float z = d/2.0f;
 	   ModelBuilder build = new ModelBuilder();
 	   Material material;

 	   if(texture == null){
 	      material =  new Material(ColorAttribute.createDiffuse(color));
 	      build.begin();
 	      MeshPartBuilder meshBuilder;
 	      meshBuilder = build.part("py", GL30.GL_TRIANGLES,Usage.Position | Usage.Normal,material);
 	 	   meshBuilder.rect(x,y, z,
 	 	   		    x,y,-z,
 	 	     		   -x,y,-z,
 	 	     		   -x,y, z,
 	 	     		   0.0f, 1.0f, 0.0f);
 	 	   Model model = build.end();
 	 	   return new ModelInstance(model);
 	   }else{
 	     material =  new Material(ColorAttribute.createDiffuse(color),TextureAttribute.createDiffuse(texture));
 	     build.begin();
 	 	   MeshPartBuilder meshBuilder;
 	 	   meshBuilder = build.part("py", GL30.GL_TRIANGLES,Usage.Position | Usage.Normal| Usage.TextureCoordinates,material);
 	 	   meshBuilder.rect(x,y, z,
 	 	   		    x,y,-z,
 	 	     		   -x,y,-z,
 	 	     		   -x,y, z,
 	 	     		   0.0f, 1.0f, 0.0f);
 	 	   Model model = build.end();
 	 	   return new ModelInstance(model);
 	   }
 	   
           

        }
    }
    
    public static Mesh createMesh(float x, float y, float z, float w, float d){
	     //Material material =  new Material(ColorAttribute.createDiffuse(color));
	     Mesh mesh = new Mesh(true, 4, 0, 
		     new VertexAttribute(Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE),
		     new VertexAttribute(Usage.Normal, 3, ShaderProgram.NORMAL_ATTRIBUTE));
	     mesh.setVertices(new float[]{
		      (( w/2.0f)+x), y,(( d/2.0f)+z),0,1,0,
		      (( w/2.0f)+x), y,((-d/2.0f)+z),0,1,0,
		      ((-w/2.0f)+x), y,((-d/2.0f)+z),0,1,0,
		      ((-w/2.0f)+x), y,(( d/2.0f)+z),0,1,0
	     });
	     return mesh;
    }
    
    public static Decal createProgressBar(float x, float y, float z, int w, int h, float progress, float max, Color color){
	FrameBuffer buffer = new FrameBuffer(Format.RGBA8888,w,h,false);
	ShapeRenderer sr = new ShapeRenderer();
	SpriteBatch batch = new SpriteBatch();
	OrthographicCamera camera = new OrthographicCamera(w, h);
	batch.setProjectionMatrix(camera.combined);
	sr.setProjectionMatrix(camera.combined);
	buffer.begin();
	Gdx.gl.glClearColor(0.0f, 0, 0, 0.0f);
	Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
	float ppp = ((float)w) / max;
	sr.begin(ShapeType.Filled);
	sr.setColor(Color.RED);
	sr.rect(-w/2,-h/2,ppp*progress,h);
	sr.end();
	sr.begin(ShapeType.Line);
	sr.setColor(Color.BLACK);
	sr.rect(-w/2+1,-h/2+1,w-1,h-1);
	sr.end();
	buffer.end();
	return Decal.newDecal(new TextureRegion(buffer.getColorBufferTexture()));
    }
    
    public static TextureRegion createText(String text) {
	BitmapFont font = new BitmapFont();
	CharSequence str = text;
	FrameBuffer buffer = new FrameBuffer(Format.RGBA8888,256,256,false);
	SpriteBatch batch = new SpriteBatch();
	OrthographicCamera camera = new OrthographicCamera(256, 256);
	batch.setProjectionMatrix(camera.combined);
	buffer.begin();
	Gdx.gl.glViewport(0, 0, 256,256);
	Gdx.gl.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
	Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
	batch.begin();
	font.setColor(Color.WHITE);
	font.draw(batch, str, 0, 0);
	batch.end();
	buffer.end();
	return new TextureRegion(buffer.getColorBufferTexture());
    }
}
