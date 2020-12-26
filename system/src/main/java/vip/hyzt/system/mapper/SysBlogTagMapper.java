package vip.hyzt.system.mapper;

import vip.hyzt.system.domain.SysBlogTag;
import java.util.List;

/**
 * 博客标签关联表 数据层
 *
 * @author hyzt
 * @date 2020-12-05
 */
public interface SysBlogTagMapper
{
    /**
     * 通过标签ID查询博客使用数量
     *
     * @param tagId 标签ID
     * @return 结果
     */
    public int countBlogTagByTagId(Long tagId);

    /**
     * 通过博客ID删除博客和标签关联
     *
     * @param blogId 博客ID
     * @return 结果
     */
    public int deleteBlogTagByBlogId(Long blogId);

    /**
     * 批量删除博客和标签关联
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBlogTag(Long[] ids);

    /**
     * 批量新增博客标签信息
     *
     * @param blogTagsList 博客标签集合
     * @return 结果
     */
    public int batchBlogTag(List<SysBlogTag> blogTagsList);

    /**
     * 删除博客和标签关联信息
     *
     * @param blogTag 博客和标签关联信息
     * @return 结果
     */
    public int deleteBlogTagInfo(SysBlogTag blogTag);
}
