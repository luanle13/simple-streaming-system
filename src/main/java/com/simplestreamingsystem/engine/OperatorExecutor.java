package com.simplestreamingsystem.engine;

import com.simplestreamingsystem.api.Event;
import com.simplestreamingsystem.api.GroupingStrategy;
import com.simplestreamingsystem.api.Operator;
import org.apache.commons.lang3.SerializationUtils;

public class OperatorExecutor extends ComponentExecutor {
    private final Operator _operator;

    public OperatorExecutor(Operator operator) {
        super(operator);
        this._operator = operator;
        for (int i  = 0; i < operator.getParallelism(); i++) {
            Operator cloned = SerializationUtils.clone(operator);
            instanceExecutors[i] = new OperatorInstanceExecutor(i, cloned);
        }
    }

    @Override
    public void start() {
        if (instanceExecutors != null) {
            for (InstanceExecutor executor: instanceExecutors) {
                executor.start();
            }
        }
    }
    public GroupingStrategy getGroupingStrategy() { return _operator.getGroupingStrategy(); }
}
