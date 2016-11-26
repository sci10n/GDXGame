package me.sciion.gdx.level.marker;

import com.badlogic.gdx.maps.MapObject;

import me.sciion.gdx.level.entity.Entity;

@FunctionalInterface
public interface Marker {

    public Entity load(MapObject object);
}
