package com.pcitc.service.equipment;

import com.pcitc.base.common.LayuiTableData;
import com.pcitc.base.common.LayuiTableParam;

public interface InvestService {
	
	
	
	
	//投资进度分页方法
	public LayuiTableData getInvestmentrogressPage(LayuiTableParam param)throws Exception;
	//采购进度方法
	public LayuiTableData getProcurementProgramPage(LayuiTableParam param);
	
	
	
	
	
	
	
	

}