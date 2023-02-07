<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="ISO-8859-1" isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "myTags" uri = "/WEB-INF/custom.tld"%>

<link href="../../static/css/action-page.css" rel="stylesheet" type="text/css" />

<form action="#" class="acrylic shadow action-form cashier-form add-good-form">

    <div class="form-group">
        <label for="" class="control-label">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="words.order.number">Order Number:</myTags:Dictionary>:</p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="words.order.number">Order Number:</myTags:Dictionary>:</p>
        </label>
        <input id="seniorCashierCancelOrderedGoodOrderId" list="seniorCashierCancelOrderedGoodOrderList" value="" class="custom-select">
        <datalist id="seniorCashierCancelOrderedGoodOrderList"></datalist>
    </div>

    <div class="form-group">
        <label for="" class="control-label">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="field.words.code">Code:</myTags:Dictionary>:</p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="field.words.code">Code:</myTags:Dictionary>:</p>
        </label>
        <input id="seniorCashierCancelOrderedGoodGoodCode" list="seniorCashierCancelOrderedGoodGoodList" value="" class="custom-select">
        <datalist id="seniorCashierCancelOrderedGoodGoodList"></datalist>
    </div>

    <div id="seniorCashierCancelOrderedGoodBtn" type="button" class="btn btn-primary" value="Cancel Good">
        <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="buttons.cancel.good">Cancel Good</myTags:Dictionary></p>
        <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="buttons.cancel.good">Cancel Good</myTags:Dictionary></p>
    </div>
</form>

<script src="../../../static/js/ajax/senior-cashier/cancelOrderedGoodAjax.js"></script>