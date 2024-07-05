package org.problemEditor.view;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ItemListener;
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

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Warning",
                JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    protected void setRedFieldBorderIfEmpty(@NotNull JTextComponent component) {
        if (component.getText().isEmpty()) {
            component.setBorder(BorderFactory.createLineBorder(Color.RED));
        }
    }

    public int showOptionDialog(String title, String message) {
        Object[] options = {"Yes", "No"};
        return JOptionPane.showOptionDialog(null, message, title, JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    }

    public void highlightEmptyFields() {
        for (JTextComponent component : textComponents) {
            if (component.getText().isEmpty()) {
                setRedFieldBorderIfEmpty(component);
            }
        }
    }

    public boolean checkMissingInputs() {
        for (JTextComponent component : textComponents) {
            if (component.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    protected ItemListener toggleLanguageCheckbox(JCheckBox germanCheckBox, JCheckBox englishCheckBox) {
        return e -> {
            if (e.getSource() == germanCheckBox) {
                englishCheckBox.setSelected(!germanCheckBox.isSelected());
            } else if (e.getSource() == englishCheckBox) {
                germanCheckBox.setSelected(!englishCheckBox.isSelected());
            }
        };
    }

    public abstract void init();

    protected abstract ArrayList<JTextComponent> createTextComponentsList();

    public abstract JPanel getMainPanel();
}
