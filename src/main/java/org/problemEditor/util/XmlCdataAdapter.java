package org.problemEditor.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlCdataAdapter extends XmlAdapter<String, String> {

    @Override
    public String marshal(String value) throws Exception {
        return "<![CDATA[" + value + "]]>";
    }

    @Override
    public String unmarshal(String value) throws Exception {
        return value;
    }
}
