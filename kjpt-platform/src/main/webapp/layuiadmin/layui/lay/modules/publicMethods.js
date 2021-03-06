/**
 * Created on 2018年8月14日11点05分
 * 方法
 */
layui.define(['jquery','form','table','laydate'],
    function(exports) {
        var $ = layui.jquery,form=layui.form,table=layui.table,laydate=layui.laydate;
        var param1=JSON.parse(window.localStorage.getItem("param"));
        var publicMet={
            //递归删除空属性防止把null变成空值
            deleteEmptyProp:function(obj) {
                for (var a in obj) {
                    if (typeof (obj[a]) == "object" && obj[a] != null) {
                        deleteEmptyProp(obj[a]);
                    }
                    else {
                        if (!obj[a]) {
                            delete obj[a];
                        }
                    }
                }
                return obj;
            },
            /**
             * ajax请求
             * @param {String} url 请求地址
             * @param {String} params 请求参数
             * @param {String} callback 状态
             */
            ajaxPost:function(url, params, callback) {
                var result = null;
                var headers = {
                    //'Content-Type':'application/json',
                    //'access-token':getCookie("token")
                };
                $.ajax({
                    type: 'post',
                    async: false,
                    url: url,
                    data: params,
                    dataType: 'json',
                    headers: headers,
                    beforeSend: function (XMLHttpRequest) {
                    	XMLHttpRequest.setRequestHeader("client_req_type", "ajaxPost");
                    },
                    success: function (data, status) {
                        result = data;
                        if (data && data.code && data.code == '401') {
                        	//layer.alert(data.message, {title : '提示'},function(){
                        		 window.open(data.data,"_top");
                                 return false;
                        	//});
                        }
                        if (data && data.code && data.code == '101') {
                            console.log("操作失败，请刷新重试，具体错误：" + data.message);
                            return false;
                        }
                        if (callback) {
                            callback.call(this, data, status);
                        }
                    },
                    error: function (err, err1, err2) {
                        console.log("ajaxPost发生异常，请仔细检查请求url是否正确");
                    }
                });
                return result;
            },
            /**
             * ajax请求
             * @param {String} url 请求地址
             * @param {String} params 请求参数
             * @param {String} callback 状态
             */
            ajaxAsyncPost:function(url, params, callback) {
                var headers = {};
                $.ajax({
                    type: 'post',
                    async: true,
                    url: url,
                    data: params,
                    dataType: 'json',
                    headers: headers,
                    beforeSend: function (XMLHttpRequest) {
                    	XMLHttpRequest.setRequestHeader("client_req_type", "ajaxAsyncPost");
                    },
                    success: function (data, status) {
                    	if (data && data.code && data.code == '401') {
                    		//layer.alert(data.message, {title : '提示'},function(){
                       		 	window.open(data.data,"_top");
                                return false;
                    		//});
                        }
                        if (data && data.code && data.code == '101') {
                            console.log("操作失败，请刷新重试，具体错误：" + data.message);
                            return false;
                        }
                        if (callback) {
                            callback.call(this, data, status);
                        }
                    },
                    error: function (err, err1, err2) {
                        console.log("ajaxPost发生异常，请仔细检查请求url是否正确");
                    }
                });
            },
            /**
             * ajax请求
             * @param {String} url 请求地址
             * @param {String} params 请求参数
             * @param {String} callback 状态
             */
            ajaxGet:function(url, params, callback) {
                var result = null;
                var headers = {
                    //'Content-Type':'application/json',
                    //'access-token':getCookie("token")
                };
                if (params && typeof params == "object") {
                    params = publicMet.deleteEmptyProp(params);
                }
                $.ajax({
                    type: 'get',
                    async: false,
                    url: url,
                    data: params,
                    dataType: 'json',
                    headers: headers,
                    beforeSend: function (XMLHttpRequest) {
                    	XMLHttpRequest.setRequestHeader("client_req_type", "ajaxGet");
                    },
                    success: function (data, status) {
                        result = data;
                        if (data && data.code && data.code == '401') {
                        	layer.alert("登录超时，请重新登录！", {title : '提示'},function(){
                       		 	window.open("/login","_top");
                                return false;
                    		});
                        }
                        if (data && data.code && data.code == '101') {
                            console.log("操作失败，请刷新重试，具体错误：" + data.message);
                            return false;
                        }
                        if (callback) {
                            callback.call(this, data, status);
                        }
                    },
                    error: function (err, err1, err2) {
                        console.log("ajaxGet发生异常，请仔细检查请求url是否正确");
                    }
                });
                return result;
            },
            /**
             * select 渲染
             * @param {String} title 标题
             * @param {String} temUrl 地址
             * @param {String} width 宽
             * @param {String} height 高
             */
            selectRendering:function(url,id){
                publicMet.ajaxPost(url, null, function (data, status) {
                    $.each(data,function(i,dt){
                        $("#"+id).append("<option value='"+data[i].code+"'>"+data[i].name+"</option>");
                    });
                })
            },
            /**
             * 封装弹出层 宽高自定义
             * @param {String} title 标题
             * @param {String} temUrl 地址
             * @param {String} width 宽
             * @param {String} height 高
             */
            openBaseWin:function(title,temUrl,width,height) {
                layer.open({
                    title:title,
                    skin: 'layui-layer-lan',
                    shadeClose: false,
                    type: 2,
                    fixed: false,
                    //若使用小窗口形式，则修改 maxmin 值为 true，则注释掉area:[100%,100%]属性,同时设置area: ['900px', '450px']
                    maxmin: false,
                    //若直接弹出页面 ，则修改 area;[100%,100%]，同时设置 maxmin 为false
                    area: [width, height],
                    content:  temUrl
                });
            },
            /**
             * 弹出层 宽800，高500框
             * @param title
             * @param temUrl
             * @returns
             */
            openBaseWinFull:function(title,temUrl) {
                layer.open({
                    title:title,
                    skin: 'layui-layer-lan',
                    shadeClose: true,
                    type: 2,
                    fixed: false,
                    //若使用小窗口形式，则修改 maxmin 值为 true，则注释掉area:[100%,100%]属性,同时设置area: ['900px', '450px']
                    maxmin : true,
                    area : [ '80%', '90%' ],
                    content:  temUrl
                });
            },
            openBaseWinFullPercentage:function(title,temUrl) {
                layer.open({
                    title:title,
                    skin: 'layui-layer-lan',
                    shadeClose: true,
                    type: 2,
                    fixed: false,
                    //若使用小窗口形式，则修改 maxmin 值为 true，则注释掉area:[100%,100%]属性,同时设置area: ['900px', '450px']
                    maxmin: false,
                    //若直接弹出页面 ，则修改 area;[100%,100%]，同时设置 maxmin 为false
                    area: ['80%', '60%'],
                    content:  temUrl
                });
            },
            openBaseWin76:function(title,temUrl) {
                layer.open({
                    title:title,
                    skin: 'layui-layer-lan',
                    shadeClose: true,
                    type: 2,
                    fixed: false,
                    //若使用小窗口形式，则修改 maxmin 值为 true，则注释掉area:[100%,100%]属性,同时设置area: ['900px', '450px']
                    maxmin : true,
                    area : [ '70%', '60%' ],
                    content:  temUrl
                });
            },
            openBaseWin67:function(title,temUrl) {
                layer.open({
                    title:title,
                    skin: 'layui-layer-lan',
                    shadeClose: true,
                    type: 2,
                    fixed: false,
                    //若使用小窗口形式，则修改 maxmin 值为 true，则注释掉area:[100%,100%]属性,同时设置area: ['900px', '450px']
                    maxmin : true,
                    area : [ '60%', '70%' ],
                    content:  temUrl
                });
            },
            openBaseWin56:function(title,temUrl) {
                layer.open({
                    title:title,
                    skin: 'layui-layer-lan',
                    shadeClose: true,
                    type: 2,
                    fixed: false,
                    //若使用小窗口形式，则修改 maxmin 值为 true，则注释掉area:[100%,100%]属性,同时设置area: ['900px', '450px']
                    maxmin : true,
                    area : [ '50%', '60%' ],
                    content:  temUrl
                });
            },
            /**
             * 生成一个表单
             * @param {String} name 表单的名称
             * @param {String} id 表单的ID
             * @param {String} action 表单的action
             * @param {String} method 表单的method
             */
            createForm:function(name, id, action, method, target) {
                var form = document.createElement('form');
                form.name = name;
                form.id = id;
                form.action = action;
                form.method = method;
                form.target = target;
                return form;
            },
            /**
             * 生成一个隐藏域
             * @param {String} name 隐藏域的名称
             * @param {String} value 隐藏域的值
             */
            createHiddenInput:function(name, value) {
                var field = document.createElement('input');
                field.type = 'hidden';
                field.name = name;
                field.id = name;
                field.value = value;
                return field;
            },
            insertValueToForm:function(formObj,formFieldName,fieldValue){
                formObj.appendChild(publicMet.createHiddenInput(formFieldName,fieldValue));
            },
            //获取当前日期
            getCurrentDate:function(){
                var date = new Date();
                var year = date.getFullYear();
                var month = date.getMonth()+1;
                var day = date.getDate();
                var hour = date.getHours();
                var minute = date.getMinutes();
                var second = date.getSeconds();
                return year+''+month+''+day+''+hour+''+minute+''+second;
            },
            //获取当期日期+时间
            getCurrentTime:function(){
            	var date = new Date();
                var seperator1 = "-";
                var seperator2 = ":";
                var month = date.getMonth() + 1;
                var strDate = date.getDate();
                if (month >= 1 && month <= 9) {
                    month = "0" + month;
                }
                if (strDate >= 0 && strDate <= 9) {
                    strDate = "0" + strDate;
                }
                var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
                        + " " + date.getHours() + seperator2 + date.getMinutes()
                        + seperator2 + date.getSeconds();
                return currentdate;
            },
            /*
             * 附件
             */
            viewAtt: function(dataId, flagId)
            {
                var param = {};
                param.file_ids_value = dataId;
                param.file_opt_flag = flagId;//文件配置ID
                param.file_edit_detail = "detail";//新增、修改用edit, 显示用detail
                var temUrl = "/sysFilePage/uploadFileTable?param=" + encodeURIComponent(JSON.stringify(param));
                publicMet.openBaseWin("文件上传列表查看", temUrl,"70%","50%");
            },
            /*
            * select 切换
            */
            selectTab:function (id,contentBox) {
                $("#"+id).off("change").on("change",function(){
                    var index = $(this).find("option:selected").index();
                    $(this).addClass("on").siblings().removeClass("on");
                    $("#"+contentBox +" .box").eq(index).addClass("active").siblings().removeClass("active");
                });
            },
            /*
            * 数据字典转换中文
            */
            dataDictionary:function (param) {
                var dataDictionaryCode=[],dataDictionaryName=[];
                publicMet.ajaxPost(param.url, null, function (data, status) {
                    $.each(data,function(i,dt){
                        dataDictionaryCode.push(data[i].code);
                        dataDictionaryName.push(data[i].name)
                    });
                });
                /*table列表显示字典*/
                $(param.element).find("tr").each(function (i,dt) {
                    var elementCode=param.data[i][param.field];
                    var index=dataDictionaryCode.indexOf(elementCode);
                    var elementName=dataDictionaryName[index];
                    if(index==-1){}else {
                        $(this).find("td").eq(param.column).find("div").text(elementName);
                    }
                });
                /*详情页面显示字典*/
                if($("#"+param.id).length>0){
                    var index=dataDictionaryCode.indexOf(param.data[param.field]);
                    var elementName=dataDictionaryName[index];
                    $("#"+param.id).val(elementName);
                }
            },
            /*
            * table手动添加tr
            *
            */
            addTableTr:function(trParam){
                /* 获取选中的th*/
                var classNs=$("#"+trParam.id+" .layui-table-header table tr th:eq(0)>div").attr("class");
                var oNumber=trParam.orderNumber==undefined ? true : trParam.orderNumber;
                $(".layui-none").remove();
                var number=trParam.number;
                var tbody=$("#"+trParam.id+" .layui-table-main table tbody");
                var num =  $(tbody).find("tr").length + 1;
                var tableHtml = "<tr data-index='"+(num-1)+"'></tr>";
                var tableTdHtml='';
                var column=[],element=[],columnEvent=[], name=[],value=[],url=[],parentCode=[],hide=[],valueId=[],disabled=[];
                $(tbody).append(tableHtml);
                /*判断是否有特殊的td*/
                if(trParam.specialElement.length>0){
                    $.each(trParam.specialElement,function(i,dt){
                        column.push(dt.column);
                        element.push(dt.element);
                        columnEvent.push(dt.event);
                        name.push(dt.name);
                        value.push(dt.value);
                        url.push(dt.url);
                        parentCode.push(dt.parentCode);
                        hide.push(dt.hide);
                        valueId.push(dt.valueId);
                        disabled.push(dt.disabled)
                    });
                }
                for(var i=0;i<number;i++){
                    var width=$("#"+trParam.id+" .layui-table-header table th:eq("+i+") div").outerWidth();
                    tableTdHtml+="<td style='width: "+width+"px'><input style='margin-left: 10px;' type='text'></td>";
                }
                $(tbody).find("tr:last").append(tableTdHtml);
                for(var i=0;i<number;i++){
                    if(i==0){
                        $(tbody).find("tr:last td").eq(i).html("<div class='"+classNs+"'><input type='checkbox' lay-skin='primary'></div>")
                    }else if(i==1 && oNumber==true){
                        $(tbody).find("tr:last td").eq(i).css("text-align","center").html("<div  class='layui-table-cell' style='width: 45px;'>"+num+"</div>");
                    }else if(column.indexOf(i)!=-1){
                        var columnIndex=column.indexOf(i);
                        var columnNum=column[columnIndex];
                        var columnElement=element[columnIndex];
                        var columnEventN=columnEvent[columnIndex];
                        var columnName=name[columnIndex];
                        var columnValue=value[columnIndex];
                        var columnUrl=url[columnIndex];
                        var columnCode=parentCode[columnIndex];
                        var columnHide=hide[columnIndex];
                        var columnValueId=valueId[columnIndex];
                        var columnDisabled=disabled[columnIndex];
                        if(columnElement=="file"){
                            if(columnHide==true){
                                $(tbody).find("tr:last td").eq(columnNum).addClass("layui-hide").html("<p class='file-name add-file' style='width: 100%;margin-left: 15px;' onclick='"+columnEventN+"'>"+columnName+"<input type='hidden' value='"+columnValue+"'></p>")
                            }else {
                                $(tbody).find("tr:last td").eq(columnNum).html("<p class='file-name add-file' style='width: 100%;margin-left: 15px;' onclick='"+columnEventN+"'>"+columnName+"<input type='hidden' value='"+columnValue+"'></p>")
                            }
                        }else if(columnElement=="input"){
                            if(columnHide==true){
                                $(tbody).find("tr:last td").eq(columnNum).addClass("layui-hide").html("<p class='file-name add-file' style='width: 100%;margin-left: 15px;'><input type='hidden' value=''></p>")
                            }else if(columnDisabled==true){
                                if(columnValue==undefined){
                                    columnValue=""
                                }
                                $(tbody).find("tr:last td").eq(columnNum).html("<input type='text'  disabled='disabled' value='"+columnValue+"'>")
                            }
                        }else if(columnElement=="select"){
                            var certTypeStr;
                            if(columnValue.length>0){
                                var columnValueArr=columnValue.split(",");
                                var columnValueIdArr=columnValueId.split(",");
                                // console.log(columnValueArr)
                                certTypeStr = "<select>";
                                $.each(columnValueArr, function(index) {
                                    if(index == 0){
                                        certTypeStr += "<option value='"+columnValueIdArr[index]+"' selected>"+columnValueArr[index]+"</option>";
                                    } else {
                                        certTypeStr += "<option value='"+columnValueIdArr[index]+"'>"+columnValueArr[index]+"</option>";
                                    }
                                });
                            }else  {
                                certTypeStr = "<select>";
                            }
                            certTypeStr += "</select>";
                            $(tbody).find("tr:last td").addClass("tdSelect").eq(columnNum).html(certTypeStr)
                        }else if(columnElement=="laydate"){
                            $(tbody).find("tr:last td").eq(columnNum).html("<input style='width: 52.5%;' type='text' class='layui-input datetime inputVal' placeholder='请选择日期'>");
                        }else  if(columnElement=="a"){
                            $(tbody).find("tr:last td").eq(columnNum).html(columnValue)
                        }else if(columnElement=="td"){
                            alert("td")
                        }
                    }
                }
                $(tbody).find("tr").click(function () {
                    $(this).siblings().removeClass("layui-table-click");
                    $(this).siblings().find("td").find(".layui-unselect").removeClass("layui-form-checked");
                    $(this).find("td").find(".layui-unselect").addClass("layui-form-checked");
                    $(this).addClass("layui-table-click");
                });
                publicMet.timeAdd();
                $(".layui-none").remove();
                form.render();

            },
            timeAdd:function(){
                lay('.datetime').each(function() {
                    laydate.render({
                        elem : this,
                        trigger : 'click',
                        value:new Date()
                    });
                });
            },
            batchDele:function (id) {
                if($("#"+id+" .layui-table-body table tr").find(".layui-form-checked").length==0){
                    layer.msg('请选择要删除的数据');
                }
                $("#"+id+" .layui-table-body table tr").each(function () {
                    if($(this).find(".layui-unselect").hasClass("layui-form-checked")){
                        $(this).remove();
                    }
                });
            },
            tableRender:function (elem,id,url,toolbar,cols,where,page,done) {
                // console.log("111");
                if(page==true){
                    var page={
                        count: 500 //数据总数，从服务端得到
                        , groups: 5
                        , limits: [15, 30, 45, 60]
                        // , layout: ['count', 'limit', 'prev', 'page', 'next', 'skip'] //自定义分页布局
                        , layout: ['prev', 'page', 'next', 'skip', 'count', 'limit'] //自定义分页布局
                        , first: '首页' //不显示首页
                        , last: '尾页' //不显示尾页
                        , theme: '#0F9EE0'
                    }
                }else {
                    var page=false;
                }
                if(toolbar){
                    table.render({
                        elem: '#'+elem //表格容器
                        , url:url //请求的url地址
                        , limit: 15 //每页默认显示的数量
                        , id: id
                        , where:where
                        ,method:"POST"
                        , height: commonDislodgeTable()
                        , page: page
                        ,toolbar:{
                            toolbar:"#test"
                        }
                        ,defaultToolbar:['filter','exports','print']
                        , cols: [cols]
                        , done: done
                    });
                }else {
                    // console.log("111");
                    table.render({
                        elem: '#'+elem //表格容器
                        , url:url //请求的url地址
                        , limit: 15 //每页默认显示的数量
                        , id: id
                        , where:where
                        ,method:"POST"
                        , height: commonDislodgeTable()
                        , page: page
                        ,defaultToolbar:['filter','exports','print']
                        , cols: [cols]
                        , done: done
                    });
                }
            },
            tableRenderH:function (elem,id,height,url,toolbar,cols,where,page,done) {
                if(page==true){
                    var page={
                        count: 500 //数据总数，从服务端得到
                        , groups: 5
                        , limits: [15, 30, 45, 60]
                        , layout: ['count', 'limit', 'prev', 'page', 'next', 'skip'] //自定义分页布局
                        , first: '首页' //不显示首页
                        , last: '尾页' //不显示尾页
                        , theme: '#0F9EE0'
                    }
                }else {
                    var page=false;
                }
                if(toolbar){
                    table.render({
                        elem: '#'+elem //表格容器
                        , url:url //请求的url地址
                        , limit: 15//每页默认显示的数量
                        , id: id
                        , where:where
                        ,method:"POST"
                        , height: height
                        , page: page
                        ,toolbar:{
                            toolbar:"#test"
                        }
                        ,defaultToolbar:['filter','exports','print']
                        , cols: [cols]
                        , done: done
                    });
                }else {
                    table.render({
                        elem: '#'+elem //表格容器
                        , url:url //请求的url地址
                        , limit: 15 //每页默认显示的数量
                        , id: id
                        , where:where
                        , height: height
                        ,method:"POST"
                        , page: page
                        ,defaultToolbar:['filter','exports','print']
                        , cols: [cols]
                        , done: done
                    });
                }
            },
            moduleConfigs:function (data) {
                var param=data;
                $.each(param, function( key, data ) {
                    var spareId=data.spareId;
                    if(spareId==null){
                        var classDiv;
                        if(data.name.length>7){
                            classDiv="layui-icon_p layui-icon_report";
                        }else if(data.name.length>6){
                            classDiv="layui-icon_p layui-icon_institute";
                        }else if(data.name.length<=6){
                            classDiv="layui-leader";
                        }
                        if(data.name=="待办督办"){
                            var html="<li class='"+data.class+"' data-power='"+data.power+"'>" +
                                "       <div class='layui-agency-left' style='background:"+data.color+"'>" +
                                "             <p>待办督办</p>"+
                                "              <ul>" +
                                "                   <li>" +
                                "                       <img src='/layuiadmin/layui/images/agencyOne.png' alt=''>" +
                                "                       <span class='layui-agency-left-span'>待办事项</span>" +
                                "                       <span class='layui-agency-span' id='agencyOne'>1</span>" +
                                "                   </li>" +
                                "                   <li>" +
                                "                       <img src='/layuiadmin/layui/images/agencyFour.png' alt=''>" +
                                "                       <span class='layui-agency-left-span'>任务督办</span>" +
                                "                       <span class='layui-agency-span' id='agencyFour'>1</span>" +
                                "                   </li>" +
                                "              </ul>"
                                "           <p>"+data.name+"</p>" +
                                "      </div></li>";
                        }else {
                            var html="<li class='item "+data.class+"' data-power='"+data.power+"'>" +
                                "       <div class='layui-text-icon "+classDiv+"' style='background:"+data.color+" url("+data.img+") center center no-repeat;' id='"+data.id+"'>" +
                                "           <p>"+data.name+"</p>" +
                                "           <img src='/layuiadmin/layui/images/level-close.png' class='dele'>"
                                "      </div></li>";
                        }
                        $("#moduleConfig").append(html);
                    }else {
                        if(data.spareId=="dragon_id"){
                            var html="<li class='"+data.class+"' data-power='"+data.power+"'>" +
                                "       <div class='layui-text-icon' style='background:"+data.color+" url("+data.img+") center center no-repeat;' id='"+data.id+"'>" +
                                "           <p>"+data.name+"</p>" +
                                "           <span id='"+data.spareId+"'><img class='loadingImg' src='/layuiadmin/layui/images/loadingImg04.gif'></span>" +
                                "      </div></li>";
                        }else {
                            var html="<li class='item "+data.class+"' data-power='"+data.power+"'>" +
                                "       <div class='layui-text-icon' style='background:"+data.color+" url("+data.img+") center center no-repeat;' id='"+data.id+"'>" +
                                "           <p>"+data.name+"</p>" +
                                "           <span id='"+data.spareId+"'><img class='loadingImg' src='/layuiadmin/layui/images/loadingImg04.gif'></span>" +
                                "           <img src='/layuiadmin/layui/images/level-close.png' class='dele'>"
                                "      </div></li>";

                        }
                        $("#moduleConfig").append(html);
                    }
                });
                var html="<li class='layui-col-md3'>" +
                    "           <div class='layui-col-md' id='layui-add'>" +
                    "               <div class='layui-add'>" +
                    "                   <img src='/layuiadmin/layui/images/icon-puls.png'/>" +
                    "               </div>" +
                    "           </div>" +
                    "      </li>";
                $("#moduleConfig").append(html);
                publicMet.deleTr();
            },
            moduleConfig:function (data) {
                var spareId=data.spareId;
                var classDiv;
                if(data.name.length==4){
                    classDiv="layui-leader";
                }else {

                }
                var colorList = ["#14ea28","#14eae7","#2314ea","#ea14bf","#ea1465",
                    "#ea6f14","#99490f","#8AC007","#CCC007","#FFAD5C"];
                for(var i=0;i<colorList.length;i++){
                    var colorIndex = Math.floor(Math.random()*colorList.length);
                    var color = colorList[colorIndex];
                }
                /*function getColorByRandom(colorList){
                    var colorIndex = Math.floor(Math.random()*colorList.length);
                    var color = colorList[colorIndex];
                    colorList.splice(colorIndex,1);
                    return color;
                }*/
                parent.parent.$("#moduleConfig #layui-add").parent().remove();
                parent.$(".layui-row-special ul #layui-add").parent().remove();
                if(data.power==""|| data.power==null || parseInt(data.power)>13){
                    if(spareId==null || spareId==""){
                        var html="<li class='"+data.class+"' data-power='"+data.power+"'>" +
                            "       <div class='layui-text-icon "+classDiv+"' style='background:"+color+" /*url("+data.img+") center center no-repeat;*/' id='"+data.id+"'>" +
                            "           <p>"+data.name+"</p>" +
                            "           <img src='/layuiadmin/layui/images/level-close.png' class='dele'>"
                            "      </div></li>";
                        publicMet.moduleConfigAdd(html);
                    }else {
                        var html="<li class='"+data.class+"' data-power='"+data.power+"'>" +
                            "       <div class='layui-text-icon' style='background:"+color+" /*url("+data.img+") center center no-repeat;*/' id='"+data.id+"'>" +
                            "           <p>"+data.name+"</p>" +
                            "           <span id='"+data.spareId+"'><img class='loadingImg' src='/layuiadmin/layui/images/loadingImg04.gif'></span>" +
                            "           <img src='/layuiadmin/layui/images/level-close.png' class='dele'>"
                            "      </div></li>";
                        publicMet.moduleConfigAdd(html);
                    }
                }else {
                    if(spareId==null || spareId==""){
                        var html="<li class='"+data.class+"' data-power='"+data.power+"'>" +
                            "       <div class='layui-text-icon "+classDiv+"' style='background:"+color+"/* url("+data.img+") center center no-repeat;*/' id='"+data.id+"'>" +
                            "           <p>"+data.name+"</p>" +
                            "           <img src='/layuiadmin/layui/images/level-close.png' class='dele'>"
                        "      </div></li>";
                        publicMet.moduleConfigAdd(html);
                    }else {
                        var html="<li class='"+data.class+"' data-power='"+data.power+"'>" +
                            "       <div class='layui-text-icon' style='background:"+color+"/* url("+data.img+") center center no-repeat;*/' id='"+data.id+"'>" +
                            "           <p>"+data.name+"</p>" +
                            "           <span id='"+data.spareId+"'><img class='loadingImg' src='/layuiadmin/layui/images/loadingImg04.gif'></span>" +
                            "           <img src='/layuiadmin/layui/images/level-close.png' class='dele'>"
                        "      </div></li>";
                        publicMet.moduleConfigAdd(html);

                    }
                }
                publicMet.deleTrP();
            },
            moduleConfigAdd:function(html){
                if(parent.$("#moduleConfig li").length<16){
                    parent.$("#moduleConfig").append(html);
                }else {
                    parent.$(".layui-row-special ul").append(html);
                }

            },
            deleTr:function(){
                $(".dele").click(function () {
                    $(this).parents("li").remove();
                    var classT=0;
                    if($("#moduleConfig li").eq(0).attr("class")=="layui-col-md6" && $("#moduleConfig li").eq(1).attr("class")=="layui-col-md6"){
                        $("#moduleConfig li").eq(1).css("margin-top","0px");
                    }
                    $("#moduleConfig li").each(function () {
                        var str=$(this).attr("class");
                        classT+=parseInt(str.charAt(str.length-1));
                        //alert(classT)
                        if(classT>12){
                            $("#moduleConfig li").eq($(this).index()).prevAll().css("margin-top","0px");
                            $("#moduleConfig li").eq($(this).index()).css("margin-top","20px");
                            $("#moduleConfig li").eq($(this).index()).nextAll().css("margin-top","20px");
                            return false;
                        }
                        //alert(classT);
                        //alert($(this).index())
                    });

                });
            },
            deleTrP:function(){
                parent.$(".dele").click(function () {
                    $(this).parents("li").remove();
                    var classT=0;
                    if($("#moduleConfig li").eq(0).attr("class")=="layui-col-md6" && $("#moduleConfig li").eq(1).attr("class")=="layui-col-md6"){
                        $("#moduleConfig li").eq(1).css("margin-top","0px");
                    }
                    $("#moduleConfig li").each(function () {
                        var str=$(this).attr("class");
                        classT+=parseInt(str.charAt(str.length-1));
                        //alert(classT)
                        if(classT>12){
                            $("#moduleConfig li").eq($(this).index()).prevAll().css("margin-top","0px");
                            $("#moduleConfig li").eq($(this).index()).css("margin-top","20px");
                            $("#moduleConfig li").eq($(this).index()).nextAll().css("margin-top","20px");
                            return false;
                        }
                        //alert(classT);
                        //alert($(this).index())
                    });

                });
            },
            /*随即生成多个tr*/
            createTable:function(startYear,endYear,id,number,str,edit){
                $("#"+id+" table tbody").empty();
                if(str!=''){
                    var yearIndex=str.split("#").length;
                }else {
                    var yearIndex=(endYear-startYear)+1;
                }
                var tdN='',tdNC;
                for(var n=0;n<number;n++){
                    if(edit!=null){
                        tdN+="<td class='td"+n+"'></td>";
                        tdNC+="<td class='td"+n+"'></td>";
                    }else {
                        if(n==1){
                            tdN+="<td><input class='td"+n+"' value=''  onkeyup=\"this.value=this.value.replace(/\D/g,'')\" onafterpaste=\"this.value=this.value.replace(/\D/g,'')\" type='number' lay-verify='validateNumber'/></td>";
                        }else {
                            tdN+="<td><input class='td"+n+"' value='0' onkeyup=\"this.value=this.value.replace(/\D/g,'')\" onafterpaste=\"this.value=this.value.replace(/\D/g,'')\"   type='number'/></td>";
                        }
                        tdNC+="<td class='td"+n+"'></td>";
                    }
                }
                for(var i=0;i<yearIndex;i++){
                    var tr="<tr><td>"+(parseInt(startYear)+parseInt(i))+"</td>" +tdN+ "<td></td></tr>";
                    $("#"+id+" table tbody").append(tr);
                }
                var trTotal="<tr><td>合计</td>" +tdNC+ "<td></td></tr>";
                $("#"+id+" table tbody").append(trTotal);
                if(str!=''){
                    var strArr=str.split("#");
                    var strArrC1=0,strArrC2=0,strArrC3=0;
                    $.each(strArr,function (i, val) {
                        var strArrC=strArr[i].split(",");
                        if(edit!=null){
                            $("#"+id+" table tbody tr:eq("+i+") td:eq(0)").html(strArrC[0] );
                            $("#"+id+" table tbody tr:eq("+i+") td:eq(1)").html((strArrC[1] == "null") ? 0 : strArrC[1]);
                            $("#"+id+" table tbody tr:eq("+i+") td:eq(2)").html((strArrC[2] == "null") ? 0 : strArrC[2]);
                            $("#"+id+" table tbody tr:eq("+i+") td:eq(3)").html((strArrC[3] == "null") ? 0 : strArrC[3]);
                        }else {
                            $("#"+id+" table tbody tr:eq("+i+") td:eq(1) input").val(strArrC[1]);
                            $("#"+id+" table tbody tr:eq("+i+") td:eq(2) input").val(strArrC[2]);
                            $("#"+id+" table tbody tr:eq("+i+") td:eq(3)").html(strArrC[3]);
                        }
                        strArrC1+=parseFloat((strArrC[1] == "null") ? 0 : strArrC[1]);
                        strArrC2+=parseFloat((strArrC[2] == "null") ? 0 : strArrC[2]);
                        strArrC3+=parseFloat((strArrC[3] == "null") ? 0 : strArrC[3]);
                    });
                    $("#"+id+" table tbody tr:last td:eq(1)").text(strArrC1);
                    $("#"+id+" table tbody tr:last td:eq(2)").text(strArrC2);
                    $("#"+id+" table tbody tr:last td:eq(3)").text(strArrC3);
                }
                /*计算*/
                $("#"+id+" table tbody tr input").change(function () {
                    /*同行相加*/
                    var colleagueInputL=$(this).parents("tr").find("input").length;
                    var trInputC=0;
                    for(var i=0;i<colleagueInputL;i++){
                        if($(this).parents("tr").find("input").eq(i).val()==''){
                            trInputC+=0;
                        }else {
                            trInputC+=parseFloat($(this).parents("tr").find("input").eq(i).val());
                        }
                    }
                    $(this).parents("tr").find("td:last").text(trInputC);
                    /*同列相加*/
                    var columnClass=$(this).attr("class");
                    var columnTrL=$("#"+id+" table tbody tr").length;
                    var columnC=0;
                    for(var j=0;j<columnTrL-1;j++){
                        var columnVal=$("#"+id+" table tbody tr").eq(j).find("."+columnClass).val();
                        if(columnVal==''){
                            columnC+=0;
                        }else {
                            columnC+=parseFloat(columnVal);
                        }
                    }
                    $("#"+id+" table tbody tr:last").find("."+columnClass).text(columnC);
                    /*总行*/
                    var totalInputL=$(this).parents("tbody").find("input").length;
                    var totalC=0;
                    for(var g=0;g<totalInputL;g++){
                        if($(this).parents("tbody").find("input").eq(g).val()==''){
                            totalC+=0;
                        }else {
                            totalC+=parseFloat($(this).parents("tbody").find("input").eq(g).val());
                        }
                    }
                    $("#"+id+" table tbody tr:last td:last").text(totalC);
                });
            },
            /*获取值*/
            getVal:function (id,number) {
                /*获取值*/
                var columnTrL=$("#"+id+" table tbody tr").length;
                var columnSC='';
                $("#"+id+" table tbody tr").each(function (i) {
                    if(i==columnTrL-1){
                        return;
                    }
                    var columnS='';
                    for(var k=0;k<number;k++){
                        var tdElement=$("#"+id+" table tbody tr:eq("+i+") td").eq(k);
                        if($(tdElement).find("input").length>0){
                            if($(tdElement).find("input").val()==''){
                                columnS+=null;
                            }else {
                                columnS+=$(tdElement).find("input").val()+",";
                            }
                        }else {
                            columnS+=$(tdElement).html()+",";
                        }
                    }
                    columnSC+=columnS.substring(0,columnS.length-1)+"#"
                });
                columnSC=columnSC.substring(0,columnSC.length-1);
                return columnSC;
            },
            isPositiveInteger:function(s)
            {//是否为正整数
                var re = /^[1-9][0-9]+$/ ;
                return re.test(s)
            }

        };
        
        
        function formVerify(){
        	form.verify({
                validateNumber: function (value, item) {
                	if(isInteger(value)==false)
                	{
                		 return '请输入整数';
                	}else  if (value <= 0 || value > 2000) {
                        return '请填写正确的值';
                    }
                },
                specialCharacters:function (value, item) {
                    if(value.indexOf("#")>=0 || value.indexOf("|")>=0){
                        return "不能填写#与|两个字符。";
                    }
                }
            });
        }
        function isInteger(str) {
        	return /^\d+$/.test(str);
        }
        /**
         * 接口输出
         */
        exports('publicMethods',publicMet);
    });