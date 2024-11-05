

$(document).ready(function() {
    $.ajax({
        url: '/login',
        type:'GET'
    }).then(function(data) {
        console.log(data)
    });

});

function loginCheck(userName, password){
    alert("login check test")
}

// function refersh(){
//     $.ajax({
//         url: '/login',
//         type:'GET'
//     })
// }