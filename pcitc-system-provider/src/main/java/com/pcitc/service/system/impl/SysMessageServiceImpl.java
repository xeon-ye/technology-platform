/**
 * 
 */
package com.pcitc.service.system.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pcitc.base.common.LayuiTableData;
import com.pcitc.base.common.LayuiTableParam;
import com.pcitc.base.system.SysMessage;
import com.pcitc.base.system.SysMessageExample;
import com.pcitc.base.system.SysMessageExample.Criteria;
import com.pcitc.mapper.system.SysMessageMapper;
import com.pcitc.service.system.SysMessageService;

/**
 * @author zhf
 * 系统消息
 *
 */
@Service("sysMessageService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class SysMessageServiceImpl implements SysMessageService {

	@Autowired
	private SysMessageMapper sysMessageMapper;
	
	/**
	 * 新增消息
	 */
	@Override
	public int insertSysMessage(SysMessage sysMessage) {
		sysMessageMapper.insert(sysMessage);
		return 0;
	}
	
	/**
	 * 查询用户消息列表数据
	 */
	@Override
	public LayuiTableData getMessageListData(LayuiTableParam param) {
		Map<String,Object> paraMap = param.getParam();
		
		SysMessageExample sysMessageExample = new SysMessageExample();
		Criteria cri = sysMessageExample.createCriteria();
		if (paraMap.get("messageTitle") != null && !paraMap.get("messageTitle").equals("")) {
			cri.andMessageTitleLike("%" + paraMap.get("messageTitle").toString()  + "%");
		}
		if (paraMap.get("userId") != null && !paraMap.get("userId").equals("")) {
			cri.andUserIdLike("%" + paraMap.get("userId").toString() + "%");
		}
		if (paraMap.get("messageType") != null && !paraMap.get("messageType").equals("")) {
			cri.andMessageTypeEqualTo(paraMap.get("messageType").toString());
		}
		sysMessageExample.setOrderByClause(" create_date desc ");
		
		// 1、设置分页信息，包括当前页数和每页显示的总计数
		PageHelper.startPage(param.getPage(), param.getLimit());
		
		List<SysMessage> list = sysMessageMapper.selectByExample(sysMessageExample);
		// 3、获取分页查询后的数据
		PageInfo<SysMessage> pageInfo = new PageInfo<SysMessage>(list);
		LayuiTableData data = new LayuiTableData();
		data.setData(pageInfo.getList());
		Long total = pageInfo.getTotal();
		data.setCount(total.intValue());
		return data;
	}

}