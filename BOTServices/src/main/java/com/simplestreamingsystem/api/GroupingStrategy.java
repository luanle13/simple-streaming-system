package com.simplestreamingsystem.api;

public interface GroupingStrategy {
    int getInstance(Event event, int parallelism);
}
