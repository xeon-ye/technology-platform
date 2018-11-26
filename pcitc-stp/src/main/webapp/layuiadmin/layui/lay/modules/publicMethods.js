/**
 * Created on 2018年8月14日11点05分
 * 方法
 */
layui.define(['jquery','form','table','laydate'],
    function(exports) {
        var $ = layui.jquery,form=layui.form,table=layui.table,laydate=layui.laydate;
        var param=JSON.parse(window.localStorage.getItem("param"));
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
                if (params && typeof params == "object") {
                    params = publicMet.deleteEmptyProp(params);
                }
                $.ajax({
                    type: 'post',
                    async: false,
                    url: url,
                    data: params,
                    dataType: 'json',
                    headers: headers,
                    success: function (data, status) {
                        result = data;
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
                    shadeClose: true,
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
            openBaseWinEight:function(title,temUrl) {
               layer.open({
                   title:title,
                   skin: 'layui-layer-lan',
                   shadeClose: true,
                   type: 2,
                   fixed: false,
                   //若使用小窗口形式，则修改 maxmin 值为 true，则注释掉area:[100%,100%]属性,同时设置area: ['900px', '450px']
                   maxmin: false,
                   //若直接弹出页面 ，则修改 area;[100%,100%]，同时设置 maxmin 为false
                   area: ['800px', '500px'],
                   content:  temUrl
               });
            },
            openBaseWinSix:function(title,temUrl) {
                layer.open({
                    title:title,
                    skin: 'layui-layer-lan',
                    shadeClose: true,
                    type: 2,
                    fixed: false,
                    //若使用小窗口形式，则修改 maxmin 值为 true，则注释掉area:[100%,100%]属性,同时设置area: ['900px', '450px']
                    maxmin: false,
                    //若直接弹出页面 ，则修改 area;[100%,100%]，同时设置 maxmin 为false
                    area: ['600px', '400px'],
                    content:  temUrl
                });
            },
            openBaseWinFull:function(title,temUrl) {
                layer.open({
                    title:title,
                    skin: 'layui-layer-lan',
                    shadeClose: true,
                    type: 2,
                    fixed: false,
                    //若使用小窗口形式，则修改 maxmin 值为 true，则注释掉area:[100%,100%]属性,同时设置area: ['900px', '450px']
                    maxmin: false,
                    //若直接弹出页面 ，则修改 area;[100%,100%]，同时设置 maxmin 为false
                    area: ['100%', '100%'],
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
                var number=trParam.number;
                var tbody=$("#"+trParam.id+" .layui-table-body table tbody");
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
                    var width=$("#"+trParam.id+" .layui-table-header table th").eq(i).outerWidth(true);
                    tableTdHtml+="<td style='width: "+width+"px'><input style='width: 90%;margin-left: 5px;' type='text'></td>";
                }
                $(tbody).find("tr:last").append(tableTdHtml);
                for(var i=0;i<number;i++){
                    if(i==0){
                        $(tbody).find("tr:last td").eq(i).html("<div class='layui-table-cell laytable-cell-1-0 laytable-cell-checkbox' style='padding-right: 4px;'><input type='checkbox' lay-skin='primary'></div>")
                    }else if(i==1){
                        $(tbody).find("tr:last td").eq(i).css("text-align","center").html("<div style='padding-right: 4px;'>"+num+"</div>");
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
                                console.log(columnValueArr)
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
                            $(tbody).find("tr:last td").eq(columnNum).html(certTypeStr)
                        }else if(columnElement=="laydate"){
                            $(tbody).find("tr:last td").eq(columnNum).html("<div class='layui-table-cell'><input type='text' class='layui-input datetime inputVal' placeholder='请选择日期' style='width:92%'></div>");
                        }else  if(columnElement=="a"){
                            $(tbody).find("tr:last td").eq(columnNum).html(columnValue)
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
                        trigger : 'click'
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
            tableRender:function (elem,id,url,toolbar,cols,done) {
                if(toolbar){
                    table.render({
                        elem: '#'+elem //表格容器
                        , url:url //请求的url地址
                        , limit: param.selfRownum //每页默认显示的数量
                        , id: id
                        ,method:"POST"
                        , height: commonDislodgeTable()
                        , page: {
                            count: 500 //数据总数，从服务端得到
                            , groups: 5
                            , limits: [param.selfRownum, 30, 45, 60]
                            , layout: ['count', 'limit', 'prev', 'page', 'next', 'skip'] //自定义分页布局
                            , first: '首页' //不显示首页
                            , last: '尾页' //不显示尾页
                            , theme: '#0F9EE0'
                        }
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
                        , limit: param.selfRownum //每页默认显示的数量
                        , id: id
                        ,method:"POST"
                        , height: commonDislodgeTable()
                        , page: {
                            count: 500 //数据总数，从服务端得到
                            , groups: 5
                            , limits: [param.selfRownum, 30, 45, 60]
                            , layout: ['count', 'limit', 'prev', 'page', 'next', 'skip'] //自定义分页布局
                            , first: '首页' //不显示首页
                            , last: '尾页' //不显示尾页
                            , theme: '#0F9EE0'
                        }
                        ,defaultToolbar:['filter','exports','print']
                        , cols: [cols]
                        , done: done
                    });
                }
            },
        };
        /**
         * 接口输出
         */
        exports('publicMethods',publicMet);
    });