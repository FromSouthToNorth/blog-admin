package vip.hyzt.system.service;

import vip.hyzt.common.core.domain.entity.SysTag;
import java.util.List;

/**
 * 博客标签 服务层
 *
 * @author hyzt
 * @date 2020-12-05
 */
public interface ISysTagService
{
    /**
     * 根据条件分页查询博客标签数据
     *
     * @param tag 标签信息
     * @return 标签数据集合列表
     */
    public List<SysTag> selectTagList(SysTag tag);

    /**
     * 查询所有标签
     *
     * @return 标签列表
     */
    public List<SysTag> selectTagAll();

    /**
     * 根据标签ID查询标签
     *
     * @param tagId 标签ID
     * @return 结果
     */
    public SysTag selectTagById(Long tagId);

    /**
     * 校验标签名称是否唯一
     *
     * @param tag 标签信息
     * @return 结果
     */
    public String checkTagNameUnique(SysTag tag);

    /**
     * 通过标签ID查询标签使用数量
     *
     * @param tagId 标签ID
     * @return 结果
     */
    public int countBlogTagByTagId(Long tagId);

    /**
     * 新增保存标签信息
     *
     * @param tag 标签信息
     * @return 结果
     */
    public int insertTag(SysTag tag);

    /**
     * 修改保存标签信息
     *
     * @param tag 标签信息
     * @return 结果
     */
    public int updateTag(SysTag tag);

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
