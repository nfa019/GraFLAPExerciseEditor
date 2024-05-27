package org.problemEditor.model.xmlData.xmlTags;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Import {
    @XmlAttribute
    private String id;
    @XmlAttribute
    private String importMode;
    @XmlValue
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
