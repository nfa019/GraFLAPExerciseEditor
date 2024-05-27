package org.problemEditor.model.xmlData.xmlTags;

import javax.xml.bind.annotation.XmlElement;

public class PostAnswerDate {
    @XmlElement(name = "startouttext")
    private StartOutText startOutText;
    @XmlElement
    private Translated translated;
    @XmlElement(name = "endouttext")
    private EndOutText endOutText;

    public PostAnswerDate(Translated translated) {
        this.translated = translated;
        startOutText = new StartOutText();
        endOutText = new EndOutText();
    }

    public EndOutText getEndOutText() {
        return endOutText;
    }

    public StartOutText getStartOutText() {
        return startOutText;
    }

    public Translated getTranslated() {
        return translated;
    }
}
