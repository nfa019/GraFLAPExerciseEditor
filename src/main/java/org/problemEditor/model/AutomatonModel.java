package org.problemEditor.model;

import org.problemEditor.enums.AutomatonState;
import org.problemEditor.enums.AutomatonType;
import org.problemEditor.enums.Determinism;
import org.jetbrains.annotations.NotNull;
import org.problemEditor.util.MyFileHandler;

import java.util.Locale;

public class AutomatonModel {
    private final String title;
    private final String description;
    private final String language;
    private final String acceptedWords;
    private final String nonAcceptedWords;
    private final String sampleSolution;
    private final AutomatonState state;
    private final Determinism determinism;
    private final AutomatonType type;
    private final String jffPathName;
    private final String jff;
    private final boolean randomizeLowerCase;
    private final boolean partsDefinitionRequested;
    private final boolean automaticSolution;
    private final Locale chosenLanguage;
    private static String jffInternal="";

    public static class Builder {
        private String title = "";
        private String description = "";
        private String language = "";
        private String acceptedWords = "";
        private String nonAcceptedWords = "";
        private String sampleSolution = "";
        private String jffPathName = "";
        private String jff ="";
        private AutomatonState state = AutomatonState.GRAMMAR;
        private AutomatonType type = AutomatonType.NOT_SPECIFIED;
        private Determinism determinism = Determinism.NOT_SPECIFIED;
        private boolean randomizeLowerCase = true;
        private boolean partsDefinitionRequested = false;
        private boolean automaticSolution = true;
        private Locale chosenLanguage = Locale.GERMAN;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder language(String language) {
            this.language = language;
            return this;
        }

        public Builder acceptedWords(String acceptedWords) {
            this.acceptedWords = acceptedWords;
            return this;
        }

        public Builder nonAcceptedWords(String nonAcceptedWords) {
            this.nonAcceptedWords = nonAcceptedWords;
            return this;
        }

        public Builder sampleSolution(String sampleSolution) {
            this.sampleSolution = sampleSolution;
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


        public Builder state(AutomatonState state) {
            this.state = state;
            return this;
        }

        public Builder type(AutomatonType type) {
            this.type = type;
            return this;
        }

        public Builder determinism(Determinism determinism) {
            this.determinism = determinism;
            return this;
        }


        public Builder randomizeLowerCase(boolean randomizeLowerCase) {
            this.randomizeLowerCase = randomizeLowerCase;
            return this;
        }

        public Builder partsDefinitionRequested(boolean parts) {
            this.partsDefinitionRequested = parts;
            return this;
        }

        public Builder automaticSolution(boolean automaticSolution) {
            this.automaticSolution = automaticSolution;
            return this;
        }

        public Builder chosenLanguage(Locale chosenLanguage) {
            this.chosenLanguage = chosenLanguage;
            return this;
        }

        public AutomatonModel build() {
            return new AutomatonModel(this);
        }

    }

    private AutomatonModel(@NotNull Builder builder) {
        this.title = builder.title;
        this.description = builder.description;
        this.language = builder.language;
        this.acceptedWords = builder.acceptedWords;
        this.nonAcceptedWords = builder.nonAcceptedWords;
        this.sampleSolution = builder.sampleSolution;
        this.state = builder.state;
        this.type = builder.type;
        this.determinism = builder.determinism;
        this.randomizeLowerCase = builder.randomizeLowerCase;
        this.partsDefinitionRequested = builder.partsDefinitionRequested;
        this.automaticSolution = builder.automaticSolution;
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
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
    }

    public String getAcceptedWords() {
        return acceptedWords;
    }

    public String getNonAcceptedWords() {
        return nonAcceptedWords;
    }

    public String getSampleSolution() {
        return sampleSolution;
    }

    public AutomatonState getState() {
        return state;
    }

    public AutomatonType getType() {
        return type;
    }

    public Determinism getDeterminism() {
        return determinism;
    }

    public String getJffPathName() {
        return jffPathName;
    }

    public String getJff() { return jff;  }

    public boolean isRandomizeLowerCaseSelected() {
        return randomizeLowerCase;
    }

    public boolean isPartsDefinitionSelected() {
        return partsDefinitionRequested;
    }

    public boolean isAutomaticSolutionSelected() {
        return automaticSolution;
    }

    public Locale getChosenLanguage() {
        return chosenLanguage;
    }

}
