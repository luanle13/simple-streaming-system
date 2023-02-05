package com.simplestreamingsystem.goverment.bank;

import com.simplestreamingsystem.goverment.VehicleInfor;

public class BankInfor {
    public VehicleInfor vehicleInfor;
    public int money;

    public BankInfor(VehicleInfor vehicleInfor, int money){
        this.vehicleInfor = vehicleInfor;
        this.money = money;
    }
}
