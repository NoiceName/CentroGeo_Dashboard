var currentXML;
var XMLloaded = false;


function onload() {
  console.log("page is loaded");
}


 	google.charts.load('current', {'packages':['corechart']});
 	google.charts.load('current', {'packages':['bar']});



// Function getting called whenever the "Generate Graph" button is pressed --> called when sucess() is ready
function genGraph() {
  console.log("Generating the graph");

  var chartType = "";
  chartType = getActiveChartType();


  // determine what graph should be drawn


  //    GRAPH showing info about chosen vehicle over time
	if (chartType == "vehInfo") {
    var timeStamps = [];
		//select choosen vehicle
    var vehChoice = getSelectedIds()[0];
    vehChoice = "v40";

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
    // select chosen edge
    var edgeChoice;
    edgeChoice = "e43"

    var appearance = [];
    var timeStamps = [];

    //iterate over each snapshot
    for (var i = 0; i < currentXML.length; i++) {
      snapshot = currentXML[i];
      //get timeStamp of current snapshot
      var timeStamp;
      var time;

      timeStamp = snapshot.getElementsByTagName('snapshot'); 
      time = timeStamp[0].getAttribute("time");
    
      timeStamps[i] = parseFloat(time);
      
      var count = 0;

      var path = "//route";
      var nodes = snapshot.evaluate(path, snapshot, null, XPathResult.ANY_TYPE, null);

      //iterate over each route
      while(node = nodes.iterateNext()) {
        //split each route into seperate edges
        var result = node.getAttribute("edges").split(" ");

        //iterae over each edge
        for (var j = 0; j < result.length; j++) {
          if(result[j] == edgeChoice) { count++; }
        }
      }
      appearance[i] = count;
    }

    var dataArray = [[]];
    dataArray[0] = ["Time stamp", ("edge " + edgeChoice)];

    for (var i = 0; i < timeStamps.length; i++) {
      dataArray[i+1] = [timeStamps[i], appearance[i]];
    }

    drawLineChart(dataArray, "Edge appearance frequency", createChartSpace(), "time", "appearances");


	}


  //    GRAPH showing #cars per lane
	else if (chartType == "transVeh") {
    //select choosen lane
    var laneChoice;
    laneChoice = getSelectedIds();

		var cars = [[]];
    var timeStamps = [];

    //itterate over each snapshot
    for (var i = 0; i < currentXML.length; i++) {
      var snapshot = currentXML[i];
      cars[i] = [];

      // itterate over each chosen lane
      for (var j = 0; j < laneChoice.length; j++) {

        var path = "/snapshot/lane[@id=\""+ laneChoice[j] + "\"]/vehicles";
        var nodes = snapshot.evaluate(path, snapshot, null, XPathResult.ANY_TYPE, null);
      
        var result = nodes.iterateNext();
        cars[i][j] = result.getAttribute("value").split("v").length -1;

      }


      var timeStamp;
      var time;

      timeStamp = snapshot.getElementsByTagName('snapshot'); 
      time = timeStamp[0].getAttribute("time");
    
      timeStamps[i] = parseFloat(time);
    }
    


		var dataArray = [[]];
    dataArray[0] = ["Time stamps"];
    for (var i = 0; i < laneChoice.length; i++) {
      dataArray[0].push("lane " + laneChoice[i]); 
    }

		for (var i = 0; i < timeStamps.length; i++) {
      dataArray[i+1] = [timeStamps[i]];
      for (var j = 0; j < laneChoice.length; j++) {
        dataArray[i+1].push(cars[i][j]);
      }
		}
    

		drawLineChart(dataArray, "Number of lane transiting vehicles", createChartSpace(), "time", "cars");

	}

	//draw a useless graph
	else if (chartType == "otherC") {
		drawPieChart(getXMLarray(currentXML[10]), "Vehicles and their speed", createChartSpace());
	}

}






function drawBarChart(dataArray, title, id) {

	var data = google.visualization.arrayToDataTable([
		['Year', 'Sales', 'Expenses', 'Profit'],
		['2014', 1000, 400, 200],
		['2015', 1170, 460, 250],
		['2016', 660, 1120, 300],
		['2017', 1030, 540, 350]
	]);

	var options = {
		chart: {
			title: 'Mockup graph',
			subtitle: 'Sales, Expenses, and Profit: 2014-2017',
      height: 350,
		},
		bars: 'vertical' // Required for Material Bar Charts.
	};

	var chart = new google.charts.Bar(document.getElementById(id));

	chart.draw(data, google.charts.Bar.convertOptions(options));

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





function getXMLarray(xmlFile) {

	//Return array containing every vehicle + the speed of that vehicle
	var vArray =  xmlFile.getElementsByTagName('vehicle');
	var outArray = [[]];
	outArray[0] = ["Vehicle id", "Speed"];

	for (var i = 0; i < vArray.length; i++) {
		outArray[i+1] = [];
		outArray[i+1][0] = vArray[i].getAttribute("id");
		outArray[i+1][1] = parseInt(vArray[i].getAttribute("speed"));
	}


	// console.log(outArray.slice(0, 6));
	return outArray;
}





//populate the laneSelect with options
function populateLaneSelect(xmlfile2) {

	var xmlFile = xmlfile2;
	var options = xmlFile.getElementsByTagName('lane'); 
	var vLanes = [];

	for (var i = 0; i < options.length; i++) {
		vLanes[i] = [];
		vLanes[i] = options[i].getAttribute("id");
	}

	selectEl = document.getElementById("laneSelect"); 

	for (var i = 1; i < options.length; i++) {
		selectEl.options.add(new Option(vLanes[i], vLanes[i]));
	}

	console.log("lanes populated");
}




function getLanesId() {

  var snapshot = currentXML[1];
  var lanesID = [];

  var lanes = snapshot.getElementsByTagName('lane');

  for (var i = 0; i < lanes.length; i++) {
    lanesID[i] = [];
    lanesID[i] = lanes[i].getAttribute("id");
  }

  return lanesID;

}




 $(function () {     $('#chartGen').click(function(event) {

      //check if the XML file has alread been loaded
      if (XMLloaded) {
        genGraph();
        return;
      } 
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
        
        
//        populateLaneSelect(dataArray[1]);

        currentXML = dataArray;

    return xmlDoc;
  }
    
    }); 
    });

