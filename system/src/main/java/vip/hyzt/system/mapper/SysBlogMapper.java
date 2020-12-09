package vip.hyzt.system.mapper;

import vip.hyzt.common.core.domain.entity.SysBlog;
import java.util.List;

/**
 * 博客 数据层
 *
 * @author hyzt
 * @date 2020-12-06
 */
public interface SysBlogMapper
{
    /**
     * 根据条件分页查询博客数据
     *
     * @param blog 博客信息
     * @return 博客数据集合
     */
    public List<SysBlog> selectBlogList(SysBlog blog);

    /**
     * 根据博客ID查询博客信息
     *
     * @param blogId 博客ID
     * @return 博客信息
     */
    public SysBlog selectBlogById(Long blogId);

    /**
     * 新增博客信息
     *
     * @param blog 博客信息
     * @return 结果
     */
    public int insertBlog(SysBlog blog);

    /**
     * 修改博客信息
     *
     * @param blog 博客信息
     * @return 结果
     */
    public int updateBlog(SysBlog blog);

    /**
     * 根据博客ID删除博客信息
     *
     * @param blogId 博客ID
     * @return 结果
     */
    public int deleteBlogById(Long blogId);

    /**
     * 根据博客ID批量删除博客
     *
     * @param blogIds 需要删除的博客ID
     * @return 结果
     */
    public int deleteBlogByIds(Long[] blogIds);

    /**
     * 校验博客标题是否唯一
     *
     * @param blogTitle 博客标题
     * @return 结果
     */
    public SysBlog checkBlogTitleUnique(String blogTitle);

    /**
     * 根据博客ID查询博客内容
     *
     * @param blogId 博客ID
     * @return 博客内容
     */
    public String selectBlogContentById(Long blogId);
}
