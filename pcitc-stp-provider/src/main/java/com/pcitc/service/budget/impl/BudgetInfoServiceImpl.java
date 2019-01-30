package com.pcitc.service.budget.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pcitc.base.common.LayuiTableData;
import com.pcitc.base.common.LayuiTableParam;
import com.pcitc.base.common.enums.DelFlagEnum;
import com.pcitc.base.stp.budget.BudgetInfo;
import com.pcitc.base.stp.budget.BudgetInfoExample;
import com.pcitc.base.util.MyBeanUtils;
import com.pcitc.common.WorkFlowStatusEnum;
import com.pcitc.mapper.budget.BudgetInfoMapper;
import com.pcitc.service.budget.BudgetInfoService;

@Service("budGetInfoService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class BudgetInfoServiceImpl implements BudgetInfoService
{

	@Autowired
	private BudgetInfoMapper budgetInfoMapper;
	
	@Override
	public BudgetInfo selectBudgetInfo(String dataId) throws Exception
	{
		return budgetInfoMapper.selectByPrimaryKey(dataId);
	}

	@Override
	public Integer updateBudgetInfo(BudgetInfo groupTotal) throws Exception
	{
		
		return budgetInfoMapper.updateByPrimaryKey(groupTotal);
	}

	@Override
	public int deleteBudgetInfo(String id) throws Exception
	{
		BudgetInfo info = budgetInfoMapper.selectByPrimaryKey(id);
		if(info != null) 
		{
			info.setDelFlag(DelFlagEnum.STATUS_DEL.getCode());
			return budgetInfoMapper.updateByPrimaryKey(info);
		}
		return 0;
	}

	@Override
	public List<BudgetInfo> selectBudgetInfoListByIds(List<String> list) throws Exception
	{
		BudgetInfoExample example = new BudgetInfoExample();
		BudgetInfoExample.Criteria c = example.createCriteria();
		c.andDataIdIn(list);
		return budgetInfoMapper.selectByExample(example);
	}

	@Override
	public Integer insertBudgetInfo(BudgetInfo BudgetInfo) throws Exception
	{
		return budgetInfoMapper.insert(BudgetInfo);
	}

	@Override
	public List<BudgetInfo> selectBudgetInfoList(String nd,Integer budgetType) throws Exception
	{
		BudgetInfoExample example = new BudgetInfoExample();
		BudgetInfoExample.Criteria c = example.createCriteria();
		c.andDelFlagEqualTo(DelFlagEnum.STATUS_NORMAL.getCode());
		c.andNdEqualTo(nd);
		c.andBudgetTypeEqualTo(budgetType);
		example.setOrderByClause("update_time DESC");
		return budgetInfoMapper.selectByExample(example);
	}

	@Override
	public LayuiTableData selectBudgetInfoPage(LayuiTableParam param) throws Exception
	{
		BudgetInfoExample example = new BudgetInfoExample();
		BudgetInfoExample.Criteria c = example.createCriteria();
		c.andDelFlagEqualTo(DelFlagEnum.STATUS_NORMAL.getCode());
		c.andBudgetTypeEqualTo(new Integer(param.getParam().get("budget_type").toString()));
		c.andNdEqualTo(param.getParam().get("nd").toString());
		example.setOrderByClause("data_version");
		return this.findByExample(param, example);
	}
	private LayuiTableData findByExample(LayuiTableParam param,BudgetInfoExample example) 
	{
		//每页显示条数
		int pageSize = param.getLimit();
		//从第多少条开始
		int pageStart = (param.getPage()-1)*pageSize;
		//当前是第几页
		int pageNum = pageStart/pageSize + 1;
		// 1、设置分页信息，包括当前页数和每页显示的总计数
		PageHelper.startPage(pageNum, pageSize);
		
		List<BudgetInfo> list = budgetInfoMapper.selectByExample(example);
		// 3、获取分页查询后的数据
		PageInfo<BudgetInfo> pageInfo= new PageInfo<BudgetInfo>(list);
		LayuiTableData data = new LayuiTableData();
		data.setData(pageInfo.getList());
		Long total = pageInfo.getTotal();
		data.setCount(total.intValue());
		return data;
	}

	@Override
	public Integer createBlankBudgetInfo(String nd,Integer budgetType)
	{
		BudgetInfo params = (BudgetInfo) MyBeanUtils.createDefaultModel(BudgetInfo.class);
		params.setAuditStatus(WorkFlowStatusEnum.STATUS_WAITING.getCode());
		params.setBudgetType(budgetType);
		params.setNd(nd);
		params.setBudgetMoney(0d);
		Integer size = budgetInfoMapper.selectByExample(new BudgetInfoExample()).size();
		params.setDataVersion("vs-"+nd+"-"+budgetType+"-"+((1001+size)+"").substring(1));
		return budgetInfoMapper.insert(params);
	}

}
