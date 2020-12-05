package vip.hyzt.system.service;

import vip.hyzt.common.core.domain.TreeSelect;
import vip.hyzt.common.core.domain.entity.SysType;
import java.util.List;

/**
 * 博客类型 服务层
 *
 * @author hyzt
 * @date 2020-12-03
 */
public interface ISysTypeService
{
    /**
     * 查询博客类型数据
     *
     * @param type 博客类型
     * @return 博客类型集合
     */
    public List<SysType> selectTypeList(SysType type);

    /**
     * 构建前端所需要树结构
     *
     * @param types 类型列表
     * @return 树结构列表
     */
    public List<SysType> buildTypeTree(List<SysType> types);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param types 类型列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildTypeTreeSelect(List<SysType> types);

    /**
     * 根据类型ID查询信息
     *
     * @param typeId 类型ID
     * @return 类型信息
     */
    public SysType selectTypeById(Long typeId);

    /**
     * 根据ID查询所子类型
     *
     * @param typeId 类型ID
     * @return 类型列表
     */
    public List<SysType> selectChildrenDeptById(Long typeId);

    /**
     * 根据ID查询所有子类型（正常状态）
     *
     * @param typeId 类型ID
     * @return 子类型数
     */
    public int selectNormalChildrenTypeById(Long typeId);

    /**
     * 是否有子节点
     *
     * @param typeId 类型ID
     * @return 结果
     */
    public boolean hasChildByTypeId(Long typeId);

    /**
     * 查询类型是否存在博客
     *
     * @param typeId 类型ID
     * @return 结果
     */
    public boolean checkTypeExistBlog(Long typeId);

    /**
     * 校验类型名称是否唯一
     *
     * @param type 类型信息
     * @return 结果
     */
    public String checkTypeNameUnique(SysType type);

    /**
     * 新增类型信息
     *
     * @param type 类型信息
     * @return 结果
     */
    public int insertType(SysType type);

    /**
     * 修改类型信息
     *
     * @param type 类型信息
     * @return 结果
     */
    public int updateType(SysType type);

    /**
     * 删除类型信息
     *
     * @param typeId 类型ID
     * @return 结果
     */
    public int deleteTypeById(Long typeId);
}
