package me.sciion.gdx.level;

import com.artemis.annotations.EntityId;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import net.dermetfan.utils.Pair;

public class LevelGlobals {

    @EntityId public int focusEntity;
    
    public Array<Pair<Vector3, Vector3>> rays;
    
    public LevelGlobals(){
	rays = new Array<Pair<Vector3, Vector3>>();
	
    }
}
