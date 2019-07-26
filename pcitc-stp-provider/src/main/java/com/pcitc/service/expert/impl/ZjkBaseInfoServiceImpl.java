package com.pcitc.service.expert.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.github.abel533.echarts.Label;
import com.github.abel533.echarts.series.Graph;
import com.github.abel533.echarts.style.ItemStyle;
import com.github.abel533.echarts.style.itemstyle.Normal;
import com.netflix.discovery.converters.Auto;
import com.pcitc.base.common.*;
import com.pcitc.base.expert.*;
import com.pcitc.base.search.ZjkSearchLogExample;
import com.pcitc.base.stp.out.OutAppraisal;
import com.pcitc.base.stp.out.OutPatent;
import com.pcitc.base.stp.out.OutProjectInfo;
import com.pcitc.base.stp.out.OutProjectInfoExample;
import com.pcitc.base.system.*;
import com.pcitc.mapper.expert.*;
import com.pcitc.service.expert.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pcitc.base.common.enums.DataOperationStatusEnum;
import com.pcitc.base.common.enums.DelFlagEnum;
import com.pcitc.base.util.DateUtil;
import com.pcitc.base.util.MyBeanUtils;
import com.pcitc.base.util.StrUtil;
import com.pcitc.base.util.TreeNodeUtil;
import com.pcitc.config.SpringContextUtil;
import com.pcitc.service.feign.SystemRemoteClient;

/**
 * <p>接口实现类</p>
 * <p>Table: zjk_base_info - 专家-基本信息</p>
 *
 * @since 2018-12-08 04:10:36
 */
@Service("zjkBaseInfoService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class ZjkBaseInfoServiceImpl implements ZjkBaseInfoService {

    @Resource
    private ZjkExpertMapper zjkBaseInfoMapper;

    @Autowired
    private ZjkChengguoService zjkChengguoService;

    @Resource
    private SystemRemoteClient systemRemoteClient;

    @Autowired
    private TechFamilyTypeService techFamilyTypeService;
    @Autowired
    private ZjkZhuanliService zjkZhuanliService;

    public List<ZjkExpert> findZjkExpertList(ZjkExpert zjkBaseInfo) {
        List<ZjkExpert> record = zjkBaseInfoMapper.findZjkExpertList(zjkBaseInfo);
        return record;
    }

    @Override
    public List<ZjkExpert> findZjkBaseInfoList(ZjkExpert record) throws Exception {
        return zjkBaseInfoMapper.findZjkExpertList(record);
    }

    @Override
    public List<ZjkExpert> findZjkBaseInfoListRandom(ZjkExpert record) throws Exception {
    	/*List<ZjkExpert> zjkExpertreturnList = zjkBaseInfoMapper.findZjkExpertList(record);
        int[] s = StrUtil.randomCommon(0, zjkExpertreturnList.size(), 10);
        List<ZjkExpert> experts = new ArrayList<>();
        int len = (s==null)?zjkExpertreturnList.size():s.length;
        for (int i = 0; i < len; i++) {
            ZjkExpert e = (s==null)?zjkExpertreturnList.get(i):zjkExpertreturnList.get(s[i]);
            String userDesc = e.getUserDesc();
            if (!StrUtil.isEmpty(userDesc)) {
                if (userDesc.length() > 30) {
                    userDesc = userDesc.substring(0, 30) + "...";
                } else {
                    userDesc = userDesc + "...";
                }
            } else {
                e.setUserDesc("待完善...");
            }
            e.setUserDesc(userDesc);
            experts.add(e);
        }
        return experts;
        */
        List<ZjkExpert> zjkExpertreturnList = selectYsList();
        return zjkExpertreturnList;
    }

    @Override
    public int updateOrInsertZjkBaseInfo(ZjkExpert zjkBaseInfo) throws Exception {
        int result = 500;

        ZjkExpert expert = zjkBaseInfoMapper.selectByPrimaryKey(zjkBaseInfo.getDataId());
        if (expert != null) {
            //不要修改
            zjkBaseInfoMapper.updateByPrimaryKeySelective(zjkBaseInfo);
        } else {
//            zjkBaseInfo.setDataId(IdUtil.createIdByTime());
            zjkBaseInfoMapper.insertSelective(zjkBaseInfo);
        }
        result = 200;
        return result;
    }

    @Override
    public int deleteZjkBaseInfoById(String id) throws Exception {
        int result = 500;
        if (id != null) {
            zjkBaseInfoMapper.deleteByPrimaryKey(id);
        }
        result = 200;
        return result;
    }

    @Override
    public ZjkExpert getZjkBaseInfoInfo(String id) throws Exception {

        return zjkBaseInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public long countByExample(ZjkExpertExample example) {
        return zjkBaseInfoMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(ZjkExpertExample example) {
        return zjkBaseInfoMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(String recordId) {
        return zjkBaseInfoMapper.deleteByPrimaryKey(recordId);
    }

    @Override
    public Integer deleteZjkBaseInfoReal(String recordId) {
        return zjkBaseInfoMapper.deleteByPrimaryKey(recordId);
    }

    @Override
    public int insert(ZjkExpert record) {
        return zjkBaseInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(ZjkExpert record) {
        return zjkBaseInfoMapper.insertSelective(record);
    }

    public ZjkExpert insertObject(ZjkExpert record) {
        this.insert(record);
        return record;
    }

    @Override
    public List<ZjkExpert> selectByExample(ZjkExpertExample example) {
        return zjkBaseInfoMapper.selectByExample(example);
    }

    @Override
    public ZjkExpert selectByPrimaryKey(String recordId) {
        return zjkBaseInfoMapper.selectByPrimaryKey(recordId);
    }

    @Override
    public int updateByExampleSelective(@Param("record") ZjkExpert record, @Param("example") ZjkExpertExample example) {
        return zjkBaseInfoMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(@Param("record") ZjkExpert record, @Param("example") ZjkExpertExample example) {
        return zjkBaseInfoMapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(ZjkExpert record) {
        return zjkBaseInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ZjkExpert record) {
        if (record.getStatus() == null) {
            record.setStatus("");
        }
        return zjkBaseInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public Integer deleteZjkBaseInfo(String zjkBaseInfoId) {
        try {
            ZjkExpert record = zjkBaseInfoMapper.selectByPrimaryKey(zjkBaseInfoId.toString());
            if (record != null) {
                record.setDelFlag(1);
                zjkBaseInfoMapper.updateByPrimaryKey(record);
            }
            return Integer.parseInt(String.valueOf(DataOperationStatusEnum.DEL_OK.getStatusCode()));
        } catch (Exception e) {
            return Integer.parseInt(String.valueOf(DataOperationStatusEnum.DEL_DATA_ERROR.getStatusCode()));
        }
    }

    @Override
    public LayuiTableData findZjkBaseInfoByPage(LayuiTableParam param) {
        ZjkExpertExample example = new ZjkExpertExample();
        ZjkExpertExample.Criteria c = example.createCriteria();
        c.andStatusEqualTo("0");
        c.andDelFlagEqualTo("0");
        Object expertName = param.getParam().get("expertName");
        if (!StrUtil.isObjectEmpty(expertName)) {
            c.andExpertNameLike("%" + expertName + "%");
        }

        Object auditStatus = param.getParam().get("auditStatus");
        if (!StrUtil.isObjectEmpty(auditStatus)) {
            c.andAuditStatusEqualTo(auditStatus.toString());
        }

        Object sysFlag = param.getParam().get("sysFlag");
        if (!StrUtil.isObjectEmpty(sysFlag)) {
            c.andSysFlagEqualTo(sysFlag.toString());
        } else {
            c.andSysFlagEqualTo("0");
        }
        Object email = param.getParam().get("email");
        if (!StrUtil.isObjectEmpty(email)) {
            c.andEmailLike("%" + email + "%");
        }
        Object company = param.getParam().get("company");
        if (!StrUtil.isObjectEmpty(company)) {
            c.andCompanyEqualTo(company.toString());
        }

        LayuiTableData data = new LayuiTableData();
        Object keywords = param.getParam().get("keyword");
        if (keywords != null && !"".equals(keywords)) {
            c.andExpertNameLike("%" + keywords + "%");
        }
        example.setOrderByClause("create_date desc");
        return this.findByExample(param, example);

    }

    public List<SysDictionary> getDicSon(String strParentCode) {
        List<SysDictionary> list = systemRemoteClient.getDictionaryListByParentCode(strParentCode);
        return list;
    }

    public String getDicSonVal(List<SysDictionary> list, String val) {
        List<SysDictionary> dictionaries = list.stream().filter(a -> val.equals(a.getCode())).collect(Collectors.toList());
        return (dictionaries == null || dictionaries.size() == 0) ? "" : dictionaries.get(0).getCode();
    }

    @Override
    public LayuiTableData findZjkBaseInfoByPageIndex(LayuiTableParam param) {
        Object hyly = param.getParam().get("hyly");//行业领域
        Object jg = param.getParam().get("jg");//机构(研究院)
        Object zjmc = param.getParam().get("zjmc");//专家名称
        Object key = param.getParam().get("key");//关键字

        Object nld = param.getParam().get("nld");//年龄段
        Object zc = param.getParam().get("zc");//职称

        Object gb = param.getParam().get("gb");//规避本院

        ZjkExpertExample example = new ZjkExpertExample();
        ZjkExpertExample.Criteria c = example.createCriteria();
        c.andStatusEqualTo("0");
        c.andSysFlagEqualTo("0");
        c.andDelFlagEqualTo("0");
        if (hyly != null && !"".equals(hyly)) {
            c.andExpertProfessionalFieldIn(Arrays.asList(hyly.toString().split(",")));
//            ZjkExpertExample.Criteria criteria2 = example.or();
//            criteria2.andParentIdEqualTo(param.getParam().get("fileKind").toString());
//            example.or(criteria2);
        }
        if (zc != null && !"".equals(zc)) {
            c.andExpertProfessinalIn(Arrays.asList(zc.toString().split(",")));
        }
        if (nld != null && !"".equals(nld)) {
            c.andAgeBetweenIn(Arrays.asList(nld.toString().split(",")));
        }
        if (jg != null && !"".equals(jg)) {//选择了机构
            List<String> jgList = Arrays.asList(jg.toString().split(","));
            if (gb != null && !"".equals(gb)) {//选择了规避,移除
                jgList.remove(gb);
            }
            c.andCompanyIn(jgList);
        } else {//只有规避机构
            c.andCompanyNotEqualTo(gb.toString());
        }
        if (zjmc != null && !"".equals(zjmc)) {
            c.andExpertNameLike("%" + zjmc + "%");
        }
        if (key != null && !"".equals(key)) {
            //获取成果人员id
            ZjkAchievementExample chengguoExample = new ZjkAchievementExample();
            System.out.println("关键字 = " + Arrays.asList(key.toString().split(",")));
            chengguoExample.createCriteria().andAchievementKeysIn(Arrays.asList(key.toString().split(",")));
            List<ZjkAchievement> zjkChengguos = zjkChengguoService.selectByExample(chengguoExample);
            //添加人员id查询条件
            List<String> strings = zjkChengguos.stream().map(ZjkAchievement::getExpertId).collect(Collectors.toList());
            if (strings != null && strings.size() > 0 && !"".equals(strings)) {
                Set<String> set = new HashSet<>();
                set.addAll(strings);
                List<String> stringsSet = (List<String>) (List) Arrays.asList(set.toArray());
                c.andDataIdIn(stringsSet);
            }
            c.andOrColumn(key.toString(), new String[]{"expert_name", "email", "user_desc"}, "like");
        }
        example.setOrderByClause("create_date desc");
        return this.findByExample(param, example);
    }

    /**
     * 根据条件分页搜索
     *
     * @param param
     * @param example
     * @return
     */
    public LayuiTableData findByExample(LayuiTableParam param, ZjkExpertExample example) {
        int pageSize = param.getLimit();
        int pageStart = (param.getPage() - 1) * pageSize;
        int pageNum = pageStart / pageSize + 1;
        PageHelper.startPage(pageNum, pageSize);
        List<ZjkExpert> list = zjkBaseInfoMapper.selectByExample(example);
        // 3、获取分页查询后的数据
        PageInfo<ZjkExpert> pageInfo = new PageInfo<ZjkExpert>(list);
        LayuiTableData data = new LayuiTableData();
        Object keywords = param.getParam().get("keyword");
        if (keywords != null && !"".equals(keywords)) {
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

    /**
     * 树形菜单
     *
     * @return
     */
    @Override
    public List<TreeNode> selectObjectByTree() {
        List<TreeNode> nodes = new ArrayList<TreeNode>();
        ZjkExpertExample example = new ZjkExpertExample();
//        example.getOredCriteria().add(example.createCriteria().andStatusNotEqualTo(DataOperationStatusEnum.DEL_OK.getStatusCode().toString()));
        List<ZjkExpert> records = zjkBaseInfoMapper.selectByExample(example);
        for (ZjkExpert record : records) {
            TreeNode node = new TreeNode();
            node.setId(record.getId());
            //            node.setLevelCode(record.getUnitLevel().toString());
            node.setParentId(record.getParentId());
            nodes.add(node);
        }
        //构建树形结构(从根节点开始的树形结构)

        ZjkExpertExample zjkBaseInfoExample = new ZjkExpertExample();
        String strParentId = zjkBaseInfoMapper.selectByExample(zjkBaseInfoExample).get(0).getId();
        List<TreeNode> orderNodes = TreeNodeUtil.getChildrenNode(strParentId, nodes);

        return orderNodes;
    }

    @Autowired
    ZjkPicService zjkPicService;

    public JSONObject echarts(JSONObject jsonObject) {
        Result result = new Result();
        ZjkPic zjkPic = zjkPicService.selectByPrimaryKey(jsonObject.get("id").toString());
        String sql = zjkPic.getSqlSql();
        String x = zjkPic.getX();
        String y = zjkPic.getY();
//        String title = zjkPic.getTitle();
        String bak1 = zjkPic.getBak1();
        String bak2 = zjkPic.getBak2();
        String bak3 = zjkPic.getBak3();//字典
        String javaCallBack = zjkPic.getCallBackClass();

        List<String> stringsDic = new ArrayList<>();
        if (bak3 != null && !"".equals(bak3)) {
            if ("year".equals(bak3)) {
                int nowDate = Integer.parseInt(DateUtil.dateToStr(new Date(), DateUtil.FMT_YYYY));
                for (int i = 10; i > 0; i--) {
                    stringsDic.add(((nowDate - i) + "").trim());
                }
                stringsDic.add((nowDate + "").trim());
            } else {
                List<SysDictionary> dicSon = this.getDicSon(bak3);
                stringsDic = dicSon.stream().map(SysDictionary::getName).collect(Collectors.toList());
            }
        }

        Map<String, Object> param = (Map<String, Object>) jsonObject.get("param");

        if (bak1 != null && !"".equals(bak1) && !"".equals(bak2) && bak2 != null) {
            Object o = SpringContextUtil.getBean(bak1);
            invokeMethod(o.getClass(), o, bak2, param);
        }

        for (Map.Entry<String, Object> e : param.entrySet()) {
            sql = sql.replace("#{" + e.getKey() + "}", "'" + ((e.getValue() == null) ? "" : e.getValue()) + "'");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("sqlval", sql);
        List<Map<String, Object>> maps = zjkBaseInfoMapper.listSqlResult(map);

        if ("bar".equals(zjkPic.getEcharType())) {
            if ("1".equals(zjkPic.getIsDz())) {
                ChartSingleLineResultData csr = new ChartSingleLineResultData();
                List<Object> ySeries = new ArrayList<Object>();
                List<String> xData = new ArrayList<String>();
                for (int i = 0; i < maps.size(); i++) {
                    Map<String, Object> m = maps.get(i);
                    for (Map.Entry<String, Object> entry : m.entrySet()) {
                        String key = entry.getKey();
                        if (x.contains(key)) {
                            xData.add(entry.getValue().toString());
                        }
                        if (y.contains(key)) {
                            ySeries.add(entry.getValue());
                        }
                    }
                }

                csr.setSeriesDataList(ySeries);
                csr.setxAxisDataList(xData);
                result.setSuccess(true);
                result.setData(csr);
            }
        } else if ("pie".equals(zjkPic.getEcharType())) {
            ChartPieResultData pie = new ChartPieResultData();
            List<ChartPieDataValue> dataList = new ArrayList<ChartPieDataValue>();
            List<String> legendDataList = new ArrayList<String>();
            for (int i = 0; i < maps.size(); i++) {
                Map<String, Object> m = maps.get(i);
                String name = "";
                String value = "";
                for (Map.Entry<String, Object> entry : m.entrySet()) {
                    String key = entry.getKey();
                    if (x.equals(key)) {
                        name = entry.getValue().toString();
                    }
                    if (y.equals(key)) {
                        value = entry.getValue().toString();
                    }
                }
                legendDataList.add(name);
                dataList.add(new ChartPieDataValue(value, name));
            }
            pie.setDataList(dataList);
            pie.setLegendDataList(legendDataList);
            result.setSuccess(true);
            result.setData(pie);
        } else if ("force".equals(zjkPic.getEcharType())) {
            ChartForceResultData force = new ChartForceResultData();
            List<ChartForceDataNode> nodes = new ArrayList<ChartForceDataNode>();

            List<ChartForceDataLink> links = new ArrayList<ChartForceDataLink>();

            List<ChartForceCategories> categories = new ArrayList<ChartForceCategories>();

            List<String> legendDataList = new ArrayList<String>();

            String firstName = param.get(ChartForceResultData.name).toString();
            Object object_first_val = param.get(ChartForceResultData.value);
            String firstValue = object_first_val == null ? "" : object_first_val.toString();

            nodes.add(new ChartForceDataNode(0, firstName, firstValue, firstName));

            for (int i = 0; i < maps.size(); i++) {
                Map<String, Object> m = maps.get(i);
                String name = "";
                String value = "";

                for (Map.Entry<String, Object> entry : m.entrySet()) {
                    String key = entry.getKey();
                    if (x.equals(key)) {
                        name = entry.getValue().toString();
//                        String[] names = name.split(",");
//                        for (int j = 0; j < names.length; j++) {
//
//                            categories.add(new ChartForceCategories(names[j]));
//                        }
                        categories.add(new ChartForceCategories(name));
                    }
                    if (y.equals(key)) {
                        value = entry.getValue().toString();
                    }
                }
//                String[] names = name.split(",");
//
//                for (int j = 0; j < names.length; j++) {
//
//                    nodes.add(new ChartForceDataNode(i + 1, names[j], value, names[j]));
//                    links.add(new ChartForceDataLink(names[j], firstName, i + 1, names[j]));
//                    links.add(new ChartForceDataLink(names[j], firstName, i + 1, names[j]));
//                    legendDataList.add(names[j]);
//                }
                nodes.add(new ChartForceDataNode(i + 1, name, value, name));
                links.add(new ChartForceDataLink(name, firstName, i + 1, name));
                legendDataList.add(name);
            }
            force.setLegendDataList(legendDataList);
            force.setCategories(categories);
            force.setNodes(nodes);
            force.setLinks(links);
            result.setSuccess(true);
            result.setData(force);
        }

        jsonObject.put("result", result);

        if (javaCallBack != null && !"".equals(javaCallBack)) {
            String[] strings = javaCallBack.split("|");
            Object o = SpringContextUtil.getBean(strings[0]);
            invokeMethod(o.getClass(), o, strings[1], jsonObject);
        }

        return jsonObject;
    }

    @Override
    public JSONObject findZjkBaseInfoListImg(ZjkExpert zjkBaseInfo) {
        JSONObject object = new JSONObject();
        try {
            String column_show = "expertProfessinal,college,expertProfessionalFieldName,unitBelongs,professionalAndTime,administrativeDuties,technicalPositiion,";
            List<Result> results = new ArrayList<>();
            List<ZjkExpert> experts = this.findZjkBaseInfoListRandom(zjkBaseInfo);
            int length = experts.size() > 10 ? 10 : experts.size();
            for (int ind = 0; ind < length; ind++) {
                Result result = new Result();
                ZjkExpert e = experts.get(ind);
                Map<String, Object> maps = MyBeanUtils.transBean2Map(e);
                //组装数据
                ChartForceResultData force = new ChartForceResultData();
                List<ChartForceDataNode> nodes = new ArrayList<ChartForceDataNode>();

                List<ChartForceDataLink> links = new ArrayList<ChartForceDataLink>();

                List<ChartForceCategories> categories = new ArrayList<ChartForceCategories>();

                List<String> legendDataList = new ArrayList<String>();

                String firstName = e.getExpertName();
                Object object_first_val = e.getDataId();
                String firstValue = object_first_val == null ? "" : object_first_val.toString();

                nodes.add(new ChartForceDataNode(0, firstName, firstValue, firstName));
//                System.out.println(column_show);
                for (Map.Entry<String, Object> entry : maps.entrySet()) {
                    Object val = entry.getValue();
                    if (val == null) {
                        continue;
                    }
                    if (column_show.indexOf(entry.getKey() + ",") < 0) {
                        continue;
                    }
                    String name = val.toString();
                    categories.add(new ChartForceCategories(name));
                    String value = entry.getValue().toString();
//                for (int j = 0; j < names.length; j++) {
//                    nodes.add(new ChartForceDataNode(i + 1, names[j], value, names[j]));
//                    links.add(new ChartForceDataLink(names[j], firstName, i + 1, names[j]));
//                    links.add(new ChartForceDataLink(names[j], firstName, i + 1, names[j]));
//                    legendDataList.add(names[j]);
//                }
                    nodes.add(new ChartForceDataNode(ind + 1, name, value, name));
                    links.add(new ChartForceDataLink(name, firstName, ind + 1, name));
                    legendDataList.add(name);
                }
                force.setLegendDataList(legendDataList);
                force.setCategories(categories);
                force.setNodes(nodes);
                force.setLinks(links);
                result.setSuccess(true);
                result.setData(force);
                results.add(result);
            }
            object.put("results", results);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public JSONObject picExpertDetail(ZjkExpert zjkBaseInfo) {
        JSONObject object = new JSONObject();
        try {
            Result result = new Result();
            //查询专家信息
            ZjkExpert expert = this.selectByPrimaryKey(zjkBaseInfo.getDataId());
            //查询专家类型
            String expertType = "";
            List<SysDictionary> dictionaryListByParentCode = systemRemoteClient.getDictionaryListByParentCode("ZJK_ZJLX");
            for (int i = 0, j = dictionaryListByParentCode.size(); i < j; i++) {
                if (dictionaryListByParentCode.get(i).getCode().equals(expert.getBak3())) {
                    expertType = dictionaryListByParentCode.get(i).getName();
                }
            }
            String eName = expert.getExpertName();
            Map<String, Object> map = new HashMap<>();
            LayuiTableParam param = new LayuiTableParam();
            map.put("name", eName);
            param.setParam(map);
            param.setLimit(100000000);
            param.setPage(0);
            //成果
            LayuiTableData outAppraisalListPage = systemRemoteClient.getOutAppraisalListPage(param);
            //奖励
            LayuiTableData outRewardListPage = systemRemoteClient.getOutRewardListPage(param);
            //查询专利信息
            LayuiTableData dataPatent = systemRemoteClient.selectOutPatentList(param);
            //查询课题信息
            LayuiTableData outProjectListPageExpert = systemRemoteClient.getOutProjectListPageExpert(param);
            //查询评审数量
            ZjkChoiceExample zjkChoiceExample = new ZjkChoiceExample();
            ZjkChoiceExample.Criteria criteria = zjkChoiceExample.createCriteria();
            criteria.andZjIdEqualTo(zjkBaseInfo.getDataId());
            criteria.andUserIdEqualTo("xm");
            List<ZjkChoice> zjkChoices = zjkChoiceMapper.selectByExample(zjkChoiceExample);
            Map<String, String> map_choice = zjkChoices.stream().collect(Collectors.toMap(ZjkChoice::getXmId, ZjkChoice::getXmName, (e1, e2) -> e1));

            //组装数据
            ChartGraphResultData force = new ChartGraphResultData();
            List<ChartGraphDataNode> nodes = new ArrayList<ChartGraphDataNode>();

            List<ChartGraphDataLink> links = new ArrayList<ChartGraphDataLink>();

            List<ChartForceCategories> categories = new ArrayList<ChartForceCategories>();

            List<String> legendDataList = new ArrayList<String>();

            Map<String,String> map_id = new HashMap<>();

            int link_index = 0;


            Graph graph = new Graph();


            //人员信息;基本信息;专利 课题  评审
            String expertName = expert.getExpertName() + "(" + expertType + ")";
            double x = 200;
            double y = 300;//x,y不能相同
            double d = 360/8;//不变
            double r = 400;
            String dataId = expert.getDataId();
            ItemStyle itemStyle = new ItemStyle();
            itemStyle.normal().show(false);
            nodes.add(new ChartGraphDataNode(0, expertName, null, "0",itemStyle,200,200,"80"));
            categories.add(new ChartForceCategories(expertName));

            String psName = "评审项目" + zjkChoices.size() + "个";




            //相关课题
            int count_project =  outProjectListPageExpert.getCount();
            String projectName = "相关课题" + count_project + "个";
            String projectValue = dataId + "kt";

            int p_index = 1;
            double p_x = getX(x,y,d*p_index,r);
            double p_y = getY(x,y,d*p_index,r);
            double p_d = getD(count_project);
            double p_r = getR(count_project);
            double p_s_sym = getSYMSon(count_project);
            String p_p_sym_p = getSYMP(p_s_sym);
            nodes.add(new ChartGraphDataNode(p_index, projectName, projectValue, p_index+"",itemStyle,p_x,p_y,p_p_sym_p));
            links.add(new ChartGraphDataLink(p_index+"", "0", link_index+++"", projectName));
            categories.add(new ChartForceCategories(projectName));



            List<Map<String, String>> list_kt = (List<Map<String, String>>) outProjectListPageExpert.getData();
            List<String> xmids = new ArrayList<>();
            //定义牵头单位,负责单位---企业
            Map<String, String> map_dw = new HashMap<>();
            for (int i = 0, j = list_kt.size(); i < j; i++) {
                Map<String, String> map_obj = list_kt.get(i);
                String name = map_obj.get("xmmc");
                String value = map_obj.get("xmid");
                String source = (p_index*1000+i)+"";
                xmids.add(map_obj.get("dataId"));

//                nodes.add(new ChartGraphDataNode(1, name, value, source,itemStyle,getX(cgx,cgy,cg_d*i,cgr),getY(cgx,cgy,cg_d*i,cgr),cg_s_sym+""));
//                links.add(new ChartGraphDataLink(source, "6", link_index+++"", name));

                nodes.add(new ChartGraphDataNode(p_index, name, value,source,itemStyle,getX(p_x,p_y,p_d*i,p_r),getY(p_x,p_y,p_d*i,p_r),p_s_sym+""));
                links.add(new ChartGraphDataLink(source+"", p_index+"", link_index+++"", name));
                //课题关联评审过的项目
                if (map_choice.get(value) != null) {
                    links.add(new ChartGraphDataLink(name, psName, link_index+++"", name));
                }
                //企业组装
                String fzdw = map_obj.get("fzdw");
                if (fzdw != null && !"".equals(fzdw)) {
                    map_dw.put(fzdw, name);
                }
                String define8 = map_obj.get("define8");
                if (define8 != null && !"".equals(define8)) {
                    map_dw.put(define8, name);
                }
            }
//
//            //相关专利
//            String patentName = "相关专利" + dataPatent.getCount() + "个";
//            String patentValue = dataId + "zl";
//
//            nodes.add(new ChartGraphDataNode(2, patentName, patentValue, "2",itemStyle));
//            links.add(new ChartGraphDataLink(patentName, expertName, link_index+++"", patentName));
//            categories.add(new ChartForceCategories(patentName));
//            List<String> zlids = new ArrayList<>();
//
//            List<Map<String, String>> list_patent = (List<Map<String, String>>) dataPatent.getData();
//            for (int i = 0, j = list_patent.size(); i < j; i++) {
//                int l = 2;
//                String name = list_patent.get(i).get("fmmc");
//                String value = list_patent.get(i).get("dataId");
//                zlids.add(value);
//
//                nodes.add(new ChartGraphDataNode(l, name, value, name,itemStyle));
//                links.add(new ChartGraphDataLink(name, patentName, link_index+++"", name));
//                //发明人
//                String[] array_fmr = list_patent.get(i).get("fmr").split(";");
//                for (int k = 0, array_fmr_l = array_fmr.length; k < array_fmr_l; k++) {
//                    String array_fmr_name = array_fmr[k];
//                    nodes.add(new ChartGraphDataNode(l, array_fmr_name, value, array_fmr_name,itemStyle));
//                    links.add(new ChartGraphDataLink(array_fmr_name, name, link_index+++"", array_fmr_name));
//                }
//            }
//
//            //相关技术
//            String techName = "";
//            //查询专家相关技术族信息
//            //查询技术-课题
//            IndexOutProjectInfoExample indexOutProjectInfoExample = new IndexOutProjectInfoExample();
//            if (xmids == null || xmids.size() == 0) {
//                xmids.add("");
//            }
//            List<IndexOutProjectInfo> indexOutProjectInfos = systemRemoteClient.selectByExampleKt(xmids);
//            //查询技术-专利
//            IndexOutPatentExample indexOutPatentExample = new IndexOutPatentExample();
//            if (zlids == null || zlids.size() == 0) {
//                zlids.add("");
//            }
//            List<IndexOutPatent> indexOutPatents = systemRemoteClient.selectByExampleZl(zlids);
//            //-------------------合并技术
//            Map<String, String> map_index_kt = indexOutProjectInfos.stream().collect(Collectors.toMap(IndexOutProjectInfo::getTypeCode, IndexOutProjectInfo::getTypeName, (e1, e2) -> e1));
//            Map<String, String> map_index_kt_gl = indexOutProjectInfos.stream().collect(Collectors.toMap(IndexOutProjectInfo::getTypeCode, IndexOutProjectInfo::getXmmc, (e1, e2) -> e1));
//            Map<String, String> map_index_zl = indexOutPatents.stream().collect(Collectors.toMap(IndexOutPatent::getTypeCode, IndexOutPatent::getTypeName, (e1, e2) -> e1));
//            Map<String, String> map_index_zl_gl = indexOutPatents.stream().collect(Collectors.toMap(IndexOutPatent::getTypeCode, IndexOutPatent::getFmmc, (e1, e2) -> e1));
//
//            List<Map<String, String>> map_index_hb = new ArrayList<>();
//            map_index_hb.add(map_index_kt);
//            map_index_hb.add(map_index_zl);
//
//            Map<String, String> map_index = new HashMap<>();
//            map_index_hb.forEach(m -> map_index.putAll(m));
//
//            techName = "相关技术" + map_index.size() + "项";
//            String techValue = dataId + "tech";
//            nodes.add(new ChartGraphDataNode(3, techName, techValue, "3",itemStyle));
//            links.add(new ChartGraphDataLink(techName, expertName, link_index+++"", techName));
//            categories.add(new ChartForceCategories(techName));
//            //------------------遍历map_index--------拼装技术族
//            for (Map.Entry<String, String> entry : map_index.entrySet()) {
//                String typeCode = entry.getKey();
//                String typeName = entry.getValue();
//                nodes.add(new ChartGraphDataNode(3, typeName, techValue, typeName,itemStyle));
//                links.add(new ChartGraphDataLink(typeName, techName, link_index+++"", typeName));
//                //技术关联课题
//                String gl_kt_name = map_index_kt_gl.get(typeCode);
//                if (gl_kt_name != null && !"".equals(gl_kt_name)) {
//                    links.add(new ChartGraphDataLink(gl_kt_name, typeName, link_index+++"", gl_kt_name));
//                }
//                //技术关联专利
//                String gl_zl_name = map_index_zl_gl.get(typeCode);
//                if (gl_zl_name != null && !"".equals(gl_zl_name)) {
//                    links.add(new ChartGraphDataLink(gl_zl_name, typeName, link_index+++"", gl_zl_name));
//                }
//            }
//
//            //相关企业
//            int length_company = map_dw.size();
//            String companyName = "相关企业" + length_company + "个";
//            String companyValue = dataId + "company";
//
//            nodes.add(new ChartGraphDataNode(4, companyName, companyValue, "4",itemStyle));
//            links.add(new ChartGraphDataLink(companyName, expertName, link_index+++"", companyName));
//            categories.add(new ChartForceCategories(companyName));
//            for (Map.Entry<String, String> entry : map_dw.entrySet()) {
//                //关联企业
//                String map_key = entry.getKey();
//                nodes.add(new ChartGraphDataNode(4, map_key, companyValue, map_key,itemStyle));
//                links.add(new ChartGraphDataLink(map_key, companyName, link_index+++"", map_key));
//                //企业子项关联课题
//                String map_val = entry.getValue();
//                nodes.add(new ChartGraphDataNode(4, map_key, companyValue, map_key,itemStyle));
//                links.add(new ChartGraphDataLink(map_key, map_val, link_index+++"", map_key));
//            }
//
//            //评审项目
//            String psValue = dataId + "ps";
//            nodes.add(new ChartGraphDataNode(5, psName, psValue, "5",itemStyle));
//            links.add(new ChartGraphDataLink(psName, expertName, link_index+++"", psName));
//            categories.add(new ChartForceCategories(psName));

            //成果
            int count_appraisal = outAppraisalListPage.getCount();
            String cgName = "成果" + count_appraisal + "个";
            String cgValue = dataId + "cg";

            int cg_index = 6;
            double cgx = getX(x,y,d*cg_index,r);
            double cgy = getY(x,y,d*cg_index,r);
            double cg_d = getD(count_appraisal);
            double cgr = getR(count_appraisal);
            double cg_s_sym = getSYMSon(count_appraisal);
            String cg_p_sym_p = getSYMP(cg_s_sym);

            nodes.add(new ChartGraphDataNode(1, cgName, cgValue, cg_index+"",itemStyle,cgx,cgy,cg_p_sym_p));
            links.add(new ChartGraphDataLink(cg_index+"", "0", link_index+++"", cgName));
            categories.add(new ChartForceCategories(cgName));

            List<Map<String, String>> list_appraisal = (List<Map<String, String>>) outAppraisalListPage.getData();
            for (int i = 0, j = list_appraisal.size(); i < j; i++) {
                String source = (cg_index*1000+i)+"";
                String name = list_appraisal.get(i).get("cgmc");
                String value = list_appraisal.get(i).get("dataId");
                nodes.add(new ChartGraphDataNode(1, name, value, source,itemStyle,getX(cgx,cgy,cg_d*i,cgr),getY(cgx,cgy,cg_d*i,cgr),cg_s_sym+""));
                links.add(new ChartGraphDataLink(source, "6", link_index+++"", name));
            }
            //奖励
            int count_reward = outRewardListPage.getCount();
            String awardName = "奖励" + outRewardListPage.getCount() + "个";
            String awardValue = dataId + "award";

            int jl_index = 7;
            double jl_x = getX(x,y,d*jl_index,r);
            double jl_y = getY(x,y,d*jl_index,r);
            double jl_d = getD(count_reward);
            double jl_r = getR(count_reward);
            double jl_s_sym = getSYMSon(count_reward);
            String jl_p_sym_p = getSYMP(cg_s_sym);

            nodes.add(new ChartGraphDataNode(2, awardName, awardValue, jl_index+"",itemStyle,jl_x,jl_y,jl_p_sym_p));
            links.add(new ChartGraphDataLink("7", "0", link_index+++"", awardName));
            categories.add(new ChartForceCategories(awardName));

//                ChartGraphDataNode(int category, String name, Object value, String id,Object label) {

            List<Map<String, String>> list_award = (List<Map<String, String>>) outRewardListPage.getData();
            for (int i = 0, j = list_award.size(); i < j; i++) {
                String name = list_award.get(i).get("xmmc");
                String value = list_award.get(i).get("dataId");
                String source = (7*1000+i)+"";
                nodes.add(new ChartGraphDataNode(2, name, value, source,itemStyle,getX(jl_x,jl_y,cg_d*i,jl_r),getY(jl_x,jl_y,jl_d*i,jl_r),jl_s_sym+""));
//                nodes.add(new ChartGraphDataNode(7, name, value, source,itemStyle,220,200+i*10));
                links.add(new ChartGraphDataLink(source, "7", link_index+++"", name));
            }
            //图例
            legendDataList.add(expertName);//0
            legendDataList.add(projectName);//1
//            legendDataList.add(patentName);//2
//            legendDataList.add(psName);//3
//            legendDataList.add(techName);//4
//            legendDataList.add(companyName);//5
            legendDataList.add(cgName);//6
            legendDataList.add(awardName);//7

            //封装
            force.setLegendDataList(legendDataList);
            force.setCategories(categories);
            force.setNodes(nodes);
            force.setLinks(links);
            result.setSuccess(true);
            result.setData(force);
            object.put("results", result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    //获取子项Sysm
    private static double getSYMSon(int length){
        return (length/10==0||length/10<5)?10:(length/10<10?10:(length/10));
    }
    //获取父项Sysm
    private static String getSYMP(double son_length){
        return son_length*3+"";
    }
    /**
     *
     * @param x:x坐标
     * @param y:y坐标
     * @param du 角度
     * @param r 半径
     * @return
     */
    private static double getX(double x,double y,double du,double r){
        double x1 = x + r * Math.cos(du * 3.14 / 180);
        return x1;

    }
    //获取Y
    private static double getY(double x,double y,double du,double r){
        double y1 = y + r * Math.sin(du * 3.14 / 180);
        return y1;
    }
     //获取角度
    private static double getD(int length)
    {
        return 360/length;
    }
    //获取半径
    private static double getR(int length)
    {
        return 100+(length>=100?length:90);
    }


    public Map<String, Object> getResult(Map<String, Object> param) {
        ZjkExpert zjkBaseInfo = this.selectByPrimaryKey(param.get("expertId").toString());
        param.put(ChartForceResultData.name, zjkBaseInfo.getExpertName());
        param.put(ChartForceResultData.value, zjkBaseInfo.getId());
        return param;
    }

    public static Object invokeMethod(Class clazz, Object object, String strName, Map<String, Object> param) {
        Object obj = new Object();
        try {
            clazz.getMethod(strName, Map.class);
            Method method = clazz.getMethod(strName, Map.class);
            obj = method.invoke(object, new Object[]{param});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            return obj;
        }
    }

    //TO DO
    //专家参与项目与成果，专利关系，对应查询？专家与成果专利对应关系

    @Autowired
    private ZjkExpertProjectService zjkExpertProjectService;

    /**
     * 专家保存
     *
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject SaveSKMExpert(JSONObject jsonObject) {
        //判断成功失败
        ResultSKM resultSKM = JSONObject.parseObject(jsonObject.get("rs").toString(), ResultSKM.class);
        String from = jsonObject.get("from").toString();
        JSONArray array = (JSONArray) resultSKM.getData();
        String ids = "";
        for (int i = 0, j = array.size(); i < j; i++) {
            JSONObject obj = (JSONObject) array.get(i);
            String strExpertId = getObjString(obj.get("expertid"));
            ids = (ids + "," + getObjString(obj.get("expertid")));
            ZjkExpert record = new ZjkExpert();
            record.setDataId(getObjString(obj.get("expertid")));                        //        expertid: 专家id
            record.setExpertName(getObjString(obj.get("expertName")));                  //        expertName: 专家姓名
            record.setCompanyName(getObjString(obj.get("companyName")));                //        companyName: 所属公司
            record.setAge(getObjString(obj.get("age")));                                //        age: 年龄
            record.setIndustryName(getObjString(obj.get("skillFields")));               //        skillFields:擅长技术领域

            //这两个字段存入附加信息表

            String projectIds = getObjString(obj.get("projectIds"));
            String projectNames = getObjString(obj.get("projectNames"));
            record.setJoinProjectId(projectIds);               //        projectIds:参与项目id
            record.setJoinProjectName(projectNames);           //        projectNames : 参与项目名称

            //项目分批插入专家项目附加表
            String[] projectIdsArray = projectIds.split(",");
            String[] projectNamesArray = projectNames.split(",");
            for (int k = 0; k < projectIdsArray.length; k++) {
                ZjkExpertProject project = new ZjkExpertProject();
                project.setExpertId(strExpertId);
                project.setProjectId(projectIdsArray[k]);
                project.setProjectName(projectNamesArray[k]);
                zjkExpertProjectService.insert(project);
            }

            record.setMobile(getObjString(obj.get("telephone")));                       //        telephone : 电话
            record.setEmail(getObjString(obj.get("email")));                            //        email : 邮箱
            record.setAddress(getObjString(obj.get("address")));                        //        address : 家庭住址
            record.setCardId(getObjString(obj.get("idNumber")));                        //        idNumber: 证件号
            record.setCardType(getObjString(obj.get("idNumberType")));                  //        idNumberType: 证件类型
            record.setEducation(getObjString(obj.get("educationBk")));                  //        educationBk: 学历
            record.setExpertProfessinal(getObjString(obj.get("discription")));         //        discription: 专家简介
            record.setUserDesc(getObjString(obj.get("title")));                       //        title: 职称
            record.setSex("男".equals(getObjString(obj.get("gender"))) ? "ROOT_UNIVERSAL_XB_N" : "ROOT_UNIVERSAL_XB_V");                           //        gender: 性别
            record.setExpertNationality(getObjString(obj.get("nationality")));        //        nationality: 国籍
            record.setStatus("0");
            record.setSysFlag("0");
            record.setAuditStatus("0");
            record.setDelFlag(0);
            record.setBak3("1");
            record.setCreateDate(DateUtil.dateToStr(new Date(), DateUtil.FMT_DD));
            record.setCreateUser("165553436ed_dfd5e137");
            record.setCreateUserDisp("111111");

            record.setAmountCount("0");
            record.setAchievementCount("0");
            record.setCompanyCount("0");
            record.setProjectCount("0");
            record.setPatentCount("0");

            record.setSourceId("SKM");
            record.setSource("SKM");

            this.insert(record);
//            c.andStatusEqualTo("0");
//            c.andSysFlagEqualTo("0");
//            c.andDelFlagEqualTo("0");
//            c.andAuditStatusEqualTo("2");
        }
        System.out.println(ids);
        return jsonObject;
    }

    @Override
    public JSONObject SaveSKMType(JSONObject jsonObject) {
        //判断成功失败
        ResultSKM resultSKM = JSONObject.parseObject(jsonObject.get("rs").toString(), ResultSKM.class);
        JSONArray array = (JSONArray) resultSKM.getData();
        for (int i = 0, j = array.size(); i < j; i++) {
            JSONObject obj = (JSONObject) array.get(i);
            TechFamilyType record = new TechFamilyType();
            record.setDataId(getObjString(obj.get("classifyId")));
            record.setName(getObjString(obj.get("classifyName")));
            String pId = getObjString(obj.get("parentId"));
            pId = "0".equals(pId) ? "" : pId;
            record.setParentId(pId);
            techFamilyTypeService.insert(record);
        }
        return jsonObject;
    }

    @Override
    public JSONObject savePatent(JSONObject jsonObject) {
        //判断成功失败
        ResultSKM resultSKM = JSONObject.parseObject(jsonObject.get("rs").toString(), ResultSKM.class);
        String from = jsonObject.get("from").toString();

        JSONArray array = (JSONArray) resultSKM.getData();
        for (int i = 0, j = array.size(); i < j; i++) {
            JSONObject obj = (JSONObject) array.get(i);
            ZjkPatent record = new ZjkPatent();
//            record.setDataId(getObjString(obj.get("entityName")));                  //   entityName: hyzlknowledge  //固定值
//            record.setDataId(getObjString(obj.get("teptName")));                    //   teptName: 行业专利   //固定值
            record.setDataId(getObjString(obj.get("id")));                          //   id: 专利id
            record.setPatentName(getObjString(obj.get("knowledgeName")));               //   knowledgeName: 专利名称
            record.setApplyDate(getObjString(obj.get("applyDate")));                   //   applyDate: 申请时间
            record.setPublicDate(getObjString(obj.get("noticeDate")));                  //   noticeDate: 公开时间
            record.setApplyPeople(getObjString(obj.get("patentHolder")));                //   patentHolder:专利申请人
            record.setInventPeopleName(getObjString(obj.get("patentInventor")));              //   patentInventor:专利发明人
            record.setPatentePeopleName(getObjString(obj.get("rightsHolder")));                //   rightsHolder:专利权人
            record.setPatentKeys(getObjString(obj.get("termTags")));                    //   termTags:标签
            record.setOwnerProjectId(getObjString(obj.get("parentProjectId")));             //   parentProjectId: 所属项目id
            record.setOwnerProjectName(getObjString(obj.get("parentProjectName")));           //   parentProjectName:所属项目名称
            record.setOwnerTechType(getObjString(obj.get("classifyId")));                  //   classifyId: 所属分类id
            record.setBak1(getObjString(obj.get("classifyName")));                  //   classifyName: 所属分类名称
            record.setExpertNationality(getObjString(obj.get("nationCode")));                  //   nationCode: 国别
            record.setCompany(getObjString(obj.get("companyName")));                 //   companyName: 所属公司
            record.setPatenteBackground(getObjString(obj.get("patentBackground")));            //   patentBackground: 专利背景
            record.setPatentDesc(getObjString(obj.get("patentDescription")));           //   patentDescription: 专利描述
            zjkZhuanliService.insert(record);
        }
        return jsonObject;
    }

    @Override
    public JSONObject saveSKMAchievement(JSONObject jsonObject) {
        //判断成功失败
        ResultSKM resultSKM = JSONObject.parseObject(jsonObject.get("rs").toString(), ResultSKM.class);
        String from = jsonObject.get("from").toString();
        JSONArray array = (JSONArray) resultSKM.getData();
        for (int i = 0, j = array.size(); i < j; i++) {
            JSONObject obj = (JSONObject) array.get(i);
            ZjkAchievement record = new ZjkAchievement();
//            record.setDataId(getObjString(obj.get("entityName")));           //entityName: kycgknowledge//固定值
//            record.setDataId(getObjString(obj.get("teptName")));             //teptName: 科研成果   //固定值
            record.setDataId(getObjString(obj.get("id")));                   //id: 成果id
            record.setAchievementName(getObjString(obj.get("knowledgeName")));        //knowledgeName:成果名称
            record.setPublishDate(getObjString(obj.get("issuedate")));            //issuedate:年度（日期）,
            record.setCompany(getObjString(obj.get("dutyunit")));             //dutyunit:所属公司(机构）
            record.setAchievementType(getObjString(obj.get("reportclass")));          //reportclass:成果类型
            record.setIndustryId(getObjString(obj.get("classify")));             //classify:专业领域/行业领域(ID)
            record.setIndustryName(getObjString(obj.get("classifyName")));         //classifyName :专业领域/行业领域(NAME)
            record.setAchievementLevel(getObjString(obj.get("level")));                //level:成果级别
            record.setOwnerProjectId(getObjString(obj.get("projectId")));            //projectId : 所属项目（ID）
            record.setOwnerProjectName(getObjString(obj.get("projectName")));          //projectName:所属项目（NAME）
            record.setOwnerContractName(getObjString(obj.get("compact")));              //compact	:所属合同l
            record.setAchievementKeys(getObjString(obj.get("keyword")));              //keyword	:成果标签（关键字）
            record.setFinishPeople(getObjString(obj.get("reportwriter")));         //reportwriter:负责人
            record.setAchievementDesc(getObjString(obj.get("description")));          //description:成果描述
            //TO DO
//            record.setFinishPeopleIds(getObjString(obj.get("reportwriterIds")));        //        reportwriterIds: 负责人ID多个逗号分隔，待提供

            zjkChengguoService.insert(record);
        }
        return jsonObject;
    }

    @Override
    public Object updateAuditStatus(String strDataId) {
        try {
            int index = strDataId.indexOf("_");
            String flag = strDataId.substring(0, index);
            String dataId = strDataId.substring(index + 1, strDataId.length());
            ZjkExpert expert = this.selectByPrimaryKey(dataId);
            expert.setAuditStatus("agree".equals(flag) ? "2" : "0");
            this.updateByPrimaryKey(expert);
            return Integer.parseInt(String.valueOf(DataOperationStatusEnum.DEL_OK.getStatusCode()));
        } catch (Exception e) {
            e.printStackTrace();
            return Integer.parseInt(String.valueOf(DataOperationStatusEnum.DEL_DATA_ERROR.getStatusCode()));
        }
    }

    @Override
    public JSONObject updateExpertByType(ZjkExpert zjkBaseInfo) {
        String bak1 = zjkBaseInfo.getBak1();
        switch (bak1) {
            case "1"://1冻结,正常0
                ZjkExpert expert = this.selectByPrimaryKey(zjkBaseInfo.getDataId());
                expert.setSysFlag(zjkBaseInfo.getSysFlag());
                this.updateByPrimaryKey(expert);
                break;
            case "2":
                break;
            default:
                break;
        }
        return new JSONObject();
    }

    /**
     * 展示已选专家
     *
     * @param param
     * @return
     */
    @Override
    public LayuiTableData showExpertPageTableData(LayuiTableParam param) {
        ZjkExpertExample example = new ZjkExpertExample();
        ZjkExpertExample.Criteria c = example.createCriteria();
        c.andStatusEqualTo("0");
        c.andDelFlagEqualTo("0");
        c.andSysFlagEqualTo("0");
        example.setOrderByClause("create_date desc");
        return this.findByExample(param, example);
    }

    @Autowired
    private ZjkAchievementMapper zjkChengguoMapper;
    @Autowired
    private ZjkPatentMapper zjkPatentMapper;
    @Autowired
    private ZjkChoiceMapper zjkChoiceMapper;
    @Autowired
    private ZjkComplaintMapper zjkComplaintMapper;

    @Override
    public LayuiTableData findZjkBaseInfoByPageCount(LayuiTableParam param) {
        ZjkExpertExample example = new ZjkExpertExample();
        ZjkExpertExample.Criteria c = example.createCriteria();
        c.andStatusEqualTo("0");
        c.andDelFlagEqualTo("0");
//        c.andSysFlagEqualTo("0");
        Object expertName = param.getParam().get("expertName");
        if (!StrUtil.isNullEmpty(expertName)) {
            c.andExpertNameLike("%" + expertName + "%");
        }

        Object auditStatus = param.getParam().get("auditStatus");
        if (!StrUtil.isNullEmpty(auditStatus)) {
            c.andAuditStatusEqualTo(auditStatus.toString());
        }
        Object mobile = param.getParam().get("mobile");
        if (!StrUtil.isNullEmpty(mobile)) {
            c.andMobileLike("%" + mobile + "%");
        }
        Object expertProfessionalFieldsj = param.getParam().get("expertProfessionalFieldsj");
        if (!StrUtil.isNullEmpty(expertProfessionalFieldsj)) {
            c.andExpertProfessionalFieldEqualTo(expertProfessionalFieldsj.toString());
        }

        Object company1DivValue = param.getParam().get("company1DivValue");
        if (!StrUtil.isNullEmpty(company1DivValue)) {
            c.andCompanyEqualTo(company1DivValue.toString());
        }

        Object sysFlag = param.getParam().get("sysFlag");
        if (!StrUtil.isNullEmpty(sysFlag)) {
            c.andSysFlagEqualTo(sysFlag.toString());
        } else {
            c.andSysFlagEqualTo("0");
        }
        Object email = param.getParam().get("email");
        if (email != null && !"".equals(email)) {
            c.andEmailLike("%" + email + "%");
        }
        Object company = param.getParam().get("company");
        if (!StrUtil.isNullEmpty(company)) {
            c.andCompanyEqualTo(company.toString());
        }

        LayuiTableData data = new LayuiTableData();
        Object keywords = param.getParam().get("keyword");
        if (keywords != null && !"".equals(keywords)) {
            example = new ZjkExpertExample();
            ZjkExpertExample.Criteria c1 = example.createCriteria();
            c1.andStatusEqualTo("0");
            c1.andDelFlagEqualTo("0");
            c1.andSysFlagEqualTo("0");
            c1.andExpertNameLike("%" + keywords + "%");

            ZjkExpertExample.Criteria c2 = example.createCriteria();
            c2.andStatusEqualTo("0");
            c2.andDelFlagEqualTo("0");
            c2.andSysFlagEqualTo("0");
            c2.andUserDescLike("%" + keywords + "%");
            example.or(c2);
//            example.or().andExpertNameLike("%" + keywords + "%");
//            example.or().andUserDescLike("%" + keywords + "%");
        }
        example.setOrderByClause("create_date desc");

        //查询专家数量
        data = this.findByExample(param, example);
        List<ZjkExpert> experts = new ArrayList<>();
        if (keywords != null && !"".equals(keywords)) {
            List<Map<String, Object>> maps = (List<Map<String, Object>>) data.getData();
            for (int i = 0, j = maps.size(); i < j; i++) {
                ZjkExpert expert = new ZjkExpert();
                MyBeanUtils.transMap2Bean(maps.get(i), expert);
                experts.add(expert);
            }
        } else {
            experts = (List<ZjkExpert>) data.getData();
        }
//        List ids = experts.stream().map(ZjkExpert::getDataId).distinct().collect(Collectors.toList());
//        List names = experts.stream().map(ZjkExpert::getExpertName).distinct().collect(Collectors.toList());
//        if (ids == null || ids.size() == 0) {
//            ids = new ArrayList<>();
//            ids.add("");
//        }

//        //查询专家成果数量
//        param.getParam().put("expertNames", String.join(",",names));
//        LayuiTableData outAppraisalListPage = null;
//        try {
//            outAppraisalListPage = systemRemoteClient.getOutAppraisalListPage(param);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        ZjkAchievementExample zjkAchievementExample = new ZjkAchievementExample();
//        zjkAchievementExample.createCriteria().andExpertIdIn(ids);
//        List<ZjkAchievement> zjkAchievements = zjkChengguoMapper.selectByExample(zjkAchievementExample);
//        Map<String, Long> a_count = zjkAchievements.stream().collect(Collectors.groupingBy(ZjkAchievement::getExpertId, Collectors.counting()));
//        //查询专家专利数量
//        LayuiTableData patentList = systemRemoteClient.selectOutPatentList(param);
//        ZjkPatentExample zjkPatentExample = new ZjkPatentExample();
//        zjkPatentExample.createCriteria().andExpertIdIn(ids);
//        List<ZjkPatent> zjkPatentExamples = zjkPatentMapper.selectByExample(zjkPatentExample);
//        Map<String, Long> patent_count = zjkPatentExamples.stream().collect(Collectors.groupingBy(ZjkPatent::getExpertId, Collectors.counting()));

        //参与课题数量
//        ZjkChoiceExample zjkChoiceExample = new ZjkChoiceExample();
//        zjkChoiceExample.createCriteria().andZjIdIn(ids);
//        zjkChoiceExample.createCriteria().andStatusEqualTo("2");
//        List<ZjkChoice> zjkChoices = zjkChoiceMapper.selectByExample(zjkChoiceExample);
//        Map<String, Long> choice_count = zjkChoices.stream().collect(Collectors.groupingBy(ZjkChoice::getZjId, Collectors.counting()));
//        //被投诉次数
//        ZjkComplaintExample zjkComplaintExample = new ZjkComplaintExample();
//        zjkComplaintExample.createCriteria().andZjkIdIn(ids);
//        zjkComplaintExample.createCriteria().andStatusEqualTo("2");
//        List<ZjkComplaint> zjkComplaints = zjkComplaintMapper.selectByExample(zjkComplaintExample);
//        Map<String, Long> complaint_count = zjkComplaints.stream().collect(Collectors.groupingBy(ZjkComplaint::getZjkId, Collectors.counting()));

        for (int i = 0, j = experts.size(); i < j; i++) {
            String eName = experts.get(i).getExpertName();

            Map<String, Object> map = new HashMap<>();

            map.put("name", eName);
            param.setParam(map);
            int count = systemRemoteClient.selectOutPatentList(param).getCount();
            experts.get(i).setPatentCount(count + "");
            LayuiTableData outAppraisalListPage = null;
            try {
                outAppraisalListPage = systemRemoteClient.getOutAppraisalListPage(param);
                experts.get(i).setAchievementCount(outAppraisalListPage.getCount() + "");

                experts.get(i).setProjectCount(systemRemoteClient.getOutProjectListPageExpert(param).getCount() + "");
                experts.get(i).setBak7(systemRemoteClient.getOutRewardListPage(param).getCount() + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        data.setData(experts);
        return data;
    }

    public String ageBetween(String strAge) {
        int age = Integer.parseInt(strAge);
        String ret = "";
        if (age <= 30) {
            ret = "20-30";
        } else if (age <= 40) {
            ret = "30-40";
        } else if (age <= 50) {
            ret = "40-50";
        } else if (age <= 60) {
            ret = "50-60";
        } else if (age <= 70) {
            ret = "60-70";
        } else if (age <= 80) {
            ret = "70-80";
        } else {
            ret = "80以上";
        }
        return ret;
    }

    //obj to string
    public String getObjString(Object obj) {
        return (obj == null || "".equals(obj)) ? "" : obj.toString();
    }

    @Override
    public List<Map<String, Object>> queryAllExpert(Map<String, Object> map) {
        return zjkBaseInfoMapper.queryAllExpert(new HashMap<>());
    }

    @Override
    public List<ZjkExpert> selectYsList() {
        ZjkExpertExample example = new ZjkExpertExample();
        ZjkExpertExample.Criteria c = example.createCriteria();
        c.andBak3EqualTo("ZJK_ZJLX_YS");
        c.andDelFlagEqualTo(DelFlagEnum.STATUS_NORMAL.getCode().toString());
        String[] sorts = new String[]{"陈俊武", "徐承恩", "李大东", "顾心怿", "汪燮卿", "毛炳权", "关兴亚", "袁晴棠", "何鸣元", "杨启业", "胡永康", "曹湘洪", "蒋士成", "舒兴田", "王基铭", "康玉柱", "马永生", "曹耀峰", "李阳", "金之钧", "戴厚良", "谢在库"};

        List<String> names = new ArrayList<String>(Arrays.asList(sorts));

        List<ZjkExpert> rs = zjkBaseInfoMapper.selectByExample(example);
        rs.sort(new Comparator<ZjkExpert>() {
            @Override
            public int compare(ZjkExpert o1, ZjkExpert o2) {
                Integer s1 = names.indexOf(o1.getExpertName());
                Integer s2 = names.indexOf(o2.getExpertName());
                return s1 - s2;
            }

        });

        return rs;
    }

}