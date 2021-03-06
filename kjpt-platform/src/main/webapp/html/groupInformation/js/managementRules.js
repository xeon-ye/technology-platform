layui.use(['form', 'table', 'layer', 'laydate'], function(){
  var form = layui.form;
  var $ = layui.$;
  var table = layui.table;
  var layer = layui.layer;
  var laydate = layui.laydate;

  // var newTime = new Date(); //发布日期初始值
  // var timeString = newTime.getFullYear() + '-'+ (newTime.getMonth()+1) + '-' + newTime.getDate();
  laydate.render({elem: '#releaseTime',trigger:'click'});
  // laydate.render({elem: '#yearOrMonth',trigger:'click',type:'month'});

  // var params = getQueryVariable();
  // var reportType = params.reportType;

  function setSelectInput(){ //js动态设置条件过滤布局
      var len = ($("#selectRows").children(".layui-col-md4").length - 1) % 3;
      switch(len){
        case 0:
          $('#btnGroup').removeClass('layui-col-md4');
          $('#btnGroup').addClass('layui-col-md12');
        break;
        case 1:
          $('#btnGroup').removeClass('layui-col-md4');
          $('#btnGroup').addClass('layui-col-md8');
        break;
      }
  }
  setSelectInput();

  var tipTitle = "管理办法";

  var cols  = [ //表头
    {type: 'radio', field: 'id'},
    {field: 'methodName', title: tipTitle+'名称', templet: function(d) {
      return '<a href="planDetails.html?id='+d.id+'" class="layui-table-link">'+d.methodName+'</a>';
    }},
    // {field: 'publication', title:'发布处室'},
    {field: 'creator', title:'发布人'},
    {field: 'readRangeText', title:'阅读范围'},
    {field: 'edition', title:'版次'},
    {field: 'publishDate', title: '发布日期',sort:true,templet: function(d){
      var times = new Date(d.publishDate);
       return times.getFullYear() + '-' + (times.getMonth()+1) + '-' +times.getDate();
    }}
  ]

  //表格渲染
  var tableRender = false;
  function queryTable(searchData) {
    if (!tableRender) {
      tableRender = true;
      table.render({
        elem: '#tableDemo'
        ,url: '/manageMethod-api/query' //数据接口
        ,cols: [
          cols
        ],
        request: {
          pageName: 'pageNum', // 重置默认分页请求请求参数 page => pageIndex
          limitName: 'pageSize' // 重置默认分页请求请求参数 limit => pageSize
        },
        parseData: function(res) {return layuiParseData(res);},

        page: true, //开启分页
        limit: 10, // 每页数据条数,
        limits: [5, 10, 15, 20], // 配置分页数据条数
        where: searchData
      });
    } else {
      table.reload('tableDemo', {where: searchData});
    }
  }

  form.on('submit(formDemo)', function(data) {
    queryTable(data.field);
    return false;
  });

  $('[lay-filter="formDemo"]').click();

  function openDataDilog(type, id) {
	  var dialogTitle = '新增';
	  if (type === 'edit') {
		  dialogTitle = '编辑'; 
    }
    if(type === 'see'){
      dialogTitle = '查看'; 
    }
	  
	  // 打开弹窗
	  top.layer.open({
      type: 2,
      title: dialogTitle,
      area: ['880px', '70%'],
		  content: '/html/groupInformation/addManage.html?type=' + type  + '&id='+(id || ''),
		  btn: null,
		  end: function() {
        var relData = getDialogData('dialog-data');
			  if (relData) {
				  if (relData.code === '0') {
            layer.msg(dialogTitle+'成功!', {icon: 1});
            $('[lay-filter="formDemo"]').click();
				  } else {
					  layer.msg(relData.message, {icon: 2});
				  }
			  }
		  }
	  });
  }
  
  // 新增规划
  $('#addItem').on('click', function(e) {
	  openDataDilog('add');
  })
  // 查看规划
  $('#seeItem').on('click', function(e) {
	   // 获取表格中选中的数据
     var itemRowData = table.checkStatus('tableDemo').data;
     if (itemRowData.length) {
     openDataDilog('see', itemRowData[0].id);
     } else {
       layer.msg('请选择需要查看的'+tipTitle+'！');
     }
  })
  // 编辑规划
  $('#editItem').on('click', function(e) {
    // 获取表格中选中的数据
    var itemRowData = table.checkStatus('tableDemo').data;
	  if (itemRowData.length) {
		openDataDilog('edit', itemRowData[0].id);
    } else {
    	layer.msg('请选择需要编辑的'+tipTitle+'！');
    }
  })
  // 删除规划
  $('#delItem').on('click', function(e) {
    // 获取表格中选中的数据
    var itemRowData = table.checkStatus('tableDemo').data;
    if (itemRowData.length) {
		  top.layer.confirm('您确定要删除”'+itemRowData[0].methodName+'“吗？', {icon: 3, title:'删除提示'}, function(index){
		    top.layer.close(index);
        // 确认删除
        httpModule({
          url: '/manageMethod-api/delete/' + itemRowData[0].id,
          type: 'DELETE',
          success: function(relData) {
            if (relData.code === '0') {
              layer.msg('删除成功!', {icon: 1});
              $('[lay-filter="formDemo"]').click();
            } else {
              layer.msg('删除失败', {icon: 2});
            }
          }
        });
      });
    } else {
    	layer.msg('请选择需要删除的'+tipTitle+'！');
    }
  })

    $('#exportData').click(function() {
        var formValue = form.val('patentFormDemo'),
            searchData = {
                methodName: formValue.methodName || '', // 管理办法名称：
                edition: formValue.edition || '', // 版次：
                publishDate: formValue.publishDate || '' // 发布日期：
            },
            exportUrl = '';

        for (var key in searchData) {
            exportUrl += '&' + key + '=' + searchData[key];
        }
        exportUrl = '/manageMethod-api/exportExcel?' + exportUrl.substring(1);
        window.open(exportUrl, '_blank');
    })

    importFiles({
        id:'#importData',
        url:'/manageMethod-api/input_excel',
        callback: function (result) {
            if(result.code=="0") {
                layer.msg('数据导入成功!', {icon: 1});
                $('[lay-filter="formDemo"]').click();
            }else{
                layer.msg('数据导入失败!失败信息：'+result.message, {icon: 1});
            }
        }
    })
});