var urlLunches = "/lunches/date/";
var lunchesJson;
var tableLunches = "#lunches";
var dateTimePicker = $("#datetimepicker");
var changeTime = function () {
    cleanTables();
    readData(fromPickerDate(dateTimePicker.val()));
};

$(document).ready(function(){

    dateTimePicker.datepicker({
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

    dateTimePicker.datepicker().show();

    dateTimePicker
        .val(toPickerDate(dateToRestString(new Date())));

    readData(fromPickerDate(dateTimePicker.val()));

    $("#logout").click(function(){
        location.href = "/logout";
    });

    $("#backbtn").click(function () {
        window.history.back();
    });

    dateTimePicker.change(changeTime);

    $("#select").click(changeTime);
});

function readData(date) {

    $(tableLunches + " tr").remove();

    $.when(

        $.get(urlLunches + date, function (json) {
            lunchesJson = json;
        })

    ).then( function () {

        createTable(lunchesJson);

    });

}

function createTable(json) {
    var index;
    var lastDate = json[0].date;
    var soupIndex = 1;
    var mealIndex = 1;
    var day = 1;
    var textDate = lastDate[2] + "." + lastDate[1] + "." + lastDate[0];
    var tableName = "#day";
    var tableHeader = "#table";
    var tableHeaderT = "#tableT";
    var dayName = "#dayName";
    $(tableHeader + day).text(getSlovakDayFromStringDDdotMMdotYYYY(textDate));
    $(dayName + day).text(getSlovakDayFromStringDDdotMMdotYYYY(textDate) + " " + textDate);
    $(tableHeaderT + day).text(textDate);
    $(tableHeader+day).parent().show();
    $(tableName+day+" > tbody:last-child").append("<tr class='hidden-print'><th class='danger'>Číslo</th><th class='danger'>Popis jedla</th></tr>");
    for (index = 0; index < json.length; index++) {
        if (compareArrayDate(json[index].date, lastDate)) {
            if (json[index].soup) {
                if (soupIndex === 1) {
                    $(tableName+day+" > tbody:last-child").append("<tr><td colspan='2' class='info'>Polievky</td></tr>");
                }
                $(tableName+day+" > tbody:last-child").append("<tr><td width='10%'>"+soupIndex+"</td><td>"+json[index].description+"</td></tr>");
                soupIndex++;
            } else {
                if (mealIndex === 1) {
                    $(tableName+day+" > tbody:last-child").append("<tr><td colspan='2' class='info'>Hlavné jedlá</td></tr>");
                }
                $(tableName+day+" > tbody:last-child").append("<tr><td width='10%'>"+mealIndex+"</td><td>"+json[index].description+"</td></tr>");
                mealIndex++;
            }
        } else {
            soupIndex = 1;
            mealIndex = 1;
            day++;
            lastDate = json[index].date;
            textDate = lastDate[2] + "." + lastDate[1] + "." + lastDate[0];
            $(tableHeader + day).text(getSlovakDayFromStringDDdotMMdotYYYY(textDate));
            $(dayName + day).text(getSlovakDayFromStringDDdotMMdotYYYY(textDate) + " " + textDate);
            $(tableHeaderT + day).text(textDate);
            $(tableHeader + day).parent().show();
            $(tableName + day + " > tbody:last-child").append("<tr class='hidden-print'><th class='danger'>Číslo</th><th class='danger'>Popis jedla</th></tr>")
            if (json[index].soup) {
                if (soupIndex === 1) {
                    $(tableName+day+" > tbody:last-child").append("<tr><td colspan='2' class='info'>Polievky</td></tr>");
                }
                $(tableName+day+" > tbody:last-child").append("<tr><td width='10%'>"+soupIndex+"</td><td>"+json[index].description+"</td></tr>");
                soupIndex++;
            } else {
                if (mealIndex === 1) {
                    $(tableName+day+" > tbody:last-child").append("<tr><td colspan='2' class='info'>Hlavné jedlá</td></tr>");
                }
                $(tableName+day+" > tbody:last-child").append("<tr><td width='10%'>"+mealIndex+"</td><td>"+json[index].description+"</td></tr>");
                mealIndex++;
            }
        }
    }
}


function toPickerDate(date) {
    return date.substr(6,2) + "." + date.substr(4,2) + "." + date.substr(0,4);
}

function fromPickerDate(date) {
    return date.substr(6,4) + date.substr(3,2) + date.substr(0,2);
}


function dateToRestString(date) {
    return (date.getFullYear()) +
        ('0' + (date.getMonth() + 1)).slice(-2) +
        ('0' + (date.getDate())).slice(-2);
}

function compareArrayDate(dateArray1, dateArray2) {
    return (dateArray1[0] === dateArray2[0] && dateArray1[1] === dateArray2[1] && dateArray1[2] === dateArray2[2])
}

function cleanTables() {
    var tableName = "#day";
    var tableHeader = "#table";
    for (var i=1;i<=5;i++) {
        $(tableName + i + " tr").remove();
        $(tableHeader + i + " tr").remove();
    }
}
