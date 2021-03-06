package com.pcitc.web.scientificplan;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.pcitc.base.common.Result;
import com.pcitc.base.expert.ZjkBase;
import com.pcitc.base.scientificplan.SciencePlan;
import com.pcitc.service.scientificplan.SciencePlanService;
import com.pcitc.web.expert.ExpertProviderClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(value = "sciencePlan-api", description = "科技规划接口")
@RestController
@RequestMapping(value = "/sciencePlan-api")
public class SciencePlanClient {

    private final static Logger logger = LoggerFactory.getLogger(SciencePlanClient.class);

    @Autowired
    private SciencePlanService sciencePlanService;

    @ApiOperation(value = "根据主键获取一个平台的信息", notes = "根据主键获取一个平台的信息")
    @RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
    public SciencePlan load(@PathVariable String id) {
        return sciencePlanService.load(id);
    }


    @ApiOperation(value = "科技规划信息保存", notes = "科技规划信息保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public SciencePlan save(@RequestBody SciencePlan sciencePlan) {
        return sciencePlanService.save(sciencePlan);
    }

    @ApiOperation(value = "删除科技规划", notes = "删除科技规划")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Integer delete(@PathVariable String id) {
        return sciencePlanService.delete(id);
    }

    @ApiOperation(value = "查询科技规划列表", notes = "查询科技规划列表")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public PageInfo query(@RequestBody(required = false) Map param) {
        return sciencePlanService.query(param);
    }


    @ApiOperation(value = "无分页查询科技规划列表", notes = "无分页查询科技规划列表")
    @RequestMapping(value = "/queryNoPage", method = RequestMethod.POST)
    public JSONArray queryNoPage(@RequestBody(required = false) Map param) {
        List list = sciencePlanService.queryNoPage(param);
        JSONArray json = JSONArray.parseArray(JSON.toJSONString(list));
        return json;
    }

    @ApiOperation(value = "导入科技规划", notes = "导入科技规划")
    @RequestMapping(value = "/excel_input", method = RequestMethod.POST)
    public Result excel_input(@RequestBody List<SciencePlan> list) throws Exception
    {
        Result result=new Result();
        try {
            int count = sciencePlanService.insertBatch(list);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("导入科技规划失败");
            logger.error("导入专家信息失败", e);
        }
        return result;
    }

}
