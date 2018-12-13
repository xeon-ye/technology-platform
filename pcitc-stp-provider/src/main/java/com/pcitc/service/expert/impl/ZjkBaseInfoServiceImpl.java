package com.pcitc.service.expert.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pcitc.base.common.*;
import com.pcitc.base.common.enums.DataOperationStatusEnum;
import com.pcitc.base.common.enums.DelFlagEnum;
import com.pcitc.base.expert.*;
import com.pcitc.base.expert.ZjkBaseInfoExample;
import com.pcitc.base.hana.report.AchievementsAnalysis;
import com.pcitc.base.util.IdUtil;
import com.pcitc.base.util.ReverseSqlResult;
import com.pcitc.base.util.TreeNodeUtil;
import com.pcitc.mapper.expert.ZjkBaseInfoMapper;
import com.pcitc.service.expert.ZjkBaseInfoService;
import com.pcitc.service.expert.ZjkChengguoService;
import com.pcitc.service.expert.ZjkPicService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


/**
 * <p>接口实现类</p>
 * <p>Table: zjk_base_info - 专家-基本信息</p>
 *
 * @since 2018-12-08 04:10:36
 */
@Service("zjkBaseInfoService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class ZjkBaseInfoServiceImpl implements ZjkBaseInfoService {

    @Autowired
    private ZjkBaseInfoMapper zjkBaseInfoMapper;

    public List<ZjkBaseInfo> findZjkBaseInfoList(ZjkBaseInfo zjkBaseInfo) {
        List<ZjkBaseInfo> record = zjkBaseInfoMapper.findZjkBaseInfoList(zjkBaseInfo);
        return record;
    }

    @Override
    public int updateOrInsertZjkBaseInfo(ZjkBaseInfo zjkBaseInfo) throws Exception {
        int result = 500;
        if (zjkBaseInfo.getId() != null && zjkBaseInfo.getId() != null) {
            zjkBaseInfoMapper.updateByPrimaryKeySelective(zjkBaseInfo);
        } else {
            zjkBaseInfo.setId(IdUtil.createIdByTime());
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
    public ZjkBaseInfo getZjkBaseInfoInfo(String id) throws Exception {

        return zjkBaseInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public long countByExample(ZjkBaseInfoExample example) {
        return zjkBaseInfoMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(ZjkBaseInfoExample example) {
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
    public int insert(ZjkBaseInfo record) {
        record.setId(IdUtil.createIdByTime());
        return zjkBaseInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(ZjkBaseInfo record) {
        return zjkBaseInfoMapper.insertSelective(record);
    }

    public ZjkBaseInfo insertObject(ZjkBaseInfo record) {
        this.insert(record);
        return record;
    }

    @Override
    public List<ZjkBaseInfo> selectByExample(ZjkBaseInfoExample example) {
        return zjkBaseInfoMapper.selectByExample(example);
    }

    @Override
    public ZjkBaseInfo selectByPrimaryKey(String recordId) {
        return zjkBaseInfoMapper.selectByPrimaryKey(recordId);
    }

    @Override
    public int updateByExampleSelective(@Param("record") ZjkBaseInfo record, @Param("example") ZjkBaseInfoExample example) {
        return zjkBaseInfoMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(@Param("record") ZjkBaseInfo record, @Param("example") ZjkBaseInfoExample example) {
        return zjkBaseInfoMapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(ZjkBaseInfo record) {
        return zjkBaseInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ZjkBaseInfo record) {
        if (record.getStatus() == null) {
            record.setStatus("");
        }
        return zjkBaseInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public Integer deleteZjkBaseInfo(Serializable zjkBaseInfoId) {
        try {
            ZjkBaseInfo record = zjkBaseInfoMapper.selectByPrimaryKey(zjkBaseInfoId.toString());
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
        ZjkBaseInfoExample example = new ZjkBaseInfoExample();
        ZjkBaseInfoExample.Criteria c = example.createCriteria();
//        c.andStatusEqualTo("1");
//        if(param.getParam().get("fileKind") !=null && !com.pcitc.common.StringUtils.isBlank(param.getParam().get("fileKind")+""))
//        {
        //   c.andIdLike("'%"+param.getParam().get("fileKind")+"%'");
//            ZjkBaseInfoExample.Criteria criteria2 = example.or();
//            criteria2.andParentIdEqualTo(param.getParam().get("fileKind").toString());
//            example.or(criteria2);
        //       }
        example.setOrderByClause("create_date desc");
        return this.findByExample(param, example);

    }


    @Autowired
    private ZjkChengguoService zjkChengguoService;

    @Override
    public LayuiTableData findZjkBaseInfoByPageIndex(LayuiTableParam param) {
        Object hyly = param.getParam().get("hyly");//行业领域
        Object jg = param.getParam().get("jg");//机构(研究院)
        Object zjmc = param.getParam().get("zjmc");//专家名称
        Object key = param.getParam().get("key");//关键字

        Object nld = param.getParam().get("nld");//年龄段
        Object zc = param.getParam().get("zc");//职称

        Object gb = param.getParam().get("gb");//规避本院

        ZjkBaseInfoExample example = new ZjkBaseInfoExample();
        ZjkBaseInfoExample.Criteria c = example.createCriteria();

        if (hyly != null && !"".equals(hyly)) {
            c.andHylyIn(Arrays.asList(hyly.toString().split(",")));
//            ZjkBaseInfoExample.Criteria criteria2 = example.or();
//            criteria2.andParentIdEqualTo(param.getParam().get("fileKind").toString());
//            example.or(criteria2);
        }
        if (zc != null && !"".equals(zc)) {
            c.andZhichengIn(Arrays.asList(zc.toString().split(",")));
        }
        if (nld != null && !"".equals(nld)) {
            c.andAgeBetweenIn(Arrays.asList(nld.toString().split(",")));
        }
        if (jg != null && !"".equals(jg)) {//选择了机构
            List<String> jgList = Arrays.asList(jg.toString().split(","));
            if (gb != null && !"".equals(gb) && jgList.contains(gb)) {//选择了规避,移除
                jgList.remove(jgList.indexOf(gb));
            }
            c.andCompanyIn(jgList);
        } else {//只有规避机构
            c.andCompanyNotEqualTo(gb.toString());
        }
        if (zjmc != null && !"".equals(zjmc)) {
            c.andNameLike("%" + zjmc + "%");
        }
        if (key != null && !"".equals(key)) {
            //获取成果人员id
            ZjkChengguoExample chengguoExample = new ZjkChengguoExample();
            chengguoExample.createCriteria().andCgKeysIn(Arrays.asList(key.toString().split(",")));
            List<ZjkChengguo> zjkChengguos = zjkChengguoService.selectByExample(chengguoExample);
            //添加人员id查询条件
            List<String> strings = zjkChengguos.stream().map(ZjkChengguo::getZjId).collect(Collectors.toList());
            if (strings != null && strings.size() > 0 && !"".equals(strings)) {
                Set<String> set = new HashSet<>();
                set.addAll(strings);
                List<String> stringsSet = (List<String>) (List) Arrays.asList(set.toArray());
                c.andCompanyIn(stringsSet);
            }
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
    private LayuiTableData findByExample(LayuiTableParam param, ZjkBaseInfoExample example) {
        int pageSize = param.getLimit();
        int pageStart = (param.getPage() - 1) * pageSize;
        int pageNum = pageStart / pageSize + 1;
        PageHelper.startPage(pageNum, pageSize);
        List<ZjkBaseInfo> list = zjkBaseInfoMapper.selectByExample(example);
        // 3、获取分页查询后的数据
        PageInfo<ZjkBaseInfo> pageInfo = new PageInfo<ZjkBaseInfo>(list);
        LayuiTableData data = new LayuiTableData();
        data.setData(pageInfo.getList());
        Long total = pageInfo.getTotal();
        data.setCount(total.intValue());
        return data;
    }

    /**
     * 树形菜单
     *
     * @return
     */
    @Override
    public List<TreeNode> selectObjectByTree() {
        List<TreeNode> nodes = new ArrayList<TreeNode>();
        ZjkBaseInfoExample example = new ZjkBaseInfoExample();
        example.getOredCriteria().add(example.createCriteria().andStatusNotEqualTo(DataOperationStatusEnum.DEL_OK.getStatusCode().toString()));
        List<ZjkBaseInfo> records = zjkBaseInfoMapper.selectByExample(example);
        for (ZjkBaseInfo record : records) {
            TreeNode node = new TreeNode();
            node.setId(record.getId());
            //            node.setLevelCode(record.getUnitLevel().toString());
            node.setParentId(record.getParentId());
            nodes.add(node);
        }
        //构建树形结构(从根节点开始的树形结构)

        ZjkBaseInfoExample zjkBaseInfoExample = new ZjkBaseInfoExample();
        String strParentId = zjkBaseInfoMapper.selectByExample(zjkBaseInfoExample).get(0).getId();
        List<TreeNode> orderNodes = TreeNodeUtil.getChildrenNode(strParentId, nodes);

        return orderNodes;
    }


    @Autowired
    ZjkPicService zjkPicService;

    public JSONObject echarts(JSONObject jsonObject) {
        Result result = new Result();
        String id = jsonObject.get("id").toString();
        ZjkPic zjkPic = zjkPicService.selectByPrimaryKey(id);
        String sql = zjkPic.getSqlSql();
        String x = zjkPic.getX();
        String y = zjkPic.getY();
        String title = zjkPic.getTitle();

        Map<String,Object> param = (Map<String, Object>) jsonObject.get("param");
        for(Map.Entry<String,Object> e:param.entrySet()){
            sql =sql.replace("#{"+e.getKey()+"}","'"+e.getValue().toString()+"'");
        }

        if ("bar".equals(zjkPic.getEcharType())) {
            Map<String, Object> map = new HashMap<>();
            map.put("sqlval", sql);
            List<Map<String, Object>> maps = zjkBaseInfoMapper.listSqlResult(map);
            if ("1".equals(zjkPic.getIsDz())) {
                //转
//                String[][] data = ReverseSqlResult.reverdraSort(ReverseSqlResult.listToArray(maps));
                ChartSingleLineResultData csr = new ChartSingleLineResultData();
                List<Object> ySeries = new ArrayList<Object>();
                List<String> xData = new ArrayList<String>();
                for (int i = 0; i < maps.size(); i++) {
                    Map<String, Object> m = maps.get(i);
                    for (Map.Entry<String, Object> entry : m.entrySet()) {
                        String key = entry.getKey();
                        if (x.contains(key)) {
                            xData.add(entry.getValue().toString());
                        }else if (y.contains(key)) {
                            ySeries.add(entry.getValue());
                        }
                    }
                }
                csr.setSeriesDataList(ySeries);
                csr.setxAxisDataList(xData);
                result.setSuccess(true);
                result.setData(csr);
            }
        }

        jsonObject.put("result", result);
        return jsonObject;
    }
}