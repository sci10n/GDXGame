package me.sciion.gdx.level.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;

public class DecalComponent extends Component{
    
    public Decal decal;
    public Vector3 offset;
    
    public DecalComponent(){
	offset = new Vector3();
    }

}
