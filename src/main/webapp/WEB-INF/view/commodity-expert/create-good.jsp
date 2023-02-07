<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "myTags" uri = "/WEB-INF/custom.tld"%>

<link href="../../static/css/commodity-expert/create-good.css" rel="stylesheet" type="text/css" />

<form action="#" class="create-good-form acrylic shadow">
    <div class="form-group">
        <label for="" class="control-label">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="field.words.code">Code:</myTags:Dictionary>:</p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="field.words.code">Code:</myTags:Dictionary>:</p>
        </label>
        <input type="text" class="form-control" id="create-good-code" />
    </div>

    <div class="form-group">
        <label for="" class="control-label">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="field.words.measurement">Measurement:</myTags:Dictionary>:</p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="field.words.measurement">Measurement:</myTags:Dictionary>:</p>
        </label>
        <input id="create-good-measurement" list="create-good-measurement-list" value="" class="custom-select">
        <datalist id="create-good-measurement-list"></datalist>
    </div>

    <div class="form-group">
        <label for="" class="control-label">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="field.words.title">Title:</myTags:Dictionary>:</p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="field.words.title">Title:</myTags:Dictionary>:</p>
        </label>
        <input type="text" class="form-control" id="create-good-title" />
    </div>
    <div class="form-group">
        <label for="" class="control-label">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="field.words.description">Description:</myTags:Dictionary>:</p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="field.words.description">Description:</myTags:Dictionary>:</p>
        </label>
        <textarea class="form-control" id="create-good-description"></textarea>
    </div>
    <div class="form-group">
        <label for="" class="control-label">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="field.words.price">Price:</myTags:Dictionary>:</p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="field.words.price">Price:</myTags:Dictionary>:</p>
        </label>
        <input type="text" class="form-control" id="create-good-price" />
    </div>

    <div id="create-good-btn" type="button" class="btn btn-primary" value="Create Good">
        <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="buttons.create.good">Create Good</myTags:Dictionary></p>
        <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="buttons.create.good">Create Good</myTags:Dictionary></p>
    </div>
</form>

<script src="../../../static/js/ajax/commodity-expert/createGoodAjax.js"></script>