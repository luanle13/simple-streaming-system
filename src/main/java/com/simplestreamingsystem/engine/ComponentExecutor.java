package com.simplestreamingsystem.engine;

import com.simplestreamingsystem.api.Component;
import com.simplestreamingsystem.api.Event;

import java.util.ArrayList;
import java.util.List;

public abstract class ComponentExecutor {
    private final Component _component;
    protected InstanceExecutor[] instanceExecutors;

    ComponentExecutor(Component component) {
        this._component = component;
        int parallelism = component.getParallelism();
        this.instanceExecutors = new InstanceExecutor[parallelism];
    }
    public abstract void start();
    public InstanceExecutor[] getInstanceExecutors() {
        return instanceExecutors;
    }
    public Component getComponent() {
        return _component;
    }
    public void setIncomingQueues(EventQueue[] queues) {
        for (int i = 0; i < queues.length; i++) {
            instanceExecutors[i].setIncomingQueue(queues[i]);
        }
    }
}
