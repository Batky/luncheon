var urlOrders = "http://obedy.kudela.sk:8080/orders/exact/date/";
var urlUser = "http://obedy.kudela.sk:8080/users/actual";
var urlUserId = "http://obedy.kudela.sk:8080/users/id/";
var ulrUserAdd = "/user/";
var urlStoreUser = "http://obedy.kudela.sk:8080/orders/store/user";
var urlAllUsers = "http://obedy.kudela.sk:8080/users/all";
var actualDate = new Date();
var actualDateChanged = (actualDate.getFullYear()) +
    ('0' + (actualDate.getMonth() + 1)).slice(-2) +
    ('0' + (actualDate.getDate())).slice(-2);
var user;
var lunchesJson;

$(document).ready(function(){

    $("#datetimepicker")
        .val(toPickerDate(dateToRestString(new Date())));

    $("#datetimepicker")
        .change(function () {
            $("#day1")
                .find("tr")
                .remove();
            var valueDate = $("#datetimepicker").val();
            actualDateChanged = fromPickerDate(valueDate);
            createTable(user);
        });

    $("#backbtn").click(function () {
        window.history.back();
    });

    $("#btnDel").click(function () {
        deleteOrders();
    });

    $("#btnSave").click(function () {
        gatherOrders();
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

        $("#day1")
            .find("tr")
            .remove();
        var str = $(this).text();
        var num = str.split("@",1);
        $.getJSON(urlUserId+ num[0], function (json) {
            user = json;
            $("#userHeader").text("Zoznam obedov - " + user.longName);
            createTable(user);
        });
    });

    var top = $('.floating').offset().top;
    // $('.trigger').click(function () {
    //     $('.static').css('position','');
    //     $('.left2').toggle('slow',function(){
    //         top = $('.static').offset().top;
    //     });
    //
    //
    // });

    // $(document).scroll(function(){
    //     $('.floating').css('position','');
    //     top = $('.floating').offset().top;
    //     $('.floating').css('position','absolute');   $('.floating').css('top',Math.max(top,$(document).scrollTop()));
    // });

});

function createTableUsers(json) {
    for (var index=0;index < json.length; index++){
        $("#users")
            .find("> tbody:last-child")
            .append("<tr><td hidden>"+json[index].id+"@</td><td>" + json[index].lastName + " " + json[index].firstName + "</td></tr>");
    }
}

function create(json) {
    lunchesJson = json;
    var checked = "";
    var index;
    var lastDate = lunchesJson[0].lunch.date;
    var soupIndex = 1;
    var mealIndex = 1;
    var day = 1;
    var textDate = lastDate[2] + "." + lastDate[1] + "." + lastDate[0];
    $("#day1")
        .find("> tbody:last-child")
        .append("<tr><th class='danger'>Číslo</th><th class='danger'>Popis jedla</th><th class='danger'>Objednávka</th></tr>");
    var datepart = 1;
    for (index = 0; index < lunchesJson.length; index++) {
        var lunch = lunchesJson[index].lunch;
        var ordered = lunchesJson[index].ordered;
        if (compareArrayDate(lunch.date, lastDate)) {
            if (lunch.soup) {
                checked = "";
                if (ordered) {
                    checked = "checked"
                }
                var radios = "<input type='radio' name='soup" + datepart + "' " + checked + ">";
                if (soupIndex === 1) {
                    $("#day1")
                        .find("> tbody:last-child")
                        .append("<tr><td colspan='3' class='info'>Polievky</td></tr>");
                }
                $("#day1")
                    .find("> tbody:last-child")
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
                var radiom = "<input type='radio' name='meal" + datepart + "' " + checked + ">";
                if (mealIndex === 1) {
                    $("#day1")
                        .find("> tbody:last-child")
                        .append("<tr><td colspan='3' class='info'>Hlavné jedlá</td></tr>");
                }
                $("#day1")
                    .find("> tbody:last-child")
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
            $("#day1")
                .find("> tbody:last-child")
                .append("<tr><th class='danger'>Číslo</th><th class='danger'>Popis jedla</th><th class='danger'>Objednávka</th></tr>")
            if (lunch.soup) {
                checked = "";
                if (ordered) {
                    checked = "checked"
                }
                var radios1 = "<input type='radio' name='soup" + datepart + "' " + checked + ">";
                if (soupIndex === 1) {
                    $("#day1")
                        .find("> tbody:last-child")
                        .append("<tr><td colspan='3' class='info'>Polievky</td></tr>");
                }
                $("#day1")
                    .find("> tbody:last-child")
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
                var radiom1 = "<input type='radio' name='meal" + datepart + "' " + checked + ">";
                if (mealIndex === 1) {
                    $("#day1")
                        .find("> tbody:last-child")
                        .append("<tr><td colspan='3' class='info'>Hlavné jedlá</td></tr>");
                }
                $("#day1")
                    .find("> tbody:last-child")
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

function saveOrders(refresh) {
    var jsonLunchesOrders = JSON.stringify(lunchesJson);
    console.log(jsonLunchesOrders);
    console.log(urlStoreUser);
    $.when(
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
                // location.reload();
            } else {
                alert("Obed sa nepodarilo ulozit, skontroluj vyber !");
            }
        }
    })).then(function () {
        if (refresh) {
            $("#day1")
                .find("tr")
                .remove();
            createTable(user);
        }
    });
}
function gatherOrders() {
    var index = 0;
    console.log(lunchesJson);
    $('input:radio').each(function () {
        lunchesJson[index].ordered = !!$(this).prop('checked');
        index++;
    });
    console.log(lunchesJson);
    saveOrders(false);
}

function deleteOrders() {
    var firstDate = lunchesJson[0].lunch.date;
    for(var i=0;i<lunchesJson.length;i++){
        if (compareArrayDate(firstDate, lunchesJson[i].lunch.date)) {
            lunchesJson[i].ordered = false;
        } else {
            firstDate = lunchesJson[i].lunch.date;
            lunchesJson[i].ordered = false;
        }
    }
    saveOrders(true);
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