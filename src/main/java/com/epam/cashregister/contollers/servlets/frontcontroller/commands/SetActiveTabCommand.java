package com.epam.cashregister.contollers.servlets.frontcontroller.commands;

import com.epam.cashregister.contollers.servlets.frontcontroller.FrontCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * The SetActiveTabCommand class is a subclass of FrontCommand that is responsible for storing the current action page to the session;
 * */
public class SetActiveTabCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        Map<String, String[]> params = request.getParameterMap();
        String tab = Arrays.asList(params.get("tab")).get(0);

        if (tab != null && !tab.equals("")) {
            HttpSession session = request.getSession();
            session.setAttribute("ACTIVE_TAB", tab);
        }
    }
}
