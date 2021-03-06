package com.pcitc.web.controller.system;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pcitc.base.common.LayuiTableData;
import com.pcitc.base.common.LayuiTableParam;
import com.pcitc.base.common.Result;
import com.pcitc.base.common.enums.DelFlagEnum;
import com.pcitc.base.common.enums.RequestProcessStatusEnum;
import com.pcitc.base.system.SysUser;
import com.pcitc.base.util.DataTableInfoVo;
import com.pcitc.base.util.IdUtil;
import com.pcitc.base.util.MD5Util;
import com.pcitc.web.common.BaseController;
import com.pcitc.web.utils.EquipmentUtils;
import com.pcitc.web.utils.PageCommon;

@Api(value = "user-api", description = "用户信息")
@Controller
public class UserController extends BaseController {

	/*
	 * 1、可以直接通过注册的服务名来访问，来实现访问和负载。不过如果用zuul的话， 要用zuul的服务名和实际访问的服务名一起
	 * 2、pplus本身是一个微服务，属于微服务之间的调用，可以直接用名称，不用ip.（注意启动类中的注解）
	 */
    private static final String USER_CURRENT_URL = "http://kjpt-zuul/system-proxy/user-provider/user/currentUserInfo/";
	private static final String USER_GET_URL = "http://kjpt-zuul/system-proxy/user-provider/user/get-user/";
	private static final String USER_GET_IN_ROLE_URL = "http://kjpt-zuul/system-proxy/user-provider/user/user-in-role";
	private static final String USER_GET_NOT_ROLE_URL = "http://kjpt-zuul/system-proxy/user-provider/user/user-not-role";
	private static final String USER_ADD_URL = "http://kjpt-zuul/system-proxy/user-provider/user/add-user";
	//private static final String USER_UPDATE_URL = "http://kjpt-zuul/system-proxy/user-provider/user/update-user";
	private static final String USER_PAGE_URL = "http://kjpt-zuul/system-proxy/user-provider/user/users-page";
	private static final String USER_LIST_PAGE_URL = "http://kjpt-zuul/system-proxy/user-provider/user/user-list";
	private static final String USER_DEL_URL = "http://kjpt-zuul/system-proxy/user-provider/user/delete-user/";
	private static final String USER_UNIQUE_CHECK_URL = "http://kjpt-zuul/system-proxy/user-provider/user/user-validate";

	private static final String QUERY_SYS_USER_LIST_BY_PAGE = "http://kjpt-zuul/system-proxy/user-provider/user/querySysUserListByPage/";
	private static final String GET_SYS_USER_LIST_BY_UNIT = "http://kjpt-zuul/system-proxy/user-provider/user/getSysUserListByUserUnitPage/";


	
	private static final String USER_DELUSERS = "http://kjpt-zuul/system-proxy/user-provider/user/delete-users";
	// 组织机构查询（回写）
	private static final String UNIT_LIST = "http://kjpt-zuul/system-proxy/unit-provider/unit-list";
	private static final String UNIT_BY_IDS = "http://kjpt-zuul/system-proxy/unit-provider/units/get-units-byids";
	// 岗位信息查询（回写）
	//private static final String GET_POST_LIST = "http://kjpt-zuul/system-proxy/post-provider/post/list-data";
	private static final String POST_BY_IDS = "http://kjpt-zuul/system-proxy/post-provider/post/get-posts-byids";

	@RequestMapping(value = "/user/delete/{userId}")
	@ResponseBody
	public Object delUser(@PathVariable("userId") String userId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResponseEntity<Integer> presult = this.restTemplate.exchange(USER_DEL_URL + userId, HttpMethod.POST, new HttpEntity<Object>(this.httpHeaders), Integer.class);
		if (presult.getBody() > 0) {
			return new Result(true);
		} else {
			return new Result(false, "删除失败，请联系系统管理员！");
		}
	}

	@RequestMapping(value = "/user/get-user")
	@ResponseBody
	public Object getUser(@RequestParam(value = "userId", required = true) String userId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysUser user = this.restTemplate.exchange(USER_GET_URL + userId, HttpMethod.GET, new HttpEntity<Object>(this.httpHeaders), SysUser.class).getBody();
		user.setUserPassword(null);
		return user;
	}
   /**
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/user/user-saveorupdate", method = RequestMethod.POST)
	@ResponseBody
	public Object userInsert(@ModelAttribute("user") SysUser user, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject parma = JSONObject.parseObject(JSONObject.toJSONString(user));
		System.out.println(">>>>>>>>>> 用户参数: "+parma.toJSONString());
		ResponseEntity<List> checkStatus = this.restTemplate.exchange(USER_UNIQUE_CHECK_URL, HttpMethod.POST, new HttpEntity<SysUser>(user, this.httpHeaders), List.class);
		List<Boolean> unique = checkStatus.getBody();
		if (!unique.get(0)) {
			return new Result(false, "登录名不能重复！");
		}
		if (!unique.get(1)) {
			return new Result(false, "邮箱不能重复！");
		}
		if (!StringUtils.isBlank(user.getUserPassword())) {
			user.setUserPassword(MD5Util.MD5Encode(user.getUserPassword()));
		}
		ResponseEntity<Integer> status = null;
		if (StringUtils.isBlank(user.getUserId())) {
			user.setUserId(IdUtil.createIdByTime());
			user.setUserDelflag(DelFlagEnum.STATUS_NORMAL.getCode());
			status = this.restTemplate.exchange(USER_ADD_URL, HttpMethod.POST, new HttpEntity<SysUser>(user, this.httpHeaders), Integer.class);
		} else {
			status = this.restTemplate.exchange(USER_UPDATE_URL, HttpMethod.POST, new HttpEntity<SysUser>(user, this.httpHeaders), Integer.class);
		}
		if (status.getBody() == 0) {
			return new Result(false, RequestProcessStatusEnum.SERVER_BUSY.getStatusDesc());
		} else {
			return new Result(true, RequestProcessStatusEnum.OK.getStatusDesc());
		}
	}
	*/

	@RequestMapping(value = "/user/resetPassword-users", method = RequestMethod.POST)
	@ResponseBody
	public Object userResetPassword(@RequestParam(value = "userId", required = false) String userId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 如果更改密码
		
		SysUser sysUser=EquipmentUtils.getSysUser(userId, restTemplate, httpHeaders);
		sysUser.setUserPassword(MD5Util.MD5Encode("000000"));// 默认密码6个0
		Integer count=EquipmentUtils.updateSysUser(sysUser, restTemplate, httpHeaders);
		if (count<=0) {
			return new Result(false, RequestProcessStatusEnum.SERVER_BUSY.getStatusDesc());
		} else {
			return new Result(true, RequestProcessStatusEnum.OK.getStatusDesc());
		}
	}

	@RequestMapping(value = "/user/user-list")
	@ResponseBody
	public Object getUserList(@ModelAttribute("param") LayuiTableParam param, HttpServletRequest request) throws IOException {
		// System.out.print("userId:"+this.sysUser.getUserId());
		HttpEntity<LayuiTableParam> entity = new HttpEntity<LayuiTableParam>(param, this.httpHeaders);
		ResponseEntity<LayuiTableData> responseEntity = this.restTemplate.exchange(USER_LIST_PAGE_URL, HttpMethod.POST, entity, LayuiTableData.class);
		LayuiTableData data = responseEntity.getBody();
		// 用户类型回写
		String parentCode = "ROOT_XTGL_YHLX"; // 字典编码
		String writebackField = "userKind"; // 回写字段
		JSONObject dataObject = (JSONObject) JSON.toJSON(data);
		PageCommon pageCommon = new PageCommon();
		pageCommon.getDictionaryWriteback(this.restTemplate, this.httpHeaders, dataObject, parentCode, writebackField);

		// 域用户回写
		parentCode = "ROOT_XTGL_YYH"; // 字典编码
		writebackField = "isDomain"; // 回写字段
		pageCommon.getDictionaryWriteback(this.restTemplate, this.httpHeaders, dataObject, parentCode, writebackField);

		JSONArray array = JSON.parseArray(dataObject.get("data").toString());
		//机构,岗位
		Set<String> unitids = new HashSet<String>();
		Set<String> postids = new HashSet<String>();
		for(java.util.Iterator<?> iter = array.iterator();iter.hasNext();)
		{
			Map<?,?> map = (Map<?,?>)iter.next();
			String uids = (String)map.get("userUnit");
			String pids = (String)map.get("userPost");
			if(!StringUtils.isBlank(uids)) {
				unitids.addAll(Arrays.asList(uids.split(",")));
			}
			if(!StringUtils.isBlank(pids)) {
				postids.addAll(Arrays.asList(pids.split(",")));
			}
		}
		if(unitids.size()==0 || postids.size()==0)
		{
			dataObject.put("data", array);
			return dataObject;
		}

		List<Map<String,Object>> units = this.restTemplate.exchange(UNIT_BY_IDS, HttpMethod.POST, new HttpEntity<Set<String>>(unitids, this.httpHeaders), List.class).getBody();
		List<Map<String,Object>> posts = this.restTemplate.exchange(POST_BY_IDS, HttpMethod.POST, new HttpEntity<Set<String>>(postids, this.httpHeaders), List.class).getBody();

		for(java.util.Iterator<?> iter = array.iterator();iter.hasNext();)
		{
			Map<String,Object> map = (Map<String,Object>)iter.next();
			map.put("userUnitDisp", "");
			map.put("userPostDisp", "");
			String uids = (String)map.get("userUnit");
			String pids = (String)map.get("userPost");
			if(!StringUtils.isBlank(uids)) {
				List<Map<String,Object>> us = units.stream().filter(a -> Arrays.asList(uids.split(",")).contains(a.get("unitId"))).collect(Collectors.toList());
				for(Map<String,Object> u:us)
				{
					map.put("userUnitDisp", (StringUtils.isBlank(map.get("userUnitDisp")+"")?"":map.get("userUnitDisp")+",")+u.get("unitName"));
				}

			}
			if(!StringUtils.isBlank(pids)) {
				List<Map<String,Object>> ps = posts.stream().filter(a -> Arrays.asList(pids.split(",")).contains(a.get("postId"))).collect(Collectors.toList());
				for(Map<String,Object> p:ps)
				{
					map.put("userPostDisp", (StringUtils.isBlank(map.get("userPostDisp")+"")?"":map.get("userPostDisp")+",")+p.get("postName"));
				}
			}
		}
		dataObject.put("data", array);
		return dataObject;
	}

	/**
	 * 人员选择
	 *
	 * @param param
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/user/getTableData", method = RequestMethod.POST)
	@ResponseBody
	public Object getTableData(@ModelAttribute("param") LayuiTableParam param) throws IOException {

		HttpEntity<LayuiTableParam> entity = new HttpEntity<LayuiTableParam>(param, this.httpHeaders);
		ResponseEntity<LayuiTableData> responseEntity = this.restTemplate.exchange(USER_PAGE_URL, HttpMethod.POST, entity, LayuiTableData.class);
		LayuiTableData data = responseEntity.getBody();
		return JSON.toJSON(data).toString();
	}

	@RequestMapping(value = "/user/user-in-role", method = RequestMethod.POST)
	@ResponseBody
	public Object getUserDataByRole(@ModelAttribute("param") LayuiTableParam param) throws IOException {
		//System.out.print("roleId:" + param.getParam().get("roleId"));
		HttpEntity<LayuiTableParam> entity = new HttpEntity<LayuiTableParam>(param, this.httpHeaders);
		ResponseEntity<LayuiTableData> responseEntity = this.restTemplate.exchange(USER_GET_IN_ROLE_URL, HttpMethod.POST, entity, LayuiTableData.class);
		LayuiTableData data = responseEntity.getBody();

		String parentCode = "ROOT_XTGL_YHLX"; // 字典编码
		String writebackField = "userKind"; // 回写字段
		JSONObject dataObject = (JSONObject) JSON.toJSON(data);
		PageCommon pageCommon = new PageCommon();
		pageCommon.getDictionaryWriteback(this.restTemplate, this.httpHeaders, dataObject, parentCode, writebackField);
		// 机构
		writebackField = "userUnit"; // 回写字段
		DataTableInfoVo tableInfo = new DataTableInfoVo();
		tableInfo.setiDisplayLength(1000);
		String resultUnit = this.restTemplate.exchange(UNIT_LIST, HttpMethod.POST, new HttpEntity<DataTableInfoVo>(tableInfo, this.httpHeaders), String.class).getBody();
		JSONObject retUnitJson = (JSONObject) JSON.parse(resultUnit);
		com.alibaba.fastjson.JSONArray retUnitArray = retUnitJson.getJSONArray("list");
		com.alibaba.fastjson.JSONArray dataArray = dataObject.getJSONArray("data");
		for (int i = 0; i < dataArray.size(); i++) {
			JSONObject jsonObject = dataArray.getJSONObject(i);
			String wirtebackId = jsonObject.getString(writebackField);
			String[] wirtebackIds = wirtebackId.split(",");
			String wirtebackValue = "";
			for (int j = 0; j < wirtebackIds.length; j++) {
				String id = wirtebackIds[j];
				for (int k = 0; k < retUnitArray.size(); k++) {
					JSONObject unitObject = retUnitArray.getJSONObject(k);
					String unitId = unitObject.getString("unitId"); // 单位ID
					if (id.equals(unitId)) {
						String unitName = unitObject.getString("unitName"); // 单位名称
						wirtebackValue += unitName + ",";
						break;
					}
				}
			}
			if (wirtebackValue.length() > 0) {
				wirtebackValue = wirtebackValue.substring(0, wirtebackValue.length() - 1);
			}
			jsonObject.put(writebackField + "Disp", wirtebackValue);
		}
		return dataObject.toString();
	}

	@RequestMapping(value = "/user/user-not-role", method = RequestMethod.POST)
	@ResponseBody
	public Object getUserDataNotInRole(@ModelAttribute("param") LayuiTableParam param) throws IOException {
		//System.out.print("roleId:" + param.getParam().get("roleId"));
		HttpEntity<LayuiTableParam> entity = new HttpEntity<LayuiTableParam>(param, this.httpHeaders);
		ResponseEntity<LayuiTableData> responseEntity = this.restTemplate.exchange(USER_GET_NOT_ROLE_URL, HttpMethod.POST, entity, LayuiTableData.class);
		LayuiTableData data = responseEntity.getBody();

		String parentCode = "ROOT_XTGL_YHLX"; // 字典编码
		String writebackField = "userKind"; // 回写字段
		JSONObject dataObject = (JSONObject) JSON.toJSON(data);
		PageCommon pageCommon = new PageCommon();
		pageCommon.getDictionaryWriteback(this.restTemplate, this.httpHeaders, dataObject, parentCode, writebackField);
		// 机构
		writebackField = "userUnit"; // 回写字段
		DataTableInfoVo tableInfo = new DataTableInfoVo();
		tableInfo.setiDisplayLength(1000);
		String resultUnit = this.restTemplate.exchange(UNIT_LIST, HttpMethod.POST, new HttpEntity<DataTableInfoVo>(tableInfo, this.httpHeaders), String.class).getBody();
		JSONObject retUnitJson = (JSONObject) JSON.parse(resultUnit);
		com.alibaba.fastjson.JSONArray retUnitArray = retUnitJson.getJSONArray("list");
		com.alibaba.fastjson.JSONArray dataArray = dataObject.getJSONArray("data");
		for (int i = 0; i < dataArray.size(); i++) {
			JSONObject jsonObject = dataArray.getJSONObject(i);
			String wirtebackId = jsonObject.getString(writebackField);
			String[] wirtebackIds = wirtebackId.split(",");
			String wirtebackValue = "";
			for (int j = 0; j < wirtebackIds.length; j++) {
				String id = wirtebackIds[j];
				for (int k = 0; k < retUnitArray.size(); k++) {
					JSONObject unitObject = retUnitArray.getJSONObject(k);
					String unitId = unitObject.getString("unitId"); // 单位ID
					if (id.equals(unitId)) {
						String unitName = unitObject.getString("unitName"); // 单位名称
						wirtebackValue += unitName + ",";
						break;
					}
				}
			}
			if (wirtebackValue.length() > 0) {
				wirtebackValue = wirtebackValue.substring(0, wirtebackValue.length() - 1);
			}
			jsonObject.put(writebackField + "Disp", wirtebackValue);
		}
		return dataObject.toString();
	}

	/*
	 * @SuppressWarnings("unchecked")
	 *
	 * @RequestMapping(value = "/user/get-user-by-unit", method =
	 * RequestMethod.POST) public Object getUserByUnit(DataTableInfo
	 * tableInfo,HttpServletRequest request) throws IOException { //传递参数
	 * httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
	 * ResponseEntity<JSONObject> responseEntity =
	 * this.restTemplate.exchange(USER_GET_BY_UNIT, HttpMethod.POST, new
	 * HttpEntity<DataTableInfo>(tableInfo, this.httpHeaders),
	 * JSONObject.class); JSONObject retJson = responseEntity.getBody(); Long
	 * totalCount = retJson.get("totalCount") != null?
	 * Long.parseLong(retJson.get("totalCount").toString()):0l;
	 *
	 * List<SysUser> userList = JSONArray.toList(retJson.getJSONArray("list"),
	 * new SysUser(), new JsonConfig()); DataTableParameter data = new
	 * DataTableParameter(); data.setAaData(userList); //要显示的总条数
	 * data.setiTotalDisplayRecords(totalCount); //真实的总条数
	 * data.setiTotalRecords(totalCount);
	 *
	 * return data; }
	 */
	@RequestMapping(value = "/user/delete-users", method = RequestMethod.POST)
	@ResponseBody
	public Object delUsers(@RequestParam(value = "userIds", required = false) String userIds) throws IOException {
		JSONArray array = JSONArray.parseArray(userIds);
		List<?> ids = JSONObject.parseArray(array.toJSONString());
		ResponseEntity<Integer> status = this.restTemplate.exchange(USER_DELUSERS, HttpMethod.POST, new HttpEntity<List<?>>(ids, this.httpHeaders), Integer.class);
		if (status.getBody() > 0) {
			return new Result(true);
		} else {
			return new Result(false);
		}
	}

	@RequestMapping(value = "/user/user-display", method = RequestMethod.POST)
	@ResponseBody
	public Object userInfoDisplay() throws IOException {
		// 获取当前用户信息
		SysUser sysUserInfo = getUserProfile();
		return sysUserInfo;
	}

	// 更新当前用户密码
	@RequestMapping(value = "/user/upd-pass", method = RequestMethod.POST)
	@ResponseBody
	public Object updateCurrentUserPassword(@RequestParam(value = "oldPass", required = false) String oldPass, @RequestParam(value = "newPass", required = false) String newPass) throws IOException {
		SysUser sysUserInfo = getUserProfile();
		SysUser user = this.restTemplate.exchange(USER_GET_URL + sysUserInfo.getUserId(), HttpMethod.GET, new HttpEntity<Object>(this.httpHeaders), SysUser.class).getBody();
		if (user == null) {
			return new Result(false, "用户不存在！");
		}
		if (!user.getUserPassword().equals(MD5Util.MD5Encode(oldPass))) {
			return new Result(false, "原始密码错误！");
		}
		user.setUserPassword(MD5Util.MD5Encode(newPass));
		Integer count=EquipmentUtils.updateSysUser(user, restTemplate, httpHeaders);
		if (count<=0) {
			return new Result(false, "密码更改失败，请联系管理员！");
		} else {
			return new Result(true, "密码修改成功！");
		}
	}


	/**
	 *功能描述 重置密码功能
	 * @author t-chengjia.chen
	 * @date 2019/11/25
	 * @return java.lang.Object
	 */
	@ApiOperation(value = "修改当前用户密码", notes = "修改当前用户密码")
	@RequestMapping(value = "/user/changePassword", method = RequestMethod.POST)
	@ResponseBody
	public Object changePassword(@RequestParam(value = "oldPass", required = false) String oldPass, @RequestParam(value = "newPass", required = false) String newPass) throws IOException {
		SysUser sysUserInfo = getUserProfile();

		SysUser user = this.restTemplate.exchange(USER_GET_URL + sysUserInfo.getUserId(), HttpMethod.GET, new HttpEntity<Object>(this.httpHeaders), SysUser.class).getBody();
		if (user == null) {
			return new Result(false, "用户不存在！");
		}
		if (!user.getUserPassword().equals(MD5Util.MD5Encode(oldPass))) {
			return new Result(false, "原始密码错误！");
		}
		user.setUserPassword(MD5Util.MD5Encode(newPass));
		if(newPass!=null&&!newPass.isEmpty()){
			// 密码验证的正则表达式:由数字和字母组成，并且要同时含有数字和字母，且长度要在8-16位之间。
			Pattern pattern = Pattern.compile("^(?:([a-z])|([A-Z])|([0-9])|(.)){8,}|(.)+$");
			if (pattern.matcher(newPass).matches()){
				Integer count=	EquipmentUtils.updateSysUser(user, restTemplate, httpHeaders);
				if (count<=0) {
					return new Result(false, "密码更改失败，请联系管理员！");
				} else {
					return new Result(true, "密码修改成功！");
				}
			}else{
				return new Result(false, "密码格式错误！");
			}
		}else{
			return new Result(false, "新密码不能为空！");
		}
	}


    /**
     *功能描述 更改个人信息
     * @author t-chengjia.chen
     * @date 2019/11/25
     * @param
     * @return java.lang.Object
     */
    @ApiOperation(value = "修改当前用户信息", notes = "修改当前用户信息")
    @RequestMapping(value = "/user/updateCurrentUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public Object updateUserInfo(@RequestBody SysUser user) throws IOException {
		
        // 获取个人原有信息 S
    	SysUser	sysUser = this.restTemplate.exchange(USER_GET_URL + this.getUserProfile().getUserId(), HttpMethod.GET, new HttpEntity<Object>(this.httpHeaders), SysUser.class).getBody();
    	sysUser.setUserMail(user.getUserMail());
    	sysUser.setUserMobile(user.getUserMobile());
    	sysUser.setUserPhone(user.getUserPhone());
    	sysUser.setUserComment(user.getUserComment());
    	sysUser.setUserHeadPic(user.getUserHeadPic());
        Integer count=   EquipmentUtils.updateSysUser(sysUser, restTemplate, httpHeaders);
        if (count<=0) {
            return new Result(false, "个人设置失败！");
        } else {
            return new Result(true, "个人设置成功！");
        }
    }


    @ApiOperation(value = "获取当前用户信息", notes = "获取当前用户信息")
    @GetMapping(value = "/user/currentUserInfo")
    @ResponseBody
    public Object currentUserInfo( HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysUser sysUserInfo = getUserProfile();
		SysUser user = this.restTemplate.exchange(USER_GET_URL + sysUserInfo.getUserId(), HttpMethod.GET, new HttpEntity<Object>(this.httpHeaders), SysUser.class).getBody();
		user.setUserPassword(null);
		return user;
    }


	@RequestMapping(method = RequestMethod.GET, value = "/user/ini-self-config")
	private String toUpdatePassPage(HttpServletRequest request) {
		SysUser sysUserInfo = getUserProfile();
		SysUser user = this.restTemplate.exchange(USER_GET_URL + sysUserInfo.getUserId(), HttpMethod.GET, new HttpEntity<Object>(this.httpHeaders), SysUser.class).getBody();
		request.setAttribute("userInfo", user);
		return "base/user/user_config";
	}

	// 修改个人设置信息
	@RequestMapping(value = "/user/self-config", method = RequestMethod.POST)
	@ResponseBody
	public Object updateSelfConfig(@RequestBody String params) throws IOException {
		// 获取个人原有信息
		SysUser sysUserInfo = getUserProfile();
		SysUser user = this.restTemplate.exchange(USER_GET_URL + sysUserInfo.getUserId(), HttpMethod.GET, new HttpEntity<Object>(this.httpHeaders), SysUser.class).getBody();

		if (user == null) {
			return new Result(false, "用户不存在！");
		}
		JSONObject reJson = JSONObject.parseObject(params);
		//System.out.println("===updateSelfConfig---"+reJson.getString("userConfig1"));
		//user.setUserConfig1(reJson.getString("userConfig1"));
		Integer count=	EquipmentUtils.updateSysUser(user, restTemplate, httpHeaders);
		if (count<=0) {
			return new Result(false, "个人设置失败！");
		} else {
			return new Result(true, "个人设置成功！");
		}
	}

	// 更新头像
	@RequestMapping(value = "/user/upd-headimg", method = RequestMethod.POST)
	@ResponseBody
	public Object updateCurrentUserHeadImg(@RequestParam(value = "userExtend", required = false) String userExtend) throws IOException {
		SysUser sysUserInfo = getUserProfile();
		SysUser user = this.restTemplate.exchange(USER_GET_URL + sysUserInfo.getUserId(), HttpMethod.GET, new HttpEntity<Object>(this.httpHeaders), SysUser.class).getBody();
		if (user == null) {
			return new Result(false, "用户不存在！");
		}
		user.setUserExtend(userExtend);// 只更新头像
		Integer count=	EquipmentUtils.updateSysUser(user, restTemplate, httpHeaders);
		if (count<=0) {
			return new Result(false, "密码更改失败，请联系管理员！");
		} else {
			return new Result(true, "密码修改成功！");
		}
	}



	@RequestMapping(value = "/user/querySysUserListByPage", method = RequestMethod.POST)
	@ResponseBody
	public Object querySysUserListByPage(@ModelAttribute("param") LayuiTableParam param, HttpServletRequest request) throws IOException {
		HttpEntity<LayuiTableParam> entity = new HttpEntity<LayuiTableParam>(param, this.httpHeaders);
	    ResponseEntity<LayuiTableData> responseEntity = this.restTemplate.exchange(QUERY_SYS_USER_LIST_BY_PAGE, HttpMethod.POST, entity, LayuiTableData.class);
	    LayuiTableData result = responseEntity.getBody();
	    JSONObject retJson = (JSONObject) JSON.toJSON(result);
		return retJson;
	}



	@RequestMapping(value = "/user/getSysUserListByUserUnitPage", method = RequestMethod.POST)
	@ResponseBody
	public Object getSysUserListByUserUnitPage(@ModelAttribute("param") LayuiTableParam param, HttpServletRequest request) throws IOException {
		HttpEntity<LayuiTableParam> entity = new HttpEntity<LayuiTableParam>(param, this.httpHeaders);
	    ResponseEntity<LayuiTableData> responseEntity = this.restTemplate.exchange(GET_SYS_USER_LIST_BY_UNIT, HttpMethod.POST, entity, LayuiTableData.class);
	    LayuiTableData result = responseEntity.getBody();
	    JSONObject retJson = (JSONObject) JSON.toJSON(result);
		return retJson;
	}









}
