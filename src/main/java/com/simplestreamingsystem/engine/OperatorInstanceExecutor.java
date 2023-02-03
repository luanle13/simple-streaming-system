package com.simplestreamingsystem.engine;

import com.simplestreamingsystem.api.Event;
import com.simplestreamingsystem.api.Operator;

public class OperatorInstanceExecutor extends InstanceExecutor {
    private final int instanceId;
    private final Operator operator;
    public OperatorInstanceExecutor(int instanceId,, Operator operator) {
        this.instanceId = instanceId;
        this.operator = operator;
        operator.setupInstance(instanceId);
    }
    @Override
    boolean runOnce() {
        Event event;
        try {
            event = incomingQueue.take();
        } catch (InterruptedException e) {
            return false;
        }
        operator.apply(event, eventCollector);
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
