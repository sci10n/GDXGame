package me.sciion.gdx.level.event;

import com.badlogic.gdx.utils.Array;

public class EventChannel {

    private Array<EventConnector> connectors;
    
    
    public EventChannel(){
	connectors = new Array<EventConnector>();
    }
    
    public void subscribe(EventConnector c){
	if(!connectors.contains(c, false)){
	    connectors.add(c);
	}
    }
    
    public void unsubscribe(EventConnector c){
	if(connectors.contains(c, false)){
	    connectors.removeValue(c, false);
	}
    }
    
    public void input(Event event,EventConnector connector){
	for(EventConnector c : connectors){
	    if(!c.equals(connector)){
		c.recv(this, event);
	    }
	}
    }
    
    
}
