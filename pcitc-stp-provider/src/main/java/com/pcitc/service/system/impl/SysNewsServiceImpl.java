package com.pcitc.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pcitc.base.common.LayuiTableData;
import com.pcitc.base.common.LayuiTableParam;
import com.pcitc.base.common.enums.DataOperationStatusEnum;
import com.pcitc.base.common.enums.DelFlagEnum;
import com.pcitc.base.common.TreeNode;
import com.pcitc.base.system.*;
import com.pcitc.base.system.SysNewsExample;
import com.pcitc.base.util.IdUtil;
import com.pcitc.base.util.StrUtil;
import com.pcitc.base.util.TreeNodeUtil;
import com.pcitc.mapper.system.SysNewsMapper;
import com.pcitc.service.system.SysNewsService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>接口实现类</p>
 * <p>Table: sys_news - 系统新闻表</p>
 *
 * @since 2019-04-04 10:25:32
 */
@Service("sysNewsService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class SysNewsServiceImpl implements SysNewsService {

    @Autowired
    private SysNewsMapper sysNewsMapper;

    public List<SysNews> findSysNewsList(SysNews sysNews) {
        List<SysNews> record = sysNewsMapper.findSysNewsList(sysNews);
        return record;
    }

    @Override
    public int updateOrInsertSysNews(SysNews sysNews) throws Exception {
        int result = 500;
        if (sysNews.getDataId() != null && sysNews.getDataId() != null) {
            sysNewsMapper.updateByPrimaryKeySelective(sysNews);
        } else {
            sysNews.setDataId(IdUtil.createIdByTime());
            sysNewsMapper.insertSelective(sysNews);
        }
        result = 200;
        return result;
    }

    @Override
    public int deleteSysNewsById(String id) throws Exception {
        int result = 500;
        if (id != null) {
            sysNewsMapper.deleteByPrimaryKey(id);
        }
        result = 200;
        return result;
    }

    @Override
    public SysNews getSysNewsInfo(String id) throws Exception {

        return sysNewsMapper.selectByPrimaryKey(id);
    }

    @Override
    public long countByExample(SysNewsExample example) {
        return sysNewsMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(SysNewsExample example) {
        return sysNewsMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(String recordId) {
        return sysNewsMapper.deleteByPrimaryKey(recordId);
    }

    @Override
    public Integer deleteSysNewsReal(String recordId) {
        return sysNewsMapper.deleteByPrimaryKey(recordId);
    }

    @Override
    public int insert(SysNews record) {
        record.setDataId(IdUtil.createIdByTime());
        return sysNewsMapper.insert(record);
    }

    @Override
    public int insertSelective(SysNews record) {
        return sysNewsMapper.insertSelective(record);
    }

    public SysNews insertObject(SysNews record) {
        this.insert(record);
        return record;
    }

    @Override
    public List<SysNews> selectByExample(SysNewsExample example) {
        return sysNewsMapper.selectByExample(example);
    }

    @Override
    public SysNews selectByPrimaryKey(String recordId) {
        return sysNewsMapper.selectByPrimaryKey(recordId);
    }

    @Override
    public int updateByExampleSelective(@Param("record") SysNews record, @Param("example") SysNewsExample example) {
        return sysNewsMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(@Param("record") SysNews record, @Param("example") SysNewsExample example) {
        return sysNewsMapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(SysNews record) {
        return sysNewsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysNews record) {
        if (record.getStatus() == null) {
            record.setStatus("");
        }
        return sysNewsMapper.updateByPrimaryKey(record);
    }

    @Override
    public Integer deleteSysNews(Serializable sysNewsId) {
        try {
            SysNews record = sysNewsMapper.selectByPrimaryKey(sysNewsId.toString());
            if (record != null) {
                record.setStatus(DelFlagEnum.STATUS_DEL.getCode() + "");
                sysNewsMapper.updateByPrimaryKey(record);
            }
            return Integer.parseInt(String.valueOf(DataOperationStatusEnum.DEL_OK));
        } catch (Exception e) {
            return Integer.parseInt(String.valueOf(DataOperationStatusEnum.DEL_DATA_ERROR));
        }
    }

    @Override
    public LayuiTableData findSysNewsByPage(LayuiTableParam param) {
        SysNewsExample example = new SysNewsExample();
        SysNewsExample.Criteria c = example.createCriteria();
        Object title = param.getParam().get("title");
        if (!StrUtil.isObjectEmpty(title)) {
            c.andTitleLike("%" + title + "%");

        }
        Object status = param.getParam().get("status");
        if (!StrUtil.isObjectEmpty(status)) {
            c.andStatusLike("%" + status + "%");

        }
        Object author = param.getParam().get("author");
        if (!StrUtil.isObjectEmpty(author)) {
            c.andAuthorLike("%" + author + "%");

        }
        Object publicDate = param.getParam().get("publicDate");
        if (!StrUtil.isObjectEmpty(publicDate)) {
            c.andPublicDateLike("%" + publicDate + "%");

        }
        Object content = param.getParam().get("content");
        if (!StrUtil.isObjectEmpty(content)) {
            c.andContentLike("%" + content + "%");

        }
        Object stype = param.getParam().get("stype");
        if (!StrUtil.isObjectEmpty(stype)) {
            c.andStypeLike("%" + stype + "%");

        }
//        c.andStatusEqualTo("1");
//        if(param.getParam().get("fileKind") !=null && !com.pcitc.common.StringUtils.isBlank(param.getParam().get("fileKind")+""))
//        {
        //   c.andIdLike("'%"+param.getParam().get("fileKind")+"%'");
//            SysNewsExample.Criteria criteria2 = example.or();
//            criteria2.andParentIdEqualTo(param.getParam().get("fileKind").toString());
//            example.or(criteria2);
        //       }
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
    private LayuiTableData findByExample(LayuiTableParam param, SysNewsExample example) {
        int pageSize = param.getLimit();
        int pageStart = (param.getPage() - 1) * pageSize;
        int pageNum = pageStart / pageSize + 1;
        PageHelper.startPage(pageNum, pageSize);
        List<SysNews> list = sysNewsMapper.selectByExample(example);
        // 3、获取分页查询后的数据
        PageInfo<SysNews> pageInfo = new PageInfo<SysNews>(list);
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
        SysNewsExample example = new SysNewsExample();
        //example.getOredCriteria().add(example.createCriteria().andStatusNotEqualTo(DataOperationStatusEnum.DEL_OK.getStatusCode().toString()));
        List<SysNews> records = sysNewsMapper.selectByExample(example);
        for (SysNews record : records) {
            TreeNode node = new TreeNode();
            node.setId(record.getId());
            //            node.setLevelCode(record.getUnitLevel().toString());
            node.setParentId(record.getParentId());
            nodes.add(node);
        }
        //构建树形结构(从根节点开始的树形结构)

        SysNewsExample sysNewsExample = new SysNewsExample();
        String strParentId = sysNewsMapper.selectByExample(sysNewsExample).get(0).getId();
        List<TreeNode> orderNodes = TreeNodeUtil.getChildrenNode(strParentId, nodes);

        return orderNodes;
    }
}