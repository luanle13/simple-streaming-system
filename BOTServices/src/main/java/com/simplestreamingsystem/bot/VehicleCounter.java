package com.simplestreamingsystem.bot;

import com.simplestreamingsystem.api.Event;
import com.simplestreamingsystem.api.GroupingStrategy;
import com.simplestreamingsystem.api.Operator;

import java.util.*;

public class VehicleCounter extends Operator {
    private final Map<String, Integer> _counter = new HashMap<String, Integer>();
    private int _instance = 0;

    public VehicleCounter(String name, int parallelism, GroupingStrategy groupingStrategy) {
        super(name, parallelism, groupingStrategy);
    }

    @Override
    public void setupInstance(int instance) {
        this._instance = instance;
    }

    @Override
    public void apply(Event event, List<Event> eventCollector) {
        String vehicle = ((VehicleEvent)event).getData().type;
        Integer count = _counter.getOrDefault(vehicle,0) + 1;
        _counter.put(vehicle, count);
        System.out.println("VehicleCounter --> ");
        printCount();
    }

    private void printCount() {
        List<String> vehicles = new ArrayList<String>(_counter.keySet());
        Collections.sort(vehicles);
        for (String vehicle: vehicles) {
            System.out.println(" " + vehicle + ": " + _counter.get(vehicle));
        }
    }
}
