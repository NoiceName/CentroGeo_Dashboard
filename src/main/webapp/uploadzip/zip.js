const zipFile = document.getElementById("zip-file");
const zipButton = document.getElementById("zip-button");
const customText = document.getElementById("custom-text");
const zipSubmit = document.getElementById("zip-submit");
const submitButton = document.getElementById("submit-button");
const uploadedzip = document.getElementById("uploadedzip");

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

submitButton.addEventListener("click", function() {
	zipSubmit.click();
});

zipSubmit.addEventListener("change", function() {
	
});





