<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="ISO-8859-1" isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "myTags" uri = "/WEB-INF/custom.tld"%>

<link href="../../static/css/commodity-expert/update-good.css" rel="stylesheet" type="text/css" />

<form action="#" class="update-good-form acrylic shadow">
    <div class="form-group">
        <label for="" class="control-label">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="field.words.code">Code</myTags:Dictionary>:</p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="field.words.code">Code</myTags:Dictionary>:</p>
        </label>
        <input id="update-good-code" list="update-codes-list" value="" class="custom-select">
        <datalist id="update-codes-list"></datalist>
    </div>
    <div class="form-group">
        <label for="" class="control-label">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="field.words.title">Title</myTags:Dictionary>:</p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="field.words.title">Title</myTags:Dictionary>:</p>
        </label>
        <input type="text" class="form-control" id="update-good-title" />
    </div>
    <div class="form-group">
        <label for="" class="control-label">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="field.words.description">Description</myTags:Dictionary>:</p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="field.words.description">Description</myTags:Dictionary>:</p>
        </label>
        <textarea class="form-control" id="update-good-description"></textarea>
    </div>
    <div class="form-group">
        <label for="" class="control-label">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="field.words.price">Price</myTags:Dictionary>:</p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="field.words.price">Price</myTags:Dictionary>:</p>
        </label>
        <input type="text" class="form-control" id="update-good-price" />
    </div>

    <div id="update-good-btn" type="button" class="btn btn-primary" value="Update Good">
        <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="buttons.update.good">Update Good</myTags:Dictionary></p>
        <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="buttons.update.good">Update Good</myTags:Dictionary></p>
    </div>
</form>

<script src="../../../static/js/ajax/commodity-expert/updateGoodAjax.js"></script>