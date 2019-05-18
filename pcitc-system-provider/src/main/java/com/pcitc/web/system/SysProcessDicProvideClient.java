package com.pcitc.web.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pcitc.base.common.LayuiTableData;
import com.pcitc.base.common.LayuiTableParam;
import com.pcitc.base.stp.system.SysProcessDic;
import com.pcitc.service.system.SysProcessDicService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "SysProcessDic-API",tags = {"全流程模型维护"})
@RestController
public class SysProcessDicProvideClient {
	
	private final static Logger logger = LoggerFactory.getLogger(SysProcessDicProvideClient.class); 
	@Autowired
    private SysProcessDicService sysProcessDicService; 
	
	
	
	@ApiOperation(value = "全流程模型维护分页", notes = "全流程模型维护分页")
	@RequestMapping(value = "/system-provider/sys_process_dic/page", method = RequestMethod.POST)
	public LayuiTableData getSysProcessDicList(@RequestBody LayuiTableParam param)throws Exception
	{
		LayuiTableData rageResult=sysProcessDicService.getSysProcessDicPage(param) ;
		return rageResult;
	}
	
	@ApiOperation(value = "增加全流程模型维护", notes = "增加全流程模型维护")
	@RequestMapping(value = "/system-provider/sys_process_dic/add", method = RequestMethod.POST)
	public String insertSysProcessDic(@RequestBody SysProcessDic sysProcessDic) throws Exception{
		logger.info("====================add SysProcessDic....========================");
		Integer count= sysProcessDicService.insertSysProcessDic(sysProcessDic) ;
		return sysProcessDic.getId();
	}
	
	
	@ApiOperation(value = "修改全流程模型维护", notes = "修改全流程模型维护")
	@RequestMapping(value = "/system-provider/sys_process_dic/update", method = RequestMethod.POST)
	public Integer updateSysProcessDic(@RequestBody SysProcessDic sysProcessDic) throws Exception{
		logger.info("==================update SysProcessDic===========================");
		return sysProcessDicService.updateSysProcessDic(sysProcessDic);
	}
	
	
	@RequestMapping(value = "/system-provider/sys_process_dic/delete/{id}", method = RequestMethod.POST)
	public int deleteSysProcessDic(@PathVariable("id") String id)throws Exception{
		logger.info("=============================delete SysProcessDic=================");
		return sysProcessDicService.deleteSysProcessDic(id);
	}
	
	
	
	@ApiOperation(value = "获取全流程模型维护", notes = "根据ID获取全流程模型维护")
	@RequestMapping(value = "/system-provider/sys_process_dic/get/{id}", method = RequestMethod.GET)
	public SysProcessDic selectUserByUserId(@PathVariable(value = "id", required = true) String id) throws Exception {
		logger.info("===============================get SysProcessDic id "+id+"===========");
		return sysProcessDicService.selectSysProcessDic(id);
	}
	

}