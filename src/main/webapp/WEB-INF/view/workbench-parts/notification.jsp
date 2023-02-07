<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ page import="com.epam.cashregister.services.utils.NotificationManager" %>

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