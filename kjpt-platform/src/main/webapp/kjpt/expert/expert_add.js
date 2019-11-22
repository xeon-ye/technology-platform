layui.config({
    base: '/layuiadmin/lib/extend/' //静态资源所在路径
}).extend({
    enhanceform: 'enhanceform'
}).use(['form', 'jquery','laydate','enhanceform','formSelects'], function(){
    var $ = layui.jquery,form = layui.form,laydate = layui.laydate,enhanceForm = layui.enhanceform,formSelects=layui.formSelects;
    var achieveName=[],projectName=[],patentName=[],rewardName=[],headPic=''
    var enhance = new enhanceForm({
        elem: '#formPlatform' //表单选择器
    });
    /*性别*/
    createElement("ROOT_KJPT_XB","sex","radio","sex")
    /*状态*/
    createElement("ROOT_UNIVERSAL_QYZT","useStatus","radio","useStatus")
    /*学历*/
    createElement("ROOT_KJPT_XL","education","option","education")
    /*职称*/
    createElement("ROOT_KJPT_ZWJB","title","option","title")
    /*出生年*/
    laydate.render({
        elem: '#appDate', //指定元素
        type:"year"
    });
    /*动态生成元素*/
    function createElement(code,id,element,name) {
        httpModule({
            url: "/sysDictionary-api/getChildsListByCode/"+code,
            type: 'GET',
            success: function(relData) {
                if (relData.success === true) {
                    relData.data.map(function(item){
                        if(element=="option"){
                            $("#"+id).append("<option value='"+item.code+"' name='"+name+"'>"+item.name+"</option>")
                        }else if(element=="radio"){
                            $("#"+id).append('<input type="radio" name="'+name+'" value="'+item.numValue+'" title="'+item.name+'">')
                        }
                    });
                    form.render()
                }
            }
        });
    }
    /*领域*/
    httpModule({
        url: "/techFamily-api/getTreeList",
        type: 'GET',
        success: function(relData) {
            relData.children.map(function (item,index) {
                item.children.map(function (items,i) {
                    delete items.children
                })
            })
            formSelects.data('technicalField', 'local', { arr: relData.children });
            formSelects.btns('technicalField', ['remove']);
        }
    });
    /*所在单位*/
    httpModule({
        url: "/unit-api/getTreeList",
        type: 'GET',
        success: function(relData) {
            formSelects.data('belongUnit', 'local', { arr: relData.children });
            formSelects.btns('belongUnit', ['remove']);
        }
    });
    // 获取地址栏传递过来的参数
    var variable = getQueryVariable();
    /*判断id，回显*/
    if(variable!=null){
        httpModule({
            url: '/expert-api/get/'+variable.id,
            type: 'GET',
            success: function(relData) {
                if (relData.success === true) {
                    // 给form表单赋初始值
                    form.val('formPlatform', relData.data);
                    formSelects.value('belongUnit', [relData.data.belongUnit]);
                    formSelects.value('technicalField', relData.data.technicalField.split(','));
                    achieveName=JSON.parse(relData.data.zjkAchievementJsonList)
                    patentName=JSON.parse(relData.data.zjkPatentJsonList)
                    projectName=JSON.parse(relData.data.zjkProjectJsonList)
                    rewardName=JSON.parse(relData.data.zjkRewardJsonList)
                    achieveName.map(function (item, index) {
                        var html='<li><span>'+item.achieveName+'</span><i class="layui-icon layui-unselect layui-tab-close closeAchievement">ဆ</i></li>'
                        $(".zjkAchievementJsonList ul li.expert-li-last").before(html)
                    })
                    $(".closeAchievement").click(function () {
                        var num=$(this).parent('li').index()
                        $(this).parent('li').remove()
                        achieveName.splice(num,1)
                    })
                    projectName.map(function (item, index) {
                        var html='<li><span>'+item.projectName+'</span><i class="layui-icon layui-unselect layui-tab-close closeProject">ဆ</i></li>'
                        $(".zjkProjectJsonList ul li.expert-li-last").before(html)
                    })
                    $(".closeProject").click(function () {
                        var num=$(this).parent('li').index()
                        $(this).parent('li').remove()
                        projectName.splice(num,1)
                    })
                    patentName.map(function (item, index) {
                        var html='<li><span>'+item.patentName+'</span><i class="layui-icon layui-unselect layui-tab-close closePatent">ဆ</i></li>'
                        $(".zjkPatentJsonList ul li.expert-li-last").before(html)
                    })
                    $(".closePatent").click(function () {
                        var num=$(this).parent('li').index()
                        $(this).parent('li').remove()
                        patentName.splice(num,1)
                    })
                    rewardName.map(function (item, index) {
                        var html='<li><span>'+item.rewarkLevel+'</span><i class="layui-icon layui-unselect layui-tab-close closeReward">ဆ</i></li>'
                        $(".zjkRewardJsonList ul li.expert-li-last").before(html)
                    })
                    $(".closeReward").click(function () {
                        var num=$(this).parent('li').index()
                        $(this).parent('li').remove()
                        rewardName.splice(num,1)
                    })
                    form.render()
                }
            }
        });
    }

    var $ = layui.$, active = {
        addAchievements:function () {
            top.layer.open({
                type: 2,
                title: "添加成果",
                content: "/kjpt/expert/achievements_add.html?id="+variable.id, // 全局弹窗页面地址
                btn: null,
                area: ['50%', '60%'],
                end: function(lay, index) {
                    // 弹窗销毁时获取弹窗返回数据
                    achieveName = getDialogData('dialog-data');
                    if (achieveName) {
                        achieveName.map(function (item,index) {
                            var html='<li><span>'+item.achieveName+'</span><i class="layui-icon layui-unselect layui-tab-close closeAchievement">ဆ</i></li>'
                            $(".zjkAchievementJsonList ul li.expert-li-last").before(html)
                        })
                    }
                    $(".closeAchievement").click(function () {
                        var num=$(this).parent('li').index()
                        $(this).parent('li').remove()
                        achieveName.splice(num,1)
                    })
                }
            });
        },
        addProject:function(){
            top.layer.open({
                type: 2,
                title: "添加项目",
                content: "/kjpt/expert/project_add.html?id="+variable.id, // 全局弹窗页面地址
                btn: null,
                area: ['50%', '60%'],
                end: function(lay, index) {
                    // 弹窗销毁时获取弹窗返回数据
                    projectName = getDialogData('dialog-data');
                    if (projectName) {
                        projectName.map(function (item,index) {
                            var html='<li><span>'+item.projectName+'</span><i class="layui-icon layui-unselect layui-tab-close closeProject">ဆ</i></li>'
                            $(".zjkProjectJsonList ul li.expert-li-last").before(html)
                        })
                    }
                    $(".closeProject").click(function () {
                        var num=$(this).parent('li').index()
                        $(this).parent('li').remove()
                        projectName.splice(num,1)
                    })
                }
            });
        },
        addPatent:function(){
            top.layer.open({
                type: 2,
                title: "添加专利",
                content: "/kjpt/expert/patent_add.html?id="+variable.id, // 全局弹窗页面地址
                btn: null,
                area: ['50%', '60%'],
                end: function(lay, index) {
                    // 弹窗销毁时获取弹窗返回数据
                    patentName = getDialogData('dialog-data');
                    if (patentName) {
                        patentName.map(function (item,index) {
                            var html='<li><span>'+item.patentName+'</span><i class="layui-icon layui-unselect layui-tab-close closePatent">ဆ</i></li>'
                            $(".zjkPatentJsonList ul li.expert-li-last").before(html)
                        })
                    }
                    $(".closePatent").click(function () {
                        var num=$(this).parent('li').index()
                        $(this).parent('li').remove()
                        patentName.splice(num,1)
                    })
                }
            });
        },
        addReward:function(){
            top.layer.open({
                type: 2,
                title: "添加奖励信息",
                content: "/kjpt/expert/reward_add.html?id="+variable.id, // 全局弹窗页面地址
                btn: null,
                area: ['50%', '60%'],
                end: function(lay, index) {
                    // 弹窗销毁时获取弹窗返回数据
                    rewardName = getDialogData('dialog-data');
                    if (rewardName) {
                        rewardName.map(function (item,index) {
                            var html='<li><span>'+item.rewarkLevel+'</span><i class="layui-icon layui-unselect layui-tab-close closeReward">ဆ</i></li>'
                            $(".zjkRewardJsonList ul li.expert-li-last").before(html)
                        })
                    }
                    $(".closeReward").click(function () {
                        var num=$(this).parent('li').index()
                        $(this).parent('li').remove()
                        rewardName.splice(num,1)
                    })
                }
            });
        },
        close:function () {
            /*模拟关闭标签页事件*/
            closeTabsPage();
        }
    }
    $('.layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    // 绑定图片上传组件
    setImagesUpload({
        id: '#imgFileUpload', // 图片作用域的ID
        callback: function (data, type) {
            console.log(data)
            // 图片上传回调函数
            // data 为图片上传成功后的数据， type为当前操作类型
            // 如果type === 'delete'  data则为null
        }
    });
    /*保存*/
    form.on('submit(newSubmit)', function(data) {
        //console.log(data.field)
        if(data.field.sex==undefined){
            layer.msg("性别必为填项不能为空！", {icon: 2});
            return false
        }
        if(data.field.belongUnit==undefined){
            layer.msg("所在单位必为选项不能为空！", {icon: 2});
            return false
        }
        if(formSelects.value('technicalField')){
            var technicalFieldName=''
            formSelects.value('technicalField').map(function (item, index) {
                technicalFieldName+=item.name+','
            })
            data.field.technicalFieldName=technicalFieldName.substring(0,technicalFieldName.length-1)
        }
        data.field.headPic=headPic
        data.field.zjkAchievementJsonList=JSON.stringify(achieveName)
        data.field.zjkProjectJsonList=JSON.stringify(projectName)
        data.field.zjkPatentJsonList=JSON.stringify(patentName)
        data.field.zjkRewardJsonList=JSON.stringify(rewardName)
        httpModule({
            url: '/expert-api/save',
            data: data.field,
            type: "POST",
            success: function(e) {
                if(e.success){
                    layer.msg('保存成功!', {icon: 1});
                    closeTabsPage();
                }
            }
        });
        return false;
    })

});