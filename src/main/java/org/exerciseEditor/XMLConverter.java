package org.exerciseEditor;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.exerciseEditor.model.mapper.AutomatonMapper;
import org.exerciseEditor.model.AutomatonModel;
import org.exerciseEditor.model.xmlData.AutomatonDTO;
import org.w3c.dom.Document;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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

    public static void createAutomatonProblemFile(AutomatonModel automatonModel, String fileName) {
        Document doc = AutomatonMapper.createAutomatonDocument(automatonModel);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));
            transformer.transform(source, result);

        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
