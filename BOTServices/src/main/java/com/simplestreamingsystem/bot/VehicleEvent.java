package com.simplestreamingsystem.bot;

import com.simplestreamingsystem.api.Event;

public class VehicleEvent implements Event {
    private final VehicleInfor vehicleInfor;

    public VehicleEvent(VehicleInfor vehicleInfor) {
        this.vehicleInfor = vehicleInfor;
    }

    @Override
    public VehicleInfor getData() {
        return vehicleInfor;
    }
}
