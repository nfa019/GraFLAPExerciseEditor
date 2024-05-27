package org.problemEditor.controller;

import org.problemEditor.GraFlapProblemEditor;
import org.problemEditor.util.MyFileHandler;
import org.problemEditor.util.XMLConverter;
import org.problemEditor.enums.AutomatonState;
import org.problemEditor.enums.PanelName;
import org.problemEditor.model.AutomatonModel;
import org.problemEditor.view.*;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.nio.file.Path;
import java.util.Locale;

public class AutomatonController extends MVCController {
    private AutomatonModel automatonModel;
    private final AutomatonView automatonView;

    public AutomatonController(GraFlapProblemEditor graFlapProblemEditor) {
        super(graFlapProblemEditor);
        this.automatonView = graFlapProblemEditor.getAutomatonView();
        automatonView.setAutomatonViewController(this);
        automatonModel = new AutomatonModel.Builder().build();
    }

    @Override
    public void initWithNewModel() {
        automatonView.update(automatonModel);
    }

    @Override
    public void initWithExistingModel(String pathName) {
        automatonModel = XMLConverter.convertXmlToAutomaton(pathName);
        automatonView.update(automatonModel);
    }

    public void handleChooseFileButton() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JFF File (*.jff)", "jff");
        String filePath = MyFileHandler.chooseFilePath(filter);
        if (filePath != null) {
            automatonView.setChosenFileTextField(filePath);
        }
    }

    public void handleCancelButton() {
        int option = automatonView.showOptionDialog("Discard changes?", "Do you really want to cancel and discard all" +
                " changes?");
        if (option == JOptionPane.YES_OPTION) {
            automatonModel = new AutomatonModel.Builder().build();
            graFlapProblemEditor.navigateTo(PanelName.START);
        }
    }

    public void handleSaveDraftButton() {
        automatonModel = buildNewAutomatonModel();
        String saveAutomaton = saveAutomaton();
        if (saveAutomaton != null) {
            automatonView.showSuccessMessage("The entries were successfully saved in an XML file");
            graFlapProblemEditor.navigateTo(PanelName.START);
        }
    }

    public void handlePreviewButton() {
        if (checkInputs()) {
            automatonModel = buildNewAutomatonModel();
            Path tempDir = MyFileHandler.createTempDirectory();
            if (tempDir != null) {
                String tempFilePath = tempDir + "/temp.problem";
                String tempFileDirectory = tempDir + "/temp";
                XMLConverter.createAutomatonTaskFile(automatonModel, tempFilePath);
                lc2mdl.Main.convertLCtoMoodle(new File(tempFilePath), new File(tempFileDirectory), 20, "temp.problem");
                MyFileHandler.showPreview(tempDir);
            }
        } else {
            automatonView.showValidationError();
            automatonView.highlightEmptyFields();
        }
    }

    public void handleCreateButton() {
        if (checkInputs()) {
            automatonModel = buildNewAutomatonModel();
            String filePath = saveAutomaton();
            if (filePath != null) {
                File directory = getDirectoryPath(filePath);
                File createdFile = new File(filePath);
                lc2mdl.Main.convertLCtoMoodle(createdFile, directory, 20, filePath);
                automatonView.showSuccessMessage("The task file was successfully created");
                graFlapProblemEditor.navigateTo(PanelName.START);
            }
        } else {
            automatonView.showValidationError();
            automatonView.highlightEmptyFields();
        }
    }

    private AutomatonModel buildNewAutomatonModel() {
        return new AutomatonModel.Builder()
                .title(automatonView.getTitleText())
                .description(automatonView.getDescriptionText())
                .language(automatonView.getLanguageText())
                .acceptedWords(automatonView.getAcceptedWordsText())
                .nonAcceptedWords(automatonView.getNonAcceptedWordsText())
                .sampleSolution(automatonView.getSampleSolutionText())
                .state(automatonView.getRegexCheckBoxIsSelected() ? AutomatonState.REGEX : AutomatonState.GRAMMAR)
                .type(automatonView.getSelectedAutomatonType())
                .determinism(automatonView.getSelectedDeterminism())
                .jffPathName(automatonView.getChosenFileText())
                .randomizeLowerCase(automatonView.isRandomizeLowerCaseIsSelected())
                .partsDefinitionRequested(automatonView.isPartsDefinitionRequestedSelected())
                .automaticSolution(automatonView.isAutomaticSolutionSelected())
                .chosenLanguage(automatonView.isGermanSelected() ? Locale.GERMAN : Locale.ENGLISH)
                .build();
    }

    private @Nullable String saveAutomaton() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Task File (*.problem)", "problem");
        String filePath = MyFileHandler.chooseFilePath(filter);
        if (filePath != null) {
            if (!filePath.toLowerCase().endsWith(".problem")) {
                filePath = filePath + ".problem";
            }
            XMLConverter.createAutomatonTaskFile(automatonModel, filePath);
            return filePath;
        }
        return null;
    }

    private boolean checkInputs() {
        // Check entries here
        return automatonView.checkMissingInputs();
    }
}
