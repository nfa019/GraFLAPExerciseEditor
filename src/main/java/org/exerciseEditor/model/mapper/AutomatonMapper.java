package org.exerciseEditor.model.mapper;

import de.HsH.inform.GraFlap.GraFlap;
import org.exerciseEditor.MyFileHandler;
import org.exerciseEditor.enums.AutomatonState;
import org.exerciseEditor.enums.AutomatonType;
import org.exerciseEditor.enums.Determinism;
import org.exerciseEditor.model.xmlData.xmlTags.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.exerciseEditor.model.AutomatonModel;
import org.exerciseEditor.model.xmlData.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.*;


public class AutomatonMapper {

    public static @Nullable Document createAutomatonDocument(AutomatonModel automatonModel) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element problemElement = createAutomatonProblemElement(doc, automatonModel);
            doc.appendChild(problemElement);
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static @NotNull AutomatonDTO mapToDTO(AutomatonModel automatonModel) {
        AutomatonDTO automatonDTO = new AutomatonDTO();
        automatonDTO.setScript(new Script("loncapa/perl", createPerlScriptString(automatonModel)));
        automatonDTO.setTranslated(new Translated(new Lang("de", automatonModel.getDescription())));
        return automatonDTO;
    }

    public static AutomatonModel mapToUIData(@NotNull AutomatonDTO automatonDTO) {
        return new AutomatonModel.Builder()
                .title(getTitle(automatonDTO.getScript().getContent()))
                .description(automatonDTO.getTranslated().getLang().getValue())
                .language(getLanguage(automatonDTO.getScript().getContent()))
                .state(getAutomatonState(automatonDTO.getScript().getContent()))
                .determinism(getDeterminism(automatonDTO.getScript().getContent()))
                .type(getAutomatonTypeToEntity(automatonDTO.getScript().getContent()))
                .randomizeLowerCase(isRandomLetters(automatonDTO.getScript().getContent()))
                .partsDefinitionRequested(isPartsSet(automatonDTO.getScript().getContent()))
                .build();
    }

    private static @NotNull Element createAutomatonProblemElement(@NotNull Document doc,
                                                                  AutomatonModel automatonModel) {
        Element problemElement = doc.createElement("problem");
        problemElement.appendChild(createImportElement(doc, "JFCH", "/res/fh-hannover/sprengel/Informatik" +
                "/TheoretischeInformatik/Libraries/JFlap_call_preparation.library"));
        problemElement.appendChild(createImportElement(doc, "ra", "/res/fh-hannover/sprengel/Informatik" +
                "/TheoretischeInformatik/Libraries/ReadAutomaton.library"));
        problemElement.appendChild(createImportElement(doc, "jffa", "/res/fh-hannover/sprengel/Informatik" +
                "/TheoretischeInformatik/Libraries/jffautomata.library"));
        problemElement.appendChild(createScriptElement(doc, createPerlScriptString(automatonModel)));
        problemElement.appendChild(createStartOuttextElement(doc));
        problemElement.appendChild(createTranslatedElement(doc, automatonModel.getDescription()));
        problemElement.appendChild(createEndOuttextElement(doc));
        problemElement.appendChild(createPostAnswerDate(doc, automatonModel.getSampleSolution(), automatonModel.isAutomaticSolution()));

        return problemElement;
    }

    private static @NotNull Element createImportElement(@NotNull Document doc, String importId, String importPath) {
        Element importElement = doc.createElement("import");
        importElement.setAttribute("importmode", "");
        importElement.setAttribute("id", importId);
        importElement.appendChild(doc.createTextNode(importPath));
        return importElement;
    }

    private static @NotNull Element createScriptElement(@NotNull Document doc, String perlScript) {
        Element scriptElement = doc.createElement("script");
        scriptElement.setAttribute("type", "loncapa/perl");
        scriptElement.appendChild(doc.createTextNode(perlScript));
        return scriptElement;
    }

    private static @NotNull Element createTranslatedElement(@NotNull Document doc, String text) {
        Element translatedElement = doc.createElement("translated");
        translatedElement.appendChild(createLangElement(doc, text));
        return translatedElement;
    }

    private static @NotNull Element createLangElement(@NotNull Document doc, String text) {
        Element langElement = doc.createElement("lang");
        langElement.setAttribute("which", "de");
        langElement.appendChild(doc.createTextNode(text));
        return langElement;
    }

    private static @NotNull Element createPostAnswerDate(@NotNull Document doc, String text, boolean automaticSolution) {
        Element postAnswerDateElement = doc.createElement("postanswerdate");
        postAnswerDateElement.appendChild(createStartOuttextElement(doc));
        if (automaticSolution) {
            postAnswerDateElement.appendChild(doc.createTextNode("\n$solution"));
        }
        if (!text.isEmpty()) {
            postAnswerDateElement.appendChild(createTranslatedElement(doc, text));

        }
        postAnswerDateElement.appendChild(createEndOuttextElement(doc));
        return postAnswerDateElement;
    }

    private static Element createStartOuttextElement(@NotNull Document doc) {
        return doc.createElement("startouttext");
    }

    private static Element createEndOuttextElement(@NotNull Document doc) {
        return doc.createElement("endouttext");
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

    private static @NotNull String createPerlScriptString(@NotNull AutomatonModel automatonModel) {
        StringBuilder scriptBuilder = new StringBuilder();
        scriptBuilder.append("\n$bestlanguage = scalar(&languages(['de','en']));");
        scriptBuilder.append(getTaskTitle(automatonModel.getTitle()));
        scriptBuilder.append(getLanguage(automatonModel.getLanguage(), automatonModel.isRandomizeLowerCase()));
        scriptBuilder.append(getAutomatonMode(automatonModel.getState(), automatonModel.getType(), automatonModel.getAcceptedWords().isEmpty(), automatonModel.isPartsDefinitionRequested()));
        if (!Objects.equals(automatonModel.getType(), AutomatonType.NOT_SPECIFIED)) {
            scriptBuilder.append(getAutomatonTypeToXml(automatonModel.getDeterminism(), automatonModel.getType()));
        }
        scriptBuilder.append(getRemainingSettings());
        if (automatonModel.getAcceptedWords().isEmpty()) {
            scriptBuilder.append("\n$examplewords = giveExampleWords($given);");
        }
        scriptBuilder.append(getJFFAndSVGString(automatonModel.getJffPathName()));
//        scriptBuilder.append("\n$jff = &submission(\"JFC\",\"JFlapCall\");");
        scriptBuilder.append("\n@automaton = ReadAutomaton::readJFFAutomaton($jffstring,$svgstring);");
        scriptBuilder.append("\n$solution = jffautomata::samplesolution($jffstring,$svgstring);");

        return scriptBuilder.toString();
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

    private static @NotNull String getJFFAndSVGString(String pathName) {
        String jff = MyFileHandler.readFile(pathName);
        return "\n$jffstring = \"" + insertDollarSigns(jff, "(?m)(?<!<type>)(?<=>)([a-z].*)(?=<)") + "\";" + getSVGString(jff);
    }

    private static String getTaskTitle(@NotNull String title) {
        return String.format("\n$tasktitle = \"%s\";", title);
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
        svgString = svgString.replaceAll("(<[^<]*</[^>]*>)", "\n$1");
        ;
        svgString = insertDollarSigns(svgString, "(?<=>)(?!q[0-9])(.*)(?=</text>)");
        parsedString.append(svgString);
        parsedString.append("\";");
        return parsedString.toString();
    }

    private static @NotNull String getRemainingSettings() {
        return "\n$numberOfWords = 20;" +
                "\n$maxlength = 20;" +
                "\n($externalurl,$input,$error) = jflapUrlInput($mode,$given,$type,$tasktitle,$numberOfWords," +
                "$maxlength);" +
                "\n$listSubmission = listOfSubmissions(JFC,JFlapCall);";
    }

    private static @NotNull String getLanguage(String language, boolean isRandomizeLowerCase) {
        StringBuilder modifiedLanguage = new StringBuilder();
        Set<Character> uniqueChars = new HashSet<>();
        for (int i = 0; i < language.length(); i++) {
            char currentChar = language.charAt(i);
            if (Character.isLowerCase(currentChar)) {
                modifiedLanguage.append("$").append(currentChar).append(" ");
                uniqueChars.add(currentChar);
            } else {
                modifiedLanguage.append(currentChar);
            }
        }
        String modifiedString = modifiedLanguage.toString();
        StringBuilder languageBuilder = new StringBuilder();
        if (isRandomizeLowerCase) {
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


    private static @NotNull String getAutomatonTypeToXml(Determinism determinism, AutomatonType type) {
        StringBuilder typeBuilder = new StringBuilder("\n$type = \"");
        switch (determinism) {
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

        switch (type) {
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

    private static @NotNull String getAutomatonMode(@NotNull AutomatonState state, AutomatonType type, boolean acceptedWordsIsEmpty, boolean isPartsDefinitionRequested) {
        StringBuilder modeBuilder = new StringBuilder("\n$mode = 'a");

        switch (state) {
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

        if (!Objects.equals(type, AutomatonType.NOT_SPECIFIED)) {
            modeBuilder.append("t");
        }

        if (acceptedWordsIsEmpty) {
            modeBuilder.append("w");
        }

        if (isPartsDefinitionRequested) {
            modeBuilder.append("p");
        }

        modeBuilder.append("';");
        return modeBuilder.toString();
    }
}
