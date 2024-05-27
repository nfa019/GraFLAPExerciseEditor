package org.problemEditor.view;

import org.problemEditor.enums.AutomatonState;
import org.problemEditor.enums.AutomatonType;
import org.problemEditor.enums.Determinism;
import org.problemEditor.model.AutomatonModel;
import org.jetbrains.annotations.NotNull;
import org.problemEditor.controller.AutomatonController;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class AutomatonView extends BaseView {
    private JTextField languageTextField;
    private JTextField chosenFileTextField;
    private JTextField titleTextField;
    private JTextArea sampleSolutionTextArea;
    private JTextArea acceptedWordsTextArea;
    private JTextArea nonAcceptedWordsTextArea;
    private JTextArea descriptionTextArea;
    private JCheckBox grammarCheckBox;
    private JCheckBox regexCheckBox;
    private JCheckBox randomizeLowerCaseCheckBox;
    private JCheckBox automaticSolutionCheckBox;
    private JCheckBox germanCheckBox;
    private JCheckBox englishCheckBox;
    private JCheckBox partsDefinitionRequestedCheckBox;
    private JComboBox<AutomatonType> typesComboBox;
    private JComboBox<Determinism> determinismComboBox;
    private JButton chooseFileButton;
    private JButton backButton;
    private JButton createButton;
    private JButton saveDraftButton;
    private JButton previewButton;
    private JButton cancelButton;
    private JPanel OptionalPanel;
    private JPanel MainPanel;
    private AutomatonController automatonViewController;

    public AutomatonView() {
        super();
    }

    @Override
    public void init() {
        MainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        OptionalPanel.setBorder(new CompoundBorder(new LineBorder(new Color(51, 144, 218), 2),
                new EmptyBorder(5, 5, 5, 5)));
        super.textComponents = this.createTextComponentsList();
        initActionListeners();
        initMouseListener();
        initComboBoxes();
    }

    @Override
    protected ArrayList<JTextComponent> createTextComponentsList() {
        ArrayList<JTextComponent> textComponentsList = new ArrayList<>();
        textComponentsList.add(titleTextField);
        textComponentsList.add(descriptionTextArea);
        textComponentsList.add(languageTextField);
        textComponentsList.add(chosenFileTextField);
        return textComponentsList;
    }

    private void initActionListeners() {
        ItemListener grammarOrRegexCheckBoxListener = e -> {
            if (e.getSource() == grammarCheckBox) {
                regexCheckBox.setSelected(!grammarCheckBox.isSelected());
            } else if (e.getSource() == regexCheckBox) {
                grammarCheckBox.setSelected(!regexCheckBox.isSelected());
            }
        };

        ItemListener germanOrEnglishCheckboxListener = toggleLanguageCheckbox(germanCheckBox, englishCheckBox);

        grammarCheckBox.addItemListener(grammarOrRegexCheckBoxListener);
        regexCheckBox.addItemListener(grammarOrRegexCheckBoxListener);
        germanCheckBox.addItemListener(germanOrEnglishCheckboxListener);
        englishCheckBox.addItemListener(germanOrEnglishCheckboxListener);

        chooseFileButton.addActionListener(e -> {
            automatonViewController.handleChooseFileButton();
            chosenFileTextField.setBorder(UIManager.getBorder("TextField.border"));
        });
        backButton.addActionListener(e -> automatonViewController.handleBackButton());
        cancelButton.addActionListener(e -> automatonViewController.handleCancelButton());
        saveDraftButton.addActionListener(e -> automatonViewController.handleSaveDraftButton());
        previewButton.addActionListener(e -> automatonViewController.handlePreviewButton());
        createButton.addActionListener(e -> automatonViewController.handleCreateButton());
    }

    private void initMouseListener() {
        for (JTextComponent component : textComponents) {
            if (!component.equals(chosenFileTextField)) {
                component.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        component.setBorder(UIManager.getBorder("TextField.border"));
                    }
                });
            }
        }
    }

    private void initComboBoxes() {
        typesComboBox.setModel(new DefaultComboBoxModel<>(AutomatonType.values()));
        determinismComboBox.setModel(new DefaultComboBoxModel<>(Determinism.values()));
    }

    public void setAutomatonViewController(AutomatonController automatonViewController) {
        this.automatonViewController = automatonViewController;
    }

    public void setChosenFileTextField(String absolutePath) {
        this.chosenFileTextField.setText(absolutePath);
    }

    public void update(AutomatonModel model) {
        updateCheckBoxes(model);
        updateComboBoxes(model);
        updateTextComponents(model);
    }

    private void updateCheckBoxes(@NotNull AutomatonModel model) {
        if (Objects.equals(model.getState(), AutomatonState.REGEX)) {
            regexCheckBox.setSelected(true);
        } else {
            grammarCheckBox.setSelected(true);
        }
        if (Objects.equals(model.getChosenLanguage(), Locale.GERMAN)) {
            germanCheckBox.setSelected(true);
        } else if (Objects.equals(model.getChosenLanguage(), Locale.ENGLISH)) {
            englishCheckBox.setSelected(true);
        }
        automaticSolutionCheckBox.setSelected(model.isAutomaticSolutionSelected());
        randomizeLowerCaseCheckBox.setSelected(model.isRandomizeLowerCaseSelected());
        partsDefinitionRequestedCheckBox.setSelected(model.isPartsDefinitionSelected());
    }

    private void updateComboBoxes(@NotNull AutomatonModel model) {
        typesComboBox.setSelectedItem(model.getType());
        determinismComboBox.setSelectedItem(model.getDeterminism());
    }

    private void updateTextComponents(@NotNull AutomatonModel model) {
        titleTextField.setText(model.getTitle());
        descriptionTextArea.setText(model.getDescription());
        languageTextField.setText(model.getLanguage());
        chosenFileTextField.setText(model.getJffPathName());
        sampleSolutionTextArea.setText(model.getSampleSolution());
        acceptedWordsTextArea.setText(model.getAcceptedWords());
        nonAcceptedWordsTextArea.setText(model.getNonAcceptedWords());
    }

    @Override
    public JPanel getMainPanel() {
        return MainPanel;
    }

    public String getTitleText() {
        return titleTextField.getText();
    }

    public String getDescriptionText() {
        return descriptionTextArea.getText();
    }

    public String getLanguageText() {
        return languageTextField.getText();
    }

    public String getAcceptedWordsText() {

        return acceptedWordsTextArea.getText();
    }

    public String getNonAcceptedWordsText() {
        return nonAcceptedWordsTextArea.getText();
    }

    public String getSampleSolutionText() {
        return sampleSolutionTextArea.getText();
    }

    public boolean getRegexCheckBoxIsSelected() {
        return regexCheckBox.isSelected();
    }

    public AutomatonType getSelectedAutomatonType() {
        return (AutomatonType) typesComboBox.getSelectedItem();
    }

    public Determinism getSelectedDeterminism() {
        return (Determinism) determinismComboBox.getSelectedItem();
    }

    public String getChosenFileText() {
        return chosenFileTextField.getText();
    }

    public boolean isRandomizeLowerCaseIsSelected() {
        return randomizeLowerCaseCheckBox.isSelected();
    }

    public boolean isPartsDefinitionRequestedSelected() {
        return partsDefinitionRequestedCheckBox.isSelected();
    }

    public boolean isAutomaticSolutionSelected() {
        return automaticSolutionCheckBox.isSelected();
    }

    public boolean isGermanSelected() {
        return germanCheckBox.isSelected();
    }
}
