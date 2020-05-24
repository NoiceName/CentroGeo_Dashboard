$(function () {
});


$(function () {
	$( '#myform' ).submit(function( event ) {
		event.preventDefault();
		console.log("submitted a form")


		var form = $( this );

		$.ajax({
			type: 'POST',
			url: 'CentroGeo/Resources/UserResource',
			data: form.serialize(),
			dataType: 'json',
			success: function ( resp ) {
				console.log(resp);
			}
		});
	});
}); 
