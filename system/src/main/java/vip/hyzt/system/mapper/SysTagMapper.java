package vip.hyzt.system.mapper;

import vip.hyzt.common.core.domain.entity.SysTag;
import java.util.List;

/**
 * 博客标签 数据层
 *
 * @author hyzt
 * @date 2020-12-05
 */
public interface SysTagMapper
{
    /**
     * 根据条件分页查询博客标签数据
     *
     * @param tag 标签信息
     * @return 标签数据集合
     */
    public List<SysTag> selectTagList(SysTag tag);

    /**
     * 查询所有标签
     *
     * @return 标签列表
     */
    public List<SysTag> selectTagAll();

    /**
     * 通过标签ID查询标签
     *
     * @param tagId 标签ID
     * @return 标签对象信息
     */
    public SysTag selectTagById(Long tagId);

    /**
     * 根据博客ID获取标签选择框列表
     *
     * @param blogId 博客ID
     * @return 选中的标签ID列表
     */
    public List<Integer> selectTagListByBlogId(Long blogId);

    /**
     * 校验标签名称是否唯一
     *
     * @param tagName 标签名称
     * @return 标签信息
     */
    public SysTag checkTagNameUnique(String tagName);

    /**
     * 修改标签信息
     *
     * @param tag 标签信息
     * @return 结果
     */
    public int updateTag(SysTag tag);

    /**
     * 新增标签信息
     *
     * @param tag 标签信息
     * @return 结果
     */
    public int insertTag(SysTag tag);

    /**
     * 通过标签ID删除标签
     *
     * @param tagId 标签ID
     * @return 结果
     */
    public int deleteTagById(Long tagId);

    /**
     * 批量删除标签信息
     *
     * @param tagIds 需要删除的标签ID
     * @return 结果
     */
    public int deleteTagByIds(Long[] tagIds);
}
