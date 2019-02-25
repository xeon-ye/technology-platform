package com.pcitc.mapper.equipment;

import com.pcitc.base.stp.equipment.SreProject;
import com.pcitc.base.stp.equipment.SreProjectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SreProjectMapper {
    int countByExample(SreProjectExample example);

    int deleteByExample(SreProjectExample example);

    int deleteByPrimaryKey(String projectId);

    int insert(SreProject record);

    int insertSelective(SreProject record);

    List<SreProject> selectByExample(SreProjectExample example);

    SreProject selectByPrimaryKey(String projectId);

    int updateByExampleSelective(@Param("record") SreProject record, @Param("example") SreProjectExample example);

    int updateByExample(@Param("record") SreProject record, @Param("example") SreProjectExample example);

    int updateByPrimaryKeySelective(SreProject record);

    int updateByPrimaryKey(SreProject record);
    
    

    List<SreProject> getListRelationList(SreProject record);
    int getListRelationCount(SreProject record);
    
    
}