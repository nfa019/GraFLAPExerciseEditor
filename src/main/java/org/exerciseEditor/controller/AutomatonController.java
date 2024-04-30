package org.exerciseEditor.controller;

import lc2mdl.Main;
import org.exerciseEditor.GraFlapExerciseEditor;
import org.exerciseEditor.MyFileHandler;
import org.exerciseEditor.XMLConverter;
import org.exerciseEditor.enums.AutomatonState;
import org.exerciseEditor.enums.PanelName;
import org.exerciseEditor.model.AutomatonModel;
import org.exerciseEditor.view.*;
import org.jetbrains.annotations.Nullable;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class AutomatonController extends MVCController {
    private AutomatonModel automatonModel;
    private final AutomatonView automatonView;

    public AutomatonController(GraFlapExerciseEditor graFlapExerciseEditor, AutomatonModel automatonModel) {
        super(graFlapExerciseEditor);
        this.automatonModel = automatonModel;
        this.automatonView = graFlapExerciseEditor.getAutomatonView();
        automatonView.setAutomatonViewController(this);
    }

    public void initWithNewModel() {
        automatonView.update(automatonModel);
    }

    public void initWithExistingModel(String pathName) {
        automatonModel = XMLConverter.convertXmlToAutomaton(pathName);
        automatonView.update(automatonModel);
    }

    public void handleChooseFileButton() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JFF File (*.jff)", "jff");
        File startDirectory = new File("/home/sergio/Schreibtisch/Bachelorarbeit/von Frauke");
        String filePath = MyFileHandler.chooseFilePath(filter, startDirectory);
        if (filePath != null) {
            automatonView.setChosenFileTextField(filePath);
        }
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
        if (automatonView.allTextComponentsNotEmpty()) {
            this.automatonModel = buildNewAutomaton();
            String filePath = saveAutomaton();
            if (filePath != null) {
                File createdFile = new File(filePath);
                String fileName = createdFile.getName().replaceFirst("[.][^.]+$", "");
                File folder = new File("/home/sergio/Schreibtisch/Bachelorarbeit/TestProblems/ProForma/" + fileName);
                if (!folder.exists()) {
                    boolean success = folder.mkdirs();
                    if (success) {
                        System.out.println("Der Ordner wurde erfolgreich erstellt.");
//                        lc2mdl.Main.convertLCtoMoodle(createdFile, folder, 20, filePath);
                    } else {
                        System.out.println("Fehler beim Erstellen des Ordners.");
                    }
                } else {
                    System.out.println("Der Ordner existiert bereits.");
                }
            }
        } else {
            automatonView.showValidationError();
            automatonView.highlightEmptyFields();
        }
    }

    private AutomatonModel buildNewAutomaton() {
        return new AutomatonModel.Builder()
                .title(automatonView.getTitleText())
                .description(automatonView.getDescriptionText())
                .language(automatonView.getLanguageText())
                .acceptedWords(automatonView.getAcceptedWordsText())
                .nonAcceptedWords(automatonView.getNonAcceptedWordsText())
                .sampleSolution(automatonView.getSampleSolutionText())
                .state(automatonView.getRegexCheckBoxIsSelected() ? AutomatonState.REGEX : AutomatonState.GRAMMAR)
                .type(automatonView.getAutomatonType())
                .determinism(automatonView.getDeterminism())
                .jffPathName(automatonView.getChosenFileText())
                .randomizeLowerCase(automatonView.isRandomizeLowerCaseIsSelected())
                .partsDefinitionRequested(automatonView.isPartsDefinitionRequestedSelected())
                .automaticSolution(automatonView.isAutomaticSolutionSelected())
                .build();
    }

    private @Nullable String saveAutomaton() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Task File (*.problem)", "problem");
        File startDirectory = new File("/home/sergio/Schreibtisch/Bachelorarbeit/TestProblems"); // TODO: startDirectory raus nehmen
        String filePath = MyFileHandler.chooseFilePath(filter, startDirectory);
        if (filePath != null) {
//            if (!filePath.toLowerCase().endsWith(".problem")) {
//                filePath = filePath + ".problem";
//            }
            XMLConverter.createAutomatonProblemFile(automatonModel, filePath);
            automatonView.showSuccessMessage();
            graFlapExerciseEditor.navigateTo(PanelName.START);
            return filePath;
        }
        return null;
    }
}
