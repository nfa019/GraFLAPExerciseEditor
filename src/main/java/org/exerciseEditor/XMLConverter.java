package org.exerciseEditor;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.exerciseEditor.model.mapper.AutomatonMapper;
import org.exerciseEditor.model.AutomatonModel;
import org.exerciseEditor.model.xmlData.AutomatonDTO;

import java.io.*;

public class XMLConverter {

    public static void createAutomatonTaskFile(AutomatonModel automatonModel, String filePath) {
        AutomatonDTO automatonDTO = AutomatonMapper.mapToDTO(automatonModel);
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            xmlMapper.writeValue(new File(filePath), automatonDTO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static AutomatonModel convertXmlToAutomaton(String xmlFilePath) {
        File xmlFile = new File(xmlFilePath);
        XmlMapper xmlMapper = new XmlMapper();
        AutomatonDTO automatonDTO;
        try {
            automatonDTO = xmlMapper.readValue(xmlFile, AutomatonDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return AutomatonMapper.mapToUIData(automatonDTO);
    }
}
