package org.problemEditor.model.xmlData;

import org.problemEditor.model.xmlData.xmlTags.*;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "problem")
public class MachineDTO {
    @XmlElement(name = "import")
    private Import importJFCH;
    @XmlElement(name = "import")
    private Import importRM;
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

    public MachineDTO() {
        importJFCH = new Import("JFCH", "", "/res/fh-hannover/sprengel/Informatik/TheoretischeInformatik/Libraries" +
                "/JFlap_call_preparation.library");
        importRM = new Import("rm", "", "/res/fh-hannover/sprengel/Informatik/TheoretischeInformatik/Libraries" +
                "/ReadMachine.library");
        importJffa = new Import("jffa", "", "/res/fh-hannover/sprengel/Informatik/TheoretischeInformatik/Libraries" +
                "/jffautomata.library");
        startOutText = new StartOutText();
        endOutText = new EndOutText();
    }

    public Import getImportJFCH() {
        return importJFCH;
    }

    public Import getImportRM() {
        return importRM;
    }

    public Import getImportJffa() {
        return importJffa;
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
