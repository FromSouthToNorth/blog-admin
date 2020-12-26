package vip.hyzt.system.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.hyzt.common.annotation.DataScope;
import vip.hyzt.common.constant.UserConstants;
import vip.hyzt.common.core.domain.entity.SysBlog;
import vip.hyzt.common.utils.StringUtils;
import vip.hyzt.common.utils.commonmark.MarkdownUtils;
import vip.hyzt.system.domain.SysBlogTag;
import vip.hyzt.system.mapper.SysBlogMapper;
import vip.hyzt.system.mapper.SysBlogTagMapper;
import vip.hyzt.system.service.ISysBlogService;
import java.util.ArrayList;
import java.util.List;

/**
 * 博客 服务实现层
 *
 * @author hyzt
 * @date 2020-12-06
 */
@Service
public class SysBlogServiceImpl implements ISysBlogService
{
    @Autowired
    private SysBlogMapper blogMapper;

    @Autowired
    private SysBlogTagMapper blogTagMapper;

    /**
     * 根据条件分页查询博客列表
     *
     * @param blog 博客信息
     * @return 博客信息集合信息
     */
    @Override
    @DataScope(userAlias = "u")
    public List<SysBlog> selectBlogList(SysBlog blog)
    {
        return blogMapper.selectBlogList(blog);
    }

    /**
     * 新增保存博客信息
     *
     * @param blog 博客信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertBlog(SysBlog blog)
    {
        // 新增博客信息
        int rows = blogMapper.insertBlog(blog);
        // 新增博客与标签管理
        insertBlogTag(blog);
        return rows;
    }

    /**
     * 通过博客ID查询博客
     *
     * @param blogId 博客ID
     * @return 博客对象信息
     */
    @Override
    public SysBlog selectBlogById(Long blogId)
    {
        return blogMapper.selectBlogById(blogId);
    }

    /**
     * 校验博客标题是否唯一
     *
     * @param blog 博客信息
     * @return 结果
     */
    @Override
    public String checkBlogTitleUnique(SysBlog blog)
    {
        Long blogId = StringUtils.isNull(blog.getBlogId()) ? -1L : blog.getBlogId();
        SysBlog info = blogMapper.checkBlogTitleUnique(blog.getBlogTitle());
        if (StringUtils.isNotNull(info)
                && info.getBlogId().longValue() != blogId.longValue()
                && info.getUserId().longValue() == blog.getUserId().longValue() )
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 修改博客信息
     *
     * @param blog 博客信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateBlog(SysBlog blog)
    {
        Long blogId = blog.getBlogId();
        // 删除博客与标签关联
        blogTagMapper.deleteBlogTagByBlogId(blogId);
        // 新增博客与标签管理
        insertBlogTag(blog);
        return blogMapper.updateBlog(blog);
    }

    /**
     * 修改博客发布状态
     *
     * @param blog 博客信息
     * @return 结果
     */
    @Override
    public int updateUserPublishedStatus(SysBlog blog)
    {
        return blogMapper.updateBlog(blog);
    }

    /**
     * 通过博客ID删除博客
     *
     * @param blogId 博客ID
     * @return 结果
     */
    @Override
    public int deleteBlogById(Long blogId)
    {
        return blogMapper.deleteBlogById(blogId);
    }

    /**
     * 批量删除博客信息
     *
     * @param blogIds 需要删除的博客ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteBlogByIds(Long[] blogIds)
    {
        // 删除博客与标签关联
        blogTagMapper.deleteBlogTag(blogIds);
        return blogMapper.deleteBlogByIds(blogIds);
    }

    /**
     * 根据博客ID查询博客内容
     *
     * @param blogId 博客ID
     * @return 博客内容
     */
    @Override
    public String selectBlogContentById(Long blogId)
    {
        String content = blogMapper.selectBlogContentById(blogId);
        return MarkdownUtils.markdownToHtmlExtensions(content);
    }

    /**
     * 新增博客标签信息
     *
     * @param blog 博客信息
     */
    public void insertBlogTag(SysBlog blog)
    {
        Long[] tagIds = blog.getTagIds();
        if (StringUtils.isNotNull(tagIds))
        {
            // 新建博客与标签管理
            List<SysBlogTag> list = new ArrayList<>();
            for (Long tagId : tagIds)
            {
                SysBlogTag bt = new SysBlogTag();
                bt.setBlogId(blog.getBlogId());
                bt.setTagId(tagId);
                list.add(bt);
            }
            if (list.size() > 0)
            {
                blogTagMapper.batchBlogTag(list);
            }
        }
    }
}
