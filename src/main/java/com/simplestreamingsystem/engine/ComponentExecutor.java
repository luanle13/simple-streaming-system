package com.simplestreamingsystem.engine;

import com.simplestreamingsystem.api.Component;

public abstract class ComponentExecutor {
    protected Component component;
    protected InstanceExecutor[] instanceExecutors;

    ComponentExecutor(Component component) {
        this.component = component;
        int parallelism = component.getParallelism();
        this.instanceExecutors = new InstanceExecutor[parallelism];
    }
    public abstract void start();
    public InstanceExecutor[] getInstanceExecutors() {
        return instanceExecutors;
    }
    public Component getComponent() {
        return component;
    }
    public void setIncomingQueues(EventQueue[] queues) {
        for (int i = 0; i < queues.length; i++) {
            instanceExecutors[i].setIncomingQueue(queues[i]);
        }
    }
    public void setOutgoingQueue(EventQueue queue) {
        for (InstanceExecutor instance: instanceExecutors) {
            instance.setIncomingQueue(queue);
        }
    }
}
