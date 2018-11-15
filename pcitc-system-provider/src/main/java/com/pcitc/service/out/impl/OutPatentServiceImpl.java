package com.pcitc.service.out.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pcitc.base.common.LayuiTableData;
import com.pcitc.base.common.LayuiTableParam;
import com.pcitc.base.stp.out.OutPatent;
import com.pcitc.base.stp.out.OutPatentExample;
import com.pcitc.mapper.out.OutPatentMapper;
import com.pcitc.service.out.OutPatentService;

@Service("outPatentService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class OutPatentServiceImpl implements OutPatentService {

	@Autowired
	private OutPatentMapper outPatentMapper;

	private final static Logger logger = LoggerFactory.getLogger(OutPatentServiceImpl.class);

	public int insertPatentData(List<OutPatent> list) {
		// 删除年度数据
		// OutPatentExample example = new OutPatentExample();
		// outPatentMapper.deleteByExample(example);

		// 批量插入数据
		outPatentMapper.insertOutPatentBatch(list);
		return 1;
	}

	public LayuiTableData getOutPatentPage(LayuiTableParam param) {
		Map<String, Object> paraMap = param.getParam();

		// 1、设置分页信息，包括当前页数和每页显示的总计数
		PageHelper.startPage(param.getPage(), param.getLimit());

		OutPatentExample example = new OutPatentExample();
		OutPatentExample.Criteria criteria = example.createCriteria();

		if (paraMap.get("fmmc") != null && !paraMap.get("fmmc").equals("")) {
			criteria.andFmmcLike("%" + paraMap.get("fmmc").toString() + "%");
		}
		if (paraMap.get("zlh") != null && !paraMap.get("zlh").equals("")) {
			criteria.andZlhLike("%" + paraMap.get("zlh").toString() + "%");
		}
		example.setOrderByClause(" remarks desc ");

		List<OutPatent> list = outPatentMapper.selectByExample(example);
		PageInfo<OutPatent> pageInfo = new PageInfo<OutPatent>(list);
		System.out.println(">>>>>>>>>查询分页结果" + pageInfo.getList().size());

		LayuiTableData data = new LayuiTableData();
		data.setData(pageInfo.getList());
		Long total = pageInfo.getTotal();
		data.setCount(total.intValue());
		return data;
	}

	/**
	 * 查询年份的专利数量
	 */
	public int getOutPatentCount(String nd, String userId) {

		OutPatentExample example = new OutPatentExample();
		OutPatentExample.Criteria criteria = example.createCriteria();
		criteria.andRemarksLike("%" + nd + "%");
		criteria.andFlztyjEqualTo("有效");
		criteria.andFlztejEqualTo("授权");

		return outPatentMapper.countByExample(example);
	}

	/**
	 * 查询最大的导入日期，从这个日期开始导入
	 * 
	 * @return
	 */
	public String getMaxImportDate() {
		return outPatentMapper.getMaxImportDate();
	}

	public List getWXLXInfo(String nd) {
		return outPatentMapper.getWXLXInfo(nd);
	}

	/**
	 * @param nd
	 * @return 得到某个年度各专利类型在各个研究院的分布
	 */
	public List getTypeInfoByUnit(String nd) {
		return outPatentMapper.getTypeInfoByUnit(nd);
	}

	/**
	 * @param nd
	 * @return 得到某个年度各专利类型在各个研究院、分公司、集团、外部单位、研发中心的分布
	 */
	public List getApplyAgreeCountBySix(String nd) {
		return outPatentMapper.getApplyAgreeCountBySix(nd);
	}

	@Override
	public LayuiTableData getWXLXDetailsInfo(LayuiTableParam param) {
		// 每页显示条数
		int pageSize = param.getLimit();
		// 当前是第几页
		int pageNum = param.getPage();
		// 1、设置分页信息，包括当前页数和每页显示的总计数
		PageHelper.startPage(pageNum, pageSize);
		List list = outPatentMapper.getWXLXDetailsInfo(param.getParam());

		// 2、获取分页查询后的数据
		LayuiTableData data = new LayuiTableData();
		data.setData(list);
		data.setCount(list.size());

		return data;
	}

	/**
	 * @param nd
	 * @return 得到某个年度各专利类型在各个研究院的分布 详情页面
	 */
	public LayuiTableData getTypeInfoByUnitDetails(LayuiTableParam param) {
		// 每页显示条数
		int pageSize = param.getLimit();
		// 当前是第几页
		int pageNum = param.getPage();
		// 1、设置分页信息，包括当前页数和每页显示的总计数
		PageHelper.startPage(pageNum, pageSize);
		List list = outPatentMapper.getTypeInfoByUnitDetails(param.getParam());

		// 2、获取分页查询后的数据
		LayuiTableData data = new LayuiTableData();
		data.setData(list);
		data.setCount(list.size());

		return data;
	}
}
