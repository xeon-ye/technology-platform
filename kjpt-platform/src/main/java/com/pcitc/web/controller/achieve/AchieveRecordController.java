package com.pcitc.web.controller.achieve;

import com.github.pagehelper.PageInfo;
import com.pcitc.base.achieve.AchieveRecord;
import com.pcitc.base.achieve.AchieveReward;
import com.pcitc.base.achieve.AchieveSubmit;
import com.pcitc.web.common.RestBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
     * 删除
     */
    private static final String delete = "http://kjpt-zuul/stp-proxy/achieveRecord-api/delete/";


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
            @ApiImplicitParam(name = "achieveType", value = "成果类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "finishUnitName", value = "完成单位", dataType = "string", paramType = "query")
    })
    @RequestMapping(value = "/achieveRecord-api/query", method = RequestMethod.GET)
    @ResponseBody
    public PageInfo query(
            @RequestParam(required = false,value = "pageNum") Integer pageNum,
            @RequestParam(required = false,value = "pageSize") Integer pageSize,
            @RequestParam(required = false,value = "achieveName") String achieveName,
            @RequestParam(required = false,value = "achieveType") String achieveType,
            @RequestParam(required = false,value = "finishUnitName") String finishUnitName

    ) {
        Map<String, Object> condition = new HashMap<>(6);
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
        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<PageInfo> responseEntity = this.restTemplate.exchange(query, HttpMethod.POST, new HttpEntity<Map>(condition, this.httpHeaders), PageInfo.class);
        return responseEntity.getBody();
    }

    @ApiOperation(value="保存")
    @RequestMapping(value = "/achieveRecord-api/save", method = RequestMethod.POST)
    @ResponseBody
    public AchieveSubmit save(@RequestBody AchieveSubmit as){
        this.setBaseData(as);
        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        this.restTemplate.exchange(save, HttpMethod.POST, new HttpEntity<AchieveSubmit>(as, this.httpHeaders), AchieveSubmit.class);
        return as;
    }

    @ApiOperation(value="删除")
    @RequestMapping(value = "/achieveRecord-api/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Integer delete(@PathVariable String id) {
        ResponseEntity<Integer> responseEntity = this.restTemplate.exchange(delete+id, HttpMethod.DELETE, new HttpEntity(this.httpHeaders), Integer.class);
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

        String achieveRewardId = UUID.randomUUID().toString().replace("-","");
        AchieveReward ar = new AchieveReward();
        ar.setId(achieveRewardId);
        ar.setAchieveRecordId(achieveRecordId);

        as.setAchieveRecord(a);
        as.setAchieveReward(ar);
        return as;
    }
}
