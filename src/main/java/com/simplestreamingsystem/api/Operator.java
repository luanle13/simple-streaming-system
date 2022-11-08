package com.simplestreamingsystem.api;

import java.util.List;

public abstract class Operator extends Component {
    public Operator(String name) {
        super(name);
    }

    public abstract void apply(Event event, List<Event> eventCollector);
}
