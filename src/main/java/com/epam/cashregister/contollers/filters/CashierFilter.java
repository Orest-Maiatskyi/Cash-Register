package com.epam.cashregister.contollers.filters;

import com.epam.cashregister.entities.UserBean;
import com.epam.cashregister.services.consts.RolesPermissions;
import com.epam.cashregister.services.utils.NotificationManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class implements the `Filter` interface and is annotated with `@WebFilter`.
 * The `urlPatterns` attribute specifies that this filter should be applied to all URLs.
 * The purpose of this filter is to check if a user is a cashier, and if so, to validate their access to specific commands and pages.
 */
@WebFilter(urlPatterns = "/*")
public class CashierFilter implements Filter {

    final static Logger logger = Logger.getLogger(CashierFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String path = req.getRequestURI();
        UserBean user = (UserBean) req.getSession().getAttribute("USER");

        if (user != null && user.getRole().equals("cashier")) {
            if (path.startsWith("/login") || path.startsWith("/logout") || path.startsWith("/workbench")) filterChain.doFilter(servletRequest, servletResponse);
            else if (path.startsWith("/frontController")) {
                String command = req.getParameter("command");
                if (command == null) {
                    NotificationManager.setWarningNotification(req, "Incorrect command.");
                    logger.warn("Incorrect FrontController command");
                    res.sendRedirect("/workbench");
                } else {
                    HashMap<String, String[]> actions = user.getActions();
                    boolean correctUrl = false;

                    if (command.equals("SetActiveTab") || command.equals("ChangeLang")) filterChain.doFilter(servletRequest, servletResponse);
                    else {
                        for (Map.Entry<String, String[]> set : actions.entrySet()) {
                            for (String action : set.getValue()) {
                                if ((action.replaceAll(" ", "")).equals(command)) {
                                    correctUrl = true;
                                    break;
                                }
                            }
                        }

                        if (!correctUrl)
                            for (String permission : RolesPermissions.cashierPermissions)
                                if (permission.equals(command)) {
                                    correctUrl = true;
                                    break;
                                }

                        if (!correctUrl) {
                            NotificationManager.setWarningNotification(req, "Unable to: " + command + " with cashier role.");
                            logger.warn("Unable to: " + command + " with cashier role");
                            res.sendRedirect("/workbench");
                        } else filterChain.doFilter(servletRequest, servletResponse);
                    }
                }
            }

            else filterChain.doFilter(servletRequest, servletResponse);

        } else filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
