var currentXML;
var XMLloaded = false;

var vehIds;
var edgeIds;
var laneIds;
var simIds = [];


function onload() {

}


 	google.charts.load('current', {'packages':['corechart']});
 	google.charts.load('current', {'packages':['bar']});



// Function getting called whenever the "Generate Graph" button is pressed --> called when sucess() is ready
function genGraph() {
  console.log("Generating the graph");

  var simulation_id;

  //get selected simulation
  if (getSelectedSimulationID() == -1) {
    alert("Please select a Simulation");
    return
  } else {
    simulation_id = getSelectedSimulationID();
  }

  //check that user has given id options
  var userOptions;
  if (getSelectedIds().length >= 1) {
    userOptions = getSelectedIds();
  } else {
    userOptions = -1;
  }
  
  var chartType = "";
  chartType = getActiveChartType();

  var serverResponse = [];
  var dataArray = [[]];


  // determine what graph should be drawn

  //  GRAPH showing edge appearance frequency 
	if (chartType == "edgeFr") {

    //get the Data using RESTful services
      for (var j = 0; j < userOptions.length; j++) {
        $.get('/CentroGeo/resources/simulations/'+ simulation_id +'/charts/edge_appearance?edge_ids=' + userOptions[j], function(data) {
          serverResponse.push(data[0]);

          //all responses have been loaded, graph can be drawn.
          if (serverResponse.length == (userOptions.length)) {
            dataArray = getDataArray(serverResponse, "time");
            //one of the selected options failed to return data
            if (dataArray == -1) { return;}
            drawLineChart(dataArray, "Edge appearance frequency (Simulation " + simulation_id + ")", createChartSpace(), "time", "appearances");
        }
        })
      }
  } 

  //  GRAPH showing #cars per lane
	else if (chartType == "transVeh") {
    //select choosen lane
    var laneChoice;
    laneChoice = userOptions;

      //get the Data using RESTful services
      for (var j = 0; j < laneChoice.length; j++) {
        $.get('/CentroGeo/resources/simulations/'+ simulation_id +'/charts/transiting_vehicles?lanes_ids=' + laneChoice[j], function(data) {
          serverResponse.push(data[0]);

          //all responses have been loaded, graph can be drawn.
          if (serverResponse.length == (laneChoice.length)) {
            dataArray = getDataArray(serverResponse, "time");
            //one of the selected options failed to return data
            if (dataArray == -1) { return;}
            drawLineChart(dataArray, "Number of lane transiting vehicles (Simulation " + simulation_id + ")", createChartSpace(), "time", "cars");

          }
        })
      }
	}

  //  GRAPH showing info about chosen vehicle over time
  else if (chartType == "vehInfo") {

    $.get('/CentroGeo/resources/simulations/'+ simulation_id +'/charts/vehicle_information?vehicle_id=' + userOptions[0], function(data) {
          for (var i = 0; i < 3; i++) {
            serverResponse.push(data[i]);
          }
          //response has been loaded, graph can be drawn.
            dataArray = getDataArray(serverResponse, "time");
            //one of the selected options failed to return data
            if (dataArray == -1) { return;}
            drawLineChart(dataArray, "Stats for vehicle: " + userOptions[0] + " (Simulation " + simulation_id + ")", createChartSpace(), "time", "y");
        
        })
  }

  //  GRAPH showing the average route length over time
  else if (chartType == "avgRoute") {
    

  }

  //  GRAPH showing the average speed over time
  else if (chartType == "avgSpeed") {
    var simId = [];

    if (userOptions == -1) {
      simId[0] = getSelectedSimulationID();
    } else {
      simId = userOptions;
    }

    //get the Data using RESTful services
      for (var j = 0; j < simId.length; j++) {
        $.get('/CentroGeo/resources/simulations/'+ simId[j] +'/charts/average_vehicle_speed', function(data) {
          serverResponse.push(data);

          //all responses have been loaded, graph can be drawn.
          if (serverResponse.length == (simId.length)) {
            dataArray = getDataArray(serverResponse, "time");
            //one of the selected options failed to return data
            if (dataArray == -1) { return;}
            drawLineChart(dataArray, "Average speed of the vehicles", createChartSpace(), "time", "speed (m/s)");

          }
        })
      }
  }

  //  GRAPH showing the average speedFactor over time
  else if (chartType == "avgSpeedF") {
    var simId = [];
    if (userOptions == -1) {
      simId[0] = getSelectedSimulationID();
    } else {
      simId = userOptions;
    }

    //get the Data using RESTful services
      for (var j = 0; j < simId.length; j++) {
        $.get('/CentroGeo/resources/simulations/'+ simId[j] +'/charts/average_vehicle_speed_factor', function(data) {
          serverResponse.push(data);

          //all responses have been loaded, graph can be drawn.
          if (serverResponse.length == (simId.length)) {
            dataArray = getDataArray(serverResponse, "time");
            //one of the selected options failed to return data
            if (dataArray == -1) { return;}
            drawThinLineChart(dataArray, "Average speed factor of the vehicles", createChartSpace(), "time", "speed factor");

          }
        })
      }
  }

	//  GRAPH showing the total number of arrived cars over time
	else if (chartType == "cumulVeh") {
    var simId = [];

    if (userOptions == -1) {
      simId[0] = getSelectedSimulationID();
    } else {
      simId = userOptions;
    }

    //get the Data using RESTful services
      for (var j = 0; j < simId.length; j++) {
        $.get('/CentroGeo/resources/simulations/'+ simId[j] +'/charts/cumulative_number_of_arrived_vehicles', function(data) {
          serverResponse.push(data);

          //all responses have been loaded, graph can be drawn.
          if (serverResponse.length == (simId.length)) {
            dataArray = getDataArray(serverResponse, "time");
            //one of the selected options failed to return data
            if (dataArray == -1) { return;}
            drawLineChart(dataArray, "Cumulative number of arrived vehicles", createChartSpace(), "time", "count");

          }
        })
      }
  }

  //  GRAPH showing the number of transferred (teleported) vehicles over time
  else if (chartType == "transferVeh") {
    var simId = [];
    if (userOptions == -1) {
      simId[0] = getSelectedSimulationID();
    } else {
      simId = userOptions;
    }

    //get the Data using RESTful services
      for (var j = 0; j < simId.length; j++) {
        $.get('/CentroGeo/resources/simulations/'+ simId[j] +'/charts/number_of_transferred_vehicles', function(data) {
          serverResponse.push(data);

          //all responses have been loaded, graph can be drawn.
          if (serverResponse.length == (simId.length)) {
            dataArray = getDataArray(serverResponse, "time");
            //one of the selected options failed to return data
            if (dataArray == -1) { return;}
            drawLineChart(dataArray, "Number of transferred vehicles", createChartSpace(), "time", "count");

          }
        })
      }
  }

  //  GRAPH showing the number of running vehicles over time
  else if (chartType == "runningVeh") {
    var simId = [];

    if (userOptions == -1) {
      simId[0] = getSelectedSimulationID();
    } else {
      simId = userOptions;
    }

    //get the Data using RESTful services
      for (var j = 0; j < simId.length; j++) {
        $.get('/CentroGeo/resources/simulations/'+ simId[j] +'/charts/number_of_running_vehicles', function(data) {
          serverResponse.push(data);

          //all responses have been loaded, graph can be drawn.
          if (serverResponse.length == (simId.length)) {
            dataArray = getDataArray(serverResponse, "time");
            //one of the selected options failed to return data
            if (dataArray == -1) { return;}
            drawLineChart(dataArray, "Number of running vehicles", createChartSpace(), "time", "count");

          }
        })
      }
  }

}



function getDataArray(serverResponse, xTitle) {
  var dataArray = [[]];
  dataArray[0] = [xTitle];

            for (var k = 0; k < serverResponse.length; k++) {
              dataArray[0].push(serverResponse[k].id); 
              if (serverResponse[k].data.length == 0) {
                alert("The selected option: " + serverResponse[k].id + ", does not contain enough datapoints to be represented on a graph");
                return -1;
              }
            }

            for (var k = 0; k < serverResponse[0].data.length; k++) {
              dataArray[k + 1] = [serverResponse[0].data[k].x];

              for (var l = 0; l < serverResponse.length; l++) {
                // when comparing 2 simulations retrieved data length may vary
                if (l > serverResponse[l].data.length) {
                  dataArray[k + 1].push(0);
                } else {
                   dataArray[k + 1].push(serverResponse[l].data[k].y);
                }
              }
            }
  return dataArray;
}


function drawLineChart(dataArray, title, id, hTitle, vTitle) {
	var data = google.visualization.arrayToDataTable(dataArray);


	var options = {
		title: title,
		curveType: 'none',
		legend: { position: 'bottom' },
		crosshair: { trigger: 'both' },
		explorer: {axis: 'horizontal'},
		hAxis: {title:hTitle, format:"#", minValue: 0, maxValue: 5, viewWindow: {min: 0}},
		vAxis: {title:vTitle, format:"#", minValue: 0, maxValue: 5, viewWindow: {min: 0}},
    // hard coded height, maybe change change this in the generatingGraph.js
    height: 350,
	};

	var chart = new google.visualization.LineChart(document.getElementById(id));
  chart.draw(data, options);
}


function drawThinLineChart(dataArray, title, id, hTitle, vTitle) {
  var data = google.visualization.arrayToDataTable(dataArray);


  var options = {
    title: title,
    curveType: 'none',
    legend: { position: 'bottom' },
    crosshair: { trigger: 'both' },
    explorer: {axis: 'horizontal'},
    hAxis: {title:hTitle, format:"#", minValue: 0, viewWindow: {min: 0}},
    vAxis: {title:vTitle, format:"#", minValue: 1, maxValue: 1, viewWindow: {min: 0}},
    // hard coded height, maybe change change this in the generatingGraph.js
    height: 350,
  };

  var chart = new google.visualization.LineChart(document.getElementById(id));
  chart.draw(data, options);
}



//returns all lane id's of currently selected simulation
function getLanesId() {
  $.get('/CentroGeo/resources/simulations/'+ getSelectedSimulationID() +'/lane_ids', function(data) {
          laneIds = data;
        })
  return laneIds;
}

//returns all the veh id's as an array
function getVehId() {
	$.get('/CentroGeo/resources/simulations/'+ getSelectedSimulationID() +'/vehicle_ids', function(data) {
        vehIds = data;
      })
  return vehIds;
}

//returns all the edge id's
function getEdgeId() {
	$.get('/CentroGeo/resources/simulations/'+ getSelectedSimulationID() +'/edge_ids', function(data) {
        edgeIds = data;
      })
  return edgeIds;
}

//returns all simulation id's f the database
function getSimIds() {
	$.get('/CentroGeo/resources/simulations/simulation_ids', function(data) {
        for (var i = 0; i < data.length; i++) {
          simIds[i] = data[i].toString();
        }
      })
  return simIds;
}


//Generates a report when 'generate report' button is pressed.
 function generateReport() {
 	document.getElementById('reportDiv').innerHTML = "Report";
 	window.print();	
 	document.getElementById('reportDiv').innerHTML = "";
 }



$(function () {     $('#chartGen').click(function(event) {
  genGraph();

 });
 });

