package com.simplestreamingsystem.bot;

import com.simplestreamingsystem.api.Event;
import com.simplestreamingsystem.api.Source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class SensorReader extends Source {
    private int _instance = 0;
    private BufferedReader _reader;
    private transient WebcamQRCode _webcamQRCode;
//    private Socket _socket;
    private final int _portBase;

    public SensorReader(String name, int parallelism, int port) {
        super(name, parallelism);
        this._portBase = port;

    }

    @Override
    public void setupInstance(int instance) {
        this._instance = instance;
//        setupSocketReader(_portBase + _instance);
        this._webcamQRCode = new WebcamQRCode();
    }

    @Override
    public void getEvents(List<Event> eventCollector) {
        String qrResult = null;
        try {
            qrResult = _webcamQRCode.getQRResult();
        } catch (Exception e){
            e.printStackTrace();
        }

        if (qrResult != null) {
            System.out.println(qrResult);
            VehicleInfor vehicle = new VehicleInfor(qrResult);
            if (vehicle == null) {
                System.exit(0);
            }
            eventCollector.add(new VehicleEvent(vehicle));
            System.out.println("");
            System.out.println("SensorReader --> " + vehicle.type);
            _webcamQRCode.setQRResult(null);
        }
    }
}
