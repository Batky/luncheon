var urlOrders = "http://localhost:8080/orders/date/";
var urlUser = "http://localhost:8080/users/actual";
var ulrUserAdd = "/user/";
var urlStoreUser = "http://localhost:8080/orders/store/user";
var urlPostLunches = "http://localhost:8080/orders/lunches";
var actualDate = new Date();
var actualDateChanged = (actualDate.getFullYear()) +
    ('0' + (actualDate.getMonth() + 1)).slice(-2) +
    ('0' + (actualDate.getDate())).slice(-2);
var user;
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
                            "<td width='10%'>" + soupIndex + "</td>" +
                            "<td>" + lunch.description + "</td>" +
                            "<td width='10%'>" + radios +
                            "</td>" +
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
                        "<tr><td width='10%'>" + mealIndex +
                        "</td><td>" + lunch.description +
                        "</td><td width='10%'>" + radiom +
                        "</td></tr>");
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
                        .append("<tr><td width='10%'>"+soupIndex+"</td><td>"+lunch.description+"</td><td width='10%'>" + radios1 + "</td></tr>");
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
                        .append("<tr><td width='10%'>"+mealIndex+"</td><td>"+lunch.description+"</td><td width='10%'>"+radiom1+"</td></tr>");
                    mealIndex++;
                }
            }
        }
    });
}

function compareArrayDate(dateArray1, dateArray2) {
    return (dateArray1[0] === dateArray2[0] && dateArray1[1] === dateArray2[1] && dateArray1[2] === dateArray2[2])
}

function toPickerDate(date) {
    return date.substr(0,4) + "-" + date.substr(4,2) + "-" + date.substr(6,2);
}

function fromPickerDate(date) {
    return date.substr(0,4) + date.substr(5,2) + date.substr(8,2);
}

function postLunches() {
    // var date = fromPickerDate($("#datetimepicker1").val());
    var date = $("#datetimepicker1").val();
    var lunches = [];
    for (index = 1; index < 3; index++) {
        var lunchSoup = new lunch($("#idsoup"+index).val(), true, date, $("#soup"+index).val());
        lunches.push(lunchSoup);
    }
    for (index = 1; index < 6; index++) {
        var lunchSoup = new lunch($("#idmeal"+index).val(), false, date, $("#meal"+index).val());
        lunches.push(lunchSoup);
    }
    var jsonLunches = JSON.stringify(lunches);
    console.log(jsonLunches);
    $.ajax({
        url:urlPostLunches,
        type:"POST",
        headers: {
            "Accept" : "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
        data:jsonLunches,
        dataType:"json"
    })
}

function lunch(id, soup, date, description) {
    this.id = id;
    this.soup = soup;
    this.date = date;
    this.description = description;
}

function order(user, lunch, ordered, changeable) {
    this.user = user;
    this.lunch = lunch;
    this.ordered = ordered;
    this.changeable = changeable;
}

function gatherOrders() {
    var index = 1;
    var index2 = 1;
    $("tr").each(function () {
        console.log("tr " + index);
        index++;
        $("td", this).each(function () {
            console.log("td " + index2 + " " + $(this).html());
            index2++;
        })
        index2 = 1;
    })
}