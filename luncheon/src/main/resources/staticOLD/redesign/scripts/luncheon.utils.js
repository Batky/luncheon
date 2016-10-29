var backgroundPictureFileName = "redesign/images/zdrave_jedlo_";

function getRandomBackgroundImageUrl() {
    var randomPicture = Math.floor((Math.random() * 8) + 1);
    return backgroundPictureFileName + 2 + ".jpg";
}

var cookies = getCookies();

if(cookies["last_updated"] && cookies["last_updated"]<today)
{
    /* update variable */
    setCookie("last_updated", today, 1);

}

/* utility methods starts, adding utility methods to simplify setting and getting    cookies  */
function setCookie(name, value, daysToLive) {
    var cookie = name + "=" + encodeURIComponent(value);
    if (typeof daysToLive === "number")
        cookie += "; max-age=" + (daysToLive*60*60*24);
    document.cookie = cookie;
}

function getCookies() {
    var cookies = {}; // The object we will return
    var all = document.cookie; // Get all cookies in one big string
    if (all === "") // If the property is the empty string
        return cookies; // return an empty object
    var list = all.split("; "); // Split into individual name=value pairs
    for(var i = 0; i < list.length; i++) { // For each cookie
        var cookie = list[i];
        var p = cookie.indexOf("="); // Find the first = sign
        var name = cookie.substring(0,p); // Get cookie name
        var value = cookie.substring(p+1); // Get cookie value
        value = decodeURIComponent(value); // Decode the value
        cookies[name] = value; // Store name and value in object
    }
    return cookies;
}
/* utility methods ends */