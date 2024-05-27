package org.problemEditor.view;

import org.problemEditor.controller.ChooseController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ChooseView {
    private JPanel MainPanel;
    private JButton machineButton;
    private JButton automatonButton;
    private JButton grammarButton;
    private JButton backButton;
    private ChooseController chooseController;

    public ChooseView() {
        MainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        backButton.addActionListener(e -> chooseController.handleBackButton());
        automatonButton.addActionListener(e -> chooseController.handleAutomatonButton());
        grammarButton.addActionListener(e -> chooseController.handleGrammarButton());
        machineButton.addActionListener(e -> chooseController.handleMachineButton());
    }

    public JPanel getMainPanel() {
        return MainPanel;
    }

    public void setChooseViewController(ChooseController chooseController) {
        this.chooseController = chooseController;
    }
}
