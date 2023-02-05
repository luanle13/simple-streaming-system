package com.simplestreamingsystem.goverment.message;

import com.simplestreamingsystem.api.Event;
import com.simplestreamingsystem.api.GroupingStrategy;
import com.simplestreamingsystem.api.Operator;
import com.simplestreamingsystem.goverment.bank.BankEvent;
import com.simplestreamingsystem.goverment.bank.BankInfor;

import java.util.List;

public class MessageShower extends Operator {
    private int _instance = 0;

    public MessageShower(String name, int parallelism, GroupingStrategy groupingStrategy) {
        super(name, parallelism, groupingStrategy);
    }

    @Override
    public void setupInstance(int instance) {
        this._instance = instance;
    }

    @Override
    public void apply(Event event, List<Event> eventCollector) {
        MessageInfor messageInfor = ((MessageEvent)event).getData();

        System.out.println("------------------------------");
        if (messageInfor.isSuccess) {
            System.out.println("CHÚC MỪNG BẠN ĐÃ NỘP PHẠT THÀNH CÔNG");
        } else {
            System.out.println("BẠN ƠI, BẠN CẦN NỘP PHẠT, VÀ TÀI KHOẢN BẠN ĐANG HẾT TIỀN!!!!!!!");
        }
    }
}
