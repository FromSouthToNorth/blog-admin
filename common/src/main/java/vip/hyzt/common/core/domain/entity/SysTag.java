package vip.hyzt.common.core.domain.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import vip.hyzt.common.annotation.Excel;
import vip.hyzt.common.core.domain.BaseEntity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 博客标签对象 sys_tag
 * 
 * @author hyzt
 * @date 2020-12-05
 */
public class SysTag extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 标签id */
    private Long tagId;

    /** 标签名称 */
    @NotBlank(message = "标签名称不能为空")
    @Size(min = 0, max = 30, message = "标签名称长度不能超过30个字符")
    @Excel(name = "标签名称")
    private String tagName;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setTagId(Long tagId) 
    {
        this.tagId = tagId;
    }

    public Long getTagId() 
    {
        return tagId;
    }

    public void setTagName(String tagName) 
    {
        this.tagName = tagName;
    }

    public String getTagName() 
    {
        return tagName;
    }

    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("tagId", getTagId())
            .append("tagName", getTagName())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
