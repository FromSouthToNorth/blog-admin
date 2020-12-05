package vip.hyzt.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.hyzt.common.constant.UserConstants;
import vip.hyzt.common.core.domain.TreeSelect;
import vip.hyzt.common.exception.CustomException;
import vip.hyzt.common.utils.StringUtils;
import vip.hyzt.common.core.domain.entity.SysType;
import vip.hyzt.system.mapper.SysTypeMapper;
import vip.hyzt.system.service.ISysTypeService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 博客类型 服务实现层
 *
 * @author hyzt
 * @date 2020-12-03
 */
@Service
public class SysTypeServiceImpl implements ISysTypeService
{
    @Autowired
    private SysTypeMapper typeMapper;

    /**
     * 查询博客类型数据
     *
     * @param type 博客类型
     * @return 博客类型集合
     */
    @Override
    public List<SysType> selectTypeList(SysType type)
    {
        return typeMapper.selectTypeList(type);
    }

    /**
     * 构建前端所需要树结构
     *
     * @param types 类型列表
     * @return 树结构列表
     */
    @Override
    public List<SysType> buildTypeTree(List<SysType> types)
    {
        List<SysType> returnList = new ArrayList<>();
        List<Long> tempList = new ArrayList<>();
        for (SysType type : types)
        {
            tempList.add(type.getTypeId());
        }
        for (Iterator<SysType> iterator = types.iterator(); iterator.hasNext();)
        {
            SysType type = (SysType) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(type.getParentId()))
            {
                recursionFn(types, type);
                returnList.add(type);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = types;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param types 类型列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildTypeTreeSelect(List<SysType> types)
    {
        List<SysType> typeTrees = buildTypeTree(types);
        return typeTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 根据类型ID查询信息
     *
     * @param typeId 类型ID
     * @return 类型信息
     */
    @Override
    public SysType selectTypeById(Long typeId)
    {
        return typeMapper.selectTypeById(typeId);
    }

    /**
     * 根据ID查询所子类型
     *
     * @param typeId 类型ID
     * @return 类型列表
     */
    @Override
    public List<SysType> selectChildrenDeptById(Long typeId)
    {
        return null;
    }

    /**
     * 根据ID查询所有子类型（正常状态）
     *
     * @param typeId 类型ID
     * @return 子类型数
     */
    @Override
    public int selectNormalChildrenTypeById(Long typeId)
    {
        return typeMapper.selectNormalChildrenTypeById(typeId);
    }

    /**
     * 是否有子节点
     *
     * @param typeId 类型ID
     * @return 结果
     */
    @Override
    public boolean hasChildByTypeId(Long typeId)
    {
        int result = typeMapper.hasChildByTypeId(typeId);
        return result > 0 ? true : false;
    }

    /**
     * 查询类型是否存在博客
     *
     * @param typeId 类型ID
     * @return 结果
     */
    @Override
    public boolean checkTypeExistBlog(Long typeId)
    {
        int result = typeMapper.checkTypeExistBlog(typeId);
        return result > 0 ? true : false;
    }

    /**
     * 校验类型名称是否唯一
     *
     * @param type 类型信息
     * @return 结果
     */
    @Override
    public String checkTypeNameUnique(SysType type)
    {
        Long typeId = StringUtils.isNull(type.getTypeId()) ? -1L : type.getTypeId();
        type.setParentId(StringUtils.isNull(type.getParentId()) ? Long.valueOf(UserConstants.PARENT) : type.getParentId());
        SysType info = typeMapper.checkTypeNameUnique(type.getTypeName(), type.getParentId());
        if (StringUtils.isNotNull(info) && info.getTypeId().longValue() != typeId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增类型信息
     *
     * @param type 类型信息
     * @return 结果
     */
    @Override
    public int insertType(SysType type)
    {
        if (StringUtils.isNotNull(type.getParentId()))
        {
            SysType info = typeMapper.selectTypeById(type.getParentId());
            // 如果父节点不为正常状态,则不允许新增子节点
            if (!UserConstants.TYPE_NORMAL.equals(type.getStatus()))
            {
                throw new CustomException("类型停用，不允许新增");
            }
            type.setAncestors(info.getAncestors() + "," + type.getParentId());
        }
        else
        {
            type.setAncestors(UserConstants.PARENT);
            type.setParentId(Long.valueOf(UserConstants.PARENT));
        }

        return typeMapper.insertType(type);
    }

    /**
     * 修改类型信息
     *
     * @param type 类型信息
     * @return 结果
     */
    @Override
    public int updateType(SysType type)
    {
        SysType newParentType = typeMapper.selectTypeById(type.getParentId());
        SysType oldType = typeMapper.selectTypeById(type.getTypeId());
        if (StringUtils.isNotNull(newParentType) && StringUtils.isNotNull(oldType))
        {
            String newAncestors = newParentType.getAncestors() + "," + newParentType.getTypeId();
            String oldAncestors = oldType.getAncestors();
            type.setAncestors(newAncestors);
            updateTypeChildren(type.getTypeId(), newAncestors, oldAncestors);
        }
        int result = typeMapper.updateType(type);
        if (UserConstants.TYPE_NORMAL.equals(type.getStatus()))
        {
            // 如果该类型是启用状态，则启用该类型的所有上级类型
            updateParentTypeStatus(type);
        }
        return result;
    }

    /**
     * 修改该类型的父级类型状态
     *
     * @param type 当前类型
     */
    private void updateParentTypeStatus(SysType type)
    {
        String updateBy = type.getUpdateBy();
        type = typeMapper.selectTypeById(type.getTypeId());
        type.setUpdateBy(updateBy);
        typeMapper.updateTypeStatus(type);
    }

    /**
     * 修改子元素关系
     *
     * @param typeId 被修改的类型ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateTypeChildren(Long typeId, String newAncestors, String oldAncestors)
    {
        List<SysType> children = typeMapper.selectChildrenTypeById(typeId);
        for (SysType child : children)
        {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }
        if (children.size() > 0)
        {
            typeMapper.updateTypeChildren(children);
        }
    }

    /**
     * 删除类型信息
     *
     * @param typeId 类型ID
     * @return 结果
     */
    @Override
    public int deleteTypeById(Long typeId)
    {
        return typeMapper.deleteTypeById(typeId);
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysType> list, SysType t)
    {
        // 得到子节点列表
        List<SysType> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysType tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysType> getChildList(List<SysType> list, SysType t)
    {
        List<SysType> tList = new ArrayList<>();
        Iterator<SysType> it = list.iterator();
        while (it.hasNext())
        {
            SysType n = (SysType) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getTypeId().longValue())
            {
                tList.add(n);
            }
        }
        return tList;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysType> list, SysType t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }
}
