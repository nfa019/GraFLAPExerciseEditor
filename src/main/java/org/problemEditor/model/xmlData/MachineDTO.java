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
    @XmlElement(name = "import")
    private Import importJfl;
    @XmlElement(name = "postanswerdate")
    private PostAnswerDate postAnswerDate;
    @XmlElement(name = "meta")
    private Meta meta;

    public MachineDTO() {
        importJFCH = new Import("JFCH", "", "/res/fh-hannover/sprengel/Informatik/TheoretischeInformatik/Libraries" +
                "/JFlap_call_preparation.library");
        importRM = new Import("rm", "", "/res/fh-hannover/sprengel/Informatik/TheoretischeInformatik/Libraries" +
                "/ReadMachine.library");
        importJffa = new Import("jffa", "", "/res/fh-hannover/sprengel/Informatik/TheoretischeInformatik/Libraries" +
                "/jffautomata.library");
        importJfl = new Import("jfl", "", "/res/fh-hannover/sprengel/Informatik/TheoretischeInformatik/Libraries" +
                "/JFlap_callAutomaton.library");
        startOutText = new StartOutText();
        endOutText = new EndOutText();
    }


    public Script getScript() {
        return script;
    }

    public Translated getTranslated() {
        return translated;
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

    public Meta getMeta() {  return meta; }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
