package com.simplestreamingsystem.engine;

import com.simplestreamingsystem.api.Event;
import com.simplestreamingsystem.api.Operator;

public class OperatorInstanceExecutor extends InstanceExecutor {
    private final int _instanceId;
    private final Operator _operator;
    public OperatorInstanceExecutor(int instanceId, Operator _operator) {
        this._instanceId = instanceId;
        this._operator = _operator;
        _operator.setupInstance(_instanceId);
    }
    @Override
    boolean runOnce() {
        Event event;
        try {
            event = incomingQueue.take();
        } catch (InterruptedException e) {
            return false;
        }
        _operator.apply(event, eventCollector);
        try {
            for (Event output: eventCollector) {
                outgoingQueue.put(output);
            }
            eventCollector.clear();
        } catch (InterruptedException e) {
            return false;
        }
        return true;
    }
}
