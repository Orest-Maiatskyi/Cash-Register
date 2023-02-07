package com.epam.cashregister.contollers.servlets.frontcontroller;

import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Abstract class for execute needed commands
 */
public abstract class FrontCommand {

    protected static Logger logger = Logger.getLogger(FrontCommand.class);
    protected ServletContext context;
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    /**
     * initialize request, response and servlet context
     */
    public void init(ServletContext servletContext, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        this.context = servletContext;
        this.request = servletRequest;
        this.response = servletResponse;
    }

    /**
     * execute command
     */
    public abstract void process() throws ServletException, IOException;

    /**
     * sending redirect in target
     */
    protected void redirect(String target) throws IOException {
        response.sendRedirect(target);
    }
}