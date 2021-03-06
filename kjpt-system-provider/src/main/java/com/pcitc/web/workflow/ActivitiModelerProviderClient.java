package com.pcitc.web.workflow;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pcitc.base.common.LayuiTableData;
import com.pcitc.base.common.LayuiTableParam;
import com.pcitc.base.common.Result;
import com.pcitc.base.util.DateUtil;
import com.pcitc.base.util.StrUtil;
import com.pcitc.base.workflow.ProcessDefVo;
import com.pcitc.base.workflow.WorkflowVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Activity-system-API", description = "activiti基础功能流程相关的接口")
@RestController
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class ActivitiModelerProviderClient implements ModelDataJsonConstants {

	// 文件上传路径
	@Value("${spring.http.multipart.location}")
	private String				uploadPath;

	@Autowired
	private RepositoryService	repositoryService;

	@Autowired
	private ObjectMapper		objectMapper;

	String						DIR_PATH	= "";

	/**
	 * 新建模型
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ApiOperation(value = "创建activiti业务模型", notes = "activiti系统接口")
	@RequestMapping("/modeler-provider/model/create")
	public String newModel() throws UnsupportedEncodingException {
		// 初始化一个空模型
		System.out.println("=====newModel");
		Model model = repositoryService.newModel();

		// 设置一些默认信息，可以用参数接收
		int revision = 1;
		String key = "process";
		String name = "process";
		String description = "新建流程模型";

		// ModelEditorSource
		ObjectNode editorNode = objectMapper.createObjectNode();
		editorNode.put("id", "canvas");
		editorNode.put("resourceId", "canvas");
		ObjectNode stencilSetNode = objectMapper.createObjectNode();
		stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
		editorNode.put("stencilset", stencilSetNode);

		ObjectNode modelNode = objectMapper.createObjectNode();
		modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
		modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
		modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);

		model.setName(name);
		model.setKey(key);
		model.setMetaInfo(modelNode.toString());

		repositoryService.saveModel(model);

		String id = model.getId();
		repositoryService.addModelEditorSource(id, editorNode.toString().getBytes(StandardCharsets.UTF_8));

		return id;
	}

	@ApiOperation(value = "获取模型实际的json数据", notes = "activiti系统接口")
	@RequestMapping(value = "/modeler-provider/model/json-data/{modelId}", method = RequestMethod.GET, produces = "application/json")
	public ObjectNode getEditorJson(@PathVariable String modelId) {
		ObjectNode modelNode = null;

		Model model = repositoryService.getModel(modelId);

		if (model!=null) {
			try {
				if (StringUtils.isNotEmpty(model.getMetaInfo())) {
					modelNode = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
				} else {
					modelNode = objectMapper.createObjectNode();
					modelNode.put(MODEL_NAME, model.getName());
				}
				modelNode.put(MODEL_ID, model.getId());
				ObjectNode editorJsonNode = (ObjectNode) objectMapper.readTree(new String(repositoryService.getModelEditorSource(model.getId()), StandardCharsets.UTF_8));
				modelNode.put("model", editorJsonNode);

			} catch (Exception e) {
				throw new ActivitiException("Error creating model JSON", e);
			}
		}
		return modelNode;
	}

	@ApiOperation(value = "保存画好的model模型", notes = "activiti系统接口")
	@RequestMapping(value = "/modeler-provider/model/save", method = RequestMethod.POST)
	public void saveModel(@RequestBody WorkflowVo workflowVo) {// 对接收参数进行了修改
		try {
			Model model = repositoryService.getModel(workflowVo.getModelId());

			ObjectNode modelJson = (ObjectNode) objectMapper.readTree(model.getMetaInfo());

			modelJson.put(MODEL_NAME, workflowVo.getModelName());
			modelJson.put(MODEL_DESCRIPTION, workflowVo.getModelDescription());
			model.setMetaInfo(modelJson.toString());
			model.setName(workflowVo.getModelName());

			repositoryService.saveModel(model);
			repositoryService.addModelEditorSource(model.getId(), workflowVo.getJsonXml().getBytes(StandardCharsets.UTF_8));

			InputStream svgStream = new ByteArrayInputStream(workflowVo.getSvgXml().getBytes(StandardCharsets.UTF_8));
			TranscoderInput input = new TranscoderInput(svgStream);

			PNGTranscoder transcoder = new PNGTranscoder();
			// Setup output
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			TranscoderOutput output = new TranscoderOutput(outStream);

			// Do the transformation
			transcoder.transcode(input, output);
			final byte[] result = outStream.toByteArray();
			repositoryService.addModelEditorSourceExtra(model.getId(), result);
			outStream.close();

		} catch (Exception e) {
			throw new ActivitiException("Error saving model", e);
		}
	}

	@ApiOperation(value = "产生model资源，直接传到前台byte，", notes = "和generateResource方法有别")
	@RequestMapping(value = "/modeler-provider/resource/byte1", method = RequestMethod.POST)
	public byte[] generateResourceByByte(@RequestBody WorkflowVo workflowVo) {
		try {
			Model modelData = repositoryService.getModel(workflowVo.getModelId());
			if (workflowVo.getDataType().equals("xml")) {

				BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
				JsonNode editorNode = objectMapper.readTree(repositoryService.getModelEditorSource(modelData.getId()));
				BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
				BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
				byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

				return bpmnBytes;
			} else {
				byte[] pngBytes = repositoryService.getModelEditorSourceExtra(workflowVo.getModelId());
				return pngBytes;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@ApiOperation(value = "产生model资源，oss图片路径", notes = "oss图片路径")
	@RequestMapping(value = "/modeler-provider/resource/image/path", method = RequestMethod.POST)
	public String ossImagePath(@RequestBody WorkflowVo workflowVo) {
		Model modelData = repositoryService.getModel(workflowVo.getModelId());
		String resourcePath=ClassUtils.getDefaultClassLoader().getResource("").getPath();
		//String imagePath = OSSUtil.OSSPATH+"/"+OSSUtil.BUCKET+"/"+uploadPath+"activiti/"+modelData.getId()+".model.png";
		//return imagePath;
		return null;
	}

	@ApiOperation(value = "产生model资源，图形化准备", notes = "activiti系统接口")
	@RequestMapping(value = "/modeler-provider/resource", method = RequestMethod.POST)
	public String generateResource(@RequestBody WorkflowVo workflowVo) {
		try {
			System.out.println("generateResource=====部署model模型");
			Model modelData = repositoryService.getModel(workflowVo.getModelId());
			if (workflowVo.getDataType().equals("xml")) {
				System.out.println("1xml=====部署model模型"+modelData.getId());
				BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
				JsonNode editorNode = objectMapper.readTree(repositoryService.getModelEditorSource(modelData.getId()));
				System.out.println("2xml=====部署model模型"+editorNode);
				BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
				System.out.println("3xml=====部署model模型"+bpmnModel);
				BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
				System.out.println("4xml=====部署model模型"+xmlConverter);
				byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);
				System.out.println("5xml=====部署model模型"+bpmnBytes);
				ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
				System.out.println("6xml=====部署model模型"+in.available());
				String fileName = modelData.getId()+".model.bpmn";
				System.out.println("7xml=====部署model模型"+uploadPath+"activiti/"+fileName);
				// 上传xml到oss服务器
				//OSSUtil.uploadFileByInputStream(in, uploadPath+"activiti/", fileName);

				String realName = fileName.replaceAll("\\\\", "/");
				System.out.println("8xml=====部署model模型"+realName);
				return realName;
			} else {
				System.out.println("1image=====部署model模型"+workflowVo.getModelId());
				byte[] pngBytes = repositoryService.getModelEditorSourceExtra(workflowVo.getModelId());
				System.out.println("2image=====部署model模型"+pngBytes);
				String fileName = modelData.getId()+".model.png";
				System.out.println("3image=====部署model模型");
				ByteArrayInputStream in = new ByteArrayInputStream(pngBytes);
				// 上传image到oss服务器
				System.out.println("31image=====部署model模型"+uploadPath+"activiti/"+fileName);
				//String imagePath = OSSUtil.uploadFileByInputStream(in, uploadPath+"activiti/", fileName);
				//System.out.println("4image=====部署model模型"+imagePath);
				String realName = fileName.replaceAll("\\\\", "/");
				return realName;
			}
		} catch (Exception e) {
			return "";
		}
		
	}
    /**
	@ApiOperation(value = "部署model模型", notes = "activiti系统接口")
	@RequestMapping(value = "/modeler-provider/model/deploy", method = RequestMethod.POST)
	public Result deployModel(@RequestBody WorkflowVo workflowVo) throws Exception {
		System.out.println("1=====部署model模型");
		// 获取模型
		Model modelData = repositoryService.getModel(workflowVo.getModelId());
		byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());
		System.out.println("2=====部署model模型"+modelData);
		System.out.println("2=====部署model模型"+modelData.getId());
		System.out.println("2=====部署model模型"+bytes);
		if (bytes==null) { return new Result(false, workflowVo.getModelId(), "模型数据为空，请先设计流程并成功保存，再进行发布。"); }

		JsonNode modelNode = objectMapper.readTree(bytes);
		System.out.println("3=====部署model模型"+modelNode);
		BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
		System.out.println("4=====部署model模型"+model.getProcesses());
		if (model.getProcesses().size()==0) { return new Result(false, workflowVo.getModelId(), "数据模型不符要求，请至少设计一条主线流程。"); }
		System.out.println("51=====部署model模型");
		try {
			workflowVo.setDataType("xml");
			String xml = generateResource(workflowVo);
			System.out.println("513=====部署model模型"+xml);
			workflowVo.setDataType("image");
			String image = generateResource(workflowVo);
			if (xml=="") { return new Result(false, "部署失败", "流程尚未定义或定义错误，不能生成有效的xml和png文件"); }
			System.out.println("53=====部署model模型"+modelData.getId());
			System.out.println("52=====部署model模型"+xml);
			System.out.println("52=====部署model模型"+image);
			
			String zipFileName = modelData.getId()+".bpmn20.model.zip";
			String zipPath = OSSUtil.generateZipFile(uploadPath+"activiti", zipFileName, xml, image);
			System.out.println("1zipPath======="+zipPath);
			InputStream inputStream = OSSUtil.getOssFileIS(zipPath.split(OSSUtil.OSSPATH+"/"+OSSUtil.BUCKET+"/")[1]);
			ZipInputStream zipInputStream = new ZipInputStream(inputStream);
			System.out.println("2zipPath======="+zipInputStream);
			// 发布流程
			// 使用addZipInputStream后可以预防flow连线文字丢失的问题
			DeploymentBuilder deploymentBuilder = repositoryService.createDeployment().name(modelData.getName()).category(modelData.getCategory()).tenantId(modelData.getTenantId()).addZipInputStream(zipInputStream);
			
			List<JsonNode> forms = modelNode.findValues("formkeydefinition");
			for (JsonNode form : forms) {
				String formName = form.textValue();
				if (!StrUtil.isEmpty(formName)) {
					InputStream stream = this.getClass().getClassLoader().getResourceAsStream(formName);
					deploymentBuilder.addInputStream(formName, stream);
				}
			}
			Deployment deployment = deploymentBuilder.deploy();

			// 更新流程定义类别,替换掉页面的定义
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
			if (processDefinition!=null) repositoryService.setProcessDefinitionCategory(processDefinition.getId(), deployment.getCategory());

			modelData.setDeploymentId(deployment.getId());
			repositoryService.saveModel(modelData);
			return new Result(true);
		} catch (Exception ex) {
			return new Result(false, "部署失败", ex.getMessage());
		}
	}
	
	*/
	
	
	@ApiOperation(value = "部署model模型", notes = "activiti系统接口")
	@RequestMapping(value = "/modeler-provider/model/deploy", method = RequestMethod.POST)
	public Result deployModel(@RequestBody WorkflowVo workflowVo) throws Exception {
		
		Result result=new Result();
		result.setSuccess(true);
		try {
			//获取模型
			Model modelData = repositoryService.getModel(workflowVo.getModelId());
			byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());
			if (bytes == null) 
			{
				result.setSuccess(false);
				result.setMessage("模型数据为空，请先设计流程并成功保存，再进行发布。");
			    return result ;
			}
			JsonNode modelNode = new ObjectMapper().readTree(bytes);
			BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
			if(model.getProcesses().size()==0)
			{
				result.setSuccess(false);
				result.setMessage("数据模型不符要求，请至少设计一条主线流程。");
			    return result ;
			}
			byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
			//发布流程
			String processName = modelData.getId() + ".bpmn20.xml";
			String processName_png = modelData.getId() + ".bpmn20.png";
			/*
			 * String
			 * resourcePath=ClassUtils.getDefaultClassLoader().getResource("").getPath();
			 * File file = new File(resourcePath,"tem/"+processName); InputStream fis = new
			 * BufferedInputStream(new FileInputStream(resourcePath+"tem/"+processName));
			 */
			Deployment deployment = repositoryService.createDeployment()
			        .name(modelData.getName())
			        .addString(processName, new String(bpmnBytes, "UTF-8"))
			        .deploy();
           // 更新流程定义类别,替换掉页面的定义
		//	ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
			//if (processDefinition!=null) repositoryService.setProcessDefinitionCategory(processDefinition.getId(), deployment.getCategory());
					
			modelData.setDeploymentId(deployment.getId());
			repositoryService.saveModel(modelData);
			return new Result(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "部署失败", e.getMessage());
		}
	}
	
	

	@ApiOperation(value = "删除model模型", notes = "activiti系统接口")
	@RequestMapping(value = "/modeler-provider/model/delete", method = RequestMethod.POST)
	public Result deleteModel(@RequestBody WorkflowVo workflowVo) throws Exception {
		repositoryService.deleteModel(workflowVo.getModelId());
		return new Result(true);
	}

	@ApiOperation(value = "导出model模型", notes = "activiti系统接口")
	@RequestMapping(value = "/modeler-provider/model/export", method = RequestMethod.POST)
	public byte[] exportModel(@RequestBody WorkflowVo workflowVo) throws Exception {
		try {
			Model modelData = repositoryService.getModel(workflowVo.getModelId());
			BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
			JsonNode editorNode = objectMapper.readTree(repositoryService.getModelEditorSource(modelData.getId()));
			BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
			BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
			byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

			return bpmnBytes;
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * @ApiOperation(value = "上传model模型", notes = "activiti系统接口")
	 * 
	 * @RequestMapping(value = "/modeler-provider/model/upload", method =
	 * RequestMethod.POST) public Result uploadModel(@RequestBody WorkflowVo
	 * workflowVo) { List<SysFile> fileList = workflowVo.getFileList(); boolean
	 * bpmnFlag = true; String msg = ""; ModelQuery mq =
	 * repositoryService.createModelQuery();
	 * 
	 * for (int i = 0; i<fileList.size(); i++) { try { InputStream input =
	 * OSSUtil.getOssFileIS(fileList.get(i).getFilePath().split(OSSUtil.OSSPATH+"/"+
	 * OSSUtil.BUCKET+"/")[1]); // 创建转换对象 BpmnXMLConverter converter = new
	 * BpmnXMLConverter(); XMLInputFactory factory = XMLInputFactory.newInstance();
	 * XMLStreamReader reader = factory.createXMLStreamReader(input);//
	 * createXmlStreamReader // 将xml文件转换成BpmnModel BpmnModel bpmnModel =
	 * converter.convertToBpmnModel(reader); mq =
	 * mq.modelName(fileList.get(i).getFileName().substring(0,
	 * fileList.get(i).getFileName().indexOf("."))); if (mq.count()>0) { msg =
	 * fileList.get(i).getFileName().substring(0,
	 * fileList.get(i).getFileName().indexOf("."))+"-->已经上传，请重新选择！"; bpmnFlag =
	 * false; break; } if
	 * (bpmnModel.getMainProcess()==null||bpmnModel.getMainProcess().getId()==null)
	 * { msg = fileList.get(i).getFileName().substring(0,
	 * fileList.get(i).getFileName().indexOf("."))+"-->流程文件解析有问题！"; bpmnFlag =
	 * false; break; } } catch (Exception e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } } System.out.println("3uploadModel===="+msg); if
	 * (!bpmnFlag) { // 上传的文件有问题 return new Result(false, "部署失败", msg); } for (int i
	 * = 0; i<fileList.size(); i++) {
	 * 
	 * try { InputStream input =
	 * OSSUtil.getOssFileIS(fileList.get(i).getFilePath().split(OSSUtil.OSSPATH+"/"+
	 * OSSUtil.BUCKET+"/")[1]); // 创建转换对象 BpmnXMLConverter converter = new
	 * BpmnXMLConverter(); XMLInputFactory factory = XMLInputFactory.newInstance();
	 * XMLStreamReader reader = factory.createXMLStreamReader(input);//
	 * createXmlStreamReader // 将xml文件转换成BpmnModel BpmnModel bpmnModel =
	 * converter.convertToBpmnModel(reader);
	 * 
	 * BpmnJsonConverter bpmnCon = new BpmnJsonConverter(); ObjectNode modelNode =
	 * bpmnCon.convertToJson(bpmnModel); Model modelData =
	 * repositoryService.newModel(); ObjectNode modelObjectNode = new
	 * ObjectMapper().createObjectNode(); modelObjectNode.put("name",
	 * fileList.get(i).getFileName().substring(0,
	 * fileList.get(i).getFileName().indexOf("."))); modelObjectNode.put("revision",
	 * 1);
	 * 
	 * modelData.setMetaInfo(modelObjectNode.toString());
	 * modelData.setName(fileList.get(i).getFileName().substring(0,
	 * fileList.get(i).getFileName().indexOf(".")));
	 * repositoryService.saveModel(modelData);
	 * repositoryService.addModelEditorSource(modelData.getId(),
	 * modelNode.toString().getBytes(StandardCharsets.UTF_8));
	 * 
	 * } catch (Exception e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } } return new Result(true, "部署成功"); }
	 */

	@ApiOperation(value = "查询所有流程定义", notes = "查询条件：模型名称；分页查询")
	@RequestMapping(value = "/modeler-provider/process/define/list", method = RequestMethod.POST)
	public Object selectProcessDefineList(@RequestBody LayuiTableParam param) {
		int limit = 15;
		int page = 1;
		String processName = null;

		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		DeploymentQuery deQuery = repositoryService.createDeploymentQuery();
		if (param.getLimit()!=null&&!StrUtil.isBlankOrNull(param.getLimit().toString())) {
			limit = Integer.parseInt(param.getLimit().toString());
		}
		if (param.getPage()!=null&&!StrUtil.isBlankOrNull(param.getPage().toString())) {
			page = Integer.parseInt(param.getPage().toString());
		}
		if (param.getParam().get("processName")!=null&&!StrUtil.isBlankOrNull(param.getParam().get("processName").toString())) {
			processName = "%"+param.getParam().get("processName").toString()+"%";
			query = query.processDefinitionNameLike(processName);
		}
		if (param.getParam().get("processDefinitionId")!=null&&!StrUtil.isBlankOrNull(param.getParam().get("processDefinitionId").toString())) {
			query = query.processDefinitionId(param.getParam().get("processDefinitionId").toString());
		}
		if (param.getParam().get("deploymentId")!=null&&!StrUtil.isBlankOrNull(param.getParam().get("deploymentId").toString())) {
			query = query.deploymentId(param.getParam().get("deploymentId").toString());
		}
		int dataCount = (int) query.count();
		List<ProcessDefinition> processDefList = query.orderByProcessDefinitionName().desc().listPage(limit*(page-1), limit);
		List<ProcessDefVo> retList = new ArrayList<ProcessDefVo>();
		for (ProcessDefinition processDefinition : processDefList) {
			ProcessDefinitionEntity entity = (ProcessDefinitionEntity) processDefinition;
			String deploymentId = processDefinition.getDeploymentId();

			ProcessDefVo vo = new ProcessDefVo();
			BeanUtils.copyProperties(entity, vo);
			Deployment deployment = deQuery.deploymentId(deploymentId).singleResult();
			System.out.println(deploymentId+"============="+DateUtil.dateToStr(deployment.getDeploymentTime(), "yyyy-MM-dd HH:mm:ss"));
			vo.setCreateDate(deployment.getDeploymentTime());
			retList.add(vo);
		}

		LayuiTableData data = new LayuiTableData();
		data.setData(retList);
		data.setCount(dataCount);

		return data;
	}

	@ApiOperation(value = "删除流程定义", notes = "activiti系统接口")
	@RequestMapping(value = "/modeler-provider/process/define/delete", method = RequestMethod.POST)
	public Result deleteProcessDefine(@RequestBody WorkflowVo workflowVo) throws Exception {
		ProcessDefinition pd = repositoryService.getProcessDefinition(workflowVo.getProcessDefineId());
		try {
			System.out.println("pd.getDeploymentId()==="+pd.getDeploymentId());
			repositoryService.deleteDeployment(pd.getDeploymentId(),true);
			return new Result(true, workflowVo.getProcessDefineId(), "成功删除");
		} catch (Exception e) {
			return new Result(false, workflowVo.getProcessDefineId(), "删除失败，该流程定义已经关联了正在执行的流程");
		}
	}

	@ApiOperation(value = "修改流程定义状态", notes = "activiti系统接口")
	@RequestMapping(value = "/modeler-provider/process/define/state", method = RequestMethod.POST)
	public Result updateDeploymentState(@RequestBody WorkflowVo workflowVo) {
		if (workflowVo.getState().equals("1")) {
			repositoryService.suspendProcessDefinitionById(workflowVo.getProcessDefineId(), true, null);
			return new Result(true, workflowVo.getProcessDefineId(), "操作成功，已经挂起");
		} else if (workflowVo.getState().equals("2")) {
			repositoryService.activateProcessDefinitionById(workflowVo.getProcessDefineId(), true, null);
			return new Result(true, workflowVo.getProcessDefineId(), "操作成功，已经激活");
		} else {
			return new Result(false, workflowVo.getProcessDefineId(), "操作失败");
		}
	}

	@ApiOperation(value = "图形化显示业务流程", notes = "activiti系统接口")
	@RequestMapping(value = "/modeler-provider/process/show", method = RequestMethod.POST)
	public byte[] showProcessResource(@RequestBody WorkflowVo workflowVo) throws IOException {
		ProcessDefinition processDefinition = repositoryService.getProcessDefinition(workflowVo.getProcessDefineId());
		String resourceName = processDefinition.getDiagramResourceName();

		InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100]; // buff用于存放循环读取的临时数据
		int rc = 0;
		while ((rc = resourceAsStream.read(buff, 0, 100))>0) {
			swapStream.write(buff, 0, rc);
		}
		byte[] in_b = swapStream.toByteArray(); // in_b为转换之后的结果
		return in_b;
	}

}
