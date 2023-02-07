<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="ISO-8859-1" isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "myTags" uri = "/WEB-INF/custom.tld"%>

<link href="../../static/css/action-page.css" rel="stylesheet" type="text/css" />
<link href="../../static/css/button.css" rel="stylesheet" type="text/css" />

<form action="#" class="acrylic shadow action-form senior-cashier-form ">

    <div class="form-group button-form">
        <div class="button" id="seniorCashierCreateZReport">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="cabinet.menu.actions.create_z_report">Create Z Report</myTags:Dictionary></p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="cabinet.menu.actions.create_z_report">Create Z Report</myTags:Dictionary></p>
        </div>
    </div>
</form>

<script src="../../../static/js/ajax/senior-cashier/createZReportAjax.js"></script>