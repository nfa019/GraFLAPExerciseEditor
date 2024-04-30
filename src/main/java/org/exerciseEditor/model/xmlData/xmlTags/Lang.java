package org.exerciseEditor.model.xmlData.xmlTags;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class Lang {
    @JacksonXmlProperty(isAttribute = true)
    private String which;
    @JsonRawValue
    @JacksonXmlText
    private String value;

    public Lang() {
    }

    public Lang(String which,String value) {
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
