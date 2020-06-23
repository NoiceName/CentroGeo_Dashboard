/*Updates the text in the upload zip box.*/
$(function() {
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
	var menu = document.getElementById("zipmenu");

	var alignDIV = document.createElement("DIV");
	alignDIV.setAttribute("class", "d-flex justify-content-center");
	
	var spinner = document.createElement("DIV");
	spinner.setAttribute("class", "spinner-border");
	spinner.setAttribute("role", "status");
	alignDIV.appendChild(spinner);
		
	var span = document.createElement("SPAN");
	span.setAttribute("class", "sr-only");
	spinner.appendChild(span);
	
	menu.appendChild(alignDIV);

    var formData = new FormData();
    console.log(zipFile.files[0]);
    // formData.append('zip', zipFile.files[0]);
    formData = zipFile.files[0];
    if (formData != null) {
    	$.ajax({
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
    			alignDIV.remove();
    			alert("Successfully uploaded zip file");
    		},
    		error: function (jqXHR, textStatus, errorThrown) {
    			alignDIV.remove();
    			alert(jqXHR.responseText)
    		}
        
    	});
    }
    return false;
});
});




