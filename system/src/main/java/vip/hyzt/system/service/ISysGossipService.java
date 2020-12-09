package vip.hyzt.system.service;

import java.util.List;
import vip.hyzt.system.domain.SysGossip;

/**
 * 随笔Service接口
 * 
 * @author hyzt
 * @date 2020-12-09
 */
public interface ISysGossipService 
{
    /**
     * 查询随笔
     * 
     * @param gossipId 随笔ID
     * @return 随笔
     */
    public SysGossip selectGossipById(Long gossipId);

    /**
     * 查询随笔列表
     * 
     * @param sysGossip 随笔
     * @return 随笔集合
     */
    public List<SysGossip> selectGossipList(SysGossip sysGossip);

    /**
     * 新增随笔
     * 
     * @param sysGossip 随笔
     * @return 结果
     */
    public int insertGossip(SysGossip sysGossip);

    /**
     * 修改随笔
     * 
     * @param sysGossip 随笔
     * @return 结果
     */
    public int updateGossip(SysGossip sysGossip);

    /**
     * 批量删除随笔
     * 
     * @param gossipIds 需要删除的随笔ID
     * @return 结果
     */
    public int deleteGossipByIds(Long[] gossipIds);

    /**
     * 删除随笔信息
     * 
     * @param gossipId 随笔ID
     * @return 结果
     */
    public int deleteGossipById(Long gossipId);
}
