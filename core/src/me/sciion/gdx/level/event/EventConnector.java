package me.sciion.gdx.level.event;

public interface EventConnector {

    public void recv(EventChannel channel, Event event);
    public void send(EventChannel channel);
}
