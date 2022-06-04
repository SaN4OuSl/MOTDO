package com.example.mptc.model;

import java.util.List;

public class Input {
    private final CostFunction costFunction;
    private final List<Limitation> limitations;

    public Input(CostFunction costFunction, List<Limitation> limitation) {
        this.costFunction = costFunction;

        this.limitations = limitation;
    }

    public CostFunction getCostFunction() {
        return costFunction;
    }

    public List<Limitation> getLimitations() {
        return limitations;
    }

    public Limitation getLimitation(int index) {
        return limitations.get(index);
    }

    public int getLimitationCount() {
        return limitations.size();
    }
}
