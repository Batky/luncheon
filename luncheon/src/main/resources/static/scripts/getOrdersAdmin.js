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
var tableBtn = "#btn";

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

    setClicks();

});

function create(json) {
    lunchesJson = json;
    var checked = "";
    var disabled = "";
    var index;
    var lastDate = lunchesJson[0].lunch.date;
    var soupIndex = 1;
    var mealIndex = 1;
    var day = 1;
    var textDate = lastDate[2] + "." + lastDate[1] + "." + lastDate[0];
    $(tableHeader+day).text("Dátum: "+textDate);
    $(tableHeader+day).parent().show();
    $(tableName+day+" > tbody:last-child")
        .append("<tr><th class='danger'>Číslo</th><th class='danger'>Popis jedla</th><th class='danger'>Objednávka</th></tr>");
    var datepart = 1;
    for (index = 0; index < lunchesJson.length; index++) {
        var lunch = lunchesJson[index].lunch;
        var changeable = lunchesJson[index].changeable;
        var ordered = lunchesJson[index].ordered;
        if (compareArrayDate(lunch.date, lastDate)) {
            if (lunch.soup) {
                checked = "";
                if (ordered) {
                    checked = "checked"
                }
                disabled = "";
                if (!changeable) {
                    disabled = " disabled";
                    $(tableBtn+day).attr("disabled", true);
                    // $(tableName+day).attr("hidden", true);
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
}

function createTable(user) {
    var url = urlOrders + actualDateChanged + ulrUserAdd + user.id;
    $.get(url , function(jsonUserOrder) {
        create(jsonUserOrder);
    });
}

function compareArrayDate(dateArray1, dateArray2) {
    return (dateArray1[0] === dateArray2[0] && dateArray1[1] === dateArray2[1] && dateArray1[2] === dateArray2[2])
}

function saveOrders() {
    var jsonLunchesOrders = JSON.stringify(lunchesJson);
    $.ajax({
        url: urlStoreUser,
        type: "POST",
        headers: {
            "Accept": "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
        data: jsonLunchesOrders,
        dataType: "json",
        complete: function (data) {
            if (data.status == 201) {
                alert("Zmeny na obedoch sa úspešne uložili");
                location.reload();
            } else {
                alert("Obed sa nepodarilo ulozit, skontroluj vyber !");
            }
        }
    });
}
function gatherOrders() {
    var index = 0;
    $('input:radio').each(function () {
        lunchesJson[index].ordered = !!$(this).prop('checked');
        index++;
    });
    saveOrders();
}

function deleteOrders(number) {
    var findDay = 1;
    var firstDate = lunchesJson[0].lunch.date;
    for(var i=0;i<lunchesJson.length;i++){
        if (compareArrayDate(firstDate, lunchesJson[i].lunch.date)) {
            if (number == findDay) {
                lunchesJson[i].ordered = false;
            }
        } else {
            firstDate = lunchesJson[i].lunch.date;
            findDay++;
            if (number == findDay) {
                lunchesJson[i].ordered = false;
            }
        }
    }
    saveOrders();
}

function setClicks() {
    $("#btn1").click(function () {
        deleteOrders(1);
    });
    $("#btn2").click(function () {
        deleteOrders(2);
    });
    $("#btn3").click(function () {
        deleteOrders(3);
    });
    $("#btn4").click(function () {
        deleteOrders(4);
    });
    $("#btn5").click(function () {
        deleteOrders(5);
    });
    $("#btn6").click(function () {
        deleteOrders(6);
    });
    $("#btn7").click(function () {
        deleteOrders(7);
    });
    $("#btn8").click(function () {
        deleteOrders(8);
    });
    $("#btn9").click(function () {
        deleteOrders(9);
    });
    $("#btn10").click(function () {
        deleteOrders(10);
    })
}
