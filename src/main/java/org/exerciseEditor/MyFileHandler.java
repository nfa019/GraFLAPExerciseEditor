package org.exerciseEditor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MyFileHandler {

    public static @NotNull String readFile(String filename) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        return content.toString();
    }

    public static @Nullable String chooseFilePath(FileNameExtensionFilter filter, File startDirectory) {
        JFileChooser fileChooser = new JFileChooser(startDirectory);
        fileChooser.setFileFilter(filter);
        int selectionResult = fileChooser.showOpenDialog(null);
        if (selectionResult == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getPath();
        } else {
            return null;
        }
    }
}
