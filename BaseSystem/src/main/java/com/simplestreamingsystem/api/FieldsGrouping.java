package com.simplestreamingsystem.api;

import java.io.Serializable;

public class FieldsGrouping implements GroupingStrategy, Serializable {
    public FieldsGrouping() {

    }
    protected Object getKey(Event event) {
        return event.getData();
    }
    @Override
    public int getInstance(Event event, int parallelism) {
        return Math.abs(getKey(event).hashCode()) % parallelism;
    }
}
