package org.problemEditor.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;

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

    public static @Nullable String chooseFilePath(FileNameExtensionFilter filter) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);
        int selectionResult = fileChooser.showOpenDialog(null);
        if (selectionResult == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getPath();
        } else {
            return null;
        }
    }

    public static @Nullable Path createTempDirectory() {
        try {
            Path tempDir = Files.createTempDirectory("problemEditor");
            Path sourceDir = Paths.get("src/lib/Libraries");
            Path librariesDir = tempDir.resolve("Libraries");
            Files.copy(sourceDir, librariesDir, StandardCopyOption.REPLACE_EXISTING);
            Files.walk(sourceDir)
                    .forEach(sourcePath -> {
                        Path targetPath = librariesDir.resolve(sourceDir.relativize(sourcePath));
                        try {
                            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
            Path subfolder = tempDir.resolve("temp");
            Files.createDirectories(subfolder);
            return tempDir;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void showPreview(@NotNull Path tempDir) {
        try {
            Path htmlFile = tempDir.resolve("temp/task_temp1.html");
            File file = htmlFile.toFile();
            Desktop.getDesktop().browse(file.toURI());

            deleteTempDirectory(tempDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deleteTempDirectory(Path tempDir) {
        try {
            java.util.Timer timer = new Timer();

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Dateien werden gelöscht...");
                    deleteFilesFromTempDir(tempDir);
                    timer.cancel();
                }
            };

            timer.schedule(task, 5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteFilesFromTempDir(Path tempDir) {
        try {
            Files.walk(tempDir)
                    .sorted(Comparator.reverseOrder())
                    .forEach(tmpFile -> {
                        try {
                            Files.delete(tmpFile);
                        } catch (IOException e) {
                            System.err.println("Fehler beim Löschen von " + tmpFile + ": " + e.getMessage());
                        }
                    });
            Files.deleteIfExists(tempDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
