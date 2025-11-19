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
    @XmlElement(name = "import")
    private Import importJfl;
    @XmlElement(name = "import")
    private Import importParts;
    @XmlElement(name = "postanswerdate")
    private PostAnswerDate postAnswerDate;
    @XmlElement(name = "meta")
    private Meta meta;


    public AutomatonDTO() {
        importJFCH = new Import("JFCH",  "/res/fh-hannover/sprengel/Informatik/TheoretischeInformatik/Libraries" +
                "/JFlap_call_preparation.library");
        importJffperllib = new Import("jffperllib",  "/res/fh-hannover/sprengel/Informatik/TheoretischeInformatik" +
                "/Libraries/Automata.library");
        importJffa = new Import("jffa",  "/res/fh-hannover/sprengel/Informatik/TheoretischeInformatik/Libraries" +
                "/jffautomata.library");
        importJfl = new Import("jfl",  "/res/fh-hannover/sprengel/Informatik/TheoretischeInformatik/Libraries" +
                "/JFlap_callAutomaton.library");
        importParts = new Import("jfp","/res/fh-hannover/sprengel/Informatik/TheoretischeInformatik/Libraries" +
                "/jff_nopartsAutomaton.library");
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

    public void setImportParts(Boolean parts) {
        if (parts) {
            this.importParts = new Import("jfp","/res/fh-hannover/sprengel/Informatik/TheoretischeInformatik/Libraries" +
                "/jff_partsAutomaton.library");
        }
        else {
            this.importParts = new Import("jfp","/res/fh-hannover/sprengel/Informatik/TheoretischeInformatik/Libraries" +
                "/jff_nopartsAutomaton.library");
        }
    }

    public Meta getMeta() {  return meta; }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
