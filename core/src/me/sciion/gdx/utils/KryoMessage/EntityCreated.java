package me.sciion.gdx.utils.KryoMessage;

import com.badlogic.gdx.math.Vector3;

import me.sciion.gdx.utils.EntityType;

public class EntityCreated extends EntityMessage{

    public Vector3 poistion;
    public EntityType type;
    public Vector3 dimensions;

    @Override
    public String toString() {
        return id + "\t" + poistion + "\t" + type;
    }
}
