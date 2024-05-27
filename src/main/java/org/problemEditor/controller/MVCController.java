package org.problemEditor.controller;

import org.problemEditor.GraFlapProblemEditor;
import org.problemEditor.enums.PanelName;
import org.problemEditor.util.MyFileHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public abstract class MVCController {
    protected GraFlapProblemEditor graFlapProblemEditor;

    public MVCController(GraFlapProblemEditor graFlapProblemEditor) {
        this.graFlapProblemEditor = graFlapProblemEditor;
    }

    protected File getDirectoryPath(String filePath) {
        String directory = new File(filePath).getParent();
        String proFormADir = directory + "/ProFormA";
        Path sourceDir = Paths.get("src/lib/Libraries");
        Path librariesDir = sourceDir.resolve(directory + "/Libraries");
        try {
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
