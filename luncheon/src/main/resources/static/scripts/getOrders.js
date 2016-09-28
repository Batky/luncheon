var urlOrders = "http://localhost:8080/orders/date/";
var urlUser = "http://localhost:8080/users/actual";
var ulrUserAdd = "/user/";
var urlOneLunch = "http://localhost:8080/lunches/id/";
var urlPostLunches = "http://localhost:8080/orders/lunches";
var actualDate = new Date();
var actualDateChanged = (actualDate.getFullYear()) +
    ('0' + (actualDate.getMonth() + 1)).slice(-2) +
    ('0' + (actualDate.getDate())).slice(-2);
var user;

$(document).ready(function(){

    $("#logout").click(function(){
        location.href = "/logout";
    });

    $.getJSON(urlUser, function (json) {
        user = json;
        $("#userHeader").text("Zoznam obedov - " + user.longName);
        createTable(user);
    });

    //
    // $("#myModal").on('show.bs.modal', function () {
    //     fillModalForm(actualDateChanged, false);
    // });
    // $("#datetimepicker1").change(function () {
    //     fillModalForm(fromPickerDate($("#datetimepicker1").val()), true);
    // })
});

function createTable(user) {
    url = urlOrders + actualDateChanged + ulrUserAdd + user.id;
    var lunches = [];
    $.get(url , function(jsonUserOrder) {
        var index;
        var lastDate = jsonUserOrder[0].lunch.date;
        var soupIndex = 1;
        var mealIndex = 1;
        var day = 1;
        var textDate = lastDate[2] + "." + lastDate[1] + "." + lastDate[0];
        var tableName = "#day";
        var tableHeader = "#table";
        $(tableHeader+day).text("Dátum: "+textDate);
        $(tableHeader+day).parent().show();
        $(tableName+day+" > tbody:last-child").append("<tr><th class='danger'>Číslo</th><th class='danger'>Popis jedla</th><th class='danger'>Objednávka</th></tr>");
        var datepart = 1;
        for (index = 0; index < jsonUserOrder.length; index++) {
            var lunch = jsonUserOrder[index].lunch;
            if (compareArrayDate(lunch.date, lastDate)) {
                if (lunch.soup) {
                    var radios = "<input type='radio' name='soup" + datepart + "' >";
                    if (soupIndex === 1) {
                        $(tableName+day+" > tbody:last-child").append("<tr><td colspan='3' class='info'>Polievky</td></tr>");
                    }
                    $(tableName+day+" > tbody:last-child")
                        .append(
                            "<tr>" +
                            "<td>" + soupIndex + "</td>" +
                            "<td>" + lunch.description + "</td>" +
                            "<td>" + radios +
                            "</td>" +
                            "</tr>");
                    soupIndex++;
                } else {
                    var radiom = "<input type='radio' name='meal" + datepart + "' >";
                    if (mealIndex === 1) {
                        $(tableName+day+" > tbody:last-child").append("<tr><td colspan='3' class='info'>Hlavné jedlá</td></tr>");
                    }
                    $(tableName+day+" > tbody:last-child").append(
                        "<tr><td>" + mealIndex +
                        "</td><td>" + lunch.description +
                        "</td><td>" + radiom +
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
                $(tableName+day+" > tbody:last-child").append("<tr><th class='danger'>Číslo</th><th class='danger'>Popis jedla</th><th class='danger'>Objednávka</th></tr>")
                if (lunch.soup) {
                    var radios1 = "<input type='radio' name='soup" + datepart + "' >";
                    if (soupIndex === 1) {
                        $(tableName+day+" > tbody:last-child").append("<tr><td colspan='3' class='info'>Polievky</td></tr>");
                    }
                    $(tableName+day+" > tbody:last-child").append("<tr><td>"+soupIndex+"</td><td>"+lunch.description+"</td><td>" + radios1 + "</td></tr>");
                    soupIndex++;
                } else {
                    var radiom1 = "<input type='radio' name='meal" + datepart + "' >";
                    if (mealIndex === 1) {
                        $(tableName+day+" > tbody:last-child").append("<tr><td colspan='3' class='info'>Hlavné jedlá</td></tr>");
                    }
                    $(tableName+day+" > tbody:last-child").append("<tr><td>"+mealIndex+"</td><td>"+lunch.description+"</td><td>"+radiom1+"</td></tr>");
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

function cleanup() {
    var soup = "#soup";
    var idsoup = "#idsoup";
    var soupIndex = 1;
    var meal = "#meal";
    var idmeal = "#idmeal";
    var mealIndex = 1;
    for (index = 0; index < 7; index++) {
        if (index < 2) {
            $(soup+soupIndex).val("");
            $(idsoup+soupIndex).val("");
            soupIndex++;
        } else {
            $(meal+mealIndex).val("");
            $(idmeal+mealIndex).val("");
            mealIndex++;
        }
    }
}

function fillModalForm(date, fromForm){
    var oneTimeURL = urlOneLunch + date;
    if (fromForm) {
        cleanup();
    } else {
        $("#datetimepicker1").val(toPickerDate(date));
    }
    $.get(oneTimeURL, function (json) {
        if (json.length > 0) {
            var soup = "#soup";
            var idsoup = "#idsoup";
            var soupIndex = 1;
            var meal = "#meal";
            var idmeal = "#idmeal";
            var mealIndex = 1;
            for (index = 0; index < json.length; index++) {
                if (json[index].soup) {
                    $(soup+soupIndex).val(json[index].description);
                    $(idsoup+soupIndex).val(json[index].id);
                    soupIndex++;
                } else {
                    $(meal+mealIndex).val(json[index].description);
                    $(idmeal+mealIndex).val(json[index].id);
                    mealIndex++;
                }
            }
        }
    })
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