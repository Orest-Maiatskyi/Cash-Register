// When the page is fully loaded...
$(document).ready(function() {

    let numOfTabs = 1;

    let numOfRecords = 0;
    let currentTab = 0;
    let rowsPerPage = 5;
    let sortBy = "goods.id";
    let search = "%%";


    $("#good-list img[action = 'reload']").click(function() {
        updateTable();
    });


    $("#rowsPerPage").change(function() {
        currentTab = 0;
        rowsPerPage = $(this).children('option:selected').text();
        updateTable();
    });


    $("#sortBy").change(function() {
        currentTab = 0;
        sortBy = $(this).children('option:selected').attr("value");
        console.log(sortBy);
        updateTable();
    });


    $("#likeData").on('input',function() {
        currentTab = 0;
        search = "%" + $(this).val() + "%";
        updateTable();
    });

    // <<
    $("#goodListNav a[action = 'toStart']").click(function() {
        if (numOfTabs > 1 && currentTab != 0) {
            $('#goodListNav div a:contains("' + 1 + '")').click();
        }
    });

    // <
    $("#goodListNav a[action = 'prev']").click(function() {
        if (numOfTabs > 1 && currentTab != 0) {
            $('#goodListNav div a:contains("' + currentTab + '")').click();
        }
    });

    // >
    $("#goodListNav a[action = 'next']").click(function() {
        if (currentTab < numOfTabs) {
            $('#goodListNav div a:contains("' + (currentTab+2) + '")').click();
        }
    });

    // >>
    $("#goodListNav a[action = 'toEnd']").click(function() {
        if (currentTab < numOfTabs) {
            $('#goodListNav div a:contains("' + numOfTabs + '")').click();
        }
    });


    function updateTabsBar() {
        numOfTabs = Math.ceil(numOfRecords / rowsPerPage);
        $("#goodListNav div").html('');

        for (let i = 0; i < numOfTabs; i++) {
            if (currentTab == i) $("#goodListNav div").append("<li class='page-item'><a class='page-link' href='#' style='text-decoration: underline;'>" + (i+1) + "</a></li>");
            else $("#goodListNav div").append("<li class='page-item'><a class='page-link' href='#'>" + (i+1) + "</a></li>");
        }

        $("#goodListNav div a").each(function() {
            this.onclick = () => {
                currentTab = this.textContent - 1;
                updateTable();
            }
        });
    }


    function updateTable() {

        $.get("/frontController",
            {"command": "GetPaginatedGoodsList",
            "likeData": search,
            "orderBy": sortBy,
            "rowCount": rowsPerPage,
            "offset": currentTab * rowsPerPage},
            function(data, status) {

                let json = JSON.parse(data);
                let jsonData = JSON.parse(json["data"]);
                numOfRecords = jsonData["numOfRecords"];
                let goods = jsonData["goods"];
                
                $("#goodList tbody").html('');
                for (let i = 0; i < goods.length; i++) {
                    $("#goodList tbody").append(
                        "<tr>" +
                            "<td>" + goods[i]["id"] + "</td>" +
                            "<td>" + goods[i]["code"] + "</td>" +
                            "<td>" + goods[i]["measurement"] + "</td>" +
                            "<td>" + goods[i]["title"] + "</td>" +
                            "<td>" +
                                "<input type='checkbox' id='hd-" + i + "' class='hide'/>" +
                                "<label for='hd-" + i + "'>" + goods[i]["description"].substr(0, 20) + " ...</label>" +
                                "<div>" + goods[i]["description"].substr(0, 300) + "</div>" +
                            "</td>" +
                            "<td>" + goods[i]["price"] + "</td>" +
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