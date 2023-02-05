package com.simplestreamingsystem.engine;

public abstract class Process {
    private final Thread _thread;

    public Process() {
        this._thread = new Thread() {
            public void run() {
                while (runOnce());
            }
        };
    }

    public void start() {
        _thread.start();
    }

    abstract boolean runOnce();
}
