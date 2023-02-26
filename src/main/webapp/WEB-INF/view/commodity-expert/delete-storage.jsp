<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="ISO-8859-1" isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "myTags" uri = "/WEB-INF/custom.tld"%>

<link href="../../static/css/commodity-expert/add-good.css" rel="stylesheet" type="text/css" />

<form action="#" class="add-good-form acrylic shadow">

    <div class="form-group">
        <label for="" class="control-label">
            <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="words.address">Address:</myTags:Dictionary>:</p>
            <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="words.address">Address:</myTags:Dictionary>:</p>
        </label>
        <input id="delete-storage-address" list="delete-storages-address-list" value="" class="custom-select">
        <datalist id="delete-storages-address-list"></datalist>
    </div>
    <div id="delete-storage-btn" type="button" class="btn btn-primary" value="Add Storage">
        <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="buttons.delete.storage">Delete Storage</myTags:Dictionary></p>
        <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="buttons.delete.storage">Delete Storage</myTags:Dictionary></p>
    </div>
</form>

<script src="../../../static/js/ajax/commodity-expert/deleteStorageAjax.js"></script>