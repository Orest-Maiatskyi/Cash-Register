package com.epam.cashregister.contollers.servlets.frontcontroller;

import com.epam.cashregister.contollers.servlets.frontcontroller.commands.UnknownCommand;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The FrontControllerServlet class is a servlet that acts as the front controller of the application,
 * handling all incoming HTTP requests and dispatching them to the appropriate command class.
 * The servlet is annotated with @WebServlet, specifying the name and value of the servlet in the application.
 * It is also annotated with @MultipartConfig, which configures the servlet to handle file uploads with a specified
 * file size threshold, maximum file size, and maximum request size.
 * The servlet contains two main methods, doPost and doGet, which handle HTTP POST and GET requests respectively.
 * Both methods retrieve the appropriate command class for the incoming request using the getCommand method,
 * and then call the command's init and process methods to process the request.
 * The getCommand method uses reflection to dynamically instantiate the appropriate command class based on the value of
 * the "command" parameter in the incoming request. If the specified command class cannot be found, an instance of the
 * UnknownCommand class is returned.
 * The class also uses a logger to log the type of command being processed and any exceptions or errors that may occur.
 */
@WebServlet(name = "FrontControllerServlet", value = "/frontController")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,     // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class FrontControllerServlet extends HttpServlet {

    static Logger logger = Logger.getLogger(FrontControllerServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FrontCommand command = getCommand(request);
        logger.debug("Post command: " + command.getClass());
        try {
            command.init(getServletContext(), request, response);
        } catch (UnsupportedOperationException e){
            logger.info("Empty initCommand");
        }
        command.process();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FrontCommand command = getCommand(request);
        logger.debug("Get command: " + command.getClass());
        try {
            command.init(getServletContext(), request, response);
        } catch (UnsupportedOperationException e){
            logger.info("Empty initCommand");
        }
        command.process();
    }

    private FrontCommand getCommand(HttpServletRequest request) {
        try {
            Class<?> type = Class.forName(String.format( "com.epam.cashregister.contollers.servlets.frontcontroller.commands.%sCommand", request.getParameter("command")));
            return type.asSubclass(FrontCommand.class).getDeclaredConstructor().newInstance();
        } catch (Exception e) { return new UnknownCommand(); }
    }
}
