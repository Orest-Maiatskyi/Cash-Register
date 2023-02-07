package com.epam.cashregister.contollers.filters;

import com.epam.cashregister.contollers.servlets.viewservlets.LoginServlet;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The `LoginFilter` class implements the `Filter` interface and is annotated with `@WebFilter` to filter requests to the web application.
 * It uses the `urlPatterns` attribute of `@WebFilter` to match all URLs in the web application.
 * The filter is responsible for checking if the user is authorized to access a certain URL.
 * If the user is not authorized, it redirects the user to the login page.
 * The filter uses a logger to log any unauthorized attempts to access a URL.
 */
@WebFilter(urlPatterns = "/*")
public class LoginFilter implements Filter {

    final static Logger logger = Logger.getLogger(LoginServlet.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String path = req.getRequestURI();

        if (req.getSession().getAttribute("USER") != null || path.startsWith("/login") || path.startsWith("/static"))
            filterChain.doFilter(servletRequest, servletResponse);
        else {
            logger.warn("Attempt to get to the " + path + " without authorization");
            res.sendRedirect("/login");
        }

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
