var urlLunches = "/lunches/date/";
var urlOneLunch = "/lunches/exact/date/";
var urlPostLunches = "/lunches/lunches";

var actualDate = new Date();
var actualDateChanged = (actualDate.getFullYear()) +
    ('0' + (actualDate.getMonth() + 1)).slice(-2) +
    ('0' + (actualDate.getDate())).slice(-2);
urlLunches = urlLunches + actualDateChanged;

$(document).ready(function(){

    createTable();

    $("#logout").click(function(){
        location.href = "/logout";
    });

    $("#myModal").on('show.bs.modal', function () {
        fillModalForm(actualDateChanged, false);
    });
    $("#datetimepicker1").change(function () {
        fillModalForm(fromPickerDate($("#datetimepicker1").val()), true);
    });

    $("#changebtn").click(function(){
        location.href = "/admin";
    });

    $("#daily").click(function(){
        location.href = "/daily";
    });

    $("#monthly").click(function(){
        location.href = "/monthly";
    });

    $("#olymp").click(function () {
        location.href = "/orders/monthly/olymp/" + getDateMonthBack();
    })
});

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

function createTable() {
    $.get(urlLunches , function(json ) {
        var index;
        var lastDate = json[0].date;
        var soupIndex = 1;
        var mealIndex = 1;
        var day = 1;
        var textDate = lastDate[2] + "." + lastDate[1] + "." + lastDate[0];
        var tableName = "#day";
        var tableHeader = "#table";
        $(tableHeader+day).text("Dátum: "+textDate);
        $(tableHeader+day).parent().show();
        $(tableName+day+" > tbody:last-child").append("<tr><th class='danger'>Číslo</th><th class='danger'>Popis jedla</th></tr>");
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
                $(tableHeader+day).text("Dátum: "+textDate);
                $(tableHeader+day).parent().show();
                $(tableName+day+" > tbody:last-child").append("<tr><th class='danger'>Číslo</th><th class='danger'>Popis jedla</th></tr>")
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
    });
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
    $.when(
        $.ajax({
            url:urlPostLunches,
            type:"POST",
            headers: {
                "Accept" : "application/json; charset=utf-8",
                "Content-Type": "application/json; charset=utf-8"
            },
            data:jsonLunches,
            dataType:"json"
        })
    ).then(
        function () {
            location.reload();
        }
    );
}

function lunch(id, soup, date, description) {
    this.id = id;
    this.soup = soup;
    this.date = date;
    this.description = description;
}