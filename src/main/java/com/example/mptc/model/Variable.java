package com.example.mptc.model;

public class Variable {

    private final String letter;
    private final int index;

    public Variable(String letter, int index) {
        this.letter = letter;
        this.index = index;
    }

    public Variable(String letter) {
        this.letter = letter;
        this.index = -1;
    }

    public String toString() {
        return letter + (index < 0 ? "" : index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Variable variable = (Variable) o;

        if (index != variable.index) return false;
        return letter.equals(variable.letter);

    }

    @Override
    public int hashCode() {
        int result = letter.hashCode();
        result = 31 * result + index;
        return result;
    }
}
