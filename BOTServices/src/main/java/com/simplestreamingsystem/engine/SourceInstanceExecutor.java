package com.simplestreamingsystem.engine;

import com.simplestreamingsystem.api.Event;
import com.simplestreamingsystem.api.Source;

public class SourceInstanceExecutor extends InstanceExecutor {
    private final int _instanceId;
    private final Source _source;
    public SourceInstanceExecutor(int instanceId, Source source) {
        this._instanceId = instanceId;
        this._source = source;
        source.setupInstance(_instanceId);
    }

    @Override
    boolean runOnce() {
        try {
            _source.getEvents(eventCollector);
        } catch (Exception e) {
            return false;
        }

        try {
            for (Event e: eventCollector) {
                outgoingQueue.put(e);
            }
            eventCollector.clear();
        } catch (InterruptedException e) {
            return false;
        }
        return true;
    }
}
