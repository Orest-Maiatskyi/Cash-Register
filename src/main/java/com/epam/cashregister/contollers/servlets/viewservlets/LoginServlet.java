package com.epam.cashregister.contollers.servlets.viewservlets;

import com.epam.cashregister.entities.LoginBean;
import com.epam.cashregister.entities.UserBean;
import com.epam.cashregister.services.exceptions.ValidatorException;
import com.epam.cashregister.services.utils.NotificationManager;
import com.epam.cashregister.services.dao.LoginDao;
import com.epam.cashregister.services.dao.impl.LoginDaoImpl;
import com.epam.cashregister.services.validateservice.validators.LoginValidator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 The LoginServlet class is responsible for authenticating user, setting cookies (if needed) and redirecting to the workbench (if user login data is correct).
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    final static Logger logger = Logger.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        logger.info("Opened login page (login.jsp)");
        req.getRequestDispatcher("./WEB-INF/view/login.jsp").forward(req, res);
        NotificationManager.deleteNotification(req);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        NotificationManager.deleteNotification(req);

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        LoginBean loginBean = new LoginBean();
        loginBean.setEmail(email);
        loginBean.setPassword(password);

        try {
            LoginValidator.validate(loginBean);

            LoginDao loginDao = new LoginDaoImpl();
            UserBean user = loginDao.authenticate(loginBean);

            if (user != null) {
                req.getSession().setAttribute("USER", user);
                if (req.getParameter("remember") != null) {
                    String remember = req.getParameter("remember");
                    Cookie cEmail = new Cookie("cookemail", email);
                    Cookie cPassword = new Cookie("cookpass", password);
                    Cookie cRemember = new Cookie("cookrem", remember);
                    cEmail.setMaxAge(60 * 60 * 24 * 2);    // 2 days
                    cPassword.setMaxAge(60 * 60 * 24 * 2); // 2 days
                    cRemember.setMaxAge(60 * 60 * 24 * 2); // 2 days
                    res.addCookie(cEmail);
                    res.addCookie(cPassword);
                    res.addCookie(cRemember);
                }

                logger.info("Successful login");
                NotificationManager.setSuccessNotification(req, "Successful login.");
                res.sendRedirect("/workbench");

            } else {
                logger.warn("Attempt to login with incorrect login data");
                NotificationManager.setWarningNotification(req, "Incorrect email or password.");
                res.sendRedirect("/login");
            }

        } catch (ValidatorException e) {
            logger.warn(e);
            NotificationManager.setWarningNotification(req, e.getMessage());
            res.sendRedirect("/login");
        }
    }
}
