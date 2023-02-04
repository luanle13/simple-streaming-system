package com.simplestreamingsystem.job;

import com.simplestreamingsystem.api.FieldsGrouping;
import com.simplestreamingsystem.api.Job;
import com.simplestreamingsystem.api.Stream;
import com.simplestreamingsystem.engine.JobStarter;

public class Main {
    public static void main(String[] args) {
        Job job = new Job("tax-calculate");
        Stream streetLaneStream = job.addSource(new SensorReader("sensor-reader", 1, 9990));
        streetLaneStream.applyOperator(new TaxCalculator("tax-calculator", 1, new FieldsGrouping()));
//                .applyOperator(new VehicleCounter("vehicle-counter", 1, new FieldsGrouping()));
        JobStarter starter = new JobStarter(job);
        starter.start();
    }
}
