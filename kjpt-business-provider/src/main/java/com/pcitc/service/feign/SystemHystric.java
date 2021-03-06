package com.pcitc.service.feign;

import java.util.List;

import org.springframework.stereotype.Component;

import com.pcitc.base.common.LayuiTableData;
import com.pcitc.base.common.LayuiTableParam;
import com.pcitc.base.system.SysDictionary;
import com.pcitc.base.system.SysFunction;
import com.pcitc.base.system.SysMeeting;
import com.pcitc.base.system.SysPost;
import com.pcitc.base.system.SysUser;
import com.pcitc.base.workflow.WorkflowVo;

@Component
public class SystemHystric implements SystemRemoteClient {

	@Override
	public String startWorkflowByProcessDefinitionId(WorkflowVo workflowVo) {
		System.out.println("执行断路器。。。。。。。。");
		throw new RuntimeException("调用失败.");
	}

	@Override
	public List<SysUser> selectUsersByPostCode(String postCode) {
		System.out.println("执行断路器。。。。。。。。");
		throw new RuntimeException("调用失败.");
	}

	@Override
	public SysPost getSysPostByCode(String postCode) {
		System.out.println("执行断路器。。。。。。。。");
		throw new RuntimeException("调用失败.");
	}

	@Override
	public List<SysDictionary> getDictionaryListByParentCode(String parentCode) {
		getSysPostByCode("获取字典异常");
		throw new RuntimeException("调用失败.");
	}



	@Override
	public LayuiTableData selectProjectPlanByCond(LayuiTableParam param) {
		System.out.println("执行断路器。。。。。。。。");
		throw new RuntimeException("调用失败.");
	}

	@Override
	public LayuiTableData selectCommonProjectByCond(LayuiTableParam param) {
		System.out.println("执行断路器。。。。。。。。");
		throw new RuntimeException("调用失败.");
	}

	@Override
	public String insertSysMeeting(SysMeeting sysMeeting) {
		System.out.println("执行断路器。。。。。。。。");
		throw new RuntimeException("调用失败.");
	}

    @Override
    public LayuiTableData getOutAppraisalListPage(LayuiTableParam param) throws Exception {
        System.out.println("执行断路器。。。。。。。。");
        throw new RuntimeException("调用失败.");    }

    @Override
    public LayuiTableData selectOutPatentList(LayuiTableParam param) {
        System.out.println("执行断路器。。。。。。。。");
        throw new RuntimeException("调用失败.");    }

    @Override
    public LayuiTableData getOutRewardListPage(LayuiTableParam param) throws Exception {
        System.out.println("执行断路器。。。。。。。。");
        throw new RuntimeException("调用失败.");    }

    @Override
    public LayuiTableData getOutProjectListPageExpert(LayuiTableParam param) throws Exception {
        System.out.println("执行断路器。。。。。。。。");
        throw new RuntimeException("调用失败.");
    }



	@Override
	public SysFunction getFunctionByUrl(String url) {
		 System.out.println("执行断路器。。。。。。。。");
	     throw new RuntimeException("调用失败.");
	}


}
