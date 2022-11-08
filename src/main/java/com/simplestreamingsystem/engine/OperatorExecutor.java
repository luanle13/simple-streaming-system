package com.simplestreamingsystem.engine;

import com.simplestreamingsystem.api.Event;
import com.simplestreamingsystem.api.Operator;

public class OperatorExecutor extends ComponentExecutor {
    private final Operator _operator;

    public OperatorExecutor(Operator operator) {
        super(operator);
        this._operator = operator;
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
