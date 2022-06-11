package com.example.mptc.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import static java.lang.Math.min;

@Data
@NoArgsConstructor
public class Element {
    private int delivery;
    private int value;

    public static int findMinElement(int a, int b) {
        return min(a,b);
    }
}
