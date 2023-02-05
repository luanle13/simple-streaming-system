package com.simplestreamingsystem.api;

import java.io.Serializable;
import java.util.List;

public abstract class Operator extends Component implements Serializable {
    private final GroupingStrategy groupingStrategy;
    public Operator(String name, int parallelism, GroupingStrategy groupingStrategy) {
        super(name, parallelism);
        this.groupingStrategy = groupingStrategy;
    }
    public abstract void setupInstance(int instance);
    public abstract void apply(Event event, List<Event> eventCollector);
    public GroupingStrategy getGroupingStrategy() {
        return groupingStrategy;
    }
}
