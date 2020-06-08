$(function () {
	$("#lineChartSelect").click(function () {
		clearInputFields();
		$("#lineChartOptions").removeClass('d-none');
	});

	$("#pieChartSelect").click(function () {
		clearInputFields();
		$("#pieChartOptions").removeClass('d-none');
	});
});

//Hides all other option menus
function clearInputFields(){
	var elements = document.getElementsByClassName('chart-options');
	for(var i=0; i<elements.length; i++){
		var elem = elements[i];
		if(!elem.classList.contains('d-none')){
			elem.classList.add('d-none');
		}
	}
}
