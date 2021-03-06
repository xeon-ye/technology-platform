package com.pcitc.mapper.system;

import com.pcitc.base.system.SysMailRecord;
import com.pcitc.base.system.SysMailRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysMailRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_mail_record
     *
     * @mbg.generated Mon Jun 04 10:14:04 CST 2018
     */
    long countByExample(SysMailRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_mail_record
     *
     * @mbg.generated Mon Jun 04 10:14:04 CST 2018
     */
    int deleteByExample(SysMailRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_mail_record
     *
     * @mbg.generated Mon Jun 04 10:14:04 CST 2018
     */
    int deleteByPrimaryKey(String mailId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_mail_record
     *
     * @mbg.generated Mon Jun 04 10:14:04 CST 2018
     */
    int insert(SysMailRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_mail_record
     *
     * @mbg.generated Mon Jun 04 10:14:04 CST 2018
     */
    int insertSelective(SysMailRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_mail_record
     *
     * @mbg.generated Mon Jun 04 10:14:04 CST 2018
     */
    List<SysMailRecord> selectByExample(SysMailRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_mail_record
     *
     * @mbg.generated Mon Jun 04 10:14:04 CST 2018
     */
    SysMailRecord selectByPrimaryKey(String mailId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_mail_record
     *
     * @mbg.generated Mon Jun 04 10:14:04 CST 2018
     */
    int updateByExampleSelective(@Param("record") SysMailRecord record, @Param("example") SysMailRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_mail_record
     *
     * @mbg.generated Mon Jun 04 10:14:04 CST 2018
     */
    int updateByExample(@Param("record") SysMailRecord record, @Param("example") SysMailRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_mail_record
     *
     * @mbg.generated Mon Jun 04 10:14:04 CST 2018
     */
    int updateByPrimaryKeySelective(SysMailRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_mail_record
     *
     * @mbg.generated Mon Jun 04 10:14:04 CST 2018
     */
    int updateByPrimaryKey(SysMailRecord record);
}