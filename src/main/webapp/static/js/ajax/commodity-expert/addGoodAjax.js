// When the page is fully loaded...
$(document).ready(function() {


    let codes = [];
    let storages = [];
    let measurement;


    // LIST ALL AVAILABLE GOODS
    document.getElementById("add-good-code").onfocus = function () {

        $.get("/frontController",
            {"command": "GetGoodCodes"},
            function(data, status) {
                const jsonResp = JSON.parse(data);
                const jsonCodes = JSON.parse(jsonResp['codes'])
                codes = jsonCodes;

                $("#add-good-codes-list").empty();
                for (let i = 0; i < codes.length; i++) $("#add-good-codes-list").append("<option>" + codes[i] + "</option>");
                document.getElementById("add-good-code").value=' ';

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


    // LIST ALL AVAILABLE STORAGES
    document.getElementById("add-good-storage").onfocus = function () {

        $.get("/frontController",
            {"command": "GetStorages"},
            function(data, status) {
                const jsonResp = JSON.parse(data);
                const jsonStorages = JSON.parse(jsonResp['storages'])
                storages = jsonStorages;

                $("#add-good-storages-list").empty();
                for (let i = 0; i < storages.length; i++) $("#add-good-storages-list").append("<option>" + storages[i] + "</option>");
                document.getElementById("add-good-storage").value=' ';

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


    // GET AND SET CURRENT MEASUREMENT
    $("#add-good-code").change(function() {

        $.get("/frontController",
            {"command": "GetMeasurement", "code": $("#add-good-code").val()},
            function(data, status) {
                const jsonResp = JSON.parse(data);
                let jsonMeasurement = jsonResp['measurement'];
                measurement = jsonMeasurement;

                if (measurement == "N/A") $("#add-good-quantity").prop("disabled", true);
                else $("#add-good-quantity").prop("disabled", false);

                switch (measurement) {
                    case "PC":
                        if (window.CURRENT_LANG == "rus") measurement = "ШТ";
                        break;
                    case "KG":
                        if (window.CURRENT_LANG == "rus") measurement = "КГ";
                        break;
                    case "L":
                        if (window.CURRENT_LANG == "rus") measurement = "Л";
                        break;
                    case "N/A":
                        if (window.CURRENT_LANG == "rus") measurement = "Н/Д";
                        break;
                }

                $("#add-good-measurement-box").val(measurement);

            })
            .fail(function(data, status) { // on failure

            });
    });


    // BLOCK QUANTITY FIELD IF INVALID STORAGE
    $("#add-good-storage").change(function() {

        if (!storages.includes($("#add-good-storage").val())) $("#add-good-quantity").prop("disabled", true);
        else $("#add-good-quantity").prop("disabled", false);

    });


    // ADD GOOD
    $("#add-good-btn").click(function() {

        let code = $("#add-good-code").val();
        let storage = $("#add-good-storage").val();
        let quantity = $("#add-good-quantity").val();

        let warning = "";

        if (!codes.includes(code)) warning = "The code field is invalid.";
        else if (!storages.includes(storage)) warning = "The storage field is invalid.";
        else if (isNaN(quantity) || quantity.toString().indexOf('.') == -1) warning = "The quantity field must contain only float type with dot";

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
                    "command": "AddGood",
					"code": code,
					"storage": storage,
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
