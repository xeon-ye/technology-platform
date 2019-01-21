package com.pcitc.service.search.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pcitc.base.common.LayuiTableData;
import com.pcitc.base.common.LayuiTableParam;
import com.pcitc.base.common.PageResult;
import com.pcitc.base.common.TreeNode2;
import com.pcitc.base.expert.ZjkPatent;
import com.pcitc.base.hana.report.H1AMKYSY100117;
import com.pcitc.base.report.SysReportStp;
import com.pcitc.base.report.SysReportStpExample;
import com.pcitc.base.stp.out.*;
import com.pcitc.base.system.SysFile;
import com.pcitc.base.system.SysFileVo;
import com.pcitc.base.util.CommonUtil;
import com.pcitc.base.util.DataTableInfoVo;
import com.pcitc.base.util.DateUtil;
import com.pcitc.base.util.MyBeanUtils;
import com.pcitc.mapper.out.OutAppraisalMapper;
import com.pcitc.mapper.out.OutProjectInfoMapper;
import com.pcitc.mapper.out.OutRewardMapper;
import com.pcitc.service.expert.TfmTypeService;
import com.pcitc.service.report.SysReportStpService;
import com.pcitc.service.search.FullSearchService;
import com.pcitc.service.system.SysFileService;
import com.pcitc.utils.StringUtils;
import com.pcitc.web.feign.HomeProviderClient;
import com.pcitc.web.feign.ZjkBaseInfoServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.lang.model.element.Element;
import javax.swing.*;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.*;

@Service("fullSearchServiceImpl")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FullSearchServiceImpl implements FullSearchService {

    @Autowired
    OutProjectInfoMapper outProjectInfoMapper;

    @Autowired
    SysFileService sysFileService;

    @Autowired
    TfmTypeService tfmTypeService;

    @Autowired
    ZjkBaseInfoServiceClient zjkBaseInfoServiceClient;

    @Autowired
    HomeProviderClient homeProviderClient;

    private static final String[] tabString = {"科研", "成果"};

    private static int limit_scientific = 1;

    public LayuiTableData getTableSearch(LayuiTableParam param) throws Exception {
        //公共
        int page = param.getPage();
        int limit = param.getLimit();
        Map<String, Object> param_public = param.getParam();
        Object obj = param_public.get("keyword");
        Object tabCountObj = param_public.get("fileCount");
        String keyword = (obj == null || "".equals(obj)) ? "" : obj.toString();
        int msg;
        int tabsCount = Integer.parseInt((tabCountObj == null || "".equals(tabCountObj)) ? "0" : tabCountObj.toString());//tab总数量  首页：0，其他页有值
        System.out.println("tabsCount = " + tabsCount);
        LayuiTableData tableData = new LayuiTableData();

        //组装 tabs
        LayuiTableParam param_common = new LayuiTableParam();
        param_common.setPage(page);
        Map<String, Object> map_common = param.getParam();
        map_common.put("keyword", keyword);
        param_common.setParam(map_common);
        param_common.setLimit(limit_scientific);
        param_common.setParam(param_public);

        //科研课题
        LayuiTableData tableDataScientific = this.getTableDataScientific(param_common);
//        List<?> scientificData = tableDataScientific.getData();
        //科研成果
        LayuiTableData tableDataAchivementc = this.getTableDataAchivement(param_common);
//        List<?> achivementcData = tableDataAchivementc.getData();
        //报表
        LayuiTableData tableDataReport = this.getTableDataReport(param_common);
//        List<?> reportData = tableDataReport.getData();

        //科技奖励
        LayuiTableData tableDataOutReward = this.getOutRewardListPage(param_common);
//        List<?> outRewardData = tableDataOutReward.getData();

        //科研装备
        LayuiTableData tableDataEquipment = this.getEquipmentListPage(param_common);

        //技术族
        LayuiTableData tableDataTech = tfmTypeService.findTfmTypeByPage(param_common);
//        List<?> TechData = tableDataTech.getData();
        //专家信息
        LayuiTableData tableDataExpert = zjkBaseInfoServiceClient.selectZjkBaseInfoByPage(param_common);
//        List<?> ExpertData = tableDataExpert.getData();
        //知识产权
        LayuiTableData tableDataPatent = zjkBaseInfoServiceClient.selectZjkZhuanliByPage(param_common);

        //汇总
        List list = new ArrayList<>();
        int total = 0;


        List<TreeNode2> Equipment = (List<TreeNode2>) tableDataEquipment.getData();
        if (Equipment != null&&Equipment.size()>0) {
            total = total + 1;
            for (int i = 0; i < Equipment.size(); i++) {
                Equipment.get(i).setSelect_type("equipment");
                list.add(Equipment.get(i));
            }
        }

        List<Map<String, String>> Achivementc = (List<Map<String, String>>) tableDataAchivementc.getData();
        if (Achivementc != null&&Achivementc.size()>0) {
            total = total + 1;
            for (int i = 0; i < Achivementc.size(); i++) {
                Map<String, Object> map = MyBeanUtils.transBean2Map(Achivementc.get(i));
                map.put("select_type", "achivement");
                list.add(map);
            }
        }

        List<Map<String, String>> Scientific = (List<Map<String, String>>) tableDataScientific.getData();
        if (Scientific != null&&Scientific.size()>0) {
            total = total + 1;
            for (int i = 0; i < Scientific.size(); i++) {
                Map<String, Object> map = MyBeanUtils.transBean2Map(Scientific.get(i));
                map.put("select_type", "scientific");
                list.add(map);
            }
        }

        List<Map<String, String>> Report = (List<Map<String, String>>) tableDataReport.getData();
        if (Report != null&&Report.size()>0) {
            total = total + 1;
            for (int i = 0; i < Report.size(); i++) {
                Map<String, Object> map = MyBeanUtils.transBean2Map(Report.get(i));
                map.put("select_type", "report");
                list.add(map);
            }
        }

        List<Map<String, String>> OutReward = (List<Map<String, String>>) tableDataOutReward.getData();
        if (OutReward != null&&OutReward.size()>0) {
            total = total + 1;
            for (int i = 0; i < OutReward.size(); i++) {
                Map<String, Object> map = MyBeanUtils.transBean2Map(OutReward.get(i));
                map.put("select_type", "outReward");
                list.add(map);
            }
        }

        List<Map<String, String>> zjkTech = (List<Map<String, String>>) tableDataTech.getData();
        if (zjkTech != null&&zjkTech.size()>0) {
            total = total + 1;
            for (int i = 0; i < zjkTech.size(); i++) {
                Map<String, Object> map = MyBeanUtils.transBean2Map(zjkTech.get(i));
                map.put("select_type", "tech");
                list.add(map);
            }
        }

        List<Map<String, String>> zjkExpert = (List<Map<String, String>>) tableDataExpert.getData();
        if (zjkExpert != null&&zjkExpert.size()>0) {
            total = total + 1;
            for (int i = 0, j = zjkExpert.size(); i < j; i++) {
                Map<String, Object> map = MyBeanUtils.transBean2Map(zjkExpert.get(i));
                map.put("select_type", "expert");
                list.add(map);
            }
        }

        List<Map<String, String>> zjkPatents = (List<Map<String, String>>) tableDataPatent.getData();
        if (zjkPatents != null&&zjkPatents.size()>0) {
            total = total + 1;
            for (int i = 0, j = zjkPatents.size(); i < j; i++) {
                Map<String, Object> map = MyBeanUtils.transBean2Map(zjkPatents.get(i));
                map.put("select_type", "patent");
                list.add(map);

            }
        }
//        getTabList(param_common, total, list, limit);

        //首页total，其他页取值
        msg = (page == 1) ? (total) : (tabsCount);
        LayuiTableData tableDataFile = new LayuiTableData();
        DataTableInfoVo dataTableInfoVo = new DataTableInfoVo();
   /*     if (msg >= page * limit) {
            dataTableInfoVo.setiDisplayStart(0);
            dataTableInfoVo.setiDisplayLength(1);
            SysFileVo vo = new SysFileVo();
            if (!"".equals(keyword)) {
                vo.setFileName(keyword);
            }
            vo.setDataTableInfoVo(dataTableInfoVo);
            try {
                JSONObject jsonObject = setFileFlag(vo);
                tableDataFile.setData((List<SysFile>) jsonObject.get("list"));
                tableDataFile.setCount(Integer.valueOf((jsonObject.get("totalCount") + "")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            tableDataFile.addPropertyToData("select_type","file");
            List<?> fileData = tableDataFile.getData();
            if (fileData != null && fileData.size() > 0) {
                total = total + tableDataFile.getCount();
            }
        } else {
            boolean isShowFileAll = (page * limit - msg) >= limit;
            if (isShowFileAll) {
                list = new ArrayList();
            }
            int file_limit = limit - msg % limit;
            if (file_limit > 0) {
                int start_dis = 0;
                if (msg >= limit) {
                    start_dis = (isShowFileAll) ? (((page - (msg / limit + msg % limit > 0 ? 1 : 0)) * limit) - file_limit) : (page - 1) * param.getLimit();
                } else {
                    start_dis = (isShowFileAll) ? (file_limit + ((page - (msg / limit + msg % limit > 0 ? 1 : 0) - 1) * limit)) : (page - 1) * param.getLimit();
                }
                int limit_dis = (isShowFileAll) ? limit : file_limit;
                dataTableInfoVo.setiDisplayStart(start_dis);
                dataTableInfoVo.setiDisplayLength(limit_dis);
            } else {
                dataTableInfoVo.setiDisplayStart(0);
                dataTableInfoVo.setiDisplayLength(1);
            }
            SysFileVo vo = new SysFileVo();
            if (!"".equals(keyword)) {
                vo.setFileName(keyword);
            }
            vo.setDataTableInfoVo(dataTableInfoVo);
            try {
                JSONObject jsonObject = setFileFlag(vo);
                tableDataFile.setData((List<SysFile>) jsonObject.get("list"));
                tableDataFile.setCount(Integer.valueOf((jsonObject.get("totalCount") + "")));
            } catch (Exception e) {
                e.printStackTrace();
            }

            tableDataFile.addPropertyToData("select_type","file");
            List<?> fileData = tableDataFile.getData();
            //只算>0;<0算total
            if (fileData != null && fileData.size() > 0) {
                if (file_limit > 0) {
                    for (int i = 0; i < fileData.size(); i++) {
                        list.add(fileData.get(i));
                    }
                }
                total = total + tableDataFile.getCount();
            }
        }*/
        //返回
        System.out.println("total = " + total);
        tableData.setCount(total);
        tableData.setData(list);
        tableData.setMsg(msg + "");
        return tableData;
    }

    private LayuiTableData getEquipmentListPage(LayuiTableParam param_common) throws Exception {
        JSONArray jSONArray = homeProviderClient.get_home_KYZB_02(JSONObject.toJSONString(param_common.getParam()));
        Object keyword = param_common.getParam().get("keyword");
        LayuiTableData layuiTableData = new LayuiTableData();

        List<H1AMKYSY100117> list = JSONObject.parseArray(jSONArray.toJSONString(), H1AMKYSY100117.class);

        List<TreeNode2> chartCircleList = new ArrayList<TreeNode2>();
        List<TreeNode2> chartCircleListRed = new ArrayList<TreeNode2>();
        String strKeyword = "";
        for (int i = 0; i < list.size(); i++) {
            H1AMKYSY100117 bean = list.get(i);

            TreeNode2 node = new TreeNode2();
            node.setExtend01(bean.getG0ZCXLMS());//设备类型
            node.setExtend02(bean.getG0GSJC());//直属研究院
            node.setExtend04(bean.getG0ZBHND());//购置年度
            node.setExtend05(bean.getG0NDJAR());//使用年限
            node.setExtend06(bean.getG0NDSYN());//剩余年限

            DecimalFormat decimalFormat = new DecimalFormat(".00");
            node.setExtend07(bean.getG0NCGZYZJE());//购置金额(万元)
            if (bean.getG0NCGZYZJE() != null) {
                node.setExtend07(decimalFormat.format(Double.valueOf(bean.getG0NCGZYZJE()) / 10000l));
            }
            node.setExtend08(bean.getG0LJZJJE());//折旧金额（万元）
            if (bean.getG0LJZJJE() != null) {
                node.setExtend08(decimalFormat.format(Double.valueOf(bean.getG0LJZJJE()) / 10000l));
            }
            node.setExtend09("0");
            if (bean.getG0NCGZYZJE() != null && bean.getG0LJZJJE() != null) {
                Double rs = Double.valueOf(node.getExtend07()) - Double.valueOf(node.getExtend08());
                node.setExtend09(decimalFormat.format(rs));
            }
            node.setExtend10(bean.getBl() + "%");//折旧率
            strKeyword = bean.getG0TXT50();
            if (keyword != null && !"".equals(keyword) && strKeyword.contains(keyword.toString())) {
                strKeyword = bean.getG0TXT50().replace(keyword.toString(), "<span style=\"color:red\">" + keyword + "</span>");
                node.setExtend03(strKeyword);//设备名称
                chartCircleListRed.add(node);
            }else {
                node.setExtend03(strKeyword);//设备名称
                chartCircleList.add(node);
            }
        }
        List<TreeNode2> resultList = new ArrayList<TreeNode2>();
        if (keyword != null && !"".equals(keyword)){
            if (chartCircleListRed.size()>0){
                resultList.add(chartCircleListRed.get(0));
            }
            layuiTableData.setData(resultList);
        }else {
            if (chartCircleList.size()>0){
                resultList.add(chartCircleList.get(0));
            }
            layuiTableData.setData(resultList);
        }
        return layuiTableData;
    }

    public JSONObject setFileFlag(SysFileVo vo) {
        JSONObject jsonObject = null;
        try {
            jsonObject = sysFileService.selectSysFileListEs(vo);
            List<SysFile> sysFiles = (List<SysFile>) jsonObject.get("list");
            if (sysFiles != null && sysFiles.size() > 0) {
                for (int i = 0, j = sysFiles.size(); i < j; i++) {
                    sysFiles.get(i).setBak10("file");
                }
                jsonObject.put("list", sysFiles);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Autowired
    private SysReportStpService sysReportStpService;

    @Override
    public LayuiTableData getTableDataReport(LayuiTableParam param) {
        SysReportStpExample example = new SysReportStpExample();
        SysReportStpExample.Criteria c = example.createCriteria();

        Object keywords = param.getParam().get("keyword");
        if (keywords != null && !"".equals(keywords)) {
//            String[] strings = {"report_name","report_desc","report_module"};
//            c.andOrColumn(keywords.toString(),strings,"like");
//            c.andReportNameLike("%"+keywords.toString()+"%");
            example.or().andReportNameLike("%" + keywords.toString() + "%");
            example.or().andReportModuleLike("%" + keywords.toString() + "%");
            example.or().andReportDescLike("%" + keywords.toString() + "%");
        }

        int pageSize = param.getLimit();
        int pageStart = (param.getPage() - 1) * pageSize;
        int pageNum = pageStart / pageSize + 1;
        PageHelper.startPage(pageNum, pageSize);
        List<SysReportStp> list = sysReportStpService.selectByExample(example);

        PageInfo<SysReportStp> pageInfo = new PageInfo<SysReportStp>(list);

//        for (int i = 0; i < pageInfo.getList().size(); i++) {
//            pageInfo.getList().get(i).setBak1("report");
//        }
        // 3、获取分页查询后的数据
        LayuiTableData data = new LayuiTableData();

        if (keywords != null && !"".equals(keywords)) {
            data.setData(setKeyWordCss(pageInfo, keywords.toString()));
        } else {
            data.setData(pageInfo.getList());
        }
        Long total = pageInfo.getTotal();
        data.setCount(total.intValue());
        return data;
    }

    private void getTabList(LayuiTableParam param_common, int total, List list, int limit) {
        int tabLeng = tabString.length;
        if (tabLeng > limit) {

        } else {
            //正常展示

        }

        for (int a = 0; a < tabLeng; a++) {
            switch (tabString[a]) {
                case "科研":
                    //科研
                    LayuiTableData tableDataScientific = this.getTableDataScientific(param_common);
                    List<?> scientificData = tableDataScientific.getData();
                    if (scientificData != null) {
                        for (int i = 0; i < scientificData.size(); i++) {
                            list.add(scientificData.get(i));
                        }
                        total = total + 1;
                    }
                    break;
                case "成果":
                    //成果
                    LayuiTableData tableDataAchivementc = this.getTableDataAchivement(param_common);
                    List<?> achivementcData = tableDataAchivementc.getData();
                    //汇总
                    if (achivementcData != null) {
                        for (int i = 0; i < achivementcData.size(); i++) {
                            list.add(achivementcData.get(i));
                        }
                        total = total + 1;
                    }
                    break;
            }
        }
    }

    @Autowired
    private OutRewardMapper outRewardMapper;

    public LayuiTableData getOutRewardListPage(LayuiTableParam param) {
        Map<String, Object> paraMap = param.getParam();
        PageHelper.startPage(param.getPage(), param.getLimit());

        OutRewardExample example = new OutRewardExample();
        OutRewardExample.Criteria criteria = example.createCriteria();

        if (paraMap.get("xmbh") != null && !paraMap.get("xmbh").toString().equals("")) {
            criteria.andXmbhLike("%" + paraMap.get("xmbh").toString() + "%");
        }
        if (paraMap.get("nd") != null && !paraMap.get("nd").toString().equals("")) {
            criteria.andNdEqualTo(paraMap.get("nd").toString());
        }
        if (paraMap.get("xmmc") != null && !paraMap.get("xmmc").toString().equals("")) {
            criteria.andXmmcLike("%" + paraMap.get("xmmc").toString() + "%");
        }
        List<String> strings = getListInfo(outreward);
        Object keywords = param.getParam().get("keyword");
        if (keywords != null && !"".equals(keywords)) {
            criteria.andOrColumn(keywords.toString(), strings.toArray(new String[strings.size()]), "like");
        }

        example.setOrderByClause(" sbjz,sbdj asc ");

        List<OutReward> list = outRewardMapper.selectByExample(example);
        PageInfo<OutReward> pageInfo = new PageInfo<OutReward>(list);

        LayuiTableData data = new LayuiTableData();

        if (keywords != null && !"".equals(keywords)) {
            data.setData(setKeyWordCss(pageInfo, keywords.toString()));
        } else {
            data.setData(pageInfo.getList());
        }
        Long total = pageInfo.getTotal();
        data.setCount(total.intValue());
        return data;
    }

    @Override
    public LayuiTableData getTableDataScientific(LayuiTableParam param) {
        // 每页显示条数
        int pageSize = param.getLimit();
        // 当前是第几页
        int pageNum = param.getPage();
        // 1、设置分页信息，包括当前页数和每页显示的总计数
        PageHelper.startPage(pageNum, pageSize);

        HashMap<String, Object> hashmap = new HashMap<String, Object>();
        OutProjectInfo opi = new OutProjectInfo();

        List<OutProjectInfo> list = new ArrayList<>();
        Object keywords = param.getParam().get("keyword");
//        OutProjectInfoExample example = new OutProjectInfoExample();
//        OutProjectInfoExample.Criteria c = example.createCriteria();

//        if (keywords != null && !"".equals(keywords)) {
//            for (int i = 0; i < info.length; i++) {
//                setMethodVal(c.getClass(),c,getMethodName(info[i])+"Like","%"+keywords.toString()+"%");
//                example.or(c);
//            }
//            list = outProjectInfoMapper.selectByExample(example);
//
//        }
//        else
        {
            listInfo = getListInfo(info);
            if (param.getParam().get("xmmc") != null && !StringUtils.isBlank(param.getParam().get("xmmc") + "")) {
                opi.setXmmc((String) param.getParam().get("xmmc"));
                hashmap.put("xmmc", param.getParam().get("xmmc"));
                listInfo.removeIf(value -> value.equals("xmmc"));
//                c.andXmidLike("%" + param.getParam().get("xmmc") + "%");
            }

            if (param.getParam().get("hth") != null && !StringUtils.isBlank(param.getParam().get("hth") + "")) {
                opi.setHth((String) param.getParam().get("hth"));
                hashmap.put("hth", param.getParam().get("hth"));
//                c.andHthLike("%" + param.getParam().get("hth") + "%");
                listInfo.removeIf(value -> value.equals("hth"));
            }
            // 资本性、费用性
            if (param.getParam().get("define1") != null && !StringUtils.isBlank(param.getParam().get("define1") + "")) {
                opi.setDefine1((String) param.getParam().get("define1"));

                List define1 = new ArrayList();
                String[] temS = param.getParam().get("define1").toString().split(",");
                for (int i = 0; i < temS.length; i++) {
                    define1.add(temS[i]);
                }
                hashmap.put("define1", define1);
                listInfo.removeIf(value -> value.equals("define1"));

//                c.andDefine1In(define1);
            }

            // 8大院等细分结构
            if (param.getParam().get("define2") != null && !StringUtils.isBlank(param.getParam().get("define2") + "")) {
                opi.setDefine2((String) param.getParam().get("define2"));
                List define2 = new ArrayList();
                String[] temS = param.getParam().get("define2").toString().split(",");
                for (int i = 0; i < temS.length; i++) {
                    define2.add(temS[i]);
                }
                hashmap.put("define2", define2);
                listInfo.removeIf(value -> value.equals("define2"));

            }

            // 直属研究院、分子公司、集团等9种类型
            if (param.getParam().get("define3") != null && !StringUtils.isBlank(param.getParam().get("define3") + "")) {
                opi.setDefine3((String) param.getParam().get("define3"));

                List define3 = new ArrayList();
                String[] temS = param.getParam().get("define3").toString().split(",");
                for (int i = 0; i < temS.length; i++) {
                    define3.add(temS[i]);
                }
                hashmap.put("define3", define3);
                listInfo.removeIf(value -> value.equals("define3"));

//                c.andDefine3In(define3);
            }

            // 国家项目、重大专项、重点项目、其他项目
            if (param.getParam().get("project_property") != null && !StringUtils.isBlank(param.getParam().get("project_property") + "")) {
                opi.setProjectProperty((String) param.getParam().get("project_property"));

                List project_property = new ArrayList();
                String[] temS = param.getParam().get("project_property").toString().split(",");
                for (int i = 0; i < temS.length; i++) {
                    project_property.add(temS[i]);
                }
                hashmap.put("project_property", project_property);
                listInfo.removeIf(value -> value.equals("project_property"));

//                c.andProjectPropertyIn(project_property);
            }

            // 新开项目、续建项目、完工项目
            if (param.getParam().get("project_scope") != null && !StringUtils.isBlank(param.getParam().get("project_scope") + "")) {
                opi.setProjectScope((String) param.getParam().get("project_scope"));
                listInfo.remove("project_scope");
//                c.andProjectScopeLike(param.getParam().get("project_scope").toString());
            }
            // 装备的各种技术类型
            if (param.getParam().get("zylb") != null && !StringUtils.isBlank(param.getParam().get("zylb") + "")) {
                List zylb = new ArrayList();
                String[] temS = param.getParam().get("zylb").toString().split(",");
                for (int i = 0; i < temS.length; i++) {
                    zylb.add(temS[i]);
                }
                listInfo.removeIf(value -> value.equals("zylb"));

                hashmap.put("zylb", zylb);
            }

            // 各个处室
            if (param.getParam().get("zycmc") != null && !StringUtils.isBlank(param.getParam().get("zycmc") + "")) {
                opi.setZycmc((String) param.getParam().get("zycmc"));

                List zycmc = new ArrayList();
                String[] temS = param.getParam().get("zycmc").toString().split(",");
                for (int i = 0; i < temS.length; i++) {
                    zycmc.add(temS[i]);
                }
                hashmap.put("zycmc", zycmc);
                listInfo.removeIf(value -> value.equals("zycmc"));

//                c.andZycmcLike(param.getParam().get("zycmc").toString());
            }

            // 年度，暂时不用
            if (param.getParam().get("nd") != null && !StringUtils.isBlank(param.getParam().get("nd") + "")) {
                opi.setNd((String) param.getParam().get("nd"));
                listInfo.removeIf(value -> value.equals("nd"));

//                c.andNdEqualTo(param.getParam().get("nd").toString());
            }
//            list = outProjectInfoMapper.findOutProjectInfoList(opi);

//            list = outProjectInfoMapper.selectByExample(example);

            if (keywords != null && !"".equals(keywords) && listInfo.size() > 0) {
                String sql = " and (";
                for (int i = 0; i < listInfo.size(); i++) {
//                    setMethodVal(c.getClass(),c,getMethodName(info[i])+"Like","%"+keywords.toString()+"%");
//                    example.or(c);
                    sql = sql + " " + (i == 0 ? "" : " or ") + listInfo.get(i) + " like '%" + keywords + "%'";
                }
                sql = sql + " ) ";
                hashmap.put("keyword", sql);
//                list = outProjectInfoMapper.selectByExample(example);
            }
            list = outProjectInfoMapper.selectCommonProjectByCond(hashmap);
        }

        PageInfo<OutProjectInfo> pageInfo = new PageInfo<OutProjectInfo>(list);
        LayuiTableData data = new LayuiTableData();

//        for (int i = 0; i < pageInfo.getList().size(); i++) {
//            pageInfo.getList().get(i).setDefine9("scientific");
//        }

        if (keywords != null && !"".equals(keywords) && listInfo.size() > 0) {
            data.setData(setKeyWordCss(pageInfo, keywords.toString()));
        } else {
            data.setData(pageInfo.getList());
        }
        Long total = pageInfo.getTotal();
        data.setCount(total.intValue());
        return data;
    }

    public List<Map<String, Object>> setKeyWordCss(PageInfo<?> pageInfo, String keywords) {
        List<Map<String, Object>> maps = new ArrayList<>();
        for (int i = 0; i < pageInfo.getSize(); i++) {
            Object obj = pageInfo.getList().get(i);
            Map<String, Object> map = MyBeanUtils.transBean2Map(obj);
            Set<Map.Entry<String, Object>> entrys = map.entrySet();  //此行可省略，直接将map.entrySet()写在for-each循环的条件中

            Map<String, Object> objectMap = new HashMap<>();
            for (Map.Entry<String, Object> entry : entrys) {
                Object val = entry.getValue();
                if (val != null && !"".equals(val) && val.toString().contains(keywords.toString())) {
                    objectMap.put(entry.getKey(), val.toString().replace(keywords.toString(), "<span style=\"color:red\">" + keywords.toString() + "</span>"));
                } else {
                    objectMap.put(entry.getKey(), entry.getValue());
                }
            }
            maps.add(objectMap);

        }
        return maps;
    }

    @Autowired
    OutAppraisalMapper outAppraisalMapper;

    public LayuiTableData getTableDataAchivement(LayuiTableParam param) {
        Map<String, Object> paraMap = param.getParam();

        // 1、设置分页信息，包括当前页数和每页显示的总计数
        PageHelper.startPage(param.getPage(), param.getLimit());
        List<String> strings = getListInfo(achievement);
        OutAppraisalExample example = new OutAppraisalExample();
        OutAppraisalExample.Criteria criteria = example.createCriteria();

        if (paraMap.get("cgmc") != null && !paraMap.get("cgmc").equals("")) {
            strings.removeIf(value -> value.equals("cgmc"));

            criteria.andCgmcLike("%" + paraMap.get("cgmc").toString() + "%");
        }
        if (paraMap.get("hth") != null && !paraMap.get("hth").equals("")) {
            strings.removeIf(value -> value.equals("hth"));

            criteria.andHthLike("%" + paraMap.get("hth").toString() + "%");
        }
        if (paraMap.get("xmmc") != null && !paraMap.get("xmmc").equals("")) {
            strings.removeIf(value -> value.equals("xmmc"));

            criteria.andXmmcLike("%" + paraMap.get("xmmc").toString() + "%");
        }
        if (paraMap.get("nd") != null && !"".equals(paraMap.get("nd"))) {
            strings.removeIf(value -> value.equals("nd"));
            criteria.andNdEqualTo(paraMap.get("nd").toString());
        }
        Object keywords = param.getParam().get("keyword");
        if (keywords != null && !"".equals(keywords)) {
            criteria.andOrColumn(keywords.toString(), strings.toArray(new String[strings.size()]), "like");
        }
        example.setOrderByClause(" nd desc ");

        List<OutAppraisal> list = outAppraisalMapper.selectByExample(example);
        PageInfo<OutAppraisal> pageInfo = new PageInfo<OutAppraisal>(list);

        LayuiTableData data = new LayuiTableData();

//        for (int i = 0; i < pageInfo.getList().size(); i++) {
//            pageInfo.getList().get(i).setDefine6("achivement");
//        }

        if (keywords != null && !"".equals(keywords) && getListInfo(achievement).size() > 0) {
            data.setData(setKeyWordCss(pageInfo, keywords.toString()));
        } else {
            data.setData(pageInfo.getList());
        }
//        data.setData(pageInfo.getList());

        Long total = pageInfo.getTotal();
        data.setCount(total.intValue());
        return data;
    }

    private static String[] outreward = {"xmmc", "sbdw", "sbjz", "xkfl", "sbdj", "rwly", "jddw", "psdj"};
    private static String[] achievement = {"hth", "xmmc", "cgmc", "zy"};
    private static String[] info = {"xmmc", "xmjb", "ysnd", "yshf", "ysxd", "ysje", "jf", "fwdxbm", "fwdx", "zylbbm", "zylb", "fzdwbm", "fzdw", "jtfzdwbm", "jtfzdw", "fzryx", "fzrdh", "fzrxm", "lxrdh", "lxryx", "lxrxm", "jssxxm", "jssj", "kssj", "yjsj", "zyly", "zysx", "sjid", "status", "yjsjks", "yjsjjs", "xmlbbm", "xmlbmc", "gsbmmc", "gsbmbm", "zycmc", "zycbm", "type_flag", "define3", "define4", "define5", "define6", "define7", "define8", "define9"};
    private List<String> listInfo;

    public static List<String> getListInfo(String[] array) {
        ArrayList<String> strings = new ArrayList<String>(Arrays.asList(array));
        return strings;
    }

    public static void setMethodVal(Object obj, String setVal, String[] array) {
        try {
            Class clazz = obj.getClass();//获得实体类名
            Field[] fields = obj.getClass().getDeclaredFields();//获得属性
            //获得Object对象中的所有方法
            for (Field field : fields) {
                if ("java.lang.String".equals(field.getType().getName()) && getListInfo(array).contains(field.getName())) {
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                    Method getMethod = pd.getReadMethod();//获得get方法
                    Object obj_get = getMethod.invoke(obj);//此处为执行该Object对象的get方法
                    if (obj_get == null || "".equals(obj_get)) {
                        Method setMethod = pd.getWriteMethod();//获得set方法
                        setMethod.invoke(obj, setVal);//此处为执行该Object对象的set方法
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setMethodVal(Class clazz, Object object, String strName, String strVal) {
        try {
            Method method = clazz.getMethod(strName, String.class);
            method.invoke(object, new Object[]{strVal});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static String getMethodName(String javaProperty) {
        StringBuilder sb = new StringBuilder();

        sb.append(javaProperty);
        if (Character.isLowerCase(sb.charAt(0)) && ((sb.length() == 1) || Character.isLowerCase(sb.charAt(1)))) {
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        }
        sb.insert(0, "and");

        return sb.toString();
    }

    public static void main(String[] args) {
        OutProjectInfo obj = new OutProjectInfo();
        OutProjectInfoExample example = new OutProjectInfoExample();
//        setMethodVal(example, "22");
        OutProjectInfoExample.Criteria c = example.createCriteria();
        String keywords = "333";
//        c.andXmidLike("'%"+keywords+"%'");
//        for (int i = 0; i < info.length; i++) {
//            setMethodVal(c.getClass(), c, getMethodName(info[i]) + "Like", keywords);
//        }
        List<String> listInfo = getListInfo(info);
        listInfo.removeIf(value -> value.equals("zylb"));
    }
}
