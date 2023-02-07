package com.epam.cashregister.contollers.servlets.frontcontroller.commands;

import com.epam.cashregister.contollers.servlets.frontcontroller.FrontCommand;
import com.epam.cashregister.services.utils.AjaxResponseWriter;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

/**
 * The UnknownCommand class is a subclass of FrontCommand that is responsible for processing invalid commands (commands that don't exist).;
 * */
public class UnknownCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        AjaxResponseWriter.write(response, 400, Map.of("status", "Unknown command: " + request.getParameter("command")));
    }
}
