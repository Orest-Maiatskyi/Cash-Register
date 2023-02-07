// When the page is fully loaded...
$(document).ready(function() {

    // CREATE X REPORT
    $("#seniorCashierCreateXReport").click(function() {

        $.get("/frontController",
        {"command": "CreateXReport"},
            function(data, status) {
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