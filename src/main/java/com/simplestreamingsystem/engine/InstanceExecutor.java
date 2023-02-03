package com.simplestreamingsystem.engine;

import com.simplestreamingsystem.api.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class InstanceExecutor extends Process {
    protected final List<Event> eventCollector = new ArrayList<Event>();
    protected EventQueue incomingQueue = null;
    protected EventQueue outgoingQueue = null;
    public InstanceExecutor() { }
    public void setIncomingQueue(EventQueue queue) {
        incomingQueue = queue;
    }
    public void addOutgoingQueue(String channel, EventQueue queue) {
        outgoingQueue = queue;
    }
}
