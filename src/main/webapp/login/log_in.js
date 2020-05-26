
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
			url: '/CentroGeo/Resources/UserResource',
			data: formData,
			//try with application/json later
			type: 'POST',
			dataType: 'json',
			contentType : 'application/json',
			success:function (resp) {successLogin(resp)},
             error: function(jqXHR, textStatus, errorThrown) {
                alert('Cannot contact the server!');
            }
		});
		console.log("Request sent!");
		});

}); 

function successLogin(resp) {
	console.log(resp.result)
	if(resp.result=='true'){
		//Do something
	}
	else if (resp.result=='false') {
		//Empty the username and password fields + display a message.
		var username = $('#username');
		var password = $('#userpassword');
		username.val("");
		password.val("");
		$('#failedLogin').removeClass('hidden');	
	}
}

