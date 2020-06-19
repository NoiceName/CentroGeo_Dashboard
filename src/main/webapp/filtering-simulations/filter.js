//Tags take priority over names

function reorderSimulations(pattern) {
	var simCopy = loadedSimulations.slice();
	console.log(simCopy[0]);
	
}


//removed refreshSimulations
$('document').ready(function(){
	refreshSimulations();
	$('#simulation-filter').keyup(function() {
		let inputField = document.getElementById('simulation-filter').value;
		getSimulationDivById('1');
		reorderSimulations(inputField);
	});
});


