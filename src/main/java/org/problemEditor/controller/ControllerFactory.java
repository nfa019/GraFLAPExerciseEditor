package org.problemEditor.controller;

import org.problemEditor.GraFlapProblemEditor;
import org.problemEditor.enums.ControllerName;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class ControllerFactory {
    private AutomatonController automatonViewController;
    private GrammarController grammarViewController;
    private MachineController machineViewController;


    public void createController(GraFlapProblemEditor graFlapProblemEditor) {
        automatonViewController = new AutomatonController(graFlapProblemEditor);
        grammarViewController = new GrammarController(graFlapProblemEditor);
        machineViewController = new MachineController(graFlapProblemEditor);
        HashMap<ControllerName, MVCController> allUIDataViewController = createUIDataControllerMap();
        new ChooseController(graFlapProblemEditor);
        new StartController(graFlapProblemEditor, allUIDataViewController);
    }

    private @NotNull HashMap<ControllerName, MVCController> createUIDataControllerMap() {
        HashMap<ControllerName, MVCController> allUIDataViewController = new HashMap<>();
        allUIDataViewController.put(ControllerName.AUTOMATON_VIEW_CONTROLLER, automatonViewController);
        allUIDataViewController.put(ControllerName.GRAMMAR_VIEW_CONTROLLER, grammarViewController);
        allUIDataViewController.put(ControllerName.MACHINE_VIEW_CONTROLLER, machineViewController);
        return allUIDataViewController;
    }
}
