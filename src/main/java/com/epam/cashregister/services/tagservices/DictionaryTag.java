package com.epam.cashregister.services.tagservices;


import com.epam.cashregister.services.utils.PropertiesManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;

public class DictionaryTag extends SimpleTagSupport {

    private String language;
    private String wordKey;

    private static Properties ENG_PROP, RUS_PROP;

    {
        if (ENG_PROP == null) ENG_PROP = PropertiesManager.getPropertyFile("language_eng.properties");
        if (RUS_PROP == null) RUS_PROP = PropertiesManager.getPropertyFile("language_rus.properties");
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setWordKey(String wordKey) {
        this.wordKey = wordKey;
    }

    StringWriter sw = new StringWriter();

    public void useMessageFromTheBody() throws JspException, IOException {
        getJspBody().invoke(sw);
        getJspContext().getOut().println(sw.toString());
    }

    @Override
    public void doTag() throws JspException, IOException {

        if (ENG_PROP != null && RUS_PROP != null && language != null && wordKey != null) {
            if (language.equals("eng") || language.equals("rus")) {

                String word = null;
                if (language.equals("eng")) word = ENG_PROP.getProperty(wordKey);
                if (language.equals("rus")) word = RUS_PROP.getProperty(wordKey);

                if (word != null) getJspContext().getOut().println(word);
                else useMessageFromTheBody();

            } else useMessageFromTheBody();
        } else useMessageFromTheBody();
    }
}
