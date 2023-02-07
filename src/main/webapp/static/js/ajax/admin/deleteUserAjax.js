// When the page is fully loaded...
$(document).ready(function() {

    function remove(email) {
        $.get("/frontController",
                {"command": "DeleteUser",
                 "email": email,
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

    // REMOVE USER
    $("#deleteUserBtn").click(function() {

        let email = $("#deleteEmail").val();

        let regex = new RegExp('[a-z0-9]+@[a-z]+\.[a-z]{2,3}');

        let error = "";
        if (email === undefined) error = "Invalid email."
        else if (!regex.test(email)) error = "Invalid email.";

        if (error != "") {
            new Toast({
		        title: false,
				text: error,
				theme: "warning",
				autohide: true,
				interval: 3000,
			});
            return ;
        } else { remove(email); }
    });

});