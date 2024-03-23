package org.exerciseEditor;

import org.exerciseEditor.enums.ControllerName;
import org.jetbrains.annotations.NotNull;
import org.exerciseEditor.controller.*;

import java.util.HashMap;

public class CreateController {
    private AutomatonController automatonViewController;
    private GrammarController grammarViewController;
    private MachineController machineViewController;


    public void createController(GraFlapExerciseEditor graFlapExerciseEditor, @NotNull CreateModels models) {
        automatonViewController = new AutomatonController(graFlapExerciseEditor, models.getAutomatonModel());
        grammarViewController = new GrammarController(graFlapExerciseEditor, models.getGrammarModel());
        machineViewController = new MachineController(graFlapExerciseEditor, models.getMachineModel());
        HashMap<ControllerName, MVCController> allUIDataViewController = createUIDataControllerMap();
        new ChooseController(graFlapExerciseEditor);
        new StartController(graFlapExerciseEditor, allUIDataViewController);
    }

    private @NotNull HashMap<ControllerName, MVCController> createUIDataControllerMap() {
        HashMap<ControllerName, MVCController> allUIDataViewController = new HashMap<>();
        allUIDataViewController.put(ControllerName.AUTOMATON_VIEW_CONTROLLER, automatonViewController);
        allUIDataViewController.put(ControllerName.GRAMMAR_VIEW_CONTROLLER, grammarViewController);
        allUIDataViewController.put(ControllerName.MACHINE_VIEW_CONTROLLER, machineViewController);
        return allUIDataViewController;
    }
}
