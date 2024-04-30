package org.exerciseEditor.view;

import org.exerciseEditor.enums.Determinism;
import org.exerciseEditor.enums.MachineType;
import org.exerciseEditor.model.BaseModel;
import org.exerciseEditor.controller.MachineController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import java.util.ArrayList;

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
    private MachineController machineViewController;

    public MachineView() {
        super();
    }

    @Override
    public void update(BaseModel model) {
        // TODO: Update-Methode erstellen!
    }

    @Override
    public void init() {
        MainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        backButton.addActionListener(e -> machineViewController.handleBackButton());
        cancelButton.addActionListener(e -> machineViewController.handleCancelButton());
        // TODO: init-Methode erstellen!
    }

    @Override
    protected ArrayList<JTextComponent> createTextComponentsList() {
        return null;
    }

    public void setMachineViewController(MachineController machineViewController) {
        this.machineViewController = machineViewController;
    }

    @Override
    public JPanel getMainPanel() {
        return MainPanel;
    }

    public MachineType getMachineType() {
        return (MachineType) typeComboBox.getSelectedItem();
    }

    public Determinism getDeterminism() {
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
}
