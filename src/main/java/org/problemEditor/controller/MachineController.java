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
            Locale loc = machineModel.getChosenLanguage();
            String options = "";
            if (loc.equals(Locale.GERMAN)){
                options += "--language=de";
            }else{
                options += "--language=en";
            }
            Path tempDir = MyFileHandler.createTempDirectory();
            if (tempDir != null) {
                String tempFilePath = tempDir + "/temp.problem";
                String tempFileDirectory = tempDir + "/temp";
                XMLConverter.createMachineTaskFile(machineModel, tempFilePath);
                lc2mdl.Main.convertLCtoMoodle(new File(tempFilePath), new File(tempFileDirectory), 1, "",options);
                MyFileHandler.showPreview(tempDir);
            }
        }
    }

    public void handleCreateButton() {
        if (checkInputs()) {
            machineModel = buildNewMachineModel();
            Locale loc = machineModel.getChosenLanguage();
            String options = "";
            if (loc.equals(Locale.GERMAN)){
                options += "--language=de";
            }else{
                options += "--language=en";
            }
            String filePath = saveMachine();
            if (filePath != null) {
                File directory = getDirectoryPath(filePath);
                File createdFile = new File(filePath);
                lc2mdl.Main.convertLCtoMoodle(createdFile, directory, 20, "",options);
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
                .sampleSolution(machineView.getSampleSolutionText())
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

    private boolean checkNumberInOutput(){
        String inString = machineView.getInputText();
        String outString = machineView.getOutputText();
        if (inString.isEmpty()){
            return false;
        } else if (outString.isEmpty()) {
            return true;
        } else {
            inString = inString.replaceAll("\\n", ",");
            inString = inString.replaceAll("\\s", "");
            outString = outString.replaceAll("\\n", ",");
            outString = outString.replaceAll("\\s", "");
            String[] inWords = inString.split(",");
            String[] outWords = outString.split(",");
            if (inWords.length == outWords.length){
                return true;
            } else {
                return false;
            }
        }
    }

    private boolean checkInputs() {
        boolean check = false;
        // Check entries here
        if (machineView.checkMissingInputs()){
            if (checkNumberInOutput()){
                check = true;
            }else{
                machineView.showErrorMessage("You need the same number of input and output words !");
            }
        }else{
            machineView.showValidationError();
            machineView.highlightEmptyFields();
        }

        return check;
    }

    public void handleChooseFileButton() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JFF File (*.jff)", "jff");
        String filePath = MyFileHandler.chooseFilePath(filter);
        if (filePath != null) {
            machineView.setChosenFileTextField(filePath);
        }
    }
}
