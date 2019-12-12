function getProjectData(data, key, value) {
  var itemData = null;
  for (var i = 0; i < data.length; i++) {
    if (data[i][key] === value) {
      itemData = data[i];
      break;
    }
  }
  return itemData;
}

layui.use(['laydate'], function() {
  var laydate = layui.laydate,
  projectYearChart = echarts.init(document.getElementById('addProjectYear')), // 新增项目数量年度趋势分析
  projectMonthChart = echarts.init(document.getElementById('addProjectMonth')), // 新增项目数量年度趋势分析
  typeRatioChart = echarts.init(document.getElementById('projectTypeRatio')), // 项目计划投资按项目类型占比分析 
  agenciesRatioChart = echarts.init(document.getElementById('projectAgenciesRatio')), // 项目投资计划按机构占比分析 
  keyData = [
    { name: '核能开发', valueKey: 'nuclearEnergyDev'},
    { name: '集中开发', valueKey: 'focusDev'},
    { name: '国防基础', valueKey: 'nationalDefense'},
    { name: '国防技术基础', valueKey: 'defenseTechnology'}
  ];

  // 模拟数据 年度趋势分析
  var projectData = [
    {
      year: '2019',
      nuclearEnergyDev: 1800,
      focusDev: 2000,
      nationalDefense: 3900,
      defenseTechnology: 1200
    },
    {
      year: '2018',
      nuclearEnergyDev: 3900,
      focusDev: 5800,
      nationalDefense: 4100,
      defenseTechnology: 1210
    },
    {
      year: '2017',
      nuclearEnergyDev: 6000,
      focusDev: 2000,
      nationalDefense: 3200,
      defenseTechnology: 1400
    }
  ];
  
  // 渲染新增项目数量年度趋势分析表
  projectYearChart.setOption(getChartOption({
    itemName: 'labelName',
    series: [
      { name: '2019', valueKey: 'value2019'},
      { name: '2018', valueKey: 'value2018'},
      { name: '2017', valueKey: 'value2017'}
    ],
    data: (function() {
      var chartData = [];
      $.each(keyData, function(i, item) {
        var itemData = {
          labelName: item.name,
          value2017: getProjectData(projectData, 'year', '2017')[item.valueKey],
          value2018: getProjectData(projectData, 'year', '2018')[item.valueKey],
          value2019: getProjectData(projectData, 'year', '2019')[item.valueKey]
        };
        chartData.push(itemData);
      })
      return chartData;
    })(),
    color: ['#0AA1FF', '#5DAC4A', '#FCBD3B']
  }));


  function loadMonthChart(date) {
    var time = date || '2019',
    chartData = [];

    for (var i = 1; i < 13; i++) {
      chartData.push({
        name: i+'月',
        value: (parseInt(Math.random() * 400) + 100) // 100 ~ 500
      })
    }

    projectMonthChart.setOption(getChartOption({
      itemName: 'name',
      legend: { show: false },
      grid: { top: 24 },
      series: [
        { name: '月度分析', valueKey: 'value'},
      ],
      data: chartData,
      color: '#FCBD3B'
    }));
  }

  // 渲染新增项目数量月度趋势分析表
  loadMonthChart();
  laydate.render({
    elem: '#projectMonth',
    type: 'year',
    value: '2019',
    done: function(value, date, endDate) {
      loadMonthChart(value);
    }
  });

  function loadTypeRatioChart(date) {
    var chartData = [], itemName = ['国防技术基础', '国防基础', '集中开发', '核能开发'];
    for (var i = 0; i < itemName.length; i++) {
      chartData.push({
        name: itemName[i], value: (parseInt(Math.random() * 400) + 100) // 100 ~ 500
      });
    }

    typeRatioChart.setOption(getPieChartOption({
      title: ' 项目计划投资按项目类型占比分析',
      series: chartData,
      color: ['#FFDF29', '#2370A3', '#009186', '#9EBE4A']
    }));
  }
  loadTypeRatioChart();

  laydate.render({
    elem: '#TypeRatioDate',
    type: 'month',
    value: '2019-12',
    done: function(value, date, endDate) {
      loadTypeRatioChart(value);
    }
  });

  // 

  function loadagenciesRatioChart(date) {
    var chartData = [], itemName = [
      '中核院',
      '核工院',
      '核能院',
      '核设计院',
      '核工业院',
      '核出版社',
      '核物流院',
      '核三一院'
    ];
    for (var i = 0; i < itemName.length; i++) {
      chartData.push({
        name: itemName[i], value: (parseInt(Math.random() * 400) + 100) // 100 ~ 500
      });
    }

    agenciesRatioChart.setOption(getPieChartOption({
      title: ' 项目投资计划按机构占比分析',
      series: chartData,
      color: ['#FFDF29', '#9EBE4A', '#5DAC4A', '#009186', '#2370A3', '#845596', '#F07045', '#FCBD3B']
    }));
  }
  loadagenciesRatioChart();

  laydate.render({
    elem: '#AgenciesRatioDate',
    type: 'month',
    value: '2019-12',
    done: function(value, date, endDate) {
      loadagenciesRatioChart(value);
    }
  });

})
