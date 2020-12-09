package vip.hyzt.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import vip.hyzt.common.annotation.Excel;
import vip.hyzt.common.core.domain.BaseEntity;

/**
 * 随笔对象 sys_gossip
 * 
 * @author hyzt
 * @date 2020-12-09
 */
public class SysGossip extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 随笔id */
    private Long gossipId;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 随笔内容 */
    @Excel(name = "随笔内容")
    private String gossipContent;

    /** 随笔点赞数 */
    @Excel(name = "随笔点赞数")
    private Long likeNumber;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setGossipId(Long gossipId) 
    {
        this.gossipId = gossipId;
    }

    public Long getGossipId() 
    {
        return gossipId;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setGossipContent(String gossipContent) 
    {
        this.gossipContent = gossipContent;
    }

    public String getGossipContent() 
    {
        return gossipContent;
    }

    public void setLikeNumber(Long likeNumber) 
    {
        this.likeNumber = likeNumber;
    }

    public Long getLikeNumber() 
    {
        return likeNumber;
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
            .append("gossipId", getGossipId())
            .append("userId", getUserId())
            .append("gossipContent", getGossipContent())
            .append("likeNumber", getLikeNumber())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
