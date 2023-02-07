// When the page is fully loaded...
$(document).ready(function() {

    let ordersId = [];
    let goods = [];

    // LIST ALL AVAILABLE ORDERS
    document.getElementById("seniorCashierCancelOrderedGoodOrderId").onfocus = function () {
        $.get("/frontController",
            {"command": "GetOrdersWhichAreInProcess"},
            function(data, status) {
                const jsonResp = JSON.parse(data);
                const orders = JSON.parse(jsonResp["data"]);
                ordersId = orders;

                $("#seniorCashierCancelOrderedGoodOrderList").empty();
                for (let i = 0; i < orders.length; i++) $("#seniorCashierCancelOrderedGoodOrderList").append("<option>" + orders[i] + "</option>");
                document.getElementById("seniorCashierCancelOrderedGoodOrderId").value=' ';
            })
            .fail(function(data, status) { // on failure
                new Toast({
					title: false,
					text: JSON.parse(data.responseText)["status"],
					theme: "danger",
					autohide: true,
					interval: 3000,
				});
            });
    }


    // LIST ALL ORDERED GOODS
    $("#seniorCashierCancelOrderedGoodOrderId").change(function() {

        $.get("/frontController",
            {"command": "GetOrderedGoodCodes",
            "orderId": $("#seniorCashierCancelOrderedGoodOrderId").val()},
            function(data, status) {
                const jsonResp = JSON.parse(data);
                const jsonCodes = JSON.parse(jsonResp['codes'])
                goods = jsonCodes;

                $("#seniorCashierCancelOrderedGoodGoodList").empty();
                for (let i = 0; i < goods.length; i++) $("#seniorCashierCancelOrderedGoodGoodList").append("<option>" + goods[i] + "</option>");
                document.getElementById("seniorCashierCancelOrderedGoodGoodCode").value=' ';
            })
            .fail(function(data, status) { // on failure
                new Toast({
					title: false,
					text: JSON.parse(data.responseText)["status"],
					theme: "danger",
					autohide: true,
					interval: 3000,
				});
            });
    });


    // CANCEL ORDERED GOOD
    $("#seniorCashierCancelOrderedGoodBtn").click(function() {

        if (ordersId.includes(parseInt($("#seniorCashierCancelOrderedGoodOrderId").val()))) {
            if (goods.includes($("#seniorCashierCancelOrderedGoodGoodCode").val())) {
                $.get("/frontController",
                    {"command": "CancelOrderedGood",
                    "orderId": $("#seniorCashierCancelOrderedGoodOrderId").val(),
                    "goodCode": $("#seniorCashierCancelOrderedGoodGoodCode").val()},
                    function(data, status) {
                        new Toast({
                            title: false,
                            text: "Success",
                            theme: "success",
                            autohide: true,
                            interval: 3000,
                        });
                        goods = [];
                    })
                    .fail(function(data, status) { // on failure
                        new Toast({
                            title: false,
                            text: JSON.parse(data.responseText)["status"],
                            theme: "danger",
                            autohide: true,
                            interval: 3000,
                        });
                    });
            } else {
                new Toast({
                    title: false,
                    text: "Incorrect good code.",
                    theme: "warning",
                    autohide: true,
                    interval: 3000,
                });
            }
        } else {
            new Toast({
                title: false,
                text: "Incorrect order id.",
                theme: "warning",
                autohide: true,
                interval: 3000,
            });
        }
    });


});