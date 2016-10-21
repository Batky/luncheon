var dayNames = [ "Nedeľa", "Pondelok", "Utorok", "Streda", "Štvrtok", "Piatok", "Sobota" ];
var dayNamesMin = [ "Ne", "Po", "Ut", "St", "Št", "Pi", "So" ];
var dayNamesShort = [ "Ned", "Pon", "Uto", "Str", "Štv", "Pia", "Sob" ];
var monthNames = [ "Január", "Február", "Marec", "Apríl", "Máj", "Jún", "Júl", "August", "September", "Október", "November", "December" ];
var monthNamesShort = [ "Jan", "Feb", "Mar", "Apr", "Máj", "Jún", "Júl", "Aug", "Sep", "Okt", "Nov", "Dec" ];
var relations = ["EMPLOYEE", "VISITOR", "PARTIAL", "ADMIN", "POWER_USER"];
var svkRelations = ["Kmeňový", "Návšteva", "Brigádnik", "Super admin", "Administrátor"];

function getSlovakDay(date) {
    switch (date.getDay()) {
        case 0: return "Nedeľa";
        case 1: return "Pondelok";
        case 2: return "Utorok";
        case 3: return "Streda";
        case 4: return "Štvrtok";
        case 5: return "Piatok";
        case 6: return "Sobota";
    }
}

function getSlovakDayFromStringYYYYMMDD(date) {
    return getSlovakDay(new Date(date.substr(0,4) + "/" + date.substr(4,2) + "/" + date.substr(6,2)));
}

function getSlovakDayFromStringDDdotMMdotYYYY(date) {
    var splitDate = date.split(".");
    return getSlovakDay(new Date(splitDate[2] + "/" + splitDate[1] + "/" + splitDate[0]));
}

function fromSvkRelation(svkRelation) {
    for (var i=0;i<svkRelations.length;i++){
        if (svkRelations[i] === svkRelation) {
            return relations[i];
        }
    }
}

function toSvkRelation(relation) {
    for (var i=0;i<relations.length;i++){
        if (relations[i] === relation) {
            return svkRelations[i];
        }
    }
}

function isRelationVisitor(relation) {
     return (relation === relations[1]);
}

function gainReadableDateTime(dateTime) {
    var day = dateTime[2] + ".";
    day = (day.length < 3 ? "0" + day : day);
    var month = dateTime[1] + ".";
    month = (month.length < 3 ? "0" + month : month);
    var hour = dateTime[3] + ":";
    hour = (hour.length < 3 ? "0" + hour : hour);
    var minutes = dateTime[4] + "";
    minutes = (minutes.length < 2 ? "0" + minutes : minutes);
    return day + month + dateTime[0] + " " + hour + minutes;
}