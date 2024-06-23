package org.problemEditor.model.xmlData.xmlTags;



import org.problemEditor.util.XmlCdataAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class Script {
    @XmlAttribute
    private String type;
    @XmlValue
    @XmlJavaTypeAdapter(XmlCdataAdapter.class)
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
