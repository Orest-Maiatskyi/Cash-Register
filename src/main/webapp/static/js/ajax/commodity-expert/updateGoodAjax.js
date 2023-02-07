// When the page is fully loaded...
$(document).ready(function() {


    class DataValidator {
		constructor (code, title, description, price) {
			this.code = code;
			this.title = title;
			this.description = description;
			this.price = price;
		}

		validateCode() {
			let warning = "";
			if (this.code === "") warning = "The code field must not be empty.";
			else if (this.code.length != 8) warning = "The code field must contain exactly 8 digits.";
			else if (this.code.match(/^[0-9]+$/) == null) warning = "The code field must contain only numbers.";
			return warning;
		}

		validateTitle() {
			let warning = "";
			if (this.title === "") warning = "The title field must not be empty.";
			else if (this.title.length < 3) warning = "The title field must contain at least 3 characters.";
			else if (this.title.length > 255) warning = "The title field can contain only up to 255 characters.";
			else if (this.title.replaceAll(' ', '').length < 3) warning = "Invalid number of spaces in title field.";
			return warning;
		}

		validateDescription() {
			let warning = "";
			if (this.description === "") warning = "The description field must not be empty.";
			else if (this.description.length < 3) warning = "The description field must contain at least 3 characters.";
			else if (this.description.replaceAll(' ', '').length < 3) warning = "Invalid number of spaces in description.";
			return warning;
		}

		validatePrice() {
			let warning = "";
			if (this.price === "") warning = "The price field must not be empty.";
			else if (isNaN(this.price) || this.price.toString().indexOf('.') == -1) warning = "The price field must contain only float type with dot.";
			return warning;
		}

		validateAll() {
			let warning = "";

			if ((warning = this.validateCode()) == "")
                if ((warning = this.validateTitle()) == "")
                    if ((warning = this.validateDescription()) == "")
                        if ((warning = this.validatePrice()) == "") { }
								

			if (warning.length != 0)
				new Toast({
					title: false,
					text: warning,
					theme: "warning",
					autohide: true,
					interval: 3000,
				});

			return warning.length == 0 ? true : false;
		}
	}


    let codes = [];
    let oldTitle = "";
    let oldDescription = "";
    let oldPrice = 0.0;


    // LIST ALL AVAILABLE GOODS
    document.getElementById("update-good-code").onfocus = function () {

        $.get("frontController",
            {"command": "GetGoodCodes"},
            function(data, status) {
                const jsonResp = JSON.parse(data);
                const jsonCodes = JSON.parse(jsonResp['codes'])
                codes = jsonCodes;

                $("#update-codes-list").empty();
                for (let i = 0; i < codes.length; i++) $("#update-codes-list").append("<option>" + codes[i] + "</option>");
                document.getElementById("update-good-code").value=' ';
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


    // GET GOOD BY USER GOOD CODE
    $("#update-good-code").change(function(event) {

        $.get("frontController",
            {"command": "GetGoodByCode",
            "code": $("#update-good-code").val()},
            function(data, status) {

                const jsonResp = JSON.parse(data);
                let goodJson = JSON.parse(jsonResp["good"]);
                let title = goodJson["title"];
                let description = goodJson["description"];
                let price = goodJson["price"];

                $("#update-good-title").val(title);
                $("#update-good-description").val(description);
                $("#update-good-price").val(price);

                oldTitle = title;
                oldDescription = description;
                oldPrice = price;
            })
            .fail(function(data, status) { //on failure
				new Toast({
					title: false,
					text: JSON.parse(data.responseText)["status"],
					theme: "danger",
					autohide: true,
					interval: 3000,
				});
			});

    });


    // UPDATE GOOD
    $("#update-good-btn").click(function(event) {

        let code = $("#update-good-code").val();
        let newTitle = $("#update-good-title").val();
        let newDescription = $("#update-good-description").val();
        let newPrice = $("#update-good-price").val();

        if (newTitle != oldTitle || newDescription != oldDescription || newPrice != oldPrice) {

            if (!new DataValidator(
                code,
                newTitle,
                newDescription,
                newPrice
            ).validateAll()) return;

            // Ajax POST request, similar to the GET request.
		    $.post("frontController", {
		        "command": "UpdateGood",
			    "code": code,
				"title": newTitle,
				"description": newDescription,
				"price": newPrice
				}, function(data, status) { // on success
					new Toast({
						title: false,
						text: "Success",
						theme: "success",
						autohide: true,
						interval: 3000,
					});

				    //document.getElementById("update-good-code").focus();
                    oldTitle = newTitle;
                    oldDescription = newDescription;
                    oldPrice = newPrice;
		    })
			.fail(function(data, status) { //on failure
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
				text: "No new data to update.",
				theme: "warning",
				autohide: true,
				interval: 3000,
			});
        }

    });


});