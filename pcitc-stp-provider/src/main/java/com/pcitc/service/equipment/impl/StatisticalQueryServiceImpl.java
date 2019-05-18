package com.pcitc.service.equipment.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pcitc.base.common.Constant;
import com.pcitc.base.common.LayuiTableData;
import com.pcitc.base.common.LayuiTableParam;
import com.pcitc.base.stp.equipment.SreProject;
import com.pcitc.base.stp.equipment.SrePurchase;
import com.pcitc.mapper.equipment.SreProjectMapper;
import com.pcitc.mapper.equipment.SrePurchaseMapper;
import com.pcitc.service.equipment.StatisticalQueryService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("statisticalQueryService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)

public class StatisticalQueryServiceImpl implements StatisticalQueryService {
    private final static Logger logger = LoggerFactory.getLogger(PurchaseServiceImpl.class);
    @Autowired
    private SrePurchaseMapper srePurchaseMapper;
    @Autowired
    private SreProjectMapper sreProjectMapper;

    private String getTableParam(LayuiTableParam param,String paramName,String defaultstr)
    {
        String resault="";
        Object object=param.getParam().get(paramName);
        if(object!=null)
        {
            resault=(String)object;
        }
        return resault;
    }


    public LayuiTableData getPurchaseEquipmenList(LayuiTableParam param)throws Exception
    {
        List<SrePurchase> list = new ArrayList<SrePurchase>();
        //每页显示条数
        int pageSize = param.getLimit();
        //从第多少条开始
        int pageStart = (param.getPage()-1)*pageSize;
        //当前是第几页
        int pageNum = pageStart/pageSize + 1;
        // 1、设置分页信息，包括当前页数和每页显示的总计数
        PageHelper.startPage(pageNum, pageSize);
        String purchaseName=getTableParam(param,"purchaseName","");
        String departName=getTableParam(param,"departName","");
        String departCode = getTableParam(param, "departCode", "");
        String stage=getTableParam(param,"stage","");
        String state=getTableParam(param,"state","");
        String proposerName=getTableParam(param,"proposerName","");
        String parentUnitPathNames=getTableParam(param,"parentUnitPathNames","");
        String parentUnitPathIds=getTableParam(param,"parentUnitPathIds","");
        String createDate=getTableParam(param,"createDate","");
        String isKJBPerson = getTableParam(param, "isKJBPerson", "");
        Map map=new HashMap();
        PageInfo<SrePurchase> pageInfo = null;
        map.put("purchaseName", purchaseName);
        map.put("departName", departName);
        map.put("stage", stage);
        map.put("state", state);
        map.put("proposerName", proposerName);
        map.put("parentUnitPathNames", parentUnitPathNames);
        map.put("parentUnitPathIds", parentUnitPathIds);
        map.put("createDate", createDate);

        System.out.println(">>>>>>>>applyDepartCode="+departCode);
        StringBuffer applyUnitCodeStr=new StringBuffer();
        if(!departCode.equals("")) {
            applyUnitCodeStr.append(" ("); String arr[]=departCode.split(",");
            for(int i=0;i<arr.length;i++) {
                if(i>0) {
                    applyUnitCodeStr.append(" OR FIND_IN_SET('"+arr[i]
                            +"', t.`depart_code`)");
                }else {
                    applyUnitCodeStr.append("FIND_IN_SET('"+arr[i]+"', t.`depart_code`)");
                }
            }
            applyUnitCodeStr.append(" )");
        }

        map.put("sqlStr", applyUnitCodeStr.toString());
        list = srePurchaseMapper.getList(map);
        if(isKJBPerson.equals("true")){
            pageInfo = new PageInfo<SrePurchase>(list);
        }else{
             list = new ArrayList<SrePurchase>();
            pageInfo = new PageInfo<SrePurchase>(list);
            System.err.println("此用户不是科技部人员");
        }
        System.out.println(">>>>>>>>>查询分页结果"+pageInfo.getList().size());
        LayuiTableData data = new LayuiTableData();
        data.setData(pageInfo.getList());
        Long total = pageInfo.getTotal();
        data.setCount(total.intValue());
        return data;
    }

    @Override
    public LayuiTableData getProjectEquipmentList(LayuiTableParam param) {
        JSONObject parmamss = JSONObject.parseObject(JSONObject.toJSONString(param));
        logger.info("============参数：" + parmamss.toString());
        List<SreProject> list = new ArrayList<SreProject>();
        PageInfo<SreProject> pageInfo = new PageInfo<SreProject>(list);
        //每页显示条数
        int pageSize = param.getLimit();
        //从第多少条开始
        int pageStart = (param.getPage()-1)*pageSize;
        //当前是第几页
        int pageNum = pageStart/pageSize + 1;
        // 1、设置分页信息，包括当前页数和每页显示的总计数
        PageHelper.startPage(pageNum, pageSize);

        String name=getTableParam(param,"name","");
        String equipmentIds=getTableParam(param,"equipmentIds","");
        String auditStatus=getTableParam(param,"auditStatus","");
        String setupYear=getTableParam(param,"setupYear","");
        String keyWord=getTableParam(param,"keyWord","");
        String leadUnitName=getTableParam(param,"leadUnitName","");
        String leadUnitCode=getTableParam(param,"leadUnitCode","");
        String applyUnitName=getTableParam(param,"applyUnitName","");
        String applyUnitCode=getTableParam(param,"applyUnitCode","");
        String joinUnitName=getTableParam(param,"joinUnitName","");
        String joinUnitCode=getTableParam(param,"joinUnitCode","");
        String taskWriteUsersIds=getTableParam(param,"taskWriteUsersIds","");
        String startMoney = getTableParam(param, "startMoney", "");
        String endMoney = getTableParam(param, "endMoney", "");


        String createUserId=getTableParam(param,"createUserId","");
        String createUserName=getTableParam(param,"createUserName","");
        String professionalFieldCode=getTableParam(param,"professionalFieldCode","");
        String professionalFieldName=getTableParam(param,"professionalFieldName","");
        String setupId=getTableParam(param,"setupId","");
        String taskId=getTableParam(param,"taskId","");
        String isKJBPerson = getTableParam(param, "isKJBPerson", "");

        String belongDepartmentName=getTableParam(param,"belongDepartmentName","");
        String professionalDepartName=getTableParam(param,"professionalDepartName","");

        String unitPathIds=getTableParam(param,"unitPathIds","");
        String parentUnitPathIds=getTableParam(param,"parentUnitPathIds","");


        Map map=new HashMap();
        map.put("belongDepartmentName", belongDepartmentName);
        map.put("professionalDepartName", professionalDepartName);
        map.put("name", name);
        map.put("equipmentIds", equipmentIds);
        map.put("auditStatus", auditStatus);
        map.put("setupYear", setupYear);
        map.put("keyWord", keyWord);
        map.put("leadUnitName", leadUnitName);
        map.put("leadUnitCode", leadUnitCode);
        map.put("applyUnitName", applyUnitName);
        map.put("joinUnitName", joinUnitName);
        map.put("joinUnitCode", joinUnitCode);
        map.put("taskWriteUsersIds", taskWriteUsersIds);

        if(StringUtils.isNotBlank(startMoney) && StringUtils.isNotBlank(endMoney)){
            BigDecimal b1=new BigDecimal(startMoney);
            BigDecimal b2=new BigDecimal(endMoney);
            map.put("startMoney", b1);
            map.put("endMoney", b2);
        }


        map.put("createUserId", createUserId);
        map.put("createUserName", createUserName);
        map.put("professionalFieldCode", professionalFieldCode);
        map.put("professionalFieldName", professionalFieldName);
        map.put("setupId", setupId);
        map.put("taskId", taskId);
        map.put("unitPathIds", unitPathIds);
        map.put("parentUnitPathIds", parentUnitPathIds);
        System.out.println(">>>>>>>>applyUnitCode="+applyUnitCode);
        StringBuffer applyUnitCodeStr=new StringBuffer();
        if(!applyUnitCode.equals(""))
        {
            applyUnitCodeStr.append(" (");
            String arr[]=applyUnitCode.split(",");
            for(int i=0;i<arr.length;i++)
            {
                if(i>0)
                {
                    applyUnitCodeStr.append(" OR FIND_IN_SET('"+arr[i]+"', t.`apply_unit_code`)");
                }else
                {
                    applyUnitCodeStr.append("FIND_IN_SET('"+arr[i]+"', t.`apply_unit_code`)");
                }

            }
            applyUnitCodeStr.append(" )");
        }

        map.put("sqlStr", applyUnitCodeStr.toString());

        System.out.println(">>>>>>>>sqlstr"+applyUnitCodeStr.toString());
        list = sreProjectMapper.getList(map);
        if(isKJBPerson.equals("true")){
            pageInfo = new PageInfo<SreProject>(list);
        }else{
            list = new ArrayList<SreProject>();
            pageInfo = new PageInfo<SreProject>(list);
        }
        System.out.println(">>>>>>>>>查询分页结果"+pageInfo.getList().size());

        LayuiTableData data = new LayuiTableData();
        data.setData(pageInfo.getList());
        Long total = pageInfo.getTotal();
        data.setCount(total.intValue());
        return data;
    }
}