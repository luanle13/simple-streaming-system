package com.simplestreamingsystem.job;

import com.simplestreamingsystem.api.Job;
import com.simplestreamingsystem.api.Stream;
import com.simplestreamingsystem.engine.JobStarter;

public class TaxCalculateJob {
    public static void main(String[] args) {
        Job job = new Job("tax_calculate");
        Stream streetLaneStream = job.addSource(new SensorReader("sensor-reader", 9990));
        streetLaneStream.applyOperator(new TaxCalculator("tax-calculator"));
        JobStarter starter = new JobStarter(job);
        starter.start();
    }
}
