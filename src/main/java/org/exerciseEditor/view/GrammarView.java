package org.exerciseEditor.view;

import org.exerciseEditor.model.BaseModel;
import org.exerciseEditor.model.GrammarModel;
import org.jetbrains.annotations.NotNull;
import org.exerciseEditor.controller.GrammarController;
import org.exerciseEditor.enums.GrammarType;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.ArrayList;

public class GrammarView extends BaseView {
    private JTextField languageTextField;
    private JCheckBox randomizeLowerCaseCheckBox;
    private JPanel MainPanel;
    private JPanel OptionalPanel;
    private JComboBox<GrammarType> typeComboBox;
    private JTextArea generatedWordsTextArea;
    private JTextArea nonGeneratedWordsTextArea;
    private JTextField titleTextField;
    private JTextArea descriptionTextArea;
    private JButton backButton;
    private JButton createButton;
    private JButton saveDraftButton;
    private JButton previewButton;
    private JButton cancelButton;
    private GrammarController grammarViewController;
    public GrammarView() {
    }

    @Override
    public void update(BaseModel model) {
        // TODO: Update-Methode erstellen!
        if (model instanceof GrammarModel) {
            updateCheckBoxes((GrammarModel) model);
        }
    }

    private void updateCheckBoxes(@NotNull GrammarModel model) {
        randomizeLowerCaseCheckBox.setSelected(model.isRandomizeLowerCase());
    }

    @Override
    public void init() {
        MainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        OptionalPanel.setBorder(new CompoundBorder(new LineBorder(new Color(51, 144, 218), 2),
                new EmptyBorder(5, 5, 5, 5)));
        super.textComponents = this.createTextComponentsList();
        initActionListeners();
    }

    @Override
    protected ArrayList<JTextComponent> createTextComponentsList() {
        ArrayList<JTextComponent> textComponentsList = new ArrayList<>();
        textComponentsList.add(titleTextField);
        textComponentsList.add(descriptionTextArea);
        textComponentsList.add(languageTextField);
        textComponentsList.add(generatedWordsTextArea);
        textComponentsList.add(nonGeneratedWordsTextArea);
        return null;
    }

    private void initActionListeners() {
        backButton.addActionListener(e -> grammarViewController.handleBackButton());
        cancelButton.addActionListener(e -> grammarViewController.handleCancelButton());
        saveDraftButton.addActionListener(e -> grammarViewController.handleSaveDraftButton());
        previewButton.addActionListener(e -> grammarViewController.handlePreviewButton());
        createButton.addActionListener(e -> grammarViewController.handleCreateButton());
    }

    public void setGrammarViewController(GrammarController grammarViewController) {
        this.grammarViewController = grammarViewController;
    }

    public JTextField getLanguageTextField() {
        return languageTextField;
    }

    public JCheckBox getShouldTheLettersBeCheckBox() {
        return randomizeLowerCaseCheckBox;
    }

    public JComboBox<GrammarType> getTypeComboBox() {
        return typeComboBox;
    }

    public JTextArea getGeneratedWordsTextArea() {
        return generatedWordsTextArea;
    }

    public JTextArea getNonGeneratedWordsTextArea() {
        return nonGeneratedWordsTextArea;
    }

    public JTextField getTitleTextField() {
        return titleTextField;
    }

    public JTextArea getDescriptionTextArea() {
        return descriptionTextArea;
    }
    @Override
    public JPanel getMainPanel() {
        return MainPanel;
    }
}
