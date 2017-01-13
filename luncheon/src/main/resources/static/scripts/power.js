var urlLunches = "/orders/exact/date/";
var urlUser = "/user/";
var lunchesJson;
var users = [];
var usersWithId = [];
var urlAllUsers = "/users/all";
var urlStoreUser = "/orders/store/user";
var urlUserId = "users/id/";
var datetimepicker = $("#datetimepicker");
var changeTime = function () {
    cleanTables();
    var valueDate = datetimepicker.val();
    var valueId = idFromName($("#userpicker").val());
    readData(fromPickerDate(valueDate), valueId);
};
var visitor = false;

$(document).ready(function(){

    datetimepicker.datepicker({
        dateFormat: "dd.mm.yy",
        dayNames: dayNames,
        dayNamesMin: dayNamesMin,
        dayNamesShort: dayNamesShort,
        firstDay: 1,
        monthNames:monthNames,
        monthNamesShort: monthNamesShort
    });

    datetimepicker.datepicker().show();

    datetimepicker
        .val(toPickerDate(dateToRestString(new Date())));

    // readData(fromPickerDate(datetimepicker.val()));

    $("#logout").click(function(){
        location.href = "/logout";
    });

    $("#backbtn").click(function () {
        location.href = "/lunches";
    });

    datetimepicker
        .change(changeTime);

    $("#selectDate")
        .click(changeTime);

    $("#selectUser")
        .click(changeTime);

    readUsers();

    $("#delete").click(function () {
        deleteOrders();
    });

    $("#save").click(function () {
        if (visitor) {
            $("#description").val(getDescription());
            $("#myModal").modal("show");
        } else {
            gatherOrders(false);
        }
    });

    $("#move").click(function () {
        moveOrders();
    });

});

function readData(date, userId) {
    $.when(

        $.get(urlLunches + date + urlUser + userId, function (json) {
            lunchesJson = json;
        }),

        $.get(urlUserId + userId, function (json) {
            if (isRelationVisitor(json.relation)) {
                visitor = true;
            } else {
                visitor = false;
            }
        })

    ).then( function () {
        createTable(lunchesJson);
    });
}

function createTable(json) {
    var checked = "";
    var index;
    var lastDate = json[0].lunch.date;
    var soupIndex = 1;
    var mealIndex = 1;
    var textDate = lastDate[2] + "." + lastDate[1] + "." + lastDate[0];
    var tableName = "#day1";
    var tableHeader = "#table1";
    $(tableHeader).text("Dátum: "+textDate);
    $(tableHeader).parent().show();
    $(tableName+" > tbody:last-child")
        .append(
            "<tr>" +
            "<th class='danger'>Číslo</th>" +
            "<th class='danger'>Popis jedla</th>" +
            "<th class='danger'>Objednané</th>" +
            "</tr>");
    for (index = 0; index < json.length; index++) {
        if (json[index].lunch.soup) {
            if (soupIndex === 1) {
                $(tableName+" > tbody:last-child").append("<tr><td colspan='3' class='info'>Polievky</td></tr>");
            }
            checked = "";
            if (json[index].ordered) {
                checked = "checked";
            }
            var radio = "<input type='radio' name='soup' " + checked + " >";
            $(tableName+" > tbody:last-child")
                .append("" +
                    "<tr>" +
                    "<td width='10%'>"+soupIndex+"</td>" +
                    "<td>"+json[index].lunch.description+"</td>" +
                    "<td width='10%'>"+radio+"</td>" +
                    "</tr>");
            soupIndex++;
        } else {
            if (mealIndex === 1) {
                $(tableName+" > tbody:last-child").append("<tr><td colspan='3' class='info'>Hlavné jedlá</td></tr>");
            }
            checked = "";
            if (json[index].ordered) {
                checked = "checked";
            }
            var radiom = "<input type='radio' name='meal' " + checked + " >";
            $(tableName+" > tbody:last-child")
                .append(
                    "<tr>" +
                    "<td width='10%'>"+mealIndex+"</td>" +
                    "<td>"+json[index].lunch.description+"</td>" +
                    "<td width='10%'>"+radiom+"</td>" +
                    "</tr>");
            mealIndex++;
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
    $("#day1").find("tr").remove();
    $("#table1").find("tr").remove();
}

function readUsers() {
    $.get(urlAllUsers, function (json) {
        users = [];
        usersWithId = [];
        for(var i=0;i<json.length;i++){
            var longName = json[i].longName;
            var id = json[i].id;
            var longNameWithId = [];
            longNameWithId.push(id);
            longNameWithId.push(longName);
            users.push(longName);
            usersWithId.push(longNameWithId);
        }
        $( "#userpicker" ).autocomplete({
            source: users
        });

        $( "#userpicker2" ).autocomplete({
            source: users
        });

    });
}

function idFromName(name) {
    if (name != "") {
        for(var i = 0;i<usersWithId.length;i++){
            if (name === usersWithId[i][1]) {
                return usersWithId[i][0];
            }
        }
    }
    return 0;
}

function saveOrders(refresh, show) {
    var jsonLunchesOrders = JSON.stringify(lunchesJson);
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
                if (show) {
                    if (data.status == 201) {
                        alert("Zmeny na obedoch sa úspešne uložili");
                    }
                    else
                        if (data.status == 202) { alert("Zmeny boli akceptované"); }
                        else { alert("Obed sa nepodarilo ulozit, skontroluj vyber !");}
                }
            }
        })).then(function () {
        if (refresh) {
            changeTime();
        }
    });
}

function getDescription() {
    if (visitor) {
        var description = "";
        for (var i=0;i<lunchesJson.length;i++) {
            if (lunchesJson[i].ordered) {
                description = lunchesJson[i].description;
                break;
            }
        }
        return description;
    } else {
        return "";
    }
}

function gatherOrders(withDescription) {
    var index = 0;
    $('input:radio').each(function () {
        lunchesJson[index].ordered = $(this).prop('checked');
        if (withDescription) {
            lunchesJson[index].description = $("#description").val();
        }
        index++;
    });
    saveOrders(false, true);
}

function deleteOrders() {
    for(var i=0;i<lunchesJson.length;i++){
        lunchesJson[i].ordered = false;
    }
    saveOrders(true, true);
}

function moveOrders() {
    var index = 0;
    $('input:radio').each(function () {
        lunchesJson[index].ordered = !!$(this).prop('checked');
        index++;
    });
    var tmpOrdered  = [];
    var tmpDate = lunchesJson[0].lunch.date[0] + "" + lunchesJson[0].lunch.date[1] + "" + lunchesJson[0].lunch.date[2];
    for(var indx=0;indx<lunchesJson.length;indx++){
        if (lunchesJson[indx].ordered) {
            tmpOrdered.push(true);
        } else {
            tmpOrdered.push(false);
        }
        lunchesJson[indx].ordered = false;
    }
    var newUserId = selectUser();
    if (newUserId === 0) {
        alert("Nie je vybratý žiadny užívateľ");
        return;
    }
    if (newUserId === lunchesJson[0].user) {
        alert("Nie je možné presunúť obedy na toho istého užívateľa");
    } else {
        if (!noneOrdered(tmpOrdered)) {
            alert("Povodný užívateľ nemá nič objednané");
        } else {
            var hasOrder = false;
            $.when(
                $.get(urlLunches + tmpDate + urlUser + newUserId, function (json) {
                    for (var i=0; i<json.length;i++) {
                        if (json[i].ordered) {
                            hasOrder = true;
                            break;
                        }
                    }
                    if (hasOrder) {
                        alert("Užívateľ na ktorého chcete presunúť obed, má už obed zapísaný.");
                    } else {
                        saveOrders(false, false);
                        for (var ix=0;ix<lunchesJson.length;ix++){
                            lunchesJson[ix].ordered = tmpOrdered[ix];
                            lunchesJson[ix].user = newUserId;
                        }
                        saveOrders(true, true);
                    }
                })
            ).then( function () {
            });
        }
    }
}

function noneOrdered(json) {
    var ordered = false;
    for (var i=0; i< json.length; i++) {
        if (json[i]) {
            ordered = true;
            break;
        }
    }
    return ordered;
}

function selectUser() {
    var user = $("#userpicker2").val();
    if (user === "") {
        return 0;
    }
    for(var i=0;i<usersWithId.length;i++) {
        if (user === usersWithId[i][1]) {
            return usersWithId[i][0];
        }
    }
    return 0;
}

function continueSave() {
    gatherOrders(true);
}