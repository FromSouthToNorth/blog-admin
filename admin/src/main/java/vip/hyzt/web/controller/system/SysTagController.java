package vip.hyzt.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.hyzt.common.annotation.Log;
import vip.hyzt.common.constant.UserConstants;
import vip.hyzt.common.core.controller.BaseController;
import vip.hyzt.common.core.domain.AjaxResult;
import vip.hyzt.common.core.domain.entity.SysTag;
import vip.hyzt.common.core.page.TableDataInfo;
import vip.hyzt.common.enums.BusinessType;
import vip.hyzt.common.utils.SecurityUtils;
import vip.hyzt.common.utils.poi.ExcelUtil;
import vip.hyzt.system.service.ISysTagService;
import java.util.List;

/**
 * 博客标签信息
 *
 * @author hyzt
 * @date 2020-12-05
 */
@RestController
@RequestMapping("/system/tag")
public class SysTagController extends BaseController
{
    @Autowired
    private ISysTagService tagService;

    /**
     * 博客标签条件分页查询
     */
    @PreAuthorize("@ss.hasPermi('system:tag:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysTag tag)
    {
        startPage();
        List<SysTag> list = tagService.selectTagList(tag);
        return getDataTable(list);
    }

    @Log(title = "博客标签管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:tag:list')")
    @GetMapping("/export")
    public AjaxResult export(SysTag tag)
    {
        List<SysTag> list = tagService.selectTagList(tag);
        ExcelUtil<SysTag> util = new ExcelUtil<>(SysTag.class);
        return util.exportExcel(list, "博客标签数据");
    }

    /**
     * 根据标签编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:tag:query')")
    @GetMapping(value = "/{tagId}")
    public AjaxResult getInfo(@PathVariable Long tagId)
    {
        return AjaxResult.success(tagService.selectTagById(tagId));
    }

    /**
     * 新增博客标签
     */
    @PreAuthorize("@ss.hasPermi('system:tag:add')")
    @Log(title = "博客标签管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysTag tag)
    {
        if (UserConstants.NOT_UNIQUE.equals(tagService.checkTagNameUnique(tag)))
        {
            return AjaxResult.error("新增博客标签'" + tag.getTagName() + "'失败，标签名称已存在");
        }
        tag.setCreateBy(SecurityUtils.getUsername());
        return toAjax(tagService.insertTag(tag));
    }

    /**
     * 修改保存博客标签
     */
    @PreAuthorize("@ss.hasPermi('system:tag:edit')")
    @Log(title = "博客标签管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysTag tag)
    {
        if (UserConstants.NOT_UNIQUE.equals(tagService.checkTagNameUnique(tag)))
        {
            return AjaxResult.error("修改博客标签'" + tag.getTagName() + "'失败，标签名称已存在");
        }
        tag.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(tagService.updateTag(tag));
    }

    /**
     * 删除博客标签
     */
    @PreAuthorize("@ss.hasPermi('system:tag:remove')")
    @Log(title = "博客标签管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{tagIds}")
    public AjaxResult remove(@PathVariable Long[] tagIds)
    {
        return toAjax(tagService.deleteTagByIds(tagIds));
    }
}
