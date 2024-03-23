package org.exerciseEditor.enums;

public enum PanelName {
    START("start"),
    CHOOSE("choose"),
    AUTOMATON("automaton"),
    GRAMMAR("grammar"),
    MACHINE("machine");

    private final String name;

    PanelName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
