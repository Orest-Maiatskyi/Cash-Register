package com.epam.cashregister.contollers.servlets.frontcontroller.commands;

import com.epam.cashregister.contollers.servlets.frontcontroller.FrontCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * Overrides the process method of the FrontCommand class to change the current action stored in the session.
 */
public class ChangeCurrentActionCommand extends FrontCommand {

    @Override
    public void process() throws ServletException, IOException {

        Map<String, String[]> params = request.getParameterMap();
        String currentAction = Arrays.asList(params.get("action")).get(0);

        if (currentAction != null) {
            HttpSession session = request.getSession();
            session.setAttribute("CURRENT_ACTION", currentAction);
        }
    }
}
