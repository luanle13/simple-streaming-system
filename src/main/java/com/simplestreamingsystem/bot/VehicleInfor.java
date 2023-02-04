package com.simplestreamingsystem.bot;

public class VehicleInfor {
    public String type;                 // Loại xe
    public String carLicensePlates;     // Biển số xe
    public String ownerPhoneNumber;     // SDT chủ xe

    public VehicleInfor() {}
    public VehicleInfor(String type, String carLicensePlates, String ownerPhoneNumber){
        this.type = type;
        this.carLicensePlates = carLicensePlates;
        this.ownerPhoneNumber = ownerPhoneNumber;
    }
    public VehicleInfor(String rawData){
        String[] strs = rawData.split(" ", 3);
        type = strs[0];
        carLicensePlates = strs[1];
        ownerPhoneNumber = strs[2];
    }
}
