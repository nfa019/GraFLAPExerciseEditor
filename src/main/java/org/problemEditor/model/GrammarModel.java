package org.problemEditor.model;

import org.jetbrains.annotations.NotNull;
import org.problemEditor.enums.GrammarType;

import java.util.Locale;

public class GrammarModel {
    private final String title;
    private final String description;
    private final String language;
    private final String generatedWords;
    private final String nonGeneratedWords;
    private final boolean randomizeLowerCase;
    private final GrammarType type;
    private final Locale chosenLanguage;

    public static class Builder {
        private String title = "";
        private String description = "";
        private String language = "";
        private String generatedWords = "";
        private String nonGeneratedWords = "";
        private boolean randomizeLowerCase = true;
        private GrammarType type = GrammarType.NOT_SPECIFIED;
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

        public Builder chosenLanguage(Locale chosenLanguage) {
            this.chosenLanguage = chosenLanguage;
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
        this.chosenLanguage = builder.chosenLanguage;
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

    public Locale getChosenLanguage() {
        return chosenLanguage;
    }
}
