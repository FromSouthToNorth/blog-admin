package vip.hyzt.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.hyzt.common.constant.UserConstants;
import vip.hyzt.common.core.domain.entity.SysTag;
import vip.hyzt.common.exception.CustomException;
import vip.hyzt.common.utils.StringUtils;
import vip.hyzt.common.utils.spring.SpringUtils;
import vip.hyzt.system.mapper.SysBlogTagMapper;
import vip.hyzt.system.mapper.SysTagMapper;
import vip.hyzt.system.service.ISysTagService;
import java.util.List;

/**
 * 博客标签 服务处理层
 *
 * @author hyzt
 * @date 2020-12-05
 */
@Service
public class SysTagServiceImpl implements ISysTagService
{
    @Autowired
    private SysTagMapper tagMapper;

    @Autowired
    private SysBlogTagMapper blogTagMapper;

    /**
     * 根据条件分页查询博客标签数据
     *
     * @param tag 标签信息
     * @return 标签数据集合列表
     */
    @Override
    public List<SysTag> selectTagList(SysTag tag)
    {
        return tagMapper.selectTagList(tag);
    }

    /**
     * 查询所有标签
     *
     * @return 标签列表
     */
    @Override
    public List<SysTag> selectTagAll()
    {
        return SpringUtils.getAopProxy(this).selectTagList(new SysTag());
    }

    /**
     * 根据标签ID查询标签
     *
     * @param tagId 标签ID
     * @return 结果
     */
    @Override
    public SysTag selectTagById(Long tagId)
    {
        return tagMapper.selectTagById(tagId);
    }

    /**
     * 校验标签名称是否唯一
     *
     * @param tag 标签信息
     * @return 结果
     */
    @Override
    public String checkTagNameUnique(SysTag tag)
    {
        Long tagId = StringUtils.isNull(tag.getTagId()) ? -1L : tag.getTagId();
        SysTag info = tagMapper.checkTagNameUnique(tag.getTagName());
        if (StringUtils.isNotNull(info) && info.getTagId().longValue() != tagId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 通过标签ID查询标签使用数量
     *
     * @param tagId 标签ID
     * @return 结果
     */
    @Override
    public int countBlogTagByTagId(Long tagId)
    {
        return blogTagMapper.countBlogTagByTagId(tagId);
    }

    /**
     * 新增保存标签信息
     *
     * @param tag 标签信息
     * @return 结果
     */
    @Override
    public int insertTag(SysTag tag)
    {
        int result = tagMapper.insertTag(tag);
        return result;
    }

    /**
     * 修改保存标签信息
     *
     * @param tag 标签信息
     * @return 结果
     */
    @Override
    public int updateTag(SysTag tag)
    {
        int result = tagMapper.updateTag(tag);
        return result;
    }

    /**
     * 通过标签ID删除标签
     *
     * @param tagId 标签ID
     * @return 结果
     */
    @Override
    public int deleteTagById(Long tagId)
    {
        return tagMapper.deleteTagById(tagId);
    }

    /**
     * 批量删除标签信息
     *
     * @param tagIds 需要删除的标签ID
     * @return 结果
     */
    @Override
    public int deleteTagByIds(Long[] tagIds)
    {
        for (Long tagId : tagIds)
        {
            SysTag tag = selectTagById(tagId);
            if (countBlogTagByTagId(tagId) > 0)
            {
                throw new CustomException(String.format("%1$s已分配,不能删除", tag.getTagName()));
            }
        }
        return tagMapper.deleteTagByIds(tagIds);
    }
}
