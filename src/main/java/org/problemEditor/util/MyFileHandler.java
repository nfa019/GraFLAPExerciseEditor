package org.problemEditor.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.problemEditor.Main;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
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
            String jarFilePath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            String sourceDirectory = "Libraries";
            try {
                MyFileHandler.copyDirectoryFromJar(jarFilePath, sourceDirectory, tempDir + "/Libraries");
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    public static void copyDirectoryFromJar(String jarFilePath, String sourceDirectory, String destinationPath) throws IOException {
        try (JarFile jarFile = new JarFile(jarFilePath)) {
            Enumeration<JarEntry> entries = jarFile.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();

                if (entryName.startsWith(sourceDirectory) && !entry.isDirectory()) {
                    InputStream inputStream = jarFile.getInputStream(entry);
                    File destFile = new File(destinationPath, entryName.substring(sourceDirectory.length() + 1));
                    File destDir = destFile.getParentFile();

                    if (!destDir.exists()) {
                        destDir.mkdirs();
                    }

                    try (OutputStream outputStream = new FileOutputStream(destFile)) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                    }

                    inputStream.close();
                }
            }
        }
    }
}
