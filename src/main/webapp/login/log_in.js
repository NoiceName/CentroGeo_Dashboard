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
			dataType: "application/json",
			contentType : "application/json",
			success: function ( resp ) {
				console.log(resp);
			},
            error: function(jqXHR, textStatus, errorThrown) {
                alert('Error: ' + textStatus + ' ' + errorThrown);
            }
		});
		console.log("Request sent!");
		});

}); 
