var currentXML;
var XMLloaded = false;

var vehIds;
var edgeIds;
var laneIds;


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

  //    GRAPH showing info about chosen vehicle over time
	if (chartType == "vehInfo") {

    $.get('/CentroGeo/resources/simulations/'+ simulation_id +'/charts/vehicle_information?vehicle_id=' + userOptions[0], function(data) {
          for (var i = 0; i < 3; i++) {
            serverResponse.push(data[i]);
          }
          //response has been loaded, graph can be drawn.
            dataArray = getDataArray(serverResponse, "time");
            drawLineChart(dataArray, "Stats for vehicle: " + userOptions[0] + " (Simulation " + simulation_id + ")", createChartSpace(), "time", "y");
        
        })
	}

  //    GRAPH showing edge appearance frequency 
	else if (chartType == "edgeFr") {

    //get the Data using RESTful services
      for (var j = 0; j < userOptions.length; j++) {
        $.get('/CentroGeo/resources/simulations/'+ simulation_id +'/charts/edge_appearance?edge_ids=' + userOptions[j], function(data) {
          serverResponse.push(data[0]);

          //all responses have been loaded, graph can be drawn.
          if (serverResponse.length == (userOptions.length)) {
            dataArray = getDataArray(serverResponse, "time");
            drawLineChart(dataArray, "Edge appearance frequency (Simulation " + simulation_id + ")", createChartSpace(), "time", "appearances");
        }
        })
      }
  } 

  //    GRAPH showing #cars per lane
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
            drawLineChart(dataArray, "Number of lane transiting vehicles (Simulation " + simulation_id + ")", createChartSpace(), "time", "cars");

          }
        })
      }
	}

	//   Graph showing the total number of arrived cars over time
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
            drawLineChart(dataArray, "Cumulative number of arrived vehicles", createChartSpace(), "time", "count");

          }
        })
      }
  }

  //   GRAPH showing the number of running vehicles ver time
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
            }

            for (var k = 0; k < serverResponse[0].data.length; k++) {
              // dataArray[k + 1] = [];
              dataArray[k + 1] = [serverResponse[0].data[k].x];
              for (var l = 0; l < serverResponse.length; l++) {
                dataArray[k + 1].push(serverResponse[l].data[k].y);
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



//should return all lane id's f currently selected simulation
function getLanesId() {

  var snapshot = currentXML[1];
  laneIds = [];

  var lanes = snapshot.getElementsByTagName('lane');

  for (var i = 0; i < lanes.length; i++) {
    laneIds[i] = [];
    laneIds[i] = lanes[i].getAttribute("id");
  }

  return laneIds;
}

//should return all the veh id's as an array
function getVehId() {
  vehIds = ["v10", "v25", "v40", "v61", "v135", "v254", "v316", "v479", "v524", "v698", "v702", "v866", "v978"];
  return vehIds;
}

//should return all the edge id's
function getEdgeId() {
  edgeIds = ["e2", "e14", "e15", "e25", "e26", "e27", "e33", "e38", "e31", "e43"];
  return edgeIds;
}

//should return all simulation id's f the database
function getSimIds() {
  return [getSelectedSimulationID(), "1", "2", "5", "6"];
}




$(function () {     $('#chartGen').click(function(event) {
  genGraph();

 });
 });

 $(function () {     $(document).ready(function() {
      //Statically set simulation id !!! that is sent to the server
	  var simulation_id = getSelectedSimulationID();

    $.ajax({
      url: '/CentroGeo/resources/simulations/'+simulation_id+'/snapshots',
      //try with application/json later
      type: 'GET',
      success: function (resp) {success(resp)},
      error: function(jqXHR, textStatus, errorThrown) {
        alert('Cannot contact the server!');
      }
    });
    

  function success(resp) {
    //resp is the data array

        //avoid loading the data multiple times
        XMLloaded = true;
        
        dataArray = [];
        var parser = new DOMParser();

        for (var i = 0; i < resp.length; i++) {
            xmlDoc = parser.parseFromString(resp[i].data, "text/xml");
            dataArray[i] = xmlDoc;
        }
        
        currentXML = dataArray;
        return 
  }
    
    }); 
    });

//Generates a report when 'generate report' button is pressed.
 function generateReport() {
 	document.getElementById('reportDiv').innerHTML = "Report";
 	window.print();	
 	document.getElementById('reportDiv').innerHTML = "";
 }


