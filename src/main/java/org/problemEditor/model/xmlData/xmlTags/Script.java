package org.problemEditor.model.xmlData.xmlTags;


import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Script {
    @XmlAttribute
    private String type;
    @XmlValue
    private String value;

    public Script() {
    }

    public Script(String value) {
        this.value = value;
        this.type = "loncapa/perl";
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
