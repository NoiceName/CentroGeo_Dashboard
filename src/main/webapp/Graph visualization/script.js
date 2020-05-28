var currentXML;
var XMLloaded = false;


function onload() {

  currentXML = getXML();
  console.log("page is loaded done");

}


 	google.charts.load('current', {'packages':['corechart']});
 	google.charts.load('current', {'packages':['bar']});
    // google.charts.setOnLoadCallback(drawPieChart);
    // google.charts.setOnLoadCallback(drawLineChart);




// Function getting called whenever the "Generate Graph" button is pressed --> called when sucess() is ready
function genGraph() {
  console.log("gen the graph XML");
 // console.log(currentXML);


  var simulation;
  // get the values of the different selects
  var chartType = document.getElementById('chartChoice').value;
  var laneChoice = document.getElementById('laneSelect').value;

  var cars = [];



  for (var i = 0; i < currentXML.length; i++) {
    var snapshot = currentXML[i];

    var path = "/snapshot/lane[@id=\""+ laneChoice + "\"]/vehicles";
    var nodes = snapshot.evaluate(path, snapshot, null, XPathResult.ANY_TYPE, null);

    var result = nodes.iterateNext();
    cars[i] = result.getAttribute("value").split("v").length -1;
  }
 
  console.log(cars);

  
  

  // determine what graph should be drawn
  if (chartType == "barC") {
    drawBarChart();

  }
  else if (chartType == "pieC") {
    drawPieChart();

  }
  else if (chartType == "lineC") {
    var dataArray = [[]];
    dataArray[0] = ["Time stamp", "#cars"];

    for (var i = 0; i < cars.length; i++) {
      dataArray[i+1] = [i, cars[i]];
    }

    drawLineChart(dataArray, "Number of cars on lane " + laneChoice);

  }
  else if (chartType == "otherC") {
    drawPieChart(getXMLarray(snapshot), "Vehicles and their speed");

  }

}






    function drawBarChart(dataArray, title) {

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
          },
          bars: 'vertical' // Required for Material Bar Charts.
        };

        var chart = new google.charts.Bar(document.getElementById('mainChart'));

        chart.draw(data, google.charts.Bar.convertOptions(options));
    }




      function drawPieChart(dataArray, title) {

        var data = google.visualization.arrayToDataTable(dataArray);

        
        var options = {
          title: title
        };

        var chart = new google.visualization.PieChart(document.getElementById("mainChart"));

        chart.draw(data, options);
      }




      function drawLineChart(dataArray, title) {
        var data = google.visualization.arrayToDataTable(dataArray);


        var options = {
          title: title,
          curveType: 'function',
          legend: { position: 'bottom' },
          crosshair: { trigger: 'both' },
          explorer: {axis: 'horizontal'},
          hAxis: {format:"#", minValue: 0, maxValue: 5, viewWindow: {min: 0}},
          vAxis: {format:"#", minValue: 0, maxValue: 5, viewWindow: {min: 0}},
          
        };

        var chart = new google.visualization.LineChart(document.getElementById("mainChart"));


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




 $(function () {     $('#chartGen').click(function(event) {

      //check if the XML file has alread been loaded
      if (XMLloaded) {
        console.log("No loading twice!");
        genGraph();
        return;
      } 
      //Statically set simulation id !!! that is sent to the server
      var jsonObject = {'simulation':1};
      var json = JSON.stringify(jsonObject);

    $.ajax({
      url: '/CentroGeo/Resources/metadata/states',
      data: json,
      //try with application/json later
      type: 'POST',
      dataType: 'json',
      contentType : 'application/json',
      success: function (resp) {success(resp)},
      error: function(jqXHR, textStatus, errorThrown) {
        alert('Cannot contact the server!');
      }
    });
    

  function success(resp) {
    //resp is the data array

        //avoid loading the data multiple times
        console.log("XML retrieved");
        XMLloaded = true;

        dataArray = [];
        var parser = new DOMParser();

        for (var i = 0; i < resp.length; i++) {
            xmlDoc = parser.parseFromString(resp[i].data, "text/xml");
            dataArray[i] = xmlDoc;
        }
        
        
        populateLaneSelect(dataArray[1]);

        currentXML = dataArray;
        // console.log("succes XML");
        // console.log(currentXML);
        genGraph();

       

    return xmlDoc;
  }
    
    }); 
    });

