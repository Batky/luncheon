var urlUsers = "/security/users/";
var usersJson;
var tableUsers = "#users";
var lastSelectedRow;
var urlUserCard = "users/card/";
var urlUserId = "users/id/";
var urlUserPid = "users/pid/";
var urlStoreUser = "users/user/";
var selectedUserId = 0;

$(document).ready(function(){

    readData();

    $("#logout").click(function(){
        location.href = "/logout";
    });

    $(document).on('click', '#users > tbody > tr',  function() {

        $(lastSelectedRow).removeClass("selectedRow");
        $(this).addClass("selectedRow");
        lastSelectedRow = $(this);
        var str = $(this).text().split("@");
        selectedUserId = str[0];
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
                "<td width='20%'>" + toSvkRelation(json[index].relation) + "</td>" +
                "</tr>");
    }
}

function cleanForm() {
    $("#id").val("");
    $("#pid").val("");
    $("#name").val("");
    $("#lastName").val("");
    $("#barCode").val("");
    $("#type").val(svkRelations[0]);
}

function createUser() {
    var user = new User(
        $("#id").val(),
        $("#pid").val(),
        $("#name").val(),
        $("#lastName").val(),
        $("#barCode").val(),
        $("#type").val());

    var jsonUser = JSON.stringify(user);

    $.when(
        $.ajax({
            url:urlStoreUser,
            type:"POST",
            headers: {
                "Accept" : "application/json; charset=utf-8",
                "Content-Type": "application/json; charset=utf-8"
            },
            data:jsonUser,
            dataType:"json"
        })
    ).then(
        function () {
            location.reload();
        }
    );
}

function User(id, pid, firstName, lastName, barCode, relation) {
    this.id = id;
    this.pid = pid;
    this.firstName = firstName;
    this.lastName = lastName;
    this.barCode = barCode;
    this.relation = fromSvkRelation(relation);
}

function openDialogCreate() {
    cleanForm();
    $("#myModal").modal("show");
}

function openDialogUpdate() {
    $.when(
        $.get(urlUserId + selectedUserId, function (json) {
            $("#id").val(json.id);
            $("#pid").val(json.pid);
            $("#name").val(json.firstName);
            $("#lastName").val(json.lastName);
            $("#barCode").val(json.barCode);
            $("#type").val(toSvkRelation(json.relation));
        })
    ).then(
        function () {
            $("#myModal").modal("show");
        }
    );
}