package org.problemEditor.model.mapper;

import org.problemEditor.model.xmlData.xmlTags.*;
import org.problemEditor.enums.AutomatonState;
import org.problemEditor.enums.AutomatonType;
import org.problemEditor.enums.Determinism;
import org.problemEditor.model.xmlData.AutomatonDTO;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.problemEditor.model.AutomatonModel;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutomatonMapper extends Mapper {

    public static @NotNull AutomatonDTO mapToDTO(AutomatonModel automatonModel) {
        AutomatonDTO automatonDTO = new AutomatonDTO();
        automatonDTO.setScript(new Script(createPerlScriptString(automatonModel)));
        automatonDTO.setMeta(new Meta("jff",automatonModel.getJff()));
        automatonDTO.setTranslated(getTranslatedElement(automatonModel.getChosenLanguage(),
                automatonModel.getDescription()));
        automatonDTO.setPostAnswerDate(new PostAnswerDate(getTranslatedElement(automatonModel.getChosenLanguage(),
                getSampleSolutionToXml(automatonModel.getSampleSolution(),
                        automatonModel.isAutomaticSolutionSelected()))));
        return automatonDTO;
    }

    public static AutomatonModel mapToModel(@NotNull AutomatonDTO automatonDTO) {
        String sampleSolution = automatonDTO.getPostAnswerDate().getTranslated().getLang().getValue();
        String[] goodbad = getWordString(automatonDTO.getScript().getValue());
        return new AutomatonModel.Builder()
                .title(getTitleToModel(automatonDTO.getScript().getValue()))
                .chosenLanguage(getChosenLanguageToModel(automatonDTO.getScript().getValue()))
                .description(automatonDTO.getTranslated().getLang().getValue())
                .language(getLanguageToModel(automatonDTO.getScript().getValue()))
                .state(getAutomatonStateToModel(automatonDTO.getScript().getValue()))
                .determinism(getDeterminismToModel(automatonDTO.getScript().getValue()))
                .type(getAutomatonTypeToModel(automatonDTO.getScript().getValue()))
                .randomizeLowerCase(isRandomLetters(automatonDTO.getScript().getValue()))
                .partsDefinitionRequested(isPartsSet(automatonDTO.getScript().getValue()))
                .automaticSolution(getAutomaticSolutionToModel(sampleSolution))
                .sampleSolution(getSampleSolutionToModel(sampleSolution))
                .acceptedWords(goodbad[0])
                .nonAcceptedWords(goodbad[1])
                .jffPathName("internal")
                .jff(automatonDTO.getMeta().getValue())
                .build();
    }

    private static @Nullable AutomatonState getAutomatonStateToModel(String script) {
        Pattern pattern = Pattern.compile("\\$mode\\s*=\\s*'(a)([gr])");
        Matcher matcher = pattern.matcher(script);
        if (matcher.find()) {
            char nextChar = matcher.group(2).charAt(0);
            if (nextChar == 'g') {
                return AutomatonState.GRAMMAR;
            } else if (nextChar == 'r') {
                return AutomatonState.REGEX;
            } else {
                System.out.println("Error reading mode, \"$mode = \" found but without valid value!");
                return null;
            }
        } else {
            System.out.println("Error reading mode, \"$mode = \" was not found!");
            return null;
        }
    }

    private static @Nullable AutomatonType getAutomatonTypeToModel(String script) {
        Pattern pattern = Pattern.compile("\\$type\\s*=\\s*\".*(tm|fa|pda|non)\";");
        Matcher matcher = pattern.matcher(script);
        if (matcher.find()) {
            String type = matcher.group(1);
            switch (type) {
                case "tm":
                    return AutomatonType.TURING_AUTOMATON;
                case "fa":
                    return AutomatonType.FINITE_STATE_AUTOMATON;
                case "pda":
                    return AutomatonType.PUSH_DOWN_AUTOMATON;
                case "non":
                    return AutomatonType.NOT_SPECIFIED;
                default:
                    System.out.println("Error reading type, \"$type = \" found but without valid value");
                    return null;
            }
        } else {
            return null;
        }
    }

    private static boolean isPartsSet(String script) {
        Pattern pattern = Pattern.compile("\\$mode\\s*=\\s*'(.*)';");
        Matcher matcher = pattern.matcher(script);
        if (matcher.find()) {
            String modeValue = matcher.group(1);
            return modeValue.endsWith("p");
        } else {
            System.out.println("Error when reading mode, whether parts are queried");
        }
        return false;
    }

    private static @NotNull String createPerlScriptString(@NotNull AutomatonModel automatonModel) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(generateLocaleStatement(automatonModel.getChosenLanguage()));
        stringBuilder.append(getLanguageToXml(automatonModel.getLanguage(),
                automatonModel.isRandomizeLowerCaseSelected()));
        stringBuilder.append(getAutomatonModeToXml(automatonModel.getState(), automatonModel.getType(),
                automatonModel.isPartsDefinitionSelected()));
        stringBuilder.append(getTaskTitleToXml(automatonModel.getTitle()));
        if (!Objects.equals(automatonModel.getType(), AutomatonType.NOT_SPECIFIED)) {
            stringBuilder.append(getAutomatonTypeToXml(automatonModel.getDeterminism(), automatonModel.getType()));
        }
        stringBuilder.append(getRemainingSettings(createWordString(automatonModel.getAcceptedWords(),automatonModel.getNonAcceptedWords())));

        stringBuilder.append("\n$examplewords = giveExampleWords($given);");
        stringBuilder.append(getJFFAndSVGString(automatonModel.getJff()));
        stringBuilder.append("\n@automaton = ReadAutomaton::readJFFAutomaton($jffstring,$svgstring);");
        stringBuilder.append("\n$solution = jffautomata::samplesolution($jffstring,$svgimage);");

        return stringBuilder.toString();
    }



    private static @NotNull String getSampleSolutionToXml(String sampleSolutionText,
                                                          boolean isAutomaticSolutionSelected) {
        String sampleSolution = "";
        if (isAutomaticSolutionSelected) {
            sampleSolution = "\n$solution\n ";
        }
        return sampleSolution + sampleSolutionText;
    }

    private static String getSampleSolutionToModel(String script){
        String sampletext = script;
        if (script.contains("\n$solution\n")){
            sampletext = script.replace("\n$solution\n", "");
        }

        return sampletext;

    }

    private static boolean getAutomaticSolutionToModel(String script){
        if (script.contains("$solution")){
            return true;
        } else {
            return false;
        }
    }

    private static @NotNull String getAutomatonTypeToXml(@NotNull Determinism determinism, AutomatonType type) {
        StringBuilder stringBuilder = new StringBuilder("\n$type = \"");
        switch (determinism) {
            case DETERMINISTIC:
                stringBuilder.append("d");
                break;
            case NOT_DETERMINISTIC:
                stringBuilder.append("n");
                break;
        }

        switch (type) {
            case TURING_AUTOMATON:
                stringBuilder.append("tm");
                break;
            case FINITE_STATE_AUTOMATON:
                stringBuilder.append("fa");
                break;
            case PUSH_DOWN_AUTOMATON:
                stringBuilder.append("pda");
                break;
            case NOT_SPECIFIED:
                stringBuilder.append("non");
                break;
        }
        stringBuilder.append("\";");
        return stringBuilder.toString();
    }

    private static @NotNull String getAutomatonModeToXml(@NotNull AutomatonState state, AutomatonType type,
                                                         boolean isPartsDefinitionRequested) {
        StringBuilder modeBuilder = new StringBuilder("\n$mode = 'a");

        switch (state) {
            case GRAMMAR:
                modeBuilder.append("g");
                break;
            case REGEX:
                modeBuilder.append("r");
                break;
        }

        if (!Objects.equals(type, AutomatonType.NOT_SPECIFIED)) {
            modeBuilder.append("t");
        }

        modeBuilder.append("w");

        if (isPartsDefinitionRequested) {
            modeBuilder.append("p");
        }

        modeBuilder.append("';");
        return modeBuilder.toString();
    }
}
