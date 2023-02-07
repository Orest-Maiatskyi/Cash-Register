<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="ISO-8859-1" isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "myTags" uri = "/WEB-INF/custom.tld"%>

<link href="../../static/css/action-page.css" rel="stylesheet" type="text/css" />

<form action="#" class="acrylic shadow action-form cashier-form add-good-form">

    <div class="form-group">
        <label for="" class="control-label">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="words.order.number">Order Number</myTags:Dictionary>:</p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="words.order.number">Order Number</myTags:Dictionary>:</p>
        </label>
        <input id="cashierCloseOrderOrderId" list="cashierCloseOrderOrderList" value="" class="custom-select">
        <datalist id="cashierCloseOrderOrderList"></datalist>
    </div>

    <div class="form-group">
        <label for="" class="control-label">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="words.email">Email</myTags:Dictionary>:</p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="words.email">Email</myTags:Dictionary>:</p>
        </label>
        <input id="cashierCloseOrderEmail" value="" class="form-control">
    </div>

    <div id="cashierCloseOrderBtn" type="button" class="btn btn-primary" value="Add Good">
        <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="buttons.close.order">Close Order</myTags:Dictionary></p>
        <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="buttons.close.order">Close Order</myTags:Dictionary></p>
    </div>
</form>

<script src="../../../static/js/ajax/cashier/closeOrderAjax.js"></script>