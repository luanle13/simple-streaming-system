package com.simplestreamingsystem.api;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Job {
    private final String name;
    private final Set<Source> sourceSet = new HashSet<Source>();

    public Job(String jobName) {
        this.name = jobName;
    }

    public Stream addSource(Source source) {
        if (sourceSet.contains(source)) {
            throw new RuntimeException("Source " + source.getName() + " is added to job twice!");
        } else {
            sourceSet.add(source);
        }
        return source.getOutgoingStream();
    }

    public String getName() {
        return name;
    }

    public Collection<Source> getSources() {
        return sourceSet;
    }
}
