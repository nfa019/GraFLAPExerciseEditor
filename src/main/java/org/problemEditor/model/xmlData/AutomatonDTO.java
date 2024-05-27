package org.problemEditor.model.xmlData;

import org.problemEditor.model.xmlData.xmlTags.*;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "problem")
public class AutomatonDTO {
    @XmlElement(name = "import")
    private Import importJFCH;
    @XmlElement(name = "import")
    private Import importJffperllib;
    @XmlElement(name = "import")
    private Import importJffa;
    @XmlElement
    private Script script;
    @XmlElement(name = "startouttext")
    private StartOutText startOutText;
    @XmlElement
    private Translated translated;
    @XmlElement(name = "endouttext")
    private EndOutText endOutText;
    @XmlElement(name = "postanswerdate")
    private PostAnswerDate postAnswerDate;

    public AutomatonDTO() {
        importJFCH = new Import("JFCH", "", "/res/fh-hannover/sprengel/Informatik/TheoretischeInformatik/Libraries" +
                "/JFlap_call_preparation.library");
        importJffperllib = new Import("jffperllib", "", "/res/fh-hannover/sprengel/Informatik/TheoretischeInformatik" +
                "/Libraries/ReadAutomaton.library");
        importJffa = new Import("jffa", "", "/res/fh-hannover/sprengel/Informatik/TheoretischeInformatik/Libraries" +
                "/jffautomata.library");
        startOutText = new StartOutText();
        endOutText = new EndOutText();
    }

    public Import getImportJFCH() {
        return importJFCH;
    }

    public Import getImportJffa() {
        return importJffa;
    }

    public Import getImportJffperllib() {
        return importJffperllib;
    }

    public Script getScript() {
        return script;
    }

    public Translated getTranslated() {
        return translated;
    }

    public StartOutText getStartOutText() {
        return startOutText;
    }

    public EndOutText getEndOutText() {
        return endOutText;
    }

    public PostAnswerDate getPostAnswerDate() {
        return postAnswerDate;
    }

    public void setScript(Script script) {
        this.script = script;
    }

    public void setTranslated(Translated translated) {
        this.translated = translated;
    }

    public void setPostAnswerDate(PostAnswerDate postAnswerDate) {
        this.postAnswerDate = postAnswerDate;
    }
}
