package com.simplestreamingsystem.goverment;

import com.simplestreamingsystem.api.FieldsGrouping;
import com.simplestreamingsystem.api.Job;
import com.simplestreamingsystem.api.Stream;
import com.simplestreamingsystem.engine.JobStarter;
import com.simplestreamingsystem.goverment.bank.BankRequest;
import com.simplestreamingsystem.goverment.message.MessageShower;
import com.simplestreamingsystem.goverment.penalty.PenaltyCalculator;

public class Main {
    public static void main(String[] args) {
        Job job = new Job("penalty-calculate");
        Stream streetLaneStream = job.addSource(new ServerReader("server-reader", 1, 9991));
        streetLaneStream.applyOperator(new PenaltyCalculator("penalty-calculator", 1, new FieldsGrouping()))
                        .applyOperator(new BankRequest("bank-request", 1, new FieldsGrouping()))
                        .applyOperator(new MessageShower("message-shower", 1, new FieldsGrouping()));

        JobStarter starter = new JobStarter(job);
        starter.start();
    }
}