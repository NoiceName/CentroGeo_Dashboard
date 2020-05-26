$(function () {
	$( '#myform' ).submit(function( event ) {
		event.preventDefault();
		
		var username = $( '#username' );
		var password = $( '#userpassword' );
		
		var formData = JSON.stringify({
			'username' : username.val(),
			'password' : password.val()
		});
		
		console.log(formData);

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
	console.log(resp);
	var response = JSON.parse(resp);
}

