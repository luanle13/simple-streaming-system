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
    private final BufferedReader _reader;

    public SensorReader(String name, int port) {
        super(name);
        _reader = setupSocketReader(port);
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

    private BufferedReader setupSocketReader(int port) {
        try {
            Socket socket = new Socket("localhost", port);
            InputStream inputStream = socket.getInputStream();
            return new BufferedReader(new InputStreamReader(inputStream));
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }
}
