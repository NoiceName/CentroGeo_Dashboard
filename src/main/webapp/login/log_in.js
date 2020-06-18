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
			success:function (resp) {successLogin(resp)},
             error: function(jqXHR, textStatus, errorThrown) {
                alert('Cannot contact the server!');
            }
		});
		console.log("Request sent!");
		});

}); 

function successLogin(resp) {
	if(resp.result=='true'){
		console.log(resp);
		window.location.href = "/CentroGeo/homepage.html";
	}
	else if (resp.result=='false') {
		//Empty the username and password fields + display a message.
		var username = $('#username');
		var password = $('#userpassword');
		username.val("");
		password.val("");
	}
}

var current = null;
document.querySelector('#username').addEventListener('focus', function(e) {
  if (current) current.pause();
  current = anime({
    targets: 'path',
    strokeDashoffset: {
      value: 0,
      duration: 700,
      easing: 'easeOutQuart'
    },
    strokeDasharray: {
      value: '240 1386',
      duration: 700,
      easing: 'easeOutQuart'
    }
  });
});
document.querySelector('#userpassword').addEventListener('focus', function(e) {
  if (current) current.pause();
  current = anime({
    targets: 'path',
    strokeDashoffset: {
      value: -336,
      duration: 700,
      easing: 'easeOutQuart'
    },
    strokeDasharray: {
      value: '240 1386',
      duration: 700,
      easing: 'easeOutQuart'
    }
  });
});
document.querySelector('#login-button').addEventListener('focus', function(e) {
  if (current) current.pause();
  current = anime({
    targets: 'path',
    strokeDashoffset: {
      value: -730,
      duration: 700,
      easing: 'easeOutQuart'
    },
    strokeDasharray: {
      value: '530 1386',
      duration: 700,
      easing: 'easeOutQuart'
    }
  });
});

