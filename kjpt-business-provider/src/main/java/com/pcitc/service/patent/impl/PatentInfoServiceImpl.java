package com.pcitc.service.patent.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pcitc.base.util.IsEmptyUtil;
import com.pcitc.mapper.patent.PatentInfoMapper;
import com.pcitc.service.patent.PatentInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>接口实现类</p>
 * <p>Table: patent_info - 专利信息</p>
 *
 * @since 2019-11-16 04:10:36
 */
@Service("PatentInfoService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PatentInfoServiceImpl implements PatentInfoService {

    @Resource
    private PatentInfoMapper patentInfoMapper;

    @Override
    public PatentInfo updateOrInsertPatentInfo(PatentInfo patentInfo) {
        IsEmptyUtil.isEmpty(patentInfo.getId());
        if(Objects.nonNull(this.getPatentInfo(patentInfo.getId()))){
            patentInfoMapper.updateByPrimaryKey(patentInfo);
        }else{
            patentInfoMapper.insertSelective(patentInfo);
        }
        return patentInfo;
    }

    @Override
    public PageInfo queryPatentList(Map param) {
        int pageNum = (int)param.get("pageNum");
        int pageSize = (int)param.get("pageSize");
        PageHelper.startPage(pageNum, pageSize);
        List dataList = patentInfoMapper.queryPatentList(param);
        PageInfo pageInfo = new PageInfo(dataList);

        return pageInfo;
    }

    public PatentInfo getPatentInfo(String id){
        return patentInfoMapper.selectByPrimaryKey(id);
    }

    public Integer deletePatent(String id) {
        return patentInfoMapper.deleteByPrimaryKey(id);
    }
}