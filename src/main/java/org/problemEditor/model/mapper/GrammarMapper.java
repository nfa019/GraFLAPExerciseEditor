package org.problemEditor.model.mapper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.problemEditor.enums.GrammarType;
import org.problemEditor.model.GrammarModel;
import org.problemEditor.model.xmlData.GrammarDTO;
import org.problemEditor.model.xmlData.xmlTags.PostAnswerDate;
import org.problemEditor.model.xmlData.xmlTags.Script;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GrammarMapper extends Mapper {

    public static @NotNull GrammarDTO mapToDTO(GrammarModel grammarModel) {
        GrammarDTO grammarDTO = new GrammarDTO();
        grammarDTO.setScript(new Script(createPerlScriptString(grammarModel)));
        grammarDTO.setTranslated(getTranslatedElement(grammarModel.getChosenLanguage(), grammarModel.getDescription()));
        grammarDTO.setPostAnswerDate(new PostAnswerDate(getTranslatedElement(grammarModel.getChosenLanguage(), grammarModel.getSampleSolution())));
        return grammarDTO;
    }

    public static GrammarModel mapToModel(@NotNull GrammarDTO grammarDTO) {
        String[] goodbad = getWordString(grammarDTO.getScript().getValue());

        return new GrammarModel.Builder()
                .title(getTitleToModel(grammarDTO.getScript().getValue()))
                .chosenLanguage(getChosenLanguageToModel(grammarDTO.getScript().getValue()))
                .description(grammarDTO.getTranslated().getLang().getValue())
                .language(getLanguageToModel(grammarDTO.getScript().getValue()))
                .type(getGrammarTypeToModel(grammarDTO.getScript().getValue()))
                .randomizeLowerCase(isRandomLetters(grammarDTO.getScript().getValue()))
                .sampleSolution(grammarDTO.getPostAnswerDate().getTranslated().getLang().getValue())
                .generatedWords(goodbad[0])
                .nonGeneratedWords(goodbad[1])
                .build();
    }

    private static @Nullable GrammarType getGrammarTypeToModel(String script) {
        Pattern pattern = Pattern.compile("\\$type\\s*=\\s*\".*(rlcfg|rl|cfg|ncfg|non)\";");
        Matcher matcher = pattern.matcher(script);
        if (matcher.find()) {
            String type = matcher.group(1);
            switch (type) {
                case "rlcfg":
                    return GrammarType.RIGHT_LINEAR_OR_CONTEXT_FREE;
                case "rl":
                    return GrammarType.RIGHT_LINEAR;
                case "cfg":
                    return GrammarType.CONTEXT_FREE;
                case "ncfg":
                    return GrammarType.NON_CONTEXT_FREE;
                case "non":
                    return GrammarType.NOT_SPECIFIED;
                default:
                    System.out.println("Error reading type, \"$type = \" found but without valid value");
                    return null;
            }
        } else {
            return null;
        }
    }

    private static @NotNull String createPerlScriptString(@NotNull GrammarModel grammarModel) {
        return  generateLocaleStatement(grammarModel.getChosenLanguage()) +
                getLanguageToXml(grammarModel.getLanguage(), grammarModel.isRandomizeLowerCase()) +
                getTaskTitleToXml(grammarModel.getTitle()) +
                (getGrammarModeToXml(grammarModel.getType())) +
                getGrammarTypeToXml(grammarModel.getType()) +
                "\n$grammarstring = $given;" +
                "\n$modelsolution=\"grammar\";" +
                "\n$examplewords = giveExampleWords($given);" +
                getRemainingSettings(createWordString(grammarModel.getGeneratedWords(),grammarModel.getNonGeneratedWords()));
    }

    private static @NotNull String getGrammarModeToXml(GrammarType grammarType) {
        StringBuilder modeBuilder = new StringBuilder("\n$mode = 'gg");
        if (!Objects.equals(grammarType, GrammarType.NOT_SPECIFIED)) {
            modeBuilder.append("t");
        }
        modeBuilder.append("w';");
        return modeBuilder.toString();
    }

    private static @NotNull String getGrammarTypeToXml(@NotNull GrammarType type) {
        StringBuilder stringBuilder = new StringBuilder("\n$type = \"");
        switch (type) {
            case NOT_SPECIFIED:
                stringBuilder.append("non\";");
                break;
            case RIGHT_LINEAR:
                stringBuilder.append("rl\";");
                break;
            case RIGHT_LINEAR_OR_CONTEXT_FREE:
                stringBuilder.append("rlcfg\";");
                break;
            case CONTEXT_FREE:
                stringBuilder.append("cfg\";");
                break;
            case NON_CONTEXT_FREE:
                stringBuilder.append("ncfg\";");
                break;
        }
        return stringBuilder.toString();
    }
}
