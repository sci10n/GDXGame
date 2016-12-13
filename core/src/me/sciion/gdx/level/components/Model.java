package me.sciion.gdx.level.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.decals.Decal;

public class Model extends Component{

    enum Type{
	Model, Decal
    }
    
   public Type type;
   public ModelInstance instance;
   public Decal decal;
   
}
