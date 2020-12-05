package vip.hyzt.web.controller.system;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.hyzt.common.annotation.Log;
import vip.hyzt.common.constant.UserConstants;
import vip.hyzt.common.core.controller.BaseController;
import vip.hyzt.common.core.domain.AjaxResult;
import vip.hyzt.common.core.domain.entity.SysType;
import vip.hyzt.common.enums.BusinessType;
import vip.hyzt.common.utils.SecurityUtils;
import vip.hyzt.system.service.ISysTypeService;
import java.util.Iterator;
import java.util.List;

/**
 * 博客类型信息
 *
 * @author hyzt
 * @date 2020-12-05
 */
@RestController
@RequestMapping("/system/type")
public class SysTypeController extends BaseController
{
    @Autowired
    private ISysTypeService typeService;

    /**
     * 获取类型列表
     */
    @PreAuthorize("@ss.hasPermi('system:type:list')")
    @GetMapping("/list")
    public AjaxResult list(SysType type)
    {
        List<SysType> types = typeService.selectTypeList(type);
        return AjaxResult.success(types);
    }

    /**
     * 查询类型列表（排除节点）
     */
    @PreAuthorize("@ss.hasPermi('system:type:list')")
    @GetMapping("/list/exclude/{typeId}")
    public AjaxResult excludeChild(@PathVariable(value = "typeId", required = false) Long typeId)
    {
        List<SysType> types = typeService.selectTypeList(new SysType());
        Iterator<SysType> iterator = types.iterator();
        while (iterator.hasNext()) {
            SysType d = (SysType) iterator.next();
            if (d.getTypeId().intValue() == typeId
                    || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), typeId + ""))
            {
                iterator.remove();
            }
        }
        return AjaxResult.success(types);
    }

    /**
     * 根据类型编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:type:query')")
    @GetMapping(value = "/{typeId}")
    public AjaxResult getInfo(@PathVariable Long typeId)
    {
        return AjaxResult.success(typeService.selectTypeById(typeId));
    }

    /**
     * 获取类型下拉树列表
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect(SysType type)
    {
        List<SysType> types = typeService.selectTypeList(type);
        return AjaxResult.success(typeService.buildTypeTreeSelect(types));
    }

    /**
     * 新增类型
     */
    @PreAuthorize("@ss.hasPermi('system:type:add')")
    @Log(title = "博客类型管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysType type)
    {
        if (UserConstants.NOT_UNIQUE.equals(typeService.checkTypeNameUnique(type)))
        {
            return AjaxResult.error("新增类型'" + type.getTypeName() + "'失败，类型名称已存在");
        }
        type.setCreateBy(SecurityUtils.getUsername());
        return toAjax(typeService.insertType(type));
    }

    /**
     * 修改博客类型
     */
    @PreAuthorize("@ss.hasPermi('system:type:edit')")
    @Log(title = "博客类型管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysType type)
    {
        if (UserConstants.NOT_UNIQUE.equals(typeService.checkTypeNameUnique(type)))
        {
            return AjaxResult.error("修改类型'" + type.getTypeName() + "'失败，类型名称已存在");
        }
        else if (type.getParentId().equals(type.getTypeId()))
        {
            return AjaxResult.error("修改部门'" + type.getTypeName() + "'失败，上级部门不能是自己");
        }
        else if (StringUtils.equals(UserConstants.TYPE_DISABLE, type.getStatus())
            && typeService.selectNormalChildrenTypeById(type.getTypeId()) > 0)
        {
            return AjaxResult.error("该部门包含未停用的子类型！");
        }
        type.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(typeService.updateType(type));
    }

    /**
     * 删除类型
     */
    @PreAuthorize("@ss.hasPermi('system:type:remove')")
    @Log(title = "博客类型管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{typeId}")
    public AjaxResult remove(@PathVariable Long typeId)
    {
        if (typeService.hasChildByTypeId(typeId))
        {
            return AjaxResult.error("存在下级博客类型,不允许删除");
        }
        if (typeService.checkTypeExistBlog(typeId))
        {
            return AjaxResult.error("博客类型存在博客,不允许删除");
        }
        return toAjax(typeService.deleteTypeById(typeId));
    }
}
