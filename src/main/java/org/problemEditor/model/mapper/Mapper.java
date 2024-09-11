package org.problemEditor.model.mapper;

import de.hsh.inform.graflap.GraFlap;
import org.jetbrains.annotations.NotNull;
import org.problemEditor.enums.Determinism;
import org.problemEditor.model.xmlData.xmlTags.Lang;
import org.problemEditor.model.xmlData.xmlTags.Translated;
import org.problemEditor.util.MyFileHandler;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Mapper {

    protected static String getTaskTitleToXml(@NotNull String title) {
        return String.format("\n$tasktitle = \"%s\";", title);
    }

    protected static @NotNull String getRemainingSettings(String wordsGiven) {
        String perlStatement = "\n$maxlength = 20;";
        if (wordsGiven.isEmpty()) {
            perlStatement += "\n$numberOfWords = 20;" +
                    "\n($externalurl,$input,$error) = jflapUrlInput($mode,$given,$type,$tasktitle,$numberOfWords," +
                    "$maxlength);";
        } else {
            perlStatement += wordsGiven;
            perlStatement += "\n($externalurl,$input,$error) = jflapUrlInput($mode,$given,$type,$tasktitle,$numberOfWords," +
                    "$maxlength,$testwords);";
        }

        return perlStatement;
    }

    protected static @NotNull String createWordString (String good, String bad){
        String perlStatement = "";

        if (good.isEmpty() && bad.isEmpty()){
        }  else {
            String words = good + "!" + bad;
            words = words.replaceAll("\\n", "%");
            words = words.replaceAll("%!","!");
            words = words.replaceAll("%%","%");
            if (words.endsWith("%")){
                words = words.substring(0,words.length()-2);
            }
            words = words.replaceAll("\\s", "");
            words = words.replaceAll("([a-z])", "\\$$1");
            String[] spl = words.split("!");
            String[] first = spl[0].split("%");
            String[] second = spl[1].split("%");
            int num = first.length + second.length;
            perlStatement += "\n$numberOfWords = " + num + ";";
            perlStatement += "\n$testwords = \"" + words + "\";";
        }

        return perlStatement;
    }

    protected static @NotNull String[] getWordString (String script){
        Pattern pattern = Pattern.compile("\\$testwords\\s*=\\s*\"([\\$Ea-z!%]*)\";");
        Matcher matcher = pattern.matcher(script);
        String[] goodbad = {"",""};
        if (matcher.find()) {
            String inout = matcher.group(1);
            goodbad = inout.split("!");
            goodbad[0] = goodbad[0].replaceAll("%",System.lineSeparator());
            goodbad[0] = goodbad[0].replaceAll("\\$","");
            goodbad[1] = goodbad[1].replaceAll("%",System.lineSeparator());
            goodbad[1] = goodbad[1].replaceAll("\\$","");

        }
        return goodbad;
    }


    protected static @NotNull String getJFFAndSVGString(String jff) {
            String perlStatement = "\n$jffstring = \"" + insertDollarSigns(jff, "(?m)(?<!<type>)(?<=>)([a-z].*)(?=<)") + "\";" + getSVGString(jff);
                   perlStatement += "\n$svgimage = buildImageFromSVG($svgstring);\n";
            return perlStatement;

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

    protected static @NotNull Translated getTranslatedElement(Locale chosenLanguage, String text) {
        String language = "";
        if (chosenLanguage == Locale.GERMAN) {
            language = "de";
        } else if (chosenLanguage == Locale.ENGLISH) {
            language = "en";
        }
        return new Translated(new Lang(language, text));
    }

    protected static @NotNull String getLanguageToXml(@NotNull String language, boolean isRandomizeLowerCase) {
        StringBuilder modifiedLanguage = new StringBuilder();
        Set<Character> uniqueChars = new HashSet<>();
        boolean isGrammar = language.contains("->");
        for (int i = 0; i < language.length(); i++) {
            char currentChar = language.charAt(i);
            if (Character.isLowerCase(currentChar)) {
                modifiedLanguage.append("$").append(currentChar);
                if (isGrammar) {
                    modifiedLanguage.append(" ");
                }
                uniqueChars.add(currentChar);
            } else {
                modifiedLanguage.append(currentChar);
            }
        }
        String modifiedString = modifiedLanguage.toString();
        StringBuilder languageBuilder = new StringBuilder();
        if (isRandomizeLowerCase) {
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
        if (uniqueChars.isEmpty()) {
            return "";
        } else {
            StringBuilder buildString = new StringBuilder();
            Iterator<Character> iterator = uniqueChars.iterator();
            buildString.append("\n$").append(iterator.next()).append(" = $letter[$choice];");
            int charAt = 1;
            while (iterator.hasNext()) {
                buildString.append(String.format("\n$%c = $letter[$choice+%d];", iterator.next(), charAt));
                charAt++;
            }
            String statement = "\n@letter = ('a' .. 'z');\n$choice = random(0,$#letter-"+ charAt +",1);";
            statement = statement + buildString.toString();
            return statement;
        }
    }

    protected static String getTitleToModel(String script) {
        Pattern pattern = Pattern.compile("\\$tasktitle\\s*=\\s*\"(.*?)\"");
        Matcher matcher = pattern.matcher(script);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            System.out.println("The title was not found!");
            return "";
        }
    }

    protected static @NotNull String getLanguageToModel(String script) {
        Pattern patter = Pattern.compile("\\$given\\s*=\\s*\"(.*?)\"");
        Matcher matcher = patter.matcher(script);
        if (matcher.find()) {
            return matcher.group(1).replaceAll("\\$", "");
        } else {
            System.out.println("The language was not found!");
            return "";
        }
    }



    protected static Locale getChosenLanguageToModel(String script){
        Locale locale = Locale.ENGLISH;
        Pattern pattern = Pattern.compile("\\$bestlanguage\\s*=\\s*\"([ed])");
        Matcher matcher = pattern.matcher(script);
        if (matcher.find()) {
            char nextChar = matcher.group(1).charAt(0);
            if (nextChar == 'd') {
                locale = Locale.GERMAN;
            }
        }
        return locale;
    }

    protected static String generateLocaleStatement(Locale locale){
        if (locale==Locale.GERMAN){
            return "\n$bestlanguage=\"de\";";
        } else {
             return "\n$bestlanguage=\"en\";";
        }
    }

    protected static boolean isRandomLetters(@NotNull String script) {
        return script.contains("$choice");
    }

    protected static @NotNull Determinism getDeterminismToModel(String script) {
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
}
