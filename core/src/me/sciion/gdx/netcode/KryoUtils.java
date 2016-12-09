package me.sciion.gdx.netcode;

import com.badlogic.gdx.math.Vector3;
import com.esotericsoftware.kryo.Kryo;

import me.sciion.gdx.utils.KryoMessage.ClientRequests;
import me.sciion.gdx.utils.KryoMessage.EntityCreated;
import me.sciion.gdx.utils.KryoMessage.EntityDelete;
import me.sciion.gdx.utils.KryoMessage.EntityInput;
import me.sciion.gdx.utils.KryoMessage.EntityMessage;
import me.sciion.gdx.utils.KryoMessage.EntitySync;
import me.sciion.gdx.utils.KryoMessage.ExternalId;
import me.sciion.gdx.utils.KryoMessage.Input;
import me.sciion.gdx.utils.KryoMessage.NetworkMessage;

public class KryoUtils {

    
    public static final int TCP = 5580;
    public static final int UDP = 5640;
    
    public static void register(Kryo kryo) {
	kryo.register(Vector3.class);
	kryo.register(Input.class);
	kryo.register(NetworkMessage.class);
	kryo.register(EntityMessage.class);
	kryo.register(EntityInput.class);
	kryo.register(EntityCreated.class);
	kryo.register(ClientRequests.class);
	kryo.register(ExternalId.class);
	kryo.register(EntityDelete.class);
	kryo.register(EntitySync.class);
    }

}
