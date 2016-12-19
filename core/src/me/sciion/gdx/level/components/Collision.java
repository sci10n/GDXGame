package me.sciion.gdx.level.components;

import com.artemis.Component;
import com.badlogic.gdx.utils.Array;

import me.sciion.gdx.level.entity.listeners.CollisionListener;
import me.sciion.gdx.level.entity.listeners.NullListener;

public class Collision extends Component {

    public Array<Integer> currentCollisions;

    public Collision() {
	currentCollisions = new Array<Integer>();
    }



}
