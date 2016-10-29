$(document).ready(function () {
    var randomBackground = getRandomBackgroundForLoginPage();
    var backgroundClass = "luncheon-login-" + randomBackground;
    $('body').addClass(backgroundClass);
    $('#login-form').addClass(backgroundClass);
});
