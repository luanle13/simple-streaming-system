package com.simplestreamingsystem.bot;

import com.simplestreamingsystem.api.Event;
import com.simplestreamingsystem.api.GroupingStrategy;
import com.simplestreamingsystem.api.Operator;
import com.simplestreamingsystem.http_request.HttpRequest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaxCalculator extends Operator {
    private final Map<String, Integer> _taxPrice = new HashMap<String, Integer>() {{
       put("car", 20000);
       put("truck", 30000);
       put("bus", 0);
       put("container", 50000);
    }};
    private Integer _totalTax = 0;
    private int _instance = 0;
    private Socket _socket;

    public TaxCalculator(String name, int parallelism, GroupingStrategy groupingStrategy) throws IOException {
        super(name, parallelism, groupingStrategy);
    }

    @Override
    public void setupInstance(int instance) {
        this._instance = instance;
        try {
            if (HttpRequest.requestOpenSocket(9992, "client", "admin") == HttpURLConnection.HTTP_OK) {
                this._socket = new Socket("localhost", 9992);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println(_socket);
    }

    @Override
    public void apply(Event event, List<Event> eventCollector) {
        VehicleInfor vehicleInfor = ((VehicleEvent)event).getData();
        String vehicle = vehicleInfor.type;
        Integer tax = this._taxPrice.get(vehicle);
        System.out.println("Tax: " + tax);
        try {
            PrintWriter out = new PrintWriter(_socket.getOutputStream(), true);
            out.println(String.format("%s %s %s %s",
                    vehicleInfor.type,
                    vehicleInfor.carLicensePlates,
                    vehicleInfor.ownerPhoneNumber,
                    tax
            ));
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("Error: UnknownHostException");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: IOException");
            System.exit(0);
        }
    }
}
