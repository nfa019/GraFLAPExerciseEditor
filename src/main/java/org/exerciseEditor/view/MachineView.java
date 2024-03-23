package org.exerciseEditor.view;

import org.exerciseEditor.model.BaseModel;
import org.exerciseEditor.controller.MachineController;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.util.ArrayList;

public class MachineView extends BaseView {
    private JPanel MainPanel;
    private MachineController machineViewController;
    public MachineView() {
    }

    @Override
    public void update(BaseModel model) {
        // TODO: Update-Methode erstellen!
    }

    @Override
    public void init() {
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
}
