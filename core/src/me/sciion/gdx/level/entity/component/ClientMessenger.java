package me.sciion.gdx.level.entity.component;

import me.sciion.gdx.game.render.Renderer;
import me.sciion.gdx.netcode.GameClient;
import me.sciion.gdx.netcode.messages.client.ClientMessage;

public class ClientMessenger extends Component{

    private GameClient client;
    

    public ClientMessenger(GameClient client) {
	this.client = client;
    }
    
    public void send(ClientMessage message){
	client.send(message);
    }
    
    @Override
    public void tick() {
	
    }

    @Override
    public void render(Renderer render) {
	
    }

    @Override
    public ComponentType getType() {
	return ComponentType.ClientMessenger;
    }

    @Override
    public void setup() {
	
    }

    @Override
    public void dispose() {
	
    }

}
