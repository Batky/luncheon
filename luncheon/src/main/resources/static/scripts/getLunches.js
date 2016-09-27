var urlLunches = "http://localhost:8080/lunches/date/20160122";
var actualDate = new Date();
// urlLunches = urlLunches +
//     (actualDate.getFullYear()) +
//     ('0' + (actualDate.getMonth() + 1)).slice(-2) +
//     ('0' + (actualDate.getDate())).slice(-2);

$(document).ready(createTable());

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
                    $(tableName+day+" > tbody:last-child").append("<tr><td>"+soupIndex+"</td><td>"+json[index].description+"</td></tr>");
                    soupIndex++;
                } else {
                    if (mealIndex === 1) {
                        $(tableName+day+" > tbody:last-child").append("<tr><td colspan='2' class='info'>Hlavné jedlá</td></tr>");
                    }
                    $(tableName+day+" > tbody:last-child").append("<tr><td>"+mealIndex+"</td><td>"+json[index].description+"</td></tr>");
                    mealIndex++;
                }
            } else {
                soupIndex = 1;
                mealIndex = 1;
                day++;
                lastDate = json[index].date;
                var textDate = lastDate[2] + "." + lastDate[1] + "." + lastDate[0];
                $(tableHeader+day).text("Dátum: "+textDate);
                $(tableHeader+day).parent().show();
                $(tableName+day+" > tbody:last-child").append("<tr><th class='danger'>Číslo</th><th class='danger'>Popis jedla</th></tr>")
                if (json[index].soup) {
                    if (soupIndex === 1) {
                        $(tableName+day+" > tbody:last-child").append("<tr><td colspan='2' class='info'>Polievky</td></tr>");
                    }
                    $(tableName+day+" > tbody:last-child").append("<tr><td>"+soupIndex+"</td><td>"+json[index].description+"</td></tr>");
                    soupIndex++;
                } else {
                    if (mealIndex === 1) {
                        $(tableName+day+" > tbody:last-child").append("<tr><td colspan='2' class='info'>Hlavné jedlá</td></tr>");
                    }
                    $(tableName+day+" > tbody:last-child").append("<tr><td>"+mealIndex+"</td><td>"+json[index].description+"</td></tr>");
                    mealIndex++;
                }
            }
        }
    });
}

function compareArrayDate(dateArray1, dateArray2) {
    return (dateArray1[0] === dateArray2[0] && dateArray1[1] === dateArray2[1] && dateArray1[2] === dateArray2[2])
}
