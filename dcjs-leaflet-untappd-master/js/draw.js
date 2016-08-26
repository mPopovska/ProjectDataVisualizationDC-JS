	var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", 'http://localhost:8080/rest/getWorkLogs', false ); // false for synchronous request
    xmlHttp.send( null );
    var workLogs = xmlHttp.responseText;

/* Parse JSON file, create charts, draw markers on map */    
var workData = JSON.parse(workLogs);
console.log(workLogs);

  var fullDateFormat = d3.time.format('%d %m %Y %X');
  var yearFormat = d3.time.format('%Y');
  var monthFormat = d3.time.format('%m');
  var dayOfWeekFormat = d3.time.format('%w');

  // normalize/parse data so dc can correctly sort & bin them
  // I like to think of each "d" as a row in a spreadsheet
  _.each(workData, function(d) {

    d.dt = fullDateFormat.parse(d.datum);  
    d.year = +yearFormat(d.dt);
    d.month = monthFormat(d.dt);
    d.day = dayOfWeekFormat(d.dt);

  });

  // set crossfilter
  var ndx = crossfilter(workData);

  // create dimensions (x-axis values)
  var yearDim  = ndx.dimension(function(d) {return d.year;}),
      // dc.pluck: short-hand for same kind of anon. function we used for yearDim
      monthDim  = ndx.dimension(function (d) {return d.month;}),
      dayOfWeekDim = ndx.dimension(function (d) {
        var day = d.dt.getDay();
        var name = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
        return name[day];
      }),
      holidayDim = ndx.dimension(function(d) { 
        if(d.holiday == 1)
          return 'Holiday';
        if(d.day == 0 || d.day == 6) 
          return 'Weekend';
        return 'Work Day'; 
      }),
      averageDim = ndx.dimension(function(d) {
        return d;
      }),
      allDim = ndx.dimension(function(d) {return d;});

  // create groups (y-axis values)
  var all = ndx.groupAll();

  var countPerYear = yearDim.group().reduceSum(
    function(d){
      return d.hours;
    }),
      countPerMonth = monthDim.group().reduceSum(
        function(d){
      return d.hours;
    }),

      averageDimDayGroup = averageDim.group( function(d) { console.log(d); return d.dt.getMinutes(); }).reduce(
        function (p, v) {
              ++p.number;
              p.total += v.hours;
              p.avg = p.total / p.number;
              return p;
          },
          function (p, v) {
              --p.number;
              p.total -= v.hours;
              p.avg = (p.number == 0) ? 0 : Math.round(p.total / p.number);
              return p;
          },
          function () {
              return {
                number: 0, 
                total: 0, 
                avg: 0
              };
            }
        );

  var countPerHoliday = holidayDim.group().reduceSum(
    function(d){
      return d.hours;
    });
      
  // specify charts
  var yearChart   = dc.pieChart('#chart-ring-year'),
      monthChart   = dc.pieChart('#chart-ring-month'),
      dayChart   = dc.pieChart('#chart-ring-day'),
      yearlyBarChart  = dc.barChart('#chart-rating-count'),
      dataCount = dc.dataCount('#data-count'),
      dataTable = dc.dataTable('#data-table'),
      dataTableProjectAverage = dc.dataTable('#data-table-project-average'),
      dataTableDayAverage = dc.dataTable('#data-table-day-average'),
      referralsChart = dc.bubbleChart('#chart-community-rating-count'),
      holidayChart = dc.pieChart('#chart-ring-day-holiday'),
      dayOfWeekChart = dc.rowChart('#day-of-week-chart'),
      monthlyBarChart = dc.barChart('#chart-ibu-count');

  var dayOfWeek = ndx.dimension(function (d) {
        var day = d.dt.getDay();
        var name = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
        return day + '.' + name[day];
    });
    var dayOfWeekGroup = dayOfWeek.group().reduceSum(
      function(d){
        return d.hours;
    });
    var dayOfWeekDimGroup = dayOfWeekDim.group().reduceSum(
      function(d){
        return d.hours;
    });

  var month = ndx.dimension(function (d) {
        var mon = d.dt.getMonth();
        var name = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
        //console.log(mon);
        return name[mon];
    });

  var countMonth = month.group().reduceSum(
    function(d){
      return d.hours;
    });

  var referralsChartXFilter = crossfilter(workData);
    var referralsChartXGroups = referralsChartXFilter.groupAll();
  //NOVO

  var dateDimension = referralsChartXFilter.dimension(function (d) {
        return d.dt;
    });


var projectDimension = ndx.dimension(function (d) {
        return d.project.name;
    });


var projectPerformanceGroup = projectDimension.group().reduce(
        /* callback for when data is added to the current filter results */
        

        function (p, v) {
          console.log(p.minDate);
            ++p.count;
            p.name = v.project.name;
            p.minDate = Math.min(p.minDate, v.dt);
            p.maxDate = Math.max(p.maxDate, v.dt);
            p.tags.push(...v.tags);
            p.description = v.description;
            if(v.holiday || v.dt.getDay() == 0 || v.dt.getDay() == 6)
              p.overtime += 1;
            p.totalHours += v.hours;
            

            return p;
        },
        /* callback for when data is removed from the current filter results */
        function (p, v) {
            --p.count;
            p.minDate = Math.min(p.minDate, v.dt);
            p.maxDate = Math.max(p.maxDate, v.dt);
            for(var i = 0; i < v.tags.length; i++) {
              delete p.tags[_.indexOf(p.tags, v.tags[i])];
              p.tags = _.compact(p.tags);
            }
            if(v.holiday || v.dt.getDay() == 0 || v.dt.getDay() == 6)
              p.overtime -= 1;
            p.totalHours -= v.hours;
            return p;
        },
        /* initialize p */
        function () {
            return {
                name: '',
                minDate: new Date(),
                maxDate: new Date(-86400000),
                tags: [],
                overtime: 0,
                count: 0,
                totalHours: 0
            };
        }
    );


  yearChart
      .width(150)
      .height(150)
      .dimension(yearDim)
      .group(countPerYear)
      .innerRadius(20)
      .ordering(function (d) {
        var order = {
          'TRUE': 1, 'FALSE': 2
        };
        return order[d.key];
      });


  monthChart
      .width(150)
      .height(150)
      .dimension(month)
      .group(countMonth)
      .innerRadius(20)
      .ordering(function (d) {
        var order = {
          'Jan': 1, 'Feb': 2, 'Mar': 3, 'Apr': 4,
          'May': 5, 'Jun': 6, 'Jul': 7, 'Aug': 8,
          'Sep': 9, 'Oct': 10, 'Nov': 11, 'Dec': 12
        };
        return order[d.key];
      });


  dayChart
      .width(150)
      .height(150)
      .dimension(dayOfWeekDim)
      .group(dayOfWeekDimGroup)
      .innerRadius(20)
      .ordering(function (d) {
        var order = {
          'Mon': 0, 'Tue': 1, 'Wed': 2, 'Thu': 3,
          'Fri': 4, 'Sat': 5, 'Sun': 6
        }
        return order[d.key];
      }
     );


  holidayChart
    .width(150)
    .height(150)
    .innerRadius(20)
    .dimension(holidayDim)
    .group(countPerHoliday);


  yearlyBarChart
      .width(350)
      .height(180)
      .dimension(yearDim)
      .group(countPerYear)
      .x(d3.scale.linear().domain([2010,2017]))
      .elasticY(true)
      .centerBar(true)
      .barPadding(0.1)
      .outerPadding(0.05)
      .xAxisLabel('Year')
      .yAxisLabel('Count')
      .margins({top: 10, right: 20, bottom: 50, left: 50})
      .xAxis().tickValues([2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017]);


  monthlyBarChart
   .width(420)
   .height(180)
   .x(d3.scale.ordinal())
   .xUnits(dc.units.ordinal)
   .brushOn(true)
   .dimension(monthDim)
   .barPadding(0.1)
   .outerPadding(0.05)
   .group(countPerMonth)
   .xAxisLabel('Month')
   .yAxisLabel('Count')
   .on('pretransition', function(chart) {
       chart.selectAll("rect.bar").on("click", function (d) {
           console.log('click');
           chart.filter(null)
               .filter(d.data.key)
               .redrawGroup();
       });
   });


  dayOfWeekChart /* dc.rowChart('#day-of-week-chart', 'chartGroup') */
        .width(180)
        .height(180)
        .margins({top: 20, left: 10, right: 10, bottom: 20})
        .group(dayOfWeekGroup)
        .dimension(dayOfWeek)
        // Assign colors to each value in the x scale domain
        .ordinalColors(['#3182bd', '#6baed6', '#9ecae1', '#c6dbef', '#dadaeb'])
        .label(function (d) {
            return d.key.split('.')[1];
        })
        // Title sets the row text
        .title(function (d) {
            return d.value;
        })
        .elasticX(true)
        .xAxis().ticks(4);


  referralsChart
      // (_optional_) define chart width, `default = 200`
        .width(990)
        // (_optional_) define chart height, `default = 200`
        .height(250)
        // (_optional_) define chart transition duration, `default = 750`
        .transitionDuration(1500)
        .margins({top: 10, right: 50, bottom: 30, left: 40})
        .dimension(projectDimension)
        //The bubble chart expects the groups are reduced to multiple values which are used
        //to generate x, y, and radius for each key (bubble) in the group
        .group(projectPerformanceGroup)
        
        .linearColors(['green', 'red'])
        .colorDomain([0, 1001])
    //##### Accessors

        //Accessor functions are applied to each value returned by the grouping

        // `.colorAccessor` - the returned value will be passed to the `.colors()` scale to determine a fill color
        .colorAccessor(function (p) {
          //console.log(d);
            return p.value.overtime * 1000 / p.value.count;
        })
        // `.keyAccessor` - the `X` value will be passed to the `.x()` scale to determine pixel location
        .keyAccessor(function (p) {
          //console.log(p);
            return p.value.maxDate;
        })
        // `.valueAccessor` - the `Y` value will be passed to the `.y()` scale to determine pixel location
        .valueAccessor(function (p) {
          //console.log(p);
          var date = new Date(p.value.minDate);
          var yAxis = date.getFullYear() + date.getMonth()/12;
          console.log(date.getFullYear());
            return yAxis//p.value.minDate;
        })
        // `.radiusValueAccessor` - the value will be passed to the `.r()` scale to determine radius size;
        //   by default this maps linearly to [0,100]
        .radiusValueAccessor(function (p) {
            return p.value.totalHours;
        })
        .maxBubbleRelativeSize(0.3)
        .x(dc.d3.time.scale().domain([new Date(2005, 0, 1), new Date(2017, 11, 31)]).nice(d3.time.month))
        .y(dc.d3.scale.linear().domain([1900, 2050]))
        .r(dc.d3.scale.linear().domain([0, 200]))
        //##### Elastic Scaling

        //`.elasticY` and `.elasticX` determine whether the chart should rescale each axis to fit the data.
        .elasticY(true)
        .elasticX(true)
        //`.yAxisPadding` and `.xAxisPadding` add padding to data above and below their max values in the same unit
        //domains as the Accessors.
        .yAxisPadding(1)
        .xAxisPadding(500)
        // (_optional_) render horizontal grid lines, `default=false`
        .renderHorizontalGridLines(true)
        // (_optional_) render vertical grid lines, `default=false`
        .renderVerticalGridLines(true)
        // (_optional_) render an axis label below the x axis
        .xAxisLabel('Last Date')
        // (_optional_) render a vertical axis lable left of the y axis
        .yAxisLabel('Start Date')
        //##### Labels and  Titles

        //Labels are displayed on the chart for each bubble. Titles displayed on mouseover.
        // (_optional_) whether chart should render labels, `default = true`
        .renderLabel(true)
        .label(function (p) {
            return p.key;
        })
        // (_optional_) whether chart should render titles, `default = false`
        .renderTitle(true)
        .title(function (p) {
          console.log(p);
            return [
                p.key,
                'Start Date: ' + new Date(p.value.minDate).toLocaleDateString('en-GB', {  
                    day : 'numeric',
                    month : 'short',
                    year : 'numeric'
                }).split(' ').join('/'),
                'Last Date: ' + new Date(p.value.maxDate).toLocaleDateString('en-GB', {  
                    day : 'numeric',
                    month : 'short',
                    year : 'numeric'
                }).split(' ').join('/'),
                'Total Hours: ' + p.value.totalHours,
                'Tags: ' + _.unique(p.value.tags.map((arg) => arg.name)).join(', '),
                'Description: ' + p.value.description
                ].join('\n')
        });
  

  dataCount
      .dimension(ndx)
      .group(all);

   dataTable
    .dimension(allDim)
    .group(function (d) { return 'dc.js insists on putting a row here so I remove it using JS'; })
    .size(100)
    .columns([
      function (d) { return d.id; },
      function (d) { return d.project.name; },
      function (d) { return d.datum; },
      function (d) { 
        var tags = "";
        var i = 0;
        for(i = 0; i < d.tags.length - 1; i++)
          tags += d.tags[i].name + ", ";
        tags += d.tags[i].name;
        return tags; 
      },
      function (d) { return d.hours; },
      function (d) { return d.description; },
      function (d) { return d.holiday; }
    ])
    .order(d3.descending)
    .on('renderlet', function (table) {
      // each time table is rendered remove nasty extra row dc.js insists on adding
      table.select('tr.dc-table-group').remove();

     
    });
  rank = function (p) { return "" };

    dataTableProjectAverage
    .width(768)
    .height(480)
    .dimension(projectPerformanceGroup)
    .group(rank)
    .columns([function (d) { return d.key },
              function (d) { return d.value.totalHours / d.value.count }])
    .sortBy(function (d) { console.log(d);return d.key })
    .order(d3.descending);

    dataTableDayAverage
    .width(768)
    .height(480)
    .dimension(averageDimDayGroup)
    .group(rank)
    .columns([function (d) { return d.value.avg }])
    .sortBy(function (d) { return d.key })
    .order(d3.descending);


  // register handlers
  d3.selectAll('a#all').on('click', function () {
    dc.filterAll();
    dc.renderAll();
  });

  d3.selectAll('a#year').on('click', function () {
    yearChart.filterAll();
    dc.redrawAll();
  });

  d3.selectAll('a#month').on('click', function () {
    monthChart.filterAll();
    dc.redrawAll();
  });

  d3.selectAll('a#day').on('click', function () {
    dayChart.filterAll();
    dc.redrawAll();
  });

  // showtime!
  dc.renderAll();

//});
