package me.sciion.gdx.level.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class ModelComponent extends Component{

   public ModelInstance instance;
   
   public void create(float w, float h, float d){
       ModelBuilder build = new ModelBuilder();
       Material material =  new Material(ColorAttribute.createDiffuse(Color.WHITE));
       Model model = build.createBox(w,h,d, material, Usage.Position | Usage.Normal);
       instance = new ModelInstance(model);
   }
   
}
