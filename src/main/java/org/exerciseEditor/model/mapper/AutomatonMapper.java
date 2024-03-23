package org.exerciseEditor.model.mapper;

import de.HsH.inform.GraFlap.GraFlap;
import org.exerciseEditor.MyFileHandler;
import org.exerciseEditor.enums.AutomatonState;
import org.exerciseEditor.enums.AutomatonType;
import org.exerciseEditor.enums.Determinism;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.exerciseEditor.model.AutomatonModel;
import org.exerciseEditor.model.xmlData.*;

import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutomatonMapper {
    public static @NotNull AutomatonDTO mapToDTO(AutomatonModel automatonModel) {
        AutomatonDTO automatonDTO = new AutomatonDTO();
        automatonDTO.setImportJFCH(new Import("JFCH", "", "/res/fh-hannover/sprengel/Informatik" +
                "/TheoretischeInformatik" +
                "/Libraries/JFlap_call_preparation.library"));
        automatonDTO.setScript(new Script("loncapa/perl", buildPerlScriptString(automatonModel)));
        automatonDTO.setStartOutText(new StartOutText());
        automatonDTO.setTranslated(new Translated(automatonModel.getDescription()));
        automatonDTO.setEndOutText(new EndOutText());
        return automatonDTO;
    }

    public static AutomatonModel mapToUIData(@NotNull AutomatonDTO automatonDTO) {
        return new AutomatonModel.Builder()
                .title(getTitle(automatonDTO.getScript().getContent()))
                .description(automatonDTO.getTranslated().getValue())
                .language(getLanguage(automatonDTO.getScript().getContent()))
                .state(getAutomatonState(automatonDTO.getScript().getContent()))
                .determinism(getDeterminism(automatonDTO.getScript().getContent()))
                .type(getAutomatonTypeToEntity(automatonDTO.getScript().getContent()))
                .randomizeLowerCase(isRandomLetters(automatonDTO.getScript().getContent()))
                .setPartsDefinitionRequested(isPartsSet(automatonDTO.getScript().getContent()))
                .build();
    }

    private static @NotNull String getLanguage(String script) {
        Pattern patter = Pattern.compile("\\$given\\s*=\\s*\"(.*?)\"");
        Matcher matcher = patter.matcher(script);
        if (matcher.find()) {
            return matcher.group(1).replaceAll("\\$", "");
        } else {
            System.out.println("The language was not found!");
            return "";
        }
    }

    private static String getTitle(String script) {
        Pattern pattern = Pattern.compile("\\$tasktitle\\s*=\\s*\"(.*?)\"");
        Matcher matcher = pattern.matcher(script);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            System.out.println("The title was not found!");
            return "";
        }
    }

    private static @Nullable AutomatonState getAutomatonState(String script) {
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

    private static @NotNull Determinism getDeterminism(String script) {
        Pattern pattern = Pattern.compile("\\$type\\s*=\\s*\"([nd])");
        Matcher matcher = pattern.matcher(script);
        if (matcher.find()) {
            char nextChar = matcher.group(1).charAt(0);
            if (nextChar == 'n') {
                return Determinism.NOT_DETERMINISTIC;
            } else if (nextChar == 'd') {
                return Determinism.DETERMINISTIC;
            } else {
                return Determinism.NOT_SPECIFIED;
            }
        } else {
            return Determinism.NOT_SPECIFIED;
        }
    }

    private static @Nullable AutomatonType getAutomatonTypeToEntity(String script) {
        Pattern pattern = Pattern.compile("\\$type\\s*=\\s*\".*(tm|fa|pda)\";");
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
                default:
                    System.out.println("Error reading type, \"$type = \" found but without valid value");
                    return null;
            }
        } else {
            return AutomatonType.NOT_SPECIFIED;
        }
    }

    private static boolean isRandomLetters(@NotNull String script) {
        return script.contains("$choice");
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

    private static @NotNull String buildPerlScriptString(@NotNull AutomatonModel automatonModel) {
        StringBuilder scriptBuilder = new StringBuilder();
        scriptBuilder.append(getBestLanguage());
        scriptBuilder.append(getTaskTitle(automatonModel));
        scriptBuilder.append(getLanguage(automatonModel));
        scriptBuilder.append(getAutomatonMode(automatonModel));
        if (!Objects.equals(automatonModel.getType(), AutomatonType.NOT_SPECIFIED)) {
            scriptBuilder.append(getAutomatonTypeToXml(automatonModel));
        }
        scriptBuilder.append(getRemainingSettings());
        scriptBuilder.append(getJFFAndSVGString(automatonModel));

        return scriptBuilder.toString();
    }

    private static @NotNull String getBestLanguage() {
        return "\n$bestlanguage = scalar(&languages(['de','en']));";
    }

    private static @NotNull String insertDollarSigns(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            String match = matcher.group(1);
            String replaced = match.replaceAll("([a-z])", "\\$$1 ");
            replaced = Matcher.quoteReplacement(replaced);
            matcher.appendReplacement(result, replaced);
        }
        matcher.appendTail(result);
        return result.toString().replaceAll("\"", "\\\\\"");
    }

    private static @NotNull String getJFFAndSVGString(@NotNull AutomatonModel automatonModel) {
        String jff = MyFileHandler.readFile(automatonModel.getJffPathName());
        return "\n$jffstring = \"" + insertDollarSigns(jff, "(?m)(?<!<type>)(?<=>)([a-z].*)(?=<)") + "\";" + getSVGString(jff);
    }

    private static String getTaskTitle(@NotNull AutomatonModel automatonModel) {
        return String.format("\n$tasktitle = \"%s\";", automatonModel.getTitle());
    }

    private static @NotNull String getSVGString(@NotNull String jff) {
        String[] toParse = {"Automaton to SVG#de#non#svga#non#10#-", jff};
        StringBuilder parsedString = new StringBuilder();
        parsedString.append("\n$svgstring = \"");
        String svgString;
        try {
            svgString = GraFlap.parseAndProcessSubmission(toParse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        svgString = svgString.replaceAll("(<[^<]*</[^>]*>)", "\n$1");;
        svgString = insertDollarSigns(svgString, "(?<=>)(?!q[0-9])(.*)(?=</text>)");
        parsedString.append(svgString);
        parsedString.append("\";");
        return parsedString.append("\n@automaton = ReadAutomaton::readJFFAutomaton($jffstring,$svgstring);").toString();
    }

    private static @NotNull String getRemainingSettings() {
        return "\n$numberOfWords = 20;" + "\n$maxlength = 20;" + "\n($externalurl,$input,$error) = jflapUrlInput" +
                "($mode,$given,$type,$tasktitle,$numberOfWords,$maxlength);";
    }

    private static @NotNull String getLanguage(@NotNull AutomatonModel automatonModel) {
        StringBuilder modifiedLanguage = new StringBuilder();
        String input = automatonModel.getLanguage();
        Set<Character> uniqueChars = new HashSet<>();
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if (Character.isLowerCase(currentChar)) {
                modifiedLanguage.append("$").append(currentChar);
                uniqueChars.add(currentChar);
            } else {
                modifiedLanguage.append(currentChar);
            }
        }
        String modifiedString = modifiedLanguage.toString();
        StringBuilder languageBuilder = new StringBuilder();
        if (automatonModel.isRandomizeLowerCase()) {
            languageBuilder.append("\n@letter = ('a' .. 'z');\n$choice = random(0,$#letter-2,1);");
            languageBuilder.append(generateRandomLetterAssignments(uniqueChars));
        } else {
            languageBuilder.append(generateLetterAssignments(uniqueChars));
        }
        languageBuilder.append("\n$given = \"").append(modifiedString).append("\";");

        return languageBuilder.toString();
    }

    private static @NotNull String generateLetterAssignments(@NotNull Set<Character> uniqueChars) {
        StringBuilder buildString = new StringBuilder();
        Iterator<Character> iterator = uniqueChars.iterator();
        Character charAt;
        while (iterator.hasNext()) {
            charAt = iterator.next();
            buildString.append(String.format("\n$%c = '%c';", charAt, charAt));
        }
        return buildString.toString();
    }

    private static @NotNull String generateRandomLetterAssignments(@NotNull Set<Character> uniqueChars) {
        StringBuilder buildString = new StringBuilder();
        Iterator<Character> iterator = uniqueChars.iterator();
        buildString.append("\n$").append(iterator.next()).append(" = $letter[$choice];");
        int charAt = 1;
        while (iterator.hasNext()) {
            buildString.append(String.format("\n$%c = $letter[$choice+%d];", iterator.next(), charAt));
            charAt++;
        }
        return buildString.toString();
    }


    private static @NotNull String getAutomatonTypeToXml(@NotNull AutomatonModel automatonModel) {
        StringBuilder typeBuilder = new StringBuilder("\n$type = \"");
        switch (automatonModel.getDeterminism()) {
            case DETERMINISTIC:
                typeBuilder.append("d");
                break;
            case NOT_DETERMINISTIC:
                typeBuilder.append("n");
                break;
            default:
                // No action required if the determinism is unknown
                break;
        }

        switch (automatonModel.getType()) {
            case TURING_AUTOMATON:
                typeBuilder.append("tm");
                break;
            case FINITE_STATE_AUTOMATON:
                typeBuilder.append("fa");
                break;
            case PUSH_DOWN_AUTOMATON:
                typeBuilder.append("pda");
                break;
            default:
                System.out.println("Unknown type of automaton encountered.");
                break;
        }

        typeBuilder.append("\";");
        return typeBuilder.toString();
    }

    private static @NotNull String getAutomatonMode(@NotNull AutomatonModel automatonModel) {
        StringBuilder modeBuilder = new StringBuilder("\n$mode = 'a");

        switch (automatonModel.getState()) {
            case GRAMMAR:
                modeBuilder.append("g");
                break;
            case REGEX:
                modeBuilder.append("r");
                break;
            default:
                System.out.println("Unknown State of automaton encountered.");
                break;
        }

        if (!Objects.equals(automatonModel.getType(), AutomatonType.NOT_SPECIFIED)) {
            modeBuilder.append("t");
        }

        if (!Objects.equals(automatonModel.getAcceptedWords(), "")) {
            modeBuilder.append("w");
        }

        if (automatonModel.isPartsDefinitionRequested()) {
            modeBuilder.append("p");
        }

        modeBuilder.append("';");
        return modeBuilder.toString();
    }
}
