package org.problemEditor.model.xmlData.xmlTags;
import org.problemEditor.util.XmlCdataAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class Meta {
    @XmlAttribute
    private String name = "jff";
    @XmlValue
    @XmlJavaTypeAdapter(XmlCdataAdapter.class)
    private String value = "";

    public Meta() {
    }

    public Meta(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
