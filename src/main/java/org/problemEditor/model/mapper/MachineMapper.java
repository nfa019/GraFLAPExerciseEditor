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
        System.out.println("sample solution"+machineDTO.getPostAnswerDate().getTranslated().getLang().getValue());
        return new MachineModel.Builder()
                .title(getTitleToModel(machineDTO.getScript().getValue()))
                .chosenLanguage(getChosenLanguageToModel(machineDTO.getScript().getValue()))
                .description(machineDTO.getTranslated().getLang().getValue())
                .type(getMachineTypeToModel(machineDTO.getScript().getValue()))
                .determinism(getDeterminismToModel(machineDTO.getScript().getValue()))
                .sampleSolution(machineDTO.getPostAnswerDate().getTranslated().getLang().getValue())
                .jffPathName("internal")
                .jff(machineDTO.getMeta().getValue())
                .build();
    }

    private static @NotNull String createPerlScriptString(@NotNull MachineModel machineModel) {
        return  generateLocaleStatement(machineModel.getChosenLanguage()) +
                getTaskTitleToXml(machineModel.getTitle()) +
                getMachineMode() +
                getMachineTypeToXML(machineModel.getDeterminism(), machineModel.getType()) +
                getRemainingSettings() +
                getJFFAndSVGString(machineModel.getJff()) +
                "\n$svgimage = buildImageFromSVG($svgstring);\n"
        ;
    }

    private static @NotNull String getMachineMode() {
        return "\n$mode = 'mp';";
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
