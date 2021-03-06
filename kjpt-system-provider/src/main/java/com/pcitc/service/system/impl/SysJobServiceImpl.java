package com.pcitc.service.system.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.quartz.DateBuilder;
import org.quartz.Job;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pcitc.base.common.LayuiTableData;
import com.pcitc.base.common.LayuiTableParam;
import com.pcitc.base.system.SysQrtzLog;
import com.pcitc.base.system.SysJob;
import com.pcitc.mapper.system.SysQrtzLogMapper;
import com.pcitc.mapper.system.SysJobMapper;
import com.pcitc.service.system.SysJobService;
import com.pcitc.service.system.TimedTask;
import com.pcitc.service.system.TimedTaskManager;

/**
 * package: com.pcitc.service.system.impl describe: 维护系统作业 user: yyl date:
 * 2018/5/23
 **/
@Service
public class SysJobServiceImpl implements SysJobService {

	private final static Logger logger = LoggerFactory.getLogger(SysJobServiceImpl.class);

	@Autowired
	private SysJobMapper sysJobMapper;
	@Autowired
	private SysQrtzLogMapper sysCronExceptionLogMapper;
	
	
	

	/**
	 * 按id查询
	 *
	 * @param id
	 * @return
	 */
	@Override
	public SysJob findSysJobById(String id) {
		return sysJobMapper.selectByPrimaryKey(id);
	}

	/**
	 * 按条件查询
	 *
	 * @param param
	 * @return
	 */
	@Override
	public LayuiTableData findSysJob(LayuiTableParam param) {
		
		// 1、设置分页信息，包括当前页数和每页显示的总计数
    	PageHelper.startPage(param.getPage(), param.getLimit());
        Map<String, Object> map = param.getParam();

        List<SysJob> list = sysJobMapper.selectByCondition(map);
        
        PageInfo<SysJob> pageInfo = new PageInfo<SysJob>(list);
		LayuiTableData data = new LayuiTableData();
		data.setData(pageInfo.getList());
		Long total = pageInfo.getTotal();
		data.setCount(total.intValue());
		return data;
	}

	/**
	 * 按条件查询
	 *
	 * @param param
	 * @return
	 */
	/*
	 * @Override public LayuiTableData findSysExcepJob(LayuiTableParam param) {
	 * 
	 * // 1、设置分页信息，包括当前页数和每页显示的总计数 PageHelper.startPage(param.getPage(),
	 * param.getLimit()); Map<String, Object> map = param.getParam();
	 * 
	 * List<SysCronRecord> list = sysCronRecordMapper.selectByExample(null);
	 * 
	 * PageInfo<SysCronRecord> pageInfo = new PageInfo<SysCronRecord>(list);
	 * LayuiTableData data = new LayuiTableData(); data.setData(pageInfo.getList());
	 * Long total = pageInfo.getTotal(); data.setCount(total.intValue()); return
	 * data; }
	 */

	/**
	 * 删除一条记录
	 *
	 * @param dataId
	 * @return
	 */
	@Override
	public Integer deleteSysJob(String dataId) {
		Integer ret = sysJobMapper.deleteByPrimaryKey(dataId);
		if (ret > 0) {
			SysJob sysJob = sysJobMapper.selectByPrimaryKey(dataId);
			TimedTaskManager.removeTimedTask(sysJob.getJobName(), sysJob.getJobGroup());
		}
		return ret;
	}

	/**
	 * 保存作业
	 *
	 * @param job
	 * @return
	 */
	@Override
	public Integer saveSysJob(SysJob job) {
		if (job.getDataId() == null || "".equals(job.getDataId())) {
			job.setDataId(UUID.randomUUID().toString().replace("-", ""));
			return sysJobMapper.insert(job);
		} else {
			return sysJobMapper.updateByPrimaryKey(job);
		}
	}

	/**
	 * 保存作业
	 *
	 * @param sysCronRecord
	 * @return
	 */
	/*
	 * public Integer saveSysExcepJob(SysCronRecord sysCronRecord) { if
	 * (sysCronRecord.getDataId() == null || "".equals(sysCronRecord.getDataId())) {
	 * sysCronRecord.setDataId(Integer.parseInt(UUID.randomUUID().toString().replace
	 * ("-", ""))); return sysCronRecordMapper.insert(sysCronRecord); } else {
	 * return sysCronRecordMapper.updateByPrimaryKey(sysCronRecord); } }
	 */

	/**
	 * 终止作业
	 *
	 * @param dataId
	 * @return
	 */
	@Override
	public Integer pauseJob(String dataId) {
		SysJob sysJob = sysJobMapper.selectByPrimaryKey(dataId);
		sysJob.setDelFlag(1);
		Integer ret = sysJobMapper.updateByPrimaryKey(sysJob);
		if (ret > 0) {
			TimedTaskManager.pauseTimedTask(sysJob.getJobName(), sysJob.getJobGroup());
		}
		return ret;
	}

	/**
	 * 继续作业
	 *
	 * @param dataId
	 * @return
	 */
	@Override
	public Integer resumeJob(String dataId) {
		SysJob sysJob = sysJobMapper.selectByPrimaryKey(dataId);
		sysJob.setDelFlag(0);
		Integer ret = sysJobMapper.updateByPrimaryKey(sysJob);
		if (ret > 0) {
			TimedTaskManager.resumeTimedTask(sysJob.getJobName(), sysJob.getJobGroup());
		}
		return ret;
	}

	/**
	 * 执行作业
	 *
	 * @param dataId
	 * @return
	 */
	@Override
	public Integer executeJob(String dataId) {
		SysJob sysJob = sysJobMapper.selectByPrimaryKey(dataId);
		System.out.println(">>>>>>>>>>>>>>>执行定时任务："+sysJob.getJobName());
		Integer ret = 0;
		try {
			if (sysJob != null) {
				TimedTaskManager.executeJob(sysJob.getJobName(), sysJob.getJobGroup());
				ret = 1;
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public void startJobs() {

		logger.info("启动定时任务服务。");
		List<SysJob> list = sysJobMapper.selectByCondition(new HashedMap());
		for (SysJob job : list) {
			TimedTask timedTask = this.parse2TimedTask(job);

			if (timedTask != null) {
				TimedTaskManager.addTimedTask(timedTask);
				if (job.getDelFlag() == 1) {
					TimedTaskManager.pauseTimedTask(timedTask.getJobName());
				}
			}
		}
	}

	public TimedTask parse2TimedTask(SysJob entity) {
		TimedTask timedTask = new TimedTask();
		if (StringUtils.isNotEmpty(entity.getJobGroup())) {
			timedTask.setJobGroupName(entity.getJobGroup());
		}

		timedTask.setJobName(entity.getJobName());

		try {
			timedTask.setJobClass((Class<? extends Job>) Class.forName(entity.getJobClass()));
		} catch (ClassNotFoundException ex) {
			logger.error(entity.getJobName() + "启动定时任务失败。", ex);
			return null;
		}

		if (StringUtils.isNotEmpty(entity.getIntervalUnit())) {
			timedTask.setIntervalUnit(DateBuilder.IntervalUnit.valueOf(entity.getIntervalUnit()));
			timedTask.setTimeInterval(Integer.valueOf(Integer.parseInt(entity.getTimeInterval())));
		} else {
			timedTask.setCronExpression(entity.getCronExpression());
		}

		try {
			if (StringUtils.isNotEmpty(entity.getStartTime())) {
				timedTask.setStartTime((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(entity.getStartTime()));
			}

			if (StringUtils.isNotEmpty(entity.getEndTime())) {
				timedTask.setEndTime((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(entity.getEndTime()));
			}
		} catch (ParseException ex) {
			logger.error(entity.getJobName() + "启动定时任务失败。", ex);
		}

		return timedTask;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public SysQrtzLog selectSysCronExceptionLog(String id) throws Exception
	{
		return sysCronExceptionLogMapper.selectByPrimaryKey(id);
	}

	public Integer updateSysCronExceptionLog(SysQrtzLog record)throws Exception
	{
		return sysCronExceptionLogMapper.updateByPrimaryKey(record);
	}

	public int deleteSysCronExceptionLog(String id)throws Exception
	{
		return sysCronExceptionLogMapper.deleteByPrimaryKey(id);
	}

	public Integer insertSysCronExceptionLog(SysQrtzLog record)throws Exception
	{
		return sysCronExceptionLogMapper.insert(record);
	}

	
	//查询作业异常列表数据
	public LayuiTableData getSysCronExceptionLogPage(LayuiTableParam param)throws Exception
	{
		
		
		        //每页显示条数
				int pageSize = param.getLimit();
				//从第多少条开始
				int pageStart = (param.getPage()-1)*pageSize;
				//当前是第几页
				int pageNum = pageStart/pageSize + 1;
				// 1、设置分页信息，包括当前页数和每页显示的总计数
				PageHelper.startPage(pageNum, pageSize);
						
				String jobName=getTableParam(param,"jobName","");
				String jobClass=getTableParam(param,"jobClass","");
				String logType=getTableParam(param,"logType","");
				String createTimeStr=getTableParam(param,"createTimeStr","");
				
				System.out.println(">>>>>>>>>>>>>>>createTimeStr："+createTimeStr);
				System.out.println(">>>>>>>>>>>>>>>jobName："+jobName);
				
				Map map=new HashMap();
				map.put("jobName", jobName);
				map.put("createTimeStr", createTimeStr);
				map.put("logType", logType);
				map.put("jobClass", jobClass);
				
				
				List<SysQrtzLog> list = sysCronExceptionLogMapper.getList(map);
				PageInfo<SysQrtzLog> pageInfo = new PageInfo<SysQrtzLog>(list);
				System.out.println(">>>>>>>>查询作业异常列表数据分页结果 "+pageInfo.getList().size());
				
				LayuiTableData data = new LayuiTableData();
				data.setData(pageInfo.getList());
				Long total = pageInfo.getTotal();
				data.setCount(total.intValue());
				
			    return data;
	}

	
	
	private String getTableParam(LayuiTableParam param,String paramName,String defaultstr)
	{
		String resault="";
		Object object=param.getParam().get(paramName);
		if(object!=null)
		{
			resault=(String)object;
		}
		return resault;
	}

	

	
	
	
	
	
}
