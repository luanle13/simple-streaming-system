package com.simplestreamingsystem.engine;

import com.simplestreamingsystem.api.Event;
import com.simplestreamingsystem.api.Source;

public class SourceExecutor extends ComponentExecutor {
    private final Source _source;

    public SourceExecutor(Source source) {
        super(source);
        this._source = source;
    }

    @Override
    boolean runOnce() {
        try {
            _source.getEvents(eventCollector);
        } catch (Exception e) {
            return false;
        }

        try {
            for (Event event: eventCollector) {
                outgoingQueue.put(event);
            }
            eventCollector.clear();
        } catch (InterruptedException e) {
            return false;
        }

        return true;
    }

    @Override
    public void setIncomingQueue(EventQueue queue) {
        throw new RuntimeException("No incoming queue is allowed for source executor");
    }
}
