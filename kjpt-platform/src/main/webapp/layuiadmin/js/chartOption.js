// 获取直角坐标系配置
function getChartOption(config) {
  var seriesData = [], legendData = [], categoryData = [];

  $.each(config.series, function(index, item) {
    legendData.push(item.name);

    var itemData = [];
    $.each(config.data, function(index, subItem) {
      itemData.push(subItem[item.valueKey])
    })
    seriesData.push({
      name: item.name,
      data: itemData,
      type: 'bar',
      barWidth: 28,
      label: {
        show: true,
        position: 'top',
        color: '#46484B',
        fontSize: 12
      }
    });
  })

  $.each(config.data, function(i, item) {
    categoryData.push(item[config.itemName]);
  })
  
  var option = {
    grid: (function() {
      var gridItem = {
        left: 60,
        right: 100,
        bottom: 20,
        top: 76,
        containLabel: true
      };
      if (config.grid) {
        for (var key in config.grid) {
          gridItem[key] = config.grid[key];
        }
      }
      return gridItem;
    })(),
    legend: (function() {
      var lenendItem = {
        show: true,
        left: 60,
        top: 10,
        itemWidth: 12,
        itemHeight: 12,
        padding: 0,
        itemGap: 12,
        textStyle: {
          fontSize: 12,
          lineHeight: 12
        },
        data: legendData,
      }
      if (config.legend) {
        for (var key in config.legend) {
          lenendItem[key] = config.legend[key];
        }
      }
      return lenendItem;
    })(),
    tooltip: {
      show: true,
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    xAxis: {
      type: 'category',
      data: categoryData,
      axisTick: {show: false},
      splitLine: {show: false},
      axisLine: { show: true, lineStyle: {color: '#ABB0BB'} },
      axisLabel: {
        show: true,
        fontSize: 14,
        color: '#46484B'
      }
    },
    yAxis: {
      type: 'value',
      axisLine: {show: false},
      axisTick: {show: false},
      axisLabel: {
        show: true,
        color: '#ABB0BB',
        fontSize: 12
      },
      splitLine: {
        show: true,
        lineStyle: {
          color: '#DBE0E2'
        }
      }
    },
    series: seriesData,
    color: config.color || '#0AA1FF'
  };
  return option;
}
// 饼图环图配置
function getPieChartOption(config) {
  var option = {
    title: (function() {
      if (config.title) {
        var titleItem = {
          text: '-',
          top: 'center',
          left: 'center',
          textStyle: {
            color: '#fff',
            fontWeight: 'normal',
            fontSize: 14,
            lineHeight: 20,
          },
          padding: 0,
          itemGap: 0
        }
        if (config.title === 'value') {

        } else {
          titleItem.text = config.title;
        }
      }
    })(),
    tooltip:  {
      show: true,
      trigger: 'item',
      axisPointer: {
        type: 'shadow'
      }
    },
    legend: {
      show: true,
      orient: 'vertical',
      left: 30,
      top: 20,
      itemWidth: 12,
      itemHeight: 12,
      padding: 0,
      itemGap: 16
    },
    series: [
      {
        name: config.title || 'pieTile',
        type:'pie',
        radius: ['50%', '70%'],
        center: ['60%', '50%'],
        avoidLabelOverlap: false,
        labelLine: { show: false },
        data: config.series,
        label: {
          show: true,
          position: 'inside',
          color: '#313232',
          formatter: '{c}\n{d}%'
        }
      }
    ],
    color: config.color || '#0AA1FF'
  };
  return option;
}
// 报表初始化
var kyptCharts = {
  chart: {},
  getChartOption: function (config) {
    if (config.type === 'bar' || config.type === 'line') {
      return getChartOption(config);
    } else if (config.type === 'pie') {
      return getPieChartOption(config);
    }
  },
  render: function (config) {
    var _this = this;

    if (!config.id || !config.type) {
      top.layer.alert('[kyptCharts]图表类型与ID不能为空！')
      return null;
    } else {
      _this.chart[config.id] = {
        config: {}
      };
      for (var key in config) {
        _this.chart[config.id].config[key] = config[key];
      }
    };

    var elem = document.getElementById(config.id),
    chartDemo = echarts.init(elem),
    chartOption = _this.getChartOption(config);

    // 渲染图表
    chartDemo.setOption(chartOption);
  
    // 窗口大小变化时，更新图表渲染
    $(window).resize(function() {
      chartDemo.resize();
    })
  
    // 回调函数，返回图表对象
    if (config.callback) {
      config.callback(chartDemo);
    }

    _this.chart[config.id].chart = chartDemo;
  },
  reload: function(id, config) {
    if (!id) {
      return null;
    } else {
      var _this = this;

      if (!config) {
        _this.chart[id].chart.resize();
      } else if (typeof(config) === 'object') {
        for (var key in config) {
          _this.chart[id].config[key] = config[key];
          _this.chart[id].chart.setOption(_this.getChartOption(_this.chart[id].config));
        }
      }
    }
  }
}
