package org.problemEditor.model;

import org.problemEditor.enums.Determinism;
import org.problemEditor.enums.MachineType;
import org.jetbrains.annotations.NotNull;
import org.problemEditor.util.MyFileHandler;

import java.util.Locale;

public class MachineModel {
    private final String title;
    private final String description;
    private final String input;
    private final String output;
    private final String sampleSolution;
    private final MachineType type;
    private final Determinism determinism;
    private final String jffPathName;
    private final String jff;
    private final Locale chosenLanguage;
    private static String jffInternal="";


    public static class Builder {
        private String title = "";
        private String description = "";
        private String input = "";
        private String output = "";
        private String sampleSolution = "";
        private MachineType type;
        private Determinism determinism = Determinism.NOT_SPECIFIED;
        private String jffPathName = "";
        private String jff ="";
        private Locale chosenLanguage = Locale.GERMAN;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder input(String input) {
            this.input = input;
            return this;
        }

        public Builder output(String output) {
            this.output = output;
            return this;
        }

        public Builder type(MachineType type) {
            this.type = type;
            return this;
        }

        public Builder determinism(Determinism determinism) {
            this.determinism = determinism;
            return this;
        }

        public Builder jffPathName(String jffPathName) {
            this.jffPathName = jffPathName;
            return this;
        }

        public Builder jff(String jff) {
            this.jff = jff;
            return this;
        }


        public Builder sampleSolution(String sampleSolution) {
            this.sampleSolution = sampleSolution;
            return this;
        }

        public Builder chosenLanguage(Locale chosenLanguage) {
            this.chosenLanguage = chosenLanguage;
            return this;
        }

        public MachineModel build() {
            return new MachineModel(this);
        }
    }

    private MachineModel(@NotNull Builder builder) {
        this.title = builder.title;
        this.description = builder.description;
        this.input = builder.input;
        this.output = builder.output;
        this.type = builder.type;
        this.determinism = builder.determinism;
        this.sampleSolution = builder.sampleSolution;
        this.chosenLanguage = builder.chosenLanguage;
        this.jffPathName = builder.jffPathName;
        if (this.getJffPathName().contains("internal")) {
            if (!builder.jff.isEmpty()) {
                jffInternal = builder.jff;
            }
            this.jff = jffInternal;
        } else if (!this.jffPathName.isEmpty()) {
            this.jff = MyFileHandler.readFile(this.getJffPathName());
        } else {
            this.jff = "";
        }
        System.out.println("jff nach Builder "+this.jff);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public MachineType getType() {
        return type;
    }

    public Determinism getDeterminism() {
        return determinism;
    }

    public String getJffPathName() {
        return jffPathName;
    }

    public String getSampleSolution() {
        return sampleSolution;
    }

    public Locale getChosenLanguage() {
        return chosenLanguage;
    }

    public String getJff() { return jff;  }
}
