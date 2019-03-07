package com.pcitc.web.controller.equipment;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.bcel.Const;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pcitc.base.common.Constant;
import com.pcitc.base.common.LayuiTableData;
import com.pcitc.base.common.LayuiTableParam;
import com.pcitc.base.common.Result;
import com.pcitc.base.common.WorkFlowStatusEnum;
import com.pcitc.base.common.enums.RequestProcessStatusEnum;
import com.pcitc.base.stp.IntlProject.IntlProjectNotice;
import com.pcitc.base.stp.equipment.SreProject;
import com.pcitc.base.system.SysUser;
import com.pcitc.base.util.CodeUtil;
import com.pcitc.base.util.CommonUtil;
import com.pcitc.base.util.DateUtil;
import com.pcitc.base.util.IdUtil;
import com.pcitc.base.workflow.Constants;
import com.pcitc.base.workflow.WorkflowVo;
import com.pcitc.web.common.BaseController;
import com.pcitc.web.utils.EquipmentUtils;

@Controller
@RequestMapping(value = "/sre-project-basic")
public class ProjectBasicController extends BaseController {

	/*
	 * 1、可以直接通过注册的服务名来访问，来实现访问和负载。不过如果用zuul的话， 要用zuul的服务名和实际访问的服务名一起
	 * 2、pplus本身是一个微服务，属于微服务之间的调用，可以直接用名称，不用ip.（注意启动类中的注解）
	 */

	private static final String PAGE_URL = "http://pcitc-zuul/stp-proxy/sre-provider/project_basic/page";
	private static final String ADD_URL = "http://pcitc-zuul/stp-proxy/sre-provider/project_basic/add";
	private static final String UPDATE_URL = "http://pcitc-zuul/stp-proxy/sre-provider/project_basic/update";
	private static final String DEL_URL = "http://pcitc-zuul/stp-proxy/sre-provider/project_basic/delete/";
	private static final String BATCH_DEL_URL = "http://pcitc-zuul/stp-proxy/sre-provider/project_basic/batch-delete/";
	private static final String GET_URL = "http://pcitc-zuul/stp-proxy/sre-provider/project_basic/get/";
	private static final String LIST_EQUIPMENT_BY_IDS = "http://pcitc-zuul/stp-proxy/sre-provider/equipment/list-by-ids/";

	// 流程操作--同意
	private static final String AUDIT_AGREE_URL = "http://pcitc-zuul/stp-proxy/sre-provider/project/task/agree/";
	// 流程操作--拒绝
	private static final String AUDIT_REJECT_URL = "http://pcitc-zuul/stp-proxy/sre-provider/project/task/reject/";
	
	private static final String get_user_bypostcode = "http://pcitc-zuul/system-proxy/user-provider/user/get-user-bypostcode/";
	
	private final static String process_define_id4 = "equitmentApplyProcess:1:1172522";
	
	
	private static final String PROJECT_WORKFLOW_URL = "http://pcitc-zuul/stp-proxy/stp-provider/start_project_activity/";


	@RequestMapping(value = "/to-audit-list")
	public String auditlist(HttpServletRequest request, HttpServletResponse response) {
		return "/stp/equipment/project/audit-list";
	}

	@RequestMapping(value = "/to-list")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		return "/stp/equipment/project/project-basic-list";
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public String ajaxlist(@ModelAttribute("param") LayuiTableParam param, HttpServletRequest request, HttpServletResponse response) {
		
		JSONObject parmamss = JSONObject.parseObject(JSONObject.toJSONString(param));
		
		String applyUnitCode=sysUserInfo.getUnitCode();
		param.getParam().put("applyUnitCode", applyUnitCode);
		
		LayuiTableData layuiTableData = new LayuiTableData();
		HttpEntity<LayuiTableParam> entity = new HttpEntity<LayuiTableParam>(param, httpHeaders);
		ResponseEntity<LayuiTableData> responseEntity = restTemplate.exchange(PAGE_URL, HttpMethod.POST, entity, LayuiTableData.class);
		int statusCode = responseEntity.getStatusCodeValue();
		if (statusCode == 200) {
			layuiTableData = responseEntity.getBody();
		}
		JSONObject result = JSONObject.parseObject(JSONObject.toJSONString(layuiTableData));
		logger.info("============查询结果：" + result);
		return result.toString();
	}

	/**
	 * 增加
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		
		
		String leadUnitName = sysUserInfo.getUnitName();
		String leadUnitCode = sysUserInfo.getUnitCode();
		String createUserName=sysUserInfo.getUserDisp();
		String createUserId=sysUserInfo.getUserName();
		String documentDoc= IdUtil.createFileIdByTime();
		String beginYear=EquipmentUtils.getCurrrentYear();
		String endYear=String.valueOf(Integer.valueOf(beginYear).intValue()+1);
		String id = CommonUtil.getParameter(request, "id", "");
		request.setAttribute("id", id);
		if(!id.equals(""))
		{
			ResponseEntity<SreProject> responseEntity = this.restTemplate.exchange(GET_URL + id, HttpMethod.GET, new HttpEntity<Object>(this.httpHeaders), SreProject.class);
			int statusCode = responseEntity.getStatusCodeValue();
		
			SreProject sreEquipment = responseEntity.getBody();
			request.setAttribute("sreProjectBasic", sreEquipment);
			
			leadUnitName = sreEquipment.getLeadUnitName();
			leadUnitCode = sreEquipment.getLeadUnitCode();
			createUserId= sreEquipment.getCreateUserId();
			documentDoc=sreEquipment.getDocumentDoc();
			beginYear		= sreEquipment.getBeginYear();
			endYear		= sreEquipment.getEndYear();
		}
		request.setAttribute("documentDoc", documentDoc);
		request.setAttribute("leadUnitName", leadUnitName);
		request.setAttribute("leadUnitCode", leadUnitCode);
		request.setAttribute("createUserId", createUserId);
		
		
		request.setAttribute("endYear", endYear);
		request.setAttribute("beginYear", beginYear);
		logger.info("============远程返回  beginYear " + beginYear);
		return "/stp/equipment/project/project-basic-add";
	}
	
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/project_basic_add_selectapply")
	private String project_basic_add_selectapply(HttpServletRequest request) 
	{
		String plantId = request.getParameter("equipmentIds");
		request.setAttribute("equipmentIds", plantId==null?IdUtil.createIdByTime():plantId);
		
		return "/stp/equipment/project/project_basic_add_selectapply";
		
    }
	

	/**
	 * 保存-更新操作
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/save")
	public String saveOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Result resultsDate = new Result();
		String name = CommonUtil.getParameter(request, "name", "");
		// 业务ID
		String id = CommonUtil.getParameter(request, "id", "");
		// 流程状态-是保存还是提交
		String auditStatus = CommonUtil.getParameter(request, "auditStatus", Constant.AUDIT_STATUS_DRAFT);
		String userIds = CommonUtil.getParameter(request, "userIds", "");
		String equipmentIds = CommonUtil.getParameter(request, "equipmentIds", "");
		String remarks = CommonUtil.getParameter(request, "remarks", "");
		String beginYear = CommonUtil.getParameter(request, "beginYear", "");
		String endYear = CommonUtil.getParameter(request, "endYear", "");
		String projectType = CommonUtil.getParameter(request, "projectType", "");
		String keyWord = CommonUtil.getParameter(request, "keyWord", "");
		String projectLeader = CommonUtil.getParameter(request, "projectLeader", "");
		String erpNum = CommonUtil.getParameter(request, "erpNum", "");
		String documentDoc = CommonUtil.getParameter(request, "documentDoc", "");
		String joinUnitName = CommonUtil.getParameter(request, "joinUnitName", "");
		String joinUnitCode = CommonUtil.getParameter(request, "joinUnitCode", "");
		String leadLinkmansName = CommonUtil.getParameter(request, "leadLinkmansName", "");
		String leadLinkmansCode = CommonUtil.getParameter(request, "leadLinkmansCode", "");
		String leadUnitType = CommonUtil.getParameter(request, "leadUnitType", "");
		String professional = CommonUtil.getParameter(request, "professional", "");
		String projectChargesName = CommonUtil.getParameter(request, "projectChargesName", "");
		String projectChargesCode = CommonUtil.getParameter(request, "projectChargesCode", "");
		String contractNum = CommonUtil.getParameter(request, "contractNum", "");
		String setupYear = CommonUtil.getParameter(request, "setupYear", "");
		String leadUnitName = CommonUtil.getParameter(request, "leadUnitName", "");
		String leadUnitCode = CommonUtil.getParameter(request, "leadUnitCode", "");
		String entrustUnitCode = CommonUtil.getParameter(request, "entrustUnitCode", "");
		String entrustUnitName = CommonUtil.getParameter(request, "entrustUnitName", "");
		String isWorkFlow = CommonUtil.getParameter(request, "isWorkFlow", "0");
		String functionId = CommonUtil.getParameter(request, "functionId", "");
		String yearFeeStr = CommonUtil.getParameter(request, "yearFeeStr", "");
		
		String belongDepartmentName = CommonUtil.getParameter(request, "belongDepartmentName", "");
		String belongDepartmentCode = CommonUtil.getParameter(request, "belongDepartmentCode", "");
		String professionalDepartName = CommonUtil.getParameter(request, "professionalDepartName", "");
		String professionalDepartCode = CommonUtil.getParameter(request, "professionalDepartCode", "");
		String taskWriteUserNames = CommonUtil.getParameter(request, "taskWriteUserNames", "");
		String taskWriteUsersIds = CommonUtil.getParameter(request, "taskWriteUsersIds", "");
		
		
		
		
		SreProject sreProjectBasic = null;
		ResponseEntity<String> responseEntity = null;
		// 判断是新增还是修改
		if (id.equals("")) 
		{
			sreProjectBasic = new SreProject();
			sreProjectBasic.setCreateDate(new Date());
			sreProjectBasic.setCreateUserId(sysUserInfo.getUserId());
			sreProjectBasic.setCreateUserName(sysUserInfo.getUserDisp());
			String code = CodeUtil.getCode("XTBM_0032", restTemplate, httpHeaders);
			String idv = UUID.randomUUID().toString().replaceAll("-", "");
			sreProjectBasic.setId(idv);
			sreProjectBasic.setAuditStatus(auditStatus);
		} else 
		{
			ResponseEntity<SreProject> se = this.restTemplate.exchange(GET_URL + id, HttpMethod.GET, new HttpEntity<Object>(this.httpHeaders), SreProject.class);
			sreProjectBasic = se.getBody();
		}
		// 流程状态
		sreProjectBasic.setAuditStatus(auditStatus);
		BigDecimal projectMoney=BigDecimal.ZERO;
		if (!yearFeeStr.equals("")) //2019,55,5,60#2020,553,5,558
		{
			String array[]=yearFeeStr.split("#");
			if(array!=null && array.length>0)
			{
				for(int i=0;i<array.length;i++)
				{
					String year=array[i];
					String arr[]=year.split(",");
					projectMoney=projectMoney.add(new BigDecimal(arr[3]));
				}
			}
			sreProjectBasic.setProjectMoney(projectMoney);
		}
		sreProjectBasic.setYearFeeStr(yearFeeStr); 
		sreProjectBasic.setDocumentDoc(documentDoc); 
		sreProjectBasic.setName(name);
		sreProjectBasic.setEquipmentIds(equipmentIds);
		sreProjectBasic.setSetupYear(DateUtil.dateToStr(new Date(), DateUtil.FMT_YYYY));
		sreProjectBasic.setRemarks(remarks);
		sreProjectBasic.setBeginYear(beginYear);
		sreProjectBasic.setEndYear(endYear);
		sreProjectBasic.setKeyWord(keyWord);
		sreProjectBasic.setProjectType(projectType);
		sreProjectBasic.setProjectLeader(projectLeader);
		sreProjectBasic.setJoinUnitName(joinUnitName);
		sreProjectBasic.setJoinUnitCode(joinUnitCode);
		sreProjectBasic.setLeadLinkmansName(leadLinkmansName);
		sreProjectBasic.setLeadLinkmansCode(leadLinkmansCode);
		sreProjectBasic.setLeadUnitType(leadUnitType);
		sreProjectBasic.setErpNum(erpNum);
		sreProjectBasic.setProfessional(professional);
		sreProjectBasic.setBelongDepartmentName(belongDepartmentName); 
		sreProjectBasic.setBelongDepartmentCode(belongDepartmentCode);
		sreProjectBasic.setProfessionalDepartName(professionalDepartName);
		sreProjectBasic.setProfessionalDepartCode(professionalDepartCode);
		sreProjectBasic.setProjectChargesName(projectChargesName);
		sreProjectBasic.setProjectChargesCode(projectChargesCode);
		sreProjectBasic.setIsContract("0");
		sreProjectBasic.setContractNum(contractNum);
		sreProjectBasic.setSetupYear(setupYear);
		sreProjectBasic.setLeadUnitName(leadUnitName);
		sreProjectBasic.setLeadUnitCode(leadUnitCode);
		sreProjectBasic.setEntrustUnitCode(entrustUnitCode);
		sreProjectBasic.setEntrustUnitName(entrustUnitName);
		sreProjectBasic.setApplyUnitCode(sysUserInfo.getUnitCode());
		sreProjectBasic.setApplyUnitName(sysUserInfo.getUnitName());
		sreProjectBasic.setTaskWriteUserNames(taskWriteUserNames);
		sreProjectBasic.setTaskWriteUsersIds(taskWriteUsersIds);
		
		// 判断是新增还是修改
		if (id.equals("")) 
		{
			responseEntity = this.restTemplate.exchange(ADD_URL, HttpMethod.POST, new HttpEntity<SreProject>(sreProjectBasic, this.httpHeaders), String.class);

		} else {
			responseEntity = this.restTemplate.exchange(UPDATE_URL, HttpMethod.POST, new HttpEntity<SreProject>(sreProjectBasic, this.httpHeaders), String.class);
		}
		// 返回结果代码
		int statusCode = responseEntity.getStatusCodeValue();
		if (statusCode == 200)
		{
			//如果是提交
			if(isWorkFlow.equals("1"))
			{
				String dataId = sreProjectBasic.getId();
				// 处理流程相关信息
				boolean flowFlag = dealProjectWorkFlow(dataId, functionId,sysUserInfo, "计划上报:"+sreProjectBasic.getName(), userIds, httpHeaders);
				if (flowFlag == true)
				{
					resultsDate = new Result(true, RequestProcessStatusEnum.OK.getStatusDesc());
				} else 
				{
					resultsDate = new Result(false, RequestProcessStatusEnum.SERVER_BUSY.getStatusDesc());
				}
			}
		} else 
		{
			resultsDate = new Result(false, RequestProcessStatusEnum.SERVER_BUSY.getStatusDesc());
		}

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject ob = JSONObject.parseObject(JSONObject.toJSONString(resultsDate));
		out.println(ob.toString());
		out.flush();
		out.close();
		return null;
	}
	
	

	/**
	 * 加入流程信息
	 * 
	 * @param id
	 * @param instanceName
	 * @param sysUser
	 */
	private boolean dealProjectWorkFlow(String id,String functionId, SysUser sysUser, String instanceName, String userIds, HttpHeaders httpHeaders)
	{
		WorkflowVo workflowVo = new WorkflowVo();
		workflowVo.setBusinessId(String.valueOf(id));
		workflowVo.setProcessInstanceName(instanceName);
		workflowVo.setAuthenticatedUserId(sysUser.getUserId());
		workflowVo.setAuditUserIds(sysUser.getUserId());
		// process_define_id和functionId，两种方式二选一
		// 清楚知道自己要走的流程定义id
		workflowVo.setProcessDefineId(process_define_id4);
		// 不清楚此功能菜单要走的审批流程。可以通过菜单id（functionId），部门/组织ID（orgId），项目id（id）。其中菜单id必填（和ProcessDefineId两选一）
		workflowVo.setFunctionId(functionId);
		workflowVo.setProjectId("");
		Map<String, Object> variables = new HashMap<String, Object>();
		//variables.put("starter", workflowVo.getAuthenticatedUserId());
		
		 //必须设置。流程中，需要的第二个节点的指派人；除starter外，所有待办人变量都指定为auditor(处长审批)
        //处长审批 ZSH_JTZSZYC_GJHZC_CZ
		/*ResponseEntity<List> responseEntity = this.restTemplate.exchange(get_user_bypostcode + "ZSH_JTZSZYC_GJHZC_CZ", HttpMethod.POST, new HttpEntity<Object>(this.httpHeaders), List.class);
		int statusCode = responseEntity.getStatusCodeValue();
		if (statusCode == 200)
		{
			List<SysUser> users= responseEntity.getBody();
	        System.out.println("start userIds ... "+JSON.toJSONString(users));
	        variables.put("auditor", workflowVo.getAuthenticatedUserId());
	        if(users != null && users.size()>0)
	        {
	        	variables.put("auditor", users.get(0).getUserId());
	        }
	        
		}*/
		// 发起人之后的审批环节，如果是需要选择审批人的话，此处获取选择的userIds赋值给auditor变量
		if (userIds != null && !userIds.equals("")) 
		{
			String[] userIdsArr = userIds.split(",");
			variables.put("auditor", Arrays.asList(userIdsArr));
		}
		// 必须设置，统一流程待办任务中需要的业务详情
		variables.put("auditDetailsPath", "/sre-project-basic/get/" + id);
		// 流程完全审批通过时，调用的方法
		variables.put("auditAgreeMethod", AUDIT_AGREE_URL + id);
		// 流程驳回时，调用的方法（可能驳回到第一步，也可能驳回到第1+n步
		variables.put("auditRejectMethod", AUDIT_REJECT_URL + id);
		workflowVo.setVariables(variables);
		ResponseEntity<String> status = this.restTemplate.exchange(Constants.START_WORKFLOW_URL, HttpMethod.POST, new HttpEntity<WorkflowVo>(workflowVo, httpHeaders), String.class);
		if (status.getBody() != null && status.getBody().equals("true"))
		{
			System.out.println("=================流程启动成功");
			SreProject	sreProject = this.getSreProject(id);
			sreProject.setAuditStatus(Constant.AUDIT_STATUS_SUBMIT);
			this.updateSreProject(sreProject);
			return true;
		} else
		{
			System.out.println("=================流程启动失败");
			return false;
		}
	}
	
	
	
	private String updateSreProject(SreProject sreProject)
	{
		String str="";
		ResponseEntity<String> responseEntity =this.restTemplate.exchange(UPDATE_URL, HttpMethod.POST, new HttpEntity<SreProject>(sreProject, this.httpHeaders), String.class);
		int statusCode = responseEntity.getStatusCodeValue();
		if (statusCode == 200)
		{
			str = responseEntity.getBody();
		}
		return str;
	}
	
	
	private SreProject getSreProject(String id)
	{
		SreProject	sreProjectBasic = null;
		ResponseEntity<SreProject> responseEntity = this.restTemplate.exchange(GET_URL + id, HttpMethod.GET, new HttpEntity<Object>(this.httpHeaders), SreProject.class);
		int statusCode = responseEntity.getStatusCodeValue();
		if (statusCode == 200)
		{
			sreProjectBasic = responseEntity.getBody();
		}
		return sreProjectBasic;
	}
	
	
	    
		

	

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Result resultsDate = new Result();
		ResponseEntity<Integer> responseEntity = this.restTemplate.exchange(DEL_URL + id, HttpMethod.POST, new HttpEntity<Object>(this.httpHeaders), Integer.class);
		int statusCode = responseEntity.getStatusCodeValue();
		int status = responseEntity.getBody();
		logger.info("============远程返回  statusCode " + statusCode + "  status=" + status);
		if (responseEntity.getBody() > 0) {
			resultsDate = new Result(true);
		} else {
			resultsDate = new Result(false, "删除失败，请联系系统管理员！");
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject ob = JSONObject.parseObject(JSONObject.toJSONString(resultsDate));
		out.println(ob.toString());
		out.flush();
		out.close();
		return null;
	}

	@RequestMapping(value = "/pass-back/{id}")
	public String passBackOPT(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Result resultsDate = new Result();
		String status = CommonUtil.getParameter(request, "status", "");
		String checkMoney = CommonUtil.getParameter(request, "checkMoney", "");
		ResponseEntity<SreProject> responseEntity = this.restTemplate.exchange(GET_URL + id, HttpMethod.GET, new HttpEntity<Object>(this.httpHeaders), SreProject.class);
		int statusCode = responseEntity.getStatusCodeValue();
		logger.info("============远程返回  statusCode " + statusCode);
		SreProject sreProjectBasic = responseEntity.getBody();
		if (!checkMoney.equals("")) {
			sreProjectBasic.setProjectMoney(new BigDecimal(checkMoney));
		}
		ResponseEntity<Integer> responseE = this.restTemplate.exchange(UPDATE_URL, HttpMethod.POST, new HttpEntity<SreProject>(sreProjectBasic, this.httpHeaders), Integer.class);
		// 返回结果代码
		int status_Code = responseE.getStatusCodeValue();
		if (responseE.getBody() > 0) {
			resultsDate = new Result(true);
		} else {
			resultsDate = new Result(false, "操作失败，请联系系统管理员！");
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject ob = JSONObject.parseObject(JSONObject.toJSONString(resultsDate));
		out.println(ob.toString());
		out.flush();
		out.close();
		return null;
	}

	@RequestMapping(value = "/bacth-delete")
	public String deleteBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Result resultsDate = new Result();
		ResponseEntity<Integer> responseEntity = null;
		String chkboxstr = CommonUtil.getParameter(request, "ids", "");
		String chkbox[] = chkboxstr.split(",");
		System.out.println("--------chkboxstr=" + chkboxstr + " chkbox=" + chkbox.length);

		if (chkbox != null && chkbox.length > 0) {
			List<String> list = Arrays.asList(chkbox);
			List<Long> longList = new ArrayList();
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					String str = list.get(i);
					longList.add(Long.valueOf(str));
				}
			}
			JSONArray jsonObject = JSONArray.parseArray(JSON.toJSONString(longList));
			HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), httpHeaders);
			responseEntity = restTemplate.exchange(BATCH_DEL_URL, HttpMethod.POST, entity, Integer.class);

		}
		int statusCode = responseEntity.getStatusCodeValue();
		int status = responseEntity.getBody();
		logger.info("============远程返回  statusCode " + statusCode + "  status=" + status);
		if (responseEntity.getBody() > 0) {
			resultsDate = new Result(true);
		} else {
			resultsDate = new Result(false, "删除失败，请联系系统管理员！");
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject ob = JSONObject.parseObject(JSONObject.toJSONString(resultsDate));
		out.println(ob.toString());
		out.flush();
		out.close();
		return null;

	}
	
	
	/**
	 * 详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public String get(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ResponseEntity<SreProject> responseEntity = this.restTemplate.exchange(GET_URL + id, HttpMethod.GET, new HttpEntity<Object>(this.httpHeaders), SreProject.class);
		int statusCode = responseEntity.getStatusCodeValue();
		logger.info("============远程返回  statusCode " + statusCode);
		SreProject sreProjectBasic = responseEntity.getBody();
		request.setAttribute("sreProjectBasic", sreProjectBasic);
		
		
		
		String leadUnitName = sreProjectBasic.getLeadUnitName();
		String leadUnitCode = sreProjectBasic.getLeadUnitCode();
		String createUserId= sreProjectBasic.getCreateUserId();
		String documentDoc=sreProjectBasic.getDocumentDoc();
		String beginYear		= sreProjectBasic.getBeginYear();
		String endYear		= sreProjectBasic.getEndYear();
	
		request.setAttribute("documentDoc", documentDoc);
		request.setAttribute("leadUnitName", leadUnitName);
		request.setAttribute("leadUnitCode", leadUnitCode);
		request.setAttribute("createUserId", createUserId);
		request.setAttribute("endYear", endYear);
		request.setAttribute("beginYear", beginYear);
	
	
		return "/stp/equipment/project/project-basic-view";
	}
	
	
	
	//计划审核
	@RequestMapping(value = "/audit", method = RequestMethod.GET)
	public String audit( HttpServletRequest request, HttpServletResponse response) throws Exception {

		return "/stp/equipment/project/audit";
	}
	
	@RequestMapping(value = "/audit_look", method = RequestMethod.GET)
	public String audit_look( HttpServletRequest request, HttpServletResponse response) throws Exception {

		return "/stp/equipment/project/audit_look";
	}
	
	
	

}
