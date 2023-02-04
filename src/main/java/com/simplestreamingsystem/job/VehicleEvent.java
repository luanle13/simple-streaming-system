package com.simplestreamingsystem.job;

import com.simplestreamingsystem.api.Event;

public class VehicleEvent implements Event {
    private final String _type;

    public VehicleEvent(String type) {
        this._type = type;
    }

    @Override
    public String getData() {
        return _type;
    }
}
