package org.exerciseEditor.view;

import org.exerciseEditor.controller.StartController;

import javax.swing.*;

public class StartView{
    private JButton chooseFileButton;
    private JButton newFileButton;
    private JPanel MainPanel;
    private StartController startController;

    public StartView() {
        newFileButton.addActionListener(e -> startController.handleNewFileButton());
        chooseFileButton.addActionListener(e -> startController.handleChooseFileButton());
    }

    public void setStartViewController(StartController startController) {
        this.startController = startController;
    }

    public JPanel getMainPanel() {
        return MainPanel;
    }
}
