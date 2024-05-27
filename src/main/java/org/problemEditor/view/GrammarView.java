package org.problemEditor.view;

import org.problemEditor.model.GrammarModel;
import org.jetbrains.annotations.NotNull;
import org.problemEditor.controller.GrammarController;
import org.problemEditor.enums.GrammarType;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

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
    private JCheckBox englishCheckBox;
    private JCheckBox germanCheckBox;
    private GrammarController grammarViewController;

    public GrammarView() {
        super();
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
        return textComponentsList;
    }

    private void initActionListeners() {
        typeComboBox.setModel(new DefaultComboBoxModel<>(GrammarType.values()));

        ItemListener germanOrEnglishCheckboxListener = toggleLanguageCheckbox(germanCheckBox, englishCheckBox);

        germanCheckBox.addItemListener(germanOrEnglishCheckboxListener);
        englishCheckBox.addItemListener(germanOrEnglishCheckboxListener);

        backButton.addActionListener(e -> grammarViewController.handleBackButton());
        cancelButton.addActionListener(e -> grammarViewController.handleCancelButton());
        saveDraftButton.addActionListener(e -> grammarViewController.handleSaveDraftButton());
        previewButton.addActionListener(e -> grammarViewController.handlePreviewButton());
        createButton.addActionListener(e -> grammarViewController.handleCreateButton());
    }

    public void update(GrammarModel model) {
        updateCheckBoxes(model);
        updateTextComponents(model);
        typeComboBox.setSelectedItem(model.getType());
    }

    private void updateCheckBoxes(@NotNull GrammarModel model) {
        if (Objects.equals(model.getChosenLanguage(), Locale.GERMAN)) {
            germanCheckBox.setSelected(true);
        } else if (Objects.equals(model.getChosenLanguage(), Locale.ENGLISH)) {
            englishCheckBox.setSelected(true);
        }
        randomizeLowerCaseCheckBox.setSelected(model.isRandomizeLowerCase());
    }

    private void updateTextComponents(@NotNull GrammarModel model) {
        titleTextField.setText(model.getTitle());
        descriptionTextArea.setText(model.getDescription());
        languageTextField.setText(model.getLanguage());
        generatedWordsTextArea.setText(model.getGeneratedWords());
        nonGeneratedWordsTextArea.setText(model.getNonGeneratedWords());
    }

    public void setGrammarViewController(GrammarController grammarViewController) {
        this.grammarViewController = grammarViewController;
    }

    public String getLanguageText() {
        return languageTextField.getText();
    }

    public boolean isRandomizeLowerCaseIsSelected() {
        return randomizeLowerCaseCheckBox.isSelected();
    }

    public GrammarType getSelectedGrammarType() {
        return (GrammarType) typeComboBox.getSelectedItem();
    }

    public String getGeneratedWordsText() {
        return generatedWordsTextArea.getText();
    }

    public String getNonGeneratedWordsText() {
        return nonGeneratedWordsTextArea.getText();
    }

    public String getTitleText() {
        return titleTextField.getText();
    }

    public String getDescriptionText() {
        return descriptionTextArea.getText();
    }

    public boolean isGermanSelected() {
        return germanCheckBox.isSelected();
    }

    @Override
    public JPanel getMainPanel() {
        return MainPanel;
    }
}
