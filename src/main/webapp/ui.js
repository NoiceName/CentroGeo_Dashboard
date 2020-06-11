
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
 
//Removes all badges from a specified container
function removeBadges(container){
	var c = container.children;	
	var removed = false;
	do {
		for(var i = 0; i<c.length; i++){
			let child = c[i];
			if(child.classList.contains('foundId')){
				container.removeChild(child);
				removed = true;
				break;
			} else {removed = false}
		} 
		c = container.children;
	} while(removed);
}

//Id is a string of a lane;
//Creates an appropriate <a> element and adds to to the specified container - html element
function createBadgeAndAdd(id, container) {
	var newBadge = document.createElement("a");
	newBadge.classList.add('badge', 'badge-primary', 'badge-pill', 'foundId');
	newBadge.setAttribute('href','#');
	newBadge.innerText = id;
	newBadge.addEventListener('mousedown', function () {selectBadge(newBadge, container);});
	container.appendChild(newBadge);
}

//Transfer the id from Found IDs to Selected Ids container
function selectBadge(Badge, parentElem) {
	var transferContainer = document.getElementById('selectedIdsContainer');
	var cloneBadge = Badge.cloneNode(true);
	if (parentElem.contains(Badge)){
		parentElem.removeChild(Badge);
	}
	cloneBadge.addEventListener('mousedown', function() {makeSelectedBadge.bind(this)();})
	transferContainer.appendChild(cloneBadge);
}

function makeSelectedBadge() {
	let foundIds = document.getElementById('foundIdsContainer');
	let par = this.parentNode;
	par.removeChild(this);
	let clone = this.cloneNode(true);
	foundIds.appendChild(clone);
	clone.addEventListener('mousedown', function () {selectBadge(clone, foundIds);});
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

//Removes all ids from both containers
function clearSelectedAndFound() {
	let foundContainer = document.getElementById('foundIdsContainer');
	let selectedContainer = document.getElementById('selectedIdsContainer');
	removeBadges(foundContainer);
	removeBadges(selectedContainer);
}


//Behaviour when the user clicks away from the modal
$(function() {
	$('#createChartMenu').on('hidden.bs.modal', function(e) {
		let input = document.getElementById('laneIdInput');
		input.value = '';
		clearSelectedAndFound();
	});
});
