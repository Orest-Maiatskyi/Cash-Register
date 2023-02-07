<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="ISO-8859-1" isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "myTags" uri = "/WEB-INF/custom.tld"%>

<link href="../../static/css/action-page.css" rel="stylesheet" type="text/css" />
<link href="../../static/css/cashier/update-good-quantity.css" rel="stylesheet" type="text/css" />

<form action="#" class="acrylic shadow action-form cashier-form add-good-form">

    <div class="form-group">
        <label for="" class="control-label">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="words.order.number">Order Number:</myTags:Dictionary>:</p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="words.order.number">Order Number:</myTags:Dictionary>:</p>
        </label>
        <input id="cashierUpdateGoodQuantityOrderId" list="cashierUpdateGoodQuantityOrderList" value="" class="custom-select">
        <datalist id="cashierUpdateGoodQuantityOrderList"></datalist>
    </div>

    <div class="form-group">
        <label for="" class="control-label">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="field.words.code">Code:</myTags:Dictionary>:</p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="field.words.code">Code:</myTags:Dictionary>:</p>
        </label>
        <input id="cashierUpdateGoodQuantityGoodCode" list="cashierUpdateGoodQuantityGoodList" value="" class="custom-select">
        <datalist id="cashierUpdateGoodQuantityGoodList"></datalist>
    </div>

    <div class="form-group">
        <label for="" class="control-label">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="field.words.quantity">Quantity:</myTags:Dictionary>:</p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="field.words.quantity">Quantity:</myTags:Dictionary>:</p>
        </label>
        <input id="cashierUpdateGoodQuantityGoodQuantity" type="text" class="form-control d-inline" style="width: calc(100% - 60px);">
        <input class="btn btn-outline-secondary float-right" style="width: 50px;" type="button" value="PC" id="cashierUpdateGoodQuantityMeasurementBox" disabled>
    </div>

    <div id="cashierUpdateGoodQuantityBtn" type="button" class="btn btn-primary" value="Add Good">
        <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="buttons.update.good.quantity">Update Good Quantity</myTags:Dictionary></p>
        <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="buttons.update.good.quantity">Update Good Quantity</myTags:Dictionary></p>
    </div>
</form>

<script src="../../../static/js/ajax/cashier/updateGoodQuantityAjax.js"></script>