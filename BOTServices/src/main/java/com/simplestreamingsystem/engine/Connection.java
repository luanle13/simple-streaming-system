package com.simplestreamingsystem.engine;

public class Connection {
    public final ComponentExecutor from;
    public final OperatorExecutor to;

    public Connection(ComponentExecutor from, OperatorExecutor to) {
        this.from = from;
        this.to = to;
    }
}
