package org.exerciseEditor.model.xmlData;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class Translated {
    @JacksonXmlText
    private String value;

    public Translated() {
    }

    public Translated(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
