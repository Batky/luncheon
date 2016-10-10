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
    return getSlovakDay(new Date(date.substr(6,4) + "/" + date.substr(3,2) + "/" + date.substr(0,2)));
}