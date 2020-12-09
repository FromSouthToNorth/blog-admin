package vip.hyzt.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.hyzt.common.annotation.DataScope;
import vip.hyzt.system.domain.SysGossip;
import vip.hyzt.system.mapper.SysGossipMapper;
import vip.hyzt.system.service.ISysGossipService;
import java.util.List;

/**
 * 随笔服务 实现层
 *
 * @author hyzt
 * @date 2020-12-09
 */
@Service
public class SysGossipServiceImpl implements ISysGossipService
{
    @Autowired
    private SysGossipMapper gossipMapper;

    /**
     * 查询随笔
     *
     * @param gossipId 随笔ID
     * @return 随笔
     */
    @Override
    public SysGossip selectGossipById(Long gossipId)
    {
        return gossipMapper.selectGossipById(gossipId);
    }

    /**
     * 查询随笔列表
     *
     * @param sysGossip 随笔
     * @return 随笔集合
     */
    @Override
    @DataScope(userAlias = "u")
    public List<SysGossip> selectGossipList(SysGossip sysGossip)
    {
        return gossipMapper.selectGossipList(sysGossip);
    }

    /**
     * 新增随笔
     *
     * @param sysGossip 随笔
     * @return 结果
     */
    @Override
    public int insertGossip(SysGossip sysGossip)
    {
        int row = gossipMapper.insertGossip(sysGossip);
        return row;
    }

    /**
     * 修改随笔
     *
     * @param sysGossip 随笔
     * @return 结果
     */
    @Override
    public int updateGossip(SysGossip sysGossip)
    {
        int row = gossipMapper.updateGossip(sysGossip);
        return row;
    }

    /**
     * 批量删除随笔
     *
     * @param gossipIds 需要删除的随笔ID
     * @return 结果
     */
    @Override
    public int deleteGossipByIds(Long[] gossipIds)
    {
        return gossipMapper.deleteGossipByIds(gossipIds);
    }

    /**
     * 删除随笔信息
     *
     * @param gossipId 随笔ID
     * @return 结果
     */
    @Override
    public int deleteGossipById(Long gossipId)
    {
        return gossipMapper.deleteGossipById(gossipId);
    }
}
