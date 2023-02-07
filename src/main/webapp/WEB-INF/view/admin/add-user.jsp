<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="ISO-8859-1" isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "myTags" uri = "/WEB-INF/custom.tld"%>

<link href="../../static/css/action-page.css" rel="stylesheet" type="text/css" />

<form action="#" class="acrylic shadow action-form admin-form add-user-form">
    <div class="form-group">
        <label for="" class="control-label">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="words.role">Role</myTags:Dictionary>:</p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="words.role">Role</myTags:Dictionary>:</p>
        </label>
        <select class="form-control" id="roleSelector">
            <option class="eng-lang" value="1"><myTags:Dictionary language="eng" wordKey="cabinet.roles.cashier">Role</myTags:Dictionary></option>
            <option class="eng-lang" value="2"><myTags:Dictionary language="eng" wordKey="cabinet.roles.senior_cashier">Role</myTags:Dictionary></option>
            <option class="eng-lang" value="3"><myTags:Dictionary language="eng" wordKey="cabinet.roles.commodity_expert">Role</myTags:Dictionary></option>

            <option class="rus-lang" value="1"><myTags:Dictionary language="rus" wordKey="cabinet.roles.cashier">Role</myTags:Dictionary></option>
            <option class="rus-lang" value="2"><myTags:Dictionary language="rus" wordKey="cabinet.roles.senior_cashier">Role</myTags:Dictionary></option>
            <option class="rus-lang" value="3"><myTags:Dictionary language="rus" wordKey="cabinet.roles.commodity_expert">Role</myTags:Dictionary></option>
        </select>
    </div>
    <div class="form-group">
        <label for="" class="control-label">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="words.first.name">First Name</myTags:Dictionary>:</p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="words.first.name">First Name</myTags:Dictionary>:</p>
        </label>
        <input id="firstName" value="" class="form-control">
    </div>
    <div class="form-group">
        <label for="" class="control-label">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="words.last.name">Last Name</myTags:Dictionary>:</p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="words.last.name">Last Name</myTags:Dictionary>:</p>
        </label>
        <input id="lastName" value="" class="form-control">
    </div>
    <div class="form-group">
        <label for="" class="control-label">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="words.email">Email</myTags:Dictionary>:</p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="words.email">Email</myTags:Dictionary>:</p>
        </label>
        <input id="email" value="" class="form-control">
    </div>
    <div class="form-group">
        <label for="" class="control-label">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="words.password">Password</myTags:Dictionary>:</p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="words.password">Password</myTags:Dictionary>:</p>
        </label>
        <input id="password_1" value="" class="form-control">
    </div>
    <div class="form-group">
        <label for="" class="control-label">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="words.repeat.password">Repeat Password</myTags:Dictionary>:</p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="words.repeat.password">Repeat Password</myTags:Dictionary>:</p>
        </label>
        <input id="password_2" value="" class="form-control">
    </div>
    <div class="form-group">
        <label for="" class="control-label">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="words.icon">Icon</myTags:Dictionary>:</p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="words.icon">Icon</myTags:Dictionary>:</p>
        </label>

        <div class="custom-file">
            <label class="custom-file-label" for="inputGroupFile01" style="color: #000;">Choose file</label>
            <input id="icon" type="file" class="custom-file-input" id="inputGroupFile01">
        </div>
    </div>
    <div id="addUserBtn" type="button" class="btn btn-primary">
        <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="buttons.add.user">Add User</myTags:Dictionary></p>
        <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="buttons.add.user">Add User</myTags:Dictionary></p>
    </div>
</form>

<script src="../../../static/js/ajax/admin/addUserAjax.js"></script>