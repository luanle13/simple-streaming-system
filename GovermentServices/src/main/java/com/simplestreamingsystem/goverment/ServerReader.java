package com.simplestreamingsystem.goverment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.simplestreamingsystem.api.Event;
import com.simplestreamingsystem.api.Source;
import com.simplestreamingsystem.goverment.penalty.PenaltyEvent;
import com.simplestreamingsystem.goverment.penalty.PenaltyInfor;
import com.simplestreamingsystem.http_request.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerReader extends Source {
    private int _instance = 0;
    private BufferedReader _reader;
    private Socket _socket;
    private final int _portBase;

    public ServerReader(String name, int parallelism, int port) {
        super(name, parallelism);
        this._portBase = port;
    }

    @Override
    public void setupInstance(int instance) {
        this._instance = instance;
        setupSocketReader(_portBase + _instance);
    }

    private Map<String, String> ViPhamList = new HashMap<String, String>(){{
        put("48", "VuotDenDo");
        put("72", "ChayQuaTocDo");
    }};

    @Override
    public void getEvents(List<Event> eventCollector) {
        try {
            String inp  =_reader.readLine();
//            System.out.println(inp);
            String[] rawData = inp.split(" ", 5);

            /// EXAMPLE: car 48B1-48949 0931646221 20000
            ///     Loại xe -> Biển số -> SDT -> Tiền phí BOT từ BOT gửi về

            VehicleInfor vehicle = new VehicleInfor(rawData[0], rawData[1], rawData[2]);
            PenaltyInfor penaltyInfor = new PenaltyInfor(
                    ViPhamList.get(rawData[1]),
                    vehicle,
                    Integer.parseInt(rawData[3])
            );

            if (penaltyInfor == null) {
                System.exit(0);
            }
            eventCollector.add(new PenaltyEvent(penaltyInfor));

            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .enableComplexMapKeySerialization()
                    .create();

            System.out.println("------------------------------");
            System.out.println("SERVER READER ->");
            System.out.println(gson.toJson(penaltyInfor));
        } catch (IOException e) {
            System.out.println("Failed to read input: " + e);
        }
    }

    private void setupSocketReader(int port) {
        try {
            if (HttpRequest.requestOpenSocket(port, "server","admin") == HttpURLConnection.HTTP_OK) {
                Socket socket = new Socket("localhost", port);
                System.out.println(socket);
                InputStream inputStream = socket.getInputStream();
                _reader = new BufferedReader(new InputStreamReader(inputStream));
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
