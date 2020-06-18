var selectedSimulation = null;

$(function () {
	$("#lineChartSelect").click(function () {
		clearInputFields();
		$("#lineChartOptions").removeClass('d-none');
		clearSelectedAndFound();
	});

	$("#vehicleChartSelect").click(function () {
		clearInputFields();
		$("#vehicleChartOptions").removeClass('d-none');
		clearSelectedAndFound();
	});

	$("#uselessChartSelect").click(function () {
		clearInputFields();
		$("#pieChartOptions").removeClass('d-none');
		clearSelectedAndFound();
	});

	$("#edgeFrChartSelect").click(function () {
		clearInputFields();
		$("#edgeFrOptions").removeClass('d-none');
		clearSelectedAndFound();
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


//select laneId
$(function() {
	var laneIdInput = $("#laneIdInput");

	laneIdInput.keyup(function() {
		var ids = getLanesId();
		var jsLaneIdInput = document.getElementById("laneIdInput");
		let input = jsLaneIdInput.value;
		let p = document.getElementById('foundIdsContainer');
		//clear the found input after each keystroke
		let foundContainer = document.getElementById('foundIdsContainer');
		removeBadges(foundContainer);
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


//select vehicule Id
$(function() {
	var laneIdInput = $("#vehIdInput");

	laneIdInput.keyup(function() {
		var ids = getVehId();
		var jsLaneIdInput = document.getElementById("vehIdInput");
		let input = jsLaneIdInput.value;
		let p = document.getElementById('foundIdsContainer');
		//clear the found input after each keystroke
		let foundContainer = document.getElementById('foundIdsContainer');
		removeBadges(foundContainer);
		//If the input is longer than 3 characters
		if(input.length >= 2) {
			//Filter through the lane IDS in the simulation by the given input and append them as children to the foundIdsContainer
			let filtered = getFilteredIds(input, ids);
			filtered.forEach(function(item) {
				createBadgeAndAdd(item,p);
			});
		}
		else if (input.length <= 1) {

		}
	}); 
});

//select edge Id
$(function() {
	var laneIdInput = $("#edgeIdInput");

	laneIdInput.keyup(function() {
		var ids = getEdgeId();
		var jsLaneIdInput = document.getElementById("edgeIdInput");
		let input = jsLaneIdInput.value;
		let p = document.getElementById('foundIdsContainer');
		//clear the found input after each keystroke
		let foundContainer = document.getElementById('foundIdsContainer');
		removeBadges(foundContainer);
		//If the input is longer than 2 characters
		if(input.length >= 2) {
			//Filter through the lane IDS in the simulation by the given input and append them as children to the foundIdsContainer
			let filtered = getFilteredIds(input, ids);
			filtered.forEach(function(item) {
				createBadgeAndAdd(item,p);
			});
		}
		else if (input.length <= 1) {

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
})

//Pulls the simulations from the API
function refreshSimulations(){
	$.get("/CentroGeo/resources/simulations", function (resp) {loadSimulations(resp);});
}

//Given an array of simulation objects updates loads them into SelectSimulationMenu modal
function loadSimulations(simulations){
	var container = document.getElementById('simulation-container');
	for(var i = 0; i!=simulations.length; i++){
		let simulation = createSimulationDiv(simulations[i]);
		simulation.addEventListener('click', function() {selectSimulation.bind(this)()});
		container.appendChild(simulation);
	}
}

//For testing
$(function(){ 
	refreshSimulations(); 
});

function getSelectedSimulation(){}

//Creating simulation div
function createSimulationDiv(simulation){
	//col-4 to contain the badges of an id and tags
	let tagsAndId = createCol4Div();
	//col-4 div to contain the date of a simulation + inner div
	let dateContainer = createCol4Div();
	let dateDiv = document.createElement('div');
	//col-4 div to contain the description of a simulation + inner div
	let descriptionContainer = createCol4Div();
	let descriptionDiv = document.createElement('div');
	descriptionDiv.classList.add('simulation-description');
	//col-4 div to contain the name of the simulation
	let nameDiv = document.createElement('div');
	nameDiv.classList.add('card-title');
	nameDiv.classList.add('w-50');
	nameDiv.classList.add('simulation-name');
	nameDiv.innerText = simulation.name;
	tagsArr = simulation.tags.split(";");
	populateTagsAndIds(tagsArr, simulation.simulationId, tagsAndId);
	dateDiv.innerText = simulation.date;
	dateDiv.classList.add('simulation-date');
	dateContainer.appendChild(dateDiv);
	descriptionDiv.innerText = simulation.description;
	descriptionContainer.appendChild(descriptionDiv);
	dateContainer.appendChild(dateDiv);
	//Putting the simulation together
	//Setting up the most outer element
	let div1 = document.createElement('div');
	div1.classList.add('card');
	div1.classList.add('border-dark');
	div1.classList.add('w-100');
	div1.classList.add('simulation');
	//Next Inner element
	let div2 = document.createElement('div');
	div2.classList.add('card-body');
	div1.appendChild(div2);
	//Add name
	div2.appendChild(nameDiv);
	row = document.createElement('div');
	row.classList.add('row');
	div2.appendChild(row);
	//Add description
	row.appendChild(descriptionContainer);
	//Add date
	row.appendChild(dateContainer)
	//Add tags
	row.appendChild(tagsAndId);
	return div1;
}

//Creates  a div with a column width of 4
function createCol4Div() {
	var div = document.createElement('div');
	div.classList.add('col-4');
	return div;
}

//Given an array of tags, and ID and a DIV populates that div with badges of tags and ids given
function populateTagsAndIds(tags, id, divContainer){
	for( let i = 0; i!=tags.length; i++){
		badge = createSimulationBadge('tag', tags[i]);
		divContainer.appendChild(badge);
	}
	idBadge = createSimulationBadge('id', id);
	divContainer.appendChild(idBadge);
	return divContainer;
}

//Call-back for selecting a simulation
function selectSimulation(){
	if(selectedSimulation!=null){
		deselectSimulation(selectedSimulation);
	}
	selectedSimulation = this;
	this.classList.add('bg-dark');
	this.classList.add('text-white');
	this.classList.add('selected');
	getSelectedSimulationID();
}

//Deselect a particular simulation
function deselectSimulation(simulation){
	simulation.classList.remove('bg-dark');
	simulation.classList.remove('text-white');
	simulation.classList.remove('selected');
	selectedSimulation = null;
}

//Returns the simulation that has been selected by the user.
function getSelectedSimulationID(){
	let id = selectedSimulation.getElementsByClassName('simulation-id')[0].innerText;
	return id;
}

//Creates a div of a badge that is needed for the id and the tag of a simulation
function createSimulationBadge(type, data) {
	let span = document.createElement('span');
	if (type=='tag'){
		span.classList.add('badge-info');
		span.classList.add('simulation-tag');
	} else if (type=="id"){
		span.classList.add('badge-dark');
		span.classList.add('simulation-id');
	}
	span.classList.add('badge');
	span.classList.add('badge-pill');
	span.innerText = data;
	return span;
}