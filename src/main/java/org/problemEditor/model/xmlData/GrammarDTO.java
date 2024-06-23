package org.problemEditor.model.xmlData;

import org.problemEditor.model.xmlData.xmlTags.*;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "problem")
public class GrammarDTO {
    @XmlElement(name = "import")
    private Import importJFCH;
    @XmlElement
    private Script script;
    @XmlElement(name = "startouttext")
    private StartOutText startOutText;
    @XmlElement
    private Translated translated;
    @XmlElement(name = "endouttext")
    private EndOutText endOutText;
    @XmlElement(name = "import")
    private Import importJfl;
    @XmlElement(name = "postanswerdate")
    private PostAnswerDate postAnswerDate;

    public GrammarDTO() {
        importJFCH = new Import("JFCH", "", "/res/fh-hannover/sprengel/Informatik/TheoretischeInformatik/Libraries" +
                "/JFlap_call_preparation.library");
        importJfl = new Import("jfl", "", "/res/fh-hannover/sprengel/Informatik/TheoretischeInformatik/Libraries" +
                "/JFlap_callGrammar.library");
        startOutText = new StartOutText();
        endOutText = new EndOutText();
    }

    public Import getImportJFCH() {
        return importJFCH;
    }

    public Script getScript() {
        return script;
    }

    public StartOutText getStartOutText() {
        return startOutText;
    }

    public Translated getTranslated() {
        return translated;
    }

    public EndOutText getEndOutText() {
        return endOutText;
    }

    public Import getImportJfl() {
        return importJfl;
    }

    public void setTranslated(Translated translated) {
        this.translated = translated;
    }

    public void setScript(Script script) {
        this.script = script;
    }

    public void setStartOutText(StartOutText startOutText) {
        this.startOutText = startOutText;
    }

    public void setPostAnswerDate(PostAnswerDate postAnswerDate) {
        this.postAnswerDate = postAnswerDate;
    }

    public PostAnswerDate getPostAnswerDate() {
        return postAnswerDate;
    }
}
