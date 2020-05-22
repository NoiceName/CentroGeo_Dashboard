


 	google.charts.load('current', {'packages':['corechart']});
 	google.charts.load('current', {'packages':['bar']});
    google.charts.setOnLoadCallback(drawPieChart);
    // google.charts.setOnLoadCallback(drawLineChart);





    function drawBarChart() {

    	var data = google.visualization.arrayToDataTable([
          ['Year', 'Sales', 'Expenses', 'Profit'],
          ['2014', 1000, 400, 200],
          ['2015', 1170, 460, 250],
          ['2016', 660, 1120, 300],
          ['2017', 1030, 540, 350]
        ]);

        var options = {
          chart: {
            title: 'Company Performance',
            subtitle: 'Sales, Expenses, and Profit: 2014-2017',
          },
          bars: 'horizontal' // Required for Material Bar Charts.
        };

        var chart = new google.charts.Bar(document.getElementById('mainChart'));

        chart.draw(data, google.charts.Bar.convertOptions(options));
    }




      function drawPieChart() {

        var data = google.visualization.arrayToDataTable([
          ['Task', 'Hours per Day'],
          ['Work',     11],
          ['Eat',      2],
          ['Commute',  2],
          ['Watch TV', 2],
          ['Sleep',    7]
        ]);

        var options = {
          title: 'My Daily Activities'
        };

        var chart = new google.visualization.PieChart(document.getElementById("mainChart"));

        chart.draw(data, options);
      }




      function drawLineChart() {
        var data = google.visualization.arrayToDataTable([
          ['Year', 'Sales', 'Expenses'],
          ['2004',  1000,      400],
          ['2005',  1170,      460],
          ['2006',  660,       1120],
          ['2007',  1030,      540]
        ]);

        var options = {
          title: 'Company Performance',
          curveType: 'function',
          legend: { position: 'bottom' }
        };

        var chart = new google.visualization.LineChart(document.getElementById("mainChart"));


        chart.draw(data, options);
      }




      function drawEmptyChart() {
      	
      }





      function getSelectValue() {

      	var selectedValue = document.getElementById("chartChoice").value;
      	drawChart(selectedValue);


      }


      function drawChart(chartType) {

      	if (chartType == "pieC") {drawPieChart();}
      	if (chartType == "lineC") {drawLineChart();}
      	if (chartType == "barC") { drawBarChart();}
      	if (chartType == "otherC") { drawEmptyChart();}

      }