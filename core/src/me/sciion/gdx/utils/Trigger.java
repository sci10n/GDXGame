package me.sciion.gdx.utils;

import com.artemis.World;

@FunctionalInterface
public interface Trigger {

    public float execute(int id, World world);
}
