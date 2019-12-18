package com.pcitc.web.controller.achieve;

import com.github.pagehelper.PageInfo;
import com.pcitc.base.achieve.AchieveBase;
import com.pcitc.base.achieve.AchieveRecord;
import com.pcitc.base.achieve.AchieveReward;
import com.pcitc.base.achieve.AchieveSubmit;
import com.pcitc.base.common.Result;
import com.pcitc.base.system.SysPost;
import com.pcitc.base.system.SysUser;
import com.pcitc.base.util.CommonUtil;
import com.pcitc.base.util.DateUtil;
import com.pcitc.web.common.RestBaseController;
import com.pcitc.web.utils.EquipmentUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>成果转换备案</p>
 * <p>Table:  achieve_record 成果转换备案</p>
 * @author
 */
@Api(value = "achieveRecord-api", description = "成果转换备案")
@RestController
public class AchieveRecordController extends RestBaseController {
    /**
     * 根据ID获取对象信息
     */
    private static final String load = "http://kjpt-zuul/stp-proxy/achieveRecord-api/load/";
    /**
     * 根据ID获取成果对象信息
     */
    private static final String loadAchieveBase = "http://kjpt-zuul/stp-proxy/achieve-api/load/";
    /**
     * 查询列表
     */
    private static final String query = "http://kjpt-zuul/stp-proxy/achieveRecord-api/query";
    /**
     * 保存
     */
    private static final String save = "http://kjpt-zuul/stp-proxy/achieveRecord-api/save";
    /**
     * 简单保存
     */
    private static final String simpleSave = "http://kjpt-zuul/stp-proxy/achieveRecord-api/simpleSave";
    /**
     * 删除
     */
    private static final String delete = "http://kjpt-zuul/stp-proxy/achieveRecord-api/delete/";
    
    /**
     * 流程
     */
    private static final String WORKFLOW_URL = "http://kjpt-zuul/stp-proxy/achieveRecord-api/task/start_activity/";
    
    


    @ApiOperation(value="读取")
    @RequestMapping(value = "/achieveRecord-api/load/{id}", method = RequestMethod.GET)
    @ResponseBody
    public AchieveRecord load(@PathVariable String id) {
        ResponseEntity<AchieveRecord> responseEntity = this.restTemplate.exchange(load+id, HttpMethod.GET, new HttpEntity(this.httpHeaders), AchieveRecord.class);
        return responseEntity.getBody();
    }




    @ApiOperation(value = "查询列表", notes = "查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "achieveName", value = "成果名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "finishUnitName", value = "完成单位", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "auditStatus", value = "备案状态", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "录入开始时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "录入结束时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "achieveType", value = "成果类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "grantUnitName", value = "成果受让单位", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "achieveTransType", value = "转化方式", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "aboutCompleteInfo", value = "完成情况", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "secretLevel", value = "密级", dataType = "string", paramType = "query")
    })
    @RequestMapping(value = "/achieveRecord-api/query", method = RequestMethod.GET)
    @ResponseBody
    public PageInfo query(
            @RequestParam(required = false,value = "pageNum") Integer pageNum,
            @RequestParam(required = false,value = "pageSize") Integer pageSize,
            @RequestParam(required = false,value = "achieveName") String achieveName,
            @RequestParam(required = false,value = "finishUnitName") String finishUnitName,
            @RequestParam(required = false,value = "auditStatus") String auditStatus,
            @RequestParam(required = false,value = "startDate")@DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
            @RequestParam(required = false,value = "endDate")@DateTimeFormat(pattern="yyyy-MM-dd") Date endDate,
            @RequestParam(required = false,value = "achieveType") String achieveType,
            @RequestParam(required = false,value = "grantUnitName") String grantUnitName,
            @RequestParam(required = false,value = "achieveTransType") String achieveTransType,
            @RequestParam(required = false,value = "aboutCompleteInfo") String aboutCompleteInfo,
            @RequestParam(required = false,value = "secretLevel") String secretLevel


    ) throws Exception {
        Map<String, Object> condition = new HashMap<>(6);
        SysUser sysUserInfo = this.getUserProfile();
        if (pageNum == null) {
            this.setParam(condition, "pageNum", 1);
        }else {
            this.setParam(condition, "pageNum", pageNum);
        }
        if (pageSize == null) {
            this.setParam(condition, "pageSize", 10);
        }else {
            this.setParam(condition, "pageSize", pageSize);
        }
        if (!StringUtils.isEmpty(achieveName)) {
            this.setParam(condition, "achieveName", achieveName);
        }
        if (!StringUtils.isEmpty(finishUnitName)) {
            this.setParam(condition, "finishUnitName", finishUnitName);
        }
        if (!StringUtils.isEmpty(achieveType)) {
            this.setParam(condition, "achieveType", achieveType);
        }
        if (!StringUtils.isEmpty(auditStatus)) {
            this.setParam(condition, "auditStatus", auditStatus);
        }
        if (!StringUtils.isEmpty(grantUnitName)) {
            this.setParam(condition, "grantUnitName", grantUnitName);
        }
        if (!StringUtils.isEmpty(achieveTransType)) {
            this.setParam(condition, "achieveTransType", achieveTransType);
        }
        if (!StringUtils.isEmpty(aboutCompleteInfo)) {
            this.setParam(condition, "aboutCompleteInfo", aboutCompleteInfo);
        }
        if (!StringUtils.isEmpty(DateUtil.format(startDate,DateUtil.FMT_SS))) {
            this.setParam(condition, "startDate", DateUtil.format(startDate,DateUtil.FMT_SS));
        }
        if (!StringUtils.isEmpty(DateUtil.format(endDate,DateUtil.FMT_SS))) {
            this.setParam(condition, "endDate", DateUtil.format(endDate,DateUtil.FMT_SS));
        }


        if(secretLevel != null){
            this.setParam(condition,"secretLevel",secretLevel);
        }
        this.setParam(condition,"userSecretLevel",sysUserInfo.getSecretLevel());

        //默认查询当前人所在机构下所有的成果备案
        String childUnitIds= EquipmentUtils.getAllChildsByIUnitPath(sysUserInfo.getUnitPath(), restTemplate, httpHeaders);
        this.setParam(condition,"childUnitIds",childUnitIds);

        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<PageInfo> responseEntity = this.restTemplate.exchange(query, HttpMethod.POST, new HttpEntity<Map>(condition, this.httpHeaders), PageInfo.class);
        return responseEntity.getBody();
    }

    @ApiOperation(value="保存")
    @RequestMapping(value = "/achieveRecord-api/save", method = RequestMethod.POST)
    @ResponseBody
    public AchieveSubmit save(@RequestBody AchieveSubmit as){
        this.setBaseData(as);
        setRecord(as);
        as.getAchieveRecord().setAuditStatus("0");
        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        this.restTemplate.exchange(save, HttpMethod.POST, new HttpEntity<AchieveSubmit>(as, this.httpHeaders), AchieveSubmit.class);
        return as;
    }

    @ApiOperation(value="简单保存")
    @RequestMapping(value = "/achieveRecord-api/simpleSave", method = RequestMethod.POST)
    @ResponseBody
    public AchieveSubmit simpleSave(@RequestBody AchieveSubmit as){
        this.setBaseData(as);
        setRecord(as);
        as.getAchieveRecord().setAuditStatus("0");
        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        this.restTemplate.exchange(simpleSave, HttpMethod.POST, new HttpEntity<AchieveSubmit>(as, this.httpHeaders), AchieveSubmit.class);
        return as;
    }


    @ApiOperation(value="提交")
    @RequestMapping(value = "/achieveRecord-api/submit", method = RequestMethod.POST)
    @ResponseBody
    public AchieveSubmit submit(@RequestBody AchieveSubmit as){
        this.setBaseData(as);
        setRecord(as);
        as.getAchieveRecord().setAuditStatus("1");
        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        this.restTemplate.exchange(save, HttpMethod.POST, new HttpEntity<AchieveSubmit>(as, this.httpHeaders), AchieveSubmit.class);
        return as;
    }

    private void setRecord(AchieveSubmit as){
        SysUser sysUserInfo = this.getUserProfile();
        if(as.getAchieveReward()!=null){
            as.getAchieveReward().setCreator(as.getUpdator());
            as.getAchieveReward().setUpdator(as.getUpdator());
            as.getAchieveReward().setCreateDate(as.getUpdateDate());
            as.getAchieveReward().setUpdateDate(as.getUpdateDate());
            as.getAchieveReward().setCreateUnitId(sysUserInfo.getUnitId());
            as.getAchieveReward().setCreateUnitName(sysUserInfo.getUnitName());
        }
    }


    @ApiOperation(value="删除")
    @RequestMapping(value = "/achieveRecord-api/delete/{ids}", method = RequestMethod.DELETE)
    @ResponseBody
    public Integer delete(@PathVariable String ids) {
        ResponseEntity<Integer> responseEntity = this.restTemplate.exchange(delete+ids, HttpMethod.DELETE, new HttpEntity(this.httpHeaders), Integer.class);
        return responseEntity.getBody();
    }


    @ApiOperation(value="初始化")
    @RequestMapping(value = "/achieveRecord-api/newInit", method = RequestMethod.GET)
    @ResponseBody
    public AchieveSubmit newInit() {

     /*   ResponseEntity<AchieveBase> responseEntity = this.restTemplate.exchange(loadAchieveBase+achieveBaseId, HttpMethod.GET, new HttpEntity(this.httpHeaders), AchieveBase.class);
        AchieveBase ab = responseEntity.getBody();*/


        AchieveSubmit as = new AchieveSubmit();


        AchieveRecord a = new AchieveRecord();
        String achieveRecordId = UUID.randomUUID().toString().replace("-","");
        a.setId(achieveRecordId);
        //授拟-（文件上传）：材料
        a.setGrantDoc(UUID.randomUUID().toString().replace("-",""));
        //科技成果评价报告（文件上传）
        a.setAppraisalDoc(UUID.randomUUID().toString().replace("-",""));
        //公示结果：材料
        a.setTransContractDoc(UUID.randomUUID().toString().replace("-",""));
        //评估报告：材料
        a.setTransAssessDoc(UUID.randomUUID().toString().replace("-",""));
        //决策会议纪要：材料
        a.setDecisionMeetingDoc(UUID.randomUUID().toString().replace("-",""));
        //规章制度：材料
        a.setDecisionRuleDoc(UUID.randomUUID().toString().replace("-",""));
        //公示结果：材料
        a.setTransPublicDoc(UUID.randomUUID().toString().replace("-",""));


        String achieveRewardId = UUID.randomUUID().toString().replace("-","");
        AchieveReward ar = new AchieveReward();
        ar.setId(achieveRewardId);
        ar.setAchieveRecordId(achieveRecordId);

        //成果核算：材料
        ar.setRewardAccountingDoc(UUID.randomUUID().toString().replace("-",""));
        //净收入计算报告：材料
        ar.setAssignPlanDoc(UUID.randomUUID().toString().replace("-",""));
        //净收入计算报告：材料
        ar.setIncomeReportDoc(UUID.randomUUID().toString().replace("-",""));

        as.setAchieveRecord(a);
        as.setAchieveReward(ar);
        return as;
    }
    
    
    
    
    @ApiOperation(value="成果进展情况流程")
    @RequestMapping(value = "/achieveRecord-api/start_workflow",method = RequestMethod.POST)
	public Object start_workflow(HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		this.httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);//设置参数类型和编码
		String id = CommonUtil.getParameter(request, "id", "");
		String functionId = CommonUtil.getParameter(request, "functionId", "");
		String userIds = CommonUtil.getParameter(request, "userIds", "");
		System.out.println("============start_workflow userIds="+userIds+" functionId="+functionId+" id="+id);
		
		
		 ResponseEntity<AchieveRecord> responseEntity = this.restTemplate.exchange(load+id, HttpMethod.GET, new HttpEntity(this.httpHeaders), AchieveRecord.class);
		 AchieveRecord achieveBase=  responseEntity.getBody();
        SysUser sysUserInfo = this.getUserProfile();
		String branchFlag="0";
		Map<String ,Object> paramMap = new HashMap<String ,Object>();
		paramMap.put("id", id);
		paramMap.put("functionId", functionId);
		paramMap.put("processInstanceName", "成果进展情况->"+achieveBase.getAchieveName());
		paramMap.put("authenticatedUserId", sysUserInfo.getUserId());
		paramMap.put("authenticatedUserName", sysUserInfo.getUserDisp());
		paramMap.put("auditor", userIds);
		paramMap.put("branchFlag", branchFlag);
		
		
		
		//指定岗位
		String specialAuditor1 = "";//xxx_核心成果转化-岗位代码
		StringBuffer specialAuditor1_sb = new StringBuffer();
		String unitIds=sysUserInfo.getUnitId();
		System.out.println("============unitIds ="+unitIds+" applyUnitName="+sysUserInfo.getUnitName());
		if(unitIds!=null && !unitIds.equals(""))
		{
			String arr[]=unitIds.split(",");
			if(arr!=null && arr.length>0)
			{
				for(int i=0;i<arr.length;i++)
				{
					 String unitId=arr[i];
					
					 List<SysPost> list = EquipmentUtils.getPostListByUnitId(unitId, restTemplate, httpHeaders);
					 if(list!=null && list.size()>0)
					 {
						    for(int j=0;j<list.size();j++)
							{
						    	SysPost sysPost=list.get(j);
						    	String postCode=sysPost.getPostCode();
						    	String postName=sysPost.getPostName();
						    	System.out.println("============ postName ="+ postName);
						    	if(postName.contains("核心成果转化"))
						    	{
						    		specialAuditor1_sb.append(postCode).append("-");
						    	}
						    	
							}
					 }
				}
				
			}
			specialAuditor1=specialAuditor1_sb.toString();
			if(!specialAuditor1.equals(""))
			{
				specialAuditor1= specialAuditor1.substring(0,specialAuditor1.length() - 1);
			}
			
		}
		paramMap.put("specialAuditor1", specialAuditor1);
		System.out.println("============specialAuditor1 ="+specialAuditor1);
		HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<Map<String, Object>>(paramMap,this.httpHeaders);
		Result rs = this.restTemplate.exchange(WORKFLOW_URL + id, HttpMethod.POST, httpEntity, Result.class).getBody();
		return rs;
	}
}
