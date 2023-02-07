// When the page is fully loaded...
$(document).ready(function() {

    let ordersId = [];

    // LIST ALL AVAILABLE ORDERS
    document.getElementById("seniorCashierCancelOrderOrderId").onfocus = function () {
        $.get("/frontController",
            {"command": "GetOrdersWhichAreInProcess"},
            function(data, status) {
                const jsonResp = JSON.parse(data);
                const orders = JSON.parse(jsonResp["data"]);
                ordersId = orders;

                $("#seniorCashierCancelOrderOrderList").empty();
                for (let i = 0; i < orders.length; i++) $("#seniorCashierCancelOrderOrderList").append("<option>" + orders[i] + "</option>");
                document.getElementById("seniorCashierCancelOrderOrderId").value=' ';
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


    // CANCEL ORDER
    $("#seniorCashierCancelOrderBtn").click(function() {

        if (ordersId.includes(parseInt($("#seniorCashierCancelOrderOrderId").val()))) {
            $.get("/frontController",
                {"command": "CancelOrder",
                "orderId": $("#seniorCashierCancelOrderOrderId").val()},
                function(data, status) {
                    new Toast({
						title: false,
						text: "Success",
						theme: "success",
						autohide: true,
						interval: 3000,
					});
					ordersId = [];
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
                text: "Incorrect order id.",
                theme: "warning",
                autohide: true,
                interval: 3000,
            });
        }
    });
});