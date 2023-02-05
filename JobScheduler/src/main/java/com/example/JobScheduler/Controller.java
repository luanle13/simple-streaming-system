package com.example.JobScheduler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/api")
public class Controller {
    private String _password = "admin";
    private Map<String, ServerSocket> _mapServerSocket = new HashMap<String, ServerSocket>();
//    private volatile static String _message = null;
    private volatile static List<PrintWriter> _listWriter = new ArrayList<PrintWriter>();
    private volatile static int serverCounter = 0;
    private ServerSocket findServerSocketWithPort(int port) throws IOException {
        for (Map.Entry<String,ServerSocket> item : _mapServerSocket.entrySet()) {
            if (Integer.toString(port).equals(item.getKey())) {
                return item.getValue();
            }
        }
        ServerSocket newServerSocket = new ServerSocket(port);
        _mapServerSocket.put(Integer.toString(port), newServerSocket);
        return newServerSocket;
    }
    private boolean isPortInUse(int port) {
        for (Map.Entry<String, ServerSocket> item : _mapServerSocket.entrySet()) {
            if (Integer.toString(port).equals(item.getKey())) {
                return true;
            }
        }
        return false;
    }
    private int hashKey(int key) {
        return key % serverCounter;
    }
    @PostMapping("/")
    public ResponseEntity<String> handleCreateSocketServer(@RequestBody Authentication body) {
        if (!_password.equals(body.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            try {
                ServerSocket serverSocket = findServerSocketWithPort(body.getPort());
                System.out.println(_mapServerSocket);
                new Thread(() -> {
                    try {
                        Socket socket = serverSocket.accept();
                        if (body.getType().equals("client")) {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            while (true) {
                                String message = reader.readLine();
                                if (message != null) {
                                    int key = Integer.parseInt(message.split(" ")[1]);
                                    _listWriter.get(hashKey(key)).println(message);
                                    message = null;
                                }
                            }
                        }
                        if (body.getType().equals("server")) {
                            serverCounter++;
                            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                            _listWriter.add(writer);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        if (body.getType().equals("server") && isPortInUse(body.getPort())) {
                            serverCounter--;
                            _mapServerSocket.remove(serverSocket);
                        }
                    }
                }).start();
                return ResponseEntity.status(HttpStatus.OK).build();
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }
}
