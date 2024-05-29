package org.problemEditor.controller;

import org.problemEditor.GraFlapProblemEditor;
import org.problemEditor.Main;
import org.problemEditor.enums.PanelName;
import org.problemEditor.util.MyFileHandler;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public abstract class MVCController {
    protected GraFlapProblemEditor graFlapProblemEditor;

    public MVCController(GraFlapProblemEditor graFlapProblemEditor) {
        this.graFlapProblemEditor = graFlapProblemEditor;
    }

    protected File getDirectoryPath(String filePath) {
        String directory = new File(filePath).getParent();
        String proFormADir = directory + "/ProFormA";

        // Path to the running JAR file
        String jarFilePath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String sourceDirectory = "Libraries";
        try {
            MyFileHandler.copyDirectoryFromJar(jarFilePath, sourceDirectory, directory + "/Libraries");
        } catch (IOException e) {
            e.printStackTrace();
        }

        File baseDirFile = new File(proFormADir);
        if (!baseDirFile.exists()) {
            try {
                Files.createDirectories(Paths.get(proFormADir));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String fileName = new File(filePath).getName().replaceFirst("[.][^.]+$", "");

        File fileDir = new File(proFormADir + "/" + fileName);
        if (!fileDir.exists()) {
            try {
                Files.createDirectories(Paths.get(fileDir.getAbsolutePath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            MyFileHandler.deleteFilesFromTempDir(fileDir.toPath());
            try {
                Files.createDirectories(Paths.get(fileDir.getAbsolutePath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileDir;
    }

    public void handleBackButton() {
        graFlapProblemEditor.navigateTo(PanelName.CHOOSE);
    }

    public abstract void initWithNewModel();

    public abstract void initWithExistingModel(String pathName);

}
