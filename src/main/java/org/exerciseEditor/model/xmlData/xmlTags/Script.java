package org.exerciseEditor.model.xmlData.xmlTags;


import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class Script {

    @JacksonXmlProperty(isAttribute = true)
    private String type;

    @JsonRawValue
    @JacksonXmlText
    private String content;

    public Script() {
    }

    public Script(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}
