package org.problemEditor.enums;

public enum GrammarType {
    NOT_SPECIFIED("Not Specified"),
    RIGHT_LINEAR("Right-Linear"),
    RIGHT_LINEAR_OR_CONTEXT_FREE("Right-Linear or Context-Free"),
    CONTEXT_FREE("Context-Free"),
    NON_CONTEXT_FREE("Non Context-Free");
    private final String label;
    GrammarType(String label) {
        this.label = label;
    }
    @Override
    public String toString() {
        return label;
    }
}
