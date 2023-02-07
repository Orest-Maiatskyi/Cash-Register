<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="ISO-8859-1" isELIgnored="false"%>

<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix = "myTags" uri = "/WEB-INF/custom.tld"%>

<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Properties" %>

<%@ page import="com.epam.cashregister.entities.UserBean" %>
<%@ page import="com.epam.cashregister.services.utils.PropertiesManager" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link rel="icon" type="image/x-icon" href="../../static/img/icon.png">
        <title>Workbench</title>

        <!-- BOOTSTRAP-4 -->
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>

        <!-- LOCAL -->
        <link href="../../static/css/workbench.css" rel="stylesheet" type="text/css">
        <link href="../../static/css/acrilic.css" rel="stylesheet" type="text/css" />
        <link href="../../static/css/toast.css" rel="stylesheet" type="text/css">
        <script src="../../static/js/toast.js"></script>
    </head>
    <body>
        <%
            UserBean userBean = (UserBean) session.getAttribute("USER");
            pageContext.setAttribute("userActions", userBean.getActions());
            pageContext.setAttribute("userRole", userBean.getRole());
        %>
        <jsp:include page="/WEB-INF/view/workbench-parts/notification.jsp"/>

        <!-- Vertical navbar -->
        <div class="vertical-nav bg-white" id="sidebar">

            <!-- Info panel -->
            <div class="py-4 px-3 mb-0 bg-light">
                <div class="media d-flex align-items-center"><img src="<%="data:image/png;base64," + userBean.getAvatar() %>" alt="..." width="65" class="mr-3 rounded-circle img-thumbnail shadow-sm">
                    <div class="media-body">
                        <h4 class="m-0"><%= userBean.getFirstName() + " " + userBean.getLastName() %></h4>
                        <p class="font-weight-light text-muted mb-0 eng-lang">
                            <myTags:Dictionary language="eng" wordKey='<%= "cabinet.roles." + userBean.getRole().toLowerCase().replace(" ", "_") %>'>Role</myTags:Dictionary>
                        </p>
                        <p class="font-weight-light text-muted mb-0 rus-lang">
                            <myTags:Dictionary language="rus" wordKey='<%= "cabinet.roles." + userBean.getRole().toLowerCase().replace(" ", "_") %>'>Role</myTags:Dictionary>
                        </p>
                    </div>
                </div>
            </div>
            <!-- End info panel -->

            <!-- Language btn -->
            <div id="lang-btn" class="lang-btn">Eng</div>
            <!-- End language btn -->

            <!-- Navbar elements -->
            <c:forEach var="actionSet" items="${userActions}">
                <c:set var = "tempWordKey" scope = "session" value = "cabinet.menu.categories.${fn:toLowerCase(fn:replace(actionSet.key, ' ', '_'))}"/>
                <p class="text-gray font-weight-bold text-uppercase px-3 small pt-4 pb-4 mb-0 eng-lang">
                    <myTags:Dictionary language="eng" wordKey='${tempWordKey}'>Category</myTags:Dictionary>
                </p>
                <p class="text-gray font-weight-bold text-uppercase px-3 small pt-4 pb-4 mb-0 rus-lang">
                    <myTags:Dictionary language="rus" wordKey='${tempWordKey}'>Category</myTags:Dictionary>
                </p>
                <ul class="nav flex-column bg-white mb-0">
                <c:forEach var="action" items="${actionSet.value}">
                    <c:set var = "tempSidebarLink" scope = "session" value = "${fn:toLowerCase(fn:replace(action, ' ', '-'))}"/>
                    <c:set var = "tempWordKey" scope = "session" value = "cabinet.menu.actions.${fn:toLowerCase(fn:replace(action, ' ', '_'))}"/>
                    <li class="nav-item">
                        <a href="#" class="nav-link text-dark font-italic eng-lang" sidebarLink='${tempSidebarLink}'>
                            <i class="fa fa-th-large mr-3 text-primary fa-fw"></i>
                            <myTags:Dictionary language="eng" wordKey='${tempWordKey}'>Action</myTags:Dictionary>
                        </a>
                        <a href="#" class="nav-link text-dark font-italic rus-lang" sidebarLink="${tempSidebarLink}">
                            <i class="fa fa-th-large mr-3 text-primary fa-fw"></i>
                            <myTags:Dictionary language="rus" wordKey='${tempWordKey}'>Action</myTags:Dictionary>
                        </a>
                    </li>
                </c:forEach>
                </ul>
            </c:forEach>
            <p class="text-gray font-weight-bold text-uppercase px-3 small pt-4 pb-4 mb-0 eng-lang">
                <myTags:Dictionary language="eng" wordKey='cabinet.menu.categories.control'>Category</myTags:Dictionary>
            </p>
            <p class="text-gray font-weight-bold text-uppercase px-3 small pt-4 pb-4 mb-0 rus-lang">
                <myTags:Dictionary language="rus" wordKey='cabinet.menu.categories.control'>Category</myTags:Dictionary>
            </p>
            <ul class="nav flex-column bg-white mb-0">
                <li class="nav-item">
                    <a href="/logout" class="nav-link text-dark font-italic eng-lang">
                        <i class="fa fa-th-large mr-3 text-primary fa-fw"></i>
                        <myTags:Dictionary language="eng" wordKey='cabinet.menu.actions.logout'>Action</myTags:Dictionary>
                    </a>
                    <a href="/logout" class="nav-link text-dark font-italic rus-lang">
                        <i class="fa fa-th-large mr-3 text-primary fa-fw"></i>
                        <myTags:Dictionary language="rus" wordKey='cabinet.menu.actions.logout'>Action</myTags:Dictionary>
                    </a>
                </li>
            </ul>
        <!-- End navbar elements -->

        </div>
        <!-- End vertical navbar -->

        <!-- Page content holder -->
        <div class="page-content p-5" id="content">

            <!-- Toggle button -->
            <button id="sidebarCollapse" type="button" class="btn btn-light bg-white rounded-pill shadow-sm px-4 mb-4">
                <i class="fa fa-bars mr-2"></i>
                <small class="text-uppercase font-weight-bold">Toggle</small>
            </button>
            <!-- End toggle button -->

            <!-- Actions container -->
            <div class="actions-container">
                <c:forEach var="actionSet" items="${userActions}">
                    <c:forEach var="action" items="${actionSet.value}">
                        <c:set var = "tempSidebarLink" scope = "session" value = "${fn:toLowerCase(fn:replace(action, ' ', '-'))}"/>
                        <div class="action" id='${tempSidebarLink}'>
                            <c:set var = "tempPath" scope = "session" value = "/WEB-INF/view/${fn:toLowerCase(fn:replace(userRole, ' ', '-'))}/${fn:toLowerCase(fn:replace(action, ' ', '-'))}.jsp"/>
                            <jsp:include page='${tempPath}'/>
                        </div>
                    </c:forEach>
                </c:forEach>
            </div>
            <!-- End actions container -->

        </div>
        <!-- End Page content holder -->

        <jsp:include page="/WEB-INF/view/workbench-parts/scripts.jsp"/>

    </body>
</html>