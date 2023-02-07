// When the page is fully loaded...
$(document).ready(function() {


    let orders = [];
    let goods = [];

    let code = "";
    let title = "";
    let measurement = "";

    // LIST ALL AVAILABLE ORDERS
    document.getElementById("cashierOrderId").onfocus = function () {
        $.get("/frontController",
            {"command": "GetOrdersWhichAreInProcess"},
            function(data, status) {
                const jsonResp = JSON.parse(data);
                const orders = JSON.parse(jsonResp["data"]);

                $("#cashierOrderList").empty();
                for (let i = 0; i < orders.length; i++) $("#cashierOrderList").append("<option>" + orders[i] + "</option>");
                document.getElementById("cashierOrderId").value=' ';
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


    // LIST ALL AVAILABLE GOODS
    document.getElementById("cashierGoodCode").onfocus = function () {
        $.get("/frontController",
            {"command": "GetWarehouseGoodCodes"},
            function(data, status) {
                const jsonResp = JSON.parse(data);
                const jsonCodes = JSON.parse(jsonResp['codes'])
                goods = jsonCodes;

                $("#cashierGoodList").empty();
                for (let i = 0; i < goods.length; i++) $("#cashierGoodList").append("<option>" + goods[i] + "</option>");
                document.getElementById("cashierGoodCode").value=' ';
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


    // LIST ALL AVAILABLE TITLES
    document.getElementById("cashierGoodTitle").oninput = function () {
        $.get("/frontController",
            {"command": "GetGoodsByLikeTitle",
            "title": "%" + $("#cashierGoodTitle").val() + "%"},
            function(data, status) {
                const jsonResp = JSON.parse(data);
                const jsonGoods = JSON.parse(jsonResp['data']);

                let titles = [];
                for (let i = 0; i < jsonGoods.length; i++) {
                    titles.push(jsonGoods[i]["title"]);
                }

                $("#cashierGoodTitleList").empty();
                for (let i = 0; i < titles.length; i++) $("#cashierGoodTitleList").append("<option>" + titles[i] + "</option>");
                //document.getElementById("cashierGoodTitle").value=' ';
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


    $("#cashierGoodTitle").change(function() {
        if ($("#cashierGoodTitle").val() != "") {
            $.get("/frontController",
                {"command": "GetGoodByTitle",
                "title": $("#cashierGoodTitle").val()},
                function(data, status) {
                    const jsonResp = JSON.parse(data);
                    let jsonMeasurement = JSON.parse(jsonResp['good'])['measurement'];

                    code = JSON.parse(jsonResp['good'])['code'];
                    title = JSON.parse(jsonResp['good'])['title'];
                    measurement = jsonMeasurement;

                        if (measurement == "N/A") $("#cashierGoodQuantity").prop("disabled", true);
                        else $("#cashierGoodQuantity").prop("disabled", false);

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

                        $("#cashierGoodCode").val(code);
                        // $("#cashierGoodTitle").val(title);
                        $("#cashierMeasurementBox").val(jsonMeasurement);
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
    });


    // GET AND SET CURRENT MEASUREMENT
    $("#cashierGoodCode").change(function() {

        $.get("/frontController",
            {"command": "GetGoodByCode",
            "code": $("#cashierGoodCode").val()},
            function(data, status) {
                const jsonResp = JSON.parse(data);
                let jsonMeasurement = JSON.parse(jsonResp['good'])['measurement'];

                code = $("#cashierGoodCode").val();
                title = JSON.parse(jsonResp['good'])['title'];
                measurement = jsonMeasurement;

                if (measurement == "N/A") $("#cashierGoodQuantity").prop("disabled", true);
                else $("#cashierGoodQuantity").prop("disabled", false);

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

                $("#cashierGoodTitle").val(title);
                $("#cashierMeasurementBox").val(jsonMeasurement);

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
    $("#cashierAddGoodBtn").click(function() {

        let order = $("#cashierOrderId").val();
        let code = $("#cashierGoodCode").val();
        let title = $("#cashierGoodTitle").val();
        let quantity = $("#cashierGoodQuantity").val();

        let warning = "";

        if (order.replaceAll(" ", "").length == 0) warning = "Order number field is invalid.";
        if (code.replaceAll(" ", "").length == 0) warning = "Code field is invalid.";
        if (title.replaceAll(" ", "").length == 0) warning = "Title field is invalid.";
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
                    "command": "AddGoodToOrder",
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