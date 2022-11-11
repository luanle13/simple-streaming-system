package com.simplestreamingsystem.job;

import com.simplestreamingsystem.api.Event;
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

    public TaxCalculator(String name) {
        super(name);
    }

    @Override
    public void apply(Event event, List<Event> eventCollector) {
        String vehicle = ((VehicleEvent)event).getData();
        Integer tax = this._taxPrice.get(vehicle);

        _totalTax += tax;
    }
    private void printTotalTax() {
        System.out.println("Total Tax: " + _totalTax);
    }
}
