package me.sciion.gdx.level.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

public class Model extends Component{

   public ModelInstance instance;
   public Vector3 offset;
   
   public Model(){
       offset = new Vector3();
   }
}
