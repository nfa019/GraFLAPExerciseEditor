package org.exerciseEditor.view;

import org.exerciseEditor.enums.AutomatonState;
import org.exerciseEditor.enums.AutomatonType;
import org.exerciseEditor.enums.Determinism;
import org.exerciseEditor.model.AutomatonModel;
import org.exerciseEditor.model.BaseModel;
import org.jetbrains.annotations.NotNull;
import org.exerciseEditor.controller.AutomatonController;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;

public class AutomatonView extends BaseView {
    private JCheckBox grammarCheckBox;
    private JCheckBox regexCheckBox;
    private JTextField languageTextField;
    private JButton checkInputButton;
    private JCheckBox randomizeLowerCaseCheckBox;
    private JCheckBox partsDefinitionRequestedCheckBox;
    private JTextField chosenFileTextField;
    private JButton chooseFileButton;
    private JComboBox<AutomatonType> typesComboBox;
    private JComboBox<Determinism> determinismComboBox;
    private JButton createSampleSolutionButton;
    private JTextArea sampleSolutionTextArea;
    private JTextArea acceptedWordsTextArea;
    private JTextArea nonAcceptedWordsTextArea;
    private JTextField titleTextField;
    private JTextArea descriptionTextArea;
    private JButton backButton;
    private JButton createButton;
    private JButton saveDraftButton;
    private JButton previewButton;
    private JButton cancelButton;
    private JPanel OptionalPanel;
    private JPanel MainPanel;
    private JPanel buttonPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
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
        initMouseListeners();
        initComboBoxes();
        chosenFileTextField.setEnabled(false);
    }

    @Override
    protected ArrayList<JTextComponent> createTextComponentsList() {
        ArrayList<JTextComponent> textComponentsList = new ArrayList<>();
        textComponentsList.add(titleTextField);
        textComponentsList.add(descriptionTextArea);
        textComponentsList.add(languageTextField);
        textComponentsList.add(chosenFileTextField);
        textComponentsList.add(sampleSolutionTextArea);
        textComponentsList.add(acceptedWordsTextArea);
        textComponentsList.add(nonAcceptedWordsTextArea);
        return textComponentsList;
    }

    private void initActionListeners() {
        ItemListener grammarOrRegexCheckBoxListener = e -> {
            if (e.getSource() == grammarCheckBox) {
                regexCheckBox.setSelected(false);
            } else if (e.getSource() == regexCheckBox) {
                grammarCheckBox.setSelected(false);
            }
        };
        grammarCheckBox.addItemListener(grammarOrRegexCheckBoxListener);
        regexCheckBox.addItemListener(grammarOrRegexCheckBoxListener);

        checkInputButton.addActionListener(e -> automatonViewController.handleCheckInputButton());
        chooseFileButton.addActionListener(e -> {
            automatonViewController.handleChooseFileButton();
            chosenFileTextField.setBorder(UIManager.getBorder("TextField.border"));
        });
        createSampleSolutionButton.addActionListener(e -> automatonViewController.handleCreateSampleSolutionButton());
        backButton.addActionListener(e -> automatonViewController.handleBackButton());
        cancelButton.addActionListener(e -> automatonViewController.handleCancelButton());
        saveDraftButton.addActionListener(e -> automatonViewController.handleSaveDraftButton());
        previewButton.addActionListener(e -> automatonViewController.handlePreviewButton());
        createButton.addActionListener(e -> automatonViewController.handleCreateButton());
    }

    private void initMouseListeners() {
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

    public void highlightEmptyFields() {
        for (JTextComponent component : textComponents) {
            if (component.getText().isEmpty()) {
                super.setRedFieldBorderIfEmpty(component);
            }
        }
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

    public AutomatonType getAutomatonType() {
        return (AutomatonType) typesComboBox.getSelectedItem();
    }

    public Determinism getDeterminism() {
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

    public boolean allTextComponentsNotEmpty() {
        for (JTextComponent component : textComponents) {
            if (component.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void setAutomatonViewController(AutomatonController automatonViewController) {
        this.automatonViewController = automatonViewController;
    }

    public void setChosenFileTextField(String absolutePath) {
        this.chosenFileTextField.setText(absolutePath);
    }

    @Override
    public void update(BaseModel model) {
        if (model instanceof AutomatonModel) {
            updateCheckBoxes((AutomatonModel) model);
            updateComboBoxes((AutomatonModel) model);
            updateTextComponents((AutomatonModel) model);
        }
    }

    private void updateCheckBoxes(@NotNull AutomatonModel model) {
        if (Objects.equals(model.getState(), AutomatonState.REGEX)) {
            regexCheckBox.setSelected(true);
        } else {
            grammarCheckBox.setSelected(true);
        }
        randomizeLowerCaseCheckBox.setSelected(model.isRandomizeLowerCase());
        partsDefinitionRequestedCheckBox.setSelected(model.isPartsDefinitionRequested());
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
}
