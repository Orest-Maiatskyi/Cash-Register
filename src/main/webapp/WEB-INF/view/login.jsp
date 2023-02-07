<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>

<%@ page import="com.epam.cashregister.services.utils.NotificationManager" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Login</title>

        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>

        <link href="../../static/css/login-form.css" rel="stylesheet" type="text/css">
        <link href="../../static/css/toast.css" rel="stylesheet" type="text/css">
        <script src="../../static/js/toast.js"></script>

    </head>
    <body>

        <%
            NotificationManager.Notification notification = (NotificationManager.Notification) session.getAttribute("notification");
            if (notification != null) {
        %>
        <script>
            new Toast({
                title: false,
                text: "<%= notification.text %>",
                theme: "<%= notification.theme %>",
                autohide: true,
                interval: 3000,
            });
        </script>
        <%
            }
        %>

        <%
            Cookie[] cookies=request.getCookies();
        	String email = "", password = "", rememberVal="";
        	if (cookies != null) {
        	    for (Cookie cookie : cookies) {
        	        if(cookie.getName().equals("cookemail")) email = cookie.getValue();
        	        if(cookie.getName().equals("cookpass")) password = cookie.getValue();
        	        if(cookie.getName().equals("cookrem")) rememberVal = cookie.getValue();
        	    }
        	}
        %>

        <div id="login" class="vertical-center">
            <div class="container">
                <div id="login-row" class="row justify-content-center align-items-center">
                    <div id="login-column" class="col-md-6">
                        <div id="login-box" class="col-md-12">
                            <form name="loginForm" id="login-form" class="form" action="" method="post">
                                <h3 class="text-center text-info">Login</h3>
                                <div class="form-group">
                                    <label for="email" class="text-info">Email:</label><br>
                                    <input type="text" name="email" autocomplete="off" value="<%=email%>" id="username" class="form-control" required>
                                </div>
                                <div class="form-group">
                                    <label for="password" class="text-info">Password:</label><br>
                                    <input type="text" name="password" autocomplete="off" value="<%=password%>" id="password" class="form-control" required>
                                </div>
                                <div class="form-group">
                                    <label for="remember-me" class="text-info">
                                        <span>Remember me</span>
                                        <span>
                                            <input id="remember-me" name="remember" <%= "1".equals(rememberVal.trim()) ? "checked=\"checked\"" : "" %> type="checkbox">
                                        </span>
                                    </label>
                                    <br>
                                    <input type="submit" name="submit" class="btn btn-info btn-md" value="submit">
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>