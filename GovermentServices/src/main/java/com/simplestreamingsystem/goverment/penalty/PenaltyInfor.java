package com.simplestreamingsystem.goverment.penalty;

import com.simplestreamingsystem.goverment.VehicleInfor;

public class PenaltyInfor {
    public String penaltyType;                 // Loại vi pham, Vi du: VuotDenDo, ChayQuaTocDo
    public VehicleInfor vehicleInfor;   // xe vi pham
    public int taxValue;

    public PenaltyInfor(String penaltyType, VehicleInfor vehicleInfor, int taxValue){
        this.penaltyType = penaltyType;
        this.vehicleInfor = vehicleInfor;
        this.taxValue = taxValue;
    }
}
