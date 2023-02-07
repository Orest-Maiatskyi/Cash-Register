package com.epam.cashregister.contollers.servlets.frontcontroller.commands;

import com.epam.cashregister.contollers.servlets.frontcontroller.FrontCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 The ChangeLangCommand class is a subclass of FrontCommand that is responsible for storing user language choice in user session.
 */
public class ChangeLangCommand extends FrontCommand {

    @Override
    public void process() throws ServletException, IOException {

        Map<String, String[]> params = request.getParameterMap();
        String lang = Arrays.asList(params.get("lang")).get(0);

        if (lang != null && (lang.equals("eng") || lang.equals("rus"))) {
            HttpSession session = request.getSession();
            session.setAttribute("LANG", lang);
        }
    }

}
