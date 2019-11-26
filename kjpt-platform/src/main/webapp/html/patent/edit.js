var file_readonly = false;


layui.use(['form', 'table', 'layer', 'laydate', 'upload'], function(){
    var form = layui.form;
    var $ = layui.$; 
    var laydate = layui.laydate;
  
    var $ = layui.jquery;
    var form = layui.form; 
 

  function getItemInitData(item) {
    var httpUrl = '/patentController/newInit';
    if (item && item.id) {
      httpUrl = '/patentController/load/' + item.id;
    }
    httpModule({
      url: httpUrl,
      type: 'GET',
      success: function(relData) {
        

        if (relData.code === '0') {
          // 给form表单赋初始值

          var data = relData.data;
          transToData(data, ['date','applicationDate','entryDate']);

          form.val('formMain', data);
          // 更新表单数据
          form.render();

          setRadioShow();

          var billDataID = data.id;

          setFileUpload({
            id: 'file-filter-options', // 附件上传作用域ID值 必传
            dataID: billDataID, // 用来查找当前单据下绑定的附件，没有则不查找
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
  setRadioShow();
  var variable = getQueryVariable();
  getItemInitData(variable);

	form.on('submit(newSubmit)', function(data) {

		httpModule({
			url: '/patentController/save',
			data: data.field,
			type: "POST",
			success: function(e) { 
				setDialogData(e); // 通知上层页面状态 - 弹窗中使用
				top.layer.closeAll(); // 关闭弹窗
			}
		});
		return false;
    });
    
 

    $("#radio1").on("click", function(e) { 
        setRadioShow();
    });

    function setRadioShow() {
      
      var val = '01';
      $("div[showWhere]").css('display','none');  
      if(file_readonly) {
        val = $("#applicationOfPatentTransformation").val();
      }
      else { 
        var rs = $("#radio1").find("input[type='radio']"); 
        $.each(rs, function(i, item){ 
            var el = $(item);
            if (el.prop('checked')) {
                val = el.val();
            }
        });
  
        if(val !== '03') {
          $("#licenseeProfit").val(0);
        }

        if(val !== '04') {
          $("#assignProfit").val(0);
        }
      }

      $("div[showWhere='" + val + "']").css('display',''); 

    }

        
    laydate.render({
      elem: '#date' //指定元素
      ,trigger: 'click'
    });
  
    laydate.render({
      elem: '#applicationDate' //指定元素
      ,trigger: 'click'
    });

    laydate.render({
        elem: '#entryDate' //指定元素
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
  
  