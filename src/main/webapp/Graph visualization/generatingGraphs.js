function getActiveChartType(){
	chartTypeElements = document.getElementsByClassName('chart-type');
	[].forEach.call(chartTypeElements, function(element){
		if (element.classList.contains('active')) {
			return element.getAttribute('value');
		}
	});
}
