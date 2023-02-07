<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="ISO-8859-1" isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "myTags" uri = "/WEB-INF/custom.tld"%>

<link href="../../static/css/commodity-expert/write-off-goods.css" rel="stylesheet" type="text/css" />

<form action="#" class="write-off-good-form acrylic shadow">

    <div class="form-group">
        <label for="" class="control-label">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="field.words.code">Code:</myTags:Dictionary>:</p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="field.words.code">Code:</myTags:Dictionary>:</p>
        </label>
        <input id="write-off-good-code" list="write-off-codes-list" value="" class="custom-select">
        <datalist id="write-off-codes-list"></datalist>
    </div>
    <div class="form-group">
        <label for="" class="control-label">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="field.words.storage">Storage:</myTags:Dictionary>:</p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="field.words.storage">Storage:</myTags:Dictionary>:</p>
        </label>
        <input id="write-off-good-storage" list="write-off-storages-list" value="" class="custom-select">
        <datalist id="write-off-storages-list"></datalist>
    </div>
    <div class="form-group">
        <label for="" class="control-label">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="field.words.quantity">Quantity:</myTags:Dictionary>:</p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="field.words.quantity">Quantity:</myTags:Dictionary>:</p>
        </label>
        <input id="write-off-good-quantity" type="text" class="form-control d-inline" style="width: calc(100% - 60px);">
        <input class="btn btn-outline-secondary float-right" style="width: 50px;" type="button" value="PC" id="write-off-measurement-box" disabled>
    </div>
    <div id="write-off-good-btn" type="button" class="btn btn-primary" value="Add Good">
        <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="buttons.write.off.good">Write Off</myTags:Dictionary></p>
        <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="buttons.write.off.good">Write Off</myTags:Dictionary></p>
    </div>
</form>

<script src="../../../static/js/ajax/commodity-expert/writeOffGoods.js"></script>