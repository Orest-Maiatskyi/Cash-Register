// When the page is fully loaded...
$(document).ready(function() {

    let numOfTabs = 1;

    let numOfRecords = 0;
    let currentTab = 0;
    let rowsPerPage = 5;
    let sortBy = "order_id";
    let search = "%%";


    $("#cashier-order-list img[action = 'reload']").click(function() {
        updateTable();
    });


    $("#cashier-order-list select[action = 'rowsPerPage']").change(function() {
        currentTab = 0;
        rowsPerPage = $(this).children('option:selected').text();
        updateTable();
    });


    $("#cashier-order-list select[action = 'sortBy']").change(function() {
        currentTab = 0;
        sortBy = $(this).children('option:selected').attr("value");
        updateTable();
    });


    $("#cashier-order-list input[action = 'likeData']").on('input',function() {
        currentTab = 0;
        search = "%" + $(this).val() + "%";
        updateTable();
    });

    // <<
    $("#cashier-order-list nav a[action = 'toStart']").click(function() {
        if (numOfTabs > 1 && currentTab != 0) {
            $('#cashier-order-list nav div a:contains("' + 1 + '")').click();
        }
    });

    // <
    $("#cashier-order-list nav a[action = 'prev']").click(function() {
        if (numOfTabs > 1 && currentTab != 0) {
            $('#cashier-order-list nav div a:contains("' + currentTab + '")').click();
        }
    });

    // >
    $("#cashier-order-list nav a[action = 'next']").click(function() {
        if (currentTab < numOfTabs) {
            $('#cashier-order-list nav div a:contains("' + (currentTab+2) + '")').click();
        }
    });

    // >>
    $("#cashier-order-list nav a[action = 'toEnd']").click(function() {
        if (currentTab < numOfTabs) {
            $('#cashier-order-list nav div a:contains("' + numOfTabs + '")').click();
        }
    });


    function updateTabsBar() {
        numOfTabs = Math.ceil(numOfRecords / rowsPerPage);
        $("#cashier-order-list nav div").html('');

        for (let i = 0; i < numOfTabs; i++) {
            if (currentTab == i) $("#cashier-order-list nav div").append("<li class='page-item'><a class='page-link' href='#' style='text-decoration: underline;'>" + (i+1) + "</a></li>");
            else $("#cashier-order-list nav div").append("<li class='page-item'><a class='page-link' href='#'>" + (i+1) + "</a></li>");
        }

        $("#cashier-order-list nav div a").each(function() {
            this.onclick = () => {
                currentTab = this.textContent - 1;
                updateTable();
            }
        });
    }

    function updateTable() {

        $.get("/frontController",
            {"command": "GetPaginatedOrderList",
            "likeData": search,
            "orderBy": sortBy,
            "rowCount": rowsPerPage,
            "offset": currentTab * rowsPerPage},
            function(data, status) {

                let json = JSON.parse(data);
                let jsonData = JSON.parse(json["data"]);
                numOfRecords = jsonData["numOfRecords"];
                let orders = jsonData["orders"];

                $("#cashier-order-list table tbody").html('');
                for (let i = 0; i < orders.length; i++) {
                    $("#cashier-order-list table tbody").append(
                        "<tr>" +
                            "<td>" + orders[i]["orderId"] + "</td>" +
                            "<td>" + orders[i]["status"] + "</td>" +
                            "<td>" + orders[i]["goodCode"] + "</td>" +
                            "<td>" + orders[i]["quantity"] + "</td>" +
                        "</tr>"
                    );
                }

                updateTabsBar();

                })
                .fail(function(data, status) { // on failure

                });
    }


    updateTable();

});