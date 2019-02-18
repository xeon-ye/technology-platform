package com.pcitc.service.expert.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pcitc.base.common.ChartForceCategories;
import com.pcitc.base.common.ChartForceDataLink;
import com.pcitc.base.common.ChartForceDataNode;
import com.pcitc.base.common.ChartForceResultData;
import com.pcitc.base.common.ChartPieDataValue;
import com.pcitc.base.common.ChartPieResultData;
import com.pcitc.base.common.ChartSingleLineResultData;
import com.pcitc.base.common.LayuiTableData;
import com.pcitc.base.common.LayuiTableParam;
import com.pcitc.base.common.Result;
import com.pcitc.base.common.ResultSKM;
import com.pcitc.base.common.TreeNode;
import com.pcitc.base.common.enums.DataOperationStatusEnum;
import com.pcitc.base.common.enums.DelFlagEnum;
import com.pcitc.base.expert.TechFamilyType;
import com.pcitc.base.expert.ZjkAchievement;
import com.pcitc.base.expert.ZjkAchievementExample;
import com.pcitc.base.expert.ZjkExpert;
import com.pcitc.base.expert.ZjkExpertExample;
import com.pcitc.base.expert.ZjkPatent;
import com.pcitc.base.expert.ZjkPic;
import com.pcitc.base.system.SysDictionary;
import com.pcitc.base.util.DateUtil;
import com.pcitc.base.util.MyBeanUtils;
import com.pcitc.base.util.StrUtil;
import com.pcitc.base.util.TreeNodeUtil;
import com.pcitc.config.SpringContextUtil;
import com.pcitc.mapper.expert.ZjkExpertMapper;
import com.pcitc.service.expert.TechFamilyTypeService;
import com.pcitc.service.expert.ZjkBaseInfoService;
import com.pcitc.service.expert.ZjkChengguoService;
import com.pcitc.service.expert.ZjkPicService;
import com.pcitc.service.expert.ZjkZhuanliService;
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
        List<ZjkExpert> zjkExpertreturnList = zjkBaseInfoMapper.findZjkExpertList(record);
        int[] s = StrUtil.randomCommon(0, zjkExpertreturnList.size(), 10);
        List<ZjkExpert> experts = new ArrayList<>();
        for (int i = 0; i < s.length; i++) {
            experts.add(zjkExpertreturnList.get(s[i]));
        }
        return experts;
    }

    @Override
    public int updateOrInsertZjkBaseInfo(ZjkExpert zjkBaseInfo) throws Exception {
        int result = 500;
        ZjkExpert expert = zjkBaseInfoMapper.selectByPrimaryKey(zjkBaseInfo.getDataId());
        if (expert != null) {
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
    public Integer deleteZjkBaseInfo(Serializable zjkBaseInfoId) {
        try {
            ZjkExpert record = zjkBaseInfoMapper.selectByPrimaryKey(zjkBaseInfoId.toString());
            if (record != null) {
                record.setStatus(DelFlagEnum.STATUS_DEL.getCode() + "");
                zjkBaseInfoMapper.updateByPrimaryKey(record);
            }
            return Integer.parseInt(String.valueOf(DataOperationStatusEnum.DEL_OK));
        } catch (Exception e) {
            return Integer.parseInt(String.valueOf(DataOperationStatusEnum.DEL_DATA_ERROR));
        }
    }

    @Override
    public LayuiTableData findZjkBaseInfoByPage(LayuiTableParam param) {
        ZjkExpertExample example = new ZjkExpertExample();
        ZjkExpertExample.Criteria c = example.createCriteria();
//        c.andStatusEqualTo("1");

        Object expertName = param.getParam().get("expertName");
        if (!StrUtil.isObjectEmpty(expertName)) {
            c.andExpertNameLike("%" + expertName + "%");
        }

        Object auditStatus = param.getParam().get("auditStatus");
        if (!StrUtil.isObjectEmpty(auditStatus)) {
            c.andAuditStatusEqualTo(auditStatus.toString());
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
            example.or().andExpertNameLike("%" + keywords + "%");
            example.or().andUserDescLike("%" + keywords + "%");
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
        for (int i = 0, j = array.size(); i < j; i++) {
            JSONObject obj = (JSONObject) array.get(i);
            ZjkExpert record = new ZjkExpert();
            record.setDataId(getObjString(obj.get("expertid")));                        //        expertid: 专家id
            record.setExpertName(getObjString(obj.get("expertName")));                  //        expertName: 专家姓名
            record.setCompanyName(getObjString(obj.get("companyName")));                //        companyName: 所属公司
            record.setAge(getObjString(obj.get("age")));                                //        age: 年龄
            record.setIndustryName(getObjString(obj.get("skillFields")));               //        skillFields:擅长技术领域
            record.setJoinProjectId(getObjString(obj.get("projectIds")));               //        projectIds:参与项目id
            record.setJoinProjectName(getObjString(obj.get("projectNames")));           //        projectNames : 参与项目名称
            record.setMobile(getObjString(obj.get("telephone")));                       //        telephone : 电话
            record.setEmail(getObjString(obj.get("email")));                            //        email : 邮箱
            record.setAddress(getObjString(obj.get("address")));                        //        address : 家庭住址
            record.setCardId(getObjString(obj.get("idNumber")));                        //        idNumber: 证件号
            record.setCardType(getObjString(obj.get("idNumberType")));                  //        idNumberType: 证件类型
            record.setEducation(getObjString(obj.get("educationBk")));                  //        educationBk: 学历
            record.setExpertProfessinal(getObjString(obj.get("discription")));         //        discription: 专家简介
            record.setUserDesc(getObjString(obj.get("title")));                       //        title: 职称
            record.setSex(getObjString(obj.get("gender")));                           //        gender: 性别
            record.setExpertNationality(getObjString(obj.get("nationality")));        //        nationality: 国籍
            this.insert(record);
        }
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
            String  flag= strDataId.substring(0, index);
            String  dataId= strDataId.substring(index + 1, strDataId.length());
            ZjkExpert expert = this.selectByPrimaryKey(dataId);
            expert.setAuditStatus("agree".equals(flag) ? "2" : "3");
            this.updateByPrimaryKey(expert);
            return Integer.parseInt(String.valueOf(DataOperationStatusEnum.DEL_OK.getStatusCode()));
        } catch (Exception e) {
            e.printStackTrace();
            return Integer.parseInt(String.valueOf(DataOperationStatusEnum.DEL_DATA_ERROR.getStatusCode()));
        }
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
}