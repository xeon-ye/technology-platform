package com.pcitc.service.flow.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pcitc.base.stp.flow.FlowProjectInfo;
import com.pcitc.mapper.flow.FlowProjectInfoMapper;
import com.pcitc.service.flow.FlowProjectInfoService;

@Service("flowProjectInfoService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FlowProjectInfoServiceImpl implements FlowProjectInfoService {

	@Autowired
	private FlowProjectInfoMapper flowProjectInfoMapper;
	
	/**
	 * 批量插入全流程信息
	 * 
	 * @param flowProjectInfo
	 * @return
	 * @throws Exception
	 */
	public Integer batchInsertFlowProjectInfo(FlowProjectInfo flowProjectInfo) throws Exception {
		return 1;
	}

}
