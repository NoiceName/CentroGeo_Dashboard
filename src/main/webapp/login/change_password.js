
$(function () {
    $( '#changeForm' ).submit(function( event ) {
        event.preventDefault();

        console.log("wtf");

        var username = $( '#username' );
        var oldPassword = $( '#oldPassword' );
        var newPassword = $( '#newPassword' );

        var formData = JSON.stringify({
            'username' : username.val(),
            'oldPassword' : oldPassword.val(),
            'newPassword' : newPassword.val()
        });

        //Send the username and password to the server.
        //Wait for the server response execute successLogin on a successful request
        $.ajax({
            url: '/CentroGeo/resources/user_resource/change_password',
            data: formData,
            //try with application/json later
            type: 'POST',
            dataType: 'json',
            contentType : 'application/json',
            success:function (resp) {successChange(resp)},
            error: function(jqXHR, textStatus, errorThrown) {
                alert('Cannot contact the server!');
            }
        });
        console.log("Request sent!");
    });

});

function successChange(resp) {
    if(resp.result=='true'){
        console.log(resp);
        window.location.href = "/CentroGeo/homepage.html";
    }
    else if (resp.result=='false') {
        //Empty the username and password fields + display a message.
        var username = $('#username');
        var oldPassword = $('#oldPassword');
        var newPassword = $('#newPassword');

        username.val("");
        oldPassword.val("");
        newPassword.val("");
        $('#failedChange').removeClass('hidden');
    }
}

