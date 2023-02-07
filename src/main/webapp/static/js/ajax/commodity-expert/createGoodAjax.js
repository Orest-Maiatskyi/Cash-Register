// When the page is fully loaded...
$(document).ready(function() {


	class Measurement {
		constructor (id, name, rusName) {
			this.id = id;
			this.name = name;
			this.rusName = rusName;
		}
	}


	class DataValidator {
		constructor (code, measurements, currentMeasurement, title, description, price) {
			this.code = code;
			this.measurements = measurements;
			this.currentMeasurement = currentMeasurement;
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

		validateMeasurement() {
			let warning = "";
			if (this.currentMeasurement === null) warning = "You must select the measure units.";
			else if (!this.measurements.includes(this.currentMeasurement)) warning = "The measurement field is incorrect.";
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
                if ((warning = this.validateMeasurement()) == "")
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


    let measurements = [];
    let currentMeasurement = "";


	// LIST ALL AVAILABLE MEASUREMENTS FROM DB
    document.getElementById("create-good-measurement").onfocus = function () {

        $.get("frontController",
            {"command": "GetMeasurements"},
            function(data, status) {

                const jsonResp = JSON.parse(data);
                let jsonMeasurements = JSON.parse(jsonResp['measurements']);
                measurements = []

                $("#create-good-measurement-list").empty();

                for (let i = 0; i < jsonMeasurements.length; i++) {

					let measurementId = jsonMeasurements[i]["id"];
                    let measurementName = jsonMeasurements[i]["name"];
					let measurementRusName = jsonMeasurements[i]["name"];

                    switch (measurementName) {
                        case "PC":
                            if (window.CURRENT_LANG == "rus") measurementRusName = "ШТ";
                            break;
                        case "KG":
                            if (window.CURRENT_LANG == "rus") measurementRusName = "КГ";
                            break;
                        case "L":
                            if (window.CURRENT_LANG == "rus") measurementRusName = "Л";
                            break;
                    }

					measurements.push(new Measurement(measurementId, measurementName, measurementRusName));
					
                    $("#create-good-measurement-list").append("<option>" + (window.CURRENT_LANG == "eng" ? measurementName : measurementRusName) + "</option>");

                }

                document.getElementById("create-good-measurement").value=' ';

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


    // REMEMBER WHAT MEASUREMENT THE USER HAS CHOSEN
    $("#create-good-measurement").change(function() {

        let tempMeasurementName = $("#create-good-measurement").val();

        for (let i = 0; i < measurements.length; i++) {

			if (window.CURRENT_LANG == "eng") {
				if (measurements[i].name == tempMeasurementName) {
					currentMeasurement = measurements[i];
					break;
				}
			} else {
				if (measurements[i].rusName == tempMeasurementName) {
					currentMeasurement = measurements[i];
					break;
				}
			}
		}
    });


    // CREATE GOOD
	$("#create-good-btn").click(function() {

		let code = $("#create-good-code").val();
		let measurement = currentMeasurement;
		let title = $("#create-good-title").val();
		let description = $("#create-good-description").val();
		let price = $("#create-good-price").val();

		if (!new DataValidator(
			code,
			measurements,
			currentMeasurement,
			title,
			description,
			price
		).validateAll()) return;

		// Ajax POST request, similar to the GET request.
		$.post("frontController", {
		            "command": "CreateGood",
					"code": code,
					"measurementId": measurement.id,
					"title": title,
					"description": description,
					"price": price
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