$(document).ready(function () {
    var randomBackground = getRandomBackgroundForLoginPage();
    var backgroundClass = "luncheon-login-" + randomBackground;

    const $body = $('body');
    $body.removeClass();
    $body.addClass(backgroundClass);

    const $login = $('#login-form');
    $login.removeClass();
    $login.addClass(backgroundClass);
    $login.addClass("login-form");
});
