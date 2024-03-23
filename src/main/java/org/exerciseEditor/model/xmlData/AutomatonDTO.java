package org.exerciseEditor.model.xmlData;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;


@JacksonXmlRootElement(localName = "problem")
public class AutomatonDTO {
    private Import importJFCH;
    private Script script;
    private StartOutText startouttext;
    private Translated translated;
    private EndOutText endouttext;

    public Import getImportJFCH() {
        return importJFCH;
    }

    public void setImportJFCH(Import importJFCH) {
        this.importJFCH = importJFCH;
    }

    public Script getScript() {
        return script;
    }

    public void setScript(Script script) {
        this.script = script;
    }

    public Translated getTranslated() {
        return translated;
    }

    public void setTranslated(Translated translated) {
        this.translated = translated;
    }

    public StartOutText getStartouttext() {
        return startouttext;
    }

    public void setStartOutText(StartOutText startouttext) {
        this.startouttext = startouttext;
    }

    public EndOutText getEndouttext() {
        return endouttext;
    }

    public void setEndOutText(EndOutText endouttext) {
        this.endouttext = endouttext;
    }
}
