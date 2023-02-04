package com.simplestreamingsystem.goverment.penalty;

import com.simplestreamingsystem.bot.VehicleInfor;

public class PenaltyInfor {
    public String type;                 // Loại vi pham, Vi du: VuotDenDo, ChayQuaTocDo
    public VehicleInfor vehicleInfor;   // xe vi pham
    public int taxValue;

    public PenaltyInfor(String type, VehicleInfor vehicleInfor, int taxValue){
        this.type = type;
        this.vehicleInfor = vehicleInfor;
        this.taxValue = taxValue;
    }
}
