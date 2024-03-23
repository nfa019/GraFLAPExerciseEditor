package org.exerciseEditor.view;

import org.exerciseEditor.model.BaseModel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.ArrayList;

public abstract class BaseView {

    protected ArrayList<JTextComponent> textComponents;

    protected BaseView() {
        this.textComponents = new ArrayList<>();
    }

    public void showValidationError() {
        JOptionPane.showMessageDialog(null, "Please fill in all required fields!", "Warning",
                JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccessMessage() {
        JOptionPane.showMessageDialog(null, "File successfully created!", "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    protected void setRedFieldBorderIfEmpty(@NotNull JTextComponent component) {
        if (component.getText().isEmpty()) {
            component.setBorder(BorderFactory.createLineBorder(Color.RED));
        }
    }

    public abstract void update(BaseModel model);

    public abstract void init();

    protected abstract ArrayList<JTextComponent> createTextComponentsList();

    public abstract JPanel getMainPanel();
}
