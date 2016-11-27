package me.sciion.gdx.level.components;

import com.artemis.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class ModelComponent extends Component{

   public ModelInstance instance;
   
   public void create(float w, float h, float d){
       
       if(h != 0){
	   float x = w/2.0f;
	       float y = h;
	       float z = d/2.0f;
	       Texture texture = new Texture(Gdx.files.internal("tiled3.png"));
	       Texture texture2 = new Texture(Gdx.files.internal("tiled2.png"));
	       ModelBuilder build = new ModelBuilder();
	       Material material =  new Material(ColorAttribute.createDiffuse(Color.WHITE),TextureAttribute.createDiffuse(texture));
	       Material material2  =  new Material(ColorAttribute.createDiffuse(Color.WHITE),TextureAttribute.createDiffuse(texture2));
	       //Material materialy =  new Material(ColorAttribute.createDiffuse(Color.WHITE),TextureAttribute.createDiffuse(texture));
	       //Material material =  new Material(ColorAttribute.createDiffuse(Color.WHITE));

	       build.begin();
	       MeshPartBuilder meshBuilder;
	       meshBuilder = build.part("pz", GL20.GL_TRIANGLES,Usage.Position | Usage.Normal | Usage.TextureCoordinates,material);
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
	       meshBuilder = build.part("py", GL20.GL_TRIANGLES,Usage.Position | Usage.Normal| Usage.TextureCoordinates,material2);
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
	       
	       Model model = build.end();
	       instance = new ModelInstance(model);  
       } else{
	   float x = w/2.0f;
	   float y = h;
	   float z = d/2.0f;
	   Texture texture3 = new Texture(Gdx.files.internal("dummy2.png"));
	   ModelBuilder build = new ModelBuilder();
	   Material material =  new Material(ColorAttribute.createDiffuse(Color.WHITE),TextureAttribute.createDiffuse(texture3));
           build.begin();
	   MeshPartBuilder meshBuilder;
	   meshBuilder = build.part("py", GL20.GL_TRIANGLES,Usage.Position | Usage.Normal| Usage.TextureCoordinates,material);
	   meshBuilder.rect(x,y, z,
	   		    x,y,-z,
	     		   -x,y,-z,
	     		   -x,y, z,
	     		   0.0f, 1.0f, 0.0f);
	   Model model = build.end();
	   instance = new ModelInstance(model);
       }
      
   }
   
}
