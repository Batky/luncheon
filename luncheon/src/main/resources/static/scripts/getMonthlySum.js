var urlMonthlySum = "/orders/monthly/summary/";
var summaryJson;
var tableSummary = "#summary";
var changeTime = function () {
    var valueDate = $("#datetimepicker").val();
    readData(fromPickerDate(valueDate));
    $("#printheader").text("Mesačný prehľad " + valueDate);
};

$(document).ready(function(){

    // $("#datetimepicker").datepicker({
    //     dateFormat: "mm.yy",
    //     dayNames: [ "Nedeľa", "Pondelok", "Utorok", "Streda", "Štvrtok", "Piatok", "Sobota" ],
    //     dayNamesMin: [ "Ne", "Po", "Ut", "St", "Št", "Pi", "So" ],
    //     dayNamesShort: [ "Ned", "Pon", "Uto", "Str", "Štv", "Pia", "Sob" ],
    //     firstDay: 1,
    //     monthNames: [ "Január", "Február", "Marec", "Apríl", "Máj", "Jún", "Júl", "August", "September", "Október", "November", "December" ],
    //     monthNamesShort: [ "Jan", "Feb", "Mar", "Apr", "Máj", "Jún", "Júl", "Aug", "Sep", "Okt", "Nov", "Dec" ],
    //     showWeek: false,
    //     weekHeader: "T"
    // });

    $("#datetimepicker").MonthPicker({
        Button: false,
        MonthFormat: 'mm.yy',
        i18n: {
            year: 'Rok',
            prevYear: 'Predch. rok',
            nextYear: 'Ďalší rok',
            next12Years: 'Skoč vpred o 12 rokov',
            prev12Years: 'Skoč vzad o 12 rokov',
            nextLabel: 'Ďalší',
            prevLabel: 'Predch.',
            buttonText: 'Otvor výber mesiaca',
            jumpYears: 'Skoč roky',
            backTo: 'Späť na',
            months: ['Jan.', 'Feb.', 'Mar.', 'Apr.', 'Máj', 'Jún', 'Júl', 'Aug.', 'Sep.', 'Okt.', 'Nov.', 'Dec.']
        },
        OnAfterChooseMonth: function(selectedDate) {
            changeTime();
        }
        // dateFormat: "mm.yy",
        // dayNames: [ "Nedeľa", "Pondelok", "Utorok", "Streda", "Štvrtok", "Piatok", "Sobota" ],
        // dayNamesMin: [ "Ne", "Po", "Ut", "St", "Št", "Pi", "So" ],
        // dayNamesShort: [ "Ned", "Pon", "Uto", "Str", "Štv", "Pia", "Sob" ],
        // firstDay: 1,
        // monthNames: [ "Január", "Február", "Marec", "Apríl", "Máj", "Jún", "Júl", "August", "September", "Október", "November", "December" ],
        // monthNamesShort: [ "Jan", "Feb", "Mar", "Apr", "Máj", "Jún", "Júl", "Aug", "Sep", "Okt", "Nov", "Dec" ],
        // showWeek: false,
        // weekHeader: "T"
    });

    // $("#datetimepicker").datepicker().show();
    //
    $("#datetimepicker")
        .val(toPickerDate(dateToRestString(new Date())));

    $("#printheader").text("Mesačný prehľad " + $("#datetimepicker").val());

    $("#datetimepicker")
        .change(changeTime);

    $("#select").click(changeTime);

    readData(dateToRestString(new Date()));

    $("#logout").click(function(){
        location.href = "/logout";
    });

    $("#backbtn").click(function () {
        window.history.back();
    });

    $("#olymp").click(function () {
        location.href = "/orders/monthly/olymp/" + fromPickerDate($("#datetimepicker").val());
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
    return date.substr(4,2) + "." + date.substr(0,4);
    // return date.substr(0,4) + "-" + date.substr(4,2) + "-" + date.substr(6,2);
}

function fromPickerDate(date) {
    var toDate = date.substr(3,4) + date.substr(0,2) + "01";
    return toDate;
    // return date.substr(0,4) + date.substr(5,2) + date.substr(8,2);
}

function dateToRestString(date) {
    var result = (date.getFullYear()) +
        ('0' + (date.getMonth() + 1)).slice(-2) +
        ('0' + (date.getDate())).slice(-2);
    return result;
}

function getDateMonthBack() {
    if (actualDate.getMonth() == 0) {
        return (actualDate.getFullYear() - 1) +
            ('0' + (actualDate.getMonth() + 12)).slice(-2) +
            ('0' + (actualDate.getDate())).slice(-2);
    } else {
        return (actualDate.getFullYear()) +
            ('0' + (actualDate.getMonth())).slice(-2) +
            ('0' + (actualDate.getDate())).slice(-2);
    }
}
