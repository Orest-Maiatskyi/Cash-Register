// When the page is fully loaded...
$(document).ready(function() {

    $("#cashierCreateOrder").click(function() {

        $.get("/frontController",
            {"command": "CreateOrder"},
            function(data, status) {
                new Toast({
					title: false,
					text: JSON.parse(data)["status"],
					theme: "success",
					autohide: true,
					interval: 6000,
				});
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

});