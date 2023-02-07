package com.epam.cashregister.contollers.servlets.viewservlets;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 The LogoutServlet class is responsible for user logout, clearing all cookies and redirecting to the login servlet.
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(LogoutServlet.class);

    private void eraseCookie(HttpServletRequest req, HttpServletResponse res) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null)
            for (Cookie cookie : cookies) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                res.addCookie(cookie);
            }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        logger.info("Successful logout");
        eraseCookie(req, res);
        req.getSession().invalidate();
        res.sendRedirect("/login");
    }

}
