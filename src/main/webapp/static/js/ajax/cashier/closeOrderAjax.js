// When the page is fully loaded...
$(document).ready(function() {

    let ordersId = [];

    // LIST ALL AVAILABLE ORDERS
    document.getElementById("cashierCloseOrderOrderId").onfocus = function () {
        $.get("/frontController",
            {"command": "GetOrdersWhichAreInProcess"},
            function(data, status) {
                const jsonResp = JSON.parse(data);
                const orders = JSON.parse(jsonResp["data"]);
                ordersId = orders;

                $("#cashierCloseOrderOrderList").empty();
                for (let i = 0; i < orders.length; i++) $("#cashierCloseOrderOrderList").append("<option>" + orders[i] + "</option>");
                document.getElementById("cashierCloseOrderOrderId").value=' ';
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


    // ADD GOOD
    $("#cashierCloseOrderBtn").click(function() {

        let order = $("#cashierCloseOrderOrderId").val();
        let email = $("#cashierCloseOrderEmail").val();
        let warning = "";
        let regex = new RegExp('[a-z0-9]+@[a-z]+\.[a-z]{2,3}');

        if (order.replaceAll(" ", "").length == 0) warning = "Order number field is invalid.";
        else if (!ordersId.includes(parseInt(order))) warning = "Order number is invalid.";
        else if (email != undefined && email.length != 0 && !regex.test(email)) warning = "Invalid email.";

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

        $.get("/frontController",
        {"command": "CloseOrder",
        "orderId": $("#cashierCloseOrderOrderId").val(),
        "email": email},
				function(data, status) { // on success

                    let atobData = window.atob(data);
                    var num = new Array(atobData.length);
                    for (var i = 0; i < atobData.length; i++) {
                        num[i] = atobData.charCodeAt(i);
                    }
                    var pdfData = new Uint8Array(num);

                    //var blob = new Blob([pdfData], { type: 'text/plain' });
                    blob = new Blob([pdfData], { type: 'application/pdf;base64' });
                    var url = URL.createObjectURL(blob);


                    //Download the file.
                    var a = document.createElement('a');
                    a.href = url;
                    a.download = 'File.pdf';
                    a.click();

					new Toast({
						title: false,
						text: "Success",
						theme: "success",
						autohide: true,
						interval: 3000,
					});

				    ordersId = [];
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