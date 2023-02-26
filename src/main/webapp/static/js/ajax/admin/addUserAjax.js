// When the page is fully loaded...
$(document).ready(function() {

    function addUser(role, firstName, lastName, email, password_1, password_2, icon) {
        $.get("/frontController",
                {"command": "AddUser",
                 "role": role,
                 "firstName": firstName,
                 "lastName": lastName,
                 "email": email,
                 "password_1": password_1,
                 "password_2": password_2,
                 "icon": icon
                },
                function(data, status) {
                    new Toast({
						title: false,
						text: "Success",
						theme: "success",
						autohide: true,
						interval: 3000,
					});
                }).fail(function(data, status) { // on failure
                    new Toast({
                        title: false,
                        text: JSON.parse(data.responseText)["status"],
                        theme: "danger",
                        autohide: true,
                        interval: 3000,
				    });
                });
    }

    // ADD USER
    $("#addUserBtn").click(function() {

        let role = $("#roleSelector").val();
        let firstName = $("#firstName").val();
        let lastName = $("#lastName").val();
        let email = $("#email").val();
        let password_1 = $("#password_1").val();
        let password_2 = $("#password_2").val();
        let icon = $("#icon").prop("files")[0];

        let regex = new RegExp('[a-z0-9]+@[a-z]+\.[a-z]{2,3}');

        let error = "";


        if (role === undefined) error = "Invalid role.";
        else if (role.replaceAll(" ", "").length == 0) error = "Invalid role.";

        else if (firstName === undefined) error = "Invalid firstName.";
        else if (firstName.replaceAll(" ", "").length == 0) error = "Invalid firstName.";
        else if (firstName.length != firstName.trim().length) error = "Invalid firstName.";

        else if (lastName === undefined) error = "Invalid lastName.";
        else if (lastName.replaceAll(" ", "").length == 0) error = "Invalid lastName.";
        else if (lastName.length != lastName.trim().length) error = "Invalid lastName.";

        else if (email === undefined) error = "Invalid email."
        else if (!regex.test(email)) error = "Invalid email.";

        else if (!password_1.match(/^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])([a-zA-Z0-9]{8,})$/)) error = "Password must containing at least 8 characters, 1 number, 1 upper and 1 lowercase";
        else if (password_1 !== password_2) error = "Password 1 not equal to password 2."


        if (error != "") {
            new Toast({
		        title: false,
				text: error,
				theme: "warning",
				autohide: true,
				interval: 3000,
			});

        } else if (icon !== undefined) {

            if (icon.size > 1048576) {
                new Toast({
                    title: false,
                    text: "Icon is to big. Max image size is 1MB.",
                    theme: "warning",
                    autohide: true,
                    interval: 3000,
                });
                return ;
            }

            const reader = new FileReader();
            reader.readAsDataURL(icon);

            reader.onload = function() { addUser(role, firstName, lastName, email, password_1, password_2, reader.result); }
        } else { addUser(role, firstName, lastName, email, password_1, password_2, icon); }
    });

});