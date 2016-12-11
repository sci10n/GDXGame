package me.sciion.gdx.level;

import com.badlogic.gdx.maps.MapObject;

@FunctionalInterface
public interface MarkerGenerator {
	void process(MapObject o);
}