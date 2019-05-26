package com.pcitc.service.system;

import java.util.Date;

import com.pcitc.base.common.LayuiTableData;
import com.github.pagehelper.PageInfo;
import com.pcitc.base.common.enums.DataOperationStatusEnum;
import com.pcitc.base.common.LayuiTableParam;
import com.pcitc.base.common.TreeNode;
import com.pcitc.base.system.IndexOutPatent;
import com.pcitc.base.system.IndexOutPatentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>接口类</p>
 * <p>Table: index_out_patent - </p>
 *
 * @since 2019-05-23 07:13:54
 */
public interface IndexOutPatentService {

    /**
     * 判空-信息
     *
     * @param record
     * @return
     * @throws Exception
     */
    List<IndexOutPatent> findIndexOutPatentList(IndexOutPatent record) throws Exception;

    /**
     * 删除
     *
     * @param record
     * @return
     * @throws Exception
     */
    int updateOrInsertIndexOutPatent(IndexOutPatent record) throws Exception;

    /**
     * 根据id删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    int deleteIndexOutPatentById(String id) throws Exception;

    /**
     * 根据id查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    IndexOutPatent getIndexOutPatentInfo(String id) throws Exception;

    /**
     * 查询总条数
     *
     * @param example
     * @return
     */
    long countByExample(IndexOutPatentExample example);

    /**
     * 根据example条件删除
     *
     * @param example
     * @return
     */
    int deleteByExample(IndexOutPatentExample example);

    /**
     * 根据主键删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(String id);

    /**
     * 插入信息
     *
     * @param record
     * @return
     */
    int insert(IndexOutPatent record);

    /**
     * 插入信息,返回对象
     *
     * @param record
     * @return
     */
    IndexOutPatent insertObject(IndexOutPatent record);

    /**
     * 判空-插入信息
     *
     * @param record
     * @return
     */
    int insertSelective(IndexOutPatent record);

    /**
     * 根据example条件查询
     *
     * @param example
     * @return
     */
    List<IndexOutPatent> selectByExample(IndexOutPatentExample example);

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    IndexOutPatent selectByPrimaryKey(String id);

    /**
     * 根据example条件-判空更新
     *
     * @param record
     * @param example
     * @return
     */
    int updateByExampleSelective(@Param("record") IndexOutPatent record, @Param("example") IndexOutPatentExample example);

    /**
     * 根据example条件,更新所有
     *
     * @param record
     * @param example
     * @return
     */
    int updateByExample(@Param("record") IndexOutPatent record, @Param("example") IndexOutPatentExample example);

    /**
     * 根据主键判空-更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(IndexOutPatent record);

    /**
     * 根据主键更新所有
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(IndexOutPatent record);

    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    Integer deleteIndexOutPatent(java.io.Serializable id);

    /**
     * @param param 分页查询
     * @return
     */
    LayuiTableData findIndexOutPatentByPage(LayuiTableParam param);

    /**
     * 真删除
     *
     * @param id
     * @return
     */
    Integer deleteIndexOutPatentReal(String id);

    /**
     * 查询树
     *
     * @return
     */
    List<TreeNode> selectObjectByTree();
}