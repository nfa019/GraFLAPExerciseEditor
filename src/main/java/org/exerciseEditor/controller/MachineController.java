package org.exerciseEditor.controller;

import org.exerciseEditor.GraFlapExerciseEditor;
import org.exerciseEditor.model.MachineModel;
import org.exerciseEditor.view.MachineView;
import org.jetbrains.annotations.NotNull;

public class MachineController extends MVCController {
    private MachineModel machineUIData;
    private final MachineView machineView;

    public MachineController(GraFlapExerciseEditor graFlapExerciseEditor, @NotNull MachineModel machineUIData) {
        super(graFlapExerciseEditor);
        this.machineUIData = machineUIData;
        this.machineView = graFlapExerciseEditor.getMachineView();
        machineView.setMachineViewController(this);
    }

    @Override
    public void initWithNewModel() {

    }

    @Override
    public void initWithExistingModel(String pathName) {

    }

    public void handleSaveDraftButton() {
        // TODO: Funktionalität hinzufügen!
        System.out.println("Muss noch gemacht werden!!!");
    }

    public void handlePreviewButton() {
        // TODO: Funktionalität hinzufügen!
        System.out.println("Muss noch gemacht werden!!!");
    }

    public void handleCreateButton() {
        // TODO: Funktionalität hinzufügen!
        System.out.println("Muss noch gemacht werden!!!");
    }

    private MachineModel buildMachineModel() {
        return new MachineModel.Builder()
                .title(machineView.getTitleText())
                .description(machineView.getDescriptionText())
                .input(machineView.getInputText())
                .output(machineView.getOutputText())
                .type(machineView.getMachineType())
                .determinism(machineView.getDeterminism())
                .jffPathName(machineView.getChosenFileText())
                .build();
    }
}
