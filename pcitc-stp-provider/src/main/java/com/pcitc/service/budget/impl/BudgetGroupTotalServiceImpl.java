package com.pcitc.service.budget.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pcitc.base.common.LayuiTableData;
import com.pcitc.base.common.LayuiTableParam;
import com.pcitc.base.stp.budget.BudgetGroupTotal;
import com.pcitc.base.stp.budget.BudgetGroupTotalExample;
import com.pcitc.base.stp.out.OutUnit;
import com.pcitc.base.util.MyBeanUtils;
import com.pcitc.mapper.budget.BudgetGroupTotalMapper;
import com.pcitc.service.budget.BudgetGroupTotalService;
import com.pcitc.service.feign.SystemRemoteClient;

@Service("budgetGroupTotalService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class BudgetGroupTotalServiceImpl implements BudgetGroupTotalService
{

	@Autowired
	private BudgetGroupTotalMapper budgetGroupTotalMapper;
	
	@Resource
	private SystemRemoteClient systemRemoteClient;
	
	@Override
	public BudgetGroupTotal selectBudgetGroupTotal(String dataId) throws Exception
	{
		return budgetGroupTotalMapper.selectByPrimaryKey(dataId);
	}

	@Override
	public Integer updateBudgetGroupTotal(BudgetGroupTotal groupTotal) throws Exception
	{
		
		return budgetGroupTotalMapper.updateByPrimaryKey(groupTotal);
	}

	@Override
	public int deleteBudgetGroupTotal(String id) throws Exception
	{
		BudgetGroupTotal group = budgetGroupTotalMapper.selectByPrimaryKey(id);
		if(group != null) 
		{
			return budgetGroupTotalMapper.deleteByPrimaryKey(id);
		}
		return 0;
	}

	@Override
	public List<BudgetGroupTotal> selectBudgetGroupTotalListByIds(List<String> list) throws Exception
	{
		BudgetGroupTotalExample example = new BudgetGroupTotalExample();
		BudgetGroupTotalExample.Criteria c = example.createCriteria();
		c.andDataIdIn(list);
		return budgetGroupTotalMapper.selectByExample(example);
	}

	@Override
	public Integer saveOrUpdateBudgetGroupTotal(BudgetGroupTotal budgetGroupTotal) throws Exception
	{
		BudgetGroupTotal old = budgetGroupTotalMapper.selectByPrimaryKey(budgetGroupTotal.getDataId());
		if(old == null) {
			return budgetGroupTotalMapper.insert(budgetGroupTotal);
		}else {
			MyBeanUtils.copyProperties(budgetGroupTotal, old);
			return budgetGroupTotalMapper.updateByPrimaryKey(old);
		}
	}

	@Override
	public List<BudgetGroupTotal> selectBudgetInfoId(String budgetInfoId) throws Exception
	{
		BudgetGroupTotalExample example = new BudgetGroupTotalExample();
		BudgetGroupTotalExample.Criteria c = example.createCriteria();
		c.andBudgetInfoIdEqualTo(budgetInfoId);
		c.andLevelEqualTo(0);//只显示第一级
		example.setOrderByClause("no");
		return budgetGroupTotalMapper.selectByExample(example);
	}

	@Override
	public LayuiTableData selectBudgetGroupTotalPage(LayuiTableParam param) throws Exception
	{
		BudgetGroupTotalExample example = new BudgetGroupTotalExample();
		BudgetGroupTotalExample.Criteria c = example.createCriteria();
		c.andBudgetInfoIdEqualTo(param.getParam().get("budget_info_id").toString());
		c.andLevelEqualTo(0);//只显示第一级
		example.setOrderByClause("no");
		//return this.findByExample(param, example);
		LayuiTableData tabledata = this.findByExample(param, example);
		List<Map<String,Object>> ls = new ArrayList<Map<String,Object>>();
		for(java.util.Iterator<?> iter = tabledata.getData().iterator();iter.hasNext();) 
		{
			Map<String,Object> mp  = MyBeanUtils.transBean2Map(iter.next());
			mp.put("total", new Double(mp.get("zxjf").toString())+new Double(mp.get("xmjf").toString()));
			ls.add(mp);
		}
		tabledata.setData(ls);
		return tabledata;
	}
	private LayuiTableData findByExample(LayuiTableParam param,BudgetGroupTotalExample example) 
	{
		//每页显示条数
		int pageSize = param.getLimit();
		//从第多少条开始
		int pageStart = (param.getPage()-1)*pageSize;
		//当前是第几页
		int pageNum = pageStart/pageSize + 1;
		// 1、设置分页信息，包括当前页数和每页显示的总计数
		PageHelper.startPage(pageNum, pageSize);
		
		List<BudgetGroupTotal> list = budgetGroupTotalMapper.selectByExample(example);
		// 3、获取分页查询后的数据
		PageInfo<BudgetGroupTotal> pageInfo= new PageInfo<BudgetGroupTotal>(list);
		LayuiTableData data = new LayuiTableData();
		data.setData(pageInfo.getList());
		Long total = pageInfo.getTotal();
		data.setCount(total.intValue());
		return data;
	}

	@Override
	public int deleteBudgetGroupTotalByInfo(String budgetInfoId) throws Exception
	{
		BudgetGroupTotalExample example = new BudgetGroupTotalExample();
		BudgetGroupTotalExample.Criteria c = example.createCriteria();
		c.andBudgetInfoIdEqualTo(budgetInfoId);
		List<BudgetGroupTotal> list = budgetGroupTotalMapper.selectByExample(example);
		
		Integer rs = 0;
		for(BudgetGroupTotal group:list) 
		{
			rs += budgetGroupTotalMapper.deleteByPrimaryKey(group.getDataId());
		}
		return rs;
	}

	@Override
	public List<BudgetGroupTotal> selectChildBudgetGroupTotal(String dataId)
	{
		BudgetGroupTotalExample example = new BudgetGroupTotalExample();
		BudgetGroupTotalExample.Criteria c = example.createCriteria();
		c.andParentDataIdEqualTo(dataId);
		c.andLevelEqualTo(1);//只显示第二级
		example.setOrderByClause("no");
		return budgetGroupTotalMapper.selectByExample(example);
	}

	@Override
	public List<OutUnit> selectJtUnits()
	{
		List<OutUnit> units = systemRemoteClient.selectProjectUnits("JTZS");
		return units;
	}

}
