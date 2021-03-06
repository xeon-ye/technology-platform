package com.pcitc.service.system.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.pcitc.base.system.*;
import com.pcitc.service.file.FileCommonService;
import com.pcitc.service.system.FileService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pcitc.base.common.LayuiTableData;
import com.pcitc.base.common.LayuiTableParam;
import com.pcitc.base.system.SysNoticeExample.Criteria;
import com.pcitc.base.util.DateUtil;
import com.pcitc.mapper.system.SysNoticeMapper;
import com.pcitc.mapper.system.SysUserNoticeMapper;
import com.pcitc.mapper.system.SysUserUnitMapper;
import com.pcitc.service.system.SysNoticeService;

@Service("sysNoticeService")
public class SysNoticeServiceImpl implements SysNoticeService {
	@Autowired
	private SysNoticeMapper sysNoticeMapper;
	
	@Autowired
	private SysUserUnitMapper sysUserUnitMapper;
	
	@Autowired
	private SysUserNoticeMapper sysUserNoticeMapper;

	@Autowired
	private FileCommonService fs;
	
	@Override
	public JSONObject selectSysNoticeAll(SysNoticeVo vo) throws Exception {
		// 每页显示条数
		int pageSize = Integer.parseInt(vo.getLimit());
		// 当前是第几页
		int pageNum = Integer.parseInt(vo.getPage());
		// 1、设置分页信息，包括当前页数和每页显示的总计数
		PageHelper.startPage(pageNum, pageSize);
		// 2、执行查询
		SysNoticeExample ex = new SysNoticeExample();
		Criteria or = ex.or();
		/*if (dataTableInfoVo.getiSortCol() != null && dataTableInfoVo.getiSortCol().length() > 0 && !"null".equalsIgnoreCase(dataTableInfoVo.getiSortCol())) {
			if (dataTableInfoVo.getsSortDir_0() != null && dataTableInfoVo.getsSortDir_0().length() > 0 && !"null".equalsIgnoreCase(dataTableInfoVo.getsSortDir_0())) {
				ex.setOrderByClause(dataTableInfoVo.getiSortCol() + " " + dataTableInfoVo.getsSortDir_0());
			} else {
				ex.setOrderByClause(dataTableInfoVo.getiSortCol());
			}
		}*/
		ex.setOrderByClause("notice_createtime DESC");
		//拼接查询条件
		if (vo.getNoticeTitle() != null && vo.getNoticeTitle().length() > 0) {
			or.andNoticeTitleLike("%"+vo.getNoticeTitle()+"%");
		}
		List<SysNotice> list = sysNoticeMapper.selectByExample(ex);
		// 3、获取分页查询后的数据
		PageInfo<SysNotice> pageInfo = new PageInfo<SysNotice>(list);
		// 4、封装需要返回的分页实体
		JSONObject result = new JSONObject();
		result.put("totalCount", pageInfo.getTotal());
		result.put("list", list);
		return result;
	}

	@Override
	public JSONObject selectSysNoticeList(SysNoticeVo vo) throws Exception {
		// 每页显示条数
		int pageSize = Integer.parseInt(vo.getLimit());
		// 当前是第几页
		int pageNum = Integer.parseInt(vo.getPage());
		// 1、设置分页信息，包括当前页数和每页显示的总计数
		PageHelper.startPage(pageNum, pageSize);
		// 2、执行查询
		Map<String, Object> mapParams = new HashMap<String, Object>();
		/*if (dataTableInfoVo != null && StringUtils.isNotEmpty(dataTableInfoVo.getiSortCol())) {
			if (StringUtils.isNotEmpty(dataTableInfoVo.getsSortDir_0())) {
				mapParams.put("orderByClause", dataTableInfoVo.getiSortCol() + " " + dataTableInfoVo.getsSortDir_0());
			} else {
				mapParams.put("orderByClause", dataTableInfoVo.getiSortCol());
			}
		}*/
		//拼接查询条件
		if (vo.getUserId() != null && vo.getUserId().length() > 0) {
			mapParams.put("userId",vo.getUserId());
		}
		if (vo.getUserNoticeStatus() != null) {
			mapParams.put("userNoticeStatus",vo.getUserNoticeStatus().toString());
		}
		if (vo.getNoticeTitle() != null && vo.getNoticeTitle().length() > 0) {
			mapParams.put("noticeTitle",vo.getNoticeTitle());
		}
        if (vo.getNoticeReceiver() != null && vo.getNoticeReceiver().length() > 0) {
            mapParams.put("noticeReceiver",vo.getNoticeReceiver());
        }
        mapParams.put("isPublished",1);
		mapParams.put("isVariable",1);
		List<SysNotice> list = sysNoticeMapper.selectPubNoticeList(mapParams);
		// 3、获取分页查询后的数据
		PageInfo<SysNotice> pageInfo = new PageInfo<SysNotice>(list);
		// 4、封装需要返回的分页实体
		JSONObject result = new JSONObject();
		result.put("totalCount", pageInfo.getTotal());
		result.put("list", list);
		return result;
	}
	
	@Override
	public int updateOrInsertSysNotice(SysNotice obj) throws Exception {
		int result = 500;
		if(obj.getNoticeId()!=null && obj.getNoticeId()!=""){
			fs.updateFileData(obj.getFiles(),obj.getNoticeId());
			sysNoticeMapper.updateByPrimaryKeySelective(obj);
		}else{
			String id = UUID.randomUUID().toString().replace("-", "");
			obj.setNoticeId(id);
			fs.updateFileData(obj.getFiles(),obj.getNoticeId());
			sysNoticeMapper.insertSelective(obj);
		}

        //插入-新增未读记录(只有当公告状态是已发布时才去添加是否阅读的关联记录)
        /*if ("1".equals(obj.getIsPublished()) && obj.getSysUserList()!=null && obj.getSysUserList().size()!=0 ){
            List<SysUserNotice> sysUserNoticeList = new ArrayList<SysUserNotice>();
            for(SysUser tmp : obj.getSysUserList()){
                SysUserNotice notice = new SysUserNotice();
                notice.setUserId(tmp.getUserId());
                notice.setNoticeId(obj.getNoticeId());
                notice.setUserNoticeStatus(0);
                sysUserNoticeList.add(notice);
            }
            sysUserNoticeMapper.insertBatch(sysUserNoticeList);
        }*/

		result = 200;
		return result;
	}

	@Override
	public int deleteSysNotice(String id) throws Exception {
		int result = 500;
		if(id!=null && id!=""){
			sysNoticeMapper.deleteByPrimaryKey(id);
		}
		result = 200;
		return result;
	}

	@Override
	public SysNotice getSysNoticeById(String id) throws Exception {
		return sysNoticeMapper.selectByPrimaryKey(id);
	}

	@Transactional
	@Override
	public SysNotice publishSysNotice(String id) throws Exception {
		SysNotice sysNotice = sysNoticeMapper.selectByPrimaryKey(id);
	    sysNotice.setNoticePublishtime(DateUtil.dateToStr(new Date(), DateUtil.FMT_SS));
		sysNotice.setIsPublished(1);
		sysNoticeMapper.updateByPrimaryKeySelective(sysNotice);
		SysUserUnitExample ex = new SysUserUnitExample();
		String[] arr = sysNotice.getNoticeReceiver().split(",");
		List<String> unitIds = Arrays.asList(arr);
		ex.setGroupByClause("user_id");
		ex.or().andUnitIdIn(unitIds);
		List<SysUserUnit> sysUserUnitList = sysUserUnitMapper.selectByExample(ex);
		List<SysUserNotice> userNoticeList = new ArrayList<SysUserNotice>();
		for (SysUserUnit sysUserUnit : sysUserUnitList) {
			SysUserNotice sysUserNotice = new SysUserNotice();
			sysUserNotice.setNoticeId(id);
			sysUserNotice.setUserId(sysUserUnit.getUserId());
			sysUserNotice.setUserNoticeStatus(0);
			userNoticeList.add(sysUserNotice);
		}
		if(userNoticeList!=null && userNoticeList.size()>0)
		{
			for(int i=0;i<userNoticeList.size();i++)
			{
				SysUserNotice sysUserNotice=userNoticeList.get(i);
				sysUserNoticeMapper.insert(sysUserNotice);
			}
		}
		//sysUserNoticeMapper.insertBatch(userNoticeList);
		return sysNotice;
	}
	
	@Override
	public List<SysNotice> selectNoticeMain(SysNotice record) {
        List<SysNotice> sysNotices=sysNoticeMapper.selectNoticeMain(record);
//        for (int i = 0,j=sysNotices.size(); i < j; i++) {
//            if (sysNotices.get(i).getNoticeTitle().length()>20){
//                sysNotices.get(i).setNoticeTitle(sysNotices.get(i).getNoticeTitle().substring(0,20)+"...");
//            }
//        }
	    return sysNotices;
	}

	@Transactional
	@Override
	public SysNotice getSysNoticeView(SysNoticeVo vo) {
		if(StringUtils.isNotEmpty(vo.getUserId())) {
			SysUserNoticeExample ex = new SysUserNoticeExample();
			ex.or().andNoticeIdEqualTo(vo.getNoticeId()).andUserIdEqualTo(vo.getUserId());
			List<SysUserNotice> list = sysUserNoticeMapper.selectByExample(ex);
			SysUserNotice sysUserNotice = null;
			if(list != null && list.size() > 0) {
				sysUserNotice = list.get(0);
				if(sysUserNotice.getUserNoticeStatus() == 0) {
					sysUserNotice.setUserNoticeStatus(1);
					sysUserNoticeMapper.updateByExampleSelective(sysUserNotice, ex);
				}
			}else{
                sysUserNotice = new SysUserNotice();
			    sysUserNotice.setNoticeId(vo.getNoticeId());
                sysUserNotice.setUserId(vo.getUserId());
			    sysUserNotice.setUserNoticeStatus(1);
                sysUserNoticeMapper.insert(sysUserNotice);
            }
		}
		return sysNoticeMapper.selectByPrimaryKey(vo.getNoticeId());
	}

	@Override
	public Long getSysNoticeCount(SysNoticeVo vo) {
		SysNoticeExample ex = new SysNoticeExample();
		if(!"admin".equals(vo.getsSearch())){ //超级管理员查看所有数据
            // ex.or().andUserNoticeStatusEqualTo(vo.getUserNoticeStatus()).andUserIdEqualTo(vo.getUserId()).andNoticeReceiverLike(vo.getNoticeReceiver());
            ex.or().andNoticeReceiverLike(vo.getNoticeReceiver());
        }
        ex.or().andIsVariableEqualTo(1);
        Long count = sysNoticeMapper.countByExample(ex);
		return count;
	}

    //获取登录用户已读消息的数目
	@Override
    public int getReadNoticeCount(SysNoticeVo vo){
        Map<String, Object> mapParams = new HashMap<String, Object>();
        mapParams.put("userNoticeStatus","1"); //已读状态
        mapParams.put("isPublished",1); //已发布
        mapParams.put("isVariable",1); //可用
        if(!"admin".equals(vo.getsSearch())) { //超级管理员查看所有数据
            if (StringUtils.isNotBlank(vo.getNoticeReceiver())) {
                mapParams.put("noticeReceiver",vo.getNoticeReceiver());
            }
        }
        if (StringUtils.isNotBlank(vo.getUserId())) {
            mapParams.put("userId",vo.getUserId());
        }
        List<SysNotice> list = sysNoticeMapper.selectPubNoticeList(mapParams);
        if(list!=null && list.size()>=0){
            return list.size();
        }
        return 0;
    }


	private LayuiTableData findByExample(LayuiTableParam param,SysNoticeExample example) 
	{
		//每页显示条数
		int pageSize = param.getLimit();
		//从第多少条开始
		int pageStart = (param.getPage()-1)*pageSize;
		//当前是第几页
		int pageNum = pageStart/pageSize + 1;
		// 1、设置分页信息，包括当前页数和每页显示的总计数
		PageHelper.startPage(pageNum, pageSize);
		
		List<SysNotice> list = sysNoticeMapper.selectByExample(example);
		// 3、获取分页查询后的数据
		PageInfo<SysNotice> pageInfo= new PageInfo<SysNotice>(list);
		LayuiTableData data = new LayuiTableData();
		data.setData(pageInfo.getList());
		Long total = pageInfo.getTotal();
		data.setCount(total.intValue());
		return data;
	}

	@Override
	public LayuiTableData findNoticList(LayuiTableParam param) 
	{
		SysNoticeExample example = new SysNoticeExample();
		//查询当前用户所在机构
		String userId = param.getParam().get("userId").toString();
		SysUserUnitExample ex = new SysUserUnitExample();
		ex.createCriteria().andUserIdEqualTo(userId);
		List<SysUserUnit> sysUserUnitList = sysUserUnitMapper.selectByExample(ex);
		if(sysUserUnitList.size() == 0) {
			return new LayuiTableData();
		}
		for(SysUserUnit unit:sysUserUnitList) {
			SysNoticeExample.Criteria c = example.createCriteria();
			c.andIsPublishedEqualTo(1);
			c.andIsVariableEqualTo(1);
			c.andNoticeReceiverLike("%"+unit.getUnitId()+"%");
			example.or(c);
		}
		return findByExample(param,example);
	}
	
	
	
	
	
	
	public JSONArray getTopList(Map map)throws Exception
	{
  		List<SysNotice> list = sysNoticeMapper.getList(map);
  		JSONArray json = JSONArray.parseArray(JSON.toJSONString(list));
  		return json;
	}
}
