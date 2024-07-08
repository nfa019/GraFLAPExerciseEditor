package org.problemEditor.controller;

import org.jetbrains.annotations.Nullable;
import org.problemEditor.GraFlapProblemEditor;
import org.problemEditor.enums.PanelName;
import org.problemEditor.model.GrammarModel;
import org.problemEditor.util.MyFileHandler;
import org.problemEditor.util.XMLConverter;
import org.problemEditor.view.GrammarView;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.nio.file.Path;
import java.util.Locale;

public class GrammarController extends MVCController {
    private GrammarModel grammarModel;
    private final GrammarView grammarView;

    public GrammarController(GraFlapProblemEditor graFlapProblemEditor) {
        super(graFlapProblemEditor);
        this.grammarModel = new GrammarModel.Builder().build();
        this.grammarView = graFlapProblemEditor.getGrammarView();
        grammarView.setGrammarViewController(this);
    }

    @Override
    public void initWithNewModel() {
        grammarView.update(grammarModel);
    }

    @Override
    public void initWithExistingModel(String pathName) {
        grammarModel = XMLConverter.convertXmlToGrammar(pathName);
        grammarView.update(grammarModel);
    }


    public void handleSaveDraftButton() {
        grammarModel = buildNewGrammarModel();
        String saveGrammar = saveGrammar();
        if (saveGrammar != null) {
            grammarView.showSuccessMessage("The entries were successfully saved in an XML file");
            graFlapProblemEditor.navigateTo(PanelName.START);
        }
    }

    public void handleCancelButton() {
        int option = grammarView.showOptionDialog("Discard changes?", "Do you really want to cancel and discard all" +
                " changes?");
        if (option == JOptionPane.YES_OPTION) {
            grammarModel = new GrammarModel.Builder().build();
            graFlapProblemEditor.navigateTo(PanelName.START);
        }
    }

    public void handlePreviewButton() {
        if (checkInputs()) {
            grammarModel = buildNewGrammarModel();
            Locale loc = grammarModel.getChosenLanguage();
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
                XMLConverter.createGrammarTaskFile(grammarModel, tempFilePath);
                lc2mdl.Main.convertLCtoMoodle(new File(tempFilePath), new File(tempFileDirectory), 1, "",options);
                MyFileHandler.showPreview(tempDir);
            }
        }
    }

    public void handleCreateButton() {
        if (checkInputs()) {
            grammarModel = buildNewGrammarModel();
            Locale loc = grammarModel.getChosenLanguage();
            String options = "";
            if (loc.equals(Locale.GERMAN)){
                options += "--language=de";
            }else{
                options += "--language=en";
            }
            String filePath = saveGrammar();
            if (filePath != null) {
                File directory = getDirectoryPath(filePath);
                File createdFile = new File(filePath);
                lc2mdl.Main.convertLCtoMoodle(createdFile, directory, 20, "", options);
                grammarView.showSuccessMessage("The task file was successfully created");
                graFlapProblemEditor.navigateTo(PanelName.START);
            }
        }
    }

    private GrammarModel buildNewGrammarModel() {
        return new GrammarModel.Builder()
                .title(grammarView.getTitleText())
                .description(grammarView.getDescriptionText())
                .language(grammarView.getLanguageText())
                .generatedWords(grammarView.getGeneratedWordsText())
                .nonGeneratedWords(grammarView.getNonGeneratedWordsText())
                .sampleSolution(grammarView.getSampleSolutionText())
                .type(grammarView.getSelectedGrammarType())
                .randomizeLowerCase(grammarView.isRandomizeLowerCaseIsSelected())
                .chosenLanguage(grammarView.isGermanSelected() ? Locale.GERMAN : Locale.ENGLISH)
                .build();
    }

    private @Nullable String saveGrammar() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Task File (*.problem)", "problem");
        String filePath = MyFileHandler.chooseFilePath(filter);
        if (filePath != null) {
            if (!filePath.toLowerCase().endsWith(".problem")) {
                filePath = filePath + ".problem";
            }
            XMLConverter.createGrammarTaskFile(grammarModel, filePath);
            return filePath;
        }
        return null;
    }

    private boolean checkInputs() {
        // Check entries here
        boolean check = false;

        if (grammarView.checkMissingInputs()){
            if (grammarView.checkDisjointFields(grammarView.getGeneratedWordsText(),grammarView.getNonGeneratedWordsText())){
                if (grammarView.checkWordsAlphabet(grammarView.getLanguageText(),grammarView.getGeneratedWordsText())){
                    check = true;
                }else{
                    grammarView.showErrorMessage("Generated words should be build only from chars from the alphabet.");
                }
            }else{
                grammarView.showErrorMessage("Generated and non-generated words should be disjoint.");
            }
        }else{
            grammarView.showValidationError();
            grammarView.highlightEmptyFields();
        }

        return check ;
    }

}
