package org.problemEditor.model.xmlData.xmlTags;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Lang {
    @XmlAttribute
    private String which;
    @XmlValue
    private String value;

    public Lang() {
    }

    public Lang(String which, String value) {
        this.value = value;
        this.which = which;
    }

    public String getValue() {
        return value;
    }

    public String getWhich() {
        return which;
    }
}
