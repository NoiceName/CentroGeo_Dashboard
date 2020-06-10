
$(function () {
	$("#lineChartSelect").click(function () {
		clearInputFields();
		$("#lineChartOptions").removeClass('d-none');
	});

	$("#uselessChartSelect").click(function () {
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

$(function() {
	var laneIdInput = $("#laneIdInput");

	laneIdInput.keyup(function() {
		var ids = getLanesId();
		var jsLaneIdInput = document.getElementById("laneIdInput");
		let input = jsLaneIdInput.value;
		let p = document.getElementById('foundIdsContainer');
		//If the input is longer than 3 characters
		if(input.length >= 3) {
			//Filter through the lane IDS in the simulation by the given input and append them as children to the foundIdsContainer
			let filtered = getFilteredIds(input, ids);
			filtered.forEach(function(item) {
				createBadgeAndAdd(item,p);
			});
		}
		else if (input.length <= 2) {

		}
	});

});
 
//Removes badges from display
//Not used
function removeBadges(container){
	var c = conainter.childNodes;	
}

//Id is a string of a lane;
//Creates an appropriate <a> element and adds to to the specified container - html element
function createBadgeAndAdd(id, container) {
	var newBadge = document.createElement("a");
	newBadge.classList.add('badge', 'badge-primary', 'badge-pill', 'foundId');
	newBadge.setAttribute('href','#');
	newBadge.innerText = id;
	newBadge.addEventListener('mousedown', function () {selectBadge(newBadge, container)});
	container.appendChild(newBadge);
}

//Transfer the id from Found IDs to Selected Ids container
function selectBadge(Badge, parentElem) {
	var transferContainer = document.getElementById('selectedIdsContainer');
	Badge.removeEventListener('mousedown', arguments.callee);
	transferContainer.appendChild(Badge);
}


//Returns an array of string ids of lanes that have been selected by the user.
function getSelectedIds(){
	var transferContainer = document.getElementById('selectedIdsContainer');
	let children = transferContainer.children;
	let result = [];
	for (var i = 0; i<children.length; i++){
		let child = children[i];
		if (child.classList.contains('card-title')){
			continue;
		} else {
			result.push(child.innerText);
		}
	}
	return result;
}


//inputString - String
//ids - Array of strings
//For each string in the ids check if inputString is is part of it in the case that it is 
//add it to the result.
function getFilteredIds(inputString, ids) {
		let filtered = ids.filter(function(str) {return str.includes(inputString)});
		return filtered;
}
