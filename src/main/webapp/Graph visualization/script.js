var currentXML;
var XMLloaded = false;

var vehIds;
var edgeIds;
var laneIds;


function onload() {
  console.log("page is loaded");
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
  if (getSelectedIds().length < 1) {
    return
  } else {
    userOptions = getSelectedIds();
  }
  
  var chartType = "";
  chartType = getActiveChartType();

  var serverResponse = [];
  var dataArray = [[]];


  // determine what graph should be drawn

  //    GRAPH showing info about chosen vehicle over time
	if (chartType == "vehInfo") {
    var timeStamps = [];
		//select choosen vehicle
    var vehChoice = getSelectedIds()[0];
    if (getSelectedIds().length < 1) {return}

    var routeLengths = [];
    var speeds = [];
    var speedFactors = [];

    for (var i = 0; i < currentXML.length; i++) {
      var snapshot = currentXML[i];

      // get route length, or if not exist: routeLenght = 0
      try {
        var path = "/snapshot/vehicle[@id=\""+ vehChoice + "\"]";
        var nodes = snapshot.evaluate(path, snapshot, null, XPathResult.ANY_TYPE, null);
        var result = nodes.iterateNext();
        var routeId = result.getAttribute("route");

        path = "/snapshot/route[@id=\""+ routeId + "\"]";
        nodes = snapshot.evaluate(path, snapshot, null, XPathResult.ANY_TYPE, null);
        result = nodes.iterateNext();
        routeLengths[i] = result.getAttribute("edges").split("e").length -1;

      } catch(err) {
        routeLengths[i] = 0
     }
     // get vehicle speed, or if not exist: speed = 0;
     try {
        var path = "/snapshot/vehicle[@id=\""+ vehChoice + "\"]";
        var nodes = snapshot.evaluate(path, snapshot, null, XPathResult.ANY_TYPE, null);
        var result = nodes.iterateNext();
        var speed = result.getAttribute("speed");
        speeds[i] = parseFloat(speed);

     } catch(err) {
        speeds[i] = 0;
     }
     // get vehicle speedFactor, or if not exist: speedFactor = 0;
     try {
        var path = "/snapshot/vehicle[@id=\""+ vehChoice + "\"]";
        var nodes = snapshot.evaluate(path, snapshot, null, XPathResult.ANY_TYPE, null);
        var result = nodes.iterateNext();
        var speedFactor = result.getAttribute("speedFactor");
        speedFactors[i] = parseFloat(speedFactor);

     } catch(err) {
        speedFactors[i] = 0;
     }



     // get timeStamps
        var timeStamp;
        var time;

        timeStamp = snapshot.getElementsByTagName('snapshot'); 
        time = timeStamp[0].getAttribute("time");
      
        timeStamps[i] = parseFloat(time);
    }

    var dataArray = [[]];
      dataArray[0] = ["Time stamp", "routeLength", "speed", "speedFactor"];

      for (var i = 0; i < routeLengths.length; i++) {
        dataArray[i+1] = [timeStamps[i], routeLengths[i], speeds[i], speedFactors[i]];
      }

      drawLineChart(dataArray, "Stats for vehicle: " + vehChoice, createChartSpace(), "time", "");
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

	//draw a useless graph
	else if (chartType == "otherC") {
    var xmlFile = currentXML[10];
    //Return array containing every vehicle + the speed of that vehicle
    var vArray =  xmlFile.getElementsByTagName('vehicle');
    var outArray = [[]];
    outArray[0] = ["Vehicle id", "Speed"];

    for (var i = 0; i < vArray.length; i++) {
      outArray[i+1] = [];
      outArray[i+1][0] = vArray[i].getAttribute("id");
      outArray[i+1][1] = parseInt(vArray[i].getAttribute("speed"));
    }
  		drawPieChart(outArray, "Vehicles and their speed", createChartSpace());
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


function drawPieChart(dataArray, title, id) {
	var data = google.visualization.arrayToDataTable(dataArray);


	var options = {
		title: title,
    height: 350,
	};

	var chart = new google.visualization.PieChart(document.getElementById(id));

	chart.draw(data, options);
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




$(function () {     $('#chartGen').click(function(event) {
  genGraph();

 });
 });

 $(function () {     $(document).ready(function() {
      //Statically set simulation id !!! that is sent to the server
	  var simulation_id = '1';

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
        console.log("XML retrieved");
        alert("Simulation loaded!");
        XMLloaded = true;

//        console.log(resp);
        

        dataArray = [];
        var parser = new DOMParser();

        for (var i = 0; i < resp.length; i++) {
            xmlDoc = parser.parseFromString(resp[i].data, "text/xml");
            dataArray[i] = xmlDoc;
        }
        
        currentXML = dataArray;

    return xmlDoc;
  }
    
    }); 
    });

//Generates a report when 'generate report' button is pressed.
 function generateReport() {
 	document.getElementById('reportDiv').innerHTML = "Report";
 	window.print();	
 	document.getElementById('reportDiv').innerHTML = "";
 }


