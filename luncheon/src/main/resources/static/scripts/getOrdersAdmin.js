var urlOrders = "http://localhost:8080/orders/exact/date/";
var urlUser = "http://localhost:8080/users/actual";
var urlUserId = "http://localhost:8080/users/id/";
var ulrUserAdd = "/user/";
var urlStoreUser = "http://localhost:8080/orders/store/user";
var urlAllUsers = "http://localhost:8080/users/all";
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

    $("#datetimepicker")
        .val(toPickerDate(dateToRestString(new Date())));

    $("#datetimepicker")
        .change(function () {
            var valueDate = $("#datetimepicker").val();
            actualDateChanged = fromPickerDate(valueDate);
            createTable(user);
        });

    $("#backbtn").click(function () {
        window.history.back();
    });

    $("#btn1").click(function () {
        deleteOrders();
    });

    $.get(urlAllUsers, function (json) {
        createTableUsers(json);
    });

    $.getJSON(urlUser, function (json) {
        user = json;
        $("#userHeader").text("Zoznam obedov - " + user.longName);
        createTable(user);
    });

    $(document).on('click', '#users > tbody > tr',  function() {

        $(tableName+ 1 + " tr").remove();
        var str = $(this).text();
        console.log(str);
        var num = str.split("@",1);
        console.log(num[0]);
        $.getJSON(urlUserId+ num[0], function (json) {
            user = json;
            $("#userHeader").text("Zoznam obedov - " + user.longName);
            createTable(user);
        });
    });

});

function createTableUsers(json) {
    for (var index=0;index < json.length; index++){
        $("#users > tbody:last-child")
            .append("<tr><td hidden>"+json[index].id+"@</td><td>" + json[index].lastName + " " + json[index].firstName + "</td></tr>");
    }
}

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
                // if (!changeable) {
                //     disabled = " disabled";
                //     $(tableBtn+day).attr("disabled", true);
                //     // $(tableName+day).attr("hidden", true);
                // }
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
                // if (!changeable) {
                //     disabled = " disabled"
                // }

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
                // if (!changeable) {
                //     disabled = " disabled"
                // }
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
                // if (!changeable) {
                //     disabled = " disabled"
                // }
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
    console.log(url);
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

function toPickerDate(date) {
    return date.substr(0,4) + "-" + date.substr(4,2) + "-" + date.substr(6,2);
}

function fromPickerDate(date) {
    return date.substr(0,4) + date.substr(5,2) + date.substr(8,2);
}

function dateToRestString(date) {
    var result = (date.getFullYear()) +
        ('0' + (date.getMonth() + 1)).slice(-2) +
        ('0' + (date.getDate())).slice(-2);
    console.log(result);
    return result;
}