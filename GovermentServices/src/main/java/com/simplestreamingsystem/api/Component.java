package com.simplestreamingsystem.api;

import java.io.Serializable;

public class Component implements Serializable {
    private String name;
    private int parallelism;
    private Stream outgoingStream = new Stream();

    public Component(String name, int parallelism) {
        this.name = name;
        this.parallelism = parallelism;
    }

    public String getName() {
        return name;
    }
    public int getParallelism() {
        return parallelism;
    }

    public Stream getOutgoingStream() {
        return outgoingStream;
    }
}
