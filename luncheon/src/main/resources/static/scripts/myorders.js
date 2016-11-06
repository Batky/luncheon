var user;
var usersOrders;
var tempOrders;
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

    var urlOrders;

    $.when (

        $.getJSON(urlUserActual, function (json) {
            user = json;
            urlOrders = urlOrdersForDate + actualDateChanged + ulrUserAdding + user.id;
        })

    ).then (
        function () {
            $.get(urlOrders , function(jsonUserOrder) {
                usersOrders = jsonUserOrder;
                fillTables(usersOrders);
            })
        }
    );

});

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
    $("#meal4radio").prop("checked", tmp[4].ordered);
    $("#meal5").text(tmp[6].lunch.description);
    $("#meal5radio").prop("checked", tmp[6].ordered);
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
        tempOrders[index].ordered = !!$(this).prop('checked');
        index++;
    });
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

