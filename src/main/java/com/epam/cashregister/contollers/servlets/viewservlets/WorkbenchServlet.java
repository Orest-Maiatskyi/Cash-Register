package com.epam.cashregister.contollers.servlets.viewservlets;

import com.epam.cashregister.services.utils.NotificationManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 The WorkbenchServlet class is responsible for viewing workbench.jsp page.
 */
@WebServlet("/workbench")
public class WorkbenchServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(WorkbenchServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        logger.info("Opened workbench page (workbench.jsp)");
        req.getRequestDispatcher("./WEB-INF/view/workbench.jsp").forward(req, res);
        NotificationManager.deleteNotification(req);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        super.doPost(req, res);
    }
}
