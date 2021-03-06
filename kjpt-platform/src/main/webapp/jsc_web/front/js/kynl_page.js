var variable = getQueryVariable()
if (variable) {
    if (variable.name) {
        var curName = decodeURI(variable.name);
        var curLevel = '';
        if (curName == '国家级') {
            curLevel = '01'
        } else if (curName == '国家部委级') {
            curLevel = '02'
        } else if (curName == '地方省级') {
            curLevel = '03'
        } else if (curName == '集团级') {
            curLevel = '04'
        } else if (curName == '板块级') {
            curLevel = '05'
        }
        $('.page-layout-title .tab-btn').removeClass('selected');
        $('.page-layout-title .tab-btn').each(function (item) {
            var itemText = $(this).context.innerText;
            if (itemText == curName) {
                $(this).addClass('selected');
            }
        })
        loadNotes(curLevel);
        loadCurNotes(curLevel);
    }
} else {
    loadNotes('01');
    loadCurNotes();
}

// 科技人才
kyptCharts.render({
    id: 'kynl_kjrc_charts',
    type: 'pie',
    legendPosition: 'right',
    legend: {
        top: 'center',
        formatter: 'name|value'
    },
    label: false,
    labelColor: '#fff',
    radius: ['52%', '72%'],
    // borderColor: '#001e38',
    title: '成果鉴定',
    totalTitle: true,
    company: '人',
    title: {
        textStyle: {
            color: '#fff',
            fontSize: 30,
            width: '100%',
            fontFamily:'Impact'
        },
        subtextStyle: {
            color: '#fff',
            fontSize: 12,
            width: '100%',
            verticalAlign: 'bottom',
        }
    },
    series: [
        // { name: '科技活动人员', value: '25' },
        // { name: '研究开发（R&D）', value: '20' },
        // { name: '技能人才', value: '15' },
        // { name: '技术经济人', value: '23' }
    ],
    color: ['#E6BD4A', '#60C14D', '#4998D1', '#E0DF74', '#D66635'],
    callback: function (param) {
        param.on('click', function () {
            var openUrl = {
                url: '/kjpt/achieve/identify/achieve_identify_plan_list.html',
                name: '成果鉴定'
            }
            window.open('/index', 'kjpt_webapp');
            localStorage.setItem("url", JSON.stringify(openUrl));
        })
    },
});

// 二级单位科研平台分布情况
kyptCharts.render({
    id: 'kynl_dwfb_charts',
    type: 'bar',
    grid: {
        top: 40
    },
    label: false,
    legend: {
        show: true
    },
    legendPosition: 'top',
    labelColor: '#fff',
    data: [],
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow',
            shadowStyle: {
                color: 'rgba(0, 0, 0, 0)'
            }
        }
    },
    itemName: 'name',
    series: [{
            name: '国家级',
            valueKey: 'value1',
            stack: 'charts'
        },
        {
            name: '国家部委级',
            valueKey: 'value2',
            stack: 'charts'
        },
        {
            name: '地方省级',
            valueKey: 'value3',
            stack: 'charts'
        },
        {
            name: '集团级',
            valueKey: 'value4',
            stack: 'charts'
        },
        {
            name: '板块级',
            valueKey: 'value5',
            stack: 'charts'
        }
    ],
    color: ['#D86436', '#DEAA49', '#A2CF99', '#3461D3', '#72D8F0'],
    lineColor: 'rgba(30, 83, 137, .6)',
    axisLineColor: 'rgba(30, 83, 137, .6)',
    valueColor: '#fff',
    labelColor: '#fff',
    labelRotate: 40,
    yAxis: [{
        splitNumber: 3,
        name: '单位：个',
        nameGap: 20,
        nameTextStyle: {
            color: '#fff'
        }
    }],
    barWidth: 20
});

function addTableData(data) {
    if (typeof (data) === 'object' && data.length) {
        var $tbodyContent = $('#tbodyContent').empty(),
            tbodyHtml = '';

        $.each(data, function (index, item) {
            tbodyHtml += '<tr>';
            tbodyHtml += '<td class="kyptName">' + item.createUnitName + '</td>';
            tbodyHtml += '<td class="subName">' + item.levelText + '</td>';
            tbodyHtml += '<td class="nuitName">' + item.supportingInstitutionsText + '</td>';
            tbodyHtml += '</tr>';
        });
        $tbodyContent.html(tbodyHtml);

        setTimeout(function () {
            var $table = $('#kynl_kypt_table .table-scroll-layout:eq(0)')
            $('#kynl_kypt_table > .table-header').css({
                'padding-right': $table.outerWidth() - $table.children('table').outerWidth()
            });
        }, 120);
    }
}

$('#tabHeader').on('click', '.tab-btn', function (e) {
    var curLevel = $(this).attr('data-level');
    if (!$(this).hasClass('selected')) {
        $(this).addClass('selected').siblings('.tab-btn').removeClass('selected');
        var tabType = $(this).attr('type');
        loadNotes(curLevel);
        loadCurNotes(curLevel);
    }
})

var tableListData = [{
    name: '国家（工程）技术研究中心',
    pName: '国家同位素工程技术研究中心',
    yName: 'xxx单位'
}, {
    name: '国家（工程）技术研究中心',
    pName: '国家同位素工程技术研究中心',
    yName: 'xxx单位'
}, {
    name: '国家（工程）技术研究中心',
    pName: '国家同位素工程技术研究中心',
    yName: 'xxx单位'
}, {
    name: '国家（工程）技术研究中心',
    pName: '国家同位素工程技术研究中心',
    yName: 'xxx单位'
}, {
    name: '国家（工程）技术研究中心',
    pName: '国家同位素工程技术研究中心',
    yName: 'xxx单位'
}];
// addTableData(tableListData);


var unitChartData = [{
        name: '中国核能',
        value1: 21,
        value2: 76,
        value3: 18,
        value4: 55,
        value5: 55
    },
    {
        name: '中国原子',
        value1: 46,
        value2: 18,
        value3: 46,
        value4: 18,
        value5: 55
    },
    {
        name: '中国铀业',
        value1: 18,
        value2: 55,
        value3: 46,
        value4: 18,
        value5: 55
    },
    {
        name: '中国核工',
        value1: 50,
        value2: 43,
        value3: 46,
        value4: 18,
        value5: 55
    },
    {
        name: '中核环保',
        value1: 21,
        value2: 76,
        value3: 46,
        value4: 18,
        value5: 55
    },
    {
        name: '中国同辐',
        value1: 46,
        value2: 18,
        value3: 46,
        value4: 18,
        value5: 55
    },
    {
        name: '中国中原',
        value1: 18,
        value2: 55,
        value3: 46,
        value4: 18,
        value5: 55
    },
    {
        name: '上海中核',
        value1: 50,
        value2: 43,
        value3: 46,
        value4: 18,
        value5: 55
    },
    {
        name: '中国宝原',
        value1: 21,
        value2: 76,
        value3: 46,
        value4: 18,
        value5: 55
    },
    {
        name: '中核建资',
        value1: 46,
        value2: 18,
        value3: 46,
        value4: 18,
        value5: 55
    },
    {
        name: '核动力设计院',
        value1: 18,
        value2: 55,
        value3: 46,
        value4: 18,
        value5: 55
    },
    {
        name: '中国核电工程',
        value1: 50,
        value2: 43,
        value3: 46,
        value4: 18,
        value5: 55
    },
    {
        name: '中国原子能',
        value1: 21,
        value2: 76,
        value3: 46,
        value4: 18,
        value5: 55
    },
    {
        name: '核工业西南',
        value1: 46,
        value2: 18,
        value3: 46,
        value4: 18,
        value5: 55
    },
    {
        name: '核工业标准',
        value1: 18,
        value2: 55,
        value3: 46,
        value4: 18,
        value5: 55
    },
    {
        name: '中国核科技',
        value1: 50,
        value2: 43,
        value3: 46,
        value4: 18,
        value5: 55
    },
    {
        name: '中核工程',
        value1: 21,
        value2: 76,
        value3: 46,
        value4: 18,
        value5: 55
    },
    {
        name: '中核能源',
        value1: 46,
        value2: 18,
        value3: 46,
        value4: 18,
        value5: 55
    },
    {
        name: '新华水力',
        value1: 18,
        value2: 55,
        value3: 46,
        value4: 18,
        value5: 55
    }
];
// // 模仿HTTP请求延迟加载
// setTimeout(function () {
//     // 自行添加加载动画效果
//     kyptCharts.reload('kynl_dwfb_charts', {
//         data: unitChartData
//     });
// }, 1200)
// HTTP请求公式
//科技人才
function loadTechnological() {
    httpModule({
        url: "/getTongjiList",
        success: function (result) {
            //emptyChart
            if (result.success) {
                if(result.data.length >= 0){
                    kyptCharts.reload('kynl_kjrc_charts', {
                        series: result.data
                    });
                }
                
            }
        },
        errro: function (data) {

        }
    });
}
loadTechnological();
//科技人才
function loadNotes(curLevel) {
    httpModule({
        url: "/getPlatFormList?level=" + curLevel,
        success: function (result) {
            if (result.success) {
                addTableData(result.data)
            }
        },
        errro: function (data) {

        }
    });
};


//科技人才
function loadCurNotes(value) {
    var curLevel = '';
    if (value) {
        curLevel = value;
    }
    httpModule({
        url: "/indexHomeBI-api/distribution?level=" + curLevel,
        success: function (result) {
            if (result.success) {
                var curIdList = []; //数组id
                var curItemList = [];
                var len = result.data.length;
                var curObj = {};
                var curSeriesObj = {};
                var curSeriesList = [];
                var resultArr;
                resultArr = result.data.map(function (item, index, self) {
                    return item.unitId;
                });
                for (let i of resultArr) {
                    if (!curObj[i]) {
                        curIdList.push(i)
                        curObj[i] = 1
                    }
                };
                if (curLevel == '01') {
                    curSeriesObj['name'] = '国家级';
                    curSeriesObj['valueKey'] = 'value1';
                    curSeriesObj['stack'] = 'charts';
                } else if (curLevel == '02') {
                    curSeriesObj['name'] = '国家部委级';
                    curSeriesObj['valueKey'] = 'value2';
                    curSeriesObj['stack'] = 'charts';
                } else if (curLevel == '03') {
                    curSeriesObj['name'] = '地方省级';
                    curSeriesObj['valueKey'] = 'value3';
                    curSeriesObj['stack'] = 'charts';
                } else if (curLevel == '04') {
                    curSeriesObj['name'] = '集团级';
                    curSeriesObj['valueKey'] = 'value4';
                    curSeriesObj['stack'] = 'charts';
                } else if (curLevel == '05') {
                    curSeriesObj['name'] = '板块级';
                    curSeriesObj['valueKey'] = 'value5';
                    curSeriesObj['stack'] = 'charts';
                }
                curSeriesList.push(curSeriesObj);
                $.each(curIdList, function (i, v) {
                    var curItemObj = {};

                    $.each(result.data, function (index, item) {
                        if (item.unitId == v) {
                            switch (item.level) {
                                case '01':

                                    curItemObj['name'] = item.unitName;
                                    curItemObj['value1'] = item.value;

                                    break;
                                case '02':

                                    curItemObj['name'] = item.unitName;
                                    curItemObj['value2'] = item.value;
                                    break;
                                case '03':

                                    curItemObj['name'] = item.unitName;
                                    curItemObj['value3'] = item.value;
                                    break;
                                case '04':

                                    curItemObj['name'] = item.unitName;
                                    curItemObj['value4'] = item.value;
                                    break;
                                default:

                                    curItemObj['name'] = item.unitName;
                                    curItemObj['value5'] = item.value;
                            }
                        }
                    })
                    curItemList.push(curItemObj)

                })
                if(curLevel){
                    kyptCharts.reload('kynl_dwfb_charts', {
                        series: curSeriesList,
                        data: curItemList
                    });
                }else{
                    kyptCharts.reload('kynl_dwfb_charts', {
                        data: curItemList
                    });
                }
                
            }
        },
        errro: function (data) {

        }
    });
}