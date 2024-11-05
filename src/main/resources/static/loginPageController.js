
function checkLogin(name){
    $.ajax({
        url: '/login',
        type:'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            username: $("#userName").val(),
            password: $("#password").val()
        }),
        success: function (data){
            alert(data);
            // Set input fields back to empty
            $("#userName").val("");
            $("#password").val("");
        },
        error: function (){
            console.log('Error ${error}');
            alert("Login Error!")
        }
    });
}