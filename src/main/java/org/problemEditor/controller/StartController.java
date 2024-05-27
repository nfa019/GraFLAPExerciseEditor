package org.problemEditor.controller;

import org.problemEditor.GraFlapProblemEditor;
import org.problemEditor.util.MyFileHandler;
import org.problemEditor.enums.ControllerName;
import org.problemEditor.enums.PanelName;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StartController {
    private final HashMap<ControllerName, MVCController> allUIDataViewController;
    private final GraFlapProblemEditor graFlapProblemEditor;

    public StartController(@NotNull GraFlapProblemEditor graFlapProblemEditor,
                           HashMap<ControllerName, MVCController> allUIDataViewController) {
        this.graFlapProblemEditor = graFlapProblemEditor;
        graFlapProblemEditor.setStartViewController(this);
        this.allUIDataViewController = allUIDataViewController;
    }

    public void handleNewFileButton() {
        for (MVCController MVCController : allUIDataViewController.values()) {
            MVCController.initWithNewModel();
        }
        graFlapProblemEditor.navigateTo(PanelName.CHOOSE);
    }

    public void handleChooseFileButton() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Task File (*.problem)", "problem");
        String pathName = MyFileHandler.chooseFilePath(filter);
        if (pathName != null) {
            String fileContent = MyFileHandler.readFile(pathName);
            Pattern pattern = Pattern.compile("\\$mode\\s*=\\s*'(.).*;");
            Matcher matcher = pattern.matcher(fileContent);
            if (matcher.find()) {
                char nextChar = matcher.group(1).charAt(0);
                initControllerAndView(nextChar, pathName);
            } else {
                File file = new File(pathName);
                JOptionPane.showMessageDialog(null, "Error wile reading " + file.getName() + ", line with \"$mode = " +
                        "\" not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void initControllerAndView(char nextChar, String pathName) {
        MVCController MVCController = null;
        PanelName panelName = null;
        switch (nextChar) {
            case 'a':
                MVCController = allUIDataViewController.get(ControllerName.AUTOMATON_VIEW_CONTROLLER);
                panelName = PanelName.AUTOMATON;
                break;
            case 'g':
                MVCController = allUIDataViewController.get(ControllerName.GRAMMAR_VIEW_CONTROLLER);
                panelName = PanelName.GRAMMAR;
                break;
            case 'm':
                MVCController = allUIDataViewController.get(ControllerName.MACHINE_VIEW_CONTROLLER);
                panelName = PanelName.MACHINE;
                break;
            default:
                File file = new File(pathName);
                JOptionPane.showMessageDialog(null, "Error reading " + file.getName() + ", line with " +
                        "\"$mode = \" found but without valid value!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        if (MVCController != null) {
            MVCController.initWithExistingModel(pathName);
            graFlapProblemEditor.navigateTo(panelName);
        }
    }
}
