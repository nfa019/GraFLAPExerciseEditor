package org.exerciseEditor.model.xmlData.xmlTags;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class Import {
    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(isAttribute = true, localName = "importmode")
    private String importMode;

    @JacksonXmlText
    private String value;

    public Import() {
    }

    public Import(String id, String importMode, String value) {
        this.id = id;
        this.importMode = importMode;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public String getImportMode() {
        return importMode;
    }

    public String getValue() {
        return value;
    }
}
