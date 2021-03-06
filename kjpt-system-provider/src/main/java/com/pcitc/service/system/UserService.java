package com.pcitc.service.system;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.pcitc.base.common.LayuiTableData;
import com.pcitc.base.common.LayuiTableParam;
import com.pcitc.base.common.TreeNode;
import com.pcitc.base.system.SysCollect;
import com.pcitc.base.system.SysUnitTmp;
import com.pcitc.base.system.SysUser;
import com.pcitc.base.system.SysUserExample;
import com.pcitc.base.system.SysUserTmp;
import com.pcitc.base.system.SysUserUnit;
import com.pcitc.base.util.DataTableInfo;

/**
 * @author zhf 服务接口
 */
public interface UserService {

	public SysUser selectUserByUserId(String userId);

	//根据用户id查询当前信息-new
	public SysUser currentUserInfo(String userId);

	public Integer updateUser(SysUser user);




	public int deleteUser(String userId);

	public int deleteUserReal(String userId);

	public Integer insertUser(SysUser user);
	public Integer insertUserUnit(SysUserUnit user);
	public List<SysUser> getAllUsers();

	public SysUser selectUserByUserName(String username);

	/**
	 *  不区分状态查询用户
	 * @param username
	 * @return
	 */
	public SysUser selectAllStatusUserByUserName(String username);

	/**
	 * 条件查询列表
	 *
	 * @return
	 */
	public LayuiTableData selectUserByPage(LayuiTableParam param);
	/**
	 * 条件查询列表
	 *
	 * @return
	 */
	public LayuiTableData selectUserList(LayuiTableParam param);
	/**
	 * 检查登录名是否唯一
	 * @param userId
	 * @param userName
	 * @return
	 */
	public List<Boolean> userValidate(SysUser user);

	/**
	 * 根据唯一标识查询用户信息
	 *
	 * @return
	 */
	public SysUser selectUserByIdentityId(String unifyIdentityId);

	/**
	 * @author zhf
	 * @date 2018年5月17日 上午9:30:34
	 * 包含用户本身的熟悉、所拥有的角色、菜单等
	 */
	public SysUser selectUserDetailsByUserId(String userId) throws Exception;

	/**
	 * @param roleCodes
	 * @return
	 * 根据角色编码（多个），获取所属人员
	 */
	public Object findUserByRoleCodes(Map<String,Object> paramMap);
	/**
	 * 根据所属机构加载用户信息
	 * @param tableInfo
	 * @return
	 */
	public JSONObject findUserByUnit(DataTableInfo tableInfo);
	/**
	 * 批量删除（逻辑删除）用户
	 * @return
	 */
	public Integer delUsers(List<String> userIds);
	/**
	 * 根据角色获得用户列表（分页）
	 * @param param
	 * @return
	 */
	public LayuiTableData selectUserListByRole(LayuiTableParam param);
	/**
	 *   获得没有绑定指定角色的用户列表（分页）
	 * @param param
	 * @return
	 */
	public LayuiTableData selectNotInRoleUserList(LayuiTableParam param);

	/**
	 * @param paramMap
	 * @return
	 * 查询用户信息，包含所属部门，多部门以“，”分开显示
	 */
	public JSONObject selectUserDetail(Map<String,Object> paramMap);



	public LayuiTableData querySysUserListByPage(LayuiTableParam param);

	public LayuiTableData getSysUserListByUserUnitPage(LayuiTableParam param);

	/**
	 * mybatis自带查询用户信息
	 */
	public List<SysUser> selectByExample(SysUserExample example);
	public List<SysUser> getSysUserList(Map map);

	public LayuiTableData getSysUserPage(LayuiTableParam param)throws Exception;

	public	List getList(Map map)throws Exception;
	public  Long getCount(Map map)throws Exception;





	public Integer updateSysUser(SysUser user)throws Exception;
	public Integer insertSysUser(SysUser user)throws Exception;
	public Integer updateSysUserPost(SysUser user)throws Exception;
	public Integer updateSysUserRole(SysUser user)throws Exception;
	public Integer updateUserBase(SysUser user)throws Exception;

	public SysUser getUserByUserNameAndPassword(String userName,String password);
	public List<SysCollect> getSysCollectListByUserId(String userId) throws Exception;

	Integer selectWhiteList(String userName);


	public SysUser getUserByUnifyIdentityId(String unifyIdentityId);
	
	
	
	
	
	
	
	public SysUserTmp selectSysUserTmp(String id) throws Exception;
	public Integer updateSysUserTmp(SysUserTmp record)throws Exception;
	public int deleteSysUserTmp(String id)throws Exception;
	public Integer insertSysUserTmp(SysUserTmp record)throws Exception;
	public LayuiTableData getSysUserTmpPage(LayuiTableParam param)throws Exception;
	public List getSysUserTmpList(Map map)throws Exception;

	
	
	public SysUnitTmp selectSysUnitTmp(String id) throws Exception;
	public Integer updateSysUnitTmp(SysUnitTmp record)throws Exception;
	public int deleteSysUnitTmp(String id)throws Exception;
	public Integer insertSysUnitTmp(SysUnitTmp record)throws Exception;
	public LayuiTableData getSysUnitTmpPage(LayuiTableParam param)throws Exception;
	public List getSysUnitTmpList(Map map)throws Exception;

	
	public List<TreeNode> getTreeNodeList(Map map)throws Exception;


}
