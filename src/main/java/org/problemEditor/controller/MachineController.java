package org.problemEditor.controller;

import org.jetbrains.annotations.Nullable;
import org.problemEditor.GraFlapProblemEditor;
import org.problemEditor.enums.PanelName;
import org.problemEditor.model.MachineModel;
import org.problemEditor.util.MyFileHandler;
import org.problemEditor.util.XMLConverter;
import org.problemEditor.view.MachineView;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.nio.file.Path;
import java.util.Locale;

public class MachineController extends MVCController {
    private MachineModel machineModel;
    private final MachineView machineView;

    public MachineController(GraFlapProblemEditor graFlapProblemEditor) {
        super(graFlapProblemEditor);
        this.machineView = graFlapProblemEditor.getMachineView();
        machineView.setMachineViewController(this);
        machineModel = new MachineModel.Builder().build();
    }

    @Override
    public void initWithNewModel() {
        machineView.update(machineModel);
    }

    @Override
    public void initWithExistingModel(String pathName) {
        machineModel = XMLConverter.convertXmlToMachine(pathName);
        machineView.update(machineModel);
    }

    public void handleSaveDraftButton() {
        machineModel = buildNewMachineModel();
        String saveMachine = saveMachine();
        if (saveMachine != null) {
            machineView.showSuccessMessage("The entries were successfully saved in an XML file");
            graFlapProblemEditor.navigateTo(PanelName.START);
        }
    }

    public void handlePreviewButton() {
        if (checkInputs()) {
            machineModel = buildNewMachineModel();
            Path tempDir = MyFileHandler.createTempDirectory();
            if (tempDir != null) {
                String tempFilePath = tempDir + "/temp.problem";
                String tempFileDirectory = tempDir + "/temp";
                XMLConverter.createMachineTaskFile(machineModel, tempFilePath);
                lc2mdl.Main.convertLCtoMoodle(new File(tempFilePath), new File(tempFileDirectory), 20, "temp.problem");
                MyFileHandler.showPreview(tempDir);
            }
        } else {
            machineView.showValidationError();
            machineView.highlightEmptyFields();
        }
    }

    public void handleCreateButton() {
        if (checkInputs()) {
            machineModel = buildNewMachineModel();
            String filePath = saveMachine();
            if (filePath != null) {
                File directory = getDirectoryPath(filePath);
                File createdFile = new File(filePath);
                lc2mdl.Main.convertLCtoMoodle(createdFile, directory, 20, filePath);
                machineView.showSuccessMessage("The task file was successfully created");
                graFlapProblemEditor.navigateTo(PanelName.START);
            }
        }
    }

    private MachineModel buildNewMachineModel() {
        return new MachineModel.Builder()
                .title(machineView.getTitleText())
                .description(machineView.getDescriptionText())
                .input(machineView.getInputText())
                .output(machineView.getOutputText())
                .jffPathName(machineView.getChosenFileText())
                .type(machineView.getSelectedMachineType())
                .determinism(machineView.getSelectedDeterminism())
                .chosenLanguage(machineView.isGermanSelected() ? Locale.GERMAN : Locale.ENGLISH)
                .build();
    }

    public void handleCancelButton() {
        int option = machineView.showOptionDialog("Discard changes?", "Do you really want to cancel and discard all" +
                " changes?");
        if (option == JOptionPane.YES_OPTION) {
            machineModel = new MachineModel.Builder().build();
            graFlapProblemEditor.navigateTo(PanelName.START);
        }
    }

    private @Nullable String saveMachine() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Task File (*.problem)", "problem");
        String filePath = MyFileHandler.chooseFilePath(filter);
        if (filePath != null) {
            if (!filePath.toLowerCase().endsWith(".problem")) {
                filePath = filePath + ".problem";
            }
            XMLConverter.createMachineTaskFile(machineModel, filePath);
            return filePath;
        }
        return null;
    }

    private boolean checkInputs() {
        // Check entries here
        return machineView.checkMissingInputs();
    }

    public void handleChooseFileButton() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JFF File (*.jff)", "jff");
        String filePath = MyFileHandler.chooseFilePath(filter);
        if (filePath != null) {
            machineView.setChosenFileTextField(filePath);
        }
    }
}
