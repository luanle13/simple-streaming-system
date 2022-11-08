package com.simplestreamingsystem.api;

public class Component {
    private String name;
    private Stream outgoingStream = new Stream();

    public Component(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Stream getOutgoingStream() {
        return outgoingStream;
    }
}
