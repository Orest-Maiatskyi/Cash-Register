// When the page is fully loaded...
$(document).ready(function() {

// ADD GOOD
    $("#add-storage-btn").click(function() {

        let storage = $("#add-storage-address").val();

        let warning = "";

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
                    "command": "AddStorage",
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