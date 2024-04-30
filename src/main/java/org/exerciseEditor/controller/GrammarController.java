package org.exerciseEditor.controller;

import org.exerciseEditor.GraFlapExerciseEditor;
import org.exerciseEditor.model.GrammarModel;
import org.exerciseEditor.view.GrammarView;
import org.exerciseEditor.enums.GrammarType;

import javax.swing.*;

public class GrammarController extends MVCController {
    private GrammarModel grammarUIData;
    private final GrammarView grammarView;

    public GrammarController(GraFlapExerciseEditor graFlapExerciseEditor, GrammarModel grammarUIData) {
        super(graFlapExerciseEditor);
        this.grammarUIData = grammarUIData;
        this.grammarView = graFlapExerciseEditor.getGrammarView();
        grammarView.setGrammarViewController(this);
    }

    @Override
    public void initWithNewModel() {
        initGrammarView();
    }

    @Override
    public void initWithExistingModel(String pathName) {
        // TODO: xmlConverter einbinden!
//        initGrammarView();
    }

    private void initGrammarView() {
        initCheckbox();
        initComboBox();
        initTextComponents();
    }

    private void initComboBox() {
        grammarView.getTypeComboBox().setModel(new DefaultComboBoxModel<>(GrammarType.values()));
        grammarView.getTypeComboBox().setSelectedItem(grammarUIData.getType());
    }

    private void initTextComponents() {
        grammarView.getTitleTextField().setText(grammarUIData.getTitle());
        grammarView.getDescriptionTextArea().setText(grammarUIData.getDescription());
        grammarView.getLanguageTextField().setText(grammarUIData.getLanguage());
        grammarView.getGeneratedWordsTextArea().setText(grammarUIData.getGeneratedWords());
        grammarView.getNonGeneratedWordsTextArea().setText(grammarUIData.getNonGeneratedWords());
    }

    private void initCheckbox() {
        grammarView.getShouldTheLettersBeCheckBox().setSelected(grammarUIData.isRandomizeLowerCase());
    }

    public void handleSaveDraftButton() {
    }

    public void handlePreviewButton() {
    }

    public void handleCreateButton() {
    }
}
