var urlMonthlySum = "http://localhost:8080/orders/monthly/summary/";
var summaryJson;
var tableSummary = "#summary";

$(document).ready(function(){

    $("#datetimepicker")
        .val(toPickerDate(dateToRestString(new Date())));

    $("#printheader").text("Mesačný prehľad " + $("#datetimepicker").val());

    $("#datetimepicker")
        .change(function () {
            var valueDate = $("#datetimepicker").val();
            readData(fromPickerDate(valueDate));
            $("#printheader").text("Mesačný prehľad " + valueDate);
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

    $(tableSummary + " tr").remove();

    $.when(

        $.get(urlMonthlySum + date, function (json2) {
            summaryJson = json2;
        })

    ).then( function () {

        createTableSummary(summaryJson);

    });

}

function createTableSummary(json) {
    $(tableSummary + " > tbody:last-child").append("<tr><th class='danger'>Meno</th><th class='danger'>Počet obedov</th><th class='danger'>Cena</th><th class='danger'>Spolu</th></tr>");
    var lastPrice = json[0].price;
    var sum  = [];
    sum[0] = 0;
    sum[1] = 0;
    sum[2] = 0;
    var i=0;
    for (var index = 0; index < json.length; index++) {
        if (lastPrice != json[index].price) {
            $(tableSummary + " > tbody:last-child")
                .append(
                    "<tr>" +
                    "<td colspan='2' class = 'text-danger'>Spolu:</td>" +
                    "<td></td>" +
                    "<td class='text-danger'>" + sum[i] + "</td>" +
                    "</tr>" +
                    "<tr><td colspan='4'></td></tr>");
            i++;
            lastPrice = json[index].price;
        }
        sum[i] += json[index].sum;
        $(tableSummary + " > tbody:last-child")
            .append(
                "<tr>" +
                "<td width='60%'>" + json[index].name + "</td>" +
                "<td width='10%'>" + json[index].count + "</td>" +
                "<td width='15%'>" + json[index].price + "</td>" +
                "<td width='15%'>" + json[index].sum + "</td>" +
                "</tr>");
    }
    $(tableSummary + " > tbody:last-child")
        .append(
            "<tr>" +
            "<td colspan='2' class='text-danger'>Spolu:</td>" +
            "<td></td>" +
            "<td class='text-danger'>" + sum[i] + "</td>" +
            "</tr>");
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
