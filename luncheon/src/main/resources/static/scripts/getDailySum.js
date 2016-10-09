var urlDaily = "/orders/daily/";
var urlDailySum = "/orders/daily/summary/";
var dailyJson;
var summaryJson;
var tableDaily = "#daily";
var tableSummary = "#summary";

$(document).ready(function(){

    $("#datetimepicker").datepicker({
        dateFormat: "dd.mm.yy",
        dayNames: [ "Nedeľa", "Pondelok", "Utorok", "Streda", "Štvrtok", "Piatok", "Sobota" ],
        dayNamesMin: [ "Ne", "Po", "Ut", "St", "Št", "Pi", "So" ],
        dayNamesShort: [ "Ned", "Pon", "Uto", "Str", "Štv", "Pia", "Sob" ],
        firstDay: 1,
        monthNames: [ "Január", "Február", "Marec", "Apríl", "Máj", "Jún", "Júl", "August", "September", "Október", "November", "December" ],
        monthNamesShort: [ "Jan", "Feb", "Mar", "Apr", "Máj", "Jún", "Júl", "Aug", "Sep", "Okt", "Nov", "Dec" ],
        showWeek: false,
        weekHeader: "T"
    });

    $("#datetimepicker").datepicker().show();

    $("#datetimepicker")
        .val(toPickerDate(dateToRestString(new Date())));

    $("#printheader").text("Denný prehľad " + $("#datetimepicker").val());

    $("#datetimepicker")
        .change(function () {
            var valueDate = $("#datetimepicker").val();
            readData(fromPickerDate(valueDate));
            $("#printheader").text("Denný prehľad " + valueDate);
        });

    $("#select").click(function () {
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

function toPickerDate(date) {
    var toDate = date.substr(6,2) + "." + date.substr(4,2) + "." + date.substr(0,4);
    return toDate;
}

function fromPickerDate(date) {
    return date.substr(6,4) + date.substr(3,2) + date.substr(0,2);
}

function dateToRestString(date) {
    var result = (date.getFullYear()) +
        ('0' + (date.getMonth() + 1)).slice(-2) +
        ('0' + (date.getDate())).slice(-2);
    console.log(result);
    return result;
}
