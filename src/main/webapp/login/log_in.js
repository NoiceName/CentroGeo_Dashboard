
$(function () {
	$( '#myform' ).submit(function( event ) {
		event.preventDefault();
		
		var username = $( '#username' );
		var password = $( '#userpassword' );
		
		var formData = JSON.stringify({
			'username' : username.val(),
			'password' : password.val()
		});

		//Send the username and password to the server.
		//Wait for the server response execute successLogin on a successfull request
		$.ajax({
			url: '/CentroGeo/resources/user_resource/login',
			data: formData,
			//try with application/json later
			type: 'POST',
			dataType: 'json',
			contentType : 'application/json',
			success: function (resp) {successLogin(resp)},
             error: function(resp) {
                alert('Cannot contact the server!');
                failedLogin(resp);
            }
		});
		console.log("Request sent!");
		});

}); 

function successLogin(resp) {
	console.log(resp.token);
	sessionStorage.setItem("token", resp.token);

	window.location.href = "/CentroGeo/homepage.html";
}

function failedLogin(resp) {
	//Empty the username and password fields + display a message.
	var username = $('#username');
	var password = $('#userpassword');
	username.val("");
	password.val("");
	$('#failedLogin').removeClass('hidden');
}

