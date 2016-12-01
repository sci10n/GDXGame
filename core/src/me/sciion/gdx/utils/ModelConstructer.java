package me.sciion.gdx.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class ModelConstructer {

    public static ModelInstance create(float w, float h, float d, Color color){
        
        if(h != 0){
 	   float x = w/2.0f;
 	       float y = h;
 	       float z = d/2.0f;
 	       ModelBuilder build = new ModelBuilder();
 	       Material material =  new Material(ColorAttribute.createDiffuse(color)/*,TextureAttribute.createDiffuse(texture)*/);
 	       
 	       build.begin();
 	       MeshPartBuilder meshBuilder;
 	       Material m = material;
 	       meshBuilder = build.part("pz", GL30.GL_TRIANGLES,Usage.Position | Usage.Normal /*| Usage.TextureCoordinates*/,m);
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
 	       meshBuilder = build.part("py", GL30.GL_TRIANGLES,Usage.Position | Usage.Normal/*| Usage.TextureCoordinates*/,m);
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
 	   Material material =  new Material(ColorAttribute.createDiffuse(color)/*,TextureAttribute.createDiffuse(texture)*/);
            build.begin();
 	   MeshPartBuilder meshBuilder;
 	   meshBuilder = build.part("py", GL30.GL_TRIANGLES,Usage.Position | Usage.Normal/*| Usage.TextureCoordinates*/,material);
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
