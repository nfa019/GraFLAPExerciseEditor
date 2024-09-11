package org.problemEditor.model.xmlData.xmlTags;

import org.problemEditor.util.XmlCdataAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class Lang {
    @XmlAttribute
    private String which;
    @XmlValue
    @XmlJavaTypeAdapter(XmlCdataAdapter.class)
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
