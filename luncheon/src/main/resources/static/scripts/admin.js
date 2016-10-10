var urlUsers = "/security/users/";
var usersJson;
var tableUsers = "#users";
var lastSelectedRow;

$(document).ready(function(){

    readData();

    $("#logout").click(function(){
        location.href = "/logout";
    });

    $(document).on('click', '#users > tbody > tr',  function() {

        $(lastSelectedRow).removeClass("selectedRow");
        $(this).addClass("selectedRow");
        lastSelectedRow = $(this);
        var str = $(this).text();
        console.log(str);
    });

});

function readData() {

    $(tableUsers + " tr").remove();

    $.when(

        $.get(urlUsers, function (json) {
            usersJson = json;
        })

    ).then( function () {

        createTable(usersJson);

    });

}

function createTable(json) {
    $(tableUsers + " > tbody:last-child")
        .append(
            "<tr>" +
            "<th class='danger'>id</th>" +
            "<th class='danger' hidden>Separator</th>" +
            "<th class='danger'>Osobné č.</th>" +
            "<th class='danger'>Čítačka</th>" +
            "<th class='danger'>Meno</th>" +
            "<th class='danger'>Typ stravníka</th>" +
            "</tr>");
    for (var index = 0; index < json.length; index++) {
        $(tableUsers + " > tbody:last-child")
            .append(
                "<tr>" +
                "<td width='10%'>" + json[index].id + "</td>" +
                "<td width='10%' hidden>@</td>" +
                "<td width='10%'>" + json[index].pid + "</td>" +
                "<td width='20%'>" + json[index].barCode + "</td>" +
                "<td width='40%'>" + json[index].longName + "</td>" +
                "<td width='20%'>" + slovakRelation(json[index].relation) + "</td>" +
                "</tr>");
    }
}

function slovakRelation(relation) {
    if (relation === 'EMPLOYEE') {
        return "Kmeňový";
    } else {
        if (relation === 'VISITOR') {
            return "Návšteva";
        } else {
            if (relation === 'PARTIAL') {
                return "Brigádnik";
            } else {
                if (relation === 'ADMIN') {
                    return "Super admin"
                } else {
                    if (relation === 'POWER_USER') {
                        return "Administrátor"
                    }
                }
            }
        }
        return relation;
    }
}
