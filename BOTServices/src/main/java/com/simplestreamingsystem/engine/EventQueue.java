package com.simplestreamingsystem.engine;

import com.simplestreamingsystem.api.Event;

import java.util.concurrent.ArrayBlockingQueue;

public class EventQueue extends ArrayBlockingQueue<Event> {
    public EventQueue(int size) {
        super(size);
    }
}
