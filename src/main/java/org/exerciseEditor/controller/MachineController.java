package org.exerciseEditor.controller;

import org.exerciseEditor.GraFlapExerciseEditor;
import org.exerciseEditor.model.MachineModel;
import org.exerciseEditor.view.MachineView;

public class MachineController extends MVCController {
    private MachineModel machineUIData;
    private MachineView machineView;

    public MachineController(GraFlapExerciseEditor graFlapExerciseEditor, MachineModel machineUIData) {
        super(graFlapExerciseEditor);
        this.machineUIData = machineUIData;
        this.machineView = graFlapExerciseEditor.getMachineView();
//        machineUIData.setMachineViewController(this);
    }

    @Override
    public void initWithNewModel() {

    }

    @Override
    public void initWithExistingModel(String pathName) {

    }
}
