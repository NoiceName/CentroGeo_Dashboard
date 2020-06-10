
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

$(function() {
	var ids = ['id1', 'id2', 'id3'];
	var laneIdInput = $("#laneIdInput");
	console.log('hello');


	laneIdInput.keyup(function() {
		var jsLaneIdInput = document.getElementById("laneIdInput");
		let input = jsLaneIdInput.value;
		let p = document.getElementById('foundIdsContainer');
		if(input.length >= 2) {
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
function removeBadges(container){
	var c = conainter.childNodes;	
	console.log(c);
}

//Id is a string of a lane;
function createBadgeAndAdd(id, container) {
	var newBadge = document.createElement("a");
	newBadge.classList.add('badge', 'badge-primary', 'badge-pill', 'foundId');
	newBadge.setAttribute('href','#');
	newBadge.innerText = id;
	newBadge.addEventListener('mousedown', function () {selectBadge(newBadge, container)});
	console.log(newBadge);
	container.appendChild(newBadge);
}

function selectBadge(Badge, parentElem) {
	console.log('pressed!');
	var transferContainer = document.getElementById('selectedIdsContainer');
	Badge.removeEventListener('mousedown', arguments.callee);
	transferContainer.appendChild(Badge);
}


function getSelectedIds(){
	var transferContainer = document.getElementById('selectedIdsContainer');
	let children = transferContainer.children;
	console.log(children);
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

function getFilteredIds(inputString, ids) {
		let filtered = ids.filter(function(str) {return str.includes(inputString)});
		return filtered;
}
