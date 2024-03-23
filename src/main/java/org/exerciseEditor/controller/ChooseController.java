package org.exerciseEditor.controller;

import org.exerciseEditor.GraFlapExerciseEditor;
import org.exerciseEditor.enums.PanelName;

public class ChooseController {
    private final GraFlapExerciseEditor graFlapExerciseEditor;

    public ChooseController(GraFlapExerciseEditor graFlapExerciseEditor) {
        this.graFlapExerciseEditor = graFlapExerciseEditor;
        graFlapExerciseEditor.setChooseViewController(this);
    }

    public void handleBackButton() {
        graFlapExerciseEditor.navigateTo(PanelName.START);
    }

    public void handleAutomatonButton() {
        graFlapExerciseEditor.navigateTo(PanelName.AUTOMATON);
    }

    public void handleGrammarButton() {
        graFlapExerciseEditor.navigateTo(PanelName.GRAMMAR);
    }

    public void handleMachineButton() {
        graFlapExerciseEditor.navigateTo(PanelName.MACHINE);
    }
}
