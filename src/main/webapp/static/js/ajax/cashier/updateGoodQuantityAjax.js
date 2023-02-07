// When the page is fully loaded...
$(document).ready(function() {


    let orders = [];
    let goods = [];

    let code = "";
    let title = "";
    let measurement = "";

    // LIST ALL AVAILABLE ORDERS
    document.getElementById("cashierUpdateGoodQuantityOrderId").onfocus = function () {
        $.get("/frontController",
            {"command": "GetOrdersWhichAreInProcess"},
            function(data, status) {
                const jsonResp = JSON.parse(data);
                const orders = JSON.parse(jsonResp["data"]);

                $("#cashierUpdateGoodQuantityOrderList").empty();
                for (let i = 0; i < orders.length; i++) $("#cashierUpdateGoodQuantityOrderList").append("<option>" + orders[i] + "</option>");
                document.getElementById("cashierUpdateGoodQuantityOrderId").value=' ';
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


    $("#cashierUpdateGoodQuantityOrderId").change(function() {

        $.get("/frontController",
            {"command": "GetOrderedGoodCodes",
            "orderId": $("#cashierUpdateGoodQuantityOrderId").val()},
            function(data, status) {
                const jsonResp = JSON.parse(data);
                const jsonCodes = JSON.parse(jsonResp['codes'])
                goods = jsonCodes;

                $("#cashierUpdateGoodQuantityGoodList").empty();
                for (let i = 0; i < goods.length; i++) $("#cashierUpdateGoodQuantityGoodList").append("<option>" + goods[i] + "</option>");
                document.getElementById("cashierUpdateGoodQuantityGoodCode").value=' ';
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


    // GET AND SET CURRENT MEASUREMENT
    $("#cashierUpdateGoodQuantityGoodCode").change(function() {

        $.get("/frontController",
            {"command": "GetGoodByCode",
            "code": $("#cashierUpdateGoodQuantityGoodCode").val()},
            function(data, status) {
                const jsonResp = JSON.parse(data);
                let jsonMeasurement = JSON.parse(jsonResp['good'])['measurement'];

                code = $("#cashierUpdateGoodQuantityGoodCode").val();
                title = JSON.parse(jsonResp['good'])['title'];
                measurement = jsonMeasurement;

                if (measurement == "N/A") $("#cashierUpdateGoodQuantityGoodQuantity").prop("disabled", true);
                else $("#cashierUpdateGoodQuantityGoodQuantity").prop("disabled", false);

                switch (jsonMeasurement) {
                    case "PC":
                        if (window.CURRENT_LANG == "rus") jsonMeasurement = "ШТ";
                        break;
                    case "KG":
                        if (window.CURRENT_LANG == "rus") jsonMeasurement = "КГ";
                        break;
                    case "L":
                        if (window.CURRENT_LANG == "rus") jsonMeasurement = "Л";
                        break;
                    case "N/A":
                        if (window.CURRENT_LANG == "rus") jsonMeasurement = "Н/Д";
                        break;
                }

                $("#cashierUpdateGoodQuantityMeasurementBox").val(jsonMeasurement);

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


    // ADD GOOD
    $("#cashierUpdateGoodQuantityBtn").click(function() {

        let order = $("#cashierUpdateGoodQuantityOrderId").val();
        let code = $("#cashierUpdateGoodQuantityGoodCode").val();
        let quantity = $("#cashierUpdateGoodQuantityGoodQuantity").val();

        let warning = "";

        if (order.replaceAll(" ", "").length == 0) warning = "Order number field is invalid.";
        if (code.replaceAll(" ", "").length == 0) warning = "Code field is invalid.";
        if (quantity.replaceAll(" ", "").length == 0) warning = "Quantity field is invalid.";

		if (warning.length > 0) {
        	new Toast({
        		title: false,
        		text: warning,
        		theme: "warning",
        		autohide: true,
        		interval: 3000,
            });
        	return;
        }

        $.post("/frontController", {
                    "command": "UpdateGoodQuantity",
					"orderId": order,
					"goodCode": code,
					"quantity": quantity,
				},
				function() { // on success
					new Toast({
						title: false,
						text: "Success",
						theme: "success",
						autohide: true,
						interval: 3000,
					});
				})
			.fail(function(data, status) { //on failure
			    console.log(data);
				const jsonResp = JSON.parse(data['responseText']);
				new Toast({
					title: false,
					text: jsonResp['status'],
					theme: "danger",
					autohide: true,
					interval: 3000,
				});
			});
    });


});