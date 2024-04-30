package org.exerciseEditor.model.xmlData;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import org.exerciseEditor.model.xmlData.xmlTags.*;

import java.util.ArrayList;
import java.util.List;


@JacksonXmlRootElement(localName = "problem")
public class AutomatonDTO {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "import")
    private List<Import> imports;
    @JacksonXmlProperty(localName = "script")
    private Script script;
    @JacksonXmlProperty(localName = "startouttext")
    private String startOutText;
    @JacksonXmlProperty(localName = "translated")
    private Translated translated;
    @JacksonXmlProperty(localName = "endouttext")
    private String endOutText;

    public AutomatonDTO() {
        this.imports = new ArrayList<>();
        imports.add(new Import("JFCH", "", "/res/fh-hannover/sprengel/Informatik/TheoretischeInformatik/Libraries/JFlap_call_preparation.library"));
        imports.add(new Import("jffperllib", "", "/res/fh-hannover/sprengel/Informatik/TheoretischeInformatik/Libraries/Automata.library"));
        imports.add(new Import("11", "", "/res/fh-hannover/sprengel/Informatik/TheoretischeInformatik/Libraries/jffautomata.library"));
    }

    public List<Import> getImports() {
        return imports;
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

    public String getStartOutText() {
        return startOutText;
    }

    public String getEndOutText() {
        return endOutText;
    }
}
