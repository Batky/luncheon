var user;
var usersOrders;
var tempOrders;
var stableLunches;
var stats;
var tempStableOrder;
var actualDate = new Date();
var actualDateChanged = (actualDate.getFullYear()) +
    ('0' + (actualDate.getMonth() + 1)).slice(-2) +
    ('0' + (actualDate.getDate())).slice(-2);

$(document).ready(function () {
    var randomBackground = getRandomBackgroundForMyOrderPage();
    var backgroundClass = "background" + randomBackground;

    const $orders = $('#orders-background');
    $orders.removeClass();
    $orders.addClass(backgroundClass);

    $("#mon1b").click(function () {
        startModal("mon1");
    });

    $("#tue1b").click(function () {
        startModal("tue1");
    });

    $("#wed1b").click(function () {
        startModal("wed1");
    });

    $("#thu1b").click(function () {
        startModal("thu1");
    });

    $("#fri1b").click(function () {
        startModal("fri1");
    });

    $("#mon2b").click(function () {
        startModal("mon2");
    });

    $("#tue2b").click(function () {
        startModal("tue2");
    });

    $("#wed2b").click(function () {
        startModal("wed2");
    });

    $("#thu2b").click(function () {
        startModal("thu2");
    });

    $("#fri2b").click(function () {
        startModal("fri2");
    });

    $("#mon1bs").click(function () {
        startModalStable("mon1", 1, 1);
    });

    $("#tue1bs").click(function () {
        startModalStable("tue1", 2, 1);
    });

    $("#wed1bs").click(function () {
        startModalStable("wed1", 3, 1);
    });

    $("#thu1bs").click(function () {
        startModalStable("thu1", 4, 1);
    });

    $("#fri1bs").click(function () {
        startModalStable("fri1", 5, 1);
    });

    $("#mon2bs").click(function () {
        startModalStable("mon2", 1, 2);
    });

    $("#tue2bs").click(function () {
        startModalStable("tue2", 2, 2);
    });

    $("#wed2bs").click(function () {
        startModalStable("wed2", 3, 2);
    });

    $("#thu2bs").click(function () {
        startModalStable("thu2", 4, 2);
    });

    $("#fri2bs").click(function () {
        startModalStable("fri2", 5, 2);
    });

    var urlOrders;
    var urlStatistics;

    $.when (

        $.getJSON(urlUserActual, function (json) {
            user = json;
            urlOrders = urlOrdersForDate + actualDateChanged + ulrUserAdding + user.id;
            urlStatistics = urlStatisticsForUser + user.id;
        })

    ).then (
        function () {
            $.get(urlOrders , function(jsonUserOrder) {
                usersOrders = jsonUserOrder;
                fillTables(usersOrders);
            });

            $.get(urlStatistics, function (jsonUserStats) {
                stats = jsonUserStats;
                updateStatistics(stats);
            });

            $.get(urlStableLunches, function (jsonStable) {
                stableLunches = jsonStable;
            });
        }
    );

});

function updateStatistics(statistics) {
    var todayText = "NEMÁM";
    var tomorrowText = todayText;

    if (statistics.today) {
        todayText = "MÁM";
    }

    if (statistics.tomorrow) {
        tomorrowText = "MÁM";
    }

    $("#stat-name").html(statistics.name);
    $("#stat-count").html("Počet obedov tento mesiac: " + statistics.lunchesThisMonth);
    $("#stat-today").html("Na dnes obed " + todayText);
    $("#stat-tomorrow").html("Najbližší pracovný deň " + tomorrowText);
}

function fillTables(jsonOrders) {
    hideAllWeeks();
    var week = 1;
    var soup = 0;
    var meal = 0;
    var lunch = jsonOrders[0].lunch;
    var date = lunch.date;
    var lastDate = date;
    var day = getDayFromJsonDate(date);
    var weekElement = "#" + weekElements[day] + week;
    $(weekElement + dateElement).text(date[2] + "." + date[1]);
    $(weekElement).show();
    if (jsonOrders[0].changeable) {
        $(weekElement + buttonElement).prop("disabled", false);
        $(weekElement + buttonElementStable).prop("disabled", false);
    }
    for (var i = 0; i < jsonOrders.length; i++) {
        lunch = jsonOrders[i].lunch;
        date = lunch.date;
        if (equalsArrayDate(date, lastDate)) {
            if (lunch.soup) {
                $(weekElement + soupElements[soup]).text(lunch.description);
                $(weekElement + soupElements[soup] + jsonElement).text(i);
                if (jsonOrders[i].ordered) {
                    $(weekElement + soupElements[soup]).parent().addClass("ordered");
                } else {
                    $(weekElement + soupElements[soup]).parent().removeClass("ordered");
                }
                soup++;
            } else {
                if (meal == 5) {
                    if (jsonOrders[i].ordered) {
                        $(weekElement + lastLine).show();
                    }
                }
                $(weekElement + mealElements[meal]).text(lunch.description);
                $(weekElement + mealElements[meal] + jsonElement).text(i);
                if (jsonOrders[i].ordered) {
                    $(weekElement + mealElements[meal]).parent().addClass("ordered");
                } else {
                    $(weekElement + mealElements[meal]).parent().removeClass("ordered");
                }
                meal++;
            }
        } else {
            if (meal == 5) {
                $(weekElement + lastLine).hide();
            }
            day = getDayFromJsonDate(date);
            if (day === 0) {
                week++;
            }
            weekElement = "#" + weekElements[day] + week;
            soup = 0;
            meal = 0;
            lastDate = date;
            $(weekElement + dateElement).text(date[2] + "." + date[1]);
            $(weekElement).show();
            $(weekElement + spaceElement).show();
            if (jsonOrders[i].changeable) {
                $(weekElement + buttonElement).prop("disabled", false);
                $(weekElement + buttonElementStable).prop("disabled", false);
            }
            if (lunch.soup) {
                $(weekElement + soupElements[soup]).text(lunch.description);
                $(weekElement + soupElements[soup] + jsonElement).text(i);
                if (jsonOrders[i].ordered) {
                    $(weekElement + soupElements[soup]).parent().addClass("ordered");
                } else {
                    $(weekElement + soupElements[soup]).parent().removeClass("ordered");
                }
                soup++;
            } else {
                if (meal == 5) {
                    if (jsonOrders[i].ordered) {
                        $(weekElement + lastLine).show();
                    }
                }
                $(weekElement + mealElements[meal]).text(lunch.description);
                $(weekElement + mealElements[meal] + jsonElement).text(i);
                if (jsonOrders[i].ordered) {
                    $(weekElement + mealElements[meal]).parent().addClass("ordered");
                } else {
                    $(weekElement + mealElements[meal]).parent().removeClass("ordered");
                }
                meal++;
            }
        }
    }
}

function getTmpJson(button) {
    var tmpJSON = [];
    var j=0;
    var firstIndex = Number($("#" + button + "s1j").text());
    var lastIndex = Number($("#"+ button + "m5j").text());
    if (!$("#"+button+"six").is(":hidden")) {
        lastIndex++;
    }
    for (var i=firstIndex;i<=lastIndex;i++){
        tmpJSON[j] = usersOrders[i];
        j++;
    }
    return tmpJSON;
}

function setModalTexts(tmp) {
    $("#soup1").text(tmp[0].lunch.description);
    $("#soup1radio").prop("checked", tmp[0].ordered);
    $("#soup2").text(tmp[1].lunch.description);
    $("#soup2radio").prop("checked", tmp[1].ordered);
    $("#meal1").text(tmp[2].lunch.description);
    $("#meal1radio").prop("checked", tmp[2].ordered);
    $("#meal2").text(tmp[3].lunch.description);
    $("#meal2radio").prop("checked", tmp[3].ordered);
    $("#meal3").text(tmp[4].lunch.description);
    $("#meal3radio").prop("checked", tmp[4].ordered);
    $("#meal4").text(tmp[5].lunch.description);
    $("#meal4radio").prop("checked", tmp[5].ordered);
    $("#meal5").text(tmp[6].lunch.description);
    $("#meal5radio").prop("checked", tmp[6].ordered);
}

function setModalTextsStable(tmp, orders) {
    $("#soup1s").text(orders[0].lunch.description);
    $("#soup1sradio").prop("checked", orders[0].ordered);
    $("#soup2s").text(orders[1].lunch.description);
    $("#soup2sradio").prop("checked", orders[1].ordered);

    $("#meal1s").text(tmp[0].description);
    if (tmp[0].description === "") {
        $("#meal1sradio").prop("disabled", true);
    } else {
        $("#meal1sradio").prop("disabled", false);
    }
    if (orders.length > 7 && orders[7].lunch.id == tmp[0].id) {
        $("#meal1sradio").prop("checked", true);
    }
    $("#meal2s").text(tmp[1].description);
    if (tmp[1].description === "") {
        $("#meal2sradio").prop("disabled", true);
    } else {
        $("#meal2sradio").prop("disabled", false);
    }
    if (orders.length > 7 && orders[7].lunch.id == tmp[1].id) {
        $("#meal2sradio").prop("checked", true);
    }
    $("#meal3s").text(tmp[2].description);
    if (tmp[2].description === "") {
        $("#meal3sradio").prop("disabled", true);
    } else {
        $("#meal3sradio").prop("disabled", false);
    }
    if (orders.length > 7 && orders[7].lunch.id == tmp[2].id) {
        $("#meal3sradio").prop("checked", true);
    }
    $("#meal4s").text(tmp[3].description);
    if (tmp[3].description === "") {
        $("#meal4sradio").prop("disabled", true);
    } else {
        $("#meal4sradio").prop("disabled", false);
    }
    if (orders.length > 7 && orders[7].lunch.id == tmp[3].id) {
        $("#meal4sradio").prop("checked", true);
    }
    $("#meal5s").text(tmp[4].description);
    if (tmp[4].description === "") {
        $("#meal5sradio").prop("disabled", true);
    } else {
        $("#meal5sradio").prop("disabled", false);
    }
    if (orders.length > 7 && orders[7].lunch.id == tmp[4].id) {
        $("#meal5sradio").prop("checked", true);
    }
    $("#meal6s").text(tmp[5].description);
    if (tmp[5].description === "") {
        $("#meal6sradio").prop("disabled", true);
    } else {
        $("#meal6sradio").prop("disabled", false);
    }
    if (orders.length > 7 && orders[7].lunch.id == tmp[5].id) {
        $("#meal6sradio").prop("checked", true);
    }
    $("#meal7s").text(tmp[6].description);
    if (tmp[6].description === "") {
        $("#meal7sradio").prop("disabled", true);
    } else {
        $("#meal7sradio").prop("disabled", false);
    }
    if (orders.length > 7 && orders[7].lunch.id == tmp[6].id) {
        $("#meal7sradio").prop("checked", true);
    }
    $("#meal8s").text(tmp[7].description);
    if (tmp[7].description === "") {
        $("#meal8sradio").prop("disabled", true);
    } else {
        $("#meal8sradio").prop("disabled", false);
    }
    if (orders.length > 7 && orders[7].lunch.id == tmp[7].id) {
        $("#meal8sradio").prop("checked", true);
    }
    $("#meal9s").text(tmp[8].description);
    if (tmp[8].description === "") {
        $("#meal9sradio").prop("disabled", true);
    } else {
        $("#meal9sradio").prop("disabled", false);
    }
    if (orders.length > 7 && orders[7].lunch.id == tmp[8].id) {
        $("#meal9sradio").prop("checked", true);
    }
    $("#meal10s").text(tmp[9].description);
    if (tmp[9].description === "") {
        $("#meal10sradio").prop("disabled", true);
    } else {
        $("#meal10sradio").prop("disabled", false);
    }
    if (orders.length > 7 && orders[7].lunch.id == tmp[9].id) {
        $("#meal10sradio").prop("checked", true);
    }

}

function deleteOrders() {
    for(var i=0;i<tempOrders.length;i++) {
        tempOrders[i].ordered = false;
    }
    saveOrders();
}

function changeOrdersAndSave() {
    var index = 0;
    $('input:radio').each(function () {
        if (index < 7) {
            tempOrders[index].ordered = $(this).prop('checked');
        }
        index++;
    });
    saveOrders();
}

function changeOrdersAndSaveStable() {
    var soupIndex = 0;
    var index = -5;
    var selectedIndex = -100;
    $('input:radio').each(function () {
        if ($(this).prop('name') === "meal") {
            if ($(this).prop('checked')) {
                selectedIndex = index;
            }
            index++;
        } else {
            tempOrders[soupIndex].ordered = $(this).prop('checked');
            soupIndex++;
            if (soupIndex == 2) {
                soupIndex = 0;
            }
        }
    });
    if (selectedIndex == -100) {
        return;
    }
    for (i = 2; i<tempOrders.length; i++) {
        tempOrders[i].ordered = false;
    }
    var oneLine = tempOrders[2];
    var newLunch = stableLunches[selectedIndex];
    newLunch.date = oneLine.lunch.date;
    oneLine.lunch = newLunch;
    oneLine.ordered = true;
    tempOrders.push(oneLine);
    saveOrders();
}

function saveOrders() {
    var jsonLunchesOrders = JSON.stringify(tempOrders);
    $.when (
        $.ajax({
            url: urlStoreUsersOrders,
            type: "POST",
            headers: {
                "Accept": "application/json; charset=utf-8",
                "Content-Type": "application/json; charset=utf-8"
            },
            data: jsonLunchesOrders,
            dataType: "json",
            complete: function (data) {
                if (data.status === 406) {
                    alert("Chybný užívateľ, kontaktuj administrátora");
                    succesfull = false;
                } else {
                    if (data.status === 201) {
                        alert("Zmeny na obedoch sa úspešne uložili");
                        succesfull = true;
                    } else {
                        if (data.status === 204) {
                            alert("Obed sa nepodarilo ulozit, prázny výber");
                            succesfull = false;
                        } else {
                            if (data.status === 202) {
                                alert("Obedy boli vymazané");
                                succesfull = true;
                            }
                        }
                    }
                }
                if (succesfull) {
                    location.reload();
                }
            }
        })
    ).then(function () {
    });
}

function startModal(aday) {
    tempOrders = getTmpJson(aday);
    setModalTexts(tempOrders);
    $("#modalChangeOrder").modal("show");
}

function startModalStable(aday, day, week) {
    tempOrders = getTmpJson(aday);
    tempStableOrder = new StableOrder(week, day, 0);
    setModalTextsStable(stableLunches, tempOrders);
    $("#modalChangeOrderStable").modal("show");
}


function StableOrder(week, day, orderNr) {
    this.week = week;
    this.day = day;
    this.orderNr = orderNr;
}