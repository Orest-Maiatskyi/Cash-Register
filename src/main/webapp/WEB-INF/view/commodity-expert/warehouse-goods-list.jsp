<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="ISO-8859-1" isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "myTags" uri = "/WEB-INF/custom.tld"%>

<link href="../../static/css/commodity-expert/warehouse-goods-list.css" rel="stylesheet" type="text/css" />

<div class="acrylic shadow" id="warehouse-goods-list">

    <img class="reload-btn" action="reload" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADQAAAA0CAYAAADFeBvrAAAACXBIWXMAAAsTAAALEwEAmpwYAAAC4UlEQVR4nO2aW4hNYRTHfw3jTG4z406ThBApxqgpiiYPePMgyiWmQR5EeVBye5jGPCh5cMtlRC65lPLkQS5NTAqJKCkxklEMjUvGcPTVOrXb7XPbe337nL07v1pPp9Y+/3PWXvu/1reheFgONBAjbgPvgSpiwGigD0gCJ4gBm0VMKpYQcW65BJnSqyaijAD+uASZOEmR0h+oAeY4YjowUD7f4CHGxD9gMUVAJbAWaANeAX/TfGETn4AvGT5/J/kKwizgEvArwxf0E6fCFjIOuCwloimkIF1vFdBtUUhSotN26ZUBB0IQknTEaVti+skNnyxALLUh6FiBxCRteL0tOVz0A3AfuCZd7ybwEPioJKpNS8yMNC25F7gOrAMmZMlRI2PChYDtvSGomHLgkStpF7ADGOkzZ7sPIV3AXo2y2+5I+hPYCQwKkG+YY1zIJV4CG4EKFBgiv4xJfAeYqpBzZY5C2qVETWdVYzfwXe4RLc5kENEnzcSYWCtO+S4wU/mh7NXxfgNnlSogLcMtDFp1LiFfgf3AGCLKHkfH2hflyTTFYWCTVscqUaJEtLah84gBE4FDwA9xGZFlLnDFtbaqV75GtTgNq8wHbnhYmW5lUzkFOI8lzHy0BniawWReVbzeMuAb0IQyg4FtwNscrL9xAxqjy3HJ9wYYgBKjgGbgcx5DmWkOQf+VTke+Rg0hk4GjMqXmMyq/DnDNhR7j+T2NZmBGhx6fS4wXeQ5oVVKijz1y9QCTUMK5S/ATz4EjMu0ucBynLALWAy3Agyz7hSYUMW23I6CoIHEQC0yzcESS6zFKGZbYFbKYVtuOoBx4EoIQc5q3gpCYLWtfG0KM/7sIjCVkmpWFGEd+TnlNlhcJ4FmWkkltWb2iV9a65m2R1cBQioC6NO8VOP1bhSwMU8+dWjmLtW79/dKS5j6I7LIw4VF6ZmUcaWpdXW8rMaBVxJh3FMYTAxJiQM0Zamyolym2KPgPbgOQGKAmunQAAAAASUVORK5CYII=">
    
    <div class="control-panel">
        <div class="rows-per-page-cont">
            <label>
                <p style="float: left;" class="eng-lang"><myTags:Dictionary language="eng" wordKey="words.show">Show</myTags:Dictionary></p>
                <p style="float: left;" class="rus-lang"><myTags:Dictionary language="rus" wordKey="words.show">Show</myTags:Dictionary></p>
                <select class="form-control" action="rowsPerPage">
                    <option>5</option>
                    <option>10</option>
                    <option>15</option>
                    <option>20</option>
                </select>
                <p style="float: right;" class="eng-lang"><myTags:Dictionary language="eng" wordKey="words.rows">Rows</myTags:Dictionary></p>
                <p style="float: right;" class="rus-lang"><myTags:Dictionary language="rus" wordKey="words.rows">Rows</myTags:Dictionary></p>
            </label>
        </div>

        <div class="sort-by">
             <label>
                 <p style="float: left;" class="eng-lang"><myTags:Dictionary language="eng" wordKey="words.sort.by">Sort by</myTags:Dictionary></p>
                 <p style="float: left;" class="rus-lang"><myTags:Dictionary language="rus" wordKey="words.sort.by">Sort by</myTags:Dictionary></p>
                 <select class="form-control" action="sortBy">
                    <option class="eng-lang" value="warehouse.id"><myTags:Dictionary language="eng" wordKey="words.id">#</myTags:Dictionary></option>
                    <option class="eng-lang" value="address"><myTags:Dictionary language="eng" wordKey="words.storage">storage</myTags:Dictionary></option>
                    <option class="eng-lang" value="good_code"><myTags:Dictionary language="eng" wordKey="words.code">code</myTags:Dictionary></option>
                    <option class="eng-lang" value="quantity"><myTags:Dictionary language="eng" wordKey="words.quantity">quantity</myTags:Dictionary></option>

                    <option class="rus-lang" value="warehouse.id"><myTags:Dictionary language="rus" wordKey="words.id">#</myTags:Dictionary></option>
                    <option class="rus-lang" value="address"><myTags:Dictionary language="rus" wordKey="words.storage">storage</myTags:Dictionary></option>
                    <option class="rus-lang" value="good_code"><myTags:Dictionary language="rus" wordKey="words.code">code</myTags:Dictionary></option>
                    <option class="rus-lang" value="quantity"><myTags:Dictionary language="rus" wordKey="words.quantity">quantity</myTags:Dictionary></option>
                 </select>
             </label>
        </div>

        <div class="input-group">
            <input type="text" class="form-control" placeholder="Search" action="likeData">
        </div>

    </div>

    <table class="table" cellspacing="0" style="background: #fff;" width="100%">
        <thead>
            <tr>
                <th class="th-sm">
                    <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="words.id">#</myTags:Dictionary></p>
                    <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="words.id">#</myTags:Dictionary></p>
                </th>
                <th class="th-sm">
                    <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="words.storage">storage</myTags:Dictionary></p>
                    <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="words.storage">storage</myTags:Dictionary></p>
                </th>
                <th class="th-sm">
                    <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="words.code">code</myTags:Dictionary></p>
                    <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="words.code">code</myTags:Dictionary></p>
                </th>
                <th class="th-sm">
                    <p class="eng-lang"><myTags:Dictionary language="eng" wordKey="words.quantity">quantity</myTags:Dictionary></p>
                    <p class="rus-lang"><myTags:Dictionary language="rus" wordKey="words.quantity">quantity</myTags:Dictionary></p>
                </th>
            </tr>
        </thead>
        <tbody>
        </tbody>
    </table>

    <nav action="goodListNav">
        <ul class="pagination justify-content-end">
            <li class="page-item">
                <a action="toStart" class="page-link" href="#" aria-label="Previous">
                    <span aria-hidden="true">&laquo;&laquo;</span>
                    <span class="sr-only">Previous</span>
                </a>
            </li>
            <li class="page-item">
                <a action="prev" class="page-link" href="#" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                    <span class="sr-only">Previous</span>
                </a>
            </li>
            <div style="display: inherit;">
                <li class="page-item"><a class="page-link" href="#">1</a></li>
                <li class="page-item"><a class="page-link" href="#">2</a></li>
                <li class="page-item"><a class="page-link" href="#">3</a></li>
            </div>
            <li class="page-item">
                <a action="next" class="page-link" href="#" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                    <span class="sr-only">Next</span>
                </a>
            </li>
            <li class="page-item">
                <a action="toEnd" class="page-link" href="#" aria-label="Next">
                    <span aria-hidden="true">&raquo;&raquo;</span>
                    <span class="sr-only">Next</span>
                </a>
            </li>
        </ul>
    </nav>
</div>

<script src="../../../static/js/ajax/commodity-expert/warehouseGoodsListAjax.js"></script>