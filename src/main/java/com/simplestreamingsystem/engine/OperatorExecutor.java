package com.simplestreamingsystem.engine;

import com.simplestreamingsystem.api.Event;
import com.simplestreamingsystem.api.Operator;
import org.apache.commons.lang3.SerializationUtils;

public class OperatorExecutor extends ComponentExecutor {
    private final Operator _operator;

    public OperatorExecutor(Operator operator) {
        super(operator);
        this._operator = operator;
        for (int i  = 0; i < operator.getParallelism(); i++) {
            Operator cloned = SerializationUtils.clone(operator);
            instanceExecutors[i] = new OperatorExecutor(i, cloned);
        }
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
//        eventCollector.add(event);

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
