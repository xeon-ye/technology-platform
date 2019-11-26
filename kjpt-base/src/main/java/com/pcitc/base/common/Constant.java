package com.pcitc.base.common;

import java.util.List;

public class Constant {
    public static String HOME_URL = "http://localhost:8080/";
    public static String USER_JSESSIONID_RANGE = "localhost";

    public static String ECHARTS_TYPE_LINE = "line";
    public static String ECHARTS_TYPE_BAR = "bar";
    public static String ECHARTS_TYPE_PIE = "pie";


    public static String AUDIT_STATUS_DRAFT = "0";//0未审批(草稿), 1审批中(已提交)  2审批通过   3审批不通过
    public static String AUDIT_STATUS_SUBMIT = "1";
    public static String AUDIT_STATUS_PASS = "2";
    public static String AUDIT_STATUS_REJECT = "3";
    
    
    public static String DEL_STATUS_NOT = "0";//删除状态（0未删除，1删除）
    public static String DEL_STATUS_ED = "1";
    
    public static String SOURCE_TYPE_LOCATION = "1";//数据来源（1本系统，2外系统）
    public static String SOURCE_TYPE_OUTER = "2";
    
    
    public static String TECHFAMILY_ROOT_ID  = "10";//技术簇根节点ID,用于递归查询
    
    public static String UNIT_ROOT_ID  = "46b7e45756ef4db88b6acb711f916e43";//组织机构根节点ID,用于递归查询
    
    
    
    public static String LOG_TYPE_LOGIN  = "1";//日志类型：1登陆日志，2操作日志，3错误日志
    public static String LOG_TYPE_OPT  = "2";
    public static String LOG_TYPE_ERROR  = "3";
    
    
    
    public static String LOG_SYSTEMADMIN    = "systemadmin";
    public static String LOG_SECURITYADMIN  = "securityadmin";
    public static String LOG_AUDITADMIN     = "auditadmin";
      
    
    
    
    

    /**
     * 默认文件上传类型
     */
    public static String default_file_type = "png|jpg|jpeg|gif|txt|doc|docx|xls|xlsx|ppt|pptx|pdf|wps|rar|zip|gz|rm|rmvb|mp3|wma|mid|html";

    //生成编码
    public static final String TABLE_ENCODE_CREATE = "http://kjpt-zuul/system-proxy/SystemCodeTool/generate/create";

    public static final String UPDATE_FILE_FLAG = "http://kjpt-zuul/system-proxy/sysfile-provider/sysfile/update-file-flag/";

    public static final String DELETE_FILE_FORM = "http://kjpt-zuul/system-proxy/sysfile-provider/sysfile/delete-form-file/";

}