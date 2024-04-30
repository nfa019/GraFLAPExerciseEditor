package org.exerciseEditor.model.xmlData.xmlTags;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Translated {
    @JacksonXmlProperty()
    private Lang lang;

    public Translated() {
    }

    public Translated(Lang lang) {
        this.lang = lang;
    }

    public Lang getLang() {
        return lang;
    }

}
