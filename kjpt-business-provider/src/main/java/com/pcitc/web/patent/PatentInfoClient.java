package com.pcitc.web.patent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.pcitc.base.common.Result;
import com.pcitc.base.patent.PatentInfo;
import com.pcitc.service.file.FileCommonService;
import com.pcitc.service.patent.PatentInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务接口
 * </p>
 * <p>
 * Table: patent_info - 专利信息
 * </p>
 *
 * @since 2019-11-16 04:10:36
 */
@Api(value = "PatentInfoClient-API", description = "专利信息服务接口")
@RestController
public class PatentInfoClient {
	private final static Logger logger = LoggerFactory.getLogger(PatentInfoClient.class);

	@Autowired
	PatentInfoService patentInfoService;

	/**
	 * 保存
	 *
	 * @param patentInfo
	 * @return
	 */
	@ApiOperation(value = "新增或者更新专利信息", notes = "新增或者更新专利信息,操作成功返回专利对象")

	@RequestMapping(value = "/patent-provider/patentInfo/patentInfo_save", method = RequestMethod.POST)
	public PatentInfo updateOrInsertPatentInfo(@RequestBody PatentInfo patentInfo) { return
			patentInfoService.updateOrInsertPatentInfo(patentInfo);
	}

	/**
	 * 分页查询
	 *
	 * @param param
	 * @return
	 */
	@ApiOperation(value = "查利列表-分页查询", notes = "查利列表-分页查询")
	@RequestMapping(value = "/patent-provider/patentInfo/patentInfo_query", method = RequestMethod.POST)
	public PageInfo queryPatentListByPage(@RequestBody(required = false) Map param) {
		return patentInfoService.queryPatentList(param);
	}

	/**
	 * 分页查询
	 *
	 * @param param
	 * @return
	 */
	@ApiOperation(value = "查利列表-分页查询", notes = "查利列表-分页查询")
	@RequestMapping(value = "/patent-provider/patentInfo/patentInfo_queryPatent", method = RequestMethod.POST)
	public List queryPatent(@RequestBody(required = false) Map param) {
		return patentInfoService.queryPatent(param);
	}

	@ApiOperation(value = "查询专利详细信息", notes = "按ID查询查询专利详细信息详情信息,操作成功返回PatentInfo对象")
	@RequestMapping(value = "/patent-provider/patentInfo/patentInfo_load/{id}", method = RequestMethod.GET)
	public PatentInfo getPatentInfo(@PathVariable String id) {
		return patentInfoService.getPatentInfo(id);
	}

	/**
	 * 逻辑删除专利信息
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "逻辑删除专利信息", notes = "逻辑删除专利信息")
	@RequestMapping(value = "/patent-provider/patentInfo/patentInfo_delete/{id}", method = RequestMethod.DELETE)
	public Integer deletePatent(@PathVariable String id) {
		return patentInfoService.deletePatent(id);
	}

	/**
	 * 批量移除
	 *
	 * @param ids
	 * @return
	 */
	@ApiOperation(value = "批量移除后专项", notes = "批量移除后专项")
	@RequestMapping(value = "/patent-provider/patentInfo/batchRemove/{ids}", method = RequestMethod.POST)
	public Integer batchRemove(@PathVariable String ids) {
		return patentInfoService.batchRemove(ids);
	}
	/**
	 * 批量后处理
	 *
	 * @param ids
	 * @return
	 */
	@ApiOperation(value = "批量后处理", notes = "批量后处理")
	@RequestMapping(value = "/patent-provider/patentInfo/postTreatment/{ids}", method = RequestMethod.POST)
	public Integer postTreatment(@PathVariable String ids) {
		return patentInfoService.postTreatment(ids);
	}

	@ApiOperation(value = "查询专利列表", notes = "查询专利列表")
	@RequestMapping(value = "/patent-provider/patentInfo/queryNoPage", method = RequestMethod.POST)
	public JSONArray queryNoPage(@RequestBody(required = false) Map param){
		List list=patentInfoService.queryNoPage(param);
		JSONArray json = JSONArray.parseArray(JSON.toJSONString(list));
		return json;
	}


	@ApiOperation(value = "根据法律状态查询专利数量", notes = "根据法律状态查询专利数量")
	@RequestMapping(value = "/patent-provider/patentInfo/countByLegalStatus", method = RequestMethod.POST)
	public List<Map> countByLegalStatus(@RequestBody(required = false)Map param) {
		return patentInfoService.countByLegalStatus(param);
	}

	@ApiOperation(value = "根据专利类型查询专利数量", notes = "根据专利类型查询专利数量")
	@RequestMapping(value = "/patent-provider/patentInfo/countByPatentType", method = RequestMethod.POST)
	public List<Map> countByPatentType(@RequestBody(required = false)Map param) {
		return patentInfoService.countByPatentType(param);
	}

	@ApiOperation(value = "导入专利管理信息", notes = "导入专利管理信息")
	@RequestMapping(value = "/patent-provider/patentInfo/excel_input", method = RequestMethod.POST)
	public Result excel_input(@RequestBody List<PatentInfo> list) throws Exception
	{
		Result result=new Result();
		try {
			patentInfoService.insertBatch(list);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("导入专利管理信息失败");
			logger.error("导入专利管理信息失败", e);
		}
		return result;
	}

}
