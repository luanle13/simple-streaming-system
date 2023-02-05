package com.simplestreamingsystem.engine;

import com.simplestreamingsystem.api.Event;
import com.simplestreamingsystem.api.Source;
import org.apache.commons.lang3.SerializationUtils;

public class SourceExecutor extends ComponentExecutor {
    public SourceExecutor(Source source) {
        super(source);
        for (int i = 0; i < source.getParallelism(); i++) {
            Source cloned = SerializationUtils.clone(source);
            instanceExecutors[i] = new SourceInstanceExecutor(i, cloned);
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

    @Override
    public void setIncomingQueues(EventQueue[] queues) {
        throw new RuntimeException("No incoming queue is allowed for source executor");
    }
}
