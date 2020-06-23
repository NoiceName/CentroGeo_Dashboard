
//Removes a simulations from the list if it does not contain the specified pattern in the tags or the name
function filter(pattern){
	clearSimulations();
	let simulationCopy = loadedSimulations.slice();
	do {
		removed = false;
		for (let i = 0; i != simulationCopy.length; i++) {
			let simObj = simulationCopy[i];
			let tags = simObj.tags.split(';');
			console.log(tags);
			for(let z = 0; z!=tags.length; z++){
			    if (tags[0]==null){
			    	break;
				}
				let tag = tags[z];
				tag = tag.trim();
				console.log(tag);
				tags[z] = tag;
			}
			if (!startsWith(pattern, simObj.name) && !checkArrayStartsWith(pattern, tags)) {
				simulationCopy.splice(i, 1);
				removed = true;
				break;
			}
		}
	} while(removed);
	loadSimulations(simulationCopy);
}

//Check if any items in the array begin with the given pattern
function checkArrayStartsWith(pattern, arr){
	for(let i = 0; i!=arr.length; i++){
		if(startsWith(pattern, arr[i]))	{
			return true;
		}
	}
	return false;
}

//removed refreshSimulations
$('document').ready(function(){
    refreshSimulations();
	$('#simulation-filter').keyup(function() {
	    //input type by the user
		let inputValue = document.getElementById('simulation-filter').value;
		filter(inputValue);
	});
});

//Removes the simulation names from all displays
function clearSimulationNameDisplays(){
    let displays = document.getElementsByClassName('selectedSimulationDisplay');
    for(let i = 0; i!=displays.length; i++){
    	displays[i].innerText = '';
	}
}

//Return true if a string begins with a pattern
//Return false otherwise
function startsWith(pattern, string){
	let pat = pattern.split('');
	let str = string.split('');
	for(let i = 0; i!=pat.length; i++){
	    if(pat[i]!==str[i]){
	        return false;
		}
	}
	return true;
}



