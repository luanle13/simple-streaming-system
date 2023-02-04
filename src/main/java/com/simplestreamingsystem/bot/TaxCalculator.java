package com.simplestreamingsystem.bot;

import com.simplestreamingsystem.api.Event;
import com.simplestreamingsystem.api.GroupingStrategy;
import com.simplestreamingsystem.api.Operator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaxCalculator extends Operator {

    private final Map<String, Integer> _taxPrice = new HashMap<String, Integer>() {{
       put("car", 20000);
       put("truck", 30000);
       put("bus", 0);
       put("container", 50000);
    }};
    private Integer _totalTax = 0;
    private int _instance = 0;

    public TaxCalculator(String name, int parallelism, GroupingStrategy groupingStrategy) {
        super(name, parallelism, groupingStrategy);
    }

    @Override
    public void setupInstance(int instance) {
        this._instance = instance;
    }

    @Override
    public void apply(Event event, List<Event> eventCollector) {
        String vehicle = ((VehicleEvent)event).getData().type;
        Integer tax = this._taxPrice.get(vehicle);
        System.out.println("Tax: " + tax);
//        eventCollector.add(event);
    }
}
