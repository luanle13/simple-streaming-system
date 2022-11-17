package com.simplestreamingsystem.engine;

import com.simplestreamingsystem.api.Component;
import com.simplestreamingsystem.api.Event;

import java.util.ArrayList;
import java.util.List;

public abstract class ComponentExecutor extends Process {
    private final Component _component;
    protected final List<Event> eventCollector;
    protected EventQueue incomingQueue = null;
    protected EventQueue outgoingQueue = null;

    ComponentExecutor(Component component) {
        this._component = component;
        this.eventCollector = new ArrayList<Event>();
    }

    public Component getComponent() {
        return _component;
    }

    public void setIncomingQueue(EventQueue queue) {
        incomingQueue = queue;
    }

    public void setOutgoingQueue(EventQueue queue) {
        outgoingQueue = queue;
    }
}
