var file_readonly = false;


layui.use(['form', 'table', 'layer', 'laydate', 'upload'], function(){
    var form = layui.form;
    var $ = layui.$; 
    var laydate = layui.laydate;
  
    var $ = layui.jquery;
    var form = layui.form; 
 

  function getItemInitData(item) {
    var httpUrl = '/ComputerSoftware/newInit';
    if (item && item.id) {
      httpUrl = '/ComputerSoftware/load/' + item.id;
    }
    httpModule({
      url: httpUrl,
      type: 'GET',
      success: function(relData) {
        if (relData.code === '0') {
          // 给form表单赋初始值

          var data = relData.data;
          transToData(data, ['recordDate','developFinishDate','entryTime']);

          form.val('formMain', data);
          // 更新表单数据
          form.render();
  
          setFileUpload({
            id: 'file-filter-options', // 附件上传作用域ID值 必传
            dataID: data.id, // 用来查找当前单据下绑定的附件，没有则不查找
            readonly : file_readonly,
            callback: function (tableData, type) {
              /* callback 表格数据每次变更时的回调，返回表格数据与操作类型
                * type 触发变更的类型 目前只有 delete | upload
              */
              var files = $.map(tableData, function(item) { return item.id});
              $("#files").val(files.join(','));
            }
          });


        }
      }
    });
  }
  
	// 获取地址栏传递过来的参数
  var variable = getQueryVariable();
  getItemInitData(variable);

	form.on('submit(newSubmit)', function(data) {
		httpModule({
			url: '/ComputerSoftware/save',
			data: data.field,
			type: "POST",
			success: function(e) {
				setDialogData(e); // 通知上层页面状态 - 弹窗中使用
				top.layer.closeAll(); // 关闭弹窗
			}
		});
		return false;
    })
    
  
    laydate.render({
      elem: '#recordDate' //指定元素
      ,trigger: 'click'
    });
  
    laydate.render({
      elem: '#developFinishDate' //指定元素
      ,trigger: 'click'
    });

    laydate.render({
        elem: '#entryTime' //指定元素
        ,trigger: 'click'
      });
          
    
    function transToData(data, fields) {
      $.each(fields, function(index, f){
         
        if(data[f]) {
          try {
            data[f] = (new Date(data[f])).format('yyyy-MM-dd');
          }
          catch (e) {
            
          }
        }  
      });
    }
       

  });
  
  