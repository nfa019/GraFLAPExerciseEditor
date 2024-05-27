package org.problemEditor.view;

import org.jetbrains.annotations.NotNull;
import org.problemEditor.enums.Determinism;
import org.problemEditor.enums.MachineType;
import org.problemEditor.controller.MachineController;
import org.problemEditor.model.MachineModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class MachineView extends BaseView {
    private JPanel MainPanel;
    private JComboBox<MachineType> typeComboBox;
    private JComboBox<Determinism> determinismComboBox;
    private JTextArea inputTextArea;
    private JTextArea outputTextArea;
    private JTextField titleTextField;
    private JTextArea descriptionTextArea;
    private JButton backButton;
    private JButton createButton;
    private JButton saveDraftButton;
    private JButton previewButton;
    private JButton cancelButton;
    private JTextField chosenFileTextField;
    private JButton chooseFileButton;
    private JCheckBox englishCheckBox;
    private JCheckBox germanCheckBox;
    private MachineController machineViewController;

    public MachineView() {
        super();
    }

    @Override
    public void init() {
        MainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        super.textComponents = this.createTextComponentsList();
        initActionListeners();
        initMouseListener();
        initComboBoxes();
    }

    private void initActionListeners() {
        ItemListener germanOrEnglishCheckboxListener = toggleLanguageCheckbox(germanCheckBox, englishCheckBox);

        germanCheckBox.addItemListener(germanOrEnglishCheckboxListener);
        englishCheckBox.addItemListener(germanOrEnglishCheckboxListener);

        chooseFileButton.addActionListener(e -> {
            machineViewController.handleChooseFileButton();
            chosenFileTextField.setBorder(UIManager.getBorder("TextField.border"));
        });

        backButton.addActionListener(e -> machineViewController.handleBackButton());
        cancelButton.addActionListener(e -> machineViewController.handleCancelButton());
        saveDraftButton.addActionListener(e -> machineViewController.handleSaveDraftButton());
        previewButton.addActionListener(e -> machineViewController.handlePreviewButton());
        createButton.addActionListener(e -> machineViewController.handleCreateButton());
    }

    private void initComboBoxes() {
        typeComboBox.setModel(new DefaultComboBoxModel<>(MachineType.values()));
        determinismComboBox.setModel(new DefaultComboBoxModel<>(Determinism.values()));
    }

    @Override
    protected ArrayList<JTextComponent> createTextComponentsList() {
        ArrayList<JTextComponent> textComponentsList = new ArrayList<>();
        textComponentsList.add(inputTextArea);
        textComponentsList.add(outputTextArea);
        textComponentsList.add(titleTextField);
        textComponentsList.add(descriptionTextArea);
        textComponentsList.add(chosenFileTextField);
        return textComponentsList;
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

    public void setMachineViewController(MachineController machineViewController) {
        this.machineViewController = machineViewController;
    }

    public void setChosenFileTextField(String absolutePath) {
        this.chosenFileTextField.setText(absolutePath);
    }

    public void update(MachineModel model) {
        updateCheckBoxes(model);
        updateComboBoxes(model);
        updateTextComponents(model);
    }

    private void updateCheckBoxes(@NotNull MachineModel model) {
        if (Objects.equals(model.getChosenLanguage(), Locale.GERMAN)) {
            germanCheckBox.setSelected(true);
        } else if (Objects.equals(model.getChosenLanguage(), Locale.ENGLISH)) {
            englishCheckBox.setSelected(true);
        }
    }

    private void updateComboBoxes(@NotNull MachineModel model) {
        typeComboBox.setSelectedItem(model.getType());
        determinismComboBox.setSelectedItem(model.getDeterminism());
    }

    private void updateTextComponents(@NotNull MachineModel model) {
        titleTextField.setText(model.getTitle());
        descriptionTextArea.setText(model.getDescription());
        chosenFileTextField.setText(model.getJffPathName());
        inputTextArea.setText(model.getInput());
        outputTextArea.setText(model.getOutput());
    }

    @Override
    public JPanel getMainPanel() {
        return MainPanel;
    }

    public MachineType getSelectedMachineType() {
        return (MachineType) typeComboBox.getSelectedItem();
    }

    public Determinism getSelectedDeterminism() {
        return (Determinism) determinismComboBox.getSelectedItem();
    }

    public String getInputText() {
        return inputTextArea.getText();
    }

    public String getOutputText() {
        return outputTextArea.getText();
    }

    public String getTitleText() {
        return titleTextField.getText();
    }

    public String getDescriptionText() {
        return descriptionTextArea.getText();
    }

    public String getChosenFileText() {
        return chosenFileTextField.getText();
    }

    public boolean isGermanSelected() {
        return germanCheckBox.isSelected();
    }
}
