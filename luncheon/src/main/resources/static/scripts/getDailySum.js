var urlDaily = "http://obedy.kudela.sk:8080/orders/daily/";
var urlDailySum = "http://obedy.kudela.sk:8080/orders/daily/summary/";
var urlPostLunches = "http://obedy.kudela.sk:8080/lunches/lunches";
var dailyJson;
var summaryJson;
var tableDaily = "#daily";
var tableSummary = "#summary";

$(document).ready(function(){

    $("#datetimepicker")
        .val(toPickerDate(dateToRestString(new Date())));

    $("#printheader").text("Denný prehľad " + $("#datetimepicker").val());

    $("#datetimepicker")
        .change(function () {
            var valueDate = $("#datetimepicker").val();
            readData(fromPickerDate(valueDate));
            $("#printheader").text("Denný prehľad " + valueDate);
        });

    readData(dateToRestString(new Date()));

    $("#logout").click(function(){
        location.href = "/logout";
    });

    $("#backbtn").click(function () {
        window.history.back();
    })

});

function readData(date) {

    $(tableDaily + " tr").remove();
    $(tableSummary + " tr").remove();

    $.when(

        $.get(urlDaily + date, function (json) {
            dailyJson = json;
        }),

        $.get(urlDailySum + date, function (json2) {
            summaryJson = json2;
        })

    ).then( function () {

        createTable(dailyJson);
        createTableSummary(summaryJson);

    });

}

function createTable(json) {
    $(tableDaily + " > tbody:last-child").append("<tr><th class='danger'>Meno</th><th class='danger'>Polievka</th><th class='danger'>Hlavné jedlo</th></tr>");
    for (var index = 0; index < json.length; index++) {
        $(tableDaily + " > tbody:last-child")
            .append(
                "<tr>" +
                "<td width='70%'>" + json[index].name + "</td>" +
                "<td width='15%'>" + json[index].soup + "</td>" +
                "<td width='15%'>" + json[index].meal + "</td>" +
                "</tr>");
    }
}

function createTableSummary(json) {
    $(tableSummary + " > tbody:last-child").append("<tr><th class='danger'>Označenie</th><th class='danger'>Názov</th><th class='danger'>Počet</th></tr>");
    for (var index = 0; index < json.length; index++) {
        $(tableSummary + " > tbody:last-child")
            .append(
                "<tr>" +
                "<td width='15%'>" + json[index].mark + "</td>" +
                "<td width='70%'>" + json[index].meal + "</td>" +
                "<td width='15%'>" + json[index].count + "</td>" +
                "</tr>");
        if (index == 1) {
            $(tableSummary + " > tbody:last-child")
                .append("<tr><td colspan='3'></td> </tr>");
        }
    }
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

function fromPickerToArray() {
    var date = $("#datetimepicker").val();
    var dateArray = [];
    dateArray.push(date.substr(0,4));
    dateArray.push(date.substr(5,2));
    dateArray.push(date.substr(8,2));
    return dateArray;
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
    $.ajax({
        url:urlPostLunches,
        type:"POST",
        headers: {
            "Accept" : "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
        data:jsonLunches,
        dataType:"json"
    });
    location.reload();
}

function lunch(id, soup, date, description) {
    this.id = id;
    this.soup = soup;
    this.date = date;
    this.description = description;
}

function dateToRestString(date) {
    var result = (date.getFullYear()) +
        ('0' + (date.getMonth() + 1)).slice(-2) +
        ('0' + (date.getDate())).slice(-2);
    console.log(result);
    return result;
}
