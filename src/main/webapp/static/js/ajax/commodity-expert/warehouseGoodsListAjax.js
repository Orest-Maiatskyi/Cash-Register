// When the page is fully loaded...
$(document).ready(function() {

    let numOfTabs = 1;

    let numOfRecords = 0;
    let currentTab = 0;
    let rowsPerPage = 5;
    let sortBy = "warehouse.id";
    let search = "%%";


    $("#warehouse-goods-list img[action = 'reload']").click(function() {
        updateTable();
    });


    $("#warehouse-goods-list select[action = 'rowsPerPage']").change(function() {
        currentTab = 0;
        rowsPerPage = $(this).children('option:selected').text();
        updateTable();
    });


    $("#warehouse-goods-list select[action = 'sortBy']").change(function() {
        console.log("GG");
        currentTab = 0;
        sortBy = $(this).children('option:selected').attr("value");
        updateTable();
    });


    $("#warehouse-goods-list input[action = 'likeData']").on('input',function() {
        currentTab = 0;
        search = "%" + $(this).val() + "%";
        updateTable();
    });

    // <<
    $("#warehouse-goods-list nav a[action = 'toStart']").click(function() {
        if (numOfTabs > 1 && currentTab != 0) {
            $('#warehouse-goods-list nav div a:contains("' + 1 + '")').click();
        }
    });

    // <
    $("#warehouse-goods-list nav a[action = 'prev']").click(function() {
        if (numOfTabs > 1 && currentTab != 0) {
            $('#warehouse-goods-list nav div a:contains("' + currentTab + '")').click();
        }
    });

    // >
    $("#warehouse-goods-list nav a[action = 'next']").click(function() {
        if (currentTab < numOfTabs) {
            $('#warehouse-goods-list nav div a:contains("' + (currentTab+2) + '")').click();
        }
    });

    // >>
    $("#warehouse-goods-list nav a[action = 'toEnd']").click(function() {
        if (currentTab < numOfTabs) {
            $('#warehouse-goods-list nav div a:contains("' + numOfTabs + '")').click();
        }
    });


    function updateTabsBar() {
        numOfTabs = Math.ceil(numOfRecords / rowsPerPage);
        $("#warehouse-goods-list nav div").html('');

        for (let i = 0; i < numOfTabs; i++) {
            if (currentTab == i) $("#warehouse-goods-list nav div").append("<li class='page-item'><a class='page-link' href='#' style='text-decoration: underline;'>" + (i+1) + "</a></li>");
            else $("#warehouse-goods-list nav div").append("<li class='page-item'><a class='page-link' href='#'>" + (i+1) + "</a></li>");
        }

        $("#warehouse-goods-list nav div a").each(function() {
            this.onclick = () => {
                currentTab = this.textContent - 1;
                updateTable();
            }
        });
    }


    function updateTable() {

        $.get("/frontController",
            {"command": "GetPaginatedWarehouseGoods",
            "likeData": search,
            "orderBy": sortBy,
            "rowCount": rowsPerPage,
            "offset": currentTab * rowsPerPage},
            function(data, status) {

                let json = JSON.parse(data);
                let jsonData = JSON.parse(json["data"]);
                numOfRecords = jsonData["numOfRecords"];
                let goods = jsonData["warehouseGoods"];
                
                $("#warehouse-goods-list table tbody").html('');
                for (let i = 0; i < goods.length; i++) {
                    $("#warehouse-goods-list table tbody").append(
                        "<tr>" +
                            "<td>" + goods[i]["id"] + "</td>" +
                            "<td>" + goods[i]["address"] + "</td>" +
                            "<td>" + goods[i]["code"] + "</td>" +
                            "<td>" + goods[i]["quantity"] + "</td>" +
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