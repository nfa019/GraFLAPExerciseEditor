package org.exerciseEditor.controller;

import org.exerciseEditor.GraFlapExerciseEditor;
import org.exerciseEditor.enums.PanelName;

import javax.swing.*;

public abstract class MVCController {
    protected GraFlapExerciseEditor graFlapExerciseEditor;

    public MVCController(GraFlapExerciseEditor graFlapExerciseEditor) {
        this.graFlapExerciseEditor = graFlapExerciseEditor;
    }

    public void handleCancelButton() {
        Object[] options = {"Yes", "No"};
        int option = JOptionPane.showOptionDialog(null, "Do you really want to cancel and discard all changes?",
                "Discard changes?", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (option == JOptionPane.YES_OPTION) {
            // TODO: alle eingaben löschen
            graFlapExerciseEditor.navigateTo(PanelName.START);
        }
    }

    public void handleBackButton() {
        graFlapExerciseEditor.navigateTo(PanelName.CHOOSE);
    }

    public abstract void initWithNewModel();

    public abstract void initWithExistingModel(String pathName);

}
