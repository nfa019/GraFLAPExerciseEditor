package org.problemEditor.model.mapper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.problemEditor.enums.Determinism;
import org.problemEditor.enums.MachineType;
import org.problemEditor.model.MachineModel;
import org.problemEditor.model.xmlData.MachineDTO;
import org.problemEditor.model.xmlData.xmlTags.Meta;
import org.problemEditor.model.xmlData.xmlTags.PostAnswerDate;
import org.problemEditor.model.xmlData.xmlTags.Script;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MachineMapper extends Mapper {

    public static @NotNull MachineDTO mapToDTO(MachineModel machineModel) {
        MachineDTO machineDTO = new MachineDTO();
        machineDTO.setScript(new Script(createPerlScriptString(machineModel)));
        machineDTO.setMeta(new Meta("jff",machineModel.getJff()));
        machineDTO.setTranslated(getTranslatedElement(machineModel.getChosenLanguage(), machineModel.getDescription()));
        machineDTO.setPostAnswerDate(new PostAnswerDate(getTranslatedElement(machineModel.getChosenLanguage(), machineModel.getSampleSolution())));
        return machineDTO;
    }

    public static MachineModel mapToModel(@NotNull MachineDTO machineDTO) {
        String[] inout = getInOutputString(machineDTO.getScript().getValue());
        return new MachineModel.Builder()
                .title(getTitleToModel(machineDTO.getScript().getValue()))
                .chosenLanguage(getChosenLanguageToModel(machineDTO.getScript().getValue()))
                .description(machineDTO.getTranslated().getLang().getValue())
                .type(getMachineTypeToModel(machineDTO.getScript().getValue()))
                .determinism(getDeterminismToModel(machineDTO.getScript().getValue()))
                .sampleSolution(machineDTO.getPostAnswerDate().getTranslated().getLang().getValue())
                .input(inout[0])
                .output(inout[1])
                .jffPathName("internal")
                .jff(machineDTO.getMeta().getValue())
                .build();
    }

    private static @NotNull String createPerlScriptString(@NotNull MachineModel machineModel) {

        return  generateLocaleStatement(machineModel.getChosenLanguage()) +
                getTaskTitleToXml(machineModel.getTitle()) +
                getMachineMode(machineModel.getOutput()) +
                getMachineTypeToXML(machineModel.getDeterminism(), machineModel.getType()) +
                "\n$modelsolution = \"jff\";" +
                getRemainingSettings(createInOutputString(machineModel.getInput(),machineModel.getOutput())) +
                getJFFAndSVGString(machineModel.getJff())
        ;
    }

    private static  @NotNull String createInOutputString(@NotNull String inputString,@NotNull  String outputString) {
        String perlStatement = "";
        String inString = inputString;
        String outString = outputString;
        if (inString.isEmpty()){
            System.out.println("There should be at least input words");
            return "";
        } else
            inString = inString.replaceAll("\\n", ",");
            inString = inString.replaceAll("\\s", "");
            String[] inWords = inString.split(",");
            int num = inWords.length;
            perlStatement += "\n$numberofwords = " + num + ";";
            perlStatement += "\n$testwords = \"";
            if (outString.isEmpty()) {
                for (int i=0; i<inWords.length; i++){
                    perlStatement += inWords[i];
                    if (i<inWords.length-1) {
                        perlStatement += "%";
                    }
                }
             } else {
                outString = outString.replaceAll("\\n", ",");
                outString = outString.replaceAll("\\s", "");
                String[] outWords = outString.split(",");
                if (inWords.length == outWords.length) {
                    for (int i=0; i<inWords.length; i++){
                        perlStatement += inWords[i] + ";" + outWords[i];
                        if (i<inWords.length-1) {
                            perlStatement += "%";
                        }
                    }
            perlStatement += "\";";
            } else {
                System.out.println("There should be the same number of in- and output words");
            }
        }

        return perlStatement;
    }

    private static  @NotNull String[] getInOutputString(@NotNull String script) {
        Pattern pattern = Pattern.compile("\\$testwords\\s*=\\s*\"([a-z#0-9;!%]*)\";");
        Matcher matcher = pattern.matcher(script);
        if (matcher.find()) {
            String inout = matcher.group(1);
            String[] io = {"", ""};

            if (!inout.contains(";")){
                io[0] = inout.replaceAll("%",System.lineSeparator());
            } else {
                String[] pairs = inout.split("%");
                for (int i=0; i<pairs.length; i++){
                    String[] s = pairs[i].split(";");
                    io[0] += s[0];
                    io[1] += s[1];
                    if (i<pairs.length-1){
                        io[0] += System.lineSeparator();
                        io[1] += System.lineSeparator();
                    }
                }
            }
            return io;

        } else {
            return null;
        }
    }

    private static @NotNull String getMachineMode(String out) {
        if (out.isEmpty()){
            return "\n$mode = 'mmw';";
        }else {
            return "\n$mode = 'mp';";
        }
    }

    private static @Nullable MachineType getMachineTypeToModel(String script) {
        Pattern pattern = Pattern.compile("\\$type\\s*=\\s*\".*(mealy|moore|tm)\";");
        Matcher matcher = pattern.matcher(script);
        if (matcher.find()) {
            String type = matcher.group(1);
            switch (type) {
                case "mealy":
                    return MachineType.MEALY;
                case "moore":
                    return MachineType.MOORE;
                case "tm":
                    return MachineType.TURING;
                default:
                    System.out.println("Error reading type, \"$type = \" found but without valid value");
                    return null;
            }
        } else {
            return null;
        }
    }

    private static @NotNull String getMachineTypeToXML(@NotNull Determinism determinism, @NotNull MachineType type) {
        StringBuilder stringBuilder = new StringBuilder("\n$type = \"");
        switch (type) {
            case TURING:
                if (determinism == Determinism.DETERMINISTIC) {
                    stringBuilder.append("d");
                } else if (determinism == Determinism.NOT_DETERMINISTIC) {
                    stringBuilder.append("n");
                }
                stringBuilder.append("tm");
                break;
            case MEALY:
                stringBuilder.append("mealy");
                break;
            case MOORE:
                stringBuilder.append("moore");
                break;
        }
        stringBuilder.append("\";");
        return stringBuilder.toString();
    }
}
