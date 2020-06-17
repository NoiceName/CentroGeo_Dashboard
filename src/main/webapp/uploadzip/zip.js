/*Updates the text in the upload zip box.*/
$(function() {
	console.log('updated21')
	const zipFile = document.getElementById("zip-file");
	const zipButton = document.getElementById("zip-button");
	const customText = document.getElementById("custom-text");

	zipFile.addEventListener("change", function() {
		if(zipFile.value) {
			customText.innerHTML = zipFile.value.match(/[\/\\]([\w\d\s\.\-\(\)]+)$/)[1];
		} else {
			customText.innerHTML = "no file chosen";
		}
	});

/*Simulates clicking the actual button instead of the placeholder.*/	
zipButton.addEventListener("click", function() {
	zipFile.click();
});


/*Sends the zip-file to the server.*/
$("#zipform").submit(function (evt) {
    var formData = new FormData();
    console.log(zipFile.files[0]);
    // formData.append('zip', zipFile.files[0]);
    formData = zipFile.files[0];
    
    var percent = 25;
    $('#progressBar').attr('aria-valuenow', percent).css('width', percent + '%').text(percent + '%');
    $.ajax({
    	xhr : function() {
			var xhr = new window.XMLHttpRequest();

			xhr.upload.addEventListener('progress', function(e) {

				if (e.lengthComputable) {

					console.log('Bytes Loaded: ' + e.loaded);
					console.log('Total Size: ' + e.total);
					console.log('Percentage Uploaded: ' + (e.loaded / e.total))

					var percent = Math.round((e.loaded / e.total) * 100);

					$('#progressBar').attr('aria-valuenow', percent).css('width', percent + '%').text(percent + '%');

				}

			});

			return xhr;
		},
        url: '/CentroGeo/resources/simulations',
        type: 'POST',
        data: formData,
        async: true,
        cache: false,
        contentType: 'application/zip',
        dataType: 'application/zip',
        title: 'file',
        enctype: 'application/zip',
        processData: false,
        success: function (response) {
            alert("Successfully uploaded zip file");
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.responseText)
        }
        
    });
    return false;
});
});




