package org.problemEditor.util;

import com.sun.xml.bind.marshaller.CharacterEscapeHandler;
import org.jetbrains.annotations.NotNull;
import org.problemEditor.model.GrammarModel;
import org.problemEditor.model.MachineModel;
import org.problemEditor.model.mapper.AutomatonMapper;
import org.problemEditor.model.AutomatonModel;
import org.problemEditor.model.mapper.GrammarMapper;
import org.problemEditor.model.mapper.MachineMapper;
import org.problemEditor.model.xmlData.AutomatonDTO;
import org.problemEditor.model.xmlData.GrammarDTO;
import org.problemEditor.model.xmlData.MachineDTO;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class XMLConverter {

    public static void createAutomatonTaskFile(AutomatonModel automatonModel, String filePath) {
        AutomatonDTO automatonDTO = AutomatonMapper.mapToDTO(automatonModel);
        marshal(automatonDTO, filePath);
    }

    public static AutomatonModel convertXmlToAutomaton(String xmlFilePath) {
        File xmlFile = new File(xmlFilePath);
        AutomatonDTO automatonDTO;
        try {
            JAXBContext context = JAXBContext.newInstance(AutomatonDTO.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            unmarshaller.setSchema(null);
            automatonDTO = (AutomatonDTO) unmarshaller.unmarshal(xmlFile);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return AutomatonMapper.mapToModel(automatonDTO);
    }

    public static GrammarModel convertXmlToGrammar(String xmlFilePath) {
        File xmlFile = new File(xmlFilePath);
        GrammarDTO grammarDTO;
        try {
            JAXBContext context = JAXBContext.newInstance(GrammarDTO.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            unmarshaller.setSchema(null);
            grammarDTO = (GrammarDTO) unmarshaller.unmarshal(xmlFile);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return GrammarMapper.mapToModel(grammarDTO);
    }

    public static MachineModel convertXmlToMachine(String xmlFilePath) {
        File xmlFile = new File(xmlFilePath);
        MachineDTO machineDTO;
        try {
            JAXBContext context = JAXBContext.newInstance(MachineDTO.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            unmarshaller.setSchema(null);
            machineDTO = (MachineDTO) unmarshaller.unmarshal(xmlFile);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return MachineMapper.mapToModel(machineDTO);
    }

    public static void createGrammarTaskFile(GrammarModel grammarModel, String filePath) {
        GrammarDTO grammarDTO = GrammarMapper.mapToDTO(grammarModel);
        marshal(grammarDTO, filePath);
    }

    public static void createMachineTaskFile(MachineModel machineModel, String filePath) {
        MachineDTO machineDTO = MachineMapper.mapToDTO(machineModel);
        marshal(machineDTO, filePath);
    }

    private static void marshal(@NotNull Object object, String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.setProperty(CharacterEscapeHandler.class.getName(), (CharacterEscapeHandler) (chars, start,
                                                                                                     length, isAtVal,
                                                                                                     writer) -> writer.write(chars, start, length));
            marshaller.setProperty("jaxb.encoding", "ISO-8859-1");
            marshaller.marshal(object, new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
