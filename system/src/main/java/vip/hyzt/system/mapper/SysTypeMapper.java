package vip.hyzt.system.mapper;

import org.apache.ibatis.annotations.Param;
import vip.hyzt.common.core.domain.entity.SysType;
import java.util.List;

/**
 * 博客类型管理 数据层
 *
 * @author hyzt
 * @date 2020-12-03
 */
public interface SysTypeMapper
{
    /**
     * 查询博客类型数据
     *
     * @param type 博客类型
     * @return 博客类型集合
     */
    public List<SysType> selectTypeList(SysType type);

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
    public List<SysType> selectChildrenTypeById(Long typeId);

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
    public int hasChildByTypeId(Long typeId);

    /**
     * 查询类型是否存在博客
     *
     * @param typeId 类型ID
     * @return 结果
     */
    public int checkTypeExistBlog(Long typeId);

    /**
     * 校验类型名称是否唯一
     *
     * @param typeName 类型名称
     * @param typeId 类型ID
     * @return 结果
     */
    public SysType checkTypeNameUnique(@Param("typeName") String typeName, @Param("parentId") Long typeId);

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
     * 修改所在类型的父级类型状态
     *
     * @param type 类型
     */
    public void updateTypeStatus(SysType type);

    /**
     * 修改子元素关系
     *
     * @param types 子元素
     * @return 结果
     */
    public int updateTypeChildren(@Param("types") List<SysType> types);

    /**
     * 删除类型信息
     *
     * @param typeId 类型ID
     * @return 结果
     */
    public int deleteTypeById(Long typeId);
}
