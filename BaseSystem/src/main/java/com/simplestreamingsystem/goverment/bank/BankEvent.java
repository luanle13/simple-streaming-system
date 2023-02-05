package com.simplestreamingsystem.goverment.bank;

import com.simplestreamingsystem.api.Event;

public class BankEvent implements Event {
    private final BankInfor bankInfor;

    public BankEvent(BankInfor bankInfor) {
        this.bankInfor = bankInfor;
    }

    @Override
    public BankInfor getData() {
        return bankInfor;
    }
}
