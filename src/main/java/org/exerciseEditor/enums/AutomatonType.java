package org.exerciseEditor.enums;

public enum AutomatonType {
    NOT_SPECIFIED("Not Specified"),
    FINITE_STATE_AUTOMATON("Finite State Automaton"),
    PUSH_DOWN_AUTOMATON("Push-Down Automaton"),
    TURING_AUTOMATON("Turing Automaton");

    private final String label;

    AutomatonType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
