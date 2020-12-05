package vip.hyzt.system.mapper;

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
}
