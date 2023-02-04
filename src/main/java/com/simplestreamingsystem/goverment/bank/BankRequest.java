package com.simplestreamingsystem.goverment.bank;

import com.simplestreamingsystem.api.Event;
import com.simplestreamingsystem.api.GroupingStrategy;
import com.simplestreamingsystem.api.Operator;
import com.simplestreamingsystem.goverment.message.MessageEvent;
import com.simplestreamingsystem.goverment.message.MessageInfor;

import java.util.List;

public class BankRequest extends Operator {
    private int _instance = 0;

    public BankRequest(String name, int parallelism, GroupingStrategy groupingStrategy) {
        super(name, parallelism, groupingStrategy);
    }

    @Override
    public void setupInstance(int instance) {
        this._instance = instance;
    }

    @Override
    public void apply(Event event, List<Event> eventCollector) {
        boolean isSuccess = true;
        BankInfor bankInfor = ((BankEvent)event).getData();

        try {
            // TODO: Lúc nào cũng thành công, cần check trường hợp lỗi
            isSuccess = true;
        } catch (Exception e){
            System.out.println(e);
        }

        System.out.println("TIẾN HÀNH THANH TOAN (Tax + Penalty): "
                + bankInfor.money + "  "
                + (isSuccess == true ? "Thành công" : "Thất bại"));

        MessageEvent messageEvent = new MessageEvent(new MessageInfor(
                bankInfor.vehicleInfor.ownerPhoneNumber,
                isSuccess
        ));

        eventCollector.add(messageEvent);
    }
}
