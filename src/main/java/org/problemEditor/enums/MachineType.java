package org.problemEditor.enums;

public enum MachineType {
    MEALY("Mealy"),
    MOORE("Moore"),
    TURING("Turing")
    ;
    private final String label;

    MachineType(String label) {
        this.label = label;
    }
    @Override
    public String toString() {
        return label;
    }
}
