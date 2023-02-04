package com.simplestreamingsystem.goverment.penalty;

import com.simplestreamingsystem.api.Event;
import com.simplestreamingsystem.api.GroupingStrategy;
import com.simplestreamingsystem.api.Operator;
import com.simplestreamingsystem.goverment.bank.BankEvent;
import com.simplestreamingsystem.goverment.bank.BankInfor;

import java.util.List;

public class PenaltyCalculator extends Operator {
    private int _instance = 0;

    public PenaltyCalculator(String name, int parallelism, GroupingStrategy groupingStrategy) {
        super(name, parallelism, groupingStrategy);
    }

    @Override
    public void setupInstance(int instance) {
        this._instance = instance;
    }

    private int[][] price = {
            {200000, 500000},
            {1000000, 2000000},
            {0, 0},
            {10000000, 20000000}
    };

    private int getPenaltyPrice(String vehicleType, String penaltyType){
        int vehicalId;

        if (vehicleType.equals("car")) vehicalId = 0;
        else if (vehicleType.equals("truck")) vehicalId = 1;
        else if (vehicleType.equals("bus")) vehicalId = 2;
        else if (vehicleType.equals("container")) vehicalId = 3;
        else return -1;

        int penaltyId;

        if (penaltyType.equals("VuotDenDo")) penaltyId = 0;
        else if (penaltyType.equals("ChayQuaTocDo")) penaltyId = 1;
        else return -1;

        return price[vehicalId][penaltyId];
    }

    @Override
    public void apply(Event event, List<Event> eventCollector) {
        PenaltyInfor penaltyInfor = ((PenaltyEvent)event).getData();

        String penaltyType = penaltyInfor.type;
        String vehicleType = penaltyInfor.vehicleInfor.type;

        int penalty = getPenaltyPrice(vehicleType, penaltyType);

        System.out.println("Penalty (Loại xe: " + vehicleType + " | " + "Vi phạm: " + penaltyType + ") " +
                penalty);

        BankEvent bankEvent = new BankEvent(
                new BankInfor(penaltyInfor.vehicleInfor,
                penalty + penaltyInfor.taxValue)
        );

        eventCollector.add(bankEvent);
    }
}
