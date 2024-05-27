package org.problemEditor.model.xmlData.xmlTags;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Translated {
    @XmlElement
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
