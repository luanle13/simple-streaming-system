package com.simplestreamingsystem.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class WebServer {
    class Node extends HashMap<String, String> {
        public Node(String name) {
            super();
            this.put("name", name);
        }
    }

    class Edge extends HashMap<String, String> {
        public Edge(Node from, Node to) {
            super();
            this.put("from", from.get("name"));
            this.put("to", to.get("name"));
        }
    }

    private final String _jobName;
    private final List<Node> _sources = new ArrayList<Node>();
    private final List<Node> _operators = new ArrayList<Node>();
    private final List<Edge> _edges = new ArrayList<Edge>();

    public WebServer(final String jobName, final List<Connection> connectionList) {
        this._jobName = jobName;

        Map<Node, Integer> incomingCountMap = new HashMap<Node, Integer>();

        for (Connection connection: connectionList) {
            Node from = new Node(connection.from.getComponent().getName());
            Node to = new Node(connection.to.getComponent().getName());

            Integer count = incomingCountMap.getOrDefault(to, 0);
            incomingCountMap.put(from, count);
            count = incomingCountMap.getOrDefault(to, 0);
            incomingCountMap.put(to, count + 1);

            _edges.add(new Edge(from, to));
        }
        for (Node node: incomingCountMap.keySet()) {
            if (incomingCountMap.get(node) == 0) {
                _sources.add(node);
            } else {
                _operators.add(node);
            }
        }
    }

    public void start() {
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/public");
        }).start(7000);
        app.get("/", ctx -> indexHandler(ctx));
        app.get("/plan.json", ctx -> planHandler(ctx));
    }

    private void indexHandler(Context ctx) {
        StringBuilder graph = new StringBuilder();
        for (Edge edge: _edges) {
            String from = edge.get("from");
            String to = edge.get("to");
            graph.append(String.format(
                    "%s(%s) --> %s(%s)\n",
                    from.replaceAll("\\s", ""),
                    from,
                    to.replaceAll("\\s", ""),
                    to
            ));
        }
        ctx.render("index.twig", Map.of("job", _jobName, "graph", graph));
    }

    private void planHandler(Context ctx) {
        Map<String, Object> plan = new HashMap<String, Object>();
        plan.put("name", _jobName);
        plan.put("sources", _sources);
        plan.put("operators", _operators);
        plan.put("edges", _edges);
        ctx.json(plan);
    }
}
