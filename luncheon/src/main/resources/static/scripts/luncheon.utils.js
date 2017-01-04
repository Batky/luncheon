var urlUserActual = "/users/actual";
var urlOrdersForDate = "/orders/date/";
var urlStatisticsForUser = "/orders/stats/";
var ulrUserAdding = "/user/";
var urlStoreUsersOrders = "/orders/store/user";
var urlStableLunches = "/lunches/stable";
var weekElements = ["mon", "tue", "wed", "thu", "fri"];
var numberWeekElements = ["1", "2"];
var dateElement = "d";
var soupElements = ["s1", "s2"];
var mealElements = ["m1", "m2", "m3", "m4", "m5", "m6"];
var lastLine = "six";
var spaceElement = "space";
var buttonElement = "b";
var buttonElementStable = "bs";
var jsonElement =  "j";

function getRandomBackgroundForLoginPage() {
    return Math.floor((Math.random() * 8) + 1);
}

function getRandomBackgroundForMyOrderPage() {
    return Math.floor((Math.random() * 8) + 1);
}

function logOut() {
    location.href = "/logout";
}

function getDayFromJsonDate(json) {
    return day = new Date(json[0], json[1]-1, json[2]).getDay() - 1;
}

function equalsArrayDate(dateArray1, dateArray2) {
    return (dateArray1[0] === dateArray2[0] && dateArray1[1] === dateArray2[1] && dateArray1[2] === dateArray2[2])
}

function hideAllWeeks() {
    for (var j=0;j<2;j++) {
        for (var i = 0; i < 5; i++) {
            $("#" + weekElements[i] + numberWeekElements[j]).hide();
            $("#" + weekElements[i] + numberWeekElements[j] + spaceElement).hide();
            $("#" + weekElements[i] + numberWeekElements[j] + buttonElement).prop("disabled", true);
            $("#" + weekElements[i] + numberWeekElements[j] + buttonElementStable).prop("disabled", true);
        }
    }
}

// var cookies = getCookies();
//
// if(cookies["last_updated"] && cookies["last_updated"]<today)
// {
//     /* update variable */
//     setCookie("last_updated", today, 1);
//
// }
//
// /* utility methods starts, adding utility methods to simplify setting and getting    cookies  */
// function setCookie(name, value, daysToLive) {
//     var cookie = name + "=" + encodeURIComponent(value);
//     if (typeof daysToLive === "number")
//         cookie += "; max-age=" + (daysToLive*60*60*24);
//     document.cookie = cookie;
// }
//
// function getCookies() {
//     var cookies = {}; // The object we will return
//     var all = document.cookie; // Get all cookies in one big string
//     if (all === "") // If the property is the empty string
//         return cookies; // return an empty object
//     var list = all.split("; "); // Split into individual name=value pairs
//     for(var i = 0; i < list.length; i++) { // For each cookie
//         var cookie = list[i];
//         var p = cookie.indexOf("="); // Find the first = sign
//         var name = cookie.substring(0,p); // Get cookie name
//         var value = cookie.substring(p+1); // Get cookie value
//         value = decodeURIComponent(value); // Decode the value
//         cookies[name] = value; // Store name and value in object
//     }
//     return cookies;
// }
// /* utility methods ends */