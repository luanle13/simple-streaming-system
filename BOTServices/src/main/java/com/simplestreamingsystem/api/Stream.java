package com.simplestreamingsystem.api;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Stream implements Serializable {
    private final Set<Operator> operatorSet = new HashSet<Operator>();

    public Stream applyOperator(Operator operator) {
        if (operatorSet.contains(operator)) {
            throw new RuntimeException("Operator " + operator.getName() + " is added to job twice!");
        } else {
            operatorSet.add(operator);
        }
        return operator.getOutgoingStream();
    }

    public Collection<Operator> getAppliedOparators() {
        return operatorSet;
    }
}
