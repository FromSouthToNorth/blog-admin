package vip.hyzt.system.service;

import vip.hyzt.common.core.domain.entity.SysBlog;

import java.util.List;

/**
 * 博客业务 服务层
 *
 * @author hyzt
 * @date 2020-12-06
 */
public interface ISysBlogService
{
    /**
     * 根据条件分页查询博客列表
     *
     * @param blog 博客信息
     * @return 博客信息集合信息
     */
    public List<SysBlog> selectBlogList(SysBlog blog);

    /**
     * 新增保存博客信息
     *
     * @param blog 博客信息
     * @return 结果
     */
    public int insertBlog(SysBlog blog);

    /**
     * 通过博客ID查询博客
     *
     * @param blogId 博客ID
     * @return 博客对象信息
     */
    public SysBlog selectBlogById(Long blogId);

    /**
     * 校验博客标题是否唯一
     *
     * @param blog 博客信息
     * @return 结果
     */
    public String checkBlogTitleUnique(SysBlog blog);

    /**
     * 修改博客信息
     *
     * @param blog 博客信息
     * @return 结果
     */
    public int updateBlog(SysBlog blog);

    /**
     * 修改博客发布状态
     *
     * @param blog 博客信息
     * @return 结果
     */
    public int updateUserPublishedStatus(SysBlog blog);

    /**
     * 通过博客ID删除博客
     *
     * @param blogId 博客ID
     * @return 结果
     */
    public int deleteBlogById(Long blogId);

    /**
     * 批量删除博客信息
     *
     * @param blogIds 需要删除的博客ID
     * @return 结果
     */
    public int deleteBlogByIds(Long[] blogIds);
}
