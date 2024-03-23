package org.exerciseEditor.enums;

public enum Determinism {
    NOT_SPECIFIED("Not Specified"),
    DETERMINISTIC("Deterministic"),
    NOT_DETERMINISTIC("Not-Deterministic");

    private final String label;

    Determinism(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
