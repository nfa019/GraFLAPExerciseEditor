package org.exerciseEditor;

import java.io.*;

public class Main {

    public static void main(String[] args) {
//        ersetzeTextInDatei("/home/sergio/Schreibtisch/Bachelorarbeit/TestProblems/pda_test.problem");
        String folderPath = "/home/sergio/Schreibtisch/Bachelorarbeit/TestProblems/ProForma/pda_test";
        File folder = new File(folderPath);
        if (!folder.exists()) {
            boolean success = folder.mkdirs();
            if (success) {
                System.out.println("Der Ordner wurde erfolgreich erstellt.");
            } else {
                System.out.println("Fehler beim Erstellen des Ordners.");
            }
        } else {
            System.out.println("Der Ordner existiert bereits.");
        }
        lc2mdl.Main.convertLCtoMoodle(new File("/home/sergio/Schreibtisch/Bachelorarbeit/TestProblems/pda_test.problem"), folder, 10, "/home/sergio/Schreibtisch/Bachelorarbeit/TestProblems/pda_test.problem");
//        new GraFlapExerciseEditor().startUI();
    }

    public static void ersetzeTextInDatei(String dateiPfad) {
        try {
            // FileReader zum Lesen der Datei öffnen
            FileReader fileReader = new FileReader(dateiPfad);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // StringBuilder zum Speichern der geänderten Zeilen erstellen
            StringBuilder stringBuilder = new StringBuilder();
            String zeile;

            // Zeilen der Datei lesen und "-" durch "+" ersetzen
            while ((zeile = bufferedReader.readLine()) != null) {
                zeile = zeile.replace("&gt", ">");
                zeile = zeile.replace("&lt", "<");
                stringBuilder.append(zeile);
                stringBuilder.append("\n");
            }

            // BufferedReader schließen
            bufferedReader.close();

            // FileWriter zum Schreiben der geänderten Zeilen öffnen
            FileWriter fileWriter = new FileWriter(dateiPfad);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Geänderte Zeilen in die Datei schreiben
            bufferedWriter.write(stringBuilder.toString());

            // BufferedWriter schließen
            bufferedWriter.close();

            System.out.println("Ersetzung abgeschlossen.");
        } catch (IOException e) {
            System.out.println("Fehler beim Lesen oder Schreiben der Datei: " + e.getMessage());
        }
    }
}
