package com.simplestreamingsystem.engine;

import com.simplestreamingsystem.api.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class JobStarter {
    private static final int QUEUE_SIZE = 64;

    private final Job _job;
    private final List<ComponentExecutor> _executorList = new ArrayList<ComponentExecutor>();
    private final List<Connection> _connectionList = new ArrayList<Connection>();

    public JobStarter(Job job) {
        this._job = job;
    }

    public void start() {
        setupComponentExecutors();
        setupConections();
        startProcesses();
        new WebServer(_job.getName(), _connectionList).start();
    }

    private void setupComponentExecutors() {
        for (Source source: _job.getSources()) {
            SourceExecutor executor = new SourceExecutor(source);
            _executorList.add(executor);
            traverseComponent(source, executor);
        }
    }

    private void setupConections() {
        for (Connection connection: _connectionList) {
            connectExecutors(connection);
        }
    }

    private void startProcesses() {
        Collections.reverse(_executorList);
        for (ComponentExecutor executor: _executorList) {
            executor.start();
        }
    }

    private void connectExecutors(Connection connection) {
        EventQueue intermediateQueue = new EventQueue(QUEUE_SIZE);
        connection.from.setOutgoingQueue(intermediateQueue);
        connection.to.setIncomingQueue(intermediateQueue);
    }

    private void traverseComponent(Component component, ComponentExecutor executor) {
        Stream stream = component.getOutgoingStream();

        for (Operator operator: stream.getAppliedOparators()) {
            OperatorExecutor operatorExecutor = new OperatorExecutor(operator);
            _executorList.add(operatorExecutor);
            _connectionList.add(new Connection(executor, operatorExecutor));
            traverseComponent(operator, operatorExecutor);
        }
    }
}
