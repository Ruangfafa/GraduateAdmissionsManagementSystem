function checkLogin() {
    $.ajax({
        url: '/login',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            username: $("#userName").val(),
            password: $("#password").val()
        }),
        success: function (data) {
            if (data) {
                window.location.href = data;
            } else
                alert("Invalid username or password!");

            $("#userName").val("");
            $("#password").val("");
        },
        error: function () {
            console.log('Error ${error}');
            alert("Login Error!");
        }
    });
}
