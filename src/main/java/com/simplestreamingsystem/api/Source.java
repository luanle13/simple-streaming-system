package com.simplestreamingsystem.api;

import java.io.Serializable;
import java.util.List;

public abstract class Source extends Component implements Serializable {
    public Source(String name, int parallelism) {
        super(name, parallelism);
    }
    public abstract void setupInstance(int instance);

    public abstract void getEvents(List<Event> eventCollector);
}
