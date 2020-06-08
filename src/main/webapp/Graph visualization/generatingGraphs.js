//Maximum space per row
var maxSpace = 12;

//How much space does each column occupy
var spaceSize = 4;


function getActiveChartType(){
	chartTypeElements = document.getElementsByClassName('chart-type');
	[].forEach.call(chartTypeElements, function(element){
		if (element.classList.contains('active')) {
			return element.getAttribute('value');
		}
	});
}


function generateRandomID() {
	let r = Math.random().toString(36).substring(7);
	return r;
}

//Creates space for the chart in the dashboard and return the id of that space
function createChartSpace() {
	let chartRow = document.getElementById('charts');
	let newDiv = document.createElement('div');
	//Give an ID to the div
	id = generateRandomID();
	newDiv.id = id;
	let colWidth = 'col-'+spaceSize;
	newDiv.classList.add(colWidth);
	chartRow.appendChild(newDiv);
	return id;
}

