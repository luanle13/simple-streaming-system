package com.simplestreamingsystem.goverment.message;

import com.simplestreamingsystem.api.Event;
import com.simplestreamingsystem.goverment.bank.BankInfor;

public class MessageEvent implements Event {
    private final MessageInfor messageInfor;

    public MessageEvent(MessageInfor messageInfor) {
        this.messageInfor = messageInfor;
    }

    @Override
    public MessageInfor getData() {
        return messageInfor;
    }
}
