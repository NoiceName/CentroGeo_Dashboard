const zipFile = document.getElementById("zip-file");
const zipButton = document.getElementById("zip-button");
const customText = document.getElementById("custom-text");

zipButton.addEventListener("click", function() {
	zipFile.click();
});

zipFile.addEventListener("change", function() {
	if(zipFile.value) {
		customText.innerHTML = zipFile.value.match(/[\/\\]([\w\d\s\.\-\(\)]+)$/)[1];
	} else {
		customText.innerHTML = "no file chosen";
	}
});

$("#zipform").submit(function (evt) {
    var formData = new FormData();
    console.log(zipFile.files[0]);
    // formData.append('zip', zipFile.files[0]);
    formData = zipFile.files[0];
    var token = sessionStorage.getItem("token");

    $.ajax({
        url: '/CentroGeo/resources/simulations',
        type: 'POST',
        data: formData,
        async: false,
        cache: false,
        contentType: 'application/zip',
        dataType: 'application/zip',
        headers: {"Authorization": `Bearer ${token}`},
        title: 'file',
        enctype: 'application/zip',
        processData: false,
        success: function (response) {
            alert(response);
        }
    });
    return false;
});





