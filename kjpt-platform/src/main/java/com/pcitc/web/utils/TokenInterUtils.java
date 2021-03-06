package com.pcitc.web.utils;

import com.alibaba.fastjson.JSONObject;
import com.pcitc.base.common.Constant;
import com.pcitc.base.system.SysLog;
import com.pcitc.base.system.SysUser;
import com.pcitc.web.common.JwtTokenUtil;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class TokenInterUtils {
	
	private static final String LOG_ADD_URL = "http://kjpt-zuul/system-proxy/log-provider/add";
	
	public static Map getUrlMap() 
	{
		  Map map  = new HashMap();
		  
		  map.put("/user_post_list", "查询岗位分配");
		  map.put("/user-api/updateUserPost", "分配岗位");
		  map.put("/base/user/user_secret_list.html", "查询人员密级");
		  map.put("/post/post_function", "查询岗位授权");
		  map.put("/post/save-post-functions", "岗位授权");
		  map.put("/post/del-post", "删除岗位");
		  map.put("/post/upd-or-add", "增加岗位");
		  map.put("/role/add-role", "增加角色"); 
		  map.put("/role/del-role/", "删除角色"); 
		  map.put("/user/user-in-role", "人员分配角色"); 
		  map.put("/user-api/save", "增加用户"); 
		  map.put("/user/delete-users", "删除用户"); 
		  map.put("/function/saveFunction", "新增或者修改菜单");
		  map.put("/function/deleteFunction", "删除菜单");
		  map.put("/dictionary/saveDictionary", "增加字典");
		  map.put("/activiti-model/delete/", "删除工作流程模型");
		  map.put("/activiti-model/deploy", "部署流程");
		  map.put("/workflow/function/prodef/save", "功能与工作流绑定");
		  map.put("/dictionary/batch-delete", "删除字典");
		  map.put("/workflow/function/configures", "解除功能与工作流绑定");
		  map.put("/sysJob/save", "增加定时任务");
		  map.put("/sysJob/deleteJob", "删除定时任务");
		  map.put("/sysJob/pauseJob", "暂停定时任务");
		  map.put("/sysJob/resumeJob", "启用定时任务");
		  map.put("/sysJob/executeJob", "执行定时任务");
		  
		  
		  
		  
		  
		  
		  
		  map.put("/index", "用户登陆");
		  map.put("/role/add-role", "角色增加");
		  map.put("/role/del-role/", "角色删除");
		  map.put("/function/saveFunction", "新增或者修改菜单");
		  map.put("/function/deleteFunction", "删除菜单");
		  map.put("/dictionary/saveDictionary", "增加字典");
		  map.put("/html/computersoftware/query.html", "计算机软件管理");
		  map.put("/html/scientificMaterials/scientificPlan.html", "科技规划");
		  map.put("/post/post_function", "测试二级模块2");
		  map.put("/list", "年度总结");
		  map.put("/html/scientificPlatform/scientificPlatform.html?level=01", "国家级科研平台");
		  map.put("/role/role_function", "测试二级模块");
		  map.put("/sys_meeting/list", "会议纪要");
		  map.put("/kjpt/expert/expert_list.html", "专家信息管理");
		  map.put("/html/trademark/index.html", "商标管理");
		  map.put("/role/role_function", "角色授权");
		  map.put("/sysNotice/sysNotice_list", "公告管理");
		  map.put("/task/done-task/list/ini", "已办任务");
		  map.put("/sysJob/sysJobExcep_list", "定时任务异常");
		  map.put("/functiongroup/functiongroup-manage", "菜单分组");
		  map.put("/task/message/list/ini", "审批通知");
		  map.put("/zjkMsgConfig/toListPage", "通知方式管理");
		  map.put("/standardBaseExpert/tfcAnalysis", "技术热点分析");
		  map.put("/role/role_list", "角色管理");
		  map.put("/sysConfig/page", "系统配置");
		  map.put("/tech-family/type/ini-chart", "技术族树");
		  map.put("/sysNotice/my_notice_list", "通知公告");
		  map.put("/html/patent/query.html", "专利管理");
		  map.put("/kjpt/expert/expert_query.html", "专家查询");
		  map.put("/html/scientificPlatform/scientificPlatform.html", "科研平台查询");
		  map.put("/html/scientificPlatform/scientificPlatform.html?level=04", "集团级科研平台");
		  map.put("/sysJob/sysJob_list", "定时任务管理");
		  map.put("/zjkMsg/toListPage", "回复管理");
		  map.put("/unit/unit_tree", "机构管理");
		  map.put("/task/pending/list/ini", "待办任务");
		  map.put("/post/post_function", "岗位授权");
		  map.put("/list", "工作要点");
		  map.put("/html/scientificPlatform/scientificPlatform.html?level=05", "板块级科研平台");
		  map.put("/html/scientificPlatform/scientificPlatform.html?level=02", "部委级科研平台");
		  map.put("/dictionary/toDictionaryList?menuType=1", "数据字典");
		  map.put("/task/done-task/list/ini", "已办任务");
		  map.put("/function/toFuntionList", "菜单管理");
		  map.put("/workflow/function/config", "功能流程配置");
		  map.put("/html/scientificPlatform/scientificPlatform.html?level=03", "省部级科研平台");
		  map.put("/activiti-def/processdef/list/ini", "工作流定义");
		  map.put("/html/scientificMaterials/scienceProgress.html", "科技进展动态");
		  map.put("/tech-family/type/tree-list/ini", "技术族分类");
		  map.put("/task/pending/list/ini", "待办任务");
		  map.put("/activiti-model/model/list", "工作流模型");
		  map.put("/user/user_page_list", "用户管理");
		  map.put("/sysfilekind/sysFileKind-collect", "我的收藏");
		  map.put("/sysNews/toListPage", "新闻管理");
		  map.put("/kjpt/expert/expert_demeanor.html", "专家风采");
		  
		  return map;
	}
	
	
	
	//保存日志：1登陆日志，2操作日志，
	public static Integer saveSysLog(RestTemplate restTemplate,HttpHeaders httpHeaders,HttpServletRequest request,SysUser userInfo)
		{
			Integer resutl=0;
			String url=request.getRequestURI();
			
			//System.out.println(">>>>>>>>>>>>>>>>>>当前请求"+url);
			Map map=TokenInterUtils.getUrlMap();
			Object urlName=map.get(url);
			if(urlName!=null)
			{
				String optDescribe="";
				//如果当前环境下为空,则从TOKEN中获取
				if(userInfo==null)
				{
					//System.out.println("========================当前环境userInfo为空=============================");
					List<String> list = httpHeaders.get("Authorization");
					if (list != null && list.get(0) != null)
					{
						String arr[]=list.get(0).split(" ");
						if(arr!=null && arr.length>1)
						{
							userInfo = JwtTokenUtil.getUserFromTokenByValue(list.get(0).split(" ")[1]);
						}
						
					}
				}
				if(userInfo!=null)
				{
					
					JSONObject user = JSONObject.parseObject(JSONObject.toJSONString(userInfo));
					//System.out.println(">>>>>>>>>>>>>>>>>>>>USER信息"+user.toString());
					String userName=userInfo.getUserDisp();
					String userId=userInfo.getUserName();
					SysLog sysLog = new SysLog();
					sysLog.setId(UUID.randomUUID().toString().replaceAll("-", ""));
					sysLog.setLogIp(EquipmentUtils.getRemoteHost(request));
					sysLog.setLogTime(new Date());
					sysLog.setLogUrl(url);
					if(url.equals("/index"))
					{
						sysLog.setLogType(Constant.LOG_TYPE_LOGIN);//日志类型：1登陆日志，2操作日志，3错误日志
					}else
					{
						sysLog.setLogType(Constant.LOG_TYPE_OPT);
					}
					sysLog.setOptResult("成功");
					//用户类型：1普通用户，2系统管理员，3安全员，4审计员
					if(userId.equals(Constant.LOG_SYSTEMADMIN))
					{
						sysLog.setUserType("2");
					}else if(userId.equals(Constant.LOG_SECURITYADMIN))
					{
						sysLog.setUserType("3");
					}else if(userId.equals(Constant.LOG_AUDITADMIN))
					{
						sysLog.setUserType("4");
					}else
					{
						sysLog.setUserType("1");
					}
					optDescribe=String.valueOf(urlName);
					sysLog.setUserName(userName); 
					sysLog.setUserId(userId);
					sysLog.setRequestType(request.getMethod());
					sysLog.setUnitId(userInfo.getUnitId());
					String unitName="";
					if(userInfo.getUserUnitName()!=null)
					{
						unitName=userInfo.getUserUnitName();
					}
					sysLog.setUnitName(unitName);
					sysLog.setOptDescribe(optDescribe);
					JSONObject sysLogstr = JSONObject.parseObject(JSONObject.toJSONString(sysLog));
					//System.out.println(">>>>>>>>>>>>>>>>>>>>sysLog信息"+sysLogstr.toString());
					httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
					ResponseEntity<String> responseEntity =restTemplate.exchange(LOG_ADD_URL, HttpMethod.POST, new HttpEntity<SysLog>(sysLog, httpHeaders), String.class);
					int statusCode = responseEntity.getStatusCodeValue();
					if (statusCode == 200) 
					{
						String dataId = responseEntity.getBody();
						resutl=1;
					}
				
				}
			}
			return resutl;
		}
	
	
	
	
	
	    //保存日志  3错误日志 
		public static Integer saveErrorSysLog(RestTemplate restTemplate,HttpHeaders httpHeaders,HttpServletRequest request,SysUser userInfo,String optError)
			{
				Integer resutl=0;
				String url=request.getRequestURI();
				//System.out.println(">>>>>>>>>>>>>>>>>>当前请求"+url);
				Map map=TokenInterUtils.getUrlMap();
				Object urlName=map.get(url);
				if(urlName!=null)
				{
					String optDescribe="";
					//如果当前环境下为空,则从TOKEN中获取
					if(userInfo==null)
					{
						//System.out.println("========================当前环境userInfo为空=============================");
						List<String> list = httpHeaders.get("Authorization");
						if (list != null && list.get(0) != null)
						{
							String arr[]=list.get(0).split(" ");
							if(arr!=null && arr.length>1)
							{
								userInfo = JwtTokenUtil.getUserFromTokenByValue(list.get(0).split(" ")[1]);
							}
						}
					}
					if(userInfo!=null)
					{
						
						JSONObject user = JSONObject.parseObject(JSONObject.toJSONString(userInfo));
						//System.out.println(">>>>>>>>>>>>>>>>>>>>USER信息"+user.toString());
						String userName=userInfo.getUserDisp();
						String userId=userInfo.getUserName();
						SysLog sysLog = new SysLog();
						sysLog.setId(UUID.randomUUID().toString().replaceAll("-", ""));
						sysLog.setLogIp(EquipmentUtils.getRemoteHost(request));
						sysLog.setLogTime(new Date());
						sysLog.setLogUrl(url);
						sysLog.setLogType(Constant.LOG_TYPE_ERROR);//3错误日志
						sysLog.setOptResult("失败");
						//用户类型：1普通用户，2系统管理员，3安全员，4审计员
						if(userId.equals(Constant.LOG_SYSTEMADMIN))
						{
							sysLog.setUserType("2");
						}else if(userId.equals(Constant.LOG_SECURITYADMIN))
						{
							sysLog.setUserType("3");
						}else if(userId.equals(Constant.LOG_AUDITADMIN))
						{
							sysLog.setUserType("4");
						}else
						{
							sysLog.setUserType("1");
						}
						optDescribe=String.valueOf(urlName);
						sysLog.setUserName(userName); 
						sysLog.setOptError(optError);
						sysLog.setUserId(userId);
						sysLog.setRequestType(request.getMethod());
						sysLog.setUnitId(userInfo.getUnitId());
						String unitName="";
						if(userInfo.getUserUnitName()!=null)
						{
							unitName=userInfo.getUserUnitName();
						}
						sysLog.setUnitName(unitName);
						sysLog.setOptDescribe(optDescribe);
						JSONObject sysLogstr = JSONObject.parseObject(JSONObject.toJSONString(sysLog));
						//System.out.println(">>>>>>>>>>>>>>>>>>>>sysLog信息"+sysLogstr.toString());
						httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
						ResponseEntity<String> responseEntity =restTemplate.exchange(LOG_ADD_URL, HttpMethod.POST, new HttpEntity<SysLog>(sysLog, httpHeaders), String.class);
						int statusCode = responseEntity.getStatusCodeValue();
						if (statusCode == 200) 
						{
							String dataId = responseEntity.getBody();
							resutl=1;
						}
					
					}
				}
				return resutl;
			}
		
		
		
		
		public static Integer saveSecurityadminSecretLevelLog(String optDescribe,String url,RestTemplate restTemplate,HttpHeaders httpHeaders,HttpServletRequest request,SysUser userInfo)
		{
			
			Integer resutl=0;
			SysLog sysLog = new SysLog();
			sysLog.setId(UUID.randomUUID().toString().replaceAll("-", ""));
			sysLog.setLogIp(EquipmentUtils.getRemoteHost(request));
			sysLog.setLogTime(new Date());
			sysLog.setLogUrl(url);
			sysLog.setLogType(Constant.LOG_TYPE_OPT);
			sysLog.setOptResult("成功");
			//用户类型：1普通用户，2系统管理员，3安全员，4审计员
			sysLog.setUserType("3");
			
			sysLog.setUserName(userInfo.getUserDisp()); 
			sysLog.setUserId(userInfo.getUserName());
			sysLog.setRequestType(request.getMethod());
			sysLog.setUnitId(userInfo.getUnitId());
			String unitName="";
			if(userInfo.getUserUnitName()!=null)
			{
				unitName=userInfo.getUserUnitName();
			}
			sysLog.setUnitName(unitName);
			sysLog.setOptDescribe(optDescribe);
			JSONObject sysLogstr = JSONObject.parseObject(JSONObject.toJSONString(sysLog));
			//System.out.println(">>>>>>>>>>>>>>>>>>>>sysLog信息"+sysLogstr.toString());
			httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
			ResponseEntity<String> responseEntity =restTemplate.exchange(LOG_ADD_URL, HttpMethod.POST, new HttpEntity<SysLog>(sysLog, httpHeaders), String.class);
			int statusCode = responseEntity.getStatusCodeValue();
			if (statusCode == 200) 
			{
				String dataId = responseEntity.getBody();
				resutl=1;
			}
			return resutl;
		}
		
		
		
		
		
    
}
