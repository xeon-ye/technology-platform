package com.pcitc.listener;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.pcitc.base.util.HostUtil;
import com.pcitc.service.system.SysJobService;

/**
 * package: com.pcitc.listener
 * describe: TODO
 * user: yyl
 * date: 2018/5/30
 **/
@Component
public class AppListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private SysJobService sysJobService;
    
    @Value("${jobRunHosts}")
    private String jobRunHosts;
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    	//当前主机的网络地址
    	Set<String> hostSet = HostUtil.getLocalHostAddressSet();
    	System.out.println("当前主机的网络地址: "+JSON.toJSONString(hostSet));
    	//可执行定时任务的主机 
    	Set<String> acceptSet = new HashSet<String>(Arrays.asList(jobRunHosts.split(",")));
    	System.out.println("---------可执行定时任务的主机 >>>>>>>>"+JSON.toJSONString(acceptSet));
    	acceptSet.retainAll(hostSet);
    	if(acceptSet.size() > 0)
    	{
    		sysJobService.startJobs();
    	}
    }
}
