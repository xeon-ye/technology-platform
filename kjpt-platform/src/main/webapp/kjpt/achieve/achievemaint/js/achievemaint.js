layui.use(['element', 'form', 'jquery', 'table', 'laydate'], function () {
  var element = layui.element,
    $ = layui.jquery,
    form = layui.form,
    table = layui.table;
  var laydate = layui.laydate;
  var itemRowData = ''
  /*公共方法*/
  var objLayui = {
    openLayer: function (obj) {
      top.layer.open({
        type: 2,
        title: obj.title,
        content: obj.url, // 全局弹窗页面地址
        btn: null,
        area: ['50%', '60%'],
        end: function (lay, index) {
          // 弹窗销毁时获取弹窗返回数据
          var getData = getDialogData('dialog-data');
          if (getData !== null && getData.success) {
            // tableRender(obj.tableName,obj.arr,obj.tableUrl,variable.id)
            objLayui.tableList('')
          }
        }
      });
    },
    tableList: function (where) {
      /*列表展示*/
      table.render({
        elem: '#expertTable', // 表格元素ID
        url: '/achieveMaintain-api/query' //数据接口
          ,
        cols: [
          [ //表头
            {
              type: 'checkbox', 
            },
            {
              field: 'year',
              title: '获奖年份',
              align:'center',
              sort: true,
            },
            {
              field: 'typeText',
              title: '奖项级别',
              align:'center'
            },
            {
              field: 'awardsTypeText',
              title: '奖项名称',
              align:'center'
            },
            {
              field: 'awardsChildTypeText',
              title: '奖项子名称',
              align:'center'
            },
            {
              field: 'awardLevelText',
              title: '授奖等级',
              align:'center'
            },
            // {
            //   field: 'awardsChildTypeText',
            //   title: '授权等级',
            //   align:'center'
            // },
            {
              field: 'awardsNumber',
              title: '奖项数量',
              align:'center',
              sort: true,
            }
          ]
        ],
        parseData: function (res) {
          return layuiParseData(res);
        },
        method: "GET", // 数据请求接口类型 默认 get, 如果是get 可以去掉此项
        where: where,
        request: {
          pageName: 'pageNum', // 重置默认分页请求请求参数 page => pageIndex
          limitName: 'pageSize' // 重置默认分页请求请求参数 limit => pageSize
        },
        page: true //开启分页
          ,
        limit: 10,
        limits: [10, 15, 30, 60], // 配置分页数据条数
      });
    }
  }
  /*初始表格*/
  objLayui.tableList("");

  laydate.render({
    elem: '#startYear', //指定元素
    type: "year",
    trigger: 'click',
    change: function (value, date, endDate) {
      $("#startYear").val(value);
      $("#endYear").val(value);
    }
  });

  laydate.render({
    elem: '#endYear', //指定元素
    type: "year",
    trigger: 'click',
    change: function (value, date, endDate) {
      $("#endYear").val(value)
    }
  });
  /*表格行被选中*/
  // table.on('checkbox(expertTable)', function (obj) {
  //   itemRowData = obj.data;
  //   
  // });
  form.on('select(type)', function(data){
    $('select[name="awardsType"]').attr('dic-base-data',data.value);
    createElement(data.value,"awardsType","option","awardsType");
    $("#awardsChildType" ).find('option').remove();
    $('select[name="awardLevel"]').find('option').empty();
  })
  form.on('select(awardsType)', function(data){
    $('select[name="awardLevel"]').find('option').empty();
    if(data.value == 'ROOT_KJPT_CGWH_HJLX_SBJJ_GSKJJ'){
      $('#awardsChildType').attr('name','');
      $('#awardsChildTypeInput').attr('name','awardsChildType');
      $('#selectBox').addClass('hide-box');
      $('#inputBox').removeClass('hide-box');
      $('select[name="awardLevel"]').attr('dic-base-data', data.value);
      createElement(data.value, "awardLevel", "option", "awardLevel")
    }else{
      $('#inputBox').addClass('hide-box');
      $('#selectBox').removeClass('hide-box');
      $('#awardsChildType').attr('name','awardsChildType');
      $('#awardsChildTypeInput').attr('name','');
      $('select[name="awardsChildType"]').attr('dic-base-data',data.value);
      createElement(data.value,"awardsChildType","option","awardsChildType");
    }
  })
  form.on('select(awardsChildType)', function (data) {
    $('select[name="awardLevel"]').attr('dic-base-data', data.value);
    createElement(data.value, "awardLevel", "option", "awardLevel")
  })

   /*动态生成元素*/
   function createElement(code,id,element,name) {
    $("#"+id ).find('option').remove();
    httpModule({
        url: "/sysDictionary-api/getChildsListByCode/"+code,
        type: 'GET',
        success: function(relData) {
            if (relData.success === true) {
                relData.data.map(function(item){
                    if(element=="option"){
                      var str = "<option placeholder='请选择'></option><option value='"+item.numValue+"' name='"+item.numValue+"'>"+item.name+"</option>"
                        $("#"+id).append(str)
                    }else if(element=="radio"){
                        $("#"+id).append('<input type="radio" name="'+name+'" value="'+item.numValue+'" title="'+item.name+'">')
                    }
                });
                form.render()
            }
        }
    });
}
  form.on('submit(formDemo)', function (data) {
    objLayui.tableList(data.field)
    return false
  })
  var $ = layui.$,
    active = {
      reset: function () {
        /*重置*/
        // $("#name").val("");
        objLayui.tableList();
      },
      addItem: function () {
        var obj = {
          title: '新增成果奖项',
          url: '/kjpt/achieve/achievemaint/achievemaint_add.html?type=edit',
        }
        /*新增*/
        objLayui.openLayer(obj);
      },
      editItem: function () {
        var itemRowData = table.checkStatus('expertTable').data;
        /*编辑*/
        if (itemRowData.length > 1) {
          layer.msg('请选择单条数据进行编辑！');
          return
        }
        if (itemRowData.length == 0) {
          layer.msg('请选择需要编辑的数据！');
          return
        }
        var obj = {
          title: '编辑成果奖项',
          url: '/kjpt/achieve/achievemaint/achievemaint_add.html?expertId=' + itemRowData[0].id + '&type=edit',
        }
        objLayui.openLayer(obj);
      },
      seeItem: function () {
        var itemRowData = table.checkStatus('expertTable').data;
        //查看
        if (itemRowData.length > 1) {
          layer.msg('请选择单条数据进行查看！');
          return
        }
        if (itemRowData[0].id == undefined) {
          layer.msg('请选择需要查看的数据！');
          return
        }
        var obj = {
          title: '查看成果奖项',
          url: '/kjpt/achieve/achievemaint/achievemaint_add.html?expertId=' + itemRowData[0].id + '&type=see',
        }
        objLayui.openLayer(obj);
      },
      delItem: function () {
        var itemRowData = table.checkStatus('expertTable').data;
        var idList=itemRowData.map(function(item,index){
          return item.id
        })
        idList=idList.join(',').toString();
        console.log(idList)
        // return ;
        /*删除*/
        if (itemRowData) {
          top.layer.confirm('您确定要删除吗？', {
            icon: 3,
            title: '删除提示'
          }, function (index) {
            layer.close(index);
            // 确认删除
            httpModule({
              url: '/achieveMaintain-api/delete/' + idList,
              type: 'DELETE',
              success: function (relData) {
                if (relData.success) {
                  layer.msg('删除成功!', {
                    icon: 1
                  });
                  top.layer.closeAll(); // 关闭弹窗
                  objLayui.tableList('');
                } else {
                  layer.msg('删除失败', {
                    icon: 2
                  });
                }
              }
            });
          });
        } else {
          layer.msg('请选择需要删除的数据！');
        }
      },
      exportData: function () {
        var formValue = form.val('patentFormDemo'),
          searchData = {
            type: formValue.type || '', // 获奖类型
            startYear: formValue.startYear || '', //开始时间
            endYear: formValue.endYear || '', // 结束时间
            awardsType: formValue.awardsType || '', // 成果奖项
          },
          exportUrl = '';

        for (var key in searchData) {
          exportUrl += '&' + key + '=' + searchData[key];
        }
        exportUrl = '/achieveMaintain-api/export?' + exportUrl.substring(1);
        window.open(exportUrl, '_blank');
      }
    }
  $('.layui-btn').on('click', function () {
    var type = $(this).data('type');
    active[type] ? active[type].call(this) : '';
  });

    importFiles({
        id:'#importData',
        url:'/achieveMaintain-api/input_excel',
        callback: function (result) {

          if(result.data != null && result.data.code=="1"){
              layer.msg('数据导入失败!失败信息：'+result.data.message, {icon: 1});
          }else{
              layer.msg('数据导入成功!', {icon: 1});
              $('[lay-filter="formDemo"]').click();
          }

        }
    })
});