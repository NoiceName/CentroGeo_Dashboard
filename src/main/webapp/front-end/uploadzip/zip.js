const zipFile = document.getElementById("zip-file");
const zipButton = document.getElementById("zip-button");
const customText = document.getElementById("custom-text");
const file = document.getElementById("file");

zipButton.addEventListener("click", function() {
	zipFile.click();
	
});

zipFile.addEventListener("change", function() {
	if(zipFile.value) {
		customText.innerHTML = zipFile.value.match(/[\/\\]([\w\d\s\.\-\(\)]+)$/)[1];
		//file.innerHTML = zipFile.value.match(/[\/\\]([\w\d\s\.\-\(\)]+)$/)[1];
	} else {
		customText.innerHTML = "no file chosen";
	}
});

