<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="ISO-8859-1" isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "myTags" uri = "/WEB-INF/custom.tld"%>

<link href="../../static/css/action-page.css" rel="stylesheet" type="text/css" />

<form action="#" class="acrylic shadow action-form admin-form remove-user-form">
    <div class="form-group">
        <label for="" class="control-label">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="words.email">Email</myTags:Dictionary>:</p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="words.email">Email</myTags:Dictionary>:</p>
        </label>
        <input id="deleteEmail" list="update-codes-list" value="" class="custom-select">
        <datalist id="update-codes-list"></datalist>
    </div>
    <div id="deleteUserBtn" type="button" class="btn btn-primary" value="">
        <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="buttons.remove.user">Delete User</myTags:Dictionary></p>
        <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="buttons.remove.user">Delete User</myTags:Dictionary></p>
    </div>
</form>

<script src="../../../static/js/ajax/admin/deleteUserAjax.js"></script>