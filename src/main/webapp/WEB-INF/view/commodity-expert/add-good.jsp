<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="ISO-8859-1" isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "myTags" uri = "/WEB-INF/custom.tld"%>

<link href="../../static/css/commodity-expert/add-good.css" rel="stylesheet" type="text/css" />

<form action="#" class="add-good-form acrylic shadow">

    <div class="form-group">
        <label for="" class="control-label">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="field.words.code">Code:</myTags:Dictionary>:</p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="field.words.code">Code:</myTags:Dictionary>:</p>
        </label>
        <input id="add-good-code" list="add-good-codes-list" value="" class="custom-select">
        <datalist id="add-good-codes-list"></datalist>
    </div>
    <div class="form-group">
        <label for="" class="control-label">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="field.words.storage">Storage:</myTags:Dictionary>:</p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="field.words.storage">Storage:</myTags:Dictionary>:</p>
        </label>
        <input id="add-good-storage" list="add-good-storages-list" value="" class="custom-select">
        <datalist id="add-good-storages-list"></datalist>
    </div>
    <div class="form-group">
        <label for="" class="control-label">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="field.words.quantity">Quantity:</myTags:Dictionary>:</p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="field.words.quantity">Quantity:</myTags:Dictionary>:</p>
        </label>
        <input id="add-good-quantity" type="text" class="form-control d-inline" style="width: calc(100% - 60px);">
        <input class="btn btn-outline-secondary float-right" style="width: 50px;" type="button" value="PC" id="add-good-measurement-box" disabled>
    </div>
    <div id="add-good-btn" type="button" class="btn btn-primary" value="Add Good">
        <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="buttons.add.good">Add Good</myTags:Dictionary></p>
        <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="buttons.add.good">Add Good</myTags:Dictionary></p>
    </div>
</form>

<script src="../../../static/js/ajax/commodity-expert/addGoodAjax.js"></script>