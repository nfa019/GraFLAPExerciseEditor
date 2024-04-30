package org.exerciseEditor.model;

import org.exerciseEditor.enums.AutomatonState;
import org.exerciseEditor.enums.AutomatonType;
import org.exerciseEditor.enums.Determinism;
import org.jetbrains.annotations.NotNull;

public class AutomatonModel extends BaseModel {
    private final String language;
    private final String acceptedWords;
    private final String nonAcceptedWords;
    private final String sampleSolution;
    private final AutomatonState state;
    private final Determinism determinism;
    private final AutomatonType type;
    private final String jffPathName;
    private final boolean randomizeLowerCase;
    private final boolean partsDefinitionRequested;
    private final boolean automaticSolution;

    public static class Builder {
        private String title = "";
        private String description = "";
        private String language = "";
        private String acceptedWords = "";
        private String nonAcceptedWords = "";
        private String sampleSolution = "";
        private String jffPathName = "";
        private AutomatonState state = AutomatonState.GRAMMAR;
        private AutomatonType type = AutomatonType.NOT_SPECIFIED;
        private Determinism determinism = Determinism.NOT_SPECIFIED;
        private boolean randomizeLowerCase = true;
        private boolean partsDefinitionRequested = false;
        private boolean automaticSolution = true;

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
        this.jffPathName = builder.jffPathName;
        this.randomizeLowerCase = builder.randomizeLowerCase;
        this.partsDefinitionRequested = builder.partsDefinitionRequested;
        this.automaticSolution = builder.automaticSolution;
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

    public boolean isRandomizeLowerCase() {
        return randomizeLowerCase;
    }

    public boolean isPartsDefinitionRequested() {
        return partsDefinitionRequested;
    }

    public boolean isAutomaticSolution() {
        return automaticSolution;
    }
}
