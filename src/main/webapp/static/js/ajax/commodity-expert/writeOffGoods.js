// When the page is fully loaded...
$(document).ready(function() {

    class Storage {
        constructor(id, address) {
            this.id = id;
            this.address = address;
        }
    }

    let goodCodes = [];
    let goodStorages = [];


    // LIST ALL AVAILABLE GOODS
    document.getElementById("write-off-good-code").onfocus = function () {

        $.get("/frontController",
            {"command": "GetGoodCodes"},
            function(data, status) {
                const jsonResp = JSON.parse(data);
                const codes = JSON.parse(jsonResp['codes'])
                goodCodes = codes;

                $("#write-off-codes-list").empty();
                for (let i = 0; i < codes.length; i++) $("#write-off-codes-list").append("<option>" + codes[i] + "</option>");
                document.getElementById("write-off-good-code").value=' ';

            })
            .fail(function(data, status) { // on failure

            });
    }


    // LIST ALL AVAILABLE STORAGES
    $("#write-off-good-code").change(function(event) {

        $.get("/frontController",
            {"command": "GetStoragesForGood",
            "code": $("#write-off-good-code").val()},
            function(data, status) {
                const jsonResp = JSON.parse(data);
                const storages = JSON.parse(jsonResp['storages'])

                goodStorages = [];
                $("#write-off-storages-list").empty();
                for (let i = 0; i < storages.length; i++) {
                    goodStorages.push(new Storage(storages[i]["id"], storages[i]["address"]));
                    $("#write-off-storages-list").append("<option>" + goodStorages[i].address + "</option>");
                }
                document.getElementById("write-off-good-storage").value=' ';

                if (goodStorages.length > 0) $( "#write-off-good-storage" ).prop( "disabled", false );
                else $( "#write-off-good-storage" ).prop( "disabled", true );

            })
            .fail(function(data, status) { // on failure

            });

        $.get("/frontController",
            {"command": "GetMeasurement", "code": $("#write-off-good-code").val()},
            function(data, status) {
                const jsonResp = JSON.parse(data);
                let measurement = jsonResp['measurement'];
                current_measurement = measurement;

                if (measurement == "N/A") $( "#write-off-good-quantity" ).prop( "disabled", true );
                else $( "#write-off-good-quantity" ).prop( "disabled", false );

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

                $("#write-off-measurement-box").val(measurement);

            })
            .fail(function(data, status) { // on failure

            });

    });


    // WRITE OFF GOOD
    $("#write-off-good-btn").click(function(event) {

        let code = $("#write-off-good-code").val();
        let storage = $("#write-off-good-storage").val();
        let quantity = $("#write-off-good-quantity").val();

        let correctStorageAddress = false;
        for (let i = 0; i < goodStorages.length; i++) {
            let currentStorage = goodStorages[i];
            if (currentStorage.address == storage) {
                storage = currentStorage.id;
                correctStorageAddress = true;
                break;
            }
        }

        let warning = "";

        if (!goodCodes.includes(code)) warning = "The code field is invalid.";
        else if (!correctStorageAddress) warning = "The storage field is invalid.";
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
                    "command": "WriteOffTheGoods",
					"code": code,
					"storageId": storage,
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