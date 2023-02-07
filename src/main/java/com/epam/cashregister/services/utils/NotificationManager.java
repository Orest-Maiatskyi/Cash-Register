package com.epam.cashregister.services.utils;

import javax.servlet.http.HttpServletRequest;

public class NotificationManager {

    public static class Notification {
        public String text;
        public String theme;

        public Notification(String notification, String theme) {
            text = notification;
            this.theme = theme;
        }
    }

    public static void setErrorNotification(HttpServletRequest request, String notification) {
        request.getSession().setAttribute("notification", new Notification(
                notification, "danger"
        ));
    }

    public static void setWarningNotification(HttpServletRequest request, String notification) {
        request.getSession().setAttribute("notification", new Notification(
                notification, "warning"
        ));
    }

    public static void setSuccessNotification(HttpServletRequest request, String notification) {
        request.getSession().setAttribute("notification", new Notification(
                notification, "success"
        ));
    }

    public static void deleteNotification(HttpServletRequest request) {
        if (request.getSession().getAttribute("notification") != null)
            request.getSession().removeAttribute("notification");
    }

}
