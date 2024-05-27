package org.problemEditor.controller;

import org.jetbrains.annotations.NotNull;
import org.problemEditor.GraFlapProblemEditor;
import org.problemEditor.enums.PanelName;

public class ChooseController {
    private final GraFlapProblemEditor graFlapProblemEditor;

    public ChooseController(@NotNull GraFlapProblemEditor graFlapProblemEditor) {
        this.graFlapProblemEditor = graFlapProblemEditor;
        graFlapProblemEditor.setChooseViewController(this);
    }

    public void handleBackButton() {
        graFlapProblemEditor.navigateTo(PanelName.START);
    }

    public void handleAutomatonButton() {
        graFlapProblemEditor.navigateTo(PanelName.AUTOMATON);
    }

    public void handleGrammarButton() {
        graFlapProblemEditor.navigateTo(PanelName.GRAMMAR);
    }

    public void handleMachineButton() {
        graFlapProblemEditor.navigateTo(PanelName.MACHINE);
    }
}
