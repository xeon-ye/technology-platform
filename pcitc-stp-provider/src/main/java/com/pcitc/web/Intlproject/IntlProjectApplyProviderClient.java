package com.pcitc.web.Intlproject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.pcitc.base.common.DataTableParam;
import com.pcitc.base.common.LayuiTableParam;
import com.pcitc.base.common.Result;
import com.pcitc.base.stp.IntlProject.IntlProjectApply;
import com.pcitc.base.system.SysUser;
import com.pcitc.base.workflow.WorkflowVo;
import com.pcitc.common.MailBean;
import com.pcitc.service.intlproject.IntlProjectApplyService;
import com.pcitc.service.msg.MailSentService;
import com.pcitc.web.feign.SystemRemoteClient;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="项目申报接口",tags= {"国际合作-项目申报操作服务接口"})
@RestController
public class IntlProjectApplyProviderClient 
{
	private final static Logger logger = LoggerFactory.getLogger(IntlProjectApplyProviderClient.class);

	private final static String WORKFLOW_DEFINE_ID = "projectApply:1:150007";
	@Autowired
	private MailSentService mailSentService;
	
	@Autowired
	private IntlProjectApplyService projectApplyService;
	
	@Autowired
	private SystemRemoteClient systemRemoteClient;
	
	@ApiOperation(value="分页检索项目申报",notes="检索项目申报列表数据，返回数据列表。")
	@RequestMapping(value = "/stp-provider/project/apply-list", method = RequestMethod.POST)
	public Object selectApplyByPage(@RequestBody LayuiTableParam param) 
	{
		return projectApplyService.selectProjectApplyByPage(param);
	}
	
	@ApiOperation(value="添加项目申报",notes="添加项目申报信息。")
	@RequestMapping(value = "/stp-provider/project/add-apply", method = RequestMethod.POST)
	public Integer insertApplyInfo(@RequestBody IntlProjectApply apply) 
	{
		logger.info("save notice....");
		
		return projectApplyService.saveProjectApply(apply);
	}
	@ApiOperation(value="更新项目申报",notes="更新项目申报信息。")
	@RequestMapping(value = "/stp-provider/project/upd-apply", method = RequestMethod.POST)
	public Integer updApplyInfo(@RequestBody IntlProjectApply apply) 
	{
		logger.info("upd notice....");
	
		return projectApplyService.updProjectApply(apply);
	}
	@ApiOperation(value="关闭项目申报",notes="关闭项目申报信息（逻辑删除）。")
	@RequestMapping(value = "/stp-provider/project/close-apply/{applyId}", method = RequestMethod.POST)
	public Integer delApplyClose(@PathVariable("applyId") String applyId) 
	{
		logger.info("del apply....");
	
		return projectApplyService.delProjectApply(applyId);
	}
	@ApiOperation(value="删除项目申报",notes="删除项目申报信息（物理删除）。")
	@RequestMapping(value = "/stp-provider/project/del-apply/{applyId}", method = RequestMethod.POST)
	public Integer delApplyReal(@PathVariable("applyId") String applyId) 
	{
		logger.info("del apply real....");
	
		return projectApplyService.delProjectApplyReal(applyId);
	}
	@ApiOperation(value="获得申报项目详情",notes="根据申报ID获得项目申报信息。")
	@RequestMapping(value = "/stp-provider/project/get-apply/{applyId}", method = RequestMethod.POST)
	public IntlProjectApply findApplyInfo(@PathVariable("applyId") String applyId) 
	{
		logger.info("find notice....");
	
		return projectApplyService.findProjectApply(applyId);
	}
	@ApiOperation(value="启动项目申报审批",notes="启动项目申报信息审批流程。")
	@RequestMapping(value = "/stp-provider/project/start-apply-activity/{applyId}", method = RequestMethod.POST)
	public Integer satrtApplyActivity(@PathVariable("applyId") String applyId,@RequestBody WorkflowVo workflowVo) 
	{
		System.out.println("startwork  apply  ...."+applyId);
		IntlProjectApply apply = projectApplyService.findProjectApply(applyId);
		
		
		workflowVo.setProcessDefineId(WORKFLOW_DEFINE_ID); 
		workflowVo.setBusinessId(applyId);
		workflowVo.setProcessInstanceName("申报审批："+apply.getApplyName());
		Map<String, Object> variables = new HashMap<String, Object>();  
		//starter为必填项。流程图的第一个节点待办人变量必须为starter
        variables.put("starter", workflowVo.getAuthenticatedUserId());
        
        //必须设置。流程中，需要的第二个节点的指派人；除starter外，所有待办人变量都指定为auditor(处长审批)
        //处长审批 ZSH_JTZSZYC_GJHZC_CZ
        List<SysUser> users = systemRemoteClient.selectUsersByPostCode("ZSH_JTZSZYC_GJHZC_CZ");
        System.out.println("start userIds ... "+JSON.toJSONString(users));
        variables.put("auditor", workflowVo.getAuthenticatedUserId());
        if(users != null && users.size()>0) {
        	variables.put("auditor", users.get(0).getUserId());
        }
        
        //必须设置，统一流程待办任务中需要的业务详情
        variables.put("auditDetailsPath", "/intl_project/apply_view?applyId="+applyId);
       
        //流程完全审批通过时，调用的方法
        variables.put("auditAgreeMethod", "http://pcitc-zuul/stp-proxy/stp-provider/intl_project/notice_view?noticeId="+applyId);
        
        //流程驳回时，调用的方法（可能驳回到第一步，也可能驳回到第1+n步
        variables.put("auditRejectMethod", "http://pcitc-zuul/stp-proxy/stp-provider/intl_project/notice_view?noticeId="+applyId);
        
        workflowVo.setVariables(variables);
		String rs = systemRemoteClient.startWorkflowByProcessDefinitionId(workflowVo);
		System.out.println("startwork  apply  rs...."+rs);
		if("true".equals(rs)){
			return 1;
		}else{
			return 0;
		}
	}
	//工作流审批通过后回调通知相关人员
	@ApiOperation(value="项目申报邮件通知",notes="项目申报信息审批流程通过以后，下发邮件通知。")
	@RequestMapping(value = "/stp-provider/project/apply-mail-notice/{applyId}", method = RequestMethod.POST)
	public Integer sentApplyMail(@PathVariable("applyId") String applyId) 
	{
		logger.info("sent notice....");
		
		IntlProjectApply apply = projectApplyService.findProjectApply(applyId);
		
		mailSentService.sentMail(new MailBean("398078361@qq.com","项目审批通过",apply.getApplyContent()));
		
		return 1;
	}
	@ApiOperation(value="审批通过下发通知",notes="项目申报信息审批流程通过以后，下发相关通知逻辑。")
	@RequestMapping(value = "/stp-provider/project/apply-workflow-agree/{applyId}")
	public Object projectApplyWorkflowAgree(@PathVariable(value = "applyId", required = true)String applyId,HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("审批反馈，通过！！！！");
		return  new Result(true);
	}
	@ApiOperation(value="申报审批拒绝",notes="项目申报信息审批被驳回，相关处理逻辑。")
	@RequestMapping(value = "/stp-provider/project/apply-workflow-reject/{applyId}")
	public Object projectApplyWorkflowReject(@PathVariable(value = "applyId", required = true)String applyId,HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("审批反馈，驳回！！！！");
		return  new Result(true);
	}
	@ApiOperation(value="通过审批的项目列表",notes="获取审批通过的项目列表，编制计划时获取所有审批通过的项目申报。")
	@RequestMapping(value = "/stp-provider/project/get-pass-apply-list")
	public Object getWorkflowPassApplyProject(@RequestBody DataTableParam dataTableParam,HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("获取已通过的项目申报....."+JSON.toJSONString(dataTableParam));
		PageInfo<IntlProjectApply> pageInfo = projectApplyService.findByConn(dataTableParam);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("totalCont", pageInfo.getTotal());
		map.put("list", pageInfo.getList());
		System.out.println(JSON.toJSON(map).toString());
		return JSON.toJSON(map);
	}
	@ApiOperation(value="计划已选申报列表",notes="获取已加入计划的申报项目列表。")
	@RequestMapping(value = "/stp-provider/project/join-plant-apply-list")
	public Object getJoinPlantApplyList(@RequestBody LayuiTableParam param,HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("start.....");
		Object obj =  projectApplyService.findJoinPlantApply(param);
		System.out.println( JSON.toJSON(obj).toString());
		return obj;
	
	}
	@ApiOperation(value="计划可选申报列表",notes="获取未加入计划的申报项目列表。")
	@RequestMapping(value = "/stp-provider/project/notjoin-plant-apply-list")
	public Object getNotJoinPlantApplyList(@RequestBody LayuiTableParam param,HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("start.....");
		Object obj =   projectApplyService.findNotJoinPlantApply(param);
		System.out.println( JSON.toJSON(obj).toString());
		return obj;
		
	}
	@ApiOperation(value="可立项申报列表",notes="获取申报审批和计划审批的申报项目列表。")
	@RequestMapping(value = "/stp-provider/project/get-pass-plant-apply-list")
	public Object getPlantWorkflowPassApplyProject(@RequestBody LayuiTableParam param,HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("获取已通过详见计划审查的项目申报.....");
		return projectApplyService.findJoinPlantAndPassApply(param);
	}
	
	@ApiOperation(value="检索项目申报",notes="检索项目申报列表数据，返回数据列表(不分页)。")
	@RequestMapping(value = "/stp-provider/project/apply-list-all", method = RequestMethod.POST)
	public Object selectApplyListAll(Map<String, Object> params) 
	{
		return projectApplyService.selectProjectApplyList(params);
	}
	@ApiOperation(value="创建项目编码",notes="创建申报项目的编码")
	@RequestMapping(value = "/stp-provider/project/project-code", method = RequestMethod.POST)
	public Object creatApplyCode(@RequestBody IntlProjectApply apply) 
	{
		return projectApplyService.createProjectApplyCode(apply);
	}
}
