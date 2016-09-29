var urlOrders = "http://localhost:8080/orders/date/";
var urlUser = "http://localhost:8080/users/actual";
var ulrUserAdd = "/user/";
var urlStoreUser = "http://localhost:8080/orders/store/user";
var actualDate = new Date();
var actualDateChanged = (actualDate.getFullYear()) +
    ('0' + (actualDate.getMonth() + 1)).slice(-2) +
    ('0' + (actualDate.getDate())).slice(-2);
var user;
var lunchesJson;
var tableName = "#day";
var tableHeader = "#table";

$(document).ready(function(){

    $("#logout").click(function(){
        location.href = "/logout";
    });

    $("#store").click(function(){
        gatherOrders();
    });

    $.getJSON(urlUser, function (json) {
        user = json;
        $("#userHeader").text("Zoznam obedov - " + user.longName);
        createTable(user);
    });

});

function createTable(user) {
    url = urlOrders + actualDateChanged + ulrUserAdd + user.id;
    var lunches = [];
    $.get(url , function(jsonUserOrder) {
        lunchesJson = jsonUserOrder;
        var checked = "";
        var disabled = "";
        var index;
        var lastDate = jsonUserOrder[0].lunch.date;
        var soupIndex = 1;
        var mealIndex = 1;
        var day = 1;
        var textDate = lastDate[2] + "." + lastDate[1] + "." + lastDate[0];
        $(tableHeader+day).text("Dátum: "+textDate);
        $(tableHeader+day).parent().show();
        $(tableName+day+" > tbody:last-child")
            .append("<tr><th class='danger'>Číslo</th><th class='danger'>Popis jedla</th><th class='danger'>Objednávka</th></tr>");
        var datepart = 1;
        for (index = 0; index < jsonUserOrder.length; index++) {
            var lunch = jsonUserOrder[index].lunch;
            var changeable = jsonUserOrder[index].changeable;
            var ordered = jsonUserOrder[index].ordered;
            if (compareArrayDate(lunch.date, lastDate)) {
                if (lunch.soup) {
                    checked = "";
                    if (ordered) {
                        checked = "checked"
                    }
                    disabled = "";
                    if (!changeable) {
                        disabled = " disabled"
                    }
                    var radios = "<input type='radio' name='soup" + datepart + "' " + checked + disabled + ">";
                    if (soupIndex === 1) {
                        $(tableName+day+" > tbody:last-child")
                            .append("<tr><td colspan='3' class='info'>Polievky</td></tr>");
                    }
                    $(tableName+day+" > tbody:last-child")
                        .append(
                            "<tr>" +
                            "<td style='display:none;'>" + lunch.id + "</td>" +
                            "<td width='10%'>" + soupIndex + "</td>" +
                            "<td>" + lunch.description + "</td>" +
                            "<td width='10%'>" + radios + "</td>" +
                            "</tr>");
                    soupIndex++;
                } else {
                    checked = "";
                    if (ordered) {
                        checked = "checked"
                    }
                    disabled = "";
                    if (!changeable) {
                        disabled = " disabled"
                    }

                    var radiom = "<input type='radio' name='meal" + datepart + "' " + checked + disabled + ">";
                    if (mealIndex === 1) {
                        $(tableName+day+" > tbody:last-child")
                            .append("<tr><td colspan='3' class='info'>Hlavné jedlá</td></tr>");
                    }
                    $(tableName+day+" > tbody:last-child")
                        .append(
                            "<tr>" +
                            "<td style='display:none;'>" + lunch.id + "</td>" +
                            "<td width='10%'>" + mealIndex + "</td>" +
                            "<td>" + lunch.description + "</td>" +
                            "<td width='10%'>" + radiom + "</td>" + "</tr>");
                    mealIndex++;
                }
            } else {
                datepart++;
                soupIndex = 1;
                mealIndex = 1;
                day++;
                lastDate = lunch.date;
                textDate = lastDate[2] + "." + lastDate[1] + "." + lastDate[0];
                $(tableHeader+day).text("Dátum: "+textDate);
                $(tableHeader+day).parent().show();
                $(tableName+day+" > tbody:last-child")
                    .append("<tr><th class='danger'>Číslo</th><th class='danger'>Popis jedla</th><th class='danger'>Objednávka</th></tr>")
                if (lunch.soup) {
                    checked = "";
                    if (ordered) {
                        checked = "checked"
                    }
                    disabled = "";
                    if (!changeable) {
                        disabled = " disabled"
                    }
                    var radios1 = "<input type='radio' name='soup" + datepart + "' " + checked + disabled + ">";
                    if (soupIndex === 1) {
                        $(tableName+day+" > tbody:last-child")
                            .append("<tr><td colspan='3' class='info'>Polievky</td></tr>");
                    }
                    $(tableName+day+" > tbody:last-child")
                        .append(
                            "<tr>" +
                            "<td style='display:none;'>" + lunch.id + "</td>" +
                            "<td width='10%'>"+soupIndex+"</td>" +
                            "<td>"+lunch.description+"</td>" +
                            "<td width='10%'>" + radios1 + "</td>" +
                            "</tr>");
                    soupIndex++;
                } else {
                    checked = "";
                    if (ordered) {
                        checked = "checked"
                    }
                    disabled = "";
                    if (!changeable) {
                        disabled = " disabled"
                    }
                    var radiom1 = "<input type='radio' name='meal" + datepart + "' " + checked + disabled + ">";
                    if (mealIndex === 1) {
                        $(tableName+day+" > tbody:last-child")
                            .append("<tr><td colspan='3' class='info'>Hlavné jedlá</td></tr>");
                    }
                    $(tableName+day+" > tbody:last-child")
                        .append(
                            "<tr>" +
                            "<td style='display:none;'>" + lunch.id + "</td>" +
                            "<td width='10%'>"+mealIndex+"</td>" +
                            "<td>"+lunch.description+"</td>" +
                            "<td width='10%'>"+radiom1+"</td>" +
                            "</tr>");
                    mealIndex++;
                }
            }
        }
    });
}

function compareArrayDate(dateArray1, dateArray2) {
    return (dateArray1[0] === dateArray2[0] && dateArray1[1] === dateArray2[1] && dateArray1[2] === dateArray2[2])
}

function gatherOrders() {
    var index = 0;
    $('input:radio').each(function () {
        lunchesJson[index].ordered = !!$(this).prop('checked');
        index++;
    });
    var jsonLunchesOrders = JSON.stringify(lunchesJson);
    console.log(jsonLunchesOrders);
    $.ajax({
        url:urlStoreUser,
        type:"POST",
        headers: {
            "Accept" : "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
        data:jsonLunchesOrders,
        dataType:"json"
    })
}