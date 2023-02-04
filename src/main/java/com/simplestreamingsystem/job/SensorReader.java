package com.simplestreamingsystem.job;

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
    private Socket _socket;
    private final int _portBase;

    public SensorReader(String name, int parallelism, int port) {
        super(name, parallelism);
        this._portBase = port;
    }

    @Override
    public void setupInstance(int instance) {
        this._instance = instance;
        setupSocketReader(_portBase + _instance);
    }

    @Override
    public void getEvents(List<Event> eventCollector) {
        try {
            String vehicle = _reader.readLine();
            if (vehicle == null) {
                System.exit(0);
            }
            eventCollector.add(new VehicleEvent(vehicle));
            System.out.println("");
            System.out.println("SensorReader --> " + vehicle);
        } catch (IOException e) {
            System.out.println("Failed to read input: " + e);
        }
    }

    private void setupSocketReader(int port) {
        try {
            _socket = new Socket("localhost", port);
            InputStream inputStream = _socket.getInputStream();
            _reader = new BufferedReader(new InputStreamReader(inputStream));
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
