var urlOrders = "/orders/date/";
var urlUser = "/users/actual";
var ulrUserAdd = "/user/";
var urlStoreUser = "/orders/store/user";
var actualDate = new Date();
var actualDateChanged = (actualDate.getFullYear()) +
    ('0' + (actualDate.getMonth() + 1)).slice(-2) +
    ('0' + (actualDate.getDate())).slice(-2);
var actualDateArray = [];
actualDateArray[0] = actualDate.getFullYear();
actualDateArray[1] = actualDate.getMonth() + 1;
actualDateArray[2] = actualDate.getDate();
var user;
var lunchesJson;
var tableName = "#day";
var tableHeader = "#table";
var tableHeaderT = "#tableT";
var tableBtn = "#btn";
var changeWasMade = false;
var succesfull = true;

$(document).ready(function(){

    $("#logout").click(function(){
        if (changeWasMade) {
            if (confirm("Obedy boli zmenené prajete si ich uložiť?")) {
                gatherOrders(true);
            } else {
                location.href = "/logout";
            }
        } else {
            location.href = "/logout";
        }
    });

    $("#store").click(function(){
        gatherOrders(true);
    });

    $.getJSON(urlUser, function (json) {
        user = json;
        $("#userHeaderName").text(user.longName);
        createTable(user);
    });

    setClicks();

    $(document).on('click', "input[name*='rdbtn']" ,function () {
        changeWasMade = true;
    });

});

function setHeader(json) {
    for (var i=0; i< json.length; i++) {
        if (compareArrayDate(json[i].lunch.date, actualDateArray)) {
            if (json[i].ordered) {
                $("#userHeader").text("Na dnes (" + getSlovakDay(new Date())+ ") máš objednané:");
                if (json[i].lunch.soup) {
                    $("#userHeaderSoup").text(json[i].lunch.description);
                } else {
                    $("#userHeaderMeal").text(json[i].lunch.description);
                }
            }
        }
    }
}

function create(json) {
    lunchesJson = json;
    setHeader(json);
    var checked = "";
    var disabled = "";
    var index;
    var lastDate = lunchesJson[0].lunch.date;
    var soupIndex = 1;
    var mealIndex = 1;
    var day = 1;
    var textDate = lastDate[2] + "." + lastDate[1] + "." + lastDate[0];
    if (lunchesJson[0].changeable) {
        $(tableHeader + day).text(getSlovakDayFromStringDDdotMMdotYYYY(textDate));
        $(tableHeaderT + day).text(textDate);
        $(tableHeader + day).parent().show();
        $(tableName + day + " > tbody:last-child")
            .append("<tr><th class='danger'>Číslo</th><th class='danger'>Popis jedla</th><th class='danger'>Objednávka</th></tr>");
    }
    var datepart = 1;
    for (index = 0; index < lunchesJson.length; index++) {
        var lunch = lunchesJson[index].lunch;
        var changeable = lunchesJson[index].changeable;
        if (!changeable) {
            day = 0;
            datepart = 0;
            continue;
        }
        var ordered = lunchesJson[index].ordered;
        if (compareArrayDate(lunch.date, lastDate)) {
            if (lunch.soup) {
                checked = "";
                if (ordered) {
                    checked = "checked"
                }
                // disabled = "";
                // if (!changeable) {
                //     disabled = " disabled";
                //     $(tableBtn+day).attr("disabled", true);
                //     // $(tableName+day).attr("hidden", true);
                // }
                var radioname = 'rdbtnsoup' + datepart;
                var radios = "<input type='radio' name='" + radioname + "'" + checked + " >";
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
                if (ordered) {
                    $(document).find("input[name='"+radioname+"']").prop(checked, true);
                }
            } else {
                checked = "";
                if (ordered) {
                    checked = "checked"
                }
                // disabled = "";
                // if (!changeable) {
                //     disabled = " disabled"
                // }

                var radioname = 'rdbtnmeal' + datepart;
                var radiom = "<input type='radio' name='" + radioname + "'" + checked + " >";
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
                if (ordered) {
                    $(document).find("input[name='"+radioname+"']").prop(checked, true);
                }
            }
        } else {
            datepart++;
            soupIndex = 1;
            mealIndex = 1;
            day++;
            lastDate = lunch.date;
            textDate = lastDate[2] + "." + lastDate[1] + "." + lastDate[0];
            $(tableHeader + day).text(getSlovakDayFromStringDDdotMMdotYYYY(textDate));
            $(tableHeaderT + day).text(textDate);
            $(tableHeader+day).parent().show();
            $(tableName+day+" > tbody:last-child")
                .append("<tr><th class='danger'>Číslo</th><th class='danger'>Popis jedla</th><th class='danger'>Objednávka</th></tr>")
            if (lunch.soup) {
                checked = "";
                if (ordered) {
                    checked = "checked"
                }
                // disabled = "";
                // if (!changeable) {
                //     disabled = " disabled"
                // }
                var radioname = 'rdbtnsoup' + datepart;
                var radios1 = "<input type='radio' name='" + radioname + "'" + checked + " >";
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
                if (ordered) {
                    $(document).find("input[name='"+radioname+"']").prop(checked, true);
                }

            } else {
                checked = "";
                if (ordered) {
                    checked = "checked"
                }
                // disabled = "";
                // if (!changeable) {
                //     disabled = " disabled"
                // }
                var radiom1 = "<input type='radio' name='" + radioname + "'" + checked + " >";;
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
                if (ordered) {
                    $(document).find("input[name='"+radioname+"']").prop(checked, true);
                }

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
    $.when (
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
                succesfull = true;
            } else {
                alert("Obed sa nepodarilo ulozit, skontroluj vyber !");
                succesfull = false;
            }
        }
    })
    ).then(function () {
        if (succesfull) {
            location.href = "/logout";
        }
    });
}
function gatherOrders(save) {
    var index = 0;
    for(var i=0;i<lunchesJson.length;i++) {
        if (lunchesJson[i].changeable) {
            break;
        }
        index++;
    }
    $('input:radio').each(function () {
        lunchesJson[index].ordered = !!$(this).prop('checked');
        index++;
    });
    if (save) {
        saveOrders();
    }
}

function deleteOrders(number) {
    gatherOrders(false);
    var counter = 0;
    for(var i=0;i<lunchesJson.length;i++) {
        if (lunchesJson[i].changeable) {
            break;
        }
        counter++;
    }
    number = number + (counter / 7);
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
    changeWasMade = true;
    $("#day1")
        .find("tr")
        .remove();
    $("#day2")
        .find("tr")
        .remove();
    $("#day3")
        .find("tr")
        .remove();
    $("#day4")
        .find("tr")
        .remove();
    $("#day5")
        .find("tr")
        .remove();
    $("#day6")
        .find("tr")
        .remove();
    $("#day7")
        .find("tr")
        .remove();
    $("#day8")
        .find("tr")
        .remove();
    $("#day9")
        .find("tr")
        .remove();
    $("#day10")
        .find("tr")
        .remove();
    create(lunchesJson);
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
