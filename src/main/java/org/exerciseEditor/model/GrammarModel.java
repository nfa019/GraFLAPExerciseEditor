package org.exerciseEditor.model;

import org.jetbrains.annotations.NotNull;
import org.exerciseEditor.enums.GrammarType;

public class GrammarModel extends BaseModel {
    private final String language;
    private final String generatedWords;
    private final String nonGeneratedWords;
    private final boolean randomizeLowerCase;
    private final GrammarType type;
    public static class Builder {
        private String title = "";
        private String description = "";
        private String language = "";
        private  String generatedWords = "";
        private String nonGeneratedWords = "";
        private boolean randomizeLowerCase = true;
        private GrammarType type = GrammarType.NOT_SPECIFIED;

        public Builder title(String title){
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
        public Builder generatedWords(String generatedWords) {
            this.generatedWords = generatedWords;
            return this;
        }
        public Builder nonGeneratedWords(String nonGeneratedWords) {
            this.nonGeneratedWords = nonGeneratedWords;
            return this;
        }
        public Builder randomizeLowerCase(boolean randomLetters) {
            this.randomizeLowerCase = randomLetters;
            return this;
        }
        public Builder type(GrammarType type) {
            this.type = type;
            return this;
        }
        public GrammarModel build() {
            return new GrammarModel(this);
        }
    }
    private GrammarModel(@NotNull Builder builder) {
        this.title = builder.title;
        this.description = builder.description;
        this.language = builder.language;
        this.generatedWords = builder.generatedWords;
        this.nonGeneratedWords = builder.nonGeneratedWords;
        this.randomizeLowerCase = builder.randomizeLowerCase;
        this.type = builder.type;
    }

    public String getLanguage() {
        return language;
    }

    public String getGeneratedWords() {
        return generatedWords;
    }

    public String getNonGeneratedWords() {
        return nonGeneratedWords;
    }

    public boolean isRandomizeLowerCase() {
        return randomizeLowerCase;
    }

    public GrammarType getType() {
        return type;
    }
}
