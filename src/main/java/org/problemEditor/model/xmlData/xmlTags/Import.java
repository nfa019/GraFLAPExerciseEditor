package org.problemEditor.model.xmlData.xmlTags;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Import {
    @XmlAttribute
    private String id;
    @XmlValue
    private String value;

    public Import() {
    }

    public Import(String id,  String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }


    public String getValue() {
        return value;
    }
}
