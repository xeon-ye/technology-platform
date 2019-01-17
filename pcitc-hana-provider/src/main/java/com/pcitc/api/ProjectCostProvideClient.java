package com.pcitc.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pcitc.base.common.LayuiTableData;
import com.pcitc.base.common.LayuiTableParam;
import com.pcitc.service.ICommonService;
import com.pcitc.service.IProjectCostService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Decision-API",description = "项目成本分析相关的接口")
@RestController
@RequestMapping("/hana/projectCost") 
public class ProjectCostProvideClient {

	@Autowired
	private IProjectCostService scientificFundsService;
	
	@Autowired
	private ICommonService commonService;
	
	
	@ApiOperation(value = "科研投资统计表", notes = "科研投资统计表")
	@RequestMapping(value = "/kytztjb", method = RequestMethod.POST)
	public LayuiTableData kytztjb(@RequestBody LayuiTableParam param)throws Exception
	{
		return scientificFundsService.getKytztjbData(param);
	}
	

	
	@ApiOperation(value = "科技经费统计表", notes = "科技经费统计表")
	@RequestMapping(value = "/kjjftjb", method = RequestMethod.POST)
	public LayuiTableData kjjftjb(@RequestBody LayuiTableParam param)throws Exception
	{
		return scientificFundsService.getKjjftjbData(param);
	}
	
	
	
	
	@ApiOperation(value = "横向课题全成本统计表", notes = "横向课题全成本统计表")
	@RequestMapping(value = "/hxktqcbtjb", method = RequestMethod.POST)
	public LayuiTableData hxktqcbtjb(@RequestBody LayuiTableParam param)throws Exception
	{
		return scientificFundsService.getHxktqcbtjbData(param);
	}
	
}
