package com.simplestreamingsystem.goverment.penalty;

import com.simplestreamingsystem.api.Event;

public class PenaltyEvent implements Event {
    private final PenaltyInfor penaltyInfor;

    public PenaltyEvent(PenaltyInfor penaltyInfor) {
        this.penaltyInfor = penaltyInfor;
    }
    @Override
    public PenaltyInfor getData() {
        return this.penaltyInfor;
    }
}
