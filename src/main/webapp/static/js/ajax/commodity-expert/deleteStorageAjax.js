// When the page is fully loaded...
$(document).ready(function() {

    let storages = [];

    // LIST ALL AVAILABLE STORAGES
    document.getElementById("delete-storage-address").onfocus = function () {

        $.get("/frontController",
            {"command": "GetStorages"},
            function(data, status) {
                const jsonResp = JSON.parse(data);
                const jsonStorages = JSON.parse(jsonResp['storages'])
                storages = jsonStorages;

                $("#delete-storages-address-list").empty();
                for (let i = 0; i < storages.length; i++) $("#delete-storages-address-list").append("<option>" + storages[i] + "</option>");
                document.getElementById("delete-storage-address").value=' ';

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


    // DELETE STORAGE
    $("#delete-storage-btn").click(function() {

        let storage = $("#delete-storage-address").val();

        let warning = "";
        if (!storages.includes(storage)) warning = "The storage field is invalid.";

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
                    "command": "DeleteStorage",
					"address": storage,
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