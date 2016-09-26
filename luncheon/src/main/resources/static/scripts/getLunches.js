/**
 * Created by Batky on 17. 9. 2016.
 */
var urlLunches = "http://localhost:8080/lunches/date/";
var actualDate = new Date();
urlLunches = urlLunches +
    (actualDate.getFullYear()) +
    ('0' + (actualDate.getMonth() + 1)).slice(-2) +
    ('0' + (actualDate.getDate())).slice(-2);

console.log(urlLunches);

$.getJSON(urlLunches , function(json ) {
    var index;
    for (index = 0; index < json.length; index++) {
        // var news = document.getElementsByName("lunches");
        var text = document.createElement("p");
        var date = document.createElement("p");
        var textText = document.createTextNode(json[index].description);
        var textText2 = document.createTextNode(json[index].date);
        date.appendChild(textText2);
        text.appendChild(textText);
        var tabledate = document.getElementById("day1h")
        var tdate = tabledate.createElement("td");
        tdate.appendChild(textText2);
        document.getElementById("lunches").appendChild(date).appendChild(text);
    }
});
