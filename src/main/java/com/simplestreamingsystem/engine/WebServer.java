package com.simplestreamingsystem.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebServer {
    class Node extends HashMap<String, String> {
        public Node(String name) {
            super();
            this.put("name", name);
        }
    }

//    class Edge
}
