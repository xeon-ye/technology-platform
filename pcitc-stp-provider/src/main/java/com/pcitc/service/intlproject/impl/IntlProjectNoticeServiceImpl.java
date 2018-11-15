package com.pcitc.service.intlproject.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pcitc.base.common.LayuiTableData;
import com.pcitc.base.common.LayuiTableParam;
import com.pcitc.base.common.enums.DelFlagEnum;
import com.pcitc.base.stp.IntlProject.IntlProjectNotice;
import com.pcitc.base.stp.IntlProject.IntlProjectNoticeExample;
import com.pcitc.mapper.IntlProject.IntlProjectNoticeMapper;
import com.pcitc.service.intlproject.IntlProjectNoticeService;

@Service("intlProjectNoticeService")
public class IntlProjectNoticeServiceImpl implements IntlProjectNoticeService {

	@Autowired
	private IntlProjectNoticeMapper projectNoticeMapper;
	
	@Override
	public LayuiTableData selectProjectNoticeList(LayuiTableParam param) 
	{
		IntlProjectNoticeExample example = new IntlProjectNoticeExample();
		IntlProjectNoticeExample.Criteria criteria = example.createCriteria();
		
		criteria.andDelFlagEqualTo(DelFlagEnum.STATUS_NORMAL.getCode());
		if(param.getParam().get("noticeTitle") != null && !StringUtils.isBlank(param.getParam().get("noticeTitle")+""))
		{
			criteria.andNoticeTitleLike("%"+param.getParam().get("noticeTitle")+"%");
		}
		return this.findByExample(param, example);
	}

	@Override
	public IntlProjectNotice findById(String noticeId) 
	{
		return projectNoticeMapper.selectByPrimaryKey(noticeId);
	}
	private LayuiTableData findByExample(LayuiTableParam param,IntlProjectNoticeExample example) 
	{
		//每页显示条数
		int pageSize = param.getLimit();
		//从第多少条开始
		int pageStart = (param.getPage()-1)*pageSize;
		//当前是第几页
		int pageNum = pageStart/pageSize + 1;
		// 1、设置分页信息，包括当前页数和每页显示的总计数
		PageHelper.startPage(pageNum, pageSize);
		
		List<IntlProjectNotice> list = projectNoticeMapper.selectByExample(example);
		// 3、获取分页查询后的数据
		PageInfo<IntlProjectNotice> pageInfo= new PageInfo<IntlProjectNotice>(list);
		LayuiTableData data = new LayuiTableData();
		data.setData(pageInfo.getList());
		Long total = pageInfo.getTotal();
		data.setCount(total.intValue());
		return data;
	}

	@Override
	public Integer saveProjectNotice(IntlProjectNotice notice) 
	{
		return projectNoticeMapper.insert(notice);
	}

	@Override
	public Integer updProjectNotice(IntlProjectNotice notice) 
	{
		return projectNoticeMapper.updateByPrimaryKey(notice);
	}

	@Override
	public Integer delProjectNotice(String noticeId) 
	{
		IntlProjectNotice notice = projectNoticeMapper.selectByPrimaryKey(noticeId);
		if(notice != null) 
		{
			return projectNoticeMapper.deleteByPrimaryKey(noticeId);
		}
		return 0;
	} 
}
