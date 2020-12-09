package vip.hyzt.web.controller.system;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.hyzt.common.annotation.Log;
import vip.hyzt.common.constant.UserConstants;
import vip.hyzt.common.core.controller.BaseController;
import vip.hyzt.common.core.domain.AjaxResult;
import vip.hyzt.common.core.domain.entity.SysBlog;
import vip.hyzt.common.core.page.TableDataInfo;
import vip.hyzt.common.enums.BusinessType;
import vip.hyzt.common.utils.SecurityUtils;
import vip.hyzt.common.utils.StringUtils;
import vip.hyzt.system.service.ISysBlogService;
import vip.hyzt.system.service.ISysTagService;
import java.util.List;

/**
 * 博客信息
 *
 * @author hyzt
 * @date 2020-12-06
 */
@RestController
@RequestMapping("/system/blog")
public class SysBlogController extends BaseController
{
    @Autowired
    private ISysBlogService blogService;

    @Autowired
    private ISysTagService tagService;

    /**
     * 获取博客列表
     */
    @PreAuthorize("@ss.hasPermi('system:blog:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysBlog blog)
    {
        startPage();
        List<SysBlog> list = blogService.selectBlogList(blog);
        return getDataTable(list);
    }

    /**
     * 新增博客信息
     */
    @PreAuthorize("@ss.hasPermi('system:blog:add')")
    @Log(title = "博客管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysBlog blog)
    {
        blog.setUserId(SecurityUtils.getUserId());
        if (blogService.checkBlogTitleUnique(blog).equals(UserConstants.NOT_UNIQUE))
        {
            return AjaxResult.error("博客标题已存在 ‘" + blog.getBlogTitle() + "’ 请重新输入，新的标题");
        }
        blog.setCreateBy(SecurityUtils.getUsername());
        return toAjax(blogService.insertBlog(blog));
    }

    /**
     * 博客编号查询博客信息
     */
    @PreAuthorize("@ss.hasPermi('system:blog:query')")
    @GetMapping(value = { "/", "/{blogId}" })
    public AjaxResult getInfo(@PathVariable(value = "blogId", required = false) Long blogId)
    {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("tags", tagService.selectTagAll());
        if (StringUtils.isNotNull(blogId))
        {
            ajax.put(AjaxResult.DATA_TAG, blogService.selectBlogById(blogId));
            ajax.put("tagIds", tagService.selectTagListByBlogId(blogId));
        }
        return ajax;
    }

    /**
     * 修改博客
     */
    @PreAuthorize("@ss.hasPermi('system:blog:edit')")
    @Log(title = "博客管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysBlog blog)
    {
        if (blogService.checkBlogTitleUnique(blog).equals(UserConstants.NOT_UNIQUE))
        {
            return AjaxResult.error("博客标题已存在 ’" + blog.getBlogTitle() + "’ 请重新输入，新的标题");
        }
        blog.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(blogService.updateBlog(blog));
    }

    /**
     * 删除博客
     */
    @PreAuthorize("@ss.hasPermi('system:blog:remove')")
    @Log(title = "博客管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{blogIds}")
    public AjaxResult remove(@PathVariable Long[] blogIds) {
        return toAjax(blogService.deleteBlogByIds(blogIds));
    }

    /**
     * 修改博客发布
     */
    @PreAuthorize("@ss.hasPermi('system:blog:edit')")
    @Log(title = "博客管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changePublished")
    public AjaxResult changePublished(@RequestBody SysBlog blog)
    {
        blog.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(blogService.updateUserPublishedStatus(blog));
    }

    /**
     * 查询博客内容
     */
    @PreAuthorize("@ss.hasPermi('system:blog:query')")
    @GetMapping("/getcontent/{blogId}")
    public AjaxResult getContent(@PathVariable(value = "blogId") Long blogId)
    {
        String content = blogService.selectBlogContentById(blogId);
        return AjaxResult.success(AjaxResult.DATA_TAG, content);
    }
}
